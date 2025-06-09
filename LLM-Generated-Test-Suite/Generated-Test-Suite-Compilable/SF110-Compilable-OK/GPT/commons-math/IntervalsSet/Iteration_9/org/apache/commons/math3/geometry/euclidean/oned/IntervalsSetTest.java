package org.apache.commons.math3.geometry.euclidean.oned;

import org.apache.commons.math3.geometry.euclidean.oned.*;
import org.apache.commons.math3.geometry.partitioning.BSPTree;
import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IntervalsSetTest {

    private IntervalsSet emptySet;
    private IntervalsSet finiteSet;
    private IntervalsSet infiniteSet;
    private BSPTree<Euclidean1D> tree;

    @Before
    public void setUp() {
        emptySet = new IntervalsSet();
        finiteSet = new IntervalsSet(1.0, 5.0);
        infiniteSet = new IntervalsSet(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        tree = new BSPTree<>(Boolean.TRUE);
    }

    @After
    public void tearDown() {
        emptySet = null;
        finiteSet = null;
        infiniteSet = null;
        tree = null;
    }

    @Test
    public void testEmptyConstructor() {
        assertNotNull("Empty constructor should create an instance", emptySet);
    }

    @Test
    public void testFiniteConstructor() {
        assertNotNull("Finite constructor should create an instance", finiteSet);
    }

    @Test
    public void testInfiniteConstructor() {
        assertNotNull("Infinite constructor should create an instance", infiniteSet);
    }

    @Test
    public void testTreeConstructor() {
        IntervalsSet set = new IntervalsSet(tree);
        assertNotNull("Tree constructor should create an instance", set);
    }

    @Test
    public void testBoundaryConstructor() {
        Collection<SubHyperplane<Euclidean1D>> boundary = new ArrayList<>();
        IntervalsSet set = new IntervalsSet(boundary);
        assertNotNull("Boundary constructor should create an instance", set);
    }

    @Test
    public void testBuildNew() {
        IntervalsSet newSet = finiteSet.buildNew(tree);
        assertNotNull("buildNew should create a new IntervalsSet", newSet);
    }

    @Test
    public void testComputeGeometricalProperties() {
        finiteSet.computeGeometricalProperties();
        // Assuming computeGeometricalProperties sets some internal state, we can't directly test it without accessors.
        // This test ensures no exceptions are thrown.
    }

    @Test
    public void testGetInf() {
        assertEquals("getInf should return the lower bound", 1.0, finiteSet.getInf(), 1e-10);
        assertEquals("getInf should return negative infinity for infinite set", Double.NEGATIVE_INFINITY, infiniteSet.getInf(), 1e-10);
    }

    @Test
    public void testGetSup() {
        assertEquals("getSup should return the upper bound", 5.0, finiteSet.getSup(), 1e-10);
        assertEquals("getSup should return positive infinity for infinite set", Double.POSITIVE_INFINITY, infiniteSet.getSup(), 1e-10);
    }

    @Test
    public void testAsList() {
        List<Interval> intervals = finiteSet.asList();
        assertEquals("asList should return a list with one interval", 1, intervals.size());
        assertEquals("Interval lower bound should be 1.0", 1.0, intervals.get(0).getInf(), 1e-10);
        assertEquals("Interval upper bound should be 5.0", 5.0, intervals.get(0).getSup(), 1e-10);
    }

    @Test
    public void testRecurseList() {
        // This method is private, so we indirectly test it through asList.
        List<Interval> intervals = infiniteSet.asList();
        assertEquals("asList should return a list with one interval for infinite set", 1, intervals.size());
        assertEquals("Interval lower bound should be negative infinity", Double.NEGATIVE_INFINITY, intervals.get(0).getInf(), 1e-10);
        assertEquals("Interval upper bound should be positive infinity", Double.POSITIVE_INFINITY, intervals.get(0).getSup(), 1e-10);
    }
}