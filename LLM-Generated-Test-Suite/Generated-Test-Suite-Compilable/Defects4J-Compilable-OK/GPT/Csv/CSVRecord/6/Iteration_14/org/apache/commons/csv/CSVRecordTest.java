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
    private Map<String, Integer> mapping;

    @Before
    public void setUp() {
        mapping = new HashMap<>();
        mapping.put("Name", 0);
        mapping.put("Age", 1);
        mapping.put("Country", 2);

        String[] values = {"John Doe", "30", "USA"};
        csvRecord = new CSVRecord(values, mapping, "Sample Comment", 1);
    }

    @Test
    public void testGetByEnum() {
        assertEquals("John Doe", csvRecord.get(TestEnum.NAME));
    }

    @Test
    public void testGetByIndex() {
        assertEquals("John Doe", csvRecord.get(0));
        assertEquals("30", csvRecord.get(1));
        assertEquals("USA", csvRecord.get(2));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetByIndexOutOfBounds() {
        csvRecord.get(3);
    }

    @Test
    public void testGetByName() {
        assertEquals("John Doe", csvRecord.get("Name"));
        assertEquals("30", csvRecord.get("Age"));
        assertEquals("USA", csvRecord.get("Country"));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetByNameNoMapping() {
        CSVRecord recordWithoutMapping = new CSVRecord(new String[]{"Value"}, null, null, 1);
        recordWithoutMapping.get("Name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByNameInvalidName() {
        csvRecord.get("InvalidName");
    }

    @Test
    public void testGetComment() {
        assertEquals("Sample Comment", csvRecord.getComment());
    }

    @Test
    public void testGetRecordNumber() {
        assertEquals(1, csvRecord.getRecordNumber());
    }

    @Test
    public void testIsConsistent() {
        assertTrue(csvRecord.isConsistent());
    }

    @Test
    public void testIsMapped() {
        assertTrue(csvRecord.isMapped("Name"));
        assertFalse(csvRecord.isMapped("InvalidName"));
    }

    @Test
    public void testIsSet() {
        assertTrue(csvRecord.isSet("Name"));
        assertFalse(csvRecord.isSet("InvalidName"));
    }

    @Test
    public void testIterator() {
        Iterator<String> iterator = csvRecord.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("John Doe", iterator.next());
        assertEquals("30", iterator.next());
        assertEquals("USA", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testPutIn() {
        Map<String, String> map = new HashMap<>();
        csvRecord.putIn(map);
        assertEquals("John Doe", map.get("Name"));
        assertEquals("30", map.get("Age"));
        assertEquals("USA", map.get("Country"));
    }

    @Test
    public void testSize() {
        assertEquals(3, csvRecord.size());
    }

    @Test
    public void testToMap() {
        Map<String, String> map = csvRecord.toMap();
        assertEquals("John Doe", map.get("Name"));
        assertEquals("30", map.get("Age"));
        assertEquals("USA", map.get("Country"));
    }

    @Test
    public void testToString() {
        assertEquals("[John Doe, 30, USA]", csvRecord.toString());
    }

    private enum TestEnum {
        NAME, AGE, COUNTRY
    }
}