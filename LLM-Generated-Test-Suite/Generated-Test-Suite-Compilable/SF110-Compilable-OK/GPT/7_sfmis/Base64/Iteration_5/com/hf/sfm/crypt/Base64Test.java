package com.hf.sfm.crypt;

import static org.junit.Assert.*;
import org.junit.Test;
import com.hf.sfm.crypt.Base64;

public class Base64Test {

    @Test
    public void testAltBase64ToByteArray_ValidInput() {
        String input = "!\"#$";
        byte[] expectedOutput = new byte[] {0, 1, 2};
        assertArrayEquals(expectedOutput, Base64.altBase64ToByteArray(input));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltBase64ToByteArray_InvalidLength() {
        String input = "!\"#";
        Base64.altBase64ToByteArray(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltBase64ToByteArray_IllegalCharacter() {
        String input = "!\"#$%";
        Base64.altBase64ToByteArray(input);
    }

    @Test
    public void testBase64ToByteArray_ValidInput() {
        String input = "QUJD";
        byte[] expectedOutput = new byte[] {65, 66, 67};
        assertArrayEquals(expectedOutput, Base64.base64ToByteArray(input));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBase64ToByteArray_InvalidLength() {
        String input = "QUJ";
        Base64.base64ToByteArray(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBase64ToByteArray_IllegalCharacter() {
        String input = "QUJD%";
        Base64.base64ToByteArray(input);
    }

    @Test
    public void testByteArrayToAltBase64() {
        byte[] input = new byte[] {0, 1, 2};
        String expectedOutput = "!\"#$";
        assertEquals(expectedOutput, Base64.byteArrayToAltBase64(input));
    }

    @Test
    public void testByteArrayToBase64() {
        byte[] input = new byte[] {65, 66, 67};
        String expectedOutput = "QUJD";
        assertEquals(expectedOutput, Base64.byteArrayToBase64(input));
    }

    @Test
    public void testByteArrayToBase64_EmptyArray() {
        byte[] input = new byte[] {};
        String expectedOutput = "";
        assertEquals(expectedOutput, Base64.byteArrayToBase64(input));
    }

    @Test
    public void testByteArrayToAltBase64_EmptyArray() {
        byte[] input = new byte[] {};
        String expectedOutput = "";
        assertEquals(expectedOutput, Base64.byteArrayToAltBase64(input));
    }

    @Test
    public void testRoundTrip_Base64() {
        String original = "Hello, World!";
        String encoded = Base64.byteArrayToBase64(original.getBytes());
        byte[] decoded = Base64.base64ToByteArray(encoded);
        assertEquals(original, new String(decoded));
    }

    @Test
    public void testRoundTrip_AltBase64() {
        String original = "Hello, World!";
        String encoded = Base64.byteArrayToAltBase64(original.getBytes());
        byte[] decoded = Base64.altBase64ToByteArray(encoded);
        assertEquals(original, new String(decoded));
    }
}