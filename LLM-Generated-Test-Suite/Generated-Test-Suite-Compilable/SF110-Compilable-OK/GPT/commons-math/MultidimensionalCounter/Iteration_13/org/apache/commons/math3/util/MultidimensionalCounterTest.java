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

    private MultidimensionalCounter counter;

    @Before
    public void setUp() {
        counter = new MultidimensionalCounter(3, 4, 5);
    }

    @Test(expected = NotStrictlyPositiveException.class)
    public void testConstructorWithNonPositiveSize() {
        new MultidimensionalCounter(3, 0, 5);
    }

    @Test
    public void testGetDimension() {
        assertEquals(3, counter.getDimension());
    }

    @Test
    public void testGetSizes() {
        assertArrayEquals(new int[]{3, 4, 5}, counter.getSizes());
    }

    @Test
    public void testGetSize() {
        assertEquals(60, counter.getSize());
    }

    @Test
    public void testGetCounts() {
        assertArrayEquals(new int[]{0, 0, 0}, counter.getCounts(0));
        assertArrayEquals(new int[]{0, 0, 1}, counter.getCounts(1));
        assertArrayEquals(new int[]{0, 1, 0}, counter.getCounts(5));
        assertArrayEquals(new int[]{1, 0, 0}, counter.getCounts(20));
    }

    @Test(expected = OutOfRangeException.class)
    public void testGetCountsOutOfRange() {
        counter.getCounts(60);
    }

    @Test
    public void testGetCount() {
        assertEquals(0, counter.getCount(0, 0, 0));
        assertEquals(1, counter.getCount(0, 0, 1));
        assertEquals(5, counter.getCount(0, 1, 0));
        assertEquals(20, counter.getCount(1, 0, 0));
    }

    @Test(expected = DimensionMismatchException.class)
    public void testGetCountDimensionMismatch() {
        counter.getCount(0, 0);
    }

    @Test(expected = OutOfRangeException.class)
    public void testGetCountOutOfRange() {
        counter.getCount(3, 0, 0);
    }

    @Test
    public void testIterator() {
        MultidimensionalCounter.Iterator iterator = counter.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            assertEquals(count, (int) iterator.next());
            count++;
        }
        assertEquals(60, count);
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorNextOutOfBounds() {
        MultidimensionalCounter.Iterator iterator = counter.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        iterator.next(); // This should throw NoSuchElementException
    }

    @Test
    public void testIteratorGetCount() {
        MultidimensionalCounter.Iterator iterator = counter.iterator();
        iterator.next();
        assertEquals(0, iterator.getCount());
        iterator.next();
        assertEquals(1, iterator.getCount());
    }

    @Test
    public void testIteratorGetCounts() {
        MultidimensionalCounter.Iterator iterator = counter.iterator();
        iterator.next();
        assertArrayEquals(new int[]{0, 0, 0}, iterator.getCounts());
        iterator.next();
        assertArrayEquals(new int[]{0, 0, 1}, iterator.getCounts());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemove() {
        MultidimensionalCounter.Iterator iterator = counter.iterator();
        iterator.remove();
    }

    @Test
    public void testToString() {
        assertNotNull(counter.toString());
    }
}