package net.sourceforge.schemaspy.model.xml;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

public class TableMetaTest {

    private Document document;

    @Before
    public void setUp() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.newDocument();
    }

    @Test
    public void testConstructorWithValidNode() throws Exception {
        Element tableNode = document.createElement("table");
        tableNode.setAttribute("name", "testTable");
        tableNode.setAttribute("comments", "test comments");
        tableNode.setAttribute("remoteSchema", "testSchema");
        tableNode.setAttribute("remoteCatalog", "testCatalog");

        Element columnNode = document.createElement("column");
        columnNode.setAttribute("name", "testColumn");
        tableNode.appendChild(columnNode);

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("test comments", tableMeta.getComments());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
        assertNotNull(tableMeta.getColumns());
        assertEquals(1, tableMeta.getColumns().size());
    }

    @Test
    public void testConstructorWithNoComments() throws Exception {
        Element tableNode = document.createElement("table");
        tableNode.setAttribute("name", "testTable");
        tableNode.setAttribute("remoteSchema", "testSchema");
        tableNode.setAttribute("remoteCatalog", "testCatalog");

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertNull(tableMeta.getComments());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
    }

    @Test
    public void testConstructorWithEmptyComments() throws Exception {
        Element tableNode = document.createElement("table");
        tableNode.setAttribute("name", "testTable");
        tableNode.setAttribute("comments", "   ");
        tableNode.setAttribute("remoteSchema", "testSchema");
        tableNode.setAttribute("remoteCatalog", "testCatalog");

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertNull(tableMeta.getComments());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
    }

    @Test
    public void testConstructorWithNoRemoteSchema() throws Exception {
        Element tableNode = document.createElement("table");
        tableNode.setAttribute("name", "testTable");
        tableNode.setAttribute("comments", "test comments");
        tableNode.setAttribute("remoteCatalog", "testCatalog");

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("test comments", tableMeta.getComments());
        assertNull(tableMeta.getRemoteSchema());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
    }

    @Test
    public void testConstructorWithNoRemoteCatalog() throws Exception {
        Element tableNode = document.createElement("table");
        tableNode.setAttribute("name", "testTable");
        tableNode.setAttribute("comments", "test comments");
        tableNode.setAttribute("remoteSchema", "testSchema");

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("test comments", tableMeta.getComments());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertNull(tableMeta.getRemoteCatalog());
    }

    @Test
    public void testConstructorWithMultipleColumns() throws Exception {
        Element tableNode = document.createElement("table");
        tableNode.setAttribute("name", "testTable");
        tableNode.setAttribute("comments", "test comments");
        tableNode.setAttribute("remoteSchema", "testSchema");
        tableNode.setAttribute("remoteCatalog", "testCatalog");

        Element columnNode1 = document.createElement("column");
        columnNode1.setAttribute("name", "testColumn1");
        tableNode.appendChild(columnNode1);

        Element columnNode2 = document.createElement("column");
        columnNode2.setAttribute("name", "testColumn2");
        tableNode.appendChild(columnNode2);

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("test comments", tableMeta.getComments());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
        assertNotNull(tableMeta.getColumns());
        assertEquals(2, tableMeta.getColumns().size());
    }
}