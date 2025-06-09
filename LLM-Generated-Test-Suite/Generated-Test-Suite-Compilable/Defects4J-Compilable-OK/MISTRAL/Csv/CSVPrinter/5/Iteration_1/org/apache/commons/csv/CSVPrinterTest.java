package org.apache.commons.csv;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CSVPrinterTest {

    private CSVPrinter printer;
    private CSVFormat format;
    private StringWriter writer;

    @Mock
    private ResultSet resultSet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        writer = new StringWriter();
        format = CSVFormat.DEFAULT;
        printer = new CSVPrinter(writer, format);
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
        new CSVPrinter(writer, null);
    }

    @Test
    public void testClose() throws IOException {
        printer.close();
        verify(writer).close();
    }

    @Test
    public void testFlush() throws IOException {
        printer.flush();
        verify(writer).flush();
    }

    @Test
    public void testPrint() throws IOException {
        printer.print("test");
        printer.println();
        assertEquals("test\r\n", writer.toString());
    }

    @Test
    public void testPrintNull() throws IOException {
        printer.print(null);
        printer.println();
        assertEquals("\r\n", writer.toString());
    }

    @Test
    public void testPrintComment() throws IOException {
        printer.printComment("This is a comment");
        assertEquals("# This is a comment\r\n", writer.toString());
    }

    @Test
    public void testPrintln() throws IOException {
        printer.println();
        assertEquals("\r\n", writer.toString());
    }

    @Test
    public void testPrintRecordIterable() throws IOException {
        List<String> values = Arrays.asList("value1", "value2", "value3");
        printer.printRecord(values);
        assertEquals("value1,value2,value3\r\n", writer.toString());
    }

    @Test
    public void testPrintRecordArray() throws IOException {
        String[] values = {"value1", "value2", "value3"};
        printer.printRecord(values);
        assertEquals("value1,value2,value3\r\n", writer.toString());
    }

    @Test
    public void testPrintRecordsIterable() throws IOException {
        List<List<String>> records = Arrays.asList(
                Arrays.asList("value1", "value2"),
                Arrays.asList("value3", "value4")
        );
        printer.printRecords(records);
        assertEquals("value1,value2\r\nvalue3,value4\r\n", writer.toString());
    }

    @Test
    public void testPrintRecordsArray() throws IOException {
        String[][] records = {
                {"value1", "value2"},
                {"value3", "value4"}
        };
        printer.printRecords(records);
        assertEquals("value1,value2\r\nvalue3,value4\r\n", writer.toString());
    }

    @Test
    public void testPrintRecordsResultSet() throws SQLException, IOException {
        when(resultSet.getMetaData()).thenReturn(mock(java.sql.ResultSetMetaData.class));
        when(resultSet.getMetaData().getColumnCount()).thenReturn(2);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString(1)).thenReturn("value1", "value3");
        when(resultSet.getString(2)).thenReturn("value2", "value4");

        printer.printRecords(resultSet);
        assertEquals("value1,value2\r\nvalue3,value4\r\n", writer.toString());
    }

    @Test
    public void testGetOut() {
        assertEquals(writer, printer.getOut());
    }
}