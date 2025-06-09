package net.sf.xisemele.impl;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OperationsHelperImplTest {

    private OperationsHelperImpl operationsHelper;
    private Document document;

    @Before
    public void setUp() throws ParserConfigurationException, IOException, SAXException {
        operationsHelper = new OperationsHelperImpl();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(getClass().getResourceAsStream("/test.xml"));
    }

    @Test
    public void testChildren() {
        Node root = document.getDocumentElement();
        List<Node> children = operationsHelper.children(root);
        assertNotNull(children);
        assertTrue(children.size() > 0);
        for (Node child : children) {
            assertEquals(Node.ELEMENT_NODE, child.getNodeType());
        }
    }

    @Test
    public void testFind() {
        Node foundNode = operationsHelper.find(document, "root/child1");
        assertNotNull(foundNode);
        assertEquals("child1", foundNode.getNodeName());

        foundNode = operationsHelper.find(document, "root/nonexistent");
        assertNull(foundNode);
    }

    @Test
    public void testNodeWithName() {
        Node root = document.getDocumentElement();
        List<Node> children = operationsHelper.children(root);
        Node node = operationsHelper.nodeWithName(children, "child1");
        assertNotNull(node);
        assertEquals("child1", node.getNodeName());

        node = operationsHelper.nodeWithName(children, "nonexistent");
        assertNull(node);
    }

    @Test
    public void testSublistWithName() {
        Node root = document.getDocumentElement();
        List<Node> children = operationsHelper.children(root);
        List<Node> sublist = operationsHelper.sublistWithName(children, "child1");
        assertNotNull(sublist);
        assertTrue(sublist.size() > 0);
        for (Node node : sublist) {
            assertEquals("child1", node.getNodeName());
        }

        sublist = operationsHelper.sublistWithName(children, "nonexistent");
        assertNotNull(sublist);
        assertTrue(sublist.isEmpty());
    }
}