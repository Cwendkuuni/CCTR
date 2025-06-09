package org.apache.commons.csv;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CSVParserTest {

    private CSVParser parser;
    private static final String CSV_STRING = "name,age\nJohn,30\nJane,25";
    private static final String CSV_FILE_PATH = "test.csv";
    private static final String CSV_URL = "http://example.com/test.csv";

    @Before
    public void setUp() throws IOException {
        // Create a temporary CSV file for testing
        File csvFile = new File(CSV_FILE_PATH);
        if (!csvFile.exists()) {
            csvFile.createNewFile();
        }
    }

    @After
    public void tearDown() {
        // Delete the temporary CSV file after testing
        File csvFile = new File(CSV_FILE_PATH);
        if (csvFile.exists()) {
            csvFile.delete();
        }
    }

    @Test
    public void testParseFile() throws IOException {
        parser = CSVParser.parse(new File(CSV_FILE_PATH), CSVFormat.DEFAULT);
        List<CSVRecord> records = parser.getRecords();
        assertNotNull(records);
        assertEquals(2, records.size());
        assertEquals("John", records.get(0).get("name"));
        assertEquals("30", records.get(0).get("age"));
        assertEquals("Jane", records.get(1).get("name"));
        assertEquals("25", records.get(1).get("age"));
    }

    @Test
    public void testParseString() throws IOException {
        parser = CSVParser.parse(CSV_STRING, CSVFormat.DEFAULT);
        List<CSVRecord> records = parser.getRecords();
        assertNotNull(records);
        assertEquals(2, records.size());
        assertEquals("John", records.get(0).get("name"));
        assertEquals("30", records.get(0).get("age"));
        assertEquals("Jane", records.get(1).get("name"));
        assertEquals("25", records.get(1).get("age"));
    }

    @Test
    public void testParseURL() throws IOException {
        parser = CSVParser.parse(new URL(CSV_URL), StandardCharsets.UTF_8, CSVFormat.DEFAULT);
        List<CSVRecord> records = parser.getRecords();
        assertNotNull(records);
        assertEquals(2, records.size());
        assertEquals("John", records.get(0).get("name"));
        assertEquals("30", records.get(0).get("age"));
        assertEquals("Jane", records.get(1).get("name"));
        assertEquals("25", records.get(1).get("age"));
    }

    @Test
    public void testGetHeaderMap() throws IOException {
        parser = CSVParser.parse(CSV_STRING, CSVFormat.DEFAULT);
        Map<String, Integer> headerMap = parser.getHeaderMap();
        assertNotNull(headerMap);
        assertEquals(2, headerMap.size());
        assertEquals(Integer.valueOf(0), headerMap.get("name"));
        assertEquals(Integer.valueOf(1), headerMap.get("age"));
    }

    @Test
    public void testGetCurrentLineNumber() throws IOException {
        parser = CSVParser.parse(CSV_STRING, CSVFormat.DEFAULT);
        assertEquals(1, parser.getCurrentLineNumber());
        parser.nextRecord();
        assertEquals(2, parser.getCurrentLineNumber());
    }

    @Test
    public void testGetRecordNumber() throws IOException {
        parser = CSVParser.parse(CSV_STRING, CSVFormat.DEFAULT);
        assertEquals(0, parser.getRecordNumber());
        parser.nextRecord();
        assertEquals(1, parser.getRecordNumber());
    }

    @Test
    public void testClose() throws IOException {
        parser = CSVParser.parse(CSV_STRING, CSVFormat.DEFAULT);
        parser.close();
        assertTrue(parser.isClosed());
    }

    @Test
    public void testIterator() throws IOException {
        parser = CSVParser.parse(CSV_STRING, CSVFormat.DEFAULT);
        int count = 0;
        for (CSVRecord record : parser) {
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    public void testNextRecord() throws IOException {
        parser = CSVParser.parse(CSV_STRING, CSVFormat.DEFAULT);
        CSVRecord record = parser.nextRecord();
        assertNotNull(record);
        assertEquals("John", record.get("name"));
        assertEquals("30", record.get("age"));
        record = parser.nextRecord();
        assertNotNull(record);
        assertEquals("Jane", record.get("name"));
        assertEquals("25", record.get("age"));
        assertNull(parser.nextRecord());
    }
}