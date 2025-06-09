package org.apache.commons.compress.archivers.tar;

import static org.junit.Assert.*;
import org.junit.Test;

public class TarUtilsTest {

    @Test
    public void testParseOctal() {
        byte[] buffer = "00000001234".getBytes();
        assertEquals(1234, TarUtils.parseOctal(buffer, 0, buffer.length));

        buffer = "00000000000".getBytes();
        assertEquals(0, TarUtils.parseOctal(buffer, 0, buffer.length));

        buffer = "00000007777".getBytes();
        assertEquals(4095, TarUtils.parseOctal(buffer, 0, buffer.length));

        buffer = "00000008000".getBytes();
        try {
            TarUtils.parseOctal(buffer, 0, buffer.length);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testParseName() {
        byte[] buffer = "testName".getBytes();
        assertEquals("testName", TarUtils.parseName(buffer, 0, buffer.length));

        buffer = "testName\0".getBytes();
        assertEquals("testName", TarUtils.parseName(buffer, 0, buffer.length));

        buffer = "testName\0extra".getBytes();
        assertEquals("testName", TarUtils.parseName(buffer, 0, 8));
    }

    @Test
    public void testFormatNameBytes() {
        byte[] buffer = new byte[10];
        assertEquals(10, TarUtils.formatNameBytes("testName", buffer, 0, 10));
        assertEquals("testName", new String(buffer, 0, 8));
        assertEquals(0, buffer[8]);
        assertEquals(0, buffer[9]);

        buffer = new byte[5];
        assertEquals(5, TarUtils.formatNameBytes("testName", buffer, 0, 5));
        assertEquals("testN", new String(buffer, 0, 5));
    }

    @Test
    public void testFormatUnsignedOctalString() {
        byte[] buffer = new byte[10];
        TarUtils.formatUnsignedOctalString(1234, buffer, 0, 10);
        assertEquals("00000002322", new String(buffer, 0, 10));

        buffer = new byte[5];
        try {
            TarUtils.formatUnsignedOctalString(1234, buffer, 0, 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatOctalBytes(1234, buffer, 0, 12));
        assertEquals("00000002322 ", new String(buffer, 0, 11));
        assertEquals(0, buffer[11]);
    }

    @Test
    public void testFormatLongOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatLongOctalBytes(1234, buffer, 0, 12));
        assertEquals("00000002322 ", new String(buffer, 0, 12));
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        byte[] buffer = new byte[12];
        assertEquals(12, TarUtils.formatCheckSumOctalBytes(1234, buffer, 0, 12));
        assertEquals("00000002322\0 ", new String(buffer, 0, 12));
    }

    @Test
    public void testComputeCheckSum() {
        byte[] buffer = "test".getBytes();
        assertEquals(451, TarUtils.computeCheckSum(buffer));

        buffer = new byte[10];
        assertEquals(0, TarUtils.computeCheckSum(buffer));

        buffer = new byte[]{1, 2, 3, 4, 5};
        assertEquals(15, TarUtils.computeCheckSum(buffer));
    }
}