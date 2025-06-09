package org.apache.commons.compress.archivers.tar;

import static org.junit.Assert.*;
import org.junit.Test;

public class TarUtilsTest {

    @Test
    public void testParseOctal() {
        byte[] buffer = "  12345670".getBytes();
        assertEquals(42798L, TarUtils.parseOctal(buffer, 0, buffer.length));

        buffer = "  12345670 ".getBytes();
        assertEquals(42798L, TarUtils.parseOctal(buffer, 0, buffer.length));

        buffer = "  12345670\0".getBytes();
        assertEquals(42798L, TarUtils.parseOctal(buffer, 0, buffer.length));

        buffer = "  12345678".getBytes();
        try {
            TarUtils.parseOctal(buffer, 0, buffer.length);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testParseName() {
        byte[] buffer = "filename".getBytes();
        assertEquals("filename", TarUtils.parseName(buffer, 0, buffer.length));

        buffer = "filename\0".getBytes();
        assertEquals("filename", TarUtils.parseName(buffer, 0, buffer.length));

        buffer = "filename\0extra".getBytes();
        assertEquals("filename", TarUtils.parseName(buffer, 0, buffer.length));
    }

    @Test
    public void testFormatNameBytes() {
        byte[] buffer = new byte[10];
        assertEquals(10, TarUtils.formatNameBytes("filename", buffer, 0, 10));
        assertEquals("filename", new String(buffer, 0, 8));
        assertEquals(0, buffer[8]);
        assertEquals(0, buffer[9]);

        buffer = new byte[5];
        assertEquals(5, TarUtils.formatNameBytes("filename", buffer, 0, 5));
        assertEquals("file", new String(buffer, 0, 4));
        assertEquals(0, buffer[4]);
    }

    @Test
    public void testFormatUnsignedOctalString() {
        byte[] buffer = new byte[10];
        TarUtils.formatUnsignedOctalString(42798L, buffer, 0, 10);
        assertEquals("000012345670", new String(buffer, 0, 10));

        try {
            TarUtils.formatUnsignedOctalString(Long.MAX_VALUE, buffer, 0, 10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatOctalBytes(42798L, buffer, 0, 12));
        assertEquals("000012345670 ", new String(buffer, 0, 11));
        assertEquals(0, buffer[11]);

        try {
            TarUtils.formatOctalBytes(Long.MAX_VALUE, buffer, 0, 12);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatLongOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatLongOctalBytes(42798L, buffer, 0, 12));
        assertEquals("000012345670 ", new String(buffer, 0, 12));

        try {
            TarUtils.formatLongOctalBytes(Long.MAX_VALUE, buffer, 0, 12);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatCheckSumOctalBytes(42798L, buffer, 0, 12));
        assertEquals("000012345670 ", new String(buffer, 0, 11));
        assertEquals(0, buffer[10]);
        assertEquals((byte) ' ', buffer[11]);

        try {
            TarUtils.formatCheckSumOctalBytes(Long.MAX_VALUE, buffer, 0, 12);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testComputeCheckSum() {
        byte[] buffer = "test".getBytes();
        assertEquals(448L, TarUtils.computeCheckSum(buffer));

        buffer = new byte[256];
        for (int i = 0; i < 256; i++) {
            buffer[i] = (byte) i;
        }
        assertEquals(32640L, TarUtils.computeCheckSum(buffer));
    }
}