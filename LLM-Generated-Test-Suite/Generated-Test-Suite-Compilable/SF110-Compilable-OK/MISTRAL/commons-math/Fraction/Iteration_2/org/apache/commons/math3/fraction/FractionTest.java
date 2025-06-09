package org.apache.commons.math3.fraction;

import static org.junit.Assert.*;
import org.junit.Test;

public class FractionTest {

    @Test
    public void testFractionDouble() {
        try {
            Fraction f = new Fraction(0.5);
            assertEquals(1, f.getNumerator());
            assertEquals(2, f.getDenominator());
        } catch (FractionConversionException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testFractionDoubleEpsilonMaxIterations() {
        try {
            Fraction f = new Fraction(0.5, 0.01, 100);
            assertEquals(1, f.getNumerator());
            assertEquals(2, f.getDenominator());
        } catch (FractionConversionException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testFractionDoubleMaxDenominator() {
        try {
            Fraction f = new Fraction(0.5, 10);
            assertEquals(1, f.getNumerator());
            assertEquals(2, f.getDenominator());
        } catch (FractionConversionException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testFractionInt() {
        Fraction f = new Fraction(5);
        assertEquals(5, f.getNumerator());
        assertEquals(1, f.getDenominator());
    }

    @Test
    public void testFractionIntInt() {
        Fraction f = new Fraction(1, 2);
        assertEquals(1, f.getNumerator());
        assertEquals(2, f.getDenominator());
    }

    @Test
    public void testAbs() {
        Fraction f = new Fraction(-1, 2);
        assertEquals(new Fraction(1, 2), f.abs());
    }

    @Test
    public void testCompareTo() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 3);
        assertTrue(f1.compareTo(f2) > 0);
    }

    @Test
    public void testDoubleValue() {
        Fraction f = new Fraction(1, 2);
        assertEquals(0.5, f.doubleValue(), 0.0001);
    }

    @Test
    public void testEquals() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 2);
        assertTrue(f1.equals(f2));
    }

    @Test
    public void testFloatValue() {
        Fraction f = new Fraction(1, 2);
        assertEquals(0.5f, f.floatValue(), 0.0001f);
    }

    @Test
    public void testGetDenominator() {
        Fraction f = new Fraction(1, 2);
        assertEquals(2, f.getDenominator());
    }

    @Test
    public void testGetNumerator() {
        Fraction f = new Fraction(1, 2);
        assertEquals(1, f.getNumerator());
    }

    @Test
    public void testHashCode() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 2);
        assertEquals(f1.hashCode(), f2.hashCode());
    }

    @Test
    public void testIntValue() {
        Fraction f = new Fraction(1, 2);
        assertEquals(0, f.intValue());
    }

    @Test
    public void testLongValue() {
        Fraction f = new Fraction(1, 2);
        assertEquals(0L, f.longValue());
    }

    @Test
    public void testNegate() {
        Fraction f = new Fraction(1, 2);
        assertEquals(new Fraction(-1, 2), f.negate());
    }

    @Test
    public void testReciprocal() {
        Fraction f = new Fraction(1, 2);
        assertEquals(new Fraction(2, 1), f.reciprocal());
    }

    @Test
    public void testAddFraction() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 3);
        assertEquals(new Fraction(5, 6), f1.add(f2));
    }

    @Test
    public void testAddInt() {
        Fraction f = new Fraction(1, 2);
        assertEquals(new Fraction(3, 2), f.add(1));
    }

    @Test
    public void testSubtractFraction() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 3);
        assertEquals(new Fraction(1, 6), f1.subtract(f2));
    }

    @Test
    public void testSubtractInt() {
        Fraction f = new Fraction(1, 2);
        assertEquals(new Fraction(-1, 2), f.subtract(1));
    }

    @Test
    public void testMultiplyFraction() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 3);
        assertEquals(new Fraction(1, 6), f1.multiply(f2));
    }

    @Test
    public void testMultiplyInt() {
        Fraction f = new Fraction(1, 2);
        assertEquals(new Fraction(1, 1), f.multiply(2));
    }

    @Test
    public void testDivideFraction() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 3);
        assertEquals(new Fraction(3, 2), f1.divide(f2));
    }

    @Test
    public void testDivideInt() {
        Fraction f = new Fraction(1, 2);
        assertEquals(new Fraction(1, 4), f.divide(2));
    }

    @Test
    public void testPercentageValue() {
        Fraction f = new Fraction(1, 2);
        assertEquals(50.0, f.percentageValue(), 0.0001);
    }

    @Test
    public void testGetReducedFraction() {
        assertEquals(new Fraction(1, 2), Fraction.getReducedFraction(1, 2));
    }

    @Test
    public void testToString() {
        Fraction f = new Fraction(1, 2);
        assertEquals("1 / 2", f.toString());
    }

    @Test
    public void testGetField() {
        Fraction f = new Fraction(1, 2);
        assertNotNull(f.getField());
    }
}