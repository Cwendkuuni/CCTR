package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class TarUtilsTest {

    private byte[] buffer;

    @Before
    public void setUp() {
        buffer = new byte[100];
    }

    @Test
    public void testParseOctal() {
        // Test case with valid octal string
        buffer = " 123 ".getBytes();
        assertEquals(83, TarUtils.parseOctal(buffer, 0, 5));

        // Test case with all NULs
        buffer = new byte[5];
        assertEquals(0, TarUtils.parseOctal(buffer, 0, 5));

        // Test case with leading NUL
        buffer = new byte[] {0, '1', '2', '3', ' '};
        assertEquals(0, TarUtils.parseOctal(buffer, 0, 5));

        // Test case with invalid byte
        buffer = " 128 ".getBytes();
        try {
            TarUtils.parseOctal(buffer, 0, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test case with missing trailing space/NUL
        buffer = "1234".getBytes();
        try {
            TarUtils.parseOctal(buffer, 0, 4);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testParseOctalOrBinary() {
        // Test case with valid octal string
        buffer = " 123 ".getBytes();
        assertEquals(83, TarUtils.parseOctalOrBinary(buffer, 0, 5));

        // Test case with binary number
        buffer = new byte[] {(byte) 0x81, 0x02, 0x03, 0x04, 0x05};
        assertEquals(0x0504030281L, TarUtils.parseOctalOrBinary(buffer, 0, 5));

        // Test case with binary number exceeding long size
        buffer = new byte[] {(byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        try {
            TarUtils.parseOctalOrBinary(buffer, 0, 9);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testParseBoolean() {
        // Test case with true value
        buffer = new byte[] {1};
        assertTrue(TarUtils.parseBoolean(buffer, 0));

        // Test case with false value
        buffer = new byte[] {0};
        assertFalse(TarUtils.parseBoolean(buffer, 0));

        // Test case with invalid byte
        buffer = new byte[] {2};
        try {
            TarUtils.parseBoolean(buffer, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testParseName() {
        // Test case with valid name
        buffer = "testName".getBytes();
        assertEquals("testName", TarUtils.parseName(buffer, 0, 8));

        // Test case with trailing NUL
        buffer = "testName\0".getBytes();
        assertEquals("testName", TarUtils.parseName(buffer, 0, 9));

        // Test case with buffer length reached
        buffer = "testName".getBytes();
        assertEquals("testName", TarUtils.parseName(buffer, 0, 8));
    }

    @Test
    public void testFormatNameBytes() {
        // Test case with name shorter than buffer
        buffer = new byte[10];
        assertEquals(10, TarUtils.formatNameBytes("test", buffer, 0, 10));
        assertEquals("test\0\0\0\0\0\0", new String(buffer, 0, 10));

        // Test case with name longer than buffer
        buffer = new byte[5];
        assertEquals(5, TarUtils.formatNameBytes("testName", buffer, 0, 5));
        assertEquals("testN", new String(buffer, 0, 5));
    }

    @Test
    public void testFormatUnsignedOctalString() {
        // Test case with valid octal string
        buffer = new byte[5];
        TarUtils.formatUnsignedOctalString(123, buffer, 0, 5);
        assertEquals("00123", new String(buffer, 0, 5));

        // Test case with value not fitting in buffer
        try {
            TarUtils.formatUnsignedOctalString(123456789, buffer, 0, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testFormatOctalBytes() {
        // Test case with valid octal string
        buffer = new byte[7];
        assertEquals(7, TarUtils.formatOctalBytes(123, buffer, 0, 7));
        assertEquals("000123 ", new String(buffer, 0, 7));

        // Test case with value not fitting in buffer
        try {
            TarUtils.formatOctalBytes(123456789, buffer, 0, 7);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testFormatLongOctalBytes() {
        // Test case with valid octal string
        buffer = new byte[7];
        assertEquals(7, TarUtils.formatLongOctalBytes(123, buffer, 0, 7));
        assertEquals("000123 ", new String(buffer, 0, 7));

        // Test case with value not fitting in buffer
        try {
            TarUtils.formatLongOctalBytes(123456789, buffer, 0, 7);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testFormatLongOctalOrBinaryBytes() {
        // Test case with valid octal string
        buffer = new byte[7];
        assertEquals(7, TarUtils.formatLongOctalOrBinaryBytes(123, buffer, 0, 7));
        assertEquals("000123 ", new String(buffer, 0, 7));

        // Test case with binary number
        buffer = new byte[5];
        assertEquals(5, TarUtils.formatLongOctalOrBinaryBytes(0x0504030281L, buffer, 0, 5));
        assertEquals("\u0081\u0002\u0003\u0004\u0005", new String(buffer, 0, 5));

        // Test case with value not fitting in buffer
        try {
            TarUtils.formatLongOctalOrBinaryBytes(123456789, buffer, 0, 7);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        // Test case with valid octal string
        buffer = new byte[8];
        assertEquals(8, TarUtils.formatCheckSumOctalBytes(123, buffer, 0, 8));
        assertEquals("000123\0 ", new String(buffer, 0, 8));

        // Test case with value not fitting in buffer
        try {
            TarUtils.formatCheckSumOctalBytes(123456789, buffer, 0, 8);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testComputeCheckSum() {
        // Test case with valid buffer
        buffer = "test".getBytes();
        assertEquals(448, TarUtils.computeCheckSum(buffer));

        // Test case with empty buffer
        buffer = new byte[0];
        assertEquals(0, TarUtils.computeCheckSum(buffer));
    }
}