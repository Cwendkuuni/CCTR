package org.apache.commons.math3.distribution;

import org.apache.commons.math3.distribution.HypergeometricDistribution;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.random.Well19937c;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HypergeometricDistributionTest {

    private HypergeometricDistribution distribution;

    @Before
    public void setUp() {
        distribution = new HypergeometricDistribution(100, 50, 10);
    }

    @Test(expected = NotStrictlyPositiveException.class)
    public void testConstructorPopulationSizeZero() {
        new HypergeometricDistribution(0, 10, 5);
    }

    @Test(expected = NotPositiveException.class)
    public void testConstructorNumberOfSuccessesNegative() {
        new HypergeometricDistribution(100, -1, 10);
    }

    @Test(expected = NotPositiveException.class)
    public void testConstructorSampleSizeNegative() {
        new HypergeometricDistribution(100, 10, -1);
    }

    @Test(expected = NumberIsTooLargeException.class)
    public void testConstructorNumberOfSuccessesTooLarge() {
        new HypergeometricDistribution(100, 101, 10);
    }

    @Test(expected = NumberIsTooLargeException.class)
    public void testConstructorSampleSizeTooLarge() {
        new HypergeometricDistribution(100, 10, 101);
    }

    @Test
    public void testGetNumberOfSuccesses() {
        assertEquals(50, distribution.getNumberOfSuccesses());
    }

    @Test
    public void testGetPopulationSize() {
        assertEquals(100, distribution.getPopulationSize());
    }

    @Test
    public void testGetSampleSize() {
        assertEquals(10, distribution.getSampleSize());
    }

    @Test
    public void testCumulativeProbability() {
        assertEquals(0.0, distribution.cumulativeProbability(-1), 1e-9);
        assertEquals(1.0, distribution.cumulativeProbability(50), 1e-9);
        assertTrue(distribution.cumulativeProbability(5) >= 0.0);
        assertTrue(distribution.cumulativeProbability(5) <= 1.0);
    }

    @Test
    public void testProbability() {
        assertEquals(0.0, distribution.probability(-1), 1e-9);
        assertEquals(0.0, distribution.probability(51), 1e-9);
        assertTrue(distribution.probability(5) >= 0.0);
        assertTrue(distribution.probability(5) <= 1.0);
    }

    @Test
    public void testUpperCumulativeProbability() {
        assertEquals(1.0, distribution.upperCumulativeProbability(-1), 1e-9);
        assertEquals(0.0, distribution.upperCumulativeProbability(51), 1e-9);
        assertTrue(distribution.upperCumulativeProbability(5) >= 0.0);
        assertTrue(distribution.upperCumulativeProbability(5) <= 1.0);
    }

    @Test
    public void testGetNumericalMean() {
        assertEquals(5.0, distribution.getNumericalMean(), 1e-9);
    }

    @Test
    public void testGetNumericalVariance() {
        assertTrue(distribution.getNumericalVariance() >= 0.0);
    }

    @Test
    public void testGetSupportLowerBound() {
        assertEquals(0, distribution.getSupportLowerBound());
    }

    @Test
    public void testGetSupportUpperBound() {
        assertEquals(10, distribution.getSupportUpperBound());
    }

    @Test
    public void testIsSupportConnected() {
        assertTrue(distribution.isSupportConnected());
    }
}