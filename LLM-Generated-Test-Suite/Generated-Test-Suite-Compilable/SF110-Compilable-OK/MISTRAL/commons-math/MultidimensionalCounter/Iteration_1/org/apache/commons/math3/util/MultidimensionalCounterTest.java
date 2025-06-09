package org.apache.commons.math3.util;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.util.MultidimensionalCounter;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class MultidimensionalCounterTest {

    private MultidimensionalCounter counter;

    @Before
    public void setUp() throws NotStrictlyPositiveException {
        counter = new MultidimensionalCounter(2, 3, 4);
    }

    @Test
    public void testConstructor() {
        try {
            new MultidimensionalCounter(0, 1, 2);
            fail("Expected NotStrictlyPositiveException");
        } catch (NotStrictlyPositiveException e) {
            // Expected
        }
    }

    @Test
    public void testGetDimension() {
        assertEquals(3, counter.getDimension());
    }

    @Test
    public void testGetCounts() {
        try {
            int[] counts = counter.getCounts(10);
            assertArrayEquals(new int[]{1, 1, 2}, counts);
        } catch (OutOfRangeException e) {
            fail("Unexpected OutOfRangeException");
        }

        try {
            counter.getCounts(-1);
            fail("Expected OutOfRangeException");
        } catch (OutOfRangeException e) {
            // Expected
        }

        try {
            counter.getCounts(24);
            fail("Expected OutOfRangeException");
        } catch (OutOfRangeException e) {
            // Expected
        }
    }

    @Test
    public void testGetCount() {
        try {
            int count = counter.getCount(1, 1, 2);
            assertEquals(10, count);
        } catch (OutOfRangeException | DimensionMismatchException e) {
            fail("Unexpected exception");
        }

        try {
            counter.getCount(1, 1, 4);
            fail("Expected OutOfRangeException");
        } catch (OutOfRangeException e) {
            // Expected
        } catch (DimensionMismatchException e) {
            fail("Unexpected DimensionMismatchException");
        }

        try {
            counter.getCount(1, 1);
            fail("Expected DimensionMismatchException");
        } catch (DimensionMismatchException e) {
            // Expected
        } catch (OutOfRangeException e) {
            fail("Unexpected OutOfRangeException");
        }
    }

    @Test
    public void testGetSize() {
        assertEquals(24, counter.getSize());
    }

    @Test
    public void testGetSizes() {
        assertArrayEquals(new int[]{2, 3, 4}, counter.getSizes());
    }

    @Test
    public void testToString() {
        assertEquals("[0][0][0]", counter.toString());
    }

    @Test
    public void testIterator() {
        Iterator<Integer> iterator = counter.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(0, (int) iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(1, (int) iterator.next());
        // ... continue testing other elements

        for (int i = 2; i < 24; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(i, (int) iterator.next());
        }

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIteratorGetCount() {
        Iterator<Integer> iterator = counter.iterator();
        iterator.next();
        assertEquals(0, ((MultidimensionalCounter.Iterator) iterator).getCount());
        iterator.next();
        assertEquals(1, ((MultidimensionalCounter.Iterator) iterator).getCount());
    }

    @Test
    public void testIteratorGetCounts() {
        Iterator<Integer> iterator = counter.iterator();
        iterator.next();
        assertArrayEquals(new int[]{0, 0, 0}, ((MultidimensionalCounter.Iterator) iterator).getCounts());
        iterator.next();
        assertArrayEquals(new int[]{0, 0, 1}, ((MultidimensionalCounter.Iterator) iterator).getCounts());
    }

    @Test
    public void testIteratorGetCountDim() {
        Iterator<Integer> iterator = counter.iterator();
        iterator.next();
        assertEquals(0, ((MultidimensionalCounter.Iterator) iterator).getCount(0));
        assertEquals(0, ((MultidimensionalCounter.Iterator) iterator).getCount(1));
        assertEquals(0, ((MultidimensionalCounter.Iterator) iterator).getCount(2));
        iterator.next();
        assertEquals(0, ((MultidimensionalCounter.Iterator) iterator).getCount(0));
        assertEquals(0, ((MultidimensionalCounter.Iterator) iterator).getCount(1));
        assertEquals(1, ((MultidimensionalCounter.Iterator) iterator).getCount(2));
    }

    @Test
    public void testIteratorRemove() {
        Iterator<Integer> iterator = counter.iterator();
        try {
            iterator.remove();
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // Expected
        }
    }
}