package com.google.common.base;

import org.junit.Test;
import static org.junit.Assert.*;

public class Utf8Test {

    @Test
    public void testEncodedLength() {
        // Test with ASCII characters
        assertEquals(5, Utf8.encodedLength("hello"));

        // Test with non-ASCII characters
        assertEquals(8, Utf8.encodedLength("hello世界"));

        // Test with surrogate pairs
        assertEquals(8, Utf8.encodedLength("𝌆𝌆"));

        // Test with mixed characters
        assertEquals(10, Utf8.encodedLength("hello𝌆𝌆"));

        // Test with empty string
        assertEquals(0, Utf8.encodedLength(""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodedLengthException() {
        // Test with unpaired surrogate
        Utf8.encodedLength("\ud800");
    }

    @Test
    public void testIsWellFormed() {
        // Test with well-formed UTF-8 bytes
        byte[] wellFormedBytes = "hello".getBytes();
        assertTrue(Utf8.isWellFormed(wellFormedBytes));

        // Test with well-formed UTF-8 bytes containing non-ASCII characters
        wellFormedBytes = "hello世界".getBytes();
        assertTrue(Utf8.isWellFormed(wellFormedBytes));

        // Test with empty byte array
        wellFormedBytes = new byte[0];
        assertTrue(Utf8.isWellFormed(wellFormedBytes));
    }

    @Test
    public void testIsWellFormedWithOffsetAndLength() {
        // Test with well-formed UTF-8 bytes and offset/length
        byte[] wellFormedBytes = "hello世界".getBytes();
        assertTrue(Utf8.isWellFormed(wellFormedBytes, 0, wellFormedBytes.length));
        assertTrue(Utf8.isWellFormed(wellFormedBytes, 5, 5));

        // Test with empty byte array and offset/length
        wellFormedBytes = new byte[0];
        assertTrue(Utf8.isWellFormed(wellFormedBytes, 0, 0));
    }

    @Test
    public void testIsWellFormedMalformedBytes() {
        // Test with malformed UTF-8 bytes
        byte[] malformedBytes = {(byte) 0xC0, (byte) 0x80}; // Overlong encoding
        assertFalse(Utf8.isWellFormed(malformedBytes));

        malformedBytes = new byte[] {(byte) 0xF0, (byte) 0x82, (byte) 0x82, (byte) 0x82}; // Invalid 4-byte sequence
        assertFalse(Utf8.isWellFormed(malformedBytes));
    }

    @Test
    public void testIsWellFormedWithOffsetAndLengthMalformedBytes() {
        // Test with malformed UTF-8 bytes and offset/length
        byte[] malformedBytes = {(byte) 0xC0, (byte) 0x80, (byte) 0xC0, (byte) 0x80}; // Overlong encoding
        assertFalse(Utf8.isWellFormed(malformedBytes, 0, 2));
        assertFalse(Utf8.isWellFormed(malformedBytes, 2, 2));

        malformedBytes = new byte[] {(byte) 0xF0, (byte) 0x82, (byte) 0x82, (byte) 0x82, (byte) 0xF0, (byte) 0x82, (byte) 0x82, (byte) 0x82}; // Invalid 4-byte sequence
        assertFalse(Utf8.isWellFormed(malformedBytes, 0, 4));
        assertFalse(Utf8.isWellFormed(malformedBytes, 4, 4));
    }
}