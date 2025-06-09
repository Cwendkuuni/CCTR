package com.hf.sfm.crypt;

import static org.junit.Assert.*;
import org.junit.Test;
import com.hf.sfm.crypt.Base64;

public class Base64Test {

    @Test
    public void testAltBase64ToByteArray_ValidInput() {
        String input = "!\"#$%&'()*,-.:;<=>@[\\]^_`{|}~abc";
        byte[] expectedOutput = "Man".getBytes();
        assertArrayEquals(expectedOutput, Base64.altBase64ToByteArray(input));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltBase64ToByteArray_InvalidLength() {
        String input = "abc";
        Base64.altBase64ToByteArray(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltBase64ToByteArray_InvalidCharacter() {
        String input = "abcd$";
        Base64.altBase64ToByteArray(input);
    }

    @Test
    public void testBase64ToByteArray_ValidInput() {
        String input = "TWFu";
        byte[] expectedOutput = "Man".getBytes();
        assertArrayEquals(expectedOutput, Base64.base64ToByteArray(input));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBase64ToByteArray_InvalidLength() {
        String input = "abc";
        Base64.base64ToByteArray(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBase64ToByteArray_InvalidCharacter() {
        String input = "abcd$";
        Base64.base64ToByteArray(input);
    }

    @Test
    public void testByteArrayToAltBase64() {
        byte[] input = "Man".getBytes();
        String expectedOutput = "!\"#$";
        assertEquals(expectedOutput, Base64.byteArrayToAltBase64(input));
    }

    @Test
    public void testByteArrayToBase64() {
        byte[] input = "Man".getBytes();
        String expectedOutput = "TWFu";
        assertEquals(expectedOutput, Base64.byteArrayToBase64(input));
    }

    @Test
    public void testByteArrayToBase64_EmptyArray() {
        byte[] input = new byte[0];
        String expectedOutput = "";
        assertEquals(expectedOutput, Base64.byteArrayToBase64(input));
    }

    @Test
    public void testByteArrayToAltBase64_EmptyArray() {
        byte[] input = new byte[0];
        String expectedOutput = "";
        assertEquals(expectedOutput, Base64.byteArrayToAltBase64(input));
    }
}