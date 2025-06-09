package org.apache.commons.math3.fraction;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.text.*;
import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.fraction.ProperFractionFormat;
import org.apache.commons.math3.exception.NullArgumentException;

public class ProperFractionFormatTest {

    private ProperFractionFormat format;
    private NumberFormat numberFormat;

    @Before
    public void setUp() {
        numberFormat = NumberFormat.getInstance();
        format = new ProperFractionFormat(numberFormat);
    }

    @Test
    public void testConstructorWithDefaultFormat() {
        ProperFractionFormat defaultFormat = new ProperFractionFormat();
        assertNotNull(defaultFormat.getWholeFormat());
        assertNotNull(defaultFormat.getNumeratorFormat());
        assertNotNull(defaultFormat.getDenominatorFormat());
    }

    @Test
    public void testConstructorWithCustomFormats() {
        NumberFormat customFormat = NumberFormat.getInstance();
        ProperFractionFormat customProperFractionFormat = new ProperFractionFormat(customFormat, customFormat, customFormat);
        assertEquals(customFormat, customProperFractionFormat.getWholeFormat());
        assertEquals(customFormat, customProperFractionFormat.getNumeratorFormat());
        assertEquals(customFormat, customProperFractionFormat.getDenominatorFormat());
    }

    @Test
    public void testFormat() {
        Fraction fraction = new Fraction(7, 3); // 7/3 = 2 1/3
        StringBuffer buffer = new StringBuffer();
        FieldPosition pos = new FieldPosition(0);
        StringBuffer result = format.format(fraction, buffer, pos);
        assertEquals("2 1 / 3", result.toString());
    }

    @Test
    public void testFormatNegativeFraction() {
        Fraction fraction = new Fraction(-7, 3); // -7/3 = -2 1/3
        StringBuffer buffer = new StringBuffer();
        FieldPosition pos = new FieldPosition(0);
        StringBuffer result = format.format(fraction, buffer, pos);
        assertEquals("-2 1 / 3", result.toString());
    }

    @Test
    public void testParse() throws ParseException {
        String source = "2 1 / 3";
        ParsePosition pos = new ParsePosition(0);
        Fraction fraction = format.parse(source, pos);
        assertNotNull(fraction);
        assertEquals(7, fraction.getNumerator());
        assertEquals(3, fraction.getDenominator());
    }

    @Test
    public void testParseNegativeFraction() throws ParseException {
        String source = "-2 1 / 3";
        ParsePosition pos = new ParsePosition(0);
        Fraction fraction = format.parse(source, pos);
        assertNotNull(fraction);
        assertEquals(-7, fraction.getNumerator());
        assertEquals(3, fraction.getDenominator());
    }

    @Test
    public void testParseInvalidFormat() {
        String source = "invalid";
        ParsePosition pos = new ParsePosition(0);
        Fraction fraction = format.parse(source, pos);
        assertNull(fraction);
        assertEquals(0, pos.getIndex());
    }

    @Test
    public void testSetWholeFormat() {
        NumberFormat newFormat = NumberFormat.getInstance();
        format.setWholeFormat(newFormat);
        assertEquals(newFormat, format.getWholeFormat());
    }

    @Test(expected = NullArgumentException.class)
    public void testSetWholeFormatNull() {
        format.setWholeFormat(null);
    }
}