package com.google.common.base;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.BitSet;

public class SmallCharMatcherTest {

    @Test
    public void testSmear() {
        int hashCode = 12345;
        int expected = 461845907 * Integer.rotateLeft(hashCode * -862048943, 15);
        assertEquals(expected, SmallCharMatcher.smear(hashCode));
    }

    @Test
    public void testChooseTableSize() {
        assertEquals(2, SmallCharMatcher.chooseTableSize(1));
        assertEquals(4, SmallCharMatcher.chooseTableSize(3));
        assertEquals(8, SmallCharMatcher.chooseTableSize(5));
        assertEquals(16, SmallCharMatcher.chooseTableSize(9));
    }

    @Test
    public void testFrom() {
        BitSet bitSet = new BitSet();
        bitSet.set('a');
        bitSet.set('b');
        bitSet.set('c');
        SmallCharMatcher matcher = (SmallCharMatcher) SmallCharMatcher.from(bitSet, "abc");

        assertTrue(matcher.matches('a'));
        assertTrue(matcher.matches('b'));
        assertTrue(matcher.matches('c'));
        assertFalse(matcher.matches('d'));
        assertFalse(matcher.matches('\0'));
    }

    @Test
    public void testMatches() {
        BitSet bitSet = new BitSet();
        bitSet.set('x');
        bitSet.set('y');
        SmallCharMatcher matcher = (SmallCharMatcher) SmallCharMatcher.from(bitSet, "xy");

        assertTrue(matcher.matches('x'));
        assertTrue(matcher.matches('y'));
        assertFalse(matcher.matches('z'));
        assertFalse(matcher.matches('\0'));
    }

    @Test
    public void testMatchesWithZero() {
        BitSet bitSet = new BitSet();
        bitSet.set(0);
        bitSet.set('z');
        SmallCharMatcher matcher = (SmallCharMatcher) SmallCharMatcher.from(bitSet, "z");

        assertTrue(matcher.matches('\0'));
        assertTrue(matcher.matches('z'));
        assertFalse(matcher.matches('a'));
    }

    @Test
    public void testSetBits() {
        BitSet bitSet = new BitSet();
        bitSet.set('m');
        bitSet.set('n');
        SmallCharMatcher matcher = (SmallCharMatcher) SmallCharMatcher.from(bitSet, "mn");

        BitSet result = new BitSet();
        matcher.setBits(result);

        assertTrue(result.get('m'));
        assertTrue(result.get('n'));
        assertFalse(result.get('o'));
    }

    @Test
    public void testSetBitsWithZero() {
        BitSet bitSet = new BitSet();
        bitSet.set(0);
        bitSet.set('p');
        SmallCharMatcher matcher = (SmallCharMatcher) SmallCharMatcher.from(bitSet, "p");

        BitSet result = new BitSet();
        matcher.setBits(result);

        assertTrue(result.get(0));
        assertTrue(result.get('p'));
        assertFalse(result.get('q'));
    }
}