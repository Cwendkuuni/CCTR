package org.apache.commons.math3.geometry.euclidean.oned;

import org.apache.commons.math3.geometry.euclidean.oned.*;
import org.apache.commons.math3.geometry.partitioning.BSPTree;
import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IntervalsSetTest {

    private IntervalsSet emptySet;
    private IntervalsSet finiteSet;
    private IntervalsSet infiniteSet;

    @Before
    public void setUp() {
        emptySet = new IntervalsSet();
        finiteSet = new IntervalsSet(1.0, 5.0);
        infiniteSet = new IntervalsSet(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    @Test
    public void testEmptyConstructor() {
        Assert.assertNotNull(emptySet);
        Assert.assertTrue(emptySet.asList().isEmpty());
    }

    @Test
    public void testFiniteConstructor() {
        Assert.assertNotNull(finiteSet);
        List<Interval> intervals = finiteSet.asList();
        Assert.assertEquals(1, intervals.size());
        Assert.assertEquals(1.0, intervals.get(0).getInf(), 1e-10);
        Assert.assertEquals(5.0, intervals.get(0).getSup(), 1e-10);
    }

    @Test
    public void testInfiniteConstructor() {
        Assert.assertNotNull(infiniteSet);
        List<Interval> intervals = infiniteSet.asList();
        Assert.assertEquals(1, intervals.size());
        Assert.assertEquals(Double.NEGATIVE_INFINITY, intervals.get(0).getInf(), 1e-10);
        Assert.assertEquals(Double.POSITIVE_INFINITY, intervals.get(0).getSup(), 1e-10);
    }

    @Test
    public void testBoundaryConstructor() {
        Collection<SubHyperplane<Euclidean1D>> boundary = new ArrayList<>();
        IntervalsSet boundarySet = new IntervalsSet(boundary);
        Assert.assertNotNull(boundarySet);
    }

    @Test
    public void testBuildNew() {
        BSPTree<Euclidean1D> tree = new BSPTree<>(Boolean.TRUE);
        IntervalsSet newSet = finiteSet.buildNew(tree);
        Assert.assertNotNull(newSet);
        Assert.assertEquals(Double.NEGATIVE_INFINITY, newSet.getInf(), 1e-10);
        Assert.assertEquals(Double.POSITIVE_INFINITY, newSet.getSup(), 1e-10);
    }

    @Test
    public void testComputeGeometricalProperties() {
        finiteSet.computeGeometricalProperties();
        Assert.assertEquals(4.0, finiteSet.getSize(), 1e-10);
    }

    @Test
    public void testGetInf() {
        Assert.assertEquals(Double.POSITIVE_INFINITY, emptySet.getInf(), 1e-10);
        Assert.assertEquals(1.0, finiteSet.getInf(), 1e-10);
        Assert.assertEquals(Double.NEGATIVE_INFINITY, infiniteSet.getInf(), 1e-10);
    }

    @Test
    public void testGetSup() {
        Assert.assertEquals(Double.NEGATIVE_INFINITY, emptySet.getSup(), 1e-10);
        Assert.assertEquals(5.0, finiteSet.getSup(), 1e-10);
        Assert.assertEquals(Double.POSITIVE_INFINITY, infiniteSet.getSup(), 1e-10);
    }

    @Test
    public void testAsList() {
        List<Interval> intervals = finiteSet.asList();
        Assert.assertEquals(1, intervals.size());
        Assert.assertEquals(1.0, intervals.get(0).getInf(), 1e-10);
        Assert.assertEquals(5.0, intervals.get(0).getSup(), 1e-10);
    }
}