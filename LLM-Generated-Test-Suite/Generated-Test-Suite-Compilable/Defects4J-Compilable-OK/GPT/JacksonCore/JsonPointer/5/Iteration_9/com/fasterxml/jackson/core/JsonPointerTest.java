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
        assertNull(emptyPointer.tail());
        assertEquals("", emptyPointer.toString());
    }

    @Test
    public void testCompileValidPointer() {
        JsonPointer pointer = JsonPointer.compile("/a/b/c");
        assertFalse(pointer.matches());
        assertEquals("a", pointer.getMatchingProperty());
        assertEquals(-1, pointer.getMatchingIndex());
        assertTrue(pointer.mayMatchProperty());
        assertFalse(pointer.mayMatchElement());
        assertNotNull(pointer.tail());
        assertEquals("/a/b/c", pointer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompileInvalidPointer() {
        JsonPointer.compile("a/b/c");
    }

    @Test
    public void testMatchProperty() {
        JsonPointer pointer = JsonPointer.compile("/a/b/c");
        JsonPointer next = pointer.matchProperty("a");
        assertNotNull(next);
        assertEquals("b", next.getMatchingProperty());
        assertNull(pointer.matchProperty("x"));
    }

    @Test
    public void testMatchElement() {
        JsonPointer pointer = JsonPointer.compile("/0/1/2");
        JsonPointer next = pointer.matchElement(0);
        assertNotNull(next);
        assertEquals(1, next.getMatchingIndex());
        assertNull(pointer.matchElement(1));
    }

    @Test
    public void testEqualsAndHashCode() {
        JsonPointer pointer1 = JsonPointer.compile("/a/b/c");
        JsonPointer pointer2 = JsonPointer.compile("/a/b/c");
        JsonPointer pointer3 = JsonPointer.compile("/x/y/z");

        assertEquals(pointer1, pointer2);
        assertNotEquals(pointer1, pointer3);
        assertEquals(pointer1.hashCode(), pointer2.hashCode());
        assertNotEquals(pointer1.hashCode(), pointer3.hashCode());
    }

    @Test
    public void testValueOf() {
        JsonPointer pointer = JsonPointer.valueOf("/a/b/c");
        assertEquals("/a/b/c", pointer.toString());
    }

    @Test
    public void testParseIndex() {
        JsonPointer pointer = JsonPointer.compile("/0");
        assertEquals(0, pointer.getMatchingIndex());

        pointer = JsonPointer.compile("/10");
        assertEquals(10, pointer.getMatchingIndex());

        pointer = JsonPointer.compile("/-");
        assertEquals(-1, pointer.getMatchingIndex());
    }

    @Test
    public void testQuotedTail() {
        JsonPointer pointer = JsonPointer.compile("/~0/~1");
        assertEquals("~", pointer.getMatchingProperty());
        JsonPointer next = pointer.tail();
        assertEquals("/", next.getMatchingProperty());
    }
}