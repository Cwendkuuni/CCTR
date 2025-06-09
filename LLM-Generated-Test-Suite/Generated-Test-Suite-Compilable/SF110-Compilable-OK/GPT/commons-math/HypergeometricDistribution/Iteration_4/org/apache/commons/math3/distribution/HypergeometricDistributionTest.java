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
        distribution = new HypergeometricDistribution(100, 10, 5);
    }

    @Test(expected = NotStrictlyPositiveException.class)
    public void testConstructorPopulationSizeZero() {
        new HypergeometricDistribution(0, 10, 5);
    }

    @Test(expected = NotPositiveException.class)
    public void testConstructorNumberOfSuccessesNegative() {
        new HypergeometricDistribution(100, -1, 5);
    }

    @Test(expected = NotPositiveException.class)
    public void testConstructorSampleSizeNegative() {
        new HypergeometricDistribution(100, 10, -1);
    }

    @Test(expected = NumberIsTooLargeException.class)
    public void testConstructorNumberOfSuccessesTooLarge() {
        new HypergeometricDistribution(100, 101, 5);
    }

    @Test(expected = NumberIsTooLargeException.class)
    public void testConstructorSampleSizeTooLarge() {
        new HypergeometricDistribution(100, 10, 101);
    }

    @Test
    public void testGetNumberOfSuccesses() {
        assertEquals(10, distribution.getNumberOfSuccesses());
    }

    @Test
    public void testGetPopulationSize() {
        assertEquals(100, distribution.getPopulationSize());
    }

    @Test
    public void testGetSampleSize() {
        assertEquals(5, distribution.getSampleSize());
    }

    @Test
    public void testCumulativeProbability() {
        assertEquals(0.0, distribution.cumulativeProbability(-1), 1e-9);
        assertEquals(1.0, distribution.cumulativeProbability(10), 1e-9);
        assertTrue(distribution.cumulativeProbability(3) >= 0.0);
        assertTrue(distribution.cumulativeProbability(3) <= 1.0);
    }

    @Test
    public void testProbability() {
        assertEquals(0.0, distribution.probability(-1), 1e-9);
        assertEquals(0.0, distribution.probability(11), 1e-9);
        assertTrue(distribution.probability(3) >= 0.0);
        assertTrue(distribution.probability(3) <= 1.0);
    }

    @Test
    public void testUpperCumulativeProbability() {
        assertEquals(1.0, distribution.upperCumulativeProbability(-1), 1e-9);
        assertEquals(0.0, distribution.upperCumulativeProbability(11), 1e-9);
        assertTrue(distribution.upperCumulativeProbability(3) >= 0.0);
        assertTrue(distribution.upperCumulativeProbability(3) <= 1.0);
    }

    @Test
    public void testGetNumericalMean() {
        assertEquals(0.5, distribution.getNumericalMean(), 1e-9);
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
        assertEquals(5, distribution.getSupportUpperBound());
    }

    @Test
    public void testIsSupportConnected() {
        assertTrue(distribution.isSupportConnected());
    }
}