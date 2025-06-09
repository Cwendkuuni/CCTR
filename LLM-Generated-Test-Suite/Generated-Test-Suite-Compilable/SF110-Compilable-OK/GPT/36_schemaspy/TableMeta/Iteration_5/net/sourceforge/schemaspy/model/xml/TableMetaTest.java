package net.sourceforge.schemaspy.model.xml;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TableMetaTest {

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;

    @Before
    public void setUp() throws Exception {
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.newDocument();
    }

    private Node createTableNode(String name, String comments, String remoteCatalog, String remoteSchema, int columnCount) {
        Element tableElement = document.createElement("table");
        tableElement.setAttribute("name", name);
        if (comments != null) {
            tableElement.setAttribute("comments", comments);
        }
        if (remoteCatalog != null) {
            tableElement.setAttribute("remoteCatalog", remoteCatalog);
        }
        if (remoteSchema != null) {
            tableElement.setAttribute("remoteSchema", remoteSchema);
        }

        for (int i = 0; i < columnCount; i++) {
            Element columnElement = document.createElement("column");
            tableElement.appendChild(columnElement);
        }

        return tableElement;
    }

    @Test
    public void testConstructorWithValidAttributes() {
        Node tableNode = createTableNode("testTable", "Test Comments", "testCatalog", "testSchema", 3);
        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("Test Comments", tableMeta.getComments());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertEquals(3, tableMeta.getColumns().size());
    }

    @Test
    public void testConstructorWithMissingComments() {
        Node tableNode = createTableNode("testTable", null, "testCatalog", "testSchema", 2);
        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertNull(tableMeta.getComments());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertEquals(2, tableMeta.getColumns().size());
    }

    @Test
    public void testConstructorWithMissingRemoteCatalogAndSchema() {
        Node tableNode = createTableNode("testTable", "Test Comments", null, null, 1);
        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("Test Comments", tableMeta.getComments());
        assertNull(tableMeta.getRemoteCatalog());
        assertNull(tableMeta.getRemoteSchema());
        assertEquals(1, tableMeta.getColumns().size());
    }

    @Test
    public void testConstructorWithNoColumns() {
        Node tableNode = createTableNode("testTable", "Test Comments", "testCatalog", "testSchema", 0);
        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("Test Comments", tableMeta.getComments());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertTrue(tableMeta.getColumns().isEmpty());
    }

    @Test
    public void testGetColumns() {
        Node tableNode = createTableNode("testTable", "Test Comments", "testCatalog", "testSchema", 5);
        TableMeta tableMeta = new TableMeta(tableNode);

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertNotNull(columns);
        assertEquals(5, columns.size());
    }
}