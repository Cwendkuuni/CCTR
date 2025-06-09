package org.apache.commons.lang3;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.util.Random;

public class RandomStringUtilsTest {

    private RandomStringUtils randomStringUtils;

    @Before
    public void setUp() {
        randomStringUtils = new RandomStringUtils();
    }

    @After
    public void tearDown() {
        randomStringUtils = null;
    }

    @Test
    public void testRandom() {
        int count = 10;
        String result = RandomStringUtils.random(count);
        assertEquals(count, result.length());
    }

    @Test
    public void testRandomAscii() {
        int count = 10;
        String result = RandomStringUtils.randomAscii(count);
        assertEquals(count, result.length());
        for (char c : result.toCharArray()) {
            assertTrue(c >= 32 && c <= 126);
        }
    }

    @Test
    public void testRandomAlphabetic() {
        int count = 10;
        String result = RandomStringUtils.randomAlphabetic(count);
        assertEquals(count, result.length());
        for (char c : result.toCharArray()) {
            assertTrue(Character.isLetter(c));
        }
    }

    @Test
    public void testRandomAlphanumeric() {
        int count = 10;
        String result = RandomStringUtils.randomAlphanumeric(count);
        assertEquals(count, result.length());
        for (char c : result.toCharArray()) {
            assertTrue(Character.isLetterOrDigit(c));
        }
    }

    @Test
    public void testRandomNumeric() {
        int count = 10;
        String result = RandomStringUtils.randomNumeric(count);
        assertEquals(count, result.length());
        for (char c : result.toCharArray()) {
            assertTrue(Character.isDigit(c));
        }
    }

    @Test
    public void testRandomWithLettersAndNumbers() {
        int count = 10;
        String result = RandomStringUtils.random(count, true, true);
        assertEquals(count, result.length());
        for (char c : result.toCharArray()) {
            assertTrue(Character.isLetterOrDigit(c));
        }
    }

    @Test
    public void testRandomWithStartEndLettersNumbers() {
        int count = 10;
        int start = 32;
        int end = 126;
        String result = RandomStringUtils.random(count, start, end, true, true);
        assertEquals(count, result.length());
        for (char c : result.toCharArray()) {
            assertTrue(c >= start && c <= end);
        }
    }

    @Test
    public void testRandomWithStartEndLettersNumbersChars() {
        int count = 10;
        int start = 0;
        int end = 0;
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        String result = RandomStringUtils.random(count, start, end, true, true, chars);
        assertEquals(count, result.length());
        for (char c : result.toCharArray()) {
            assertTrue(new String(chars).indexOf(c) != -1);
        }
    }

    @Test
    public void testRandomWithStartEndLettersNumbersCharsRandom() {
        int count = 10;
        int start = 0;
        int end = 0;
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        Random random = new Random();
        String result = RandomStringUtils.random(count, start, end, true, true, chars, random);
        assertEquals(count, result.length());
        for (char c : result.toCharArray()) {
            assertTrue(new String(chars).indexOf(c) != -1);
        }
    }

    @Test
    public void testRandomWithCharsString() {
        int count = 10;
        String chars = "abcdefghijklmnopqrstuvwxyz";
        String result = RandomStringUtils.random(count, chars);
        assertEquals(count, result.length());
        for (char c : result.toCharArray()) {
            assertTrue(chars.indexOf(c) != -1);
        }
    }

    @Test
    public void testRandomWithCharsArray() {
        int count = 10;
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        String result = RandomStringUtils.random(count, chars);
        assertEquals(count, result.length());
        for (char c : result.toCharArray()) {
            assertTrue(new String(chars).indexOf(c) != -1);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomWithNegativeCount() {
        RandomStringUtils.random(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomWithEmptyCharsString() {
        RandomStringUtils.random(10, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomWithEmptyCharsArray() {
        RandomStringUtils.random(10, new char[0]);
    }
}