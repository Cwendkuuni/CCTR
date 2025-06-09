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
    public void testParseOctalWithLeadingSpaces() {
        byte[] buffer = {' ', ' ', '1', '2', '3', ' ', 0};
        assertEquals(83L, TarUtils.parseOctal(buffer, 0, buffer.length));
    }

    @Test
    public void testParseOctalWithTrailingNUL() {
        byte[] buffer = {' ', '1', '2', '3', 0, 0};
        assertEquals(83L, TarUtils.parseOctal(buffer, 0, buffer.length));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseOctalInvalidCharacter() {
        byte[] buffer = {' ', '1', '2', '8', ' ', 0};
        TarUtils.parseOctal(buffer, 0, buffer.length);
    }

    @Test
    public void testParseOctalAllNULs() {
        byte[] buffer = {0, 0, 0, 0, 0, 0};
        assertEquals(0L, TarUtils.parseOctal(buffer, 0, buffer.length));
    }

    @Test
    public void testParseName() {
        byte[] buffer = {'t', 'e', 's', 't', 0, 0};
        assertEquals("test", TarUtils.parseName(buffer, 0, buffer.length));
    }

    @Test
    public void testParseNameWithNoNUL() {
        byte[] buffer = {'t', 'e', 's', 't'};
        assertEquals("test", TarUtils.parseName(buffer, 0, buffer.length));
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
        assertEquals(0, buffer[4]);
    }

    @Test
    public void testFormatUnsignedOctalString() {
        byte[] buffer = new byte[10];
        TarUtils.formatUnsignedOctalString(83L, buffer, 0, buffer.length);
        assertEquals('0', buffer[0]);
        assertEquals('0', buffer[1]);
        assertEquals('0', buffer[2]);
        assertEquals('0', buffer[3]);
        assertEquals('0', buffer[4]);
        assertEquals('0', buffer[5]);
        assertEquals('1', buffer[6]);
        assertEquals('2', buffer[7]);
        assertEquals('3', buffer[8]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatUnsignedOctalStringOverflow() {
        byte[] buffer = new byte[3];
        TarUtils.formatUnsignedOctalString(123456789L, buffer, 0, buffer.length);
    }

    @Test
    public void testFormatOctalBytes() {
        byte[] buffer = new byte[10];
        int offset = TarUtils.formatOctalBytes(83L, buffer, 0, buffer.length);
        assertEquals(10, offset);
        assertEquals('0', buffer[0]);
        assertEquals('0', buffer[1]);
        assertEquals('0', buffer[2]);
        assertEquals('0', buffer[3]);
        assertEquals('0', buffer[4]);
        assertEquals('0', buffer[5]);
        assertEquals('1', buffer[6]);
        assertEquals('2', buffer[7]);
        assertEquals('3', buffer[8]);
        assertEquals(' ', buffer[9]);
    }

    @Test
    public void testFormatLongOctalBytes() {
        byte[] buffer = new byte[10];
        int offset = TarUtils.formatLongOctalBytes(83L, buffer, 0, buffer.length);
        assertEquals(10, offset);
        assertEquals('0', buffer[0]);
        assertEquals('0', buffer[1]);
        assertEquals('0', buffer[2]);
        assertEquals('0', buffer[3]);
        assertEquals('0', buffer[4]);
        assertEquals('0', buffer[5]);
        assertEquals('1', buffer[6]);
        assertEquals('2', buffer[7]);
        assertEquals('3', buffer[8]);
        assertEquals(' ', buffer[9]);
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        byte[] buffer = new byte[10];
        int offset = TarUtils.formatCheckSumOctalBytes(83L, buffer, 0, buffer.length);
        assertEquals(10, offset);
        assertEquals('0', buffer[0]);
        assertEquals('0', buffer[1]);
        assertEquals('0', buffer[2]);
        assertEquals('0', buffer[3]);
        assertEquals('0', buffer[4]);
        assertEquals('0', buffer[5]);
        assertEquals('1', buffer[6]);
        assertEquals('2', buffer[7]);
        assertEquals('3', buffer[8]);
        assertEquals(0, buffer[9]);
    }

    @Test
    public void testComputeCheckSum() {
        byte[] buffer = {1, 2, 3, 4, 5};
        assertEquals(15L, TarUtils.computeCheckSum(buffer));
    }
}