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

public class TableMetaTest {

    private Document document;

    @Before
    public void setUp() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
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
    public void testConstructorAndGetters() {
        Node tableNode = createTableNode("TestTable", "Test comments", "TestCatalog", "TestSchema", 3);
        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("TestTable", tableMeta.getName());
        assertEquals("Test comments", tableMeta.getComments());
        assertEquals("TestCatalog", tableMeta.getRemoteCatalog());
        assertEquals("TestSchema", tableMeta.getRemoteSchema());

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertNotNull(columns);
        assertEquals(3, columns.size());
    }

    @Test
    public void testConstructorWithNoComments() {
        Node tableNode = createTableNode("TestTable", null, "TestCatalog", "TestSchema", 2);
        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("TestTable", tableMeta.getName());
        assertNull(tableMeta.getComments());
        assertEquals("TestCatalog", tableMeta.getRemoteCatalog());
        assertEquals("TestSchema", tableMeta.getRemoteSchema());

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertNotNull(columns);
        assertEquals(2, columns.size());
    }

    @Test
    public void testConstructorWithNoRemoteCatalogAndSchema() {
        Node tableNode = createTableNode("TestTable", "Test comments", null, null, 1);
        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("TestTable", tableMeta.getName());
        assertEquals("Test comments", tableMeta.getComments());
        assertNull(tableMeta.getRemoteCatalog());
        assertNull(tableMeta.getRemoteSchema());

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertNotNull(columns);
        assertEquals(1, columns.size());
    }

    @Test
    public void testConstructorWithEmptyComments() {
        Node tableNode = createTableNode("TestTable", "", "TestCatalog", "TestSchema", 0);
        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("TestTable", tableMeta.getName());
        assertNull(tableMeta.getComments());
        assertEquals("TestCatalog", tableMeta.getRemoteCatalog());
        assertEquals("TestSchema", tableMeta.getRemoteSchema());

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertNotNull(columns);
        assertEquals(0, columns.size());
    }
}