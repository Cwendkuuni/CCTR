package org.apache.commons.compress.archivers.tar;

import static org.junit.Assert.*;
import org.junit.Test;

public class TarUtilsTest {

    @Test
    public void testParseOctal() {
        byte[] buffer = new byte[] { ' ', ' ', '1', '2', '3', ' ', ' ', ' ' };
        assertEquals(83, TarUtils.parseOctal(buffer, 0, 8));

        buffer = new byte[] { ' ', ' ', '1', '2', '3', ' ', ' ', 0 };
        assertEquals(83, TarUtils.parseOctal(buffer, 0, 8));

        buffer = new byte[] { ' ', ' ', '1', '2', '3', 0, ' ', ' ' };
        assertEquals(83, TarUtils.parseOctal(buffer, 0, 8));

        buffer = new byte[] { ' ', ' ', '1', '2', '3', 0, 0, ' ' };
        assertEquals(83, TarUtils.parseOctal(buffer, 0, 8));

        buffer = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        assertEquals(0, TarUtils.parseOctal(buffer, 0, 8));

        buffer = new byte[] { ' ', ' ', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        try {
            TarUtils.parseOctal(buffer, 0, 11);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testParseName() {
        byte[] buffer = new byte[] { 't', 'e', 's', 't', ' ', ' ', ' ', ' ' };
        assertEquals("test", TarUtils.parseName(buffer, 0, 8));

        buffer = new byte[] { 't', 'e', 's', 't', 0, 0, 0, 0 };
        assertEquals("test", TarUtils.parseName(buffer, 0, 8));

        buffer = new byte[] { 't', 'e', 's', 't', ' ', ' ', ' ', 0 };
        assertEquals("test", TarUtils.parseName(buffer, 0, 8));

        buffer = new byte[] { 't', 'e', 's', 't', ' ', ' ', 0, ' ' };
        assertEquals("test", TarUtils.parseName(buffer, 0, 8));

        buffer = new byte[] { 't', 'e', 's', 't', ' ', 0, ' ', ' ' };
        assertEquals("test", TarUtils.parseName(buffer, 0, 8));
    }

    @Test
    public void testFormatNameBytes() {
        byte[] buffer = new byte[8];
        assertEquals(8, TarUtils.formatNameBytes("test", buffer, 0, 8));
        assertEquals("test", new String(buffer, 0, 4));
        assertEquals(0, buffer[4]);
        assertEquals(0, buffer[5]);
        assertEquals(0, buffer[6]);
        assertEquals(0, buffer[7]);

        buffer = new byte[4];
        assertEquals(4, TarUtils.formatNameBytes("test", buffer, 0, 4));
        assertEquals("test", new String(buffer, 0, 4));
    }

    @Test
    public void testFormatUnsignedOctalString() {
        byte[] buffer = new byte[8];
        TarUtils.formatUnsignedOctalString(83, buffer, 0, 8);
        assertEquals("000000123", new String(buffer, 0, 8));

        buffer = new byte[8];
        TarUtils.formatUnsignedOctalString(0, buffer, 0, 8);
        assertEquals("000000000", new String(buffer, 0, 8));

        buffer = new byte[8];
        try {
            TarUtils.formatUnsignedOctalString(Long.MAX_VALUE, buffer, 0, 8);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testFormatOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatOctalBytes(83, buffer, 0, 12));
        assertEquals("00000000123 ", new String(buffer, 0, 11));
        assertEquals(0, buffer[11]);

        buffer = new byte[12];
        assertEquals(12, TarUtils.formatOctalBytes(0, buffer, 0, 12));
        assertEquals("00000000000 ", new String(buffer, 0, 11));
        assertEquals(0, buffer[11]);

        buffer = new byte[12];
        try {
            TarUtils.formatOctalBytes(Long.MAX_VALUE, buffer, 0, 12);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testFormatLongOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatLongOctalBytes(83, buffer, 0, 12));
        assertEquals("00000000123 ", new String(buffer, 0, 12));

        buffer = new byte[12];
        assertEquals(12, TarUtils.formatLongOctalBytes(0, buffer, 0, 12));
        assertEquals("00000000000 ", new String(buffer, 0, 12));

        buffer = new byte[12];
        try {
            TarUtils.formatLongOctalBytes(Long.MAX_VALUE, buffer, 0, 12);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatCheckSumOctalBytes(83, buffer, 0, 12));
        assertEquals("00000000123 ", new String(buffer, 0, 10));
        assertEquals(0, buffer[10]);
        assertEquals(' ', buffer[11]);

        buffer = new byte[12];
        assertEquals(12, TarUtils.formatCheckSumOctalBytes(0, buffer, 0, 12));
        assertEquals("00000000000 ", new String(buffer, 0, 10));
        assertEquals(0, buffer[10]);
        assertEquals(' ', buffer[11]);

        buffer = new byte[12];
        try {
            TarUtils.formatCheckSumOctalBytes(Long.MAX_VALUE, buffer, 0, 12);
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

        buffer = new byte[] { 'a', 'b', 'c', 'd' };
        assertEquals(394, TarUtils.computeCheckSum(buffer));
    }
}