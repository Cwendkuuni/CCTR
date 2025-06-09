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
        BitSet chars = new BitSet();
        chars.set('a');
        chars.set('b');
        chars.set('c');
        String description = "abc";
        SmallCharMatcher matcher = (SmallCharMatcher) SmallCharMatcher.from(chars, description);

        assertTrue(matcher.matches('a'));
        assertTrue(matcher.matches('b'));
        assertTrue(matcher.matches('c'));
        assertFalse(matcher.matches('d'));
    }

    @Test
    public void testMatches() {
        BitSet chars = new BitSet();
        chars.set('x');
        chars.set('y');
        chars.set('z');
        SmallCharMatcher matcher = (SmallCharMatcher) SmallCharMatcher.from(chars, "xyz");

        assertTrue(matcher.matches('x'));
        assertTrue(matcher.matches('y'));
        assertTrue(matcher.matches('z'));
        assertFalse(matcher.matches('a'));
        assertFalse(matcher.matches('\0'));
    }

    @Test
    public void testMatchesWithZero() {
        BitSet chars = new BitSet();
        chars.set(0);
        chars.set('m');
        chars.set('n');
        SmallCharMatcher matcher = (SmallCharMatcher) SmallCharMatcher.from(chars, "mn");

        assertTrue(matcher.matches('\0'));
        assertTrue(matcher.matches('m'));
        assertTrue(matcher.matches('n'));
        assertFalse(matcher.matches('o'));
    }

    @Test
    public void testSetBits() {
        BitSet chars = new BitSet();
        chars.set('1');
        chars.set('2');
        chars.set('3');
        SmallCharMatcher matcher = (SmallCharMatcher) SmallCharMatcher.from(chars, "123");

        BitSet table = new BitSet();
        matcher.setBits(table);

        assertTrue(table.get('1'));
        assertTrue(table.get('2'));
        assertTrue(table.get('3'));
        assertFalse(table.get('4'));
    }

    @Test
    public void testSetBitsWithZero() {
        BitSet chars = new BitSet();
        chars.set(0);
        chars.set('a');
        chars.set('b');
        SmallCharMatcher matcher = (SmallCharMatcher) SmallCharMatcher.from(chars, "ab");

        BitSet table = new BitSet();
        matcher.setBits(table);

        assertTrue(table.get(0));
        assertTrue(table.get('a'));
        assertTrue(table.get('b'));
        assertFalse(table.get('c'));
    }
}