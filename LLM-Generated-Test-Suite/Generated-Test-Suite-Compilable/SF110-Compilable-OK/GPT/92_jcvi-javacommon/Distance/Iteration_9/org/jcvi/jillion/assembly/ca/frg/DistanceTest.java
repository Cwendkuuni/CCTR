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
        assertEquals(22, distance.getMax());
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
        Distance distance = Distance.buildDistance(10, 20, 2.0f, 0.5f);
        Distance transformed = Distance.transformIntoCeleraAssemblerDistance(distance);
        assertEquals(10, transformed.getMin());
        assertEquals(20, transformed.getMax());
        assertEquals(2.0f, transformed.getMean(), 0.001);
        assertEquals(0.3283f, transformed.getStdDev(), 0.001);
    }

    @Test
    public void testTransformIntoCeleraAssemblerDistanceNoChange() {
        Distance distance = Distance.buildDistance(10, 20, 15.0f, 2.5f);
        Distance transformed = Distance.transformIntoCeleraAssemblerDistance(distance);
        assertEquals(distance, transformed);
    }

    @Test
    public void testEqualsAndHashCode() {
        Distance distance1 = Distance.buildDistance(10, 20, 15.0f, 2.5f);
        Distance distance2 = Distance.buildDistance(10, 20, 15.0f, 2.5f);
        Distance distance3 = Distance.buildDistance(10, 20, 14.0f, 2.5f);

        assertTrue(distance1.equals(distance2));
        assertEquals(distance1.hashCode(), distance2.hashCode());

        assertFalse(distance1.equals(distance3));
        assertNotEquals(distance1.hashCode(), distance3.hashCode());
    }

    @Test
    public void testToString() {
        Distance distance = Distance.buildDistance(10, 20, 15.0f, 2.5f);
        assertEquals("15.000, std: 2.500", distance.toString());
    }
}