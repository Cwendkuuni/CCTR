package org.apache.commons.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class CSVParserTest {

    private CSVParser parser;
    private File testFile;
    private String testString;
    private URL testUrl;

    @Before
    public void setUp() throws Exception {
        testFile = new File("test.csv");
        testString = "name,age\nJohn,30\nJane,25";
        testUrl = new URL("http://example.com/test.csv");
    }

    @After
    public void tearDown() throws Exception {
        if (parser != null) {
            parser.close();
        }
    }

    @Test
    public void testParseFile() throws IOException {
        parser = CSVParser.parse(testFile, CSVFormat.DEFAULT);
        assertNotNull(parser);
    }

    @Test
    public void testParseString() throws IOException {
        parser = CSVParser.parse(testString, CSVFormat.DEFAULT);
        assertNotNull(parser);
    }

    @Test
    public void testParseUrl() throws IOException {
        parser = CSVParser.parse(testUrl, StandardCharsets.UTF_8, CSVFormat.DEFAULT);
        assertNotNull(parser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFileWithNullFile() throws IOException {
        CSVParser.parse((File) null, CSVFormat.DEFAULT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseStringWithNullString() throws IOException {
        CSVParser.parse((String) null, CSVFormat.DEFAULT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseUrlWithNullUrl() throws IOException {
        CSVParser.parse((URL) null, StandardCharsets.UTF_8, CSVFormat.DEFAULT);
    }

    @Test
    public void testGetCurrentLineNumber() throws IOException {
        parser = CSVParser.parse(testString, CSVFormat.DEFAULT);
        assertEquals(0, parser.getCurrentLineNumber());
    }

    @Test
    public void testGetHeaderMap() throws IOException {
        parser = CSVParser.parse(testString, CSVFormat.DEFAULT.withHeader());
        Map<String, Integer> headerMap = parser.getHeaderMap();
        assertNotNull(headerMap);
        assertEquals(2, headerMap.size());
        assertTrue(headerMap.containsKey("name"));
        assertTrue(headerMap.containsKey("age"));
    }

    @Test
    public void testGetRecordNumber() throws IOException {
        parser = CSVParser.parse(testString, CSVFormat.DEFAULT);
        assertEquals(0, parser.getRecordNumber());
    }

    @Test
    public void testGetRecords() throws IOException {
        parser = CSVParser.parse(testString, CSVFormat.DEFAULT);
        List<CSVRecord> records = parser.getRecords();
        assertEquals(2, records.size());
    }

    @Test
    public void testIsClosed() throws IOException {
        parser = CSVParser.parse(testString, CSVFormat.DEFAULT);
        assertFalse(parser.isClosed());
        parser.close();
        assertTrue(parser.isClosed());
    }

    @Test
    public void testIterator() throws IOException {
        parser = CSVParser.parse(testString, CSVFormat.DEFAULT);
        int count = 0;
        for (CSVRecord record : parser) {
            count++;
        }
        assertEquals(2, count);
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorNoSuchElementException() throws IOException {
        parser = CSVParser.parse(testString, CSVFormat.DEFAULT);
        parser.close();
        parser.iterator().next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemove() throws IOException {
        parser = CSVParser.parse(testString, CSVFormat.DEFAULT);
        parser.iterator().remove();
    }

    @Test
    public void testNextRecord() throws IOException {
        parser = CSVParser.parse(testString, CSVFormat.DEFAULT);
        CSVRecord record = parser.nextRecord();
        assertNotNull(record);
        assertEquals("John", record.get(0));
        assertEquals("30", record.get(1));
    }

    @Test
    public void testNextRecordEndOfFile() throws IOException {
        parser = CSVParser.parse(testString, CSVFormat.DEFAULT);
        parser.nextRecord();
        parser.nextRecord();
        assertNull(parser.nextRecord());
    }
}