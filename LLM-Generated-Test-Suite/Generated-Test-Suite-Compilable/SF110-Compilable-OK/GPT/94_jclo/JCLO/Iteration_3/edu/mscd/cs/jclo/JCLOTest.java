package edu.mscd.cs.jclo;

import edu.mscd.cs.jclo.JCLO;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class JCLOTest {

    private static class TestObject {
        public boolean boolField;
        public byte byteField;
        public char charField;
        public short shortField;
        public int intField;
        public float floatField;
        public double doubleField;
        public long longField;
        public String stringField;
        public int[] intArrayField;
        public String[] stringArrayField;
    }

    private TestObject testObject;
    private JCLO jclo;

    @Before
    public void setUp() {
        testObject = new TestObject();
        jclo = new JCLO(testObject);
    }

    @Test
    public void testGetValue() {
        testObject.intField = 42;
        assertEquals(42, jclo.getValue("intField"));
    }

    @Test
    public void testGetBoolean() {
        testObject.boolField = true;
        assertTrue(jclo.getBoolean("boolField"));
    }

    @Test
    public void testGetByte() {
        testObject.byteField = 1;
        assertEquals(1, jclo.getByte("byteField"));
    }

    @Test
    public void testGetChar() {
        testObject.charField = 'a';
        assertEquals('a', jclo.getChar("charField"));
    }

    @Test
    public void testGetShort() {
        testObject.shortField = 2;
        assertEquals(2, jclo.getShort("shortField"));
    }

    @Test
    public void testGetInt() {
        testObject.intField = 3;
        assertEquals(3, jclo.getInt("intField"));
    }

    @Test
    public void testGetFloat() {
        testObject.floatField = 4.5f;
        assertEquals(4.5f, jclo.getFloat("floatField"), 0.0);
    }

    @Test
    public void testGetDouble() {
        testObject.doubleField = 6.7;
        assertEquals(6.7, jclo.getDouble("doubleField"), 0.0);
    }

    @Test
    public void testGetLong() {
        testObject.longField = 8L;
        assertEquals(8L, jclo.getLong("longField"));
    }

    @Test
    public void testGetString() {
        testObject.stringField = "test";
        assertEquals("test", jclo.getString("stringField"));
    }

    @Test
    public void testGetInts() {
        testObject.intArrayField = new int[]{1, 2, 3};
        assertArrayEquals(new int[]{1, 2, 3}, jclo.getInts("intArrayField"));
    }

    @Test
    public void testGetStrings() {
        testObject.stringArrayField = new String[]{"a", "b", "c"};
        assertArrayEquals(new String[]{"a", "b", "c"}, jclo.getStrings("stringArrayField"));
    }

    @Test
    public void testParse() {
        String[] args = {"--intField=10", "--stringField=testString"};
        jclo.parse(args);
        assertEquals(10, testObject.intField);
        assertEquals("testString", testObject.stringField);
    }

    @Test
    public void testUsage() {
        String usage = jclo.usage();
        assertNotNull(usage);
        assertTrue(usage.contains("intField"));
        assertTrue(usage.contains("stringField"));
    }

    @Test
    public void testToString() {
        testObject.intField = 5;
        testObject.stringField = "hello";
        String result = jclo.toString();
        assertTrue(result.contains("intField = 5"));
        assertTrue(result.contains("stringField = hello"));
    }
}