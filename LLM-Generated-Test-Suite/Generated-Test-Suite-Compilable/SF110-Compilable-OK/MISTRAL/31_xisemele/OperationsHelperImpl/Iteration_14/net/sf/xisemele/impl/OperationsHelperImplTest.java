package net.sf.xisemele.impl;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
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
        String xml = "<root><child1><grandchild1/><grandchild2/></child1><child2/></root>";
        document = builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }

    @Test
    public void testChildren() {
        Node root = document.getDocumentElement();
        List<Node> children = operationsHelper.children(root);
        assertEquals(2, children.size());
        assertEquals("child1", children.get(0).getNodeName());
        assertEquals("child2", children.get(1).getNodeName());
    }

    @Test
    public void testFind() {
        Node foundNode = operationsHelper.find(document, "root/child1/grandchild1");
        assertNotNull(foundNode);
        assertEquals("grandchild1", foundNode.getNodeName());

        foundNode = operationsHelper.find(document, "root/child1/grandchild2");
        assertNotNull(foundNode);
        assertEquals("grandchild2", foundNode.getNodeName());

        foundNode = operationsHelper.find(document, "root/child2");
        assertNotNull(foundNode);
        assertEquals("child2", foundNode.getNodeName());

        foundNode = operationsHelper.find(document, "root/nonexistent");
        assertNull(foundNode);
    }

    @Test
    public void testNodeWithName() {
        Node root = document.getDocumentElement();
        List<Node> children = operationsHelper.children(root);
        Node child1 = operationsHelper.nodeWithName(children, "child1");
        assertNotNull(child1);
        assertEquals("child1", child1.getNodeName());

        Node child2 = operationsHelper.nodeWithName(children, "child2");
        assertNotNull(child2);
        assertEquals("child2", child2.getNodeName());

        Node nonexistent = operationsHelper.nodeWithName(children, "nonexistent");
        assertNull(nonexistent);
    }

    @Test
    public void testSublistWithName() {
        Node root = document.getDocumentElement();
        List<Node> children = operationsHelper.children(root);
        List<Node> sublist = operationsHelper.sublistWithName(children, "child1");
        assertEquals(1, sublist.size());
        assertEquals("child1", sublist.get(0).getNodeName());

        sublist = operationsHelper.sublistWithName(children, "child2");
        assertEquals(1, sublist.size());
        assertEquals("child2", sublist.get(0).getNodeName());

        sublist = operationsHelper.sublistWithName(children, "nonexistent");
        assertEquals(0, sublist.size());
    }
}