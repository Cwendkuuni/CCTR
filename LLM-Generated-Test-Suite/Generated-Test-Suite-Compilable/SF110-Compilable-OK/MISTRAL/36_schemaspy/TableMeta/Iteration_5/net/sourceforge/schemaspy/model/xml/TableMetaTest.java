package net.sourceforge.schemaspy.model.xml;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TableMetaTest {

    private Document document;
    private Node tableNode;

    @Before
    public void setUp() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(getClass().getResourceAsStream("/testTable.xml"));
        tableNode = document.getElementsByTagName("table").item(0);
    }

    @Test
    public void testConstructor() {
        TableMeta tableMeta = new TableMeta(tableNode);
        assertNotNull(tableMeta);
    }

    @Test
    public void testGetName() {
        TableMeta tableMeta = new TableMeta(tableNode);
        assertEquals("testTable", tableMeta.getName());
    }

    @Test
    public void testGetComments() {
        TableMeta tableMeta = new TableMeta(tableNode);
        assertEquals("This is a test table", tableMeta.getComments());
    }

    @Test
    public void testGetColumns() {
        TableMeta tableMeta = new TableMeta(tableNode);
        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertNotNull(columns);
        assertEquals(2, columns.size());
        assertEquals("id", columns.get(0).getName());
        assertEquals("name", columns.get(1).getName());
    }

    @Test
    public void testGetRemoteCatalog() {
        TableMeta tableMeta = new TableMeta(tableNode);
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
    }

    @Test
    public void testGetRemoteSchema() {
        TableMeta tableMeta = new TableMeta(tableNode);
        assertEquals("testSchema", tableMeta.getRemoteSchema());
    }

    @Test
    public void testNullComments() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(getClass().getResourceAsStream("/testTableNoComments.xml"));
        Node node = doc.getElementsByTagName("table").item(0);

        TableMeta tableMeta = new TableMeta(node);
        assertNull(tableMeta.getComments());
    }

    @Test
    public void testNullRemoteCatalog() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(getClass().getResourceAsStream("/testTableNoRemoteCatalog.xml"));
        Node node = doc.getElementsByTagName("table").item(0);

        TableMeta tableMeta = new TableMeta(node);
        assertNull(tableMeta.getRemoteCatalog());
    }

    @Test
    public void testNullRemoteSchema() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(getClass().getResourceAsStream("/testTableNoRemoteSchema.xml"));
        Node node = doc.getElementsByTagName("table").item(0);

        TableMeta tableMeta = new TableMeta(node);
        assertNull(tableMeta.getRemoteSchema());
    }
}