package com.fasterxml.jackson.core;

import static org.junit.Assert.*;
import org.junit.Test;

public class JsonPointerTest {

    @Test
    public void testEmptyPointer() {
        JsonPointer pointer = JsonPointer.compile("");
        assertTrue(pointer.matches());
        assertEquals("", pointer.getMatchingProperty());
        assertEquals(-1, pointer.getMatchingIndex());
        assertFalse(pointer.mayMatchElement());
        assertTrue(pointer.mayMatchProperty());
        assertNull(pointer.matchProperty("any"));
        assertNull(pointer.matchElement(0));
        assertNull(pointer.tail());
        assertEquals("", pointer.toString());
    }

    @Test
    public void testSingleSegmentPointer() {
        JsonPointer pointer = JsonPointer.compile("/foo");
        assertFalse(pointer.matches());
        assertEquals("foo", pointer.getMatchingProperty());
        assertEquals(-1, pointer.getMatchingIndex());
        assertFalse(pointer.mayMatchElement());
        assertTrue(pointer.mayMatchProperty());

        JsonPointer next = pointer.matchProperty("foo");
        assertNotNull(next);
        assertTrue(next.matches());

        assertNull(pointer.matchProperty("bar"));
        assertNull(pointer.matchElement(0));

        assertEquals("/foo", pointer.toString());
    }

    @Test
    public void testMultiSegmentPointer() {
        JsonPointer pointer = JsonPointer.compile("/foo/bar");
        assertFalse(pointer.matches());
        assertEquals("foo", pointer.getMatchingProperty());
        assertEquals(-1, pointer.getMatchingIndex());
        assertFalse(pointer.mayMatchElement());
        assertTrue(pointer.mayMatchProperty());

        JsonPointer next = pointer.matchProperty("foo");
        assertNotNull(next);
        assertFalse(next.matches());
        assertEquals("bar", next.getMatchingProperty());

        next = next.matchProperty("bar");
        assertNotNull(next);
        assertTrue(next.matches());

        assertNull(pointer.matchProperty("baz"));
        assertNull(pointer.matchElement(0));

        assertEquals("/foo/bar", pointer.toString());
    }

    @Test
    public void testElementIndexPointer() {
        JsonPointer pointer = JsonPointer.compile("/0");
        assertFalse(pointer.matches());
        assertEquals("0", pointer.getMatchingProperty());
        assertEquals(0, pointer.getMatchingIndex());
        assertTrue(pointer.mayMatchElement());
        assertTrue(pointer.mayMatchProperty());

        JsonPointer next = pointer.matchElement(0);
        assertNotNull(next);
        assertTrue(next.matches());

        assertNull(pointer.matchProperty("foo"));
        assertNull(pointer.matchElement(1));

        assertEquals("/0", pointer.toString());
    }

    @Test
    public void testInvalidPointer() {
        try {
            JsonPointer.compile("foo");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid input: JSON Pointer expression must start with '/': \"foo\"", e.getMessage());
        }
    }

    @Test
    public void testEscapedCharacters() {
        JsonPointer pointer = JsonPointer.compile("/foo~1bar");
        assertFalse(pointer.matches());
        assertEquals("foo/bar", pointer.getMatchingProperty());
        assertEquals(-1, pointer.getMatchingIndex());
        assertFalse(pointer.mayMatchElement());
        assertTrue(pointer.mayMatchProperty());

        assertEquals("/foo~1bar", pointer.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        JsonPointer pointer1 = JsonPointer.compile("/foo");
        JsonPointer pointer2 = JsonPointer.compile("/foo");
        JsonPointer pointer3 = JsonPointer.compile("/bar");

        assertEquals(pointer1, pointer2);
        assertEquals(pointer1.hashCode(), pointer2.hashCode());
        assertNotEquals(pointer1, pointer3);
        assertNotEquals(pointer1.hashCode(), pointer3.hashCode());
    }

    @Test
    public void testValueOf() {
        JsonPointer pointer1 = JsonPointer.compile("/foo");
        JsonPointer pointer2 = JsonPointer.valueOf("/foo");

        assertEquals(pointer1, pointer2);
    }
}