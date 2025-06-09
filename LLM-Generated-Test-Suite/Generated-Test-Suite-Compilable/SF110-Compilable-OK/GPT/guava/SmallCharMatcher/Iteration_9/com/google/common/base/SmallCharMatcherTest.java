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
        assertEquals(4, SmallCharMatcher.chooseTableSize(2));
        assertEquals(8, SmallCharMatcher.chooseTableSize(3));
        assertEquals(8, SmallCharMatcher.chooseTableSize(4));
        assertEquals(16, SmallCharMatcher.chooseTableSize(9));
    }

    @Test
    public void testFrom() {
        BitSet bitSet = new BitSet();
        bitSet.set(0);
        bitSet.set(1);
        bitSet.set(2);
        String description = "test";
        SmallCharMatcher matcher = (SmallCharMatcher) SmallCharMatcher.from(bitSet, description);

        assertTrue(matcher.matches((char) 0));
        assertTrue(matcher.matches((char) 1));
        assertTrue(matcher.matches((char) 2));
        assertFalse(matcher.matches((char) 3));
    }

    @Test
    public void testMatches() {
        BitSet bitSet = new BitSet();
        bitSet.set('a');
        bitSet.set('b');
        bitSet.set('c');
        String description = "abc";
        SmallCharMatcher matcher = (SmallCharMatcher) SmallCharMatcher.from(bitSet, description);

        assertTrue(matcher.matches('a'));
        assertTrue(matcher.matches('b'));
        assertTrue(matcher.matches('c'));
        assertFalse(matcher.matches('d'));
        assertFalse(matcher.matches('\0'));
    }

    @Test
    public void testMatchesWithZero() {
        BitSet bitSet = new BitSet();
        bitSet.set(0);
        bitSet.set('x');
        String description = "zero and x";
        SmallCharMatcher matcher = (SmallCharMatcher) SmallCharMatcher.from(bitSet, description);

        assertTrue(matcher.matches('\0'));
        assertTrue(matcher.matches('x'));
        assertFalse(matcher.matches('y'));
    }

    @Test
    public void testSetBits() {
        BitSet bitSet = new BitSet();
        bitSet.set('a');
        bitSet.set('b');
        String description = "ab";
        SmallCharMatcher matcher = (SmallCharMatcher) SmallCharMatcher.from(bitSet, description);

        BitSet result = new BitSet();
        matcher.setBits(result);

        assertTrue(result.get('a'));
        assertTrue(result.get('b'));
        assertFalse(result.get('c'));
    }
}