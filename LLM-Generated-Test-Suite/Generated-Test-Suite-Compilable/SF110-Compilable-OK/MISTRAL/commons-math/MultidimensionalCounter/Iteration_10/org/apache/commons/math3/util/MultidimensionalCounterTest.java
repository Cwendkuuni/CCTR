package org.apache.commons.math3.util;

import org.apache.commons.math3.exception.*;
import org.junit.Before;
import org.junit.Test;
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

        try {
            new MultidimensionalCounter(1, -1, 2);
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

        try {
            assertArrayEquals(new int[]{0, 0, 0}, counter.getCounts(0));
            assertArrayEquals(new int[]{0, 0, 1}, counter.getCounts(1));
            assertArrayEquals(new int[]{1, 2, 3}, counter.getCounts(23));
        } catch (OutOfRangeException e) {
            fail("Unexpected OutOfRangeException");
        }
    }

    @Test
    public void testGetCount() {
        try {
            counter.getCount(1, 2);
            fail("Expected DimensionMismatchException");
        } catch (DimensionMismatchException e) {
            // Expected
        }

        try {
            counter.getCount(1, 2, 3, 4);
            fail("Expected DimensionMismatchException");
        } catch (DimensionMismatchException e) {
            // Expected
        }

        try {
            counter.getCount(1, 2, -1);
            fail("Expected OutOfRangeException");
        } catch (OutOfRangeException e) {
            // Expected
        }

        try {
            counter.getCount(1, 2, 4);
            fail("Expected OutOfRangeException");
        } catch (OutOfRangeException e) {
            // Expected
        }

        try {
            assertEquals(0, counter.getCount(0, 0, 0));
            assertEquals(1, counter.getCount(0, 0, 1));
            assertEquals(23, counter.getCount(1, 2, 3));
        } catch (OutOfRangeException | DimensionMismatchException e) {
            fail("Unexpected exception");
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
        MultidimensionalCounter.Iterator iterator = (MultidimensionalCounter.Iterator) counter.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(0, (int) iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(1, (int) iterator.next());
        // ... continue testing other elements
        assertEquals(23, (int) iterator.next());
        assertFalse(iterator.hasNext());

        try {
            iterator.remove();
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // Expected
        }
    }

    @Test
    public void testIteratorGetCount() {
        MultidimensionalCounter.Iterator iterator = (MultidimensionalCounter.Iterator) counter.iterator();
        iterator.next();
        assertEquals(0, iterator.getCount());
        iterator.next();
        assertEquals(1, iterator.getCount());
    }

    @Test
    public void testIteratorGetCounts() {
        MultidimensionalCounter.Iterator iterator = (MultidimensionalCounter.Iterator) counter.iterator();
        iterator.next();
        assertArrayEquals(new int[]{0, 0, 0}, iterator.getCounts());
        iterator.next();
        assertArrayEquals(new int[]{0, 0, 1}, iterator.getCounts());
    }

    @Test
    public void testIteratorGetCountDim() {
        MultidimensionalCounter.Iterator iterator = (MultidimensionalCounter.Iterator) counter.iterator();
        iterator.next();
        assertEquals(0, iterator.getCount(0));
        assertEquals(0, iterator.getCount(1));
        assertEquals(0, iterator.getCount(2));
        iterator.next();
        assertEquals(0, iterator.getCount(0));
        assertEquals(0, iterator.getCount(1));
        assertEquals(1, iterator.getCount(2));
    }
}