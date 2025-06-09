package org.apache.commons.math3.fraction;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.text.*;
import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.fraction.ProperFractionFormat;
import org.apache.commons.math3.exception.NullArgumentException;

public class ProperFractionFormatTest {

    private ProperFractionFormat defaultFormat;
    private ProperFractionFormat customFormat;
    private NumberFormat customNumberFormat;

    @Before
    public void setUp() {
        defaultFormat = new ProperFractionFormat();
        customNumberFormat = NumberFormat.getInstance();
        customFormat = new ProperFractionFormat(customNumberFormat);
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(defaultFormat.getWholeFormat());
        assertNotNull(defaultFormat.getNumeratorFormat());
        assertNotNull(defaultFormat.getDenominatorFormat());
    }

    @Test
    public void testCustomConstructor() {
        assertEquals(customNumberFormat, customFormat.getWholeFormat());
        assertEquals(customNumberFormat, customFormat.getNumeratorFormat());
        assertEquals(customNumberFormat, customFormat.getDenominatorFormat());
    }

    @Test
    public void testFormat() {
        Fraction fraction = new Fraction(7, 3); // 2 1/3
        StringBuffer buffer = new StringBuffer();
        FieldPosition pos = new FieldPosition(0);
        String result = defaultFormat.format(fraction, buffer, pos).toString();
        assertEquals("2 1 / 3", result);
    }

    @Test
    public void testFormatNegativeFraction() {
        Fraction fraction = new Fraction(-7, 3); // -2 1/3
        StringBuffer buffer = new StringBuffer();
        FieldPosition pos = new FieldPosition(0);
        String result = defaultFormat.format(fraction, buffer, pos).toString();
        assertEquals("-2 1 / 3", result);
    }

    @Test
    public void testParse() throws ParseException {
        String source = "2 1 / 3";
        ParsePosition pos = new ParsePosition(0);
        Fraction fraction = defaultFormat.parse(source, pos);
        assertNotNull(fraction);
        assertEquals(7, fraction.getNumerator());
        assertEquals(3, fraction.getDenominator());
    }

    @Test
    public void testParseNegative() throws ParseException {
        String source = "-2 1 / 3";
        ParsePosition pos = new ParsePosition(0);
        Fraction fraction = defaultFormat.parse(source, pos);
        assertNotNull(fraction);
        assertEquals(-7, fraction.getNumerator());
        assertEquals(3, fraction.getDenominator());
    }

    @Test
    public void testParseInvalid() {
        String source = "invalid";
        ParsePosition pos = new ParsePosition(0);
        Fraction fraction = defaultFormat.parse(source, pos);
        assertNull(fraction);
    }

    @Test
    public void testSetWholeFormat() {
        NumberFormat newFormat = NumberFormat.getInstance();
        defaultFormat.setWholeFormat(newFormat);
        assertEquals(newFormat, defaultFormat.getWholeFormat());
    }

    @Test(expected = NullArgumentException.class)
    public void testSetWholeFormatNull() {
        defaultFormat.setWholeFormat(null);
    }
}