package org.apache.commons.compress.archivers.tar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class TarUtilsTest {

    @Test
    public void testParseOctal() {
        byte[] buffer = new byte[] {' ', '0', '7', '7', '7', ' ', '0'};
        assertEquals(511, TarUtils.parseOctal(buffer, 1, 3));
        assertEquals(0, TarUtils.parseOctal(buffer, 0, 1));
        assertEquals(0, TarUtils.parseOctal(buffer, 5, 1));

        byte[] invalidBuffer = new byte[] {' ', '8', '9', ' '};
        assertThrows(IllegalArgumentException.class, () -> TarUtils.parseOctal(invalidBuffer, 1, 2));
    }

    @Test
    public void testParseName() {
        byte[] buffer = new byte[] {'t', 'e', 's', 't', 0, 'e', 'x', 't'};
        assertEquals("test", TarUtils.parseName(buffer, 0, 8));
        assertEquals("est", TarUtils.parseName(buffer, 1, 3));
    }

    @Test
    public void testFormatNameBytes() {
        byte[] buffer = new byte[10];
        int offset = TarUtils.formatNameBytes("test", buffer, 0, 10);
        assertEquals(10, offset);
        assertEquals('t', buffer[0]);
        assertEquals('e', buffer[1]);
        assertEquals('s', buffer[2]);
        assertEquals('t', buffer[3]);
        assertEquals(0, buffer[4]); // NUL padding
    }

    @Test
    public void testFormatUnsignedOctalString() {
        byte[] buffer = new byte[10];
        TarUtils.formatUnsignedOctalString(511, buffer, 0, 6);
        assertEquals('0', buffer[0]);
        assertEquals('0', buffer[1]);
        assertEquals('7', buffer[2]);
        assertEquals('7', buffer[3]);
        assertEquals('7', buffer[4]);
        assertEquals('0', buffer[5]);

        assertThrows(IllegalArgumentException.class, () -> TarUtils.formatUnsignedOctalString(123456789, buffer, 0, 6));
    }

    @Test
    public void testFormatOctalBytes() {
        byte[] buffer = new byte[10];
        int offset = TarUtils.formatOctalBytes(511, buffer, 0, 8);
        assertEquals(8, offset);
        assertEquals('0', buffer[0]);
        assertEquals('0', buffer[1]);
        assertEquals('7', buffer[2]);
        assertEquals('7', buffer[3]);
        assertEquals('7', buffer[4]);
        assertEquals(' ', buffer[6]);
        assertEquals(0, buffer[7]);
    }

    @Test
    public void testFormatLongOctalBytes() {
        byte[] buffer = new byte[10];
        int offset = TarUtils.formatLongOctalBytes(511, buffer, 0, 8);
        assertEquals(8, offset);
        assertEquals('0', buffer[0]);
        assertEquals('0', buffer[1]);
        assertEquals('7', buffer[2]);
        assertEquals('7', buffer[3]);
        assertEquals('7', buffer[4]);
        assertEquals(' ', buffer[7]);
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        byte[] buffer = new byte[10];
        int offset = TarUtils.formatCheckSumOctalBytes(511, buffer, 0, 8);
        assertEquals(8, offset);
        assertEquals('0', buffer[0]);
        assertEquals('0', buffer[1]);
        assertEquals('7', buffer[2]);
        assertEquals('7', buffer[3]);
        assertEquals('7', buffer[4]);
        assertEquals(0, buffer[6]);
        assertEquals(' ', buffer[7]);
    }

    @Test
    public void testComputeCheckSum() {
        byte[] buffer = new byte[] {1, 2, 3, 4, 5};
        assertEquals(15, TarUtils.computeCheckSum(buffer));

        byte[] bufferWithNegative = new byte[] {-1, -2, -3, -4, -5};
        assertEquals(251, TarUtils.computeCheckSum(bufferWithNegative));
    }
}