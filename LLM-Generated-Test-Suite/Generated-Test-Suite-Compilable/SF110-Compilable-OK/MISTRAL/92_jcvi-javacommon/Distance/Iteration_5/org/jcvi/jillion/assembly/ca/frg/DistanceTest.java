package org.jcvi.jillion.assembly.ca.frg;

import static org.junit.Assert.*;
import org.junit.Test;

public class DistanceTest {

    @Test
    public void testBuildDistanceWithAllParameters() {
        Distance distance = Distance.buildDistance(1, 10, 5.5f, 2.0f);
        assertEquals(1, distance.getMin());
        assertEquals(10, distance.getMax());
        assertEquals(5.5f, distance.getMean(), 0.001);
        assertEquals(2.0f, distance.getStdDev(), 0.001);
    }

    @Test
    public void testBuildDistanceWithMeanAndStdDev() {
        Distance distance = Distance.buildDistance(5.5f, 2.0f);
        assertEquals(0, distance.getMin());
        assertEquals(11, distance.getMax());
        assertEquals(5.5f, distance.getMean(), 0.001);
        assertEquals(2.0f, distance.getStdDev(), 0.001);
    }

    @Test
    public void testBuildDistanceWithMinAndMax() {
        Distance distance = Distance.buildDistance(1, 10);
        assertEquals(1, distance.getMin());
        assertEquals(10, distance.getMax());
        assertEquals(5.5f, distance.getMean(), 0.001);
        assertEquals(1.5f, distance.getStdDev(), 0.001);
    }

    @Test
    public void testTransformIntoCeleraAssemblerDistance() {
        Distance original = Distance.buildDistance(1, 10, 5.5f, 2.0f);
        Distance transformed = Distance.transformIntoCeleraAssemblerDistance(original);
        assertEquals(1, transformed.getMin());
        assertEquals(10, transformed.getMax());
        assertEquals(5.5f, transformed.getMean(), 0.001);
        assertEquals(1.485f, transformed.getStdDev(), 0.001);
    }

    @Test
    public void testGetMean() {
        Distance distance = Distance.buildDistance(1, 10, 5.5f, 2.0f);
        assertEquals(5.5f, distance.getMean(), 0.001);
    }

    @Test
    public void testGetStdDev() {
        Distance distance = Distance.buildDistance(1, 10, 5.5f, 2.0f);
        assertEquals(2.0f, distance.getStdDev(), 0.001);
    }

    @Test
    public void testGetMin() {
        Distance distance = Distance.buildDistance(1, 10, 5.5f, 2.0f);
        assertEquals(1, distance.getMin());
    }

    @Test
    public void testGetMax() {
        Distance distance = Distance.buildDistance(1, 10, 5.5f, 2.0f);
        assertEquals(10, distance.getMax());
    }

    @Test
    public void testHashCode() {
        Distance distance1 = Distance.buildDistance(1, 10, 5.5f, 2.0f);
        Distance distance2 = Distance.buildDistance(1, 10, 5.5f, 2.0f);
        assertEquals(distance1.hashCode(), distance2.hashCode());
    }

    @Test
    public void testEquals() {
        Distance distance1 = Distance.buildDistance(1, 10, 5.5f, 2.0f);
        Distance distance2 = Distance.buildDistance(1, 10, 5.5f, 2.0f);
        assertTrue(distance1.equals(distance2));
    }

    @Test
    public void testToString() {
        Distance distance = Distance.buildDistance(1, 10, 5.5f, 2.0f);
        assertEquals("5.500, std: 2.000", distance.toString());
    }
}