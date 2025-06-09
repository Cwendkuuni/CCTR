package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonPointer;
import org.junit.Test;
import static org.junit.Assert.*;

public class JsonPointerTest {

    @Test
    public void testEmptyPointer() {
        JsonPointer emptyPointer = JsonPointer.compile("");
        assertTrue(emptyPointer.matches());
        assertEquals("", emptyPointer.getMatchingProperty());
        assertEquals(-1, emptyPointer.getMatchingIndex());
        assertFalse(emptyPointer.mayMatchElement());
        assertTrue(emptyPointer.mayMatchProperty());
        assertNull(emptyPointer.matchProperty("any"));
        assertNull(emptyPointer.matchElement(0));
        assertEquals("", emptyPointer.toString());
        assertEquals(emptyPointer, JsonPointer.compile(""));
    }

    @Test
    public void testCompileValidPointer() {
        JsonPointer pointer = JsonPointer.compile("/foo/bar");
        assertFalse(pointer.matches());
        assertEquals("foo", pointer.getMatchingProperty());
        assertEquals(-1, pointer.getMatchingIndex());
        assertTrue(pointer.mayMatchProperty());
        assertFalse(pointer.mayMatchElement());
        assertNotNull(pointer.tail());
        assertEquals("/foo/bar", pointer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompileInvalidPointer() {
        JsonPointer.compile("foo/bar");
    }

    @Test
    public void testMatchProperty() {
        JsonPointer pointer = JsonPointer.compile("/foo/bar");
        JsonPointer next = pointer.matchProperty("foo");
        assertNotNull(next);
        assertEquals("bar", next.getMatchingProperty());
        assertNull(pointer.matchProperty("bar"));
    }

    @Test
    public void testMatchElement() {
        JsonPointer pointer = JsonPointer.compile("/0/bar");
        JsonPointer next = pointer.matchElement(0);
        assertNotNull(next);
        assertEquals("bar", next.getMatchingProperty());
        assertNull(pointer.matchElement(1));
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
    public void testTail() {
        JsonPointer pointer = JsonPointer.compile("/foo/bar");
        JsonPointer tail = pointer.tail();
        assertEquals("/bar", tail.toString());
        assertEquals("bar", tail.getMatchingProperty());
    }

    @Test
    public void testParseIndex() {
        JsonPointer pointer = JsonPointer.compile("/123");
        assertEquals(123, pointer.getMatchingIndex());
        assertTrue(pointer.mayMatchElement());
    }

    @Test
    public void testParseQuotedTail() {
        JsonPointer pointer = JsonPointer.compile("/foo~1bar");
        assertEquals("foo/bar", pointer.getMatchingProperty());
    }

    @Test
    public void testAppendEscape() {
        JsonPointer pointer = JsonPointer.compile("/foo~0bar");
        assertEquals("foo~bar", pointer.getMatchingProperty());
    }
}