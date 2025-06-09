package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.math.MathPreconditions;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class BigIntegerMathTest {

    @Before
    public void setUp() {
        // Set up any necessary preconditions for the tests
    }

    @After
    public void tearDown() {
        // Clean up after tests if necessary
    }

    @Test
    public void testIsPowerOfTwo() {
        assertTrue(BigIntegerMath.isPowerOfTwo(BigInteger.ONE));
        assertTrue(BigIntegerMath.isPowerOfTwo(BigInteger.valueOf(2)));
        assertTrue(BigIntegerMath.isPowerOfTwo(BigInteger.valueOf(4)));
        assertFalse(BigIntegerMath.isPowerOfTwo(BigInteger.valueOf(3)));
        assertFalse(BigIntegerMath.isPowerOfTwo(BigInteger.valueOf(5)));
    }

    @Test(expected = NullPointerException.class)
    public void testIsPowerOfTwoNull() {
        BigIntegerMath.isPowerOfTwo(null);
    }

    @Test
    public void testLog2() {
        assertEquals(0, BigIntegerMath.log2(BigInteger.ONE, RoundingMode.FLOOR));
        assertEquals(1, BigIntegerMath.log2(BigInteger.valueOf(2), RoundingMode.FLOOR));
        assertEquals(2, BigIntegerMath.log2(BigInteger.valueOf(4), RoundingMode.FLOOR));
        assertEquals(3, BigIntegerMath.log2(BigInteger.valueOf(5), RoundingMode.CEILING));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLog2Zero() {
        BigIntegerMath.log2(BigInteger.ZERO, RoundingMode.FLOOR);
    }

    @Test(expected = NullPointerException.class)
    public void testLog2Null() {
        BigIntegerMath.log2(null, RoundingMode.FLOOR);
    }

    @Test
    public void testLog10() {
        assertEquals(0, BigIntegerMath.log10(BigInteger.ONE, RoundingMode.FLOOR));
        assertEquals(1, BigIntegerMath.log10(BigInteger.TEN, RoundingMode.FLOOR));
        assertEquals(2, BigIntegerMath.log10(BigInteger.valueOf(100), RoundingMode.FLOOR));
        assertEquals(3, BigIntegerMath.log10(BigInteger.valueOf(1000), RoundingMode.CEILING));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLog10Zero() {
        BigIntegerMath.log10(BigInteger.ZERO, RoundingMode.FLOOR);
    }

    @Test(expected = NullPointerException.class)
    public void testLog10Null() {
        BigIntegerMath.log10(null, RoundingMode.FLOOR);
    }

    @Test
    public void testSqrt() {
        assertEquals(BigInteger.ONE, BigIntegerMath.sqrt(BigInteger.ONE, RoundingMode.FLOOR));
        assertEquals(BigInteger.valueOf(2), BigIntegerMath.sqrt(BigInteger.valueOf(4), RoundingMode.FLOOR));
        assertEquals(BigInteger.valueOf(3), BigIntegerMath.sqrt(BigInteger.valueOf(9), RoundingMode.FLOOR));
        assertEquals(BigInteger.valueOf(4), BigIntegerMath.sqrt(BigInteger.valueOf(16), RoundingMode.CEILING));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSqrtNegative() {
        BigIntegerMath.sqrt(BigInteger.valueOf(-1), RoundingMode.FLOOR);
    }

    @Test(expected = NullPointerException.class)
    public void testSqrtNull() {
        BigIntegerMath.sqrt(null, RoundingMode.FLOOR);
    }

    @Test
    public void testDivide() {
        assertEquals(BigInteger.valueOf(2), BigIntegerMath.divide(BigInteger.valueOf(10), BigInteger.valueOf(5), RoundingMode.FLOOR));
        assertEquals(BigInteger.valueOf(3), BigIntegerMath.divide(BigInteger.valueOf(10), BigInteger.valueOf(3), RoundingMode.CEILING));
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideByZero() {
        BigIntegerMath.divide(BigInteger.ONE, BigInteger.ZERO, RoundingMode.FLOOR);
    }

    @Test(expected = NullPointerException.class)
    public void testDivideNull() {
        BigIntegerMath.divide(null, BigInteger.ONE, RoundingMode.FLOOR);
    }

    @Test
    public void testFactorial() {
        assertEquals(BigInteger.ONE, BigIntegerMath.factorial(0));
        assertEquals(BigInteger.ONE, BigIntegerMath.factorial(1));
        assertEquals(BigInteger.valueOf(2), BigIntegerMath.factorial(2));
        assertEquals(BigInteger.valueOf(6), BigIntegerMath.factorial(3));
        assertEquals(BigInteger.valueOf(24), BigIntegerMath.factorial(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFactorialNegative() {
        BigIntegerMath.factorial(-1);
    }

    @Test
    public void testBinomial() {
        assertEquals(BigInteger.ONE, BigIntegerMath.binomial(1, 0));
        assertEquals(BigInteger.ONE, BigIntegerMath.binomial(1, 1));
        assertEquals(BigInteger.valueOf(2), BigIntegerMath.binomial(2, 1));
        assertEquals(BigInteger.valueOf(6), BigIntegerMath.binomial(4, 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBinomialNegative() {
        BigIntegerMath.binomial(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBinomialKGreaterThanN() {
        BigIntegerMath.binomial(1, 2);
    }

    @Test
    public void testFitsInLong() {
        assertTrue(BigIntegerMath.fitsInLong(BigInteger.valueOf(Long.MAX_VALUE)));
        assertFalse(BigIntegerMath.fitsInLong(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE)));
    }

    @Test
    public void testListProduct() {
        List<BigInteger> nums = new ArrayList<>();
        nums.add(BigInteger.ONE);
        nums.add(BigInteger.valueOf(2));
        nums.add(BigInteger.valueOf(3));
        assertEquals(BigInteger.valueOf(6), BigIntegerMath.listProduct(nums));
    }

    @Test
    public void testListProductEmpty() {
        List<BigInteger> nums = new ArrayList<>();
        assertEquals(BigInteger.ONE, BigIntegerMath.listProduct(nums));
    }
}