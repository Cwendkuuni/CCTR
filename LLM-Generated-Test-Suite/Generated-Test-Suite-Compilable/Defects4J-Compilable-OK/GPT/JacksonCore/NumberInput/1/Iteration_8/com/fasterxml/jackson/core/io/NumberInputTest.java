package com.fasterxml.jackson.core.io;

import static org.junit.Assert.*;
import org.junit.Test;
import com.fasterxml.jackson.core.io.NumberInput;
import java.math.BigDecimal;

public class NumberInputTest {

    @Test
    public void testParseIntCharArray() {
        assertEquals(123, NumberInput.parseInt(new char[]{'1', '2', '3'}, 0, 3));
        assertEquals(0, NumberInput.parseInt(new char[]{'0'}, 0, 1));
        assertEquals(987654321, NumberInput.parseInt(new char[]{'9', '8', '7', '6', '5', '4', '3', '2', '1'}, 0, 9));
    }

    @Test
    public void testParseIntString() {
        assertEquals(123, NumberInput.parseInt("123"));
        assertEquals(-123, NumberInput.parseInt("-123"));
        assertEquals(0, NumberInput.parseInt("0"));
        assertEquals(987654321, NumberInput.parseInt("987654321"));
    }

    @Test
    public void testParseLongCharArray() {
        assertEquals(12345678901L, NumberInput.parseLong(new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1'}, 0, 11));
    }

    @Test
    public void testParseLongString() {
        assertEquals(12345678901L, NumberInput.parseLong("12345678901"));
        assertEquals(-12345678901L, NumberInput.parseLong("-12345678901"));
    }

    @Test
    public void testInLongRangeCharArray() {
        assertTrue(NumberInput.inLongRange(new char[]{'9', '2', '2', '3', '3', '7', '2', '0', '3', '6', '8', '5', '4', '7', '7', '5', '8', '0', '7'}, 0, 19, false));
        assertFalse(NumberInput.inLongRange(new char[]{'9', '2', '2', '3', '3', '7', '2', '0', '3', '6', '8', '5', '4', '7', '7', '5', '8', '0', '8'}, 0, 19, false));
    }

    @Test
    public void testInLongRangeString() {
        assertTrue(NumberInput.inLongRange("9223372036854775807", false));
        assertFalse(NumberInput.inLongRange("9223372036854775808", false));
    }

    @Test
    public void testParseAsInt() {
        assertEquals(123, NumberInput.parseAsInt("123", 0));
        assertEquals(-123, NumberInput.parseAsInt("-123", 0));
        assertEquals(0, NumberInput.parseAsInt(null, 0));
        assertEquals(0, NumberInput.parseAsInt("", 0));
        assertEquals(123, NumberInput.parseAsInt("123.45", 0));
    }

    @Test
    public void testParseAsLong() {
        assertEquals(12345678901L, NumberInput.parseAsLong("12345678901", 0L));
        assertEquals(-12345678901L, NumberInput.parseAsLong("-12345678901", 0L));
        assertEquals(0L, NumberInput.parseAsLong(null, 0L));
        assertEquals(0L, NumberInput.parseAsLong("", 0L));
        assertEquals(123L, NumberInput.parseAsLong("123.45", 0L));
    }

    @Test
    public void testParseAsDouble() {
        assertEquals(123.45, NumberInput.parseAsDouble("123.45", 0.0), 0.0001);
        assertEquals(-123.45, NumberInput.parseAsDouble("-123.45", 0.0), 0.0001);
        assertEquals(0.0, NumberInput.parseAsDouble(null, 0.0), 0.0001);
        assertEquals(0.0, NumberInput.parseAsDouble("", 0.0), 0.0001);
    }

    @Test
    public void testParseDouble() {
        assertEquals(123.45, NumberInput.parseDouble("123.45"), 0.0001);
        assertEquals(Double.MIN_VALUE, NumberInput.parseDouble(NumberInput.NASTY_SMALL_DOUBLE), 0.0001);
    }

    @Test
    public void testParseBigDecimalString() {
        assertEquals(new BigDecimal("123.45"), NumberInput.parseBigDecimal("123.45"));
    }

    @Test
    public void testParseBigDecimalCharArray() {
        assertEquals(new BigDecimal("123.45"), NumberInput.parseBigDecimal(new char[]{'1', '2', '3', '.', '4', '5'}));
    }

    @Test
    public void testParseBigDecimalCharArrayWithOffset() {
        assertEquals(new BigDecimal("23.45"), NumberInput.parseBigDecimal(new char[]{'1', '2', '3', '.', '4', '5'}, 1, 5));
    }
}