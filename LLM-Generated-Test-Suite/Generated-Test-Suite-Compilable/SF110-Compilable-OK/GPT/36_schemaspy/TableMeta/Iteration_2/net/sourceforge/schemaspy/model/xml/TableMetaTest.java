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

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;
    private Element tableNode;
    private NamedNodeMap attributes;
    private NodeList columnNodes;

    @Before
    public void setUp() throws Exception {
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.newDocument();

        tableNode = document.createElement("table");
        attributes = mock(NamedNodeMap.class);
        columnNodes = mock(NodeList.class);

        when(tableNode.getAttributes()).thenReturn(attributes);
        when(tableNode.getChildNodes()).thenReturn(columnNodes);
    }

    @Test
    public void testConstructorAndGetters() {
        // Set up mock attributes
        Attr nameAttr = document.createAttribute("name");
        nameAttr.setValue("testTable");
        when(attributes.getNamedItem("name")).thenReturn(nameAttr);

        Attr commentsAttr = document.createAttribute("comments");
        commentsAttr.setValue("This is a test table");
        when(attributes.getNamedItem("comments")).thenReturn(commentsAttr);

        Attr remoteCatalogAttr = document.createAttribute("remoteCatalog");
        remoteCatalogAttr.setValue("testCatalog");
        when(attributes.getNamedItem("remoteCatalog")).thenReturn(remoteCatalogAttr);

        Attr remoteSchemaAttr = document.createAttribute("remoteSchema");
        remoteSchemaAttr.setValue("testSchema");
        when(attributes.getNamedItem("remoteSchema")).thenReturn(remoteSchemaAttr);

        // Set up mock column nodes
        Node columnNode = mock(Node.class);
        when(columnNodes.getLength()).thenReturn(1);
        when(columnNodes.item(0)).thenReturn(columnNode);

        // Create TableMeta instance
        TableMeta tableMeta = new TableMeta(tableNode);

        // Verify getters
        assertEquals("testTable", tableMeta.getName());
        assertEquals("This is a test table", tableMeta.getComments());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
        assertEquals("testSchema", tableMeta.getRemoteSchema());

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertNotNull(columns);
        assertEquals(1, columns.size());
    }

    @Test
    public void testConstructorWithNoComments() {
        // Set up mock attributes
        Attr nameAttr = document.createAttribute("name");
        nameAttr.setValue("testTable");
        when(attributes.getNamedItem("name")).thenReturn(nameAttr);

        // No comments or remarks attribute
        when(attributes.getNamedItem("comments")).thenReturn(null);
        when(attributes.getNamedItem("remarks")).thenReturn(null);

        // Create TableMeta instance
        TableMeta tableMeta = new TableMeta(tableNode);

        // Verify getters
        assertEquals("testTable", tableMeta.getName());
        assertNull(tableMeta.getComments());
    }

    @Test
    public void testConstructorWithRemarks() {
        // Set up mock attributes
        Attr nameAttr = document.createAttribute("name");
        nameAttr.setValue("testTable");
        when(attributes.getNamedItem("name")).thenReturn(nameAttr);

        Attr remarksAttr = document.createAttribute("remarks");
        remarksAttr.setValue("This is a test table with remarks");
        when(attributes.getNamedItem("remarks")).thenReturn(remarksAttr);

        // Create TableMeta instance
        TableMeta tableMeta = new TableMeta(tableNode);

        // Verify getters
        assertEquals("testTable", tableMeta.getName());
        assertEquals("This is a test table with remarks", tableMeta.getComments());
    }

    @Test
    public void testConstructorWithEmptyComments() {
        // Set up mock attributes
        Attr nameAttr = document.createAttribute("name");
        nameAttr.setValue("testTable");
        when(attributes.getNamedItem("name")).thenReturn(nameAttr);

        Attr commentsAttr = document.createAttribute("comments");
        commentsAttr.setValue("   "); // Empty comments
        when(attributes.getNamedItem("comments")).thenReturn(commentsAttr);

        // Create TableMeta instance
        TableMeta tableMeta = new TableMeta(tableNode);

        // Verify getters
        assertEquals("testTable", tableMeta.getName());
        assertNull(tableMeta.getComments());
    }
}