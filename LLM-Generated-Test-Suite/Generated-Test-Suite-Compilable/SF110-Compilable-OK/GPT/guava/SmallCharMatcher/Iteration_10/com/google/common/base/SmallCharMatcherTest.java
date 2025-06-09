package com.google.common.base;

import com.google.common.base.SmallCharMatcher;
import com.google.common.base.CharMatcher;
import org.junit.Before;
import org.junit.Test;

import java.util.BitSet;

import static org.junit.Assert.*;

public class SmallCharMatcherTest {

    private SmallCharMatcher matcher;
    private BitSet bitSet;
    private String description;

    @Before
    public void setUp() {
        bitSet = new BitSet();
        bitSet.set('a');
        bitSet.set('b');
        bitSet.set('c');
        description = "Test Matcher";
        matcher = (SmallCharMatcher) SmallCharMatcher.from(bitSet, description);
    }

    @Test
    public void testMatches() {
        assertTrue(matcher.matches('a'));
        assertTrue(matcher.matches('b'));
        assertTrue(matcher.matches('c'));
        assertFalse(matcher.matches('d'));
        assertFalse(matcher.matches('\0'));
    }

    @Test
    public void testMatchesWithZero() {
        bitSet.set(0);
        matcher = (SmallCharMatcher) SmallCharMatcher.from(bitSet, description);
        assertTrue(matcher.matches('\0'));
    }

    @Test
    public void testSmear() {
        int hashCode = 12345;
        int smeared = SmallCharMatcher.smear(hashCode);
        assertNotEquals(hashCode, smeared);
    }

    @Test
    public void testChooseTableSize() {
        assertEquals(2, SmallCharMatcher.chooseTableSize(1));
        assertEquals(4, SmallCharMatcher.chooseTableSize(2));
        assertEquals(8, SmallCharMatcher.chooseTableSize(3));
        assertEquals(8, SmallCharMatcher.chooseTableSize(4));
    }

    @Test
    public void testFrom() {
        SmallCharMatcher matcherFrom = (SmallCharMatcher) SmallCharMatcher.from(bitSet, description);
        assertNotNull(matcherFrom);
        assertTrue(matcherFrom.matches('a'));
        assertTrue(matcherFrom.matches('b'));
        assertTrue(matcherFrom.matches('c'));
        assertFalse(matcherFrom.matches('d'));
    }

    @Test
    public void testSetBits() {
        BitSet testBitSet = new BitSet();
        matcher.setBits(testBitSet);
        assertTrue(testBitSet.get('a'));
        assertTrue(testBitSet.get('b'));
        assertTrue(testBitSet.get('c'));
        assertFalse(testBitSet.get('d'));
    }

    @Test
    public void testSetBitsWithZero() {
        bitSet.set(0);
        matcher = (SmallCharMatcher) SmallCharMatcher.from(bitSet, description);
        BitSet testBitSet = new BitSet();
        matcher.setBits(testBitSet);
        assertTrue(testBitSet.get(0));
    }
}