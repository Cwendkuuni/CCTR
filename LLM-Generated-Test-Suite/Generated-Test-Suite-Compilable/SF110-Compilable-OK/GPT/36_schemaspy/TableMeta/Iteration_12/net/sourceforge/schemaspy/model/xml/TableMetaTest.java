package net.sourceforge.schemaspy.model.xml;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TableMetaTest {

    private Document document;
    private Element tableElement;

    @Before
    public void setUp() throws Exception {
        // Initialize a DOM Document for testing
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
        LSParser parser = impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
        document = parser.parseURI("data:application/xml,<table name='testTable' comments='Test comments' remoteCatalog='testCatalog' remoteSchema='testSchema'><column name='col1'/><column name='col2'/></table>");
        tableElement = document.getDocumentElement();
    }

    @Test
    public void testConstructor() {
        TableMeta tableMeta = new TableMeta(tableElement);

        assertEquals("testTable", tableMeta.getName());
        assertEquals("Test comments", tableMeta.getComments());
        assertEquals("testCatalog", tableMeta.getRemoteCatalog());
        assertEquals("testSchema", tableMeta.getRemoteSchema());

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertNotNull(columns);
        assertEquals(2, columns.size());
    }

    @Test
    public void testConstructorWithNoComments() {
        tableElement.removeAttribute("comments");
        TableMeta tableMeta = new TableMeta(tableElement);

        assertNull(tableMeta.getComments());
    }

    @Test
    public void testConstructorWithRemarks() {
        tableElement.removeAttribute("comments");
        tableElement.setAttribute("remarks", "Remark comments");
        TableMeta tableMeta = new TableMeta(tableElement);

        assertEquals("Remark comments", tableMeta.getComments());
    }

    @Test
    public void testConstructorWithEmptyComments() {
        tableElement.setAttribute("comments", " ");
        TableMeta tableMeta = new TableMeta(tableElement);

        assertNull(tableMeta.getComments());
    }

    @Test
    public void testConstructorWithNoRemoteCatalog() {
        tableElement.removeAttribute("remoteCatalog");
        TableMeta tableMeta = new TableMeta(tableElement);

        assertNull(tableMeta.getRemoteCatalog());
    }

    @Test
    public void testConstructorWithNoRemoteSchema() {
        tableElement.removeAttribute("remoteSchema");
        TableMeta tableMeta = new TableMeta(tableElement);

        assertNull(tableMeta.getRemoteSchema());
    }

    @Test
    public void testConstructorWithNoColumns() {
        tableElement = document.createElement("table");
        tableElement.setAttribute("name", "testTable");
        TableMeta tableMeta = new TableMeta(tableElement);

        List<TableColumnMeta> columns = tableMeta.getColumns();
        assertNotNull(columns);
        assertTrue(columns.isEmpty());
    }
}