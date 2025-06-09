package com.google.common.base;

import static org.junit.Assert.*;
import org.junit.Test;
import com.google.common.base.Utf8;

public class Utf8Test {

    @Test
    public void testEncodedLengthBasicAscii() {
        assertEquals(3, Utf8.encodedLength("abc"));
    }

    @Test
    public void testEncodedLengthExtendedAscii() {
        assertEquals(4, Utf8.encodedLength("a\u00A2c")); // ¢ is U+00A2
    }

    @Test
    public void testEncodedLengthTwoByteChar() {
        assertEquals(5, Utf8.encodedLength("a\u0800c")); // U+0800
    }

    @Test
    public void testEncodedLengthSurrogatePair() {
        assertEquals(4, Utf8.encodedLength("a\uD83D\uDE00")); // 😀 is U+1F600
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodedLengthUnpairedSurrogate() {
        Utf8.encodedLength("a\uD800");
    }

    @Test
    public void testIsWellFormedBasicAscii() {
        assertTrue(Utf8.isWellFormed(new byte[] { 97, 98, 99 })); // "abc"
    }

    @Test
    public void testIsWellFormedExtendedAscii() {
        assertTrue(Utf8.isWellFormed(new byte[] { 97, (byte) 0xC2, (byte) 0xA2, 99 })); // "a¢c"
    }

    @Test
    public void testIsWellFormedTwoByteChar() {
        assertTrue(Utf8.isWellFormed(new byte[] { 97, (byte) 0xE0, (byte) 0xA0, (byte) 0x80, 99 })); // "a\u0800c"
    }

    @Test
    public void testIsWellFormedSurrogatePair() {
        assertTrue(Utf8.isWellFormed(new byte[] { 97, (byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x80 })); // "a😀"
    }

    @Test
    public void testIsWellFormedInvalidUtf8() {
        assertFalse(Utf8.isWellFormed(new byte[] { (byte) 0x80 }));
    }

    @Test
    public void testIsWellFormedWithOffsetAndLength() {
        byte[] bytes = new byte[] { 97, (byte) 0xC2, (byte) 0xA2, 99 }; // "a¢c"
        assertTrue(Utf8.isWellFormed(bytes, 1, 2)); // "¢"
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsWellFormedWithInvalidOffset() {
        byte[] bytes = new byte[] { 97, (byte) 0xC2, (byte) 0xA2, 99 }; // "a¢c"
        Utf8.isWellFormed(bytes, -1, 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsWellFormedWithInvalidLength() {
        byte[] bytes = new byte[] { 97, (byte) 0xC2, (byte) 0xA2, 99 }; // "a¢c"
        Utf8.isWellFormed(bytes, 1, 4);
    }
}