package com.fasterxml.jackson.core;

import static org.junit.Assert.*;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonPointer;

public class JsonPointerTest {

    @Test
    public void testEmptyPointer() {
        JsonPointer emptyPointer = JsonPointer.compile("");
        assertTrue(emptyPointer.matches());
        assertEquals("", emptyPointer.getMatchingProperty());
        assertEquals(-1, emptyPointer.getMatchingIndex());
        assertFalse(emptyPointer.mayMatchElement());
        assertFalse(emptyPointer.mayMatchProperty());
        assertNull(emptyPointer.matchProperty("any"));
        assertNull(emptyPointer.matchElement(0));
        assertEquals("", emptyPointer.toString());
    }

    @Test
    public void testCompileValidPointer() {
        JsonPointer pointer = JsonPointer.compile("/foo");
        assertFalse(pointer.matches());
        assertEquals("foo", pointer.getMatchingProperty());
        assertEquals(-1, pointer.getMatchingIndex());
        assertTrue(pointer.mayMatchProperty());
        assertFalse(pointer.mayMatchElement());
        assertNotNull(pointer.matchProperty("foo"));
        assertNull(pointer.matchElement(0));
        assertEquals("/foo", pointer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompileInvalidPointer() {
        JsonPointer.compile("foo");
    }

    @Test
    public void testCompilePointerWithIndex() {
        JsonPointer pointer = JsonPointer.compile("/0");
        assertFalse(pointer.matches());
        assertEquals("0", pointer.getMatchingProperty());
        assertEquals(0, pointer.getMatchingIndex());
        assertTrue(pointer.mayMatchElement());
        assertTrue(pointer.mayMatchProperty());
        assertNotNull(pointer.matchElement(0));
        assertNull(pointer.matchElement(1));
        assertEquals("/0", pointer.toString());
    }

    @Test
    public void testCompilePointerWithEscapedCharacters() {
        JsonPointer pointer = JsonPointer.compile("/~0/~1");
        assertFalse(pointer.matches());
        assertEquals("~", pointer.getMatchingProperty());
        assertEquals(-1, pointer.getMatchingIndex());
        assertTrue(pointer.mayMatchProperty());
        assertFalse(pointer.mayMatchElement());
        assertNotNull(pointer.matchProperty("~"));
        assertNull(pointer.matchElement(0));
        assertEquals("/~0/~1", pointer.toString());
    }

    @Test
    public void testTail() {
        JsonPointer pointer = JsonPointer.compile("/foo/bar");
        JsonPointer tail = pointer.tail();
        assertEquals("/bar", tail.toString());
        assertEquals("bar", tail.getMatchingProperty());
        assertNull(tail.tail().tail());
    }

    @Test
    public void testEqualsAndHashCode() {
        JsonPointer pointer1 = JsonPointer.compile("/foo/bar");
        JsonPointer pointer2 = JsonPointer.compile("/foo/bar");
        JsonPointer pointer3 = JsonPointer.compile("/foo/baz");

        assertTrue(pointer1.equals(pointer2));
        assertFalse(pointer1.equals(pointer3));
        assertEquals(pointer1.hashCode(), pointer2.hashCode());
        assertNotEquals(pointer1.hashCode(), pointer3.hashCode());
    }

    @Test
    public void testValueOf() {
        JsonPointer pointer = JsonPointer.valueOf("/foo");
        assertEquals("/foo", pointer.toString());
    }
}