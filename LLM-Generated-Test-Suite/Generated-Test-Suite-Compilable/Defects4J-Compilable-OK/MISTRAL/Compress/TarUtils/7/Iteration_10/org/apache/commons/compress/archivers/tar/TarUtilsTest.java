package org.apache.commons.compress.archivers.tar;

import static org.junit.Assert.*;
import org.junit.Test;

public class TarUtilsTest {

    @Test
    public void testParseOctal() {
        byte[] buffer = " 12345670".getBytes();
        assertEquals(42798L, TarUtils.parseOctal(buffer, 0, buffer.length));

        buffer = "00000000000".getBytes();
        assertEquals(0L, TarUtils.parseOctal(buffer, 0, buffer.length));

        buffer = " 12345670 ".getBytes();
        assertEquals(42798L, TarUtils.parseOctal(buffer, 0, buffer.length));

        buffer = " 12345670\0".getBytes();
        assertEquals(42798L, TarUtils.parseOctal(buffer, 0, buffer.length));

        buffer = " 12345678".getBytes();
        try {
            TarUtils.parseOctal(buffer, 0, buffer.length);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testParseName() {
        byte[] buffer = "filename.tar".getBytes();
        assertEquals("filename.tar", TarUtils.parseName(buffer, 0, buffer.length));

        buffer = "filename.tar\0".getBytes();
        assertEquals("filename.tar", TarUtils.parseName(buffer, 0, buffer.length));

        buffer = "filename.tar\0extra".getBytes();
        assertEquals("filename.tar", TarUtils.parseName(buffer, 0, buffer.length));
    }

    @Test
    public void testFormatNameBytes() {
        byte[] buffer = new byte[10];
        assertEquals(10, TarUtils.formatNameBytes("test", buffer, 0, 10));
        assertEquals("test", new String(buffer, 0, 4));
        for (int i = 4; i < 10; i++) {
            assertEquals(0, buffer[i]);
        }

        buffer = new byte[5];
        assertEquals(5, TarUtils.formatNameBytes("test", buffer, 0, 5));
        assertEquals("test", new String(buffer, 0, 4));
        assertEquals(0, buffer[4]);
    }

    @Test
    public void testFormatUnsignedOctalString() {
        byte[] buffer = new byte[10];
        TarUtils.formatUnsignedOctalString(1234567L, buffer, 0, 10);
        assertEquals("00000175067", new String(buffer, 0, 10));

        buffer = new byte[5];
        try {
            TarUtils.formatUnsignedOctalString(1234567L, buffer, 0, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatOctalBytes(1234567L, buffer, 0, 12));
        assertEquals("00000175067 ", new String(buffer, 0, 11));
        assertEquals(0, buffer[11]);

        buffer = new byte[5];
        try {
            TarUtils.formatOctalBytes(1234567L, buffer, 0, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatLongOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatLongOctalBytes(1234567L, buffer, 0, 12));
        assertEquals("00000175067 ", new String(buffer, 0, 12));

        buffer = new byte[5];
        try {
            TarUtils.formatLongOctalBytes(1234567L, buffer, 0, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatCheckSumOctalBytes(1234567L, buffer, 0, 12));
        assertEquals("00000175067\0 ", new String(buffer, 0, 12));

        buffer = new byte[5];
        try {
            TarUtils.formatCheckSumOctalBytes(1234567L, buffer, 0, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testComputeCheckSum() {
        byte[] buffer = "test".getBytes();
        assertEquals(451, TarUtils.computeCheckSum(buffer));

        buffer = new byte[512];
        for (int i = 0; i < 512; i++) {
            buffer[i] = (byte) i;
        }
        long sum = 0;
        for (int i = 0; i < 512; i++) {
            sum += i;
        }
        assertEquals(sum, TarUtils.computeCheckSum(buffer));
    }
}