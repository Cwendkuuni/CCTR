package com.google.common.base;

import static org.junit.Assert.*;
import org.junit.Test;

public class Utf8Test {

    @Test
    public void testEncodedLength() {
        // Test with ASCII characters
        assertEquals(5, Utf8.encodedLength("hello"));

        // Test with non-ASCII characters
        assertEquals(7, Utf8.encodedLength("héllo"));

        // Test with surrogate pairs
        assertEquals(8, Utf8.encodedLength("𝌆"));

        // Test with mixed characters
        assertEquals(10, Utf8.encodedLength("hello𝌆"));

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
        byte[] wellFormedBytes = {(byte) 0x48, (byte) 0x65, (byte) 0x6c, (byte) 0x6c, (byte) 0x6f}; // "Hello"
        assertTrue(Utf8.isWellFormed(wellFormedBytes));

        // Test with well-formed UTF-8 bytes containing non-ASCII characters
        byte[] wellFormedBytesWithNonAscii = {(byte) 0x48, (byte) 0xc3, (byte) 0xa9, (byte) 0x6c, (byte) 0x6c, (byte) 0x6f}; // "Héllo"
        assertTrue(Utf8.isWellFormed(wellFormedBytesWithNonAscii));

        // Test with empty byte array
        assertTrue(Utf8.isWellFormed(new byte[]{}));
    }

    @Test
    public void testIsWellFormedWithOffsetAndLength() {
        // Test with well-formed UTF-8 bytes and offset/length
        byte[] wellFormedBytes = {(byte) 0x48, (byte) 0x65, (byte) 0x6c, (byte) 0x6c, (byte) 0x6f}; // "Hello"
        assertTrue(Utf8.isWellFormed(wellFormedBytes, 0, 5));
        assertTrue(Utf8.isWellFormed(wellFormedBytes, 1, 4));

        // Test with well-formed UTF-8 bytes containing non-ASCII characters and offset/length
        byte[] wellFormedBytesWithNonAscii = {(byte) 0x48, (byte) 0xc3, (byte) 0xa9, (byte) 0x6c, (byte) 0x6c, (byte) 0x6f}; // "Héllo"
        assertTrue(Utf8.isWellFormed(wellFormedBytesWithNonAscii, 0, 6));
        assertTrue(Utf8.isWellFormed(wellFormedBytesWithNonAscii, 1, 5));
    }

    @Test
    public void testIsWellFormedMalformedBytes() {
        // Test with malformed UTF-8 bytes
        byte[] malformedBytes = {(byte) 0x48, (byte) 0xc3, (byte) 0x28, (byte) 0x6c, (byte) 0x6c, (byte) 0x6f}; // Invalid byte sequence
        assertFalse(Utf8.isWellFormed(malformedBytes));

        // Test with malformed UTF-8 bytes and offset/length
        assertFalse(Utf8.isWellFormed(malformedBytes, 0, 6));
        assertFalse(Utf8.isWellFormed(malformedBytes, 1, 5));
    }
}