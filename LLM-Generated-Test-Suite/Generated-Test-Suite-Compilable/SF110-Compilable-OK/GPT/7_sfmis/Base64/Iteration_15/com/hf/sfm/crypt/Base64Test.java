package com.hf.sfm.crypt;

import static org.junit.Assert.*;
import org.junit.Test;
import com.hf.sfm.crypt.Base64;

public class Base64Test {

    @Test
    public void testByteArrayToBase64() {
        byte[] input = "Hello, World!".getBytes();
        String expected = "SGVsbG8sIFdvcmxkIQ==";
        String result = Base64.byteArrayToBase64(input);
        assertEquals(expected, result);
    }

    @Test
    public void testBase64ToByteArray() {
        String input = "SGVsbG8sIFdvcmxkIQ==";
        byte[] expected = "Hello, World!".getBytes();
        byte[] result = Base64.base64ToByteArray(input);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testByteArrayToAltBase64() {
        byte[] input = "Hello, World!".getBytes();
        String expected = "SGVsbG8sIFdvcmxkIQ==".replace('+', '!').replace('/', '?');
        String result = Base64.byteArrayToAltBase64(input);
        assertEquals(expected, result);
    }

    @Test
    public void testAltBase64ToByteArray() {
        String input = "SGVsbG8sIFdvcmxkIQ==".replace('+', '!').replace('/', '?');
        byte[] expected = "Hello, World!".getBytes();
        byte[] result = Base64.altBase64ToByteArray(input);
        assertArrayEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBase64ToByteArrayWithInvalidLength() {
        String input = "SGVsbG8sIFdvcmxkIQ="; // Invalid length
        Base64.base64ToByteArray(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltBase64ToByteArrayWithInvalidCharacter() {
        String input = "SGVsbG8sIFdvcmxkIQ==".replace('+', '!').replace('/', '?') + "!";
        Base64.altBase64ToByteArray(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBase64ToByteArrayWithInvalidCharacter() {
        String input = "SGVsbG8sIFdvcmxkIQ==Z";
        Base64.base64ToByteArray(input);
    }

    @Test
    public void testEmptyByteArrayToBase64() {
        byte[] input = new byte[0];
        String expected = "";
        String result = Base64.byteArrayToBase64(input);
        assertEquals(expected, result);
    }

    @Test
    public void testEmptyBase64ToByteArray() {
        String input = "";
        byte[] expected = new byte[0];
        byte[] result = Base64.base64ToByteArray(input);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testEmptyByteArrayToAltBase64() {
        byte[] input = new byte[0];
        String expected = "";
        String result = Base64.byteArrayToAltBase64(input);
        assertEquals(expected, result);
    }

    @Test
    public void testEmptyAltBase64ToByteArray() {
        String input = "";
        byte[] expected = new byte[0];
        byte[] result = Base64.altBase64ToByteArray(input);
        assertArrayEquals(expected, result);
    }
}