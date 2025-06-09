package com.hf.sfm.crypt;

import static org.junit.Assert.*;
import org.junit.Test;
import com.hf.sfm.crypt.Base64;

public class Base64Test {

    @Test
    public void testAltBase64ToByteArray_ValidInput() {
        String input = "!\"#$%&'()*,-.:;<=>@[\\]^_`{|}~abcdefghij";
        byte[] expectedOutput = new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(expectedOutput, Base64.altBase64ToByteArray(input));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltBase64ToByteArray_InvalidLength() {
        String input = "abc";
        Base64.altBase64ToByteArray(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltBase64ToByteArray_IllegalCharacter() {
        String input = "abcd$";
        Base64.altBase64ToByteArray(input);
    }

    @Test
    public void testBase64ToByteArray_ValidInput() {
        String input = "QUJDREVGRw==";
        byte[] expectedOutput = new byte[] {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        assertArrayEquals(expectedOutput, Base64.base64ToByteArray(input));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBase64ToByteArray_InvalidLength() {
        String input = "abc";
        Base64.base64ToByteArray(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBase64ToByteArray_IllegalCharacter() {
        String input = "abcd$";
        Base64.base64ToByteArray(input);
    }

    @Test
    public void testByteArrayToAltBase64() {
        byte[] input = new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        String expectedOutput = "!\"#$%&'()*,-.:;<=>@[\\]^_`{|}~abcdefghij";
        assertEquals(expectedOutput, Base64.byteArrayToAltBase64(input));
    }

    @Test
    public void testByteArrayToBase64() {
        byte[] input = new byte[] {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        String expectedOutput = "QUJDREVGRw==";
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
}