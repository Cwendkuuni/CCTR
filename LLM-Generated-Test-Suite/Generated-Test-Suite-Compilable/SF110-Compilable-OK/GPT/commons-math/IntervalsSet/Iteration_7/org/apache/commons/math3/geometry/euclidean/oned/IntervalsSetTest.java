package org.apache.commons.math3.geometry.euclidean.oned;

import static org.junit.Assert.*;
import org.junit.Test;
import org.apache.commons.math3.geometry.euclidean.oned.*;
import org.apache.commons.math3.geometry.partitioning.BSPTree;
import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
import java.util.ArrayList;
import java.util.List;

public class IntervalsSetTest {

    @Test
    public void testDefaultConstructor() {
        IntervalsSet intervalsSet = new IntervalsSet();
        assertNotNull(intervalsSet);
        assertTrue(intervalsSet.asList().isEmpty());
    }

    @Test
    public void testConstructorWithBounds() {
        IntervalsSet intervalsSet = new IntervalsSet(1.0, 5.0);
        List<Interval> intervals = intervalsSet.asList();
        assertEquals(1, intervals.size());
        assertEquals(1.0, intervals.get(0).getInf(), 1e-10);
        assertEquals(5.0, intervals.get(0).getSup(), 1e-10);
    }

    @Test
    public void testConstructorWithInfiniteBounds() {
        IntervalsSet intervalsSet = new IntervalsSet(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        List<Interval> intervals = intervalsSet.asList();
        assertEquals(1, intervals.size());
        assertEquals(Double.NEGATIVE_INFINITY, intervals.get(0).getInf(), 1e-10);
        assertEquals(Double.POSITIVE_INFINITY, intervals.get(0).getSup(), 1e-10);
    }

    @Test
    public void testConstructorWithTree() {
        BSPTree<Euclidean1D> tree = new BSPTree<>(Boolean.TRUE);
        IntervalsSet intervalsSet = new IntervalsSet(tree);
        assertNotNull(intervalsSet);
    }

    @Test
    public void testConstructorWithBoundary() {
        List<SubHyperplane<Euclidean1D>> boundary = new ArrayList<>();
        IntervalsSet intervalsSet = new IntervalsSet(boundary);
        assertNotNull(intervalsSet);
    }

    @Test
    public void testBuildNew() {
        BSPTree<Euclidean1D> tree = new BSPTree<>(Boolean.TRUE);
        IntervalsSet intervalsSet = new IntervalsSet();
        IntervalsSet newIntervalsSet = intervalsSet.buildNew(tree);
        assertNotNull(newIntervalsSet);
    }

    @Test
    public void testComputeGeometricalProperties() {
        IntervalsSet intervalsSet = new IntervalsSet(1.0, 5.0);
        intervalsSet.computeGeometricalProperties();
        assertEquals(4.0, intervalsSet.getSize(), 1e-10);
    }

    @Test
    public void testGetInf() {
        IntervalsSet intervalsSet = new IntervalsSet(1.0, 5.0);
        assertEquals(1.0, intervalsSet.getInf(), 1e-10);
    }

    @Test
    public void testGetSup() {
        IntervalsSet intervalsSet = new IntervalsSet(1.0, 5.0);
        assertEquals(5.0, intervalsSet.getSup(), 1e-10);
    }

    @Test
    public void testAsList() {
        IntervalsSet intervalsSet = new IntervalsSet(1.0, 5.0);
        List<Interval> intervals = intervalsSet.asList();
        assertEquals(1, intervals.size());
        assertEquals(1.0, intervals.get(0).getInf(), 1e-10);
        assertEquals(5.0, intervals.get(0).getSup(), 1e-10);
    }

    @Test
    public void testAsListWithMultipleIntervals() {
        IntervalsSet intervalsSet = new IntervalsSet();
        BSPTree<Euclidean1D> tree = new BSPTree<>(new OrientedPoint(new Vector1D(1.0), false).wholeHyperplane(),
                new BSPTree<>(Boolean.FALSE),
                new BSPTree<>(new OrientedPoint(new Vector1D(5.0), true).wholeHyperplane(),
                        new BSPTree<>(Boolean.FALSE),
                        new BSPTree<>(Boolean.TRUE),
                        null),
                null);
        intervalsSet = intervalsSet.buildNew(tree);
        List<Interval> intervals = intervalsSet.asList();
        assertEquals(1, intervals.size());
        assertEquals(1.0, intervals.get(0).getInf(), 1e-10);
        assertEquals(5.0, intervals.get(0).getSup(), 1e-10);
    }
}