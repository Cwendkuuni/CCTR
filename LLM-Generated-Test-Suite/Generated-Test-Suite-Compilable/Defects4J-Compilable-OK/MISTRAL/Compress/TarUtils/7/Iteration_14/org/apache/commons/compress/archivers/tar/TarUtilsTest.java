package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class TarUtilsTest {

    private byte[] buffer;

    @Before
    public void setUp() {
        buffer = new byte[100];
    }

    @Test
    public void testParseOctal() {
        // Test with valid octal string
        byte[] octalBytes = " 01234567".getBytes();
        System.arraycopy(octalBytes, 0, buffer, 0, octalBytes.length);
        long result = TarUtils.parseOctal(buffer, 0, octalBytes.length);
        assertEquals(42798L, result);

        // Test with leading spaces
        octalBytes = "   01234567".getBytes();
        System.arraycopy(octalBytes, 0, buffer, 0, octalBytes.length);
        result = TarUtils.parseOctal(buffer, 0, octalBytes.length);
        assertEquals(42798L, result);

        // Test with trailing spaces
        octalBytes = "01234567   ".getBytes();
        System.arraycopy(octalBytes, 0, buffer, 0, octalBytes.length);
        result = TarUtils.parseOctal(buffer, 0, octalBytes.length);
        assertEquals(42798L, result);

        // Test with invalid octal digit
        octalBytes = "01234568".getBytes();
        System.arraycopy(octalBytes, 0, buffer, 0, octalBytes.length);
        try {
            TarUtils.parseOctal(buffer, 0, octalBytes.length);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testParseName() {
        // Test with valid name
        byte[] nameBytes = "filename".getBytes();
        System.arraycopy(nameBytes, 0, buffer, 0, nameBytes.length);
        String result = TarUtils.parseName(buffer, 0, nameBytes.length);
        assertEquals("filename", result);

        // Test with trailing NUL
        nameBytes = "filename\0".getBytes();
        System.arraycopy(nameBytes, 0, buffer, 0, nameBytes.length);
        result = TarUtils.parseName(buffer, 0, nameBytes.length);
        assertEquals("filename", result);

        // Test with buffer length reached
        nameBytes = "filename".getBytes();
        System.arraycopy(nameBytes, 0, buffer, 0, nameBytes.length);
        result = TarUtils.parseName(buffer, 0, nameBytes.length - 1);
        assertEquals("filenam", result);
    }

    @Test
    public void testFormatNameBytes() {
        // Test with name shorter than buffer
        String name = "filename";
        int offset = 0;
        int length = 10;
        int result = TarUtils.formatNameBytes(name, buffer, offset, length);
        assertEquals(offset + length, result);
        assertEquals("filename\0\0\0", new String(buffer, 0, length));

        // Test with name longer than buffer
        name = "longfilename";
        offset = 0;
        length = 5;
        result = TarUtils.formatNameBytes(name, buffer, offset, length);
        assertEquals(offset + length, result);
        assertEquals("longf", new String(buffer, 0, length));
    }

    @Test
    public void testFormatUnsignedOctalString() {
        // Test with value fitting in buffer
        long value = 12345L;
        int offset = 0;
        int length = 10;
        TarUtils.formatUnsignedOctalString(value, buffer, offset, length);
        assertEquals("0000030071", new String(buffer, 0, length));

        // Test with value not fitting in buffer
        value = 123456789012345L;
        offset = 0;
        length = 5;
        try {
            TarUtils.formatUnsignedOctalString(value, buffer, offset, length);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatOctalBytes() {
        // Test with value fitting in buffer
        long value = 12345L;
        int offset = 0;
        int length = 12;
        int result = TarUtils.formatOctalBytes(value, buffer, offset, length);
        assertEquals(offset + length, result);
        assertEquals("0000030071 \0", new String(buffer, 0, length));

        // Test with value not fitting in buffer
        value = 123456789012345L;
        offset = 0;
        length = 7;
        try {
            TarUtils.formatOctalBytes(value, buffer, offset, length);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatLongOctalBytes() {
        // Test with value fitting in buffer
        long value = 12345L;
        int offset = 0;
        int length = 11;
        int result = TarUtils.formatLongOctalBytes(value, buffer, offset, length);
        assertEquals(offset + length, result);
        assertEquals("0000030071 ", new String(buffer, 0, length));

        // Test with value not fitting in buffer
        value = 123456789012345L;
        offset = 0;
        length = 6;
        try {
            TarUtils.formatLongOctalBytes(value, buffer, offset, length);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testFormatCheckSumOctalBytes() {
        // Test with value fitting in buffer
        long value = 12345L;
        int offset = 0;
        int length = 12;
        int result = TarUtils.formatCheckSumOctalBytes(value, buffer, offset, length);
        assertEquals(offset + length, result);
        assertEquals("0000030071\0 ", new String(buffer, 0, length));

        // Test with value not fitting in buffer
        value = 123456789012345L;
        offset = 0;
        length = 7;
        try {
            TarUtils.formatCheckSumOctalBytes(value, buffer, offset, length);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testComputeCheckSum() {
        // Test with sample buffer
        byte[] sampleBuffer = {1, 2, 3, 4, 5};
        long result = TarUtils.computeCheckSum(sampleBuffer);
        assertEquals(15L, result);

        // Test with empty buffer
        byte[] emptyBuffer = {};
        result = TarUtils.computeCheckSum(emptyBuffer);
        assertEquals(0L, result);
    }
}