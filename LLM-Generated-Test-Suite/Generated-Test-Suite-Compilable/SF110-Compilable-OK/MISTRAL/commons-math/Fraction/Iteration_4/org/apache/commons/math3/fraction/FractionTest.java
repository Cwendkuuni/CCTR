package org.apache.commons.math3.fraction;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class FractionTest {

    private Fraction fraction1;
    private Fraction fraction2;
    private Fraction fraction3;

    @Before
    public void setUp() {
        fraction1 = new Fraction(3, 4);
        fraction2 = new Fraction(1, 2);
        fraction3 = new Fraction(5, 6);
    }

    @Test
    public void testConstructorWithDouble() throws FractionConversionException {
        Fraction fraction = new Fraction(0.75);
        assertEquals(3, fraction.getNumerator());
        assertEquals(4, fraction.getDenominator());
    }

    @Test
    public void testConstructorWithDoubleEpsilonMaxIterations() throws FractionConversionException {
        Fraction fraction = new Fraction(0.75, 0.01, 100);
        assertEquals(3, fraction.getNumerator());
        assertEquals(4, fraction.getDenominator());
    }

    @Test
    public void testConstructorWithDoubleMaxDenominator() throws FractionConversionException {
        Fraction fraction = new Fraction(0.75, 100);
        assertEquals(3, fraction.getNumerator());
        assertEquals(4, fraction.getDenominator());
    }

    @Test
    public void testConstructorWithInt() {
        Fraction fraction = new Fraction(5);
        assertEquals(5, fraction.getNumerator());
        assertEquals(1, fraction.getDenominator());
    }

    @Test
    public void testConstructorWithIntInt() {
        Fraction fraction = new Fraction(3, 4);
        assertEquals(3, fraction.getNumerator());
        assertEquals(4, fraction.getDenominator());
    }

    @Test
    public void testAbs() {
        Fraction fraction = new Fraction(-3, 4);
        assertEquals(new Fraction(3, 4), fraction.abs());
    }

    @Test
    public void testCompareTo() {
        assertTrue(fraction1.compareTo(fraction2) > 0);
        assertTrue(fraction2.compareTo(fraction3) < 0);
        assertTrue(fraction1.compareTo(fraction1) == 0);
    }

    @Test
    public void testDoubleValue() {
        assertEquals(0.75, fraction1.doubleValue(), 0.0001);
    }

    @Test
    public void testEquals() {
        assertTrue(fraction1.equals(new Fraction(3, 4)));
        assertFalse(fraction1.equals(fraction2));
    }

    @Test
    public void testFloatValue() {
        assertEquals(0.75f, fraction1.floatValue(), 0.0001f);
    }

    @Test
    public void testGetDenominator() {
        assertEquals(4, fraction1.getDenominator());
    }

    @Test
    public void testGetNumerator() {
        assertEquals(3, fraction1.getNumerator());
    }

    @Test
    public void testHashCode() {
        assertEquals(fraction1.hashCode(), new Fraction(3, 4).hashCode());
    }

    @Test
    public void testIntValue() {
        assertEquals(0, fraction1.intValue());
    }

    @Test
    public void testLongValue() {
        assertEquals(0L, fraction1.longValue());
    }

    @Test
    public void testNegate() {
        assertEquals(new Fraction(-3, 4), fraction1.negate());
    }

    @Test
    public void testReciprocal() {
        assertEquals(new Fraction(4, 3), fraction1.reciprocal());
    }

    @Test
    public void testAddFraction() {
        assertEquals(new Fraction(11, 8), fraction1.add(fraction2));
    }

    @Test
    public void testAddInt() {
        assertEquals(new Fraction(19, 4), fraction1.add(2));
    }

    @Test
    public void testSubtractFraction() {
        assertEquals(new Fraction(1, 8), fraction1.subtract(fraction2));
    }

    @Test
    public void testSubtractInt() {
        assertEquals(new Fraction(-5, 4), fraction1.subtract(2));
    }

    @Test
    public void testMultiplyFraction() {
        assertEquals(new Fraction(3, 8), fraction1.multiply(fraction2));
    }

    @Test
    public void testMultiplyInt() {
        assertEquals(new Fraction(6, 4), fraction1.multiply(2));
    }

    @Test
    public void testDivideFraction() {
        assertEquals(new Fraction(9, 8), fraction1.divide(fraction2));
    }

    @Test
    public void testDivideInt() {
        assertEquals(new Fraction(3, 8), fraction1.divide(2));
    }

    @Test
    public void testPercentageValue() {
        assertEquals(75.0, fraction1.percentageValue(), 0.0001);
    }

    @Test
    public void testGetReducedFraction() {
        assertEquals(new Fraction(3, 4), Fraction.getReducedFraction(6, 8));
    }

    @Test
    public void testToString() {
        assertEquals("3 / 4", fraction1.toString());
    }

    @Test
    public void testGetField() {
        assertNotNull(fraction1.getField());
    }
}