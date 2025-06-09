package com.fasterxml.jackson.core;

import static org.junit.Assert.*;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonPointer;

public class JsonPointerTest {

    @Test
    public void testEmptyPointer() {
        JsonPointer emptyPointer = JsonPointer.compile("");
        assertTrue(emptyPointer.matches());
        assertEquals("", emptyPointer.toString());
        assertEquals(-1, emptyPointer.getMatchingIndex());
        assertEquals("", emptyPointer.getMatchingProperty());
        assertNull(emptyPointer.tail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPointer() {
        JsonPointer.compile("invalid");
    }

    @Test
    public void testCompileValidPointer() {
        JsonPointer pointer = JsonPointer.compile("/foo/bar");
        assertFalse(pointer.matches());
        assertEquals("/foo/bar", pointer.toString());
        assertEquals("foo", pointer.getMatchingProperty());
        assertTrue(pointer.mayMatchProperty());
        assertFalse(pointer.mayMatchElement());
    }

    @Test
    public void testCompileWithIndex() {
        JsonPointer pointer = JsonPointer.compile("/0");
        assertFalse(pointer.matches());
        assertEquals("/0", pointer.toString());
        assertEquals(0, pointer.getMatchingIndex());
        assertTrue(pointer.mayMatchElement());
    }

    @Test
    public void testMatchProperty() {
        JsonPointer pointer = JsonPointer.compile("/foo/bar");
        JsonPointer next = pointer.matchProperty("foo");
        assertNotNull(next);
        assertEquals("bar", next.getMatchingProperty());
    }

    @Test
    public void testMatchElement() {
        JsonPointer pointer = JsonPointer.compile("/0/bar");
        JsonPointer next = pointer.matchElement(0);
        assertNotNull(next);
        assertEquals("bar", next.getMatchingProperty());
    }

    @Test
    public void testTail() {
        JsonPointer pointer = JsonPointer.compile("/foo/bar");
        JsonPointer tail = pointer.tail();
        assertNotNull(tail);
        assertEquals("/bar", tail.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        JsonPointer pointer1 = JsonPointer.compile("/foo/bar");
        JsonPointer pointer2 = JsonPointer.compile("/foo/bar");
        JsonPointer pointer3 = JsonPointer.compile("/foo/baz");

        assertEquals(pointer1, pointer2);
        assertNotEquals(pointer1, pointer3);
        assertEquals(pointer1.hashCode(), pointer2.hashCode());
        assertNotEquals(pointer1.hashCode(), pointer3.hashCode());
    }

    @Test
    public void testValueOf() {
        JsonPointer pointer = JsonPointer.valueOf("/foo/bar");
        assertEquals("/foo/bar", pointer.toString());
    }

    @Test
    public void testParseQuotedTail() {
        JsonPointer pointer = JsonPointer.compile("/foo~1bar");
        assertEquals("foo/bar", pointer.getMatchingProperty());
    }

    @Test
    public void testParseQuotedTailWithTilde() {
        JsonPointer pointer = JsonPointer.compile("/foo~0bar");
        assertEquals("foo~bar", pointer.getMatchingProperty());
    }

    @Test
    public void testParseIndex() {
        JsonPointer pointer = JsonPointer.compile("/123");
        assertEquals(123, pointer.getMatchingIndex());
    }

    @Test
    public void testParseIndexWithLeadingZero() {
        JsonPointer pointer = JsonPointer.compile("/0123");
        assertEquals(-1, pointer.getMatchingIndex());
    }

    @Test
    public void testParseIndexTooLong() {
        JsonPointer pointer = JsonPointer.compile("/12345678901");
        assertEquals(-1, pointer.getMatchingIndex());
    }
}