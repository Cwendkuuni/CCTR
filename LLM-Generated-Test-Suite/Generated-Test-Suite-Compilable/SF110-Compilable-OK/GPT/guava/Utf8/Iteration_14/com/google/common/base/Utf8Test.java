package com.google.common.base;

import static org.junit.Assert.*;
import org.junit.Test;

public class Utf8Test {

    @Test
    public void testEncodedLengthBasicAscii() {
        assertEquals(5, Utf8.encodedLength("Hello"));
    }

    @Test
    public void testEncodedLengthExtendedAscii() {
        assertEquals(6, Utf8.encodedLength("Héllo"));
    }

    @Test
    public void testEncodedLengthNonAscii() {
        assertEquals(9, Utf8.encodedLength("こんにちは"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodedLengthUnpairedSurrogate() {
        Utf8.encodedLength("\uD800");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodedLengthOverflow() {
        // This test is theoretical since Java strings can't exceed Integer.MAX_VALUE in length.
        // However, we simulate the condition by directly calling the method with a large value.
        Utf8.encodedLength(new String(new char[Integer.MAX_VALUE / 2]));
    }

    @Test
    public void testIsWellFormedBasicAscii() {
        assertTrue(Utf8.isWellFormed("Hello".getBytes()));
    }

    @Test
    public void testIsWellFormedExtendedAscii() {
        assertTrue(Utf8.isWellFormed("Héllo".getBytes()));
    }

    @Test
    public void testIsWellFormedNonAscii() {
        assertTrue(Utf8.isWellFormed("こんにちは".getBytes()));
    }

    @Test
    public void testIsWellFormedInvalidUtf8() {
        byte[] invalidUtf8 = {(byte) 0xC3, (byte) 0x28};
        assertFalse(Utf8.isWellFormed(invalidUtf8));
    }

    @Test
    public void testIsWellFormedWithOffsetAndLength() {
        byte[] bytes = "Hello, 世界".getBytes();
        assertTrue(Utf8.isWellFormed(bytes, 0, bytes.length));
    }

    @Test
    public void testIsWellFormedWithOffsetAndLengthInvalid() {
        byte[] bytes = {(byte) 0xE3, (byte) 0x81, (byte) 0x82, (byte) 0xC3, (byte) 0x28};
        assertFalse(Utf8.isWellFormed(bytes, 0, bytes.length));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsWellFormedWithInvalidOffset() {
        byte[] bytes = "Hello".getBytes();
        Utf8.isWellFormed(bytes, -1, bytes.length);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsWellFormedWithInvalidLength() {
        byte[] bytes = "Hello".getBytes();
        Utf8.isWellFormed(bytes, 0, bytes.length + 1);
    }
}