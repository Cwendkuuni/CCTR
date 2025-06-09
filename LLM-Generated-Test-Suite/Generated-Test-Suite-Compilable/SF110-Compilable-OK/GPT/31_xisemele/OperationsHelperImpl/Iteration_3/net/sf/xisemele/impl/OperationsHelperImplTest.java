package net.sf.xisemele.impl;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
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
        document = createSampleDocument();
    }

    private Document createSampleDocument() throws Exception {
        String xml = "<root><child1/><child2><subchild1/></child2><child3/></root>";
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
        LSInput input = impl.createLSInput();
        input.setStringData(xml);
        LSParser parser = impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
        return parser.parse(input);
    }

    @Test
    public void testChildren() {
        Node root = document.getDocumentElement();
        List<Node> children = operationsHelper.children(root);
        assertEquals(3, children.size());
        assertEquals("child1", children.get(0).getNodeName());
        assertEquals("child2", children.get(1).getNodeName());
        assertEquals("child3", children.get(2).getNodeName());
    }

    @Test
    public void testFind() {
        Node foundNode = operationsHelper.find(document, "root/child2/subchild1");
        assertNotNull(foundNode);
        assertEquals("subchild1", foundNode.getNodeName());

        Node notFoundNode = operationsHelper.find(document, "root/child2/nonexistent");
        assertNull(notFoundNode);
    }

    @Test
    public void testNodeWithName() {
        List<Node> children = operationsHelper.children(document.getDocumentElement());
        Node node = operationsHelper.nodeWithName(children, "child2");
        assertNotNull(node);
        assertEquals("child2", node.getNodeName());

        Node nonExistentNode = operationsHelper.nodeWithName(children, "nonexistent");
        assertNull(nonExistentNode);
    }

    @Test
    public void testSublistWithName() {
        List<Node> children = operationsHelper.children(document.getDocumentElement());
        List<Node> sublist = operationsHelper.sublistWithName(children, "child2");
        assertEquals(1, sublist.size());
        assertEquals("child2", sublist.get(0).getNodeName());

        List<Node> emptySublist = operationsHelper.sublistWithName(children, "nonexistent");
        assertTrue(emptySublist.isEmpty());
    }
}