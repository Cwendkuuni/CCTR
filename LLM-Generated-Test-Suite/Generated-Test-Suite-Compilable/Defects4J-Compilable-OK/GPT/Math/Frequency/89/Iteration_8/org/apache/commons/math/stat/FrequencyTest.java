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
        frequency.addValue(1);
        assertEquals(1, frequency.getCount(1));
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
    public void testGetCountObject() {
        frequency.addValue("test");
        assertEquals(1, frequency.getCount("test"));
        assertEquals(0, frequency.getCount("nonexistent"));
    }

    @Test
    public void testGetCountInt() {
        frequency.addValue(1);
        assertEquals(1, frequency.getCount(1));
        assertEquals(0, frequency.getCount(2));
    }

    @Test
    public void testGetCountLong() {
        frequency.addValue(1L);
        assertEquals(1, frequency.getCount(1L));
        assertEquals(0, frequency.getCount(2L));
    }

    @Test
    public void testGetCountChar() {
        frequency.addValue('a');
        assertEquals(1, frequency.getCount('a'));
        assertEquals(0, frequency.getCount('b'));
    }

    @Test
    public void testGetPctObject() {
        frequency.addValue("test");
        assertEquals(1.0, frequency.getPct("test"), 0.001);
        assertTrue(Double.isNaN(frequency.getPct("nonexistent")));
    }

    @Test
    public void testGetPctInt() {
        frequency.addValue(1);
        assertEquals(1.0, frequency.getPct(1), 0.001);
        assertTrue(Double.isNaN(frequency.getPct(2)));
    }

    @Test
    public void testGetPctLong() {
        frequency.addValue(1L);
        assertEquals(1.0, frequency.getPct(1L), 0.001);
        assertTrue(Double.isNaN(frequency.getPct(2L)));
    }

    @Test
    public void testGetPctChar() {
        frequency.addValue('a');
        assertEquals(1.0, frequency.getPct('a'), 0.001);
        assertTrue(Double.isNaN(frequency.getPct('b')));
    }

    @Test
    public void testGetCumFreqObject() {
        frequency.addValue("test");
        frequency.addValue("test2");
        assertEquals(1, frequency.getCumFreq("test"));
        assertEquals(2, frequency.getCumFreq("test2"));
        assertEquals(0, frequency.getCumFreq("nonexistent"));
    }

    @Test
    public void testGetCumFreqInt() {
        frequency.addValue(1);
        frequency.addValue(2);
        assertEquals(1, frequency.getCumFreq(1));
        assertEquals(2, frequency.getCumFreq(2));
        assertEquals(0, frequency.getCumFreq(3));
    }

    @Test
    public void testGetCumFreqLong() {
        frequency.addValue(1L);
        frequency.addValue(2L);
        assertEquals(1, frequency.getCumFreq(1L));
        assertEquals(2, frequency.getCumFreq(2L));
        assertEquals(0, frequency.getCumFreq(3L));
    }

    @Test
    public void testGetCumFreqChar() {
        frequency.addValue('a');
        frequency.addValue('b');
        assertEquals(1, frequency.getCumFreq('a'));
        assertEquals(2, frequency.getCumFreq('b'));
        assertEquals(0, frequency.getCumFreq('c'));
    }

    @Test
    public void testGetCumPctObject() {
        frequency.addValue("test");
        frequency.addValue("test2");
        assertEquals(0.5, frequency.getCumPct("test"), 0.001);
        assertEquals(1.0, frequency.getCumPct("test2"), 0.001);
        assertTrue(Double.isNaN(frequency.getCumPct("nonexistent")));
    }

    @Test
    public void testGetCumPctInt() {
        frequency.addValue(1);
        frequency.addValue(2);
        assertEquals(0.5, frequency.getCumPct(1), 0.001);
        assertEquals(1.0, frequency.getCumPct(2), 0.001);
        assertTrue(Double.isNaN(frequency.getCumPct(3)));
    }

    @Test
    public void testGetCumPctLong() {
        frequency.addValue(1L);
        frequency.addValue(2L);
        assertEquals(0.5, frequency.getCumPct(1L), 0.001);
        assertEquals(1.0, frequency.getCumPct(2L), 0.001);
        assertTrue(Double.isNaN(frequency.getCumPct(3L)));
    }

    @Test
    public void testGetCumPctChar() {
        frequency.addValue('a');
        frequency.addValue('b');
        assertEquals(0.5, frequency.getCumPct('a'), 0.001);
        assertEquals(1.0, frequency.getCumPct('b'), 0.001);
        assertTrue(Double.isNaN(frequency.getCumPct('c')));
    }

    @Test
    public void testToString() {
        frequency.addValue(1);
        frequency.addValue(2);
        frequency.addValue(2);
        String expected = "Value \t Freq. \t Pct. \t Cum Pct. \n" +
                          "1\t1\t50%\t50%\n" +
                          "2\t2\t100%\t100%\n";
        assertEquals(expected, frequency.toString());
    }
}