package org.apache.commons.compress.archivers.tar;

import static org.junit.Assert.*;
import org.junit.Test;
import org.apache.commons.compress.archivers.tar.TarUtils;

public class TarUtilsTest {

    @Test
    public void testParseOctalValid() {
        byte[] buffer = {' ', '1', '2', '3', ' ', 0};
        long result = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(83, result); // 123 in octal is 83 in decimal
    }

    @Test
    public void testParseOctalLeadingSpaces() {
        byte[] buffer = {' ', ' ', '7', ' ', 0};
        long result = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(7, result);
    }

    @Test
    public void testParseOctalAllNULs() {
        byte[] buffer = {0, 0, 0, 0, 0};
        long result = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(0, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseOctalInvalidByte() {
        byte[] buffer = {' ', '8', ' ', 0};
        TarUtils.parseOctal(buffer, 0, buffer.length);
    }

    @Test
    public void testParseName() {
        byte[] buffer = {'t', 'e', 's', 't', 0, 'x', 'y', 'z'};
        String result = TarUtils.parseName(buffer, 0, buffer.length);
        assertEquals("test", result);
    }

    @Test
    public void testParseNameNoNUL() {
        byte[] buffer = {'t', 'e', 's', 't'};
        String result = TarUtils.parseName(buffer, 0, buffer.length);
        assertEquals("test", result);
    }

    @Test
    public void testFormatNameBytes() {
        byte[] buffer = new byte[10];
        int offset = TarUtils.formatNameBytes("test", buffer, 0, buffer.length);
        assertEquals(10, offset);
        assertEquals('t', buffer[0]);
        assertEquals('e', buffer[1]);
        assertEquals('s', buffer[2]);
        assertEquals('t', buffer[3]);
        assertEquals(0, buffer[4]); // NUL padding
    }

    @Test
    public void testFormatUnsignedOctalString() {
        byte[] buffer = new byte[5];
        TarUtils.formatUnsignedOctalString(83, buffer, 0, buffer.length);
        assertEquals('1', buffer[0]);
        assertEquals('2', buffer[1]);
        assertEquals('3', buffer[2]);
        assertEquals('0', buffer[3]); // Leading zero padding
        assertEquals('0', buffer[4]); // Leading zero padding
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatUnsignedOctalStringOverflow() {
        byte[] buffer = new byte[2];
        TarUtils.formatUnsignedOctalString(83, buffer, 0, buffer.length);
    }

    @Test
    public void testFormatOctalBytes() {
        byte[] buffer = new byte[6];
        int offset = TarUtils.formatOctalBytes(83, buffer, 0, buffer.length);
        assertEquals(6, offset);
        assertEquals('1', buffer[0]);
        assertEquals('2', buffer[1]);
        assertEquals('3', buffer[2]);
        assertEquals('0', buffer[3]); // Leading zero padding
        assertEquals(' ', buffer[4]); // Trailing space
        assertEquals(0, buffer[5]);   // Trailing NUL
    }

    @Test
    public void testFormatLongOctalBytes() {
        byte[] buffer = new byte[5];
        int offset = TarUtils.formatLongOctalBytes(83, buffer, 0, buffer.length);
        assertEquals(5, offset);
        assertEquals('1', buffer[0]);
        assertEquals('2', buffer[1]);
        assertEquals('3', buffer[2]);
        assertEquals('0', buffer[3]); // Leading zero padding
        assertEquals(' ', buffer[4]); // Trailing space
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        byte[] buffer = new byte[6];
        int offset = TarUtils.formatCheckSumOctalBytes(83, buffer, 0, buffer.length);
        assertEquals(6, offset);
        assertEquals('1', buffer[0]);
        assertEquals('2', buffer[1]);
        assertEquals('3', buffer[2]);
        assertEquals('0', buffer[3]); // Leading zero padding
        assertEquals(0, buffer[4]);   // Trailing NUL
        assertEquals(' ', buffer[5]); // Trailing space
    }

    @Test
    public void testComputeCheckSum() {
        byte[] buffer = {1, 2, 3, 4, 5};
        long checksum = TarUtils.computeCheckSum(buffer);
        assertEquals(15, checksum);
    }
}