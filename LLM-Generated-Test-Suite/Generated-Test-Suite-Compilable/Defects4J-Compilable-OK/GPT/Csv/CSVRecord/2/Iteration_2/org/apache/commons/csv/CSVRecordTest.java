package org.apache.commons.csv;

import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;

public class CSVRecordTest {

    private CSVRecord recordWithMapping;
    private CSVRecord recordWithoutMapping;
    private Map<String, Integer> mapping;

    @Before
    public void setUp() {
        String[] values = {"value1", "value2", "value3"};
        mapping = new HashMap<>();
        mapping.put("column1", 0);
        mapping.put("column2", 1);
        mapping.put("column3", 2);

        recordWithMapping = new CSVRecord(values, mapping, "This is a comment", 1);
        recordWithoutMapping = new CSVRecord(values, null, null, 2);
    }

    @Test
    public void testGetByIndex() {
        assertEquals("value1", recordWithMapping.get(0));
        assertEquals("value2", recordWithMapping.get(1));
        assertEquals("value3", recordWithMapping.get(2));
    }

    @Test
    public void testGetByName() {
        assertEquals("value1", recordWithMapping.get("column1"));
        assertEquals("value2", recordWithMapping.get("column2"));
        assertEquals("value3", recordWithMapping.get("column3"));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetByNameWithoutMapping() {
        recordWithoutMapping.get("column1");
    }

    @Test
    public void testGetByNameWithInvalidName() {
        assertNull(recordWithMapping.get("invalidColumn"));
    }

    @Test
    public void testIsConsistent() {
        assertTrue(recordWithMapping.isConsistent());
        assertTrue(recordWithoutMapping.isConsistent());
    }

    @Test
    public void testIsMapped() {
        assertTrue(recordWithMapping.isMapped("column1"));
        assertFalse(recordWithMapping.isMapped("invalidColumn"));
        assertFalse(recordWithoutMapping.isMapped("column1"));
    }

    @Test
    public void testIsSet() {
        assertTrue(recordWithMapping.isSet("column1"));
        assertFalse(recordWithMapping.isSet("invalidColumn"));
        assertFalse(recordWithoutMapping.isSet("column1"));
    }

    @Test
    public void testIterator() {
        Iterator<String> iterator = recordWithMapping.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("value1", iterator.next());
        assertEquals("value2", iterator.next());
        assertEquals("value3", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetComment() {
        assertEquals("This is a comment", recordWithMapping.getComment());
        assertNull(recordWithoutMapping.getComment());
    }

    @Test
    public void testGetRecordNumber() {
        assertEquals(1, recordWithMapping.getRecordNumber());
        assertEquals(2, recordWithoutMapping.getRecordNumber());
    }

    @Test
    public void testSize() {
        assertEquals(3, recordWithMapping.size());
        assertEquals(3, recordWithoutMapping.size());
    }

    @Test
    public void testToString() {
        assertEquals("[value1, value2, value3]", recordWithMapping.toString());
    }
}