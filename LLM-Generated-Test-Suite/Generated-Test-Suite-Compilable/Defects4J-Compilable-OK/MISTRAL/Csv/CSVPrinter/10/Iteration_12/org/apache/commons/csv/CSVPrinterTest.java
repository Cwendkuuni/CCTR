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
    public void setUp() throws IOException {
        out = new StringWriter();
        format = CSVFormat.DEFAULT;
        printer = new CSVPrinter(out, format);
    }

    @After
    public void tearDown() throws IOException {
        printer.close();
    }

    @Test
    public void testPrint() throws IOException {
        printer.print("value1");
        printer.println();
        printer.print("value2");
        printer.close();
        assertEquals("value1\nvalue2\n", out.toString());
    }

    @Test
    public void testPrintWithNull() throws IOException {
        printer.print(null);
        printer.println();
        printer.close();
        assertEquals("\n", out.toString());
    }

    @Test
    public void testPrintRecord() throws IOException {
        printer.printRecord("value1", "value2");
        printer.close();
        assertEquals("value1,value2\n", out.toString());
    }

    @Test
    public void testPrintRecordWithIterable() throws IOException {
        List<String> values = Arrays.asList("value1", "value2");
        printer.printRecord(values);
        printer.close();
        assertEquals("value1,value2\n", out.toString());
    }

    @Test
    public void testPrintRecords() throws IOException {
        List<String> values1 = Arrays.asList("value1", "value2");
        List<String> values2 = Arrays.asList("value3", "value4");
        printer.printRecords(Arrays.asList(values1, values2));
        printer.close();
        assertEquals("value1,value2\nvalue3,value4\n", out.toString());
    }

    @Test
    public void testPrintRecordsWithArray() throws IOException {
        String[] values1 = {"value1", "value2"};
        String[] values2 = {"value3", "value4"};
        printer.printRecords(new Object[]{values1, values2});
        printer.close();
        assertEquals("value1,value2\nvalue3,value4\n", out.toString());
    }

    @Test
    public void testPrintComment() throws IOException {
        printer.printComment("This is a comment");
        printer.close();
        assertEquals("# This is a comment\n", out.toString());
    }

    @Test
    public void testPrintCommentWithNewLine() throws IOException {
        printer.printComment("This is a comment\nwith a new line");
        printer.close();
        assertEquals("# This is a comment\n# with a new line\n", out.toString());
    }

    @Test
    public void testPrintRecordsWithResultSet() throws IOException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getMetaData()).thenReturn(mock(java.sql.ResultSetMetaData.class));
        when(resultSet.getMetaData().getColumnCount()).thenReturn(2);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString(1)).thenReturn("value1", "value3");
        when(resultSet.getString(2)).thenReturn("value2", "value4");

        printer.printRecords(resultSet);
        printer.close();
        assertEquals("value1,value2\nvalue3,value4\n", out.toString());
    }

    @Test
    public void testGetOut() {
        assertEquals(out, printer.getOut());
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
}