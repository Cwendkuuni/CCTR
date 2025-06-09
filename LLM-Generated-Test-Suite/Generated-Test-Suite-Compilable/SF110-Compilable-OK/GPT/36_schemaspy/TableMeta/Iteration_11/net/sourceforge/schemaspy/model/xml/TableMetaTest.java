package net.sourceforge.schemaspy.model.xml;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TableMetaTest {

    private Document document;
    private Element tableElement;

    @Before
    public void setUp() throws Exception {
        // Initialize a DOM Document for testing
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
        LSParser parser = impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
        document = parser.parseURI("data:application/xml,<table name='testTable' comments='Test comments' remoteCatalog='testCatalog' remoteSchema='testSchema'><column name='col1'/><column name='col2'/></table>");

        // Get the table element
        tableElement = document.getDocumentElement();
    }

    @Test
    public void testConstructor() {
        TableMeta tableMeta = new TableMeta(tableElement);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("Test comments", tableMeta.getComments());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
        assertEquals("testSchema", tableMeta.getRemoteSchema());

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertNotNull(columns);
        assertEquals(2, columns.size());
    }

    @Test
    public void testGetName() {
        TableMeta tableMeta = new TableMeta(tableElement);
        assertEquals("testTable", tableMeta.getName());
    }

    @Test
    public void testGetComments() {
        TableMeta tableMeta = new TableMeta(tableElement);
        assertEquals("Test comments", tableMeta.getComments());
    }

    @Test
    public void testGetColumns() {
        TableMeta tableMeta = new TableMeta(tableElement);
        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertNotNull(columns);
        assertEquals(2, columns.size());
    }

    @Test
    public void testGetRemoteCatalog() {
        TableMeta tableMeta = new TableMeta(tableElement);
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
    }

    @Test
    public void testGetRemoteSchema() {
        TableMeta tableMeta = new TableMeta(tableElement);
        assertEquals("testSchema", tableMeta.getRemoteSchema());
    }

    @Test
    public void testConstructorWithMissingAttributes() {
        // Create a mock Node with missing attributes
        NamedNodeMap mockAttributes = mock(NamedNodeMap.class);
        when(mockAttributes.getNamedItem("name")).thenReturn(createMockNode("name", "testTable"));
        when(mockAttributes.getNamedItem("comments")).thenReturn(null);
        when(mockAttributes.getNamedItem("remarks")).thenReturn(null);
        when(mockAttributes.getNamedItem("remoteCatalog")).thenReturn(null);
        when(mockAttributes.getNamedItem("remoteSchema")).thenReturn(null);

        Node mockNode = mock(Node.class);
        when(mockNode.getAttributes()).thenReturn(mockAttributes);
        when(mockNode.getChildNodes()).thenReturn(document.createElement("columns").getChildNodes());

        TableMeta tableMeta = new TableMeta(mockNode);

        assertEquals("testTable", tableMeta.getName());
        assertNull(tableMeta.getComments());
        assertNull(tableMeta.getRemoteCatalog());
        assertNull(tableMeta.getRemoteSchema());
        assertTrue(tableMeta.getColumns().isEmpty());
    }

    private Node createMockNode(String name, String value) {
        Node mockNode = mock(Node.class);
        when(mockNode.getNodeName()).thenReturn(name);
        when(mockNode.getNodeValue()).thenReturn(value);
        return mockNode;
    }
}