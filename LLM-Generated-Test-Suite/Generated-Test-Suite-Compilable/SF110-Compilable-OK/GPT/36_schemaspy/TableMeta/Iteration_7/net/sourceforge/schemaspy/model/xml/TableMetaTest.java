package net.sourceforge.schemaspy.model.xml;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;

import java.util.List;
import net.sourceforge.schemaspy.model.xml.TableMeta;
import net.sourceforge.schemaspy.model.xml.TableColumnMeta;

public class TableMetaTest {

    private Node tableNode;
    private NamedNodeMap attributes;
    private Node nameNode;
    private Node commentsNode;
    private Node remoteSchemaNode;
    private Node remoteCatalogNode;
    private NodeList columnNodes;
    private Node columnNode;

    @Before
    public void setUp() {
        // Mocking the Node and NamedNodeMap
        tableNode = mock(Node.class);
        attributes = mock(NamedNodeMap.class);
        nameNode = mock(Node.class);
        commentsNode = mock(Node.class);
        remoteSchemaNode = mock(Node.class);
        remoteCatalogNode = mock(Node.class);
        columnNodes = mock(NodeList.class);
        columnNode = mock(Node.class);

        // Setting up the mock behavior
        when(tableNode.getAttributes()).thenReturn(attributes);
        when(attributes.getNamedItem("name")).thenReturn(nameNode);
        when(nameNode.getNodeValue()).thenReturn("TestTable");

        when(attributes.getNamedItem("comments")).thenReturn(commentsNode);
        when(commentsNode.getNodeValue()).thenReturn("Test Comments");

        when(attributes.getNamedItem("remoteSchema")).thenReturn(remoteSchemaNode);
        when(remoteSchemaNode.getNodeValue()).thenReturn("TestSchema");

        when(attributes.getNamedItem("remoteCatalog")).thenReturn(remoteCatalogNode);
        when(remoteCatalogNode.getNodeValue()).thenReturn("TestCatalog");

        when(tableNode.getChildNodes()).thenReturn(columnNodes);
        when(columnNodes.getLength()).thenReturn(1);
        when(columnNodes.item(0)).thenReturn(columnNode);
    }

    @Test
    public void testConstructorAndGetters() {
        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("TestTable", tableMeta.getName());
        assertEquals("Test Comments", tableMeta.getComments());
        assertEquals("TestSchema", tableMeta.getRemoteSchema());
        assertEquals("TestCatalog", tableMeta.getRemoteCatalog());

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertNotNull(columns);
        assertEquals(1, columns.size());
    }

    @Test
    public void testConstructorWithNoComments() {
        when(attributes.getNamedItem("comments")).thenReturn(null);
        when(attributes.getNamedItem("remarks")).thenReturn(null);

        TableMeta tableMeta = new TableMeta(tableNode);

        assertNull(tableMeta.getComments());
    }

    @Test
    public void testConstructorWithEmptyComments() {
        when(commentsNode.getNodeValue()).thenReturn("");

        TableMeta tableMeta = new TableMeta(tableNode);

        assertNull(tableMeta.getComments());
    }

    @Test
    public void testConstructorWithNoRemoteSchema() {
        when(attributes.getNamedItem("remoteSchema")).thenReturn(null);

        TableMeta tableMeta = new TableMeta(tableNode);

        assertNull(tableMeta.getRemoteSchema());
    }

    @Test
    public void testConstructorWithNoRemoteCatalog() {
        when(attributes.getNamedItem("remoteCatalog")).thenReturn(null);

        TableMeta tableMeta = new TableMeta(tableNode);

        assertNull(tableMeta.getRemoteCatalog());
    }
}