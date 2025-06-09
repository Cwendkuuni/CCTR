package org.apache.commons.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CSVPrinterTest {

    private StringWriter out;
    private CSVFormat format;
    private CSVPrinter printer;

    @Before
    public void setUp() {
        out = new StringWriter();
        format = CSVFormat.DEFAULT;
        printer = new CSVPrinter(out, format);
    }

    @After
    public void tearDown() throws IOException {
        printer.close();
    }

    @Test
    public void testConstructor() {
        assertNotNull(printer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullOut() {
        new CSVPrinter(null, format);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullFormat() {
        new CSVPrinter(out, null);
    }

    @Test
    public void testClose() throws IOException {
        printer.close();
        verify(out).close();
    }

    @Test
    public void testFlush() throws IOException {
        printer.flush();
        verify(out).flush();
    }

    @Test
    public void testPrint() throws IOException {
        printer.print("test");
        printer.flush();
        assertEquals("test", out.toString());
    }

    @Test
    public void testPrintNull() throws IOException {
        printer.print(null);
        printer.flush();
        assertEquals("", out.toString());
    }

    @Test
    public void testPrintComment() throws IOException {
        printer.printComment("test comment");
        printer.flush();
        assertEquals("# test comment\n", out.toString());
    }

    @Test
    public void testPrintln() throws IOException {
        printer.println();
        printer.flush();
        assertEquals("\n", out.toString());
    }

    @Test
    public void testPrintRecordIterable() throws IOException {
        List<String> values = Arrays.asList("value1", "value2");
        printer.printRecord(values);
        printer.flush();
        assertEquals("value1,value2\n", out.toString());
    }

    @Test
    public void testPrintRecordArray() throws IOException {
        String[] values = {"value1", "value2"};
        printer.printRecord(values);
        printer.flush();
        assertEquals("value1,value2\n", out.toString());
    }

    @Test
    public void testPrintRecordsIterable() throws IOException {
        List<List<String>> records = Arrays.asList(
                Arrays.asList("value1", "value2"),
                Arrays.asList("value3", "value4")
        );
        printer.printRecords(records);
        printer.flush();
        assertEquals("value1,value2\nvalue3,value4\n", out.toString());
    }

    @Test
    public void testPrintRecordsArray() throws IOException {
        String[][] records = {
                {"value1", "value2"},
                {"value3", "value4"}
        };
        printer.printRecords(records);
        printer.flush();
        assertEquals("value1,value2\nvalue3,value4\n", out.toString());
    }

    @Test
    public void testPrintRecordsResultSet() throws SQLException, IOException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getMetaData()).thenReturn(mock(java.sql.ResultSetMetaData.class));
        when(resultSet.getMetaData().getColumnCount()).thenReturn(2);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString(1)).thenReturn("value1", "value3");
        when(resultSet.getString(2)).thenReturn("value2", "value4");

        printer.printRecords(resultSet);
        printer.flush();
        assertEquals("value1,value2\nvalue3,value4\n", out.toString());
    }

    @Test
    public void testGetOut() {
        assertEquals(out, printer.getOut());
    }
}