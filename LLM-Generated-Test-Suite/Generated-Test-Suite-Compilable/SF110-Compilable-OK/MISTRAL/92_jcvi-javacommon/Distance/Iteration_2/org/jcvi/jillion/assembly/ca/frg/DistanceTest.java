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
        assertEquals(2, distance.getMin());
        assertEquals(9, distance.getMax());
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
        Distance distance = Distance.buildDistance(1, 10, 5.5f, 2.0f);
        Distance transformedDistance = Distance.transformIntoCeleraAssemblerDistance(distance);
        assertEquals(1, transformedDistance.getMin());
        assertEquals(10, transformedDistance.getMax());
        assertEquals(5.5f, transformedDistance.getMean(), 0.001);
        assertEquals(2.0f, transformedDistance.getStdDev(), 0.001);

        Distance smallMeanDistance = Distance.buildDistance(1, 10, 2.0f, 2.0f);
        Distance transformedSmallMeanDistance = Distance.transformIntoCeleraAssemblerDistance(smallMeanDistance);
        assertEquals(1, transformedSmallMeanDistance.getMin());
        assertEquals(10, transformedSmallMeanDistance.getMax());
        assertEquals(2.0f, transformedSmallMeanDistance.getMean(), 0.001);
        assertEquals(0.32833334f, transformedSmallMeanDistance.getStdDev(), 0.001);
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

        Distance distance3 = Distance.buildDistance(2, 11, 6.5f, 2.5f);
        assertFalse(distance1.equals(distance3));
    }

    @Test
    public void testToString() {
        Distance distance = Distance.buildDistance(1, 10, 5.5f, 2.0f);
        assertEquals("5.500, std: 2.000", distance.toString());
    }
}