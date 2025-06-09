package org.apache.commons.compress.archivers.tar;

import static org.junit.Assert.*;
import org.junit.Test;
import org.apache.commons.compress.archivers.tar.TarUtils;

public class TarUtilsTest {

    @Test
    public void testParseOctalValid() {
        byte[] buffer = {' ', '1', '2', '3', ' ', 0};
        assertEquals(83L, TarUtils.parseOctal(buffer, 0, buffer.length));
    }

    @Test
    public void testParseOctalLeadingSpaces() {
        byte[] buffer = {' ', ' ', '1', '2', '3', ' ', 0};
        assertEquals(83L, TarUtils.parseOctal(buffer, 0, buffer.length));
    }

    @Test
    public void testParseOctalAllNUL() {
        byte[] buffer = {0, 0, 0, 0, 0, 0};
        assertEquals(0L, TarUtils.parseOctal(buffer, 0, buffer.length));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseOctalInvalidByte() {
        byte[] buffer = {' ', '1', '2', '8', ' ', 0};
        TarUtils.parseOctal(buffer, 0, buffer.length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseOctalMissingTrailingSpaceOrNUL() {
        byte[] buffer = {' ', '1', '2', '3'};
        TarUtils.parseOctal(buffer, 0, buffer.length);
    }

    @Test
    public void testParseOctalOrBinaryOctal() {
        byte[] buffer = {' ', '1', '2', '3', ' ', 0};
        assertEquals(83L, TarUtils.parseOctalOrBinary(buffer, 0, buffer.length));
    }

    @Test
    public void testParseOctalOrBinaryBinary() {
        byte[] buffer = {(byte) 0x80, 0x01};
        assertEquals(129L, TarUtils.parseOctalOrBinary(buffer, 0, buffer.length));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseOctalOrBinaryExceedsMaxSignedLong() {
        byte[] buffer = {(byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        TarUtils.parseOctalOrBinary(buffer, 0, buffer.length);
    }

    @Test
    public void testParseBooleanTrue() {
        byte[] buffer = {1};
        assertTrue(TarUtils.parseBoolean(buffer, 0));
    }

    @Test
    public void testParseBooleanFalse() {
        byte[] buffer = {0};
        assertFalse(TarUtils.parseBoolean(buffer, 0));
    }

    @Test
    public void testParseName() {
        byte[] buffer = {'t', 'e', 's', 't', 0, 'i', 'g', 'n', 'o', 'r', 'e'};
        assertEquals("test", TarUtils.parseName(buffer, 0, buffer.length));
    }

    @Test
    public void testFormatNameBytes() {
        byte[] buffer = new byte[10];
        int offset = TarUtils.formatNameBytes("test", buffer, 0, buffer.length);
        assertEquals(10, offset);
        assertEquals("test", new String(buffer, 0, 4));
        for (int i = 4; i < buffer.length; i++) {
            assertEquals(0, buffer[i]);
        }
    }

    @Test
    public void testFormatUnsignedOctalString() {
        byte[] buffer = new byte[5];
        TarUtils.formatUnsignedOctalString(83, buffer, 0, buffer.length);
        assertEquals("00123", new String(buffer));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatUnsignedOctalStringTooLarge() {
        byte[] buffer = new byte[5];
        TarUtils.formatUnsignedOctalString(123456, buffer, 0, buffer.length);
    }

    @Test
    public void testFormatOctalBytes() {
        byte[] buffer = new byte[7];
        int offset = TarUtils.formatOctalBytes(83, buffer, 0, buffer.length);
        assertEquals(7, offset);
        assertEquals("0000123 ", new String(buffer, 0, 7));
    }

    @Test
    public void testFormatLongOctalBytes() {
        byte[] buffer = new byte[6];
        int offset = TarUtils.formatLongOctalBytes(83, buffer, 0, buffer.length);
        assertEquals(6, offset);
        assertEquals("000123 ", new String(buffer, 0, 6));
    }

    @Test
    public void testFormatLongOctalOrBinaryBytesOctal() {
        byte[] buffer = new byte[6];
        int offset = TarUtils.formatLongOctalOrBinaryBytes(83, buffer, 0, buffer.length);
        assertEquals(6, offset);
        assertEquals("000123 ", new String(buffer, 0, 6));
    }

    @Test
    public void testFormatLongOctalOrBinaryBytesBinary() {
        byte[] buffer = new byte[9];
        int offset = TarUtils.formatLongOctalOrBinaryBytes(123456789012345L, buffer, 0, buffer.length);
        assertEquals(9, offset);
        assertEquals((byte) 0x80, buffer[0] & 0x80);
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        byte[] buffer = new byte[8];
        int offset = TarUtils.formatCheckSumOctalBytes(83, buffer, 0, buffer.length);
        assertEquals(8, offset);
        assertEquals("00000123\0 ", new String(buffer, 0, 8));
    }

    @Test
    public void testComputeCheckSum() {
        byte[] buffer = {1, 2, 3, 4, 5};
        assertEquals(15L, TarUtils.computeCheckSum(buffer));
    }
}