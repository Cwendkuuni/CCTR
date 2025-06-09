package com.hf.sfm.crypt;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

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
        String expected = "c2VsbG8sIFdvcmxkIQ==";
        String result = Base64.byteArrayToAltBase64(input);
        assertEquals(expected, result);
    }

    @Test
    public void testAltBase64ToByteArray() {
        String input = "c2VsbG8sIFdvcmxkIQ==";
        byte[] expected = "Hello, World!".getBytes();
        byte[] result = Base64.altBase64ToByteArray(input);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBase64ToByteArrayWithInvalidLength() {
        String input = "SGVsbG8sIFdvcmxkIQ="; // Invalid length
        try {
            Base64.base64ToByteArray(input);
            fail("Expected IllegalArgumentException for invalid length");
        } catch (IllegalArgumentException e) {
            assertEquals("String length must be a multiple of four.", e.getMessage());
        }
    }

    @Test
    public void testAltBase64ToByteArrayWithInvalidCharacter() {
        String input = "SGVsbG8sIFdvcmxkIQ==";
        try {
            Base64.altBase64ToByteArray(input);
            fail("Expected IllegalArgumentException for invalid character");
        } catch (IllegalArgumentException e) {
            assertEquals("Illegal character S", e.getMessage());
        }
    }

    @Test
    public void testBase64ToByteArrayWithInvalidCharacter() {
        String input = "SGVsbG8sIFdvcmxkIQ==";
        try {
            Base64.base64ToByteArray(input);
            fail("Expected IllegalArgumentException for invalid character");
        } catch (IllegalArgumentException e) {
            assertEquals("Illegal character S", e.getMessage());
        }
    }

    @Test
    public void testByteArrayToBase64Empty() {
        byte[] input = new byte[0];
        String expected = "";
        String result = Base64.byteArrayToBase64(input);
        assertEquals(expected, result);
    }

    @Test
    public void testBase64ToByteArrayEmpty() {
        String input = "";
        byte[] expected = new byte[0];
        byte[] result = Base64.base64ToByteArray(input);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testByteArrayToAltBase64Empty() {
        byte[] input = new byte[0];
        String expected = "";
        String result = Base64.byteArrayToAltBase64(input);
        assertEquals(expected, result);
    }

    @Test
    public void testAltBase64ToByteArrayEmpty() {
        String input = "";
        byte[] expected = new byte[0];
        byte[] result = Base64.altBase64ToByteArray(input);
        assertArrayEquals(expected, result);
    }
}