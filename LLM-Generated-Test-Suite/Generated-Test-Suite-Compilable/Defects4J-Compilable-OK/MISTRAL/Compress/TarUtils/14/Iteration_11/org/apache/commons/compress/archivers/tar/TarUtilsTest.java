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
        // Test case 1: Valid octal string
        buffer = " 12345 ".getBytes();
        assertEquals(5349, TarUtils.parseOctal(buffer, 0, 7));

        // Test case 2: Leading spaces
        buffer = "    12345 ".getBytes();
        assertEquals(5349, TarUtils.parseOctal(buffer, 0, 10));

        // Test case 3: All NULs
        buffer = new byte[10];
        assertEquals(0L, TarUtils.parseOctal(buffer, 0, 10));

        // Test case 4: Invalid byte
        buffer = " 1234G ".getBytes();
        try {
            TarUtils.parseOctal(buffer, 0, 7);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test case 5: Missing trailing space/NUL
        buffer = "12345".getBytes();
        try {
            TarUtils.parseOctal(buffer, 0, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testParseOctalOrBinary() {
        // Test case 1: Valid octal string
        buffer = " 12345 ".getBytes();
        assertEquals(5349, TarUtils.parseOctalOrBinary(buffer, 0, 7));

        // Test case 2: Valid binary string
        buffer[0] = (byte) 0x80; // Set the most significant bit
        buffer[1] = 0x01;
        buffer[2] = 0x02;
        buffer[3] = 0x03;
        buffer[4] = 0x04;
        buffer[5] = 0x05;
        buffer[6] = 0x06;
        buffer[7] = 0x07;
        assertEquals(0x01020304050607L, TarUtils.parseOctalOrBinary(buffer, 0, 8));

        // Test case 3: Binary number exceeds long size
        buffer[0] = (byte) 0xFF;
        buffer[1] = (byte) 0xFF;
        buffer[2] = (byte) 0xFF;
        buffer[3] = (byte) 0xFF;
        buffer[4] = (byte) 0xFF;
        buffer[5] = (byte) 0xFF;
        buffer[6] = (byte) 0xFF;
        buffer[7] = (byte) 0xFF;
        try {
            TarUtils.parseOctalOrBinary(buffer, 0, 8);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testParseBoolean() {
        // Test case 1: True
        buffer[0] = 1;
        assertTrue(TarUtils.parseBoolean(buffer, 0));

        // Test case 2: False
        buffer[0] = 0;
        assertFalse(TarUtils.parseBoolean(buffer, 0));

        // Test case 3: Invalid byte
        buffer[0] = 2;
        try {
            TarUtils.parseBoolean(buffer, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testParseName() {
        // Test case 1: Valid name
        buffer = "testName".getBytes();
        assertEquals("testName", TarUtils.parseName(buffer, 0, 8));

        // Test case 2: Name with trailing NUL
        buffer = "testName\0".getBytes();
        assertEquals("testName", TarUtils.parseName(buffer, 0, 9));

        // Test case 3: Empty name
        buffer = new byte[10];
        assertEquals("", TarUtils.parseName(buffer, 0, 10));
    }

    @Test
    public void testFormatNameBytes() {
        // Test case 1: Name fits in buffer
        buffer = new byte[10];
        assertEquals(10, TarUtils.formatNameBytes("testName", buffer, 0, 10));
        assertEquals("testName", new String(buffer, 0, 8));

        // Test case 2: Name longer than buffer
        assertEquals(10, TarUtils.formatNameBytes("testNameLonger", buffer, 0, 10));
        assertEquals("testNameLo", new String(buffer, 0, 10));

        // Test case 3: Buffer longer than name
        assertEquals(10, TarUtils.formatNameBytes("test", buffer, 0, 10));
        assertEquals("test", new String(buffer, 0, 4));
        assertEquals(0, buffer[4]);
    }

    @Test
    public void testFormatUnsignedOctalString() {
        // Test case 1: Valid octal string
        buffer = new byte[10];
        TarUtils.formatUnsignedOctalString(12345, buffer, 0, 10);
        assertEquals("00000030331", new String(buffer, 0, 10));

        // Test case 2: Value does not fit in buffer
        try {
            TarUtils.formatUnsignedOctalString(Long.MAX_VALUE, buffer, 0, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatOctalBytes() {
        // Test case 1: Valid octal string
        buffer = new byte[10];
        assertEquals(10, TarUtils.formatOctalBytes(12345, buffer, 0, 10));
        assertEquals("00000030331 ", new String(buffer, 0, 10));

        // Test case 2: Value does not fit in buffer
        try {
            TarUtils.formatOctalBytes(Long.MAX_VALUE, buffer, 0, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatLongOctalBytes() {
        // Test case 1: Valid octal string
        buffer = new byte[10];
        assertEquals(10, TarUtils.formatLongOctalBytes(12345, buffer, 0, 10));
        assertEquals("00000030331", new String(buffer, 0, 10));

        // Test case 2: Value does not fit in buffer
        try {
            TarUtils.formatLongOctalBytes(Long.MAX_VALUE, buffer, 0, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatLongOctalOrBinaryBytes() {
        // Test case 1: Valid octal string
        buffer = new byte[10];
        assertEquals(10, TarUtils.formatLongOctalOrBinaryBytes(12345, buffer, 0, 10));
        assertEquals("00000030331", new String(buffer, 0, 10));

        // Test case 2: Valid binary string
        assertEquals(10, TarUtils.formatLongOctalOrBinaryBytes(0x01020304050607L, buffer, 0, 10));
        assertEquals("\u0080\u0001\u0002\u0003\u0004\u0005\u0006\u0007  ", new String(buffer, 0, 10));

        // Test case 3: Value does not fit in buffer
        try {
            TarUtils.formatLongOctalOrBinaryBytes(Long.MAX_VALUE, buffer, 0, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        // Test case 1: Valid octal string
        buffer = new byte[10];
        assertEquals(10, TarUtils.formatCheckSumOctalBytes(12345, buffer, 0, 10));
        assertEquals("00000030331\0 ", new String(buffer, 0, 10));

        // Test case 2: Value does not fit in buffer
        try {
            TarUtils.formatCheckSumOctalBytes(Long.MAX_VALUE, buffer, 0, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testComputeCheckSum() {
        // Test case 1: Valid checksum
        buffer = "test".getBytes();
        assertEquals(448, TarUtils.computeCheckSum(buffer));

        // Test case 2: Empty buffer
        buffer = new byte[0];
        assertEquals(0, TarUtils.computeCheckSum(buffer));
    }
}