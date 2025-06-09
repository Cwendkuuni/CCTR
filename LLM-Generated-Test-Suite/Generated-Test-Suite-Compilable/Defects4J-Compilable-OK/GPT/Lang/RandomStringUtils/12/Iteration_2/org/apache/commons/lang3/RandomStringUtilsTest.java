package org.apache.commons.lang3;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

public class RandomStringUtilsTest {

    @Test
    public void testRandomStringLength() {
        assertEquals(5, RandomStringUtils.random(5).length());
        assertEquals(0, RandomStringUtils.random(0).length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomNegativeLength() {
        RandomStringUtils.random(-1);
    }

    @Test
    public void testRandomAsciiStringLength() {
        assertEquals(5, RandomStringUtils.randomAscii(5).length());
        assertEquals(0, RandomStringUtils.randomAscii(0).length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomAsciiNegativeLength() {
        RandomStringUtils.randomAscii(-1);
    }

    @Test
    public void testRandomAlphabeticStringLength() {
        assertEquals(5, RandomStringUtils.randomAlphabetic(5).length());
        assertEquals(0, RandomStringUtils.randomAlphabetic(0).length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomAlphabeticNegativeLength() {
        RandomStringUtils.randomAlphabetic(-1);
    }

    @Test
    public void testRandomAlphanumericStringLength() {
        assertEquals(5, RandomStringUtils.randomAlphanumeric(5).length());
        assertEquals(0, RandomStringUtils.randomAlphanumeric(0).length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomAlphanumericNegativeLength() {
        RandomStringUtils.randomAlphanumeric(-1);
    }

    @Test
    public void testRandomNumericStringLength() {
        assertEquals(5, RandomStringUtils.randomNumeric(5).length());
        assertEquals(0, RandomStringUtils.randomNumeric(0).length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomNumericNegativeLength() {
        RandomStringUtils.randomNumeric(-1);
    }

    @Test
    public void testRandomWithChars() {
        char[] chars = {'a', 'b', 'c'};
        String result = RandomStringUtils.random(5, chars);
        assertEquals(5, result.length());
        for (char c : result.toCharArray()) {
            assertTrue(new String(chars).indexOf(c) >= 0);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomWithEmptyChars() {
        RandomStringUtils.random(5, new char[]{});
    }

    @Test
    public void testRandomWithStringChars() {
        String chars = "abc";
        String result = RandomStringUtils.random(5, chars);
        assertEquals(5, result.length());
        for (char c : result.toCharArray()) {
            assertTrue(chars.indexOf(c) >= 0);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomWithEmptyStringChars() {
        RandomStringUtils.random(5, "");
    }

    @Test
    public void testRandomWithCustomRange() {
        String result = RandomStringUtils.random(5, 97, 123, true, false);
        assertEquals(5, result.length());
        for (char c : result.toCharArray()) {
            assertTrue(Character.isLetter(c));
        }
    }

    @Test
    public void testRandomWithCustomRangeAndRandom() {
        Random random = new Random();
        String result = RandomStringUtils.random(5, 97, 123, true, false, null, random);
        assertEquals(5, result.length());
        for (char c : result.toCharArray()) {
            assertTrue(Character.isLetter(c));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomWithNegativeCount() {
        RandomStringUtils.random(-1, 97, 123, true, false, null, new Random());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomWithEmptyCharArray() {
        RandomStringUtils.random(5, 0, 0, false, false, new char[]{}, new Random());
    }
}