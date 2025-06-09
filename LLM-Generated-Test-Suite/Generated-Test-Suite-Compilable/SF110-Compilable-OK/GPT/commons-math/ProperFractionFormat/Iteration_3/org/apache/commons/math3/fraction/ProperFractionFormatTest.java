package org.apache.commons.math3.fraction;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.text.*;
import org.apache.commons.math3.fraction.*;
import org.apache.commons.math3.exception.*;

public class ProperFractionFormatTest {

    private ProperFractionFormat properFractionFormat;
    private NumberFormat numberFormat;

    @Before
    public void setUp() {
        numberFormat = NumberFormat.getInstance();
        properFractionFormat = new ProperFractionFormat(numberFormat);
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
        NumberFormat customWholeFormat = NumberFormat.getInstance();
        NumberFormat customNumeratorFormat = NumberFormat.getInstance();
        NumberFormat customDenominatorFormat = NumberFormat.getInstance();
        ProperFractionFormat customFormat = new ProperFractionFormat(customWholeFormat, customNumeratorFormat, customDenominatorFormat);

        assertEquals(customWholeFormat, customFormat.getWholeFormat());
        assertEquals(customNumeratorFormat, customFormat.getNumeratorFormat());
        assertEquals(customDenominatorFormat, customFormat.getDenominatorFormat());
    }

    @Test
    public void testFormat() {
        Fraction fraction = new Fraction(7, 3);
        StringBuffer buffer = new StringBuffer();
        FieldPosition pos = new FieldPosition(0);

        StringBuffer result = properFractionFormat.format(fraction, buffer, pos);
        assertEquals("2 1 / 3", result.toString());
    }

    @Test
    public void testFormatNegativeFraction() {
        Fraction fraction = new Fraction(-7, 3);
        StringBuffer buffer = new StringBuffer();
        FieldPosition pos = new FieldPosition(0);

        StringBuffer result = properFractionFormat.format(fraction, buffer, pos);
        assertEquals("-2 1 / 3", result.toString());
    }

    @Test
    public void testParseValidFraction() throws ParseException {
        String source = "2 1 / 3";
        ParsePosition pos = new ParsePosition(0);

        Fraction fraction = properFractionFormat.parse(source, pos);
        assertNotNull(fraction);
        assertEquals(7, fraction.getNumerator());
        assertEquals(3, fraction.getDenominator());
    }

    @Test
    public void testParseInvalidFraction() {
        String source = "2 1 /";
        ParsePosition pos = new ParsePosition(0);

        Fraction fraction = properFractionFormat.parse(source, pos);
        assertNull(fraction);
        assertEquals(0, pos.getIndex());
    }

    @Test
    public void testParseNegativeFraction() throws ParseException {
        String source = "-2 1 / 3";
        ParsePosition pos = new ParsePosition(0);

        Fraction fraction = properFractionFormat.parse(source, pos);
        assertNotNull(fraction);
        assertEquals(-7, fraction.getNumerator());
        assertEquals(3, fraction.getDenominator());
    }

    @Test(expected = NullArgumentException.class)
    public void testSetWholeFormatWithNull() {
        properFractionFormat.setWholeFormat(null);
    }

    @Test
    public void testSetWholeFormat() {
        NumberFormat newWholeFormat = NumberFormat.getInstance();
        properFractionFormat.setWholeFormat(newWholeFormat);
        assertEquals(newWholeFormat, properFractionFormat.getWholeFormat());
    }
}