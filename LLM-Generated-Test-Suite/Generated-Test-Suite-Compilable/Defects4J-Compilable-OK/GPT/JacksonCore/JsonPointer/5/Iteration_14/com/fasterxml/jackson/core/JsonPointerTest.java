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
    public void testCompileAndValueOf() {
        String input = "/foo/bar";
        JsonPointer pointer1 = JsonPointer.compile(input);
        JsonPointer pointer2 = JsonPointer.valueOf(input);
        assertEquals(pointer1, pointer2);
        assertEquals(input, pointer1.toString());
    }

    @Test
    public void testMatchProperty() {
        JsonPointer pointer = JsonPointer.compile("/foo/bar");
        JsonPointer next = pointer.matchProperty("foo");
        assertNotNull(next);
        assertEquals("bar", next.getMatchingProperty());
        assertNull(pointer.matchProperty("baz"));
    }

    @Test
    public void testMatchElement() {
        JsonPointer pointer = JsonPointer.compile("/0/1");
        JsonPointer next = pointer.matchElement(0);
        assertNotNull(next);
        assertEquals(1, next.getMatchingIndex());
        assertNull(pointer.matchElement(1));
    }

    @Test
    public void testMayMatchProperty() {
        JsonPointer pointer = JsonPointer.compile("/foo");
        assertTrue(pointer.mayMatchProperty());
    }

    @Test
    public void testMayMatchElement() {
        JsonPointer pointer = JsonPointer.compile("/0");
        assertTrue(pointer.mayMatchElement());
    }

    @Test
    public void testTail() {
        JsonPointer pointer = JsonPointer.compile("/foo/bar");
        JsonPointer tail = pointer.tail();
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
    public void testParseIndex() {
        JsonPointer pointer = JsonPointer.compile("/123");
        assertEquals(123, pointer.getMatchingIndex());
        
        pointer = JsonPointer.compile("/foo");
        assertEquals(-1, pointer.getMatchingIndex());
    }

    @Test
    public void testQuotedTail() {
        JsonPointer pointer = JsonPointer.compile("/~0/~1");
        assertEquals("~", pointer.matchProperty("~").getMatchingProperty());
        assertEquals("/", pointer.matchProperty("~").matchProperty("/").getMatchingProperty());
    }
}