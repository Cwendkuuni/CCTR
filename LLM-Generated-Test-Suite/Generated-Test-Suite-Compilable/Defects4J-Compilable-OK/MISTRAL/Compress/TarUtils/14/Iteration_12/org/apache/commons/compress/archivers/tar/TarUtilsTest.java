package org.apache.commons.compress.archivers.tar;

import static org.junit.Assert.*;
import org.junit.Test;

public class TarUtilsTest {

    @Test
    public void testParseOctal() {
        byte[] buffer = new byte[] { ' ', ' ', '1', '2', '3', ' ', 0 };
        assertEquals(83, TarUtils.parseOctal(buffer, 0, 7));

        buffer = new byte[] { ' ', ' ', '7', '7', '7', ' ', 0 };
        assertEquals(511, TarUtils.parseOctal(buffer, 0, 7));

        buffer = new byte[] { 0, 0, 0, 0, 0, 0, 0 };
        assertEquals(0, TarUtils.parseOctal(buffer, 0, 7));

        buffer = new byte[] { ' ', ' ', '8', ' ', 0 };
        try {
            TarUtils.parseOctal(buffer, 0, 4);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

        buffer = new byte[] { ' ', ' ', '1', '2', '3' };
        try {
            TarUtils.parseOctal(buffer, 0, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testParseOctalOrBinary() {
        byte[] buffer = new byte[] { ' ', ' ', '1', '2', '3', ' ', 0 };
        assertEquals(83, TarUtils.parseOctalOrBinary(buffer, 0, 7));

        buffer = new byte[] { (byte) 0x80, 0x01, 0x02, 0x03, 0x04 };
        assertEquals(0x01020304, TarUtils.parseOctalOrBinary(buffer, 0, 5));

        buffer = new byte[] { (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
        try {
            TarUtils.parseOctalOrBinary(buffer, 0, 9);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testParseBoolean() {
        byte[] buffer = new byte[] { 1 };
        assertTrue(TarUtils.parseBoolean(buffer, 0));

        buffer = new byte[] { 0 };
        assertFalse(TarUtils.parseBoolean(buffer, 0));

        buffer = new byte[] { 2 };
        try {
            TarUtils.parseBoolean(buffer, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testParseName() {
        byte[] buffer = new byte[] { 't', 'e', 's', 't', 0, 0, 0 };
        assertEquals("test", TarUtils.parseName(buffer, 0, 7));

        buffer = new byte[] { 't', 'e', 's', 't' };
        assertEquals("test", TarUtils.parseName(buffer, 0, 4));

        buffer = new byte[] { 0, 0, 0, 0, 0, 0, 0 };
        assertEquals("", TarUtils.parseName(buffer, 0, 7));
    }

    @Test
    public void testFormatNameBytes() {
        byte[] buffer = new byte[10];
        assertEquals(10, TarUtils.formatNameBytes("test", buffer, 0, 10));
        assertEquals("test", new String(buffer, 0, 4));
        assertEquals(0, buffer[4]);

        buffer = new byte[2];
        assertEquals(2, TarUtils.formatNameBytes("test", buffer, 0, 2));
        assertEquals("te", new String(buffer, 0, 2));
    }

    @Test
    public void testFormatUnsignedOctalString() {
        byte[] buffer = new byte[10];
        TarUtils.formatUnsignedOctalString(123, buffer, 0, 10);
        assertEquals("0000000173", new String(buffer, 0, 10));

        try {
            TarUtils.formatUnsignedOctalString(Long.MAX_VALUE, buffer, 0, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testFormatOctalBytes() {
        byte[] buffer = new byte[10];
        assertEquals(10, TarUtils.formatOctalBytes(123, buffer, 0, 10));
        assertEquals("0000000173 ", new String(buffer, 0, 9));
        assertEquals(0, buffer[9]);
    }

    @Test
    public void testFormatLongOctalBytes() {
        byte[] buffer = new byte[10];
        assertEquals(10, TarUtils.formatLongOctalBytes(123, buffer, 0, 10));
        assertEquals("0000000173", new String(buffer, 0, 9));
        assertEquals(' ', buffer[9]);
    }

    @Test
    public void testFormatLongOctalOrBinaryBytes() {
        byte[] buffer = new byte[10];
        assertEquals(10, TarUtils.formatLongOctalOrBinaryBytes(123, buffer, 0, 10));
        assertEquals("0000000173", new String(buffer, 0, 9));
        assertEquals(' ', buffer[9]);

        buffer = new byte[10];
        assertEquals(10, TarUtils.formatLongOctalOrBinaryBytes(Long.MAX_VALUE, buffer, 0, 10));
        assertEquals((byte) 0x80, buffer[0]);
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        byte[] buffer = new byte[10];
        assertEquals(10, TarUtils.formatCheckSumOctalBytes(123, buffer, 0, 10));
        assertEquals("0000000173", new String(buffer, 0, 8));
        assertEquals(0, buffer[8]);
        assertEquals(' ', buffer[9]);
    }

    @Test
    public void testComputeCheckSum() {
        byte[] buffer = new byte[] { 1, 2, 3, 4, 5 };
        assertEquals(15, TarUtils.computeCheckSum(buffer));

        buffer = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
        assertEquals(765, TarUtils.computeCheckSum(buffer));
    }
}