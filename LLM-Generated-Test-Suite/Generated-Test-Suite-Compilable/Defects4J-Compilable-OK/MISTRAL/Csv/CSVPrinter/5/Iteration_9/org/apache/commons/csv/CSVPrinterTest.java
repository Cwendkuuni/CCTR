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

    private StringWriter stringWriter;
    private CSVFormat csvFormat;
    private CSVPrinter csvPrinter;

    @Before
    public void setUp() {
        stringWriter = new StringWriter();
        csvFormat = CSVFormat.DEFAULT;
        csvPrinter = new CSVPrinter(stringWriter, csvFormat);
    }

    @After
    public void tearDown() throws IOException {
        csvPrinter.close();
    }

    @Test
    public void testConstructor() {
        assertNotNull(csvPrinter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullOut() {
        new CSVPrinter(null, csvFormat);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullFormat() {
        new CSVPrinter(stringWriter, null);
    }

    @Test
    public void testClose() throws IOException {
        csvPrinter.close();
        verify(stringWriter).close();
    }

    @Test
    public void testFlush() throws IOException {
        csvPrinter.flush();
        verify(stringWriter).flush();
    }

    @Test
    public void testPrint() throws IOException {
        csvPrinter.print("test");
        csvPrinter.println();
        assertEquals("test\r\n", stringWriter.toString());
    }

    @Test
    public void testPrintNull() throws IOException {
        csvPrinter.print(null);
        csvPrinter.println();
        assertEquals("\r\n", stringWriter.toString());
    }

    @Test
    public void testPrintComment() throws IOException {
        csvPrinter.printComment("This is a comment");
        assertEquals("# This is a comment\r\n", stringWriter.toString());
    }

    @Test
    public void testPrintCommentDisabled() throws IOException {
        csvFormat = csvFormat.withCommentStart(null);
        csvPrinter = new CSVPrinter(stringWriter, csvFormat);
        csvPrinter.printComment("This is a comment");
        assertEquals("", stringWriter.toString());
    }

    @Test
    public void testPrintln() throws IOException {
        csvPrinter.println();
        assertEquals("\r\n", stringWriter.toString());
    }

    @Test
    public void testPrintRecordIterable() throws IOException {
        List<String> values = Arrays.asList("value1", "value2", "value3");
        csvPrinter.printRecord(values);
        assertEquals("value1,value2,value3\r\n", stringWriter.toString());
    }

    @Test
    public void testPrintRecordArray() throws IOException {
        String[] values = {"value1", "value2", "value3"};
        csvPrinter.printRecord(values);
        assertEquals("value1,value2,value3\r\n", stringWriter.toString());
    }

    @Test
    public void testPrintRecordsIterable() throws IOException {
        List<List<String>> records = Arrays.asList(
                Arrays.asList("value1", "value2"),
                Arrays.asList("value3", "value4")
        );
        csvPrinter.printRecords(records);
        assertEquals("value1,value2\r\nvalue3,value4\r\n", stringWriter.toString());
    }

    @Test
    public void testPrintRecordsArray() throws IOException {
        String[][] records = {
                {"value1", "value2"},
                {"value3", "value4"}
        };
        csvPrinter.printRecords(records);
        assertEquals("value1,value2\r\nvalue3,value4\r\n", stringWriter.toString());
    }

    @Test
    public void testPrintRecordsResultSet() throws IOException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getMetaData()).thenReturn(mock(java.sql.ResultSetMetaData.class));
        when(resultSet.getMetaData().getColumnCount()).thenReturn(2);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString(1)).thenReturn("value1", "value3");
        when(resultSet.getString(2)).thenReturn("value2", "value4");

        csvPrinter.printRecords(resultSet);
        assertEquals("value1,value2\r\nvalue3,value4\r\n", stringWriter.toString());
    }

    @Test
    public void testGetOut() {
        assertEquals(stringWriter, csvPrinter.getOut());
    }
}