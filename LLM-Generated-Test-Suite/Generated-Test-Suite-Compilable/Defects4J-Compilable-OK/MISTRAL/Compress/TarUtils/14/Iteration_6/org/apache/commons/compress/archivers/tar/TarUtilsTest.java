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
        buffer[0] = ' ';
        buffer[1] = '1';
        buffer[2] = '2';
        buffer[3] = '3';
        buffer[4] = ' ';
        buffer[5] = 0;
        assertEquals(83, TarUtils.parseOctal(buffer, 0, 6));

        // Test case with all NULs
        buffer[0] = 0;
        buffer[1] = 0;
        buffer[2] = 0;
        buffer[3] = 0;
        buffer[4] = 0;
        buffer[5] = 0;
        assertEquals(0, TarUtils.parseOctal(buffer, 0, 6));

        // Test case with leading NUL
        buffer[0] = 0;
        buffer[1] = '1';
        buffer[2] = '2';
        buffer[3] = '3';
        buffer[4] = ' ';
        buffer[5] = 0;
        assertEquals(0, TarUtils.parseOctal(buffer, 0, 6));

        // Test case with invalid byte
        buffer[0] = ' ';
        buffer[1] = '1';
        buffer[2] = '2';
        buffer[3] = '8'; // Invalid octal digit
        buffer[4] = ' ';
        buffer[5] = 0;
        try {
            TarUtils.parseOctal(buffer, 0, 6);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test case with missing trailing space/NUL
        buffer[0] = ' ';
        buffer[1] = '1';
        buffer[2] = '2';
        buffer[3] = '3';
        buffer[4] = '4';
        buffer[5] = '5';
        try {
            TarUtils.parseOctal(buffer, 0, 6);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testParseOctalOrBinary() {
        // Test case with valid octal string
        buffer[0] = ' ';
        buffer[1] = '1';
        buffer[2] = '2';
        buffer[3] = '3';
        buffer[4] = ' ';
        buffer[5] = 0;
        assertEquals(83, TarUtils.parseOctalOrBinary(buffer, 0, 6));

        // Test case with valid binary string
        buffer[0] = (byte) 0x81;
        buffer[1] = (byte) 0x02;
        buffer[2] = (byte) 0x03;
        buffer[3] = (byte) 0x04;
        buffer[4] = (byte) 0x05;
        buffer[5] = (byte) 0x06;
        assertEquals(16909060, TarUtils.parseOctalOrBinary(buffer, 0, 6));

        // Test case with invalid binary string
        buffer[0] = (byte) 0x81;
        buffer[1] = (byte) 0xFF;
        buffer[2] = (byte) 0xFF;
        buffer[3] = (byte) 0xFF;
        buffer[4] = (byte) 0xFF;
        buffer[5] = (byte) 0xFF;
        try {
            TarUtils.parseOctalOrBinary(buffer, 0, 6);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testParseBoolean() {
        // Test case with true value
        buffer[0] = 1;
        assertTrue(TarUtils.parseBoolean(buffer, 0));

        // Test case with false value
        buffer[0] = 0;
        assertFalse(TarUtils.parseBoolean(buffer, 0));

        // Test case with invalid byte
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
        // Test case with valid name
        buffer[0] = 't';
        buffer[1] = 'e';
        buffer[2] = 's';
        buffer[3] = 't';
        buffer[4] = 0;
        assertEquals("test", TarUtils.parseName(buffer, 0, 5));

        // Test case with name longer than buffer
        buffer[0] = 't';
        buffer[1] = 'e';
        buffer[2] = 's';
        buffer[3] = 't';
        buffer[4] = 'i';
        buffer[5] = 'n';
        buffer[6] = 'g';
        assertEquals("testing", TarUtils.parseName(buffer, 0, 7));

        // Test case with name shorter than buffer
        buffer[0] = 't';
        buffer[1] = 'e';
        buffer[2] = 's';
        buffer[3] = 't';
        buffer[4] = 0;
        buffer[5] = 0;
        buffer[6] = 0;
        assertEquals("test", TarUtils.parseName(buffer, 0, 7));
    }

    @Test
    public void testFormatNameBytes() {
        // Test case with name shorter than buffer
        byte[] buf = new byte[10];
        assertEquals(10, TarUtils.formatNameBytes("test", buf, 0, 10));
        assertEquals("test", new String(buf, 0, 4));
        assertEquals(0, buf[4]);
        assertEquals(0, buf[5]);
        assertEquals(0, buf[6]);
        assertEquals(0, buf[7]);
        assertEquals(0, buf[8]);
        assertEquals(0, buf[9]);

        // Test case with name longer than buffer
        assertEquals(10, TarUtils.formatNameBytes("testing", buf, 0, 10));
        assertEquals("testing", new String(buf, 0, 7));
        assertEquals(0, buf[7]);
        assertEquals(0, buf[8]);
        assertEquals(0, buf[9]);
    }

    @Test
    public void testFormatUnsignedOctalString() {
        // Test case with valid value
        byte[] buf = new byte[10];
        TarUtils.formatUnsignedOctalString(123, buf, 0, 10);
        assertEquals("0000000173", new String(buf, 0, 10));

        // Test case with value that does not fit in buffer
        try {
            TarUtils.formatUnsignedOctalString(Long.MAX_VALUE, buf, 0, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatOctalBytes() {
        // Test case with valid value
        byte[] buf = new byte[10];
        assertEquals(10, TarUtils.formatOctalBytes(123, buf, 0, 10));
        assertEquals("0000000173 ", new String(buf, 0, 10));

        // Test case with value that does not fit in buffer
        try {
            TarUtils.formatOctalBytes(Long.MAX_VALUE, buf, 0, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatLongOctalBytes() {
        // Test case with valid value
        byte[] buf = new byte[10];
        assertEquals(10, TarUtils.formatLongOctalBytes(123, buf, 0, 10));
        assertEquals("0000000173 ", new String(buf, 0, 10));

        // Test case with value that does not fit in buffer
        try {
            TarUtils.formatLongOctalBytes(Long.MAX_VALUE, buf, 0, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatLongOctalOrBinaryBytes() {
        // Test case with valid octal value
        byte[] buf = new byte[10];
        assertEquals(10, TarUtils.formatLongOctalOrBinaryBytes(123, buf, 0, 10));
        assertEquals("0000000173 ", new String(buf, 0, 10));

        // Test case with valid binary value
        assertEquals(10, TarUtils.formatLongOctalOrBinaryBytes(Long.MAX_VALUE, buf, 0, 10));
        assertEquals("\u0080\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000", new String(buf, 0, 10));

        // Test case with value that does not fit in buffer
        try {
            TarUtils.formatLongOctalOrBinaryBytes(Long.MAX_VALUE, buf, 0, 9);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        // Test case with valid value
        byte[] buf = new byte[10];
        assertEquals(10, TarUtils.formatCheckSumOctalBytes(123, buf, 0, 10));
        assertEquals("0000000173\u0000 ", new String(buf, 0, 10));

        // Test case with value that does not fit in buffer
        try {
            TarUtils.formatCheckSumOctalBytes(Long.MAX_VALUE, buf, 0, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testComputeCheckSum() {
        // Test case with valid buffer
        byte[] buf = new byte[10];
        buf[0] = 1;
        buf[1] = 2;
        buf[2] = 3;
        buf[3] = 4;
        buf[4] = 5;
        buf[5] = 6;
        buf[6] = 7;
        buf[7] = 8;
        buf[8] = 9;
        buf[9] = 10;
        assertEquals(55, TarUtils.computeCheckSum(buf));

        // Test case with empty buffer
        buf = new byte[0];
        assertEquals(0, TarUtils.computeCheckSum(buf));
    }
}