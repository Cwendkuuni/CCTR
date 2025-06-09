package net.sourceforge.schemaspy.model.xml;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TableMetaTest {

    private Node tableNode;
    private NamedNodeMap attributes;
    private Node nameNode;
    private Node commentsNode;
    private Node remoteCatalogNode;
    private Node remoteSchemaNode;
    private NodeList columnNodes;
    private Node columnNode;

    @Before
    public void setUp() {
        tableNode = mock(Node.class);
        attributes = mock(NamedNodeMap.class);
        nameNode = mock(Node.class);
        commentsNode = mock(Node.class);
        remoteCatalogNode = mock(Node.class);
        remoteSchemaNode = mock(Node.class);
        columnNodes = mock(NodeList.class);
        columnNode = mock(Node.class);

        when(tableNode.getAttributes()).thenReturn(attributes);
        when(attributes.getNamedItem("name")).thenReturn(nameNode);
        when(nameNode.getNodeValue()).thenReturn("TestTable");

        when(attributes.getNamedItem("comments")).thenReturn(commentsNode);
        when(commentsNode.getNodeValue()).thenReturn("Test Comments");

        when(attributes.getNamedItem("remoteCatalog")).thenReturn(remoteCatalogNode);
        when(remoteCatalogNode.getNodeValue()).thenReturn("TestCatalog");

        when(attributes.getNamedItem("remoteSchema")).thenReturn(remoteSchemaNode);
        when(remoteSchemaNode.getNodeValue()).thenReturn("TestSchema");

        when(tableNode.getChildNodes()).thenReturn(columnNodes);
        when(columnNodes.getLength()).thenReturn(1);
        when(columnNodes.item(0)).thenReturn(columnNode);
    }

    @Test
    public void testGetName() {
        TableMeta tableMeta = new TableMeta(tableNode);
        assertEquals("TestTable", tableMeta.getName());
    }

    @Test
    public void testGetComments() {
        TableMeta tableMeta = new TableMeta(tableNode);
        assertEquals("Test Comments", tableMeta.getComments());
    }

    @Test
    public void testGetRemoteCatalog() {
        TableMeta tableMeta = new TableMeta(tableNode);
        assertEquals("TestCatalog", tableMeta.getRemoteCatalog());
    }

    @Test
    public void testGetRemoteSchema() {
        TableMeta tableMeta = new TableMeta(tableNode);
        assertEquals("TestSchema", tableMeta.getRemoteSchema());
    }

    @Test
    public void testGetColumns() {
        TableMeta tableMeta = new TableMeta(tableNode);
        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertNotNull(columns);
        assertEquals(1, columns.size());
        assertTrue(columns.get(0) instanceof TableColumnMeta);
    }

    @Test
    public void testCommentsFallbackToRemarks() {
        when(attributes.getNamedItem("comments")).thenReturn(null);
        when(attributes.getNamedItem("remarks")).thenReturn(commentsNode);
        when(commentsNode.getNodeValue()).thenReturn("Remarks Comments");

        TableMeta tableMeta = new TableMeta(tableNode);
        assertEquals("Remarks Comments", tableMeta.getComments());
    }

    @Test
    public void testCommentsNullWhenEmpty() {
        when(commentsNode.getNodeValue()).thenReturn("   ");

        TableMeta tableMeta = new TableMeta(tableNode);
        assertNull(tableMeta.getComments());
    }

    @Test
    public void testRemoteCatalogNullWhenMissing() {
        when(attributes.getNamedItem("remoteCatalog")).thenReturn(null);

        TableMeta tableMeta = new TableMeta(tableNode);
        assertNull(tableMeta.getRemoteCatalog());
    }

    @Test
    public void testRemoteSchemaNullWhenMissing() {
        when(attributes.getNamedItem("remoteSchema")).thenReturn(null);

        TableMeta tableMeta = new TableMeta(tableNode);
        assertNull(tableMeta.getRemoteSchema());
    }
}