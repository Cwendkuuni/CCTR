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
        assertTrue(emptyPointer.mayMatchProperty());
        assertNull(emptyPointer.matchProperty("any"));
        assertNull(emptyPointer.matchElement(0));
        assertNull(emptyPointer.tail());
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
        assertNull(pointer.matchProperty("bar"));
        assertNull(pointer.matchElement(0));
        assertNotNull(pointer.tail());
        assertEquals("/foo", pointer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompileInvalidPointer() {
        JsonPointer.compile("foo");
    }

    @Test
    public void testValueOf() {
        JsonPointer pointer = JsonPointer.valueOf("/foo");
        assertEquals("/foo", pointer.toString());
    }

    @Test
    public void testMatchProperty() {
        JsonPointer pointer = JsonPointer.compile("/foo");
        assertNotNull(pointer.matchProperty("foo"));
        assertNull(pointer.matchProperty("bar"));
    }

    @Test
    public void testMatchElement() {
        JsonPointer pointer = JsonPointer.compile("/0");
        assertNotNull(pointer.matchElement(0));
        assertNull(pointer.matchElement(1));
    }

    @Test
    public void testTail() {
        JsonPointer pointer = JsonPointer.compile("/foo/bar");
        JsonPointer tail = pointer.tail();
        assertEquals("/bar", tail.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        JsonPointer pointer1 = JsonPointer.compile("/foo");
        JsonPointer pointer2 = JsonPointer.compile("/foo");
        JsonPointer pointer3 = JsonPointer.compile("/bar");

        assertTrue(pointer1.equals(pointer2));
        assertFalse(pointer1.equals(pointer3));
        assertEquals(pointer1.hashCode(), pointer2.hashCode());
        assertNotEquals(pointer1.hashCode(), pointer3.hashCode());
    }

    @Test
    public void testParseIndex() {
        JsonPointer pointer = JsonPointer.compile("/123");
        assertEquals(123, pointer.getMatchingIndex());
    }

    @Test
    public void testParseQuotedTail() {
        JsonPointer pointer = JsonPointer.compile("/~0/~1");
        assertEquals("~", pointer.getMatchingProperty());
        JsonPointer tail = pointer.tail();
        assertEquals("/", tail.getMatchingProperty());
    }
}