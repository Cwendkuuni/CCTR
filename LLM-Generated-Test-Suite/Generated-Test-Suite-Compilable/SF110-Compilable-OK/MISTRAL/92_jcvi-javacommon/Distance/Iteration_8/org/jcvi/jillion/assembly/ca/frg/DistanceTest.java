package org.jcvi.jillion.assembly.ca.frg;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DistanceTest {

    private Distance distance;

    @Before
    public void setUp() {
        distance = Distance.buildDistance(1, 10, 5.5f, 1.5f);
    }

    @Test
    public void testBuildDistanceWithAllParams() {
        Distance d = Distance.buildDistance(1, 10, 5.5f, 1.5f);
        assertEquals(1, d.getMin());
        assertEquals(10, d.getMax());
        assertEquals(5.5f, d.getMean(), 0.001);
        assertEquals(1.5f, d.getStdDev(), 0.001);
    }

    @Test
    public void testBuildDistanceWithMeanAndStdDev() {
        Distance d = Distance.buildDistance(5.5f, 1.5f);
        assertEquals(2, d.getMin());
        assertEquals(9, d.getMax());
        assertEquals(5.5f, d.getMean(), 0.001);
        assertEquals(1.5f, d.getStdDev(), 0.001);
    }

    @Test
    public void testBuildDistanceWithMinAndMax() {
        Distance d = Distance.buildDistance(1, 10);
        assertEquals(1, d.getMin());
        assertEquals(10, d.getMax());
        assertEquals(5.5f, d.getMean(), 0.001);
        assertEquals(1.5f, d.getStdDev(), 0.001);
    }

    @Test
    public void testTransformIntoCeleraAssemblerDistance() {
        Distance d = Distance.buildDistance(1, 10, 5.5f, 1.5f);
        Distance transformed = Distance.transformIntoCeleraAssemblerDistance(d);
        assertEquals(1, transformed.getMin());
        assertEquals(10, transformed.getMax());
        assertEquals(5.5f, transformed.getMean(), 0.001);
        assertEquals(1.5f, transformed.getStdDev(), 0.001);

        Distance d2 = Distance.buildDistance(1, 10, 2.0f, 1.5f);
        Distance transformed2 = Distance.transformIntoCeleraAssemblerDistance(d2);
        assertEquals(1, transformed2.getMin());
        assertEquals(10, transformed2.getMax());
        assertEquals(2.0f, transformed2.getMean(), 0.001);
        assertEquals(0.32833334f, transformed2.getStdDev(), 0.001);
    }

    @Test
    public void testGetMean() {
        assertEquals(5.5f, distance.getMean(), 0.001);
    }

    @Test
    public void testGetStdDev() {
        assertEquals(1.5f, distance.getStdDev(), 0.001);
    }

    @Test
    public void testGetMin() {
        assertEquals(1, distance.getMin());
    }

    @Test
    public void testGetMax() {
        assertEquals(10, distance.getMax());
    }

    @Test
    public void testHashCode() {
        Distance d1 = Distance.buildDistance(1, 10, 5.5f, 1.5f);
        Distance d2 = Distance.buildDistance(1, 10, 5.5f, 1.5f);
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    public void testEquals() {
        Distance d1 = Distance.buildDistance(1, 10, 5.5f, 1.5f);
        Distance d2 = Distance.buildDistance(1, 10, 5.5f, 1.5f);
        assertTrue(d1.equals(d2));

        Distance d3 = Distance.buildDistance(2, 11, 6.5f, 1.5f);
        assertFalse(d1.equals(d3));
    }

    @Test
    public void testToString() {
        assertEquals("5.500, std: 1.500", distance.toString());
    }
}