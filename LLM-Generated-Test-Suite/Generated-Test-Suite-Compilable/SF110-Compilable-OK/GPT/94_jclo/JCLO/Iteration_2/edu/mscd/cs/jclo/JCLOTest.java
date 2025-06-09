package edu.mscd.cs.jclo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.mscd.cs.jclo.JCLO;

public class JCLOTest {

    private static class SampleClass {
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

    private SampleClass sample;
    private JCLO jclo;

    @Before
    public void setUp() {
        sample = new SampleClass();
        jclo = new JCLO(sample);
    }

    @Test
    public void testGetValue() {
        sample.intField = 42;
        assertEquals(42, jclo.getValue("intField"));
    }

    @Test
    public void testGetBoolean() {
        sample.boolField = true;
        assertTrue(jclo.getBoolean("boolField"));
    }

    @Test
    public void testGetByte() {
        sample.byteField = 1;
        assertEquals(1, jclo.getByte("byteField"));
    }

    @Test
    public void testGetChar() {
        sample.charField = 'a';
        assertEquals('a', jclo.getChar("charField"));
    }

    @Test
    public void testGetShort() {
        sample.shortField = 2;
        assertEquals(2, jclo.getShort("shortField"));
    }

    @Test
    public void testGetInt() {
        sample.intField = 3;
        assertEquals(3, jclo.getInt("intField"));
    }

    @Test
    public void testGetFloat() {
        sample.floatField = 4.5f;
        assertEquals(4.5f, jclo.getFloat("floatField"), 0.0);
    }

    @Test
    public void testGetDouble() {
        sample.doubleField = 6.7;
        assertEquals(6.7, jclo.getDouble("doubleField"), 0.0);
    }

    @Test
    public void testGetLong() {
        sample.longField = 8L;
        assertEquals(8L, jclo.getLong("longField"));
    }

    @Test
    public void testGetString() {
        sample.stringField = "test";
        assertEquals("test", jclo.getString("stringField"));
    }

    @Test
    public void testGetInts() {
        sample.intArrayField = new int[]{1, 2, 3};
        assertArrayEquals(new int[]{1, 2, 3}, jclo.getInts("intArrayField"));
    }

    @Test
    public void testGetStrings() {
        sample.stringArrayField = new String[]{"a", "b", "c"};
        assertArrayEquals(new String[]{"a", "b", "c"}, jclo.getStrings("stringArrayField"));
    }

    @Test
    public void testParse() {
        String[] args = {"--intField=10", "--stringField=testString"};
        jclo.parse(args);
        assertEquals(10, sample.intField);
        assertEquals("testString", sample.stringField);
    }

    @Test
    public void testUsage() {
        String usage = jclo.usage();
        assertTrue(usage.contains("-intField"));
        assertTrue(usage.contains("-stringField"));
    }

    @Test
    public void testToString() {
        sample.intField = 5;
        sample.stringField = "hello";
        String result = jclo.toString();
        assertTrue(result.contains("int: intField = 5"));
        assertTrue(result.contains("String: stringField = hello"));
    }
}