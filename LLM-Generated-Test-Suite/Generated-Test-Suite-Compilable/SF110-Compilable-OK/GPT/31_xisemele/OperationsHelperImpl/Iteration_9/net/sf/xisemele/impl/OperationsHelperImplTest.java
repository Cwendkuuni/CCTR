package net.sf.xisemele.impl;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OperationsHelperImplTest {

    private OperationsHelperImpl operationsHelper;
    private Document document;

    @Before
    public void setUp() throws Exception {
        operationsHelper = new OperationsHelperImpl();
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
        LSParser parser = impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
        document = parser.parseURI(getClass().getResource("/test.xml").toString());
    }

    @Test
    public void testChildren() {
        Node root = document.getDocumentElement();
        List<Node> children = operationsHelper.children(root);
        assertNotNull(children);
        assertEquals(2, children.size());
        assertEquals("child1", children.get(0).getNodeName());
        assertEquals("child2", children.get(1).getNodeName());
    }

    @Test
    public void testFind() {
        Node foundNode = operationsHelper.find(document, "root/child1");
        assertNotNull(foundNode);
        assertEquals("child1", foundNode.getNodeName());

        Node notFoundNode = operationsHelper.find(document, "root/nonexistent");
        assertNull(notFoundNode);
    }

    @Test
    public void testNodeWithName() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(document.createElement("node1"));
        nodes.add(document.createElement("node2"));

        Node foundNode = operationsHelper.nodeWithName(nodes, "node1");
        assertNotNull(foundNode);
        assertEquals("node1", foundNode.getNodeName());

        Node notFoundNode = operationsHelper.nodeWithName(nodes, "nonexistent");
        assertNull(notFoundNode);
    }

    @Test
    public void testSublistWithName() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(document.createElement("node1"));
        nodes.add(document.createElement("node2"));
        nodes.add(document.createElement("node1"));

        List<Node> sublist = operationsHelper.sublistWithName(nodes, "node1");
        assertNotNull(sublist);
        assertEquals(2, sublist.size());
        assertEquals("node1", sublist.get(0).getNodeName());
        assertEquals("node1", sublist.get(1).getNodeName());

        List<Node> emptySublist = operationsHelper.sublistWithName(nodes, "nonexistent");
        assertNotNull(emptySublist);
        assertTrue(emptySublist.isEmpty());
    }
}