package com.google.common.base;

import static org.junit.Assert.*;
import org.junit.Test;
import com.google.common.base.Utf8;

public class Utf8Test {

    @Test
    public void testEncodedLengthBasicAscii() {
        assertEquals(5, Utf8.encodedLength("Hello"));
    }

    @Test
    public void testEncodedLengthExtendedAscii() {
        assertEquals(7, Utf8.encodedLength("Héllo"));
    }

    @Test
    public void testEncodedLengthNonAscii() {
        assertEquals(9, Utf8.encodedLength("こんにちは"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodedLengthUnpairedSurrogate() {
        Utf8.encodedLength("\uD800");
    }

    @Test
    public void testIsWellFormedBasicAscii() {
        byte[] bytes = "Hello".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        assertTrue(Utf8.isWellFormed(bytes));
    }

    @Test
    public void testIsWellFormedExtendedAscii() {
        byte[] bytes = "Héllo".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        assertTrue(Utf8.isWellFormed(bytes));
    }

    @Test
    public void testIsWellFormedNonAscii() {
        byte[] bytes = "こんにちは".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        assertTrue(Utf8.isWellFormed(bytes));
    }

    @Test
    public void testIsWellFormedInvalidUtf8() {
        byte[] bytes = {(byte) 0xC3, (byte) 0x28}; // Invalid UTF-8 sequence
        assertFalse(Utf8.isWellFormed(bytes));
    }

    @Test
    public void testIsWellFormedWithOffsetAndLengthValid() {
        byte[] bytes = "Hello, 世界".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        assertTrue(Utf8.isWellFormed(bytes, 0, bytes.length));
    }

    @Test
    public void testIsWellFormedWithOffsetAndLengthInvalid() {
        byte[] bytes = {(byte) 0xE3, (byte) 0x81, (byte) 0x82, (byte) 0xC3, (byte) 0x28}; // Mixed valid and invalid
        assertFalse(Utf8.isWellFormed(bytes, 0, bytes.length));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsWellFormedWithOffsetAndLengthOutOfBounds() {
        byte[] bytes = "Hello".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        Utf8.isWellFormed(bytes, 0, bytes.length + 1);
    }
}