package com.hf.sfm.crypt;

import org.junit.Test;
import static org.junit.Assert.*;

public class Base64Test {

    @Test
    public void testAltBase64ToByteArray() {
        String encoded = "!\"#$%&'()*,-.:;<>@[\\]^`_{|}~abcdefghijklmnopqrstuvwxyz0123456789+?";
        byte[] expected = new byte[64];
        for (int i = 0; i < 64; i++) {
            expected[i] = (byte) i;
        }
        byte[] actual = Base64.altBase64ToByteArray(encoded);
        assertArrayEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltBase64ToByteArrayInvalidLength() {
        String invalid = "!\"#$%&'()*,-.:;<>@[\\]^`_{|}~abcdefghijklmnopqrstuvwxyz0123456789+?=";
        Base64.altBase64ToByteArray(invalid);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltBase64ToByteArrayInvalidCharacter() {
        String invalid = "!\"#$%&'()*,-.:;<>@[\\]^`_{|}~abcdefghijklmnopqrstuvwxyz0123456789+?X";
        Base64.altBase64ToByteArray(invalid);
    }

    @Test
    public void testBase64ToByteArray() {
        String encoded = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        byte[] expected = new byte[64];
        for (int i = 0; i < 64; i++) {
            expected[i] = (byte) i;
        }
        byte[] actual = Base64.base64ToByteArray(encoded);
        assertArrayEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBase64ToByteArrayInvalidLength() {
        String invalid = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        Base64.base64ToByteArray(invalid);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBase64ToByteArrayInvalidCharacter() {
        String invalid = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/X";
        Base64.base64ToByteArray(invalid);
    }

    @Test
    public void testByteArrayToAltBase64() {
        byte[] data = new byte[64];
        for (int i = 0; i < 64; i++) {
            data[i] = (byte) i;
        }
        String expected = "!\"#$%&'()*,-.:;<>@[\\]^`_{|}~abcdefghijklmnopqrstuvwxyz0123456789+?";
        String actual = Base64.byteArrayToAltBase64(data);
        assertEquals(expected, actual);
    }

    @Test
    public void testByteArrayToBase64() {
        byte[] data = new byte[64];
        for (int i = 0; i < 64; i++) {
            data[i] = (byte) i;
        }
        String expected = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        String actual = Base64.byteArrayToBase64(data);
        assertEquals(expected, actual);
    }

    @Test
    public void testMain() {
        String[] args = {};
        Base64.main(args);
        // This test is mainly to ensure that the main method runs without exceptions
    }
}