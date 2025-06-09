package org.apache.commons.math.stat;

import org.apache.commons.math.stat.Frequency;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class FrequencyTest {

    private Frequency frequency;

    @Before
    public void setUp() {
        frequency = new Frequency();
    }

    @Test
    public void testAddValueInt() {
        frequency.addValue(1);
        assertEquals(1, frequency.getCount(1));
        assertEquals(1, frequency.getSumFreq());
    }

    @Test
    public void testAddValueInteger() {
        frequency.addValue(Integer.valueOf(2));
        assertEquals(1, frequency.getCount(2));
        assertEquals(1, frequency.getSumFreq());
    }

    @Test
    public void testAddValueLong() {
        frequency.addValue(3L);
        assertEquals(1, frequency.getCount(3L));
        assertEquals(1, frequency.getSumFreq());
    }

    @Test
    public void testAddValueChar() {
        frequency.addValue('a');
        assertEquals(1, frequency.getCount('a'));
        assertEquals(1, frequency.getSumFreq());
    }

    @Test
    public void testAddValueObject() {
        frequency.addValue("test");
        assertEquals(1, frequency.getCount("test"));
        assertEquals(1, frequency.getSumFreq());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddValueIncompatibleTypes() {
        frequency.addValue(1);
        frequency.addValue("test");
    }

    @Test
    public void testClear() {
        frequency.addValue(1);
        frequency.clear();
        assertEquals(0, frequency.getSumFreq());
    }

    @Test
    public void testValuesIterator() {
        frequency.addValue(1);
        frequency.addValue(2);
        Iterator<?> iterator = frequency.valuesIterator();
        assertTrue(iterator.hasNext());
        assertEquals(1L, iterator.next());
        assertEquals(2L, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetSumFreq() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(2);
        assertEquals(3, frequency.getSumFreq());
    }

    @Test
    public void testGetCount() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(2);
        assertEquals(1, frequency.getCount(1));
        assertEquals(2, frequency.getCount(2));
        assertEquals(0, frequency.getCount(3));
    }

    @Test
    public void testGetPct() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(2);
        assertEquals(1.0 / 3.0, frequency.getPct(1), 0.0001);
        assertEquals(2.0 / 3.0, frequency.getPct(2), 0.0001);
        assertTrue(Double.isNaN(frequency.getPct(3)));
    }

    @Test
    public void testGetCumFreq() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(2);
        assertEquals(1, frequency.getCumFreq(1));
        assertEquals(3, frequency.getCumFreq(2));
        assertEquals(3, frequency.getCumFreq(3));
    }

    @Test
    public void testGetCumPct() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(2);
        assertEquals(1.0 / 3.0, frequency.getCumPct(1), 0.0001);
        assertEquals(1.0, frequency.getCumPct(2), 0.0001);
        assertEquals(1.0, frequency.getCumPct(3), 0.0001);
    }

    @Test
    public void testToString() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(2);
        String expected = "Value \t Freq. \t Pct. \t Cum Pct. \n" +
                          "1\t1\t33%\t33%\n" +
                          "2\t2\t67%\t100%\n";
        assertEquals(expected, frequency.toString());
    }
}