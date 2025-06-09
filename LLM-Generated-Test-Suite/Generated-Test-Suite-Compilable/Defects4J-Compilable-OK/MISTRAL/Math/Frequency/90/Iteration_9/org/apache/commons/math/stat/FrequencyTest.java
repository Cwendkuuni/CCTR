package org.apache.commons.math.stat;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Comparator;
import java.util.Iterator;

public class FrequencyTest {

    private Frequency frequency;

    @Before
    public void setUp() {
        frequency = new Frequency();
    }

    @Test
    public void testAddValue() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(2);
        frequency.addValue(3);

        assertEquals(1, frequency.getCount(1));
        assertEquals(2, frequency.getCount(2));
        assertEquals(1, frequency.getCount(3));
    }

    @Test
    public void testAddValueInteger() {
        frequency.addValue(Integer.valueOf(1));
        frequency.addValue(Integer.valueOf(2));
        frequency.addValue(Integer.valueOf(2));
        frequency.addValue(Integer.valueOf(3));

        assertEquals(1, frequency.getCount(1));
        assertEquals(2, frequency.getCount(2));
        assertEquals(1, frequency.getCount(3));
    }

    @Test
    public void testAddValueLong() {
        frequency.addValue(1L);
        frequency.addValue(2L);
        frequency.addValue(2L);
        frequency.addValue(3L);

        assertEquals(1, frequency.getCount(1L));
        assertEquals(2, frequency.getCount(2L));
        assertEquals(1, frequency.getCount(3L));
    }

    @Test
    public void testAddValueChar() {
        frequency.addValue('a');
        frequency.addValue('b');
        frequency.addValue('b');
        frequency.addValue('c');

        assertEquals(1, frequency.getCount('a'));
        assertEquals(2, frequency.getCount('b'));
        assertEquals(1, frequency.getCount('c'));
    }

    @Test
    public void testClear() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.clear();

        assertEquals(0, frequency.getSumFreq());
    }

    @Test
    public void testValuesIterator() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(3);

        Iterator iterator = frequency.valuesIterator();
        assertTrue(iterator.hasNext());
        assertEquals(Long.valueOf(1), iterator.next());
        assertEquals(Long.valueOf(2), iterator.next());
        assertEquals(Long.valueOf(3), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetSumFreq() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(2);
        frequency.addValue(3);

        assertEquals(4, frequency.getSumFreq());
    }

    @Test
    public void testGetCount() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(2);
        frequency.addValue(3);

        assertEquals(1, frequency.getCount(1));
        assertEquals(2, frequency.getCount(2));
        assertEquals(1, frequency.getCount(3));
        assertEquals(0, frequency.getCount(4));
    }

    @Test
    public void testGetPct() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(2);
        frequency.addValue(3);

        assertEquals(0.25, frequency.getPct(1), 0.001);
        assertEquals(0.5, frequency.getPct(2), 0.001);
        assertEquals(0.25, frequency.getPct(3), 0.001);
        assertEquals(0.0, frequency.getPct(4), 0.001);
    }

    @Test
    public void testGetCumFreq() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(2);
        frequency.addValue(3);

        assertEquals(1, frequency.getCumFreq(1));
        assertEquals(3, frequency.getCumFreq(2));
        assertEquals(4, frequency.getCumFreq(3));
        assertEquals(4, frequency.getCumFreq(4));
    }

    @Test
    public void testGetCumPct() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(2);
        frequency.addValue(3);

        assertEquals(0.25, frequency.getCumPct(1), 0.001);
        assertEquals(0.75, frequency.getCumPct(2), 0.001);
        assertEquals(1.0, frequency.getCumPct(3), 0.001);
        assertEquals(1.0, frequency.getCumPct(4), 0.001);
    }

    @Test
    public void testToString() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(2);
        frequency.addValue(3);

        String expected = "Value \t Freq. \t Pct. \t Cum Pct. \n" +
                          "1\t1\t25%\t25%\n" +
                          "2\t2\t50%\t75%\n" +
                          "3\t1\t25%\t100%\n";
        assertEquals(expected, frequency.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddValueException() {
        frequency.addValue(1);
        frequency.addValue("a");
    }

    @Test
    public void testConstructorWithComparator() {
        Comparator<Long> comparator = (o1, o2) -> o2.compareTo(o1);
        Frequency frequencyWithComparator = new Frequency(comparator);

        frequencyWithComparator.addValue(1);
        frequencyWithComparator.addValue(2);
        frequencyWithComparator.addValue(3);

        Iterator iterator = frequencyWithComparator.valuesIterator();
        assertTrue(iterator.hasNext());
        assertEquals(Long.valueOf(3), iterator.next());
        assertEquals(Long.valueOf(2), iterator.next());
        assertEquals(Long.valueOf(1), iterator.next());
        assertFalse(iterator.hasNext());
    }
}