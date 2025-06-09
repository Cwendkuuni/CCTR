package org.jcvi.jillion.assembly.ca.frg;

import static org.junit.Assert.*;
import org.junit.Test;
import org.jcvi.jillion.assembly.ca.frg.Distance;

public class DistanceTest {

    @Test
    public void testBuildDistanceWithMinMaxMeanStdDev() {
        Distance distance = Distance.buildDistance(10, 20, 15.0f, 2.5f);
        assertEquals(10, distance.getMin());
        assertEquals(20, distance.getMax());
        assertEquals(15.0f, distance.getMean(), 0.001);
        assertEquals(2.5f, distance.getStdDev(), 0.001);
    }

    @Test
    public void testBuildDistanceWithMeanStdDev() {
        Distance distance = Distance.buildDistance(15.0f, 2.5f);
        assertEquals(7, distance.getMin());
        assertEquals(17, distance.getMax());
        assertEquals(15.0f, distance.getMean(), 0.001);
        assertEquals(2.5f, distance.getStdDev(), 0.001);
    }

    @Test
    public void testBuildDistanceWithMinMax() {
        Distance distance = Distance.buildDistance(10, 20);
        assertEquals(10, distance.getMin());
        assertEquals(20, distance.getMax());
        assertEquals(15.0f, distance.getMean(), 0.001);
        assertEquals(1.6667f, distance.getStdDev(), 0.001);
    }

    @Test
    public void testTransformIntoCeleraAssemblerDistance() {
        Distance distance = Distance.buildDistance(15.0f, 2.5f);
        Distance transformed = Distance.transformIntoCeleraAssemblerDistance(distance);
        assertEquals(distance, transformed);

        Distance smallMeanDistance = Distance.buildDistance(2.0f, 0.5f);
        Distance transformedSmallMean = Distance.transformIntoCeleraAssemblerDistance(smallMeanDistance);
        assertEquals(smallMeanDistance.getMin(), transformedSmallMean.getMin());
        assertEquals(smallMeanDistance.getMax(), transformedSmallMean.getMax());
        assertEquals(smallMeanDistance.getMean(), transformedSmallMean.getMean(), 0.001);
        assertEquals(0.3283f, transformedSmallMean.getStdDev(), 0.001);
    }

    @Test
    public void testEqualsAndHashCode() {
        Distance distance1 = Distance.buildDistance(10, 20, 15.0f, 2.5f);
        Distance distance2 = Distance.buildDistance(10, 20, 15.0f, 2.5f);
        Distance distance3 = Distance.buildDistance(10, 20, 15.0f, 3.0f);

        assertTrue(distance1.equals(distance2));
        assertFalse(distance1.equals(distance3));
        assertFalse(distance1.equals(null));
        assertFalse(distance1.equals(new Object()));

        assertEquals(distance1.hashCode(), distance2.hashCode());
        assertNotEquals(distance1.hashCode(), distance3.hashCode());
    }

    @Test
    public void testToString() {
        Distance distance = Distance.buildDistance(10, 20, 15.0f, 2.5f);
        assertEquals("15.000, std: 2.500", distance.toString());
    }
}