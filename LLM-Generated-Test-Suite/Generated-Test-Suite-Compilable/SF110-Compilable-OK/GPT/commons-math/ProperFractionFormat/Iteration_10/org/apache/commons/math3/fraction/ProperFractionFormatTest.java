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
        ProperFractionFormat format = new ProperFractionFormat();
        assertNotNull(format.getWholeFormat());
        assertNotNull(format.getNumeratorFormat());
        assertNotNull(format.getDenominatorFormat());
    }

    @Test
    public void testConstructorWithCustomFormat() {
        ProperFractionFormat format = new ProperFractionFormat(numberFormat);
        assertEquals(numberFormat, format.getWholeFormat());
        assertEquals(numberFormat, format.getNumeratorFormat());
        assertEquals(numberFormat, format.getDenominatorFormat());
    }

    @Test
    public void testConstructorWithAllFormats() {
        NumberFormat wholeFormat = NumberFormat.getInstance();
        NumberFormat numeratorFormat = NumberFormat.getInstance();
        NumberFormat denominatorFormat = NumberFormat.getInstance();
        ProperFractionFormat format = new ProperFractionFormat(wholeFormat, numeratorFormat, denominatorFormat);
        assertEquals(wholeFormat, format.getWholeFormat());
        assertEquals(numeratorFormat, format.getNumeratorFormat());
        assertEquals(denominatorFormat, format.getDenominatorFormat());
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
    public void testParse() throws ParseException {
        String source = "2 1 / 3";
        ParsePosition pos = new ParsePosition(0);
        Fraction fraction = properFractionFormat.parse(source, pos);
        assertNotNull(fraction);
        assertEquals(7, fraction.getNumerator());
        assertEquals(3, fraction.getDenominator());
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

    @Test
    public void testParseInvalidFormat() {
        String source = "invalid";
        ParsePosition pos = new ParsePosition(0);
        Fraction fraction = properFractionFormat.parse(source, pos);
        assertNull(fraction);
        assertEquals(0, pos.getIndex());
        assertEquals(0, pos.getErrorIndex());
    }

    @Test(expected = NullArgumentException.class)
    public void testSetWholeFormatNull() {
        properFractionFormat.setWholeFormat(null);
    }

    @Test
    public void testSetWholeFormat() {
        NumberFormat newFormat = NumberFormat.getInstance();
        properFractionFormat.setWholeFormat(newFormat);
        assertEquals(newFormat, properFractionFormat.getWholeFormat());
    }
}