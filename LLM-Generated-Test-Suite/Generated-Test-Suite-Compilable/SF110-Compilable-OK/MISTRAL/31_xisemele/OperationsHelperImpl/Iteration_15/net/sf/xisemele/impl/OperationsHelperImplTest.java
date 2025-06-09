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
    public void setUp() throws ParserConfigurationException, SAXException, IOException {
        operationsHelper = new OperationsHelperImpl();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        String xml = "<root><child1><subchild1/><subchild2/></child1><child2/></root>";
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
        Node found = operationsHelper.find(document, "root/child1/subchild1");
        assertNotNull(found);
        assertEquals("subchild1", found.getNodeName());

        found = operationsHelper.find(document, "root/child3");
        assertNull(found);
    }

    @Test
    public void testNodeWithName() {
        Node root = document.getDocumentElement();
        List<Node> children = operationsHelper.children(root);
        Node node = operationsHelper.nodeWithName(children, "child1");
        assertNotNull(node);
        assertEquals("child1", node.getNodeName());

        node = operationsHelper.nodeWithName(children, "child3");
        assertNull(node);
    }

    @Test
    public void testSublistWithName() {
        Node root = document.getDocumentElement();
        List<Node> children = operationsHelper.children(root);
        List<Node> sublist = operationsHelper.sublistWithName(children, "child1");
        assertEquals(1, sublist.size());
        assertEquals("child1", sublist.get(0).getNodeName());

        sublist = operationsHelper.sublistWithName(children, "child3");
        assertEquals(0, sublist.size());
    }
}