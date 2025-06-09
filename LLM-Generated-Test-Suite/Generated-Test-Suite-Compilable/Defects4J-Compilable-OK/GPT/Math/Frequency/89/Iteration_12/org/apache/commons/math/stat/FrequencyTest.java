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
    public void testAddValueObject() {
        frequency.addValue("test");
        assertEquals(1, frequency.getCount("test"));
    }

    @Test
    public void testAddValueComparable() {
        frequency.addValue((Comparable<?>) "test");
        assertEquals(1, frequency.getCount("test"));
    }

    @Test
    public void testAddValueInt() {
        frequency.addValue(1);
        assertEquals(1, frequency.getCount(1));
    }

    @Test
    public void testAddValueInteger() {
        frequency.addValue(Integer.valueOf(1));
        assertEquals(1, frequency.getCount(1));
    }

    @Test
    public void testAddValueLong() {
        frequency.addValue(1L);
        assertEquals(1, frequency.getCount(1L));
    }

    @Test
    public void testAddValueChar() {
        frequency.addValue('a');
        assertEquals(1, frequency.getCount('a'));
    }

    @Test
    public void testClear() {
        frequency.addValue(1);
        frequency.clear();
        assertEquals(0, frequency.getCount(1));
    }

    @Test
    public void testValuesIterator() {
        frequency.addValue(1);
        frequency.addValue(2);
        Iterator<?> iterator = frequency.valuesIterator();
        assertTrue(iterator.hasNext());
        assertEquals(1L, iterator.next());
        assertEquals(2L, iterator.next());
    }

    @Test
    public void testGetSumFreq() {
        frequency.addValue(1);
        frequency.addValue(2);
        assertEquals(2, frequency.getSumFreq());
    }

    @Test
    public void testGetCountObject() {
        frequency.addValue("test");
        assertEquals(1, frequency.getCount("test"));
    }

    @Test
    public void testGetCountInt() {
        frequency.addValue(1);
        assertEquals(1, frequency.getCount(1));
    }

    @Test
    public void testGetCountLong() {
        frequency.addValue(1L);
        assertEquals(1, frequency.getCount(1L));
    }

    @Test
    public void testGetCountChar() {
        frequency.addValue('a');
        assertEquals(1, frequency.getCount('a'));
    }

    @Test
    public void testGetPctObject() {
        frequency.addValue("test");
        assertEquals(1.0, frequency.getPct("test"), 0.001);
    }

    @Test
    public void testGetPctInt() {
        frequency.addValue(1);
        assertEquals(1.0, frequency.getPct(1), 0.001);
    }

    @Test
    public void testGetPctLong() {
        frequency.addValue(1L);
        assertEquals(1.0, frequency.getPct(1L), 0.001);
    }

    @Test
    public void testGetPctChar() {
        frequency.addValue('a');
        assertEquals(1.0, frequency.getPct('a'), 0.001);
    }

    @Test
    public void testGetCumFreqObject() {
        frequency.addValue("test");
        assertEquals(1, frequency.getCumFreq("test"));
    }

    @Test
    public void testGetCumFreqInt() {
        frequency.addValue(1);
        assertEquals(1, frequency.getCumFreq(1));
    }

    @Test
    public void testGetCumFreqLong() {
        frequency.addValue(1L);
        assertEquals(1, frequency.getCumFreq(1L));
    }

    @Test
    public void testGetCumFreqChar() {
        frequency.addValue('a');
        assertEquals(1, frequency.getCumFreq('a'));
    }

    @Test
    public void testGetCumPctObject() {
        frequency.addValue("test");
        assertEquals(1.0, frequency.getCumPct("test"), 0.001);
    }

    @Test
    public void testGetCumPctInt() {
        frequency.addValue(1);
        assertEquals(1.0, frequency.getCumPct(1), 0.001);
    }

    @Test
    public void testGetCumPctLong() {
        frequency.addValue(1L);
        assertEquals(1.0, frequency.getCumPct(1L), 0.001);
    }

    @Test
    public void testGetCumPctChar() {
        frequency.addValue('a');
        assertEquals(1.0, frequency.getCumPct('a'), 0.001);
    }

    @Test
    public void testToString() {
        frequency.addValue(1);
        String expected = "Value \t Freq. \t Pct. \t Cum Pct. \n1\t1\t100%\t100%\n";
        assertEquals(expected, frequency.toString());
    }
}