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

    @Test
    public void testIsWellFormedBasicAscii() {
        byte[] bytes = "Hello".getBytes();
        assertTrue(Utf8.isWellFormed(bytes));
    }

    @Test
    public void testIsWellFormedExtendedAscii() {
        byte[] bytes = "Héllo".getBytes();
        assertTrue(Utf8.isWellFormed(bytes));
    }

    @Test
    public void testIsWellFormedNonAscii() {
        byte[] bytes = "こんにちは".getBytes();
        assertTrue(Utf8.isWellFormed(bytes));
    }

    @Test
    public void testIsWellFormedInvalidUtf8() {
        byte[] bytes = {(byte) 0xC3, (byte) 0x28};
        assertFalse(Utf8.isWellFormed(bytes));
    }

    @Test
    public void testIsWellFormedPartialArray() {
        byte[] bytes = "Hello, 世界".getBytes();
        assertTrue(Utf8.isWellFormed(bytes, 0, 5)); // "Hello"
        assertTrue(Utf8.isWellFormed(bytes, 7, 6)); // "世界"
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsWellFormedInvalidRange() {
        byte[] bytes = "Hello".getBytes();
        Utf8.isWellFormed(bytes, 0, 10);
    }

    @Test
    public void testIsWellFormedSlowPath() {
        byte[] bytes = {(byte) 0xE2, (byte) 0x82, (byte) 0xAC}; // Valid UTF-8 for €
        assertTrue(Utf8.isWellFormed(bytes));
    }

    @Test
    public void testIsWellFormedSlowPathInvalid() {
        byte[] bytes = {(byte) 0xE2, (byte) 0x28, (byte) 0xA1}; // Invalid UTF-8
        assertFalse(Utf8.isWellFormed(bytes));
    }
}