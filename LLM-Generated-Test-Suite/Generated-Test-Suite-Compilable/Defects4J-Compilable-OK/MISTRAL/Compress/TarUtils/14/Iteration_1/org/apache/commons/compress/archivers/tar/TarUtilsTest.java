package org.apache.commons.compress.archivers.tar;

import static org.junit.Assert.*;
import org.junit.Test;

public class TarUtilsTest {

    @Test
    public void testParseOctal() {
        byte[] buffer = new byte[] { ' ', ' ', '1', '2', '3', ' ', 0 };
        assertEquals(83, TarUtils.parseOctal(buffer, 0, 7));

        buffer = new byte[] { ' ', ' ', '1', '2', '3', 0, 0 };
        assertEquals(83, TarUtils.parseOctal(buffer, 0, 7));

        buffer = new byte[] { 0, 0, 0, 0, 0, 0, 0 };
        assertEquals(0, TarUtils.parseOctal(buffer, 0, 7));

        buffer = new byte[] { ' ', ' ', '1', '2', '3', '4', '5' };
        try {
            TarUtils.parseOctal(buffer, 0, 7);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

        buffer = new byte[] { ' ', ' ', '1', '2', '3', '8', ' ' };
        try {
            TarUtils.parseOctal(buffer, 0, 7);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testParseOctalOrBinary() {
        byte[] buffer = new byte[] { ' ', ' ', '1', '2', '3', ' ', 0 };
        assertEquals(83, TarUtils.parseOctalOrBinary(buffer, 0, 7));

        buffer = new byte[] { (byte) 0x80, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06 };
        assertEquals(0x010203040506L, TarUtils.parseOctalOrBinary(buffer, 0, 7));

        buffer = new byte[] { (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
        try {
            TarUtils.parseOctalOrBinary(buffer, 0, 7);
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

        buffer = new byte[] { 't', 'e', 's', 't', 'n', 'a', 'm', 'e' };
        assertEquals("testname", TarUtils.parseName(buffer, 0, 8));

        buffer = new byte[] { 0, 0, 0, 0, 0, 0, 0 };
        assertEquals("", TarUtils.parseName(buffer, 0, 7));
    }

    @Test
    public void testFormatNameBytes() {
        byte[] buffer = new byte[10];
        assertEquals(10, TarUtils.formatNameBytes("test", buffer, 0, 10));
        assertEquals("test", new String(buffer, 0, 4));
        assertEquals(0, buffer[4]);

        buffer = new byte[5];
        assertEquals(5, TarUtils.formatNameBytes("testname", buffer, 0, 5));
        assertEquals("testn", new String(buffer, 0, 5));
    }

    @Test
    public void testFormatUnsignedOctalString() {
        byte[] buffer = new byte[10];
        TarUtils.formatUnsignedOctalString(123, buffer, 0, 10);
        assertEquals("00000000173", new String(buffer, 0, 10));

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
        assertEquals("0000000173 ", new String(buffer, 0, 10));

        try {
            TarUtils.formatOctalBytes(Long.MAX_VALUE, buffer, 0, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testFormatLongOctalBytes() {
        byte[] buffer = new byte[10];
        assertEquals(10, TarUtils.formatLongOctalBytes(123, buffer, 0, 10));
        assertEquals("0000000173 ", new String(buffer, 0, 10));

        try {
            TarUtils.formatLongOctalBytes(Long.MAX_VALUE, buffer, 0, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testFormatLongOctalOrBinaryBytes() {
        byte[] buffer = new byte[10];
        assertEquals(10, TarUtils.formatLongOctalOrBinaryBytes(123, buffer, 0, 10));
        assertEquals("0000000173 ", new String(buffer, 0, 10));

        buffer = new byte[10];
        assertEquals(10, TarUtils.formatLongOctalOrBinaryBytes(Long.MAX_VALUE, buffer, 0, 10));
        assertEquals("\u0080\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000", new String(buffer, 0, 10));
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        byte[] buffer = new byte[10];
        assertEquals(10, TarUtils.formatCheckSumOctalBytes(123, buffer, 0, 10));
        assertEquals("0000000173\u0000 ", new String(buffer, 0, 10));

        try {
            TarUtils.formatCheckSumOctalBytes(Long.MAX_VALUE, buffer, 0, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testComputeCheckSum() {
        byte[] buffer = new byte[] { 't', 'e', 's', 't' };
        assertEquals(448, TarUtils.computeCheckSum(buffer));

        buffer = new byte[] { 0, 0, 0, 0 };
        assertEquals(0, TarUtils.computeCheckSum(buffer));
    }
}