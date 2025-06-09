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
    public void testEncodedLengthSurrogatePair() {
        assertEquals(4, Utf8.encodedLength("\uD83D\uDE00")); // 😀
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
    public void testIsWellFormedSurrogatePair() {
        assertTrue(Utf8.isWellFormed("\uD83D\uDE00".getBytes())); // 😀
    }

    @Test
    public void testIsWellFormedInvalidUtf8() {
        byte[] invalidUtf8 = {(byte) 0xC3, (byte) 0x28};
        assertFalse(Utf8.isWellFormed(invalidUtf8));
    }

    @Test
    public void testIsWellFormedEmptyArray() {
        assertTrue(Utf8.isWellFormed(new byte[0]));
    }

    @Test
    public void testIsWellFormedPartialArray() {
        byte[] bytes = "Hello, world!".getBytes();
        assertTrue(Utf8.isWellFormed(bytes, 0, 5)); // "Hello"
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsWellFormedInvalidOffset() {
        byte[] bytes = "Hello".getBytes();
        Utf8.isWellFormed(bytes, -1, 5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsWellFormedInvalidLength() {
        byte[] bytes = "Hello".getBytes();
        Utf8.isWellFormed(bytes, 0, 10);
    }
}