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
        buffer = " 12345 ".getBytes();
        assertEquals(5349, TarUtils.parseOctal(buffer, 0, 7));

        // Test case with all NULs
        buffer = new byte[7];
        assertEquals(0, TarUtils.parseOctal(buffer, 0, 7));

        // Test case with leading NUL
        buffer = new byte[] {0, '1', '2', '3', '4', '5', ' '};
        assertEquals(0, TarUtils.parseOctal(buffer, 0, 7));

        // Test case with invalid byte
        buffer = " 1234G ".getBytes();
        try {
            TarUtils.parseOctal(buffer, 0, 7);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test case with missing trailing space/NUL
        buffer = "123456".getBytes();
        try {
            TarUtils.parseOctal(buffer, 0, 6);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testParseOctalOrBinary() {
        // Test case with valid octal string
        buffer = " 12345 ".getBytes();
        assertEquals(5349, TarUtils.parseOctalOrBinary(buffer, 0, 7));

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
        buffer = "filename.txt".getBytes();
        assertEquals("filename.txt", TarUtils.parseName(buffer, 0, buffer.length));

        // Test case with trailing NUL
        buffer = "filename.txt\0".getBytes();
        assertEquals("filename.txt", TarUtils.parseName(buffer, 0, buffer.length));

        // Test case with name longer than buffer
        buffer = "longfilename.txt".getBytes();
        assertEquals("longfilename.txt", TarUtils.parseName(buffer, 0, 10));
    }

    @Test
    public void testFormatNameBytes() {
        // Test case with name shorter than buffer
        buffer = new byte[10];
        assertEquals(10, TarUtils.formatNameBytes("test", buffer, 0, 10));
        assertEquals("test\0\0\0\0\0\0", new String(buffer, 0, 10));

        // Test case with name longer than buffer
        buffer = new byte[5];
        assertEquals(5, TarUtils.formatNameBytes("longname", buffer, 0, 5));
        assertEquals("longn", new String(buffer, 0, 5));
    }

    @Test
    public void testFormatUnsignedOctalString() {
        // Test case with valid value
        buffer = new byte[10];
        TarUtils.formatUnsignedOctalString(12345, buffer, 0, 10);
        assertEquals("0000030061", new String(buffer, 0, 10));

        // Test case with value not fitting in buffer
        try {
            TarUtils.formatUnsignedOctalString(Long.MAX_VALUE, buffer, 0, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testFormatOctalBytes() {
        // Test case with valid value
        buffer = new byte[10];
        assertEquals(10, TarUtils.formatOctalBytes(12345, buffer, 0, 10));
        assertEquals("0000030061 ", new String(buffer, 0, 10));

        // Test case with value not fitting in buffer
        try {
            TarUtils.formatOctalBytes(Long.MAX_VALUE, buffer, 0, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testFormatLongOctalBytes() {
        // Test case with valid value
        buffer = new byte[10];
        assertEquals(10, TarUtils.formatLongOctalBytes(12345, buffer, 0, 10));
        assertEquals("0000030061 ", new String(buffer, 0, 10));

        // Test case with value not fitting in buffer
        try {
            TarUtils.formatLongOctalBytes(Long.MAX_VALUE, buffer, 0, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testFormatLongOctalOrBinaryBytes() {
        // Test case with valid octal value
        buffer = new byte[10];
        assertEquals(10, TarUtils.formatLongOctalOrBinaryBytes(12345, buffer, 0, 10));
        assertEquals("0000030061 ", new String(buffer, 0, 10));

        // Test case with valid binary value
        buffer = new byte[5];
        assertEquals(5, TarUtils.formatLongOctalOrBinaryBytes(0x0504030281L, buffer, 0, 5));
        assertEquals("\u0081\u0002\u0003\u0004\u0005", new String(buffer, 0, 5));

        // Test case with value not fitting in buffer
        try {
            TarUtils.formatLongOctalOrBinaryBytes(Long.MAX_VALUE, buffer, 0, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        // Test case with valid value
        buffer = new byte[10];
        assertEquals(10, TarUtils.formatCheckSumOctalBytes(12345, buffer, 0, 10));
        assertEquals("0000030061\0 ", new String(buffer, 0, 10));

        // Test case with value not fitting in buffer
        try {
            TarUtils.formatCheckSumOctalBytes(Long.MAX_VALUE, buffer, 0, 5);
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