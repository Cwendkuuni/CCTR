package org.apache.commons.csv;

import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;

public class CSVRecordTest {

    private CSVRecord csvRecord;
    private CSVRecord csvRecordWithMapping;
    private Map<String, Integer> mapping;

    @Before
    public void setUp() {
        String[] values = {"value1", "value2", "value3"};
        mapping = new HashMap<>();
        mapping.put("column1", 0);
        mapping.put("column2", 1);
        mapping.put("column3", 2);
        csvRecord = new CSVRecord(values, null, "This is a comment", 1);
        csvRecordWithMapping = new CSVRecord(values, mapping, null, 2);
    }

    @Test
    public void testGetByIndex() {
        assertEquals("value1", csvRecord.get(0));
        assertEquals("value2", csvRecord.get(1));
        assertEquals("value3", csvRecord.get(2));
    }

    @Test
    public void testGetByName() {
        assertEquals("value1", csvRecordWithMapping.get("column1"));
        assertEquals("value2", csvRecordWithMapping.get("column2"));
        assertEquals("value3", csvRecordWithMapping.get("column3"));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetByNameWithoutMapping() {
        csvRecord.get("column1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByNameWithInvalidName() {
        csvRecordWithMapping.get("invalidColumn");
    }

    @Test
    public void testGetComment() {
        assertEquals("This is a comment", csvRecord.getComment());
        assertNull(csvRecordWithMapping.getComment());
    }

    @Test
    public void testGetRecordNumber() {
        assertEquals(1, csvRecord.getRecordNumber());
        assertEquals(2, csvRecordWithMapping.getRecordNumber());
    }

    @Test
    public void testIsConsistent() {
        assertTrue(csvRecord.isConsistent());
        assertTrue(csvRecordWithMapping.isConsistent());
    }

    @Test
    public void testIsMapped() {
        assertFalse(csvRecord.isMapped("column1"));
        assertTrue(csvRecordWithMapping.isMapped("column1"));
        assertFalse(csvRecordWithMapping.isMapped("invalidColumn"));
    }

    @Test
    public void testIsSet() {
        assertFalse(csvRecord.isSet("column1"));
        assertTrue(csvRecordWithMapping.isSet("column1"));
        assertFalse(csvRecordWithMapping.isSet("invalidColumn"));
    }

    @Test
    public void testIterator() {
        Iterator<String> iterator = csvRecord.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("value1", iterator.next());
        assertEquals("value2", iterator.next());
        assertEquals("value3", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testPutIn() {
        Map<String, String> map = new HashMap<>();
        csvRecordWithMapping.putIn(map);
        assertEquals("value1", map.get("column1"));
        assertEquals("value2", map.get("column2"));
        assertEquals("value3", map.get("column3"));
    }

    @Test
    public void testSize() {
        assertEquals(3, csvRecord.size());
        assertEquals(3, csvRecordWithMapping.size());
    }

    @Test
    public void testToMap() {
        Map<String, String> map = csvRecordWithMapping.toMap();
        assertEquals("value1", map.get("column1"));
        assertEquals("value2", map.get("column2"));
        assertEquals("value3", map.get("column3"));
    }

    @Test
    public void testToString() {
        assertEquals("[value1, value2, value3]", csvRecord.toString());
    }

    @Test
    public void testValues() {
        assertArrayEquals(new String[]{"value1", "value2", "value3"}, csvRecord.values());
    }
}