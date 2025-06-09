package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TarUtilsTest {

    @Test
    public void testParseOctal() {
        byte[] buffer = " 12345670".getBytes();
        assertEquals(42798, TarUtils.parseOctal(buffer, 0, buffer.length));

        buffer = "00000000000".getBytes();
        assertEquals(0, TarUtils.parseOctal(buffer, 0, buffer.length));

        buffer = "12345678".getBytes();
        assertEquals(2739128, TarUtils.parseOctal(buffer, 0, buffer.length));

        buffer = "123456789".getBytes();
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

        buffer = "file\0name.tar".getBytes();
        assertEquals("file", TarUtils.parseName(buffer, 0, buffer.length));

        buffer = new byte[10];
        assertEquals("", TarUtils.parseName(buffer, 0, buffer.length));
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
        assertEquals(5, TarUtils.formatNameBytes("testname", buffer, 0, 5));
        assertEquals("testn", new String(buffer, 0, 5));
    }

    @Test
    public void testFormatUnsignedOctalString() {
        byte[] buffer = new byte[10];
        TarUtils.formatUnsignedOctalString(12345, buffer, 0, 10);
        assertEquals("0000030071", new String(buffer, 0, 10));

        buffer = new byte[5];
        try {
            TarUtils.formatUnsignedOctalString(123456789, buffer, 0, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatOctalBytes(12345, buffer, 0, 12));
        assertEquals("0000030071 ", new String(buffer, 0, 11));
        assertEquals(0, buffer[11]);
    }

    @Test
    public void testFormatLongOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatLongOctalBytes(12345, buffer, 0, 12));
        assertEquals("0000030071 ", new String(buffer, 0, 11));
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatCheckSumOctalBytes(12345, buffer, 0, 12));
        assertEquals("0000030071", new String(buffer, 0, 10));
        assertEquals(0, buffer[10]);
        assertEquals(' ', buffer[11]);
    }

    @Test
    public void testComputeCheckSum() {
        byte[] buffer = "test".getBytes();
        assertEquals(448, TarUtils.computeCheckSum(buffer));

        buffer = new byte[10];
        assertEquals(0, TarUtils.computeCheckSum(buffer));
    }
}