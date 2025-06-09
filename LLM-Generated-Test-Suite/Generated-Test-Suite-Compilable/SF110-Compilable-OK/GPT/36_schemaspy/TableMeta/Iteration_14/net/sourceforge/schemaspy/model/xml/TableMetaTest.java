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
    private NamedNodeMap namedNodeMap;
    private Node nameNode;
    private Node commentsNode;
    private Node remoteSchemaNode;
    private Node remoteCatalogNode;
    private NodeList columnNodeList;
    private Node columnNode;

    @Before
    public void setUp() {
        tableNode = mock(Node.class);
        namedNodeMap = mock(NamedNodeMap.class);
        nameNode = mock(Node.class);
        commentsNode = mock(Node.class);
        remoteSchemaNode = mock(Node.class);
        remoteCatalogNode = mock(Node.class);
        columnNodeList = mock(NodeList.class);
        columnNode = mock(Node.class);

        when(tableNode.getAttributes()).thenReturn(namedNodeMap);
        when(namedNodeMap.getNamedItem("name")).thenReturn(nameNode);
        when(nameNode.getNodeValue()).thenReturn("TestTable");

        when(namedNodeMap.getNamedItem("comments")).thenReturn(commentsNode);
        when(commentsNode.getNodeValue()).thenReturn("Test Comments");

        when(namedNodeMap.getNamedItem("remoteSchema")).thenReturn(remoteSchemaNode);
        when(remoteSchemaNode.getNodeValue()).thenReturn("TestSchema");

        when(namedNodeMap.getNamedItem("remoteCatalog")).thenReturn(remoteCatalogNode);
        when(remoteCatalogNode.getNodeValue()).thenReturn("TestCatalog");

        when(tableNode.getChildNodes()).thenReturn(columnNodeList);
        when(columnNodeList.getLength()).thenReturn(1);
        when(columnNodeList.item(0)).thenReturn(columnNode);
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
    public void testGetRemoteSchema() {
        TableMeta tableMeta = new TableMeta(tableNode);
        assertEquals("TestSchema", tableMeta.getRemoteSchema());
    }

    @Test
    public void testGetRemoteCatalog() {
        TableMeta tableMeta = new TableMeta(tableNode);
        assertEquals("TestCatalog", tableMeta.getRemoteCatalog());
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
        when(namedNodeMap.getNamedItem("comments")).thenReturn(null);
        when(namedNodeMap.getNamedItem("remarks")).thenReturn(commentsNode);
        when(commentsNode.getNodeValue()).thenReturn("Remarks Comments");

        TableMeta tableMeta = new TableMeta(tableNode);
        assertEquals("Remarks Comments", tableMeta.getComments());
    }

    @Test
    public void testNullCommentsWhenEmpty() {
        when(namedNodeMap.getNamedItem("comments")).thenReturn(commentsNode);
        when(commentsNode.getNodeValue()).thenReturn("   ");

        TableMeta tableMeta = new TableMeta(tableNode);
        assertNull(tableMeta.getComments());
    }

    @Test
    public void testNullRemoteSchema() {
        when(namedNodeMap.getNamedItem("remoteSchema")).thenReturn(null);

        TableMeta tableMeta = new TableMeta(tableNode);
        assertNull(tableMeta.getRemoteSchema());
    }

    @Test
    public void testNullRemoteCatalog() {
        when(namedNodeMap.getNamedItem("remoteCatalog")).thenReturn(null);

        TableMeta tableMeta = new TableMeta(tableNode);
        assertNull(tableMeta.getRemoteCatalog());
    }
}