package net.sourceforge.schemaspy.model.xml;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
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
    public void testConstructor() throws SAXException, IOException {
        String xml = "<table name=\"testTable\" comments=\"testComments\" remoteSchema=\"testSchema\" remoteCatalog=\"testCatalog\">" +
                     "<column name=\"col1\" type=\"type1\"/>" +
                     "<column name=\"col2\" type=\"type2\"/>" +
                     "</table>";
        Document doc = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
        Node tableNode = doc.getFirstChild();

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("testComments", tableMeta.getComments());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertEquals(2, columns.size());
        assertEquals("col1", columns.get(0).getName());
        assertEquals("col2", columns.get(1).getName());
    }

    @Test
    public void testConstructorWithRemarks() throws SAXException, IOException {
        String xml = "<table name=\"testTable\" remarks=\"testRemarks\" remoteSchema=\"testSchema\" remoteCatalog=\"testCatalog\">" +
                     "<column name=\"col1\" type=\"type1\"/>" +
                     "</table>";
        Document doc = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
        Node tableNode = doc.getFirstChild();

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("testRemarks", tableMeta.getComments());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertEquals(1, columns.size());
        assertEquals("col1", columns.get(0).getName());
    }

    @Test
    public void testConstructorWithEmptyComments() throws SAXException, IOException {
        String xml = "<table name=\"testTable\" comments=\"\" remoteSchema=\"testSchema\" remoteCatalog=\"testCatalog\">" +
                     "<column name=\"col1\" type=\"type1\"/>" +
                     "</table>";
        Document doc = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
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
    public void testConstructorWithNoComments() throws SAXException, IOException {
        String xml = "<table name=\"testTable\" remoteSchema=\"testSchema\" remoteCatalog=\"testCatalog\">" +
                     "<column name=\"col1\" type=\"type1\"/>" +
                     "</table>";
        Document doc = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
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
    public void testConstructorWithNoRemoteSchema() throws SAXException, IOException {
        String xml = "<table name=\"testTable\" comments=\"testComments\" remoteCatalog=\"testCatalog\">" +
                     "<column name=\"col1\" type=\"type1\"/>" +
                     "</table>";
        Document doc = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
        Node tableNode = doc.getFirstChild();

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("testComments", tableMeta.getComments());
        assertNull(tableMeta.getRemoteSchema());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertEquals(1, columns.size());
        assertEquals("col1", columns.get(0).getName());
    }

    @Test
    public void testConstructorWithNoRemoteCatalog() throws SAXException, IOException {
        String xml = "<table name=\"testTable\" comments=\"testComments\" remoteSchema=\"testSchema\">" +
                     "<column name=\"col1\" type=\"type1\"/>" +
                     "</table>";
        Document doc = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
        Node tableNode = doc.getFirstChild();

        TableMeta tableMeta = new TableMeta(tableNode);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("testComments", tableMeta.getComments());
        assertEquals("testSchema", tableMeta.getRemoteSchema());
        assertNull(tableMeta.getRemoteCatalog());
        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertEquals(1, columns.size());
        assertEquals("col1", columns.get(0).getName());
    }
}