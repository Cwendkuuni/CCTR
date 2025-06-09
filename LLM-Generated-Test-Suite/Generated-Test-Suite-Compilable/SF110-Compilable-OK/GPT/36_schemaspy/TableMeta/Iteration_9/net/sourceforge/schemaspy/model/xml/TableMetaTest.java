package net.sourceforge.schemaspy.model.xml;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Attr;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TableMetaTest {

    private Document document;
    private Element tableNode;
    private NamedNodeMap attributes;
    private NodeList columnNodes;

    @Before
    public void setUp() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.newDocument();

        tableNode = document.createElement("table");
        attributes = mock(NamedNodeMap.class);
        columnNodes = mock(NodeList.class);

        when(tableNode.getAttributes()).thenReturn(attributes);
        when(tableNode.getChildNodes()).thenReturn(columnNodes);
    }

    @Test
    public void testConstructorAndGetters() {
        // Mock attributes
        Attr nameAttr = mock(Attr.class);
        when(nameAttr.getNodeValue()).thenReturn("testTable");
        when(attributes.getNamedItem("name")).thenReturn(nameAttr);

        Attr commentsAttr = mock(Attr.class);
        when(commentsAttr.getNodeValue()).thenReturn("Test comments");
        when(attributes.getNamedItem("comments")).thenReturn(commentsAttr);

        Attr remoteCatalogAttr = mock(Attr.class);
        when(remoteCatalogAttr.getNodeValue()).thenReturn("testCatalog");
        when(attributes.getNamedItem("remoteCatalog")).thenReturn(remoteCatalogAttr);

        Attr remoteSchemaAttr = mock(Attr.class);
        when(remoteSchemaAttr.getNodeValue()).thenReturn("testSchema");
        when(attributes.getNamedItem("remoteSchema")).thenReturn(remoteSchemaAttr);

        // Mock columns
        Node columnNode = mock(Node.class);
        when(columnNodes.getLength()).thenReturn(1);
        when(columnNodes.item(0)).thenReturn(columnNode);

        TableColumnMeta columnMeta = mock(TableColumnMeta.class);
        when(columnNode.getNodeValue()).thenReturn("column1");
        when(columnMeta.getName()).thenReturn("column1");

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("Test comments", tableMeta.getComments());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
        assertEquals("testSchema", tableMeta.getRemoteSchema());

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertNotNull(columns);
        assertEquals(1, columns.size());
    }

    @Test
    public void testConstructorWithNoComments() {
        // Mock attributes
        Attr nameAttr = mock(Attr.class);
        when(nameAttr.getNodeValue()).thenReturn("testTable");
        when(attributes.getNamedItem("name")).thenReturn(nameAttr);

        when(attributes.getNamedItem("comments")).thenReturn(null);
        when(attributes.getNamedItem("remarks")).thenReturn(null);

        TableMeta tableMeta = new TableMeta(tableNode);

        assertNull(tableMeta.getComments());
    }

    @Test
    public void testConstructorWithEmptyComments() {
        // Mock attributes
        Attr nameAttr = mock(Attr.class);
        when(nameAttr.getNodeValue()).thenReturn("testTable");
        when(attributes.getNamedItem("name")).thenReturn(nameAttr);

        Attr commentsAttr = mock(Attr.class);
        when(commentsAttr.getNodeValue()).thenReturn(" ");
        when(attributes.getNamedItem("comments")).thenReturn(commentsAttr);

        TableMeta tableMeta = new TableMeta(tableNode);

        assertNull(tableMeta.getComments());
    }

    @Test
    public void testConstructorWithNoRemoteSchema() {
        // Mock attributes
        Attr nameAttr = mock(Attr.class);
        when(nameAttr.getNodeValue()).thenReturn("testTable");
        when(attributes.getNamedItem("name")).thenReturn(nameAttr);

        when(attributes.getNamedItem("remoteSchema")).thenReturn(null);

        TableMeta tableMeta = new TableMeta(tableNode);

        assertNull(tableMeta.getRemoteSchema());
    }

    @Test
    public void testConstructorWithNoRemoteCatalog() {
        // Mock attributes
        Attr nameAttr = mock(Attr.class);
        when(nameAttr.getNodeValue()).thenReturn("testTable");
        when(attributes.getNamedItem("name")).thenReturn(nameAttr);

        when(attributes.getNamedItem("remoteCatalog")).thenReturn(null);

        TableMeta tableMeta = new TableMeta(tableNode);

        assertNull(tableMeta.getRemoteCatalog());
    }
}