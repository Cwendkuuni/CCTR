package edu.mscd.cs.jclo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.mscd.cs.jclo.JCLO;

import java.lang.reflect.Field;

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
        public byte[] byteArrayField;
        public char[] charArrayField;
        public short[] shortArrayField;
        public int[] intArrayField;
        public float[] floatArrayField;
        public double[] doubleArrayField;
        public long[] longArrayField;
        public String[] stringArrayField;
    }

    private JCLO jclo;
    private TestObject testObject;

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
        testObject.doubleField = 5.5;
        assertEquals(5.5, jclo.getDouble("doubleField"), 0.0);
    }

    @Test
    public void testGetLong() {
        testObject.longField = 6L;
        assertEquals(6L, jclo.getLong("longField"));
    }

    @Test
    public void testGetString() {
        testObject.stringField = "test";
        assertEquals("test", jclo.getString("stringField"));
    }

    @Test
    public void testGetBytes() {
        testObject.byteArrayField = new byte[]{1, 2, 3};
        assertArrayEquals(new byte[]{1, 2, 3}, jclo.getBytes("byteArrayField"));
    }

    @Test
    public void testGetChars() {
        testObject.charArrayField = new char[]{'a', 'b', 'c'};
        assertArrayEquals(new char[]{'a', 'b', 'c'}, jclo.getChars("charArrayField"));
    }

    @Test
    public void testGetShorts() {
        testObject.shortArrayField = new short[]{1, 2, 3};
        assertArrayEquals(new short[]{1, 2, 3}, jclo.getShorts("shortArrayField"));
    }

    @Test
    public void testGetInts() {
        testObject.intArrayField = new int[]{1, 2, 3};
        assertArrayEquals(new int[]{1, 2, 3}, jclo.getInts("intArrayField"));
    }

    @Test
    public void testGetFloats() {
        testObject.floatArrayField = new float[]{1.1f, 2.2f, 3.3f};
        assertArrayEquals(new float[]{1.1f, 2.2f, 3.3f}, jclo.getFloats("floatArrayField"), 0.0f);
    }

    @Test
    public void testGetDoubles() {
        testObject.doubleArrayField = new double[]{1.1, 2.2, 3.3};
        assertArrayEquals(new double[]{1.1, 2.2, 3.3}, jclo.getDoubles("doubleArrayField"), 0.0);
    }

    @Test
    public void testGetLongs() {
        testObject.longArrayField = new long[]{1L, 2L, 3L};
        assertArrayEquals(new long[]{1L, 2L, 3L}, jclo.getLongs("longArrayField"));
    }

    @Test
    public void testGetStrings() {
        testObject.stringArrayField = new String[]{"one", "two", "three"};
        assertArrayEquals(new String[]{"one", "two", "three"}, jclo.getStrings("stringArrayField"));
    }

    @Test
    public void testParse() {
        String[] args = {"--intField=10", "--stringField=hello", "--boolField"};
        jclo.parse(args);
        assertEquals(10, testObject.intField);
        assertEquals("hello", testObject.stringField);
        assertTrue(testObject.boolField);
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
        testObject.intField = 42;
        testObject.stringField = "test";
        String result = jclo.toString();
        assertTrue(result.contains("intField = 42"));
        assertTrue(result.contains("stringField = test"));
    }
}