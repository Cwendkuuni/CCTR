package com.hf.sfm.crypt;

import org.junit.Test;
import static org.junit.Assert.*;

public class Base64Test {

    @Test
    public void testAltBase64ToByteArray() {
        String input = "!\"#$%&'()*,-.:;<>@[\\]^`_{|}~abcdefghijklmnopqrstuvwxyz0123456789+?";
        byte[] expected = new byte[64];
        for (int i = 0; i < 64; i++) {
            expected[i] = (byte) i;
        }
        byte[] result = Base64.altBase64ToByteArray(input);
        assertArrayEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltBase64ToByteArrayInvalidLength() {
        String input = "!\"#$%&'()*,-.:;<>@[\\]^`_{|}~abcdefghijklmnopqrstuvwxyz0123456789+?=";
        Base64.altBase64ToByteArray(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltBase64ToByteArrayInvalidCharacter() {
        String input = "!\"#$%&'()*,-.:;<>@[\\]^`_{|}~abcdefghijklmnopqrstuvwxyz0123456789+?X";
        Base64.altBase64ToByteArray(input);
    }

    @Test
    public void testBase64ToByteArray() {
        String input = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        byte[] expected = new byte[64];
        for (int i = 0; i < 64; i++) {
            expected[i] = (byte) i;
        }
        byte[] result = Base64.base64ToByteArray(input);
        assertArrayEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBase64ToByteArrayInvalidLength() {
        String input = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        Base64.base64ToByteArray(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBase64ToByteArrayInvalidCharacter() {
        String input = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/X";
        Base64.base64ToByteArray(input);
    }

    @Test
    public void testByteArrayToAltBase64() {
        byte[] input = new byte[64];
        for (int i = 0; i < 64; i++) {
            input[i] = (byte) i;
        }
        String expected = "!\"#$%&'()*,-.:;<>@[\\]^`_{|}~abcdefghijklmnopqrstuvwxyz0123456789+?";
        String result = Base64.byteArrayToAltBase64(input);
        assertEquals(expected, result);
    }

    @Test
    public void testByteArrayToBase64() {
        byte[] input = new byte[64];
        for (int i = 0; i < 64; i++) {
            input[i] = (byte) i;
        }
        String expected = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        String result = Base64.byteArrayToBase64(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMain() {
        String[] args = {};
        Base64.main(args);
        // This test is mainly to ensure that the main method runs without exceptions.
        // Additional assertions can be added based on the expected output.
    }
}