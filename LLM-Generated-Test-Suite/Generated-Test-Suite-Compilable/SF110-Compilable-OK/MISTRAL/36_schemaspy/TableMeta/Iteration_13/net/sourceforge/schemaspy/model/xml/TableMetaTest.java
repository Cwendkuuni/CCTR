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

    private DocumentBuilder documentBuilder;

    @Before
    public void setUp() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        documentBuilder = factory.newDocumentBuilder();
    }

    @Test
    public void testConstructorWithValidXML() throws SAXException, IOException {
        String xml = "<table name=\"testTable\" comments=\"test comments\" remoteSchema=\"testSchema\" remoteCatalog=\"testCatalog\">" +
                     "<column name=\"col1\" type=\"int\"/>" +
                     "<column name=\"col2\" type=\"varchar\"/>" +
                     "</table>";
        Document doc = documentBuilder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));
        Node tableNode = doc.getFirstChild();

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("test comments", tableMeta.getComments());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertEquals(2, columns.size());
        assertEquals("col1", columns.get(0).getName());
        assertEquals("col2", columns.get(1).getName());
    }

    @Test
    public void testConstructorWithMissingComments() throws SAXException, IOException {
        String xml = "<table name=\"testTable\" remoteSchema=\"testSchema\" remoteCatalog=\"testCatalog\">" +
                     "<column name=\"col1\" type=\"int\"/>" +
                     "</table>";
        Document doc = documentBuilder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));
        Node tableNode = doc.getFirstChild();

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertNull(tableMeta.getComments());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertEquals(1, columns.size());
        assertEquals("col1", columns.get(0).getName());
    }

    @Test
    public void testConstructorWithEmptyComments() throws SAXException, IOException {
        String xml = "<table name=\"testTable\" comments=\"\" remoteSchema=\"testSchema\" remoteCatalog=\"testCatalog\">" +
                     "<column name=\"col1\" type=\"int\"/>" +
                     "</table>";
        Document doc = documentBuilder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));
        Node tableNode = doc.getFirstChild();

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertNull(tableMeta.getComments());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertEquals(1, columns.size());
        assertEquals("col1", columns.get(0).getName());
    }

    @Test
    public void testConstructorWithMissingRemoteSchema() throws SAXException, IOException {
        String xml = "<table name=\"testTable\" comments=\"test comments\" remoteCatalog=\"testCatalog\">" +
                     "<column name=\"col1\" type=\"int\"/>" +
                     "</table>";
        Document doc = documentBuilder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));
        Node tableNode = doc.getFirstChild();

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("test comments", tableMeta.getComments());
        assertNull(tableMeta.getRemoteSchema());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertEquals(1, columns.size());
        assertEquals("col1", columns.get(0).getName());
    }

    @Test
    public void testConstructorWithMissingRemoteCatalog() throws SAXException, IOException {
        String xml = "<table name=\"testTable\" comments=\"test comments\" remoteSchema=\"testSchema\">" +
                     "<column name=\"col1\" type=\"int\"/>" +
                     "</table>";
        Document doc = documentBuilder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));
        Node tableNode = doc.getFirstChild();

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("test comments", tableMeta.getComments());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertNull(tableMeta.getRemoteCatalog());

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertEquals(1, columns.size());
        assertEquals("col1", columns.get(0).getName());
    }
}