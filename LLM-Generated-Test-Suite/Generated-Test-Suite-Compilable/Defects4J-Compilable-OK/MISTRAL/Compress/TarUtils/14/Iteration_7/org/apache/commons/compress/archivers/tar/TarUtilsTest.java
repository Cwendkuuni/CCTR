package org.apache.commons.compress.archivers.tar;

import static org.junit.Assert.*;
import org.junit.Test;

public class TarUtilsTest {

    @Test
    public void testParseOctal() {
        byte[] buffer = new byte[] { ' ', ' ', '1', '2', '3', ' ', 0 };
        assertEquals(83L, TarUtils.parseOctal(buffer, 0, 7));

        buffer = new byte[] { ' ', ' ', '1', '2', '3', 0, 0 };
        assertEquals(83L, TarUtils.parseOctal(buffer, 0, 7));

        buffer = new byte[] { 0, 0, 0, 0, 0, 0, 0 };
        assertEquals(0L, TarUtils.parseOctal(buffer, 0, 7));

        buffer = new byte[] { ' ', ' ', '1', '2', '3', '4', '5' };
        try {
            TarUtils.parseOctal(buffer, 0, 7);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        buffer = new byte[] { ' ', ' ', '1', '2', '3', '8', ' ' };
        try {
            TarUtils.parseOctal(buffer, 0, 7);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        buffer = new byte[] { ' ', ' ', '1', '2', '3', ' ', ' ' };
        try {
            TarUtils.parseOctal(buffer, 0, 1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testParseOctalOrBinary() {
        byte[] buffer = new byte[] { ' ', ' ', '1', '2', '3', ' ', 0 };
        assertEquals(83L, TarUtils.parseOctalOrBinary(buffer, 0, 7));

        buffer = new byte[] { (byte) 0x80, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06 };
        assertEquals(0x010203040506L, TarUtils.parseOctalOrBinary(buffer, 0, 7));

        buffer = new byte[] { (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
        try {
            TarUtils.parseOctalOrBinary(buffer, 0, 9);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
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
            // Expected
        }
    }

    @Test
    public void testParseName() {
        byte[] buffer = new byte[] { 't', 'e', 's', 't', 0, 0, 0 };
        assertEquals("test", TarUtils.parseName(buffer, 0, 7));

        buffer = new byte[] { 't', 'e', 's', 't', ' ', ' ', ' ' };
        assertEquals("test   ", TarUtils.parseName(buffer, 0, 7));

        buffer = new byte[] { 0, 0, 0, 0, 0, 0, 0 };
        assertEquals("", TarUtils.parseName(buffer, 0, 7));
    }

    @Test
    public void testFormatNameBytes() {
        byte[] buffer = new byte[7];
        assertEquals(7, TarUtils.formatNameBytes("test", buffer, 0, 7));
        assertArrayEquals(new byte[] { 't', 'e', 's', 't', 0, 0, 0 }, buffer);

        buffer = new byte[7];
        assertEquals(7, TarUtils.formatNameBytes("longtest", buffer, 0, 7));
        assertArrayEquals(new byte[] { 'l', 'o', 'n', 'g', 't', 'e', 's' }, buffer);
    }

    @Test
    public void testFormatUnsignedOctalString() {
        byte[] buffer = new byte[7];
        TarUtils.formatUnsignedOctalString(83L, buffer, 0, 7);
        assertArrayEquals(new byte[] { '0', '0', '0', '0', '1', '2', '3' }, buffer);

        buffer = new byte[7];
        try {
            TarUtils.formatUnsignedOctalString(0x100000000L, buffer, 0, 7);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatOctalBytes() {
        byte[] buffer = new byte[7];
        assertEquals(7, TarUtils.formatOctalBytes(83L, buffer, 0, 7));
        assertArrayEquals(new byte[] { '0', '0', '0', '0', '1', '2', '3', ' ', 0 }, buffer);

        buffer = new byte[7];
        try {
            TarUtils.formatOctalBytes(0x100000000L, buffer, 0, 7);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatLongOctalBytes() {
        byte[] buffer = new byte[7];
        assertEquals(7, TarUtils.formatLongOctalBytes(83L, buffer, 0, 7));
        assertArrayEquals(new byte[] { '0', '0', '0', '0', '1', '2', '3', ' ' }, buffer);

        buffer = new byte[7];
        try {
            TarUtils.formatLongOctalBytes(0x100000000L, buffer, 0, 7);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatLongOctalOrBinaryBytes() {
        byte[] buffer = new byte[7];
        assertEquals(7, TarUtils.formatLongOctalOrBinaryBytes(83L, buffer, 0, 7));
        assertArrayEquals(new byte[] { '0', '0', '0', '0', '1', '2', '3', ' ' }, buffer);

        buffer = new byte[7];
        assertEquals(7, TarUtils.formatLongOctalOrBinaryBytes(0x100000000L, buffer, 0, 7));
        assertArrayEquals(new byte[] { (byte) 0x80, 0, 0, 0, 0, 0, 0 }, buffer);

        buffer = new byte[7];
        try {
            TarUtils.formatLongOctalOrBinaryBytes(0x1000000000L, buffer, 0, 7);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        byte[] buffer = new byte[7];
        assertEquals(7, TarUtils.formatCheckSumOctalBytes(83L, buffer, 0, 7));
        assertArrayEquals(new byte[] { '0', '0', '0', '0', '1', '2', '3', 0, ' ' }, buffer);

        buffer = new byte[7];
        try {
            TarUtils.formatCheckSumOctalBytes(0x100000000L, buffer, 0, 7);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testComputeCheckSum() {
        byte[] buffer = new byte[] { 't', 'e', 's', 't' };
        assertEquals(448L, TarUtils.computeCheckSum(buffer));

        buffer = new byte[] { 0, 0, 0, 0 };
        assertEquals(0L, TarUtils.computeCheckSum(buffer));
    }
}