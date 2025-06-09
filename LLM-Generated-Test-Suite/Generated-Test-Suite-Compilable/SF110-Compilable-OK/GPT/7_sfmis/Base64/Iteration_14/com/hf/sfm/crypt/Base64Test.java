package com.hf.sfm.crypt;

import static org.junit.Assert.*;
import org.junit.Test;
import com.hf.sfm.crypt.Base64;

public class Base64Test {

    @Test
    public void testByteArrayToBase64() {
        byte[] input = "Hello, World!".getBytes();
        String expected = "SGVsbG8sIFdvcmxkIQ==";
        assertEquals(expected, Base64.byteArrayToBase64(input));
    }

    @Test
    public void testBase64ToByteArray() {
        String input = "SGVsbG8sIFdvcmxkIQ==";
        byte[] expected = "Hello, World!".getBytes();
        assertArrayEquals(expected, Base64.base64ToByteArray(input));
    }

    @Test
    public void testByteArrayToAltBase64() {
        byte[] input = "Hello, World!".getBytes();
        String expected = "SGVsbG8sIFdvcmxkIQ==".replace('+', '!').replace('/', '?');
        assertEquals(expected, Base64.byteArrayToAltBase64(input));
    }

    @Test
    public void testAltBase64ToByteArray() {
        String input = "SGVsbG8sIFdvcmxkIQ==".replace('+', '!').replace('/', '?');
        byte[] expected = "Hello, World!".getBytes();
        assertArrayEquals(expected, Base64.altBase64ToByteArray(input));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBase64ToByteArrayWithInvalidLength() {
        Base64.base64ToByteArray("SGVsbG8sIFdvcmxkIQ=");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltBase64ToByteArrayWithInvalidLength() {
        Base64.altBase64ToByteArray("SGVsbG8sIFdvcmxkIQ=");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBase64ToByteArrayWithIllegalCharacter() {
        Base64.base64ToByteArray("SGVsbG8sIFdvcmxkIQ==#");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltBase64ToByteArrayWithIllegalCharacter() {
        Base64.altBase64ToByteArray("SGVsbG8sIFdvcmxkIQ==#");
    }

    @Test
    public void testEmptyByteArrayToBase64() {
        byte[] input = new byte[0];
        String expected = "";
        assertEquals(expected, Base64.byteArrayToBase64(input));
    }

    @Test
    public void testEmptyBase64ToByteArray() {
        String input = "";
        byte[] expected = new byte[0];
        assertArrayEquals(expected, Base64.base64ToByteArray(input));
    }

    @Test
    public void testEmptyByteArrayToAltBase64() {
        byte[] input = new byte[0];
        String expected = "";
        assertEquals(expected, Base64.byteArrayToAltBase64(input));
    }

    @Test
    public void testEmptyAltBase64ToByteArray() {
        String input = "";
        byte[] expected = new byte[0];
        assertArrayEquals(expected, Base64.altBase64ToByteArray(input));
    }
}