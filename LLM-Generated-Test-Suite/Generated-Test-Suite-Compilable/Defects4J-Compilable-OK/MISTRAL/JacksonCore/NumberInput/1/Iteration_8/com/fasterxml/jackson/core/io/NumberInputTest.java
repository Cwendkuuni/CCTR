package com.fasterxml.jackson.core.io;

import static org.junit.Assert.*;
import org.junit.Test;

import java.math.BigDecimal;

public class NumberInputTest {

    @Test
    public void testParseIntCharArray() {
        char[] chars = "123456789".toCharArray();
        assertEquals(123456789, NumberInput.parseInt(chars, 0, chars.length));

        chars = "987654321".toCharArray();
        assertEquals(987654321, NumberInput.parseInt(chars, 0, chars.length));

        chars = "0".toCharArray();
        assertEquals(0, NumberInput.parseInt(chars, 0, chars.length));
    }

    @Test
    public void testParseIntString() {
        assertEquals(123456789, NumberInput.parseInt("123456789"));
        assertEquals(-123456789, NumberInput.parseInt("-123456789"));
        assertEquals(0, NumberInput.parseInt("0"));
        assertEquals(Integer.MAX_VALUE, NumberInput.parseInt(String.valueOf(Integer.MAX_VALUE)));
        assertEquals(Integer.MIN_VALUE, NumberInput.parseInt(String.valueOf(Integer.MIN_VALUE)));
    }

    @Test
    public void testParseLongCharArray() {
        char[] chars = "123456789012345678".toCharArray();
        assertEquals(123456789012345678L, NumberInput.parseLong(chars, 0, chars.length));

        chars = "987654321098765432".toCharArray();
        assertEquals(987654321098765432L, NumberInput.parseLong(chars, 0, chars.length));
    }

    @Test
    public void testParseLongString() {
        assertEquals(123456789012345678L, NumberInput.parseLong("123456789012345678"));
        assertEquals(-123456789012345678L, NumberInput.parseLong("-123456789012345678"));
        assertEquals(Long.MAX_VALUE, NumberInput.parseLong(String.valueOf(Long.MAX_VALUE)));
        assertEquals(Long.MIN_VALUE, NumberInput.parseLong(String.valueOf(Long.MIN_VALUE)));
    }

    @Test
    public void testInLongRangeCharArray() {
        char[] chars = "9223372036854775807".toCharArray();
        assertTrue(NumberInput.inLongRange(chars, 0, chars.length, false));

        chars = "9223372036854775808".toCharArray();
        assertFalse(NumberInput.inLongRange(chars, 0, chars.length, false));

        chars = "9223372036854775807".toCharArray();
        assertTrue(NumberInput.inLongRange(chars, 0, chars.length, true));
    }

    @Test
    public void testInLongRangeString() {
        assertTrue(NumberInput.inLongRange("9223372036854775807", false));
        assertFalse(NumberInput.inLongRange("9223372036854775808", false));
        assertTrue(NumberInput.inLongRange("9223372036854775807", true));
    }

    @Test
    public void testParseAsInt() {
        assertEquals(123, NumberInput.parseAsInt("123", 0));
        assertEquals(-123, NumberInput.parseAsInt("-123", 0));
        assertEquals(0, NumberInput.parseAsInt("", 0));
        assertEquals(0, NumberInput.parseAsInt(null, 0));
        assertEquals(0, NumberInput.parseAsInt("abc", 0));
        assertEquals(123, NumberInput.parseAsInt("123.45", 0));
    }

    @Test
    public void testParseAsLong() {
        assertEquals(123456789012345678L, NumberInput.parseAsLong("123456789012345678", 0L));
        assertEquals(-123456789012345678L, NumberInput.parseAsLong("-123456789012345678", 0L));
        assertEquals(0L, NumberInput.parseAsLong("", 0L));
        assertEquals(0L, NumberInput.parseAsLong(null, 0L));
        assertEquals(0L, NumberInput.parseAsLong("abc", 0L));
        assertEquals(123456789012345678L, NumberInput.parseAsLong("123456789012345678.123", 0L));
    }

    @Test
    public void testParseAsDouble() {
        assertEquals(123.45, NumberInput.parseAsDouble("123.45", 0.0), 0.0001);
        assertEquals(-123.45, NumberInput.parseAsDouble("-123.45", 0.0), 0.0001);
        assertEquals(0.0, NumberInput.parseAsDouble("", 0.0), 0.0001);
        assertEquals(0.0, NumberInput.parseAsDouble(null, 0.0), 0.0001);
        assertEquals(0.0, NumberInput.parseAsDouble("abc", 0.0), 0.0001);
    }

    @Test
    public void testParseDouble() {
        assertEquals(123.45, NumberInput.parseDouble("123.45"), 0.0001);
        assertEquals(-123.45, NumberInput.parseDouble("-123.45"), 0.0001);
        assertEquals(Double.MIN_VALUE, NumberInput.parseDouble(NumberInput.NASTY_SMALL_DOUBLE), 0.0001);
    }

    @Test
    public void testParseBigDecimalString() {
        assertEquals(new BigDecimal("123.45"), NumberInput.parseBigDecimal("123.45"));
        assertEquals(new BigDecimal("-123.45"), NumberInput.parseBigDecimal("-123.45"));
    }

    @Test
    public void testParseBigDecimalCharArray() {
        char[] chars = "123.45".toCharArray();
        assertEquals(new BigDecimal("123.45"), NumberInput.parseBigDecimal(chars));

        chars = "-123.45".toCharArray();
        assertEquals(new BigDecimal("-123.45"), NumberInput.parseBigDecimal(chars));
    }

    @Test
    public void testParseBigDecimalCharArrayWithOffset() {
        char[] chars = "123.45".toCharArray();
        assertEquals(new BigDecimal("123.45"), NumberInput.parseBigDecimal(chars, 0, chars.length));

        chars = "-123.45".toCharArray();
        assertEquals(new BigDecimal("-123.45"), NumberInput.parseBigDecimal(chars, 0, chars.length));
    }
}