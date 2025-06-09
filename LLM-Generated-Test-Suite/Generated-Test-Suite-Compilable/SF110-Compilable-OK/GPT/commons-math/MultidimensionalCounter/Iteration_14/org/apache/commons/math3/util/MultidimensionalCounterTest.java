package org.apache.commons.math3.util;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.util.MultidimensionalCounter;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class MultidimensionalCounterTest {

    private MultidimensionalCounter counter3D;
    private MultidimensionalCounter counter2D;

    @Before
    public void setUp() {
        counter3D = new MultidimensionalCounter(3, 4, 5);
        counter2D = new MultidimensionalCounter(2, 3);
    }

    @Test(expected = NotStrictlyPositiveException.class)
    public void testConstructorNotStrictlyPositiveException() {
        new MultidimensionalCounter(3, 0, 5);
    }

    @Test
    public void testGetDimension() {
        assertEquals(3, counter3D.getDimension());
        assertEquals(2, counter2D.getDimension());
    }

    @Test
    public void testGetSize() {
        assertEquals(60, counter3D.getSize());
        assertEquals(6, counter2D.getSize());
    }

    @Test
    public void testGetSizes() {
        assertArrayEquals(new int[]{3, 4, 5}, counter3D.getSizes());
        assertArrayEquals(new int[]{2, 3}, counter2D.getSizes());
    }

    @Test
    public void testGetCounts() {
        assertArrayEquals(new int[]{0, 0, 0}, counter3D.getCounts(0));
        assertArrayEquals(new int[]{2, 3, 4}, counter3D.getCounts(59));
    }

    @Test(expected = OutOfRangeException.class)
    public void testGetCountsOutOfRangeException() {
        counter3D.getCounts(60);
    }

    @Test
    public void testGetCount() {
        assertEquals(0, counter3D.getCount(0, 0, 0));
        assertEquals(59, counter3D.getCount(2, 3, 4));
    }

    @Test(expected = DimensionMismatchException.class)
    public void testGetCountDimensionMismatchException() {
        counter3D.getCount(0, 0);
    }

    @Test(expected = OutOfRangeException.class)
    public void testGetCountOutOfRangeException() {
        counter3D.getCount(3, 0, 0);
    }

    @Test
    public void testIterator() {
        MultidimensionalCounter.Iterator iterator = counter3D.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            assertEquals(count, (int) iterator.next());
            count++;
        }
        assertEquals(60, count);
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorNoSuchElementException() {
        MultidimensionalCounter.Iterator iterator = counter3D.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        iterator.next(); // Should throw NoSuchElementException
    }

    @Test
    public void testIteratorGetCount() {
        MultidimensionalCounter.Iterator iterator = counter3D.iterator();
        iterator.next();
        assertEquals(0, iterator.getCount());
    }

    @Test
    public void testIteratorGetCounts() {
        MultidimensionalCounter.Iterator iterator = counter3D.iterator();
        iterator.next();
        assertArrayEquals(new int[]{0, 0, 0}, iterator.getCounts());
    }

    @Test
    public void testIteratorGetCountWithDim() {
        MultidimensionalCounter.Iterator iterator = counter3D.iterator();
        iterator.next();
        assertEquals(0, iterator.getCount(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemove() {
        MultidimensionalCounter.Iterator iterator = counter3D.iterator();
        iterator.remove();
    }

    @Test
    public void testToString() {
        assertEquals("[0][0][0]", counter3D.toString());
    }
}