package org.apache.commons.math3.geometry.euclidean.oned;

import org.apache.commons.math3.geometry.euclidean.oned.*;
import org.apache.commons.math3.geometry.partitioning.BSPTree;
import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class IntervalsSetTest {

    private IntervalsSet emptySet;
    private IntervalsSet finiteSet;
    private IntervalsSet infiniteSet;
    private IntervalsSet customTreeSet;

    @Before
    public void setUp() {
        emptySet = new IntervalsSet();
        finiteSet = new IntervalsSet(1.0, 5.0);
        infiniteSet = new IntervalsSet(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        BSPTree<Euclidean1D> customTree = new BSPTree<>(Boolean.TRUE);
        customTreeSet = new IntervalsSet(customTree);
    }

    @After
    public void tearDown() {
        emptySet = null;
        finiteSet = null;
        infiniteSet = null;
        customTreeSet = null;
    }

    @Test
    public void testEmptyConstructor() {
        assertNotNull(emptySet);
        assertTrue(emptySet.asList().isEmpty());
    }

    @Test
    public void testFiniteConstructor() {
        assertNotNull(finiteSet);
        List<Interval> intervals = finiteSet.asList();
        assertEquals(1, intervals.size());
        assertEquals(1.0, intervals.get(0).getInf(), 1e-10);
        assertEquals(5.0, intervals.get(0).getSup(), 1e-10);
    }

    @Test
    public void testInfiniteConstructor() {
        assertNotNull(infiniteSet);
        List<Interval> intervals = infiniteSet.asList();
        assertEquals(1, intervals.size());
        assertEquals(Double.NEGATIVE_INFINITY, intervals.get(0).getInf(), 1e-10);
        assertEquals(Double.POSITIVE_INFINITY, intervals.get(0).getSup(), 1e-10);
    }

    @Test
    public void testCustomTreeConstructor() {
        assertNotNull(customTreeSet);
        assertTrue(customTreeSet.asList().isEmpty());
    }

    @Test
    public void testBuildNew() {
        BSPTree<Euclidean1D> newTree = new BSPTree<>(Boolean.FALSE);
        IntervalsSet newSet = finiteSet.buildNew(newTree);
        assertNotNull(newSet);
        assertTrue(newSet.asList().isEmpty());
    }

    @Test
    public void testComputeGeometricalProperties() {
        finiteSet.computeGeometricalProperties();
        assertEquals(4.0, finiteSet.getSize(), 1e-10);
    }

    @Test
    public void testGetInf() {
        assertEquals(Double.POSITIVE_INFINITY, emptySet.getInf(), 1e-10);
        assertEquals(1.0, finiteSet.getInf(), 1e-10);
        assertEquals(Double.NEGATIVE_INFINITY, infiniteSet.getInf(), 1e-10);
    }

    @Test
    public void testGetSup() {
        assertEquals(Double.NEGATIVE_INFINITY, emptySet.getSup(), 1e-10);
        assertEquals(5.0, finiteSet.getSup(), 1e-10);
        assertEquals(Double.POSITIVE_INFINITY, infiniteSet.getSup(), 1e-10);
    }

    @Test
    public void testAsList() {
        List<Interval> intervals = finiteSet.asList();
        assertEquals(1, intervals.size());
        assertEquals(1.0, intervals.get(0).getInf(), 1e-10);
        assertEquals(5.0, intervals.get(0).getSup(), 1e-10);
    }
}