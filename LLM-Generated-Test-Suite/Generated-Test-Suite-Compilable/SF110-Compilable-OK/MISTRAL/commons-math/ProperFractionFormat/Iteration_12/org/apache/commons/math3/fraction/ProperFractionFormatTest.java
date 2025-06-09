package org.apache.commons.math3.fraction;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.DecimalFormat;
import org.apache.commons.math3.exception.NullArgumentException;

public class ProperFractionFormatTest {

    private ProperFractionFormat properFractionFormat;
    private NumberFormat defaultFormat;

    @Before
    public void setUp() {
        defaultFormat = NumberFormat.getInstance();
        properFractionFormat = new ProperFractionFormat();
    }

    @Test
    public void testDefaultConstructor() {
        ProperFractionFormat format = new ProperFractionFormat();
        assertNotNull(format);
    }

    @Test
    public void testConstructorWithFormat() {
        ProperFractionFormat format = new ProperFractionFormat(defaultFormat);
        assertNotNull(format);
    }

    @Test
    public void testConstructorWithAllFormats() {
        NumberFormat numeratorFormat = (NumberFormat) defaultFormat.clone();
        NumberFormat denominatorFormat = (NumberFormat) defaultFormat.clone();
        ProperFractionFormat format = new ProperFractionFormat(defaultFormat, numeratorFormat, denominatorFormat);
        assertNotNull(format);
    }

    @Test
    public void testFormat() {
        Fraction fraction = new Fraction(5, 3);
        StringBuffer result = properFractionFormat.format(fraction, new StringBuffer(), new java.text.FieldPosition(0));
        assertEquals("1 2 / 3", result.toString());
    }

    @Test
    public void testGetWholeFormat() {
        NumberFormat wholeFormat = properFractionFormat.getWholeFormat();
        assertNotNull(wholeFormat);
    }

    @Test
    public void testParseValidFraction() {
        String source = "1 2/3";
        ParsePosition pos = new ParsePosition(0);
        Fraction fraction = properFractionFormat.parse(source, pos);
        assertEquals(new Fraction(5, 3), fraction);
    }

    @Test
    public void testParseInvalidFraction() {
        String source = "1 2/-3";
        ParsePosition pos = new ParsePosition(0);
        Fraction fraction = properFractionFormat.parse(source, pos);
        assertNull(fraction);
    }

    @Test
    public void testSetWholeFormat() {
        NumberFormat newFormat = new DecimalFormat("#.##");
        properFractionFormat.setWholeFormat(newFormat);
        assertEquals(newFormat, properFractionFormat.getWholeFormat());
    }

    @Test(expected = NullArgumentException.class)
    public void testSetWholeFormatNull() {
        properFractionFormat.setWholeFormat(null);
    }
}