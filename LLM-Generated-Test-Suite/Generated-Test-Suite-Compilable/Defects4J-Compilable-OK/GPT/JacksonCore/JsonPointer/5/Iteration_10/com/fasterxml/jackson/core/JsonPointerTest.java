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
        assertNull(emptyPointer.tail());
    }

    @Test
    public void testCompileValidPointer() {
        JsonPointer pointer = JsonPointer.compile("/foo/bar");
        assertFalse(pointer.matches());
        assertEquals("foo", pointer.getMatchingProperty());
        assertTrue(pointer.mayMatchProperty());
        assertFalse(pointer.mayMatchElement());

        JsonPointer tail = pointer.tail();
        assertNotNull(tail);
        assertEquals("bar", tail.getMatchingProperty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompileInvalidPointer() {
        JsonPointer.compile("foo/bar");
    }

    @Test
    public void testValueOf() {
        JsonPointer pointer = JsonPointer.valueOf("/foo/bar");
        assertEquals(JsonPointer.compile("/foo/bar"), pointer);
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
    public void testToString() {
        JsonPointer pointer = JsonPointer.compile("/foo/bar");
        assertEquals("/foo/bar", pointer.toString());
    }

    @Test
    public void testHashCode() {
        JsonPointer pointer1 = JsonPointer.compile("/foo/bar");
        JsonPointer pointer2 = JsonPointer.compile("/foo/bar");
        assertEquals(pointer1.hashCode(), pointer2.hashCode());
    }

    @Test
    public void testEquals() {
        JsonPointer pointer1 = JsonPointer.compile("/foo/bar");
        JsonPointer pointer2 = JsonPointer.compile("/foo/bar");
        JsonPointer pointer3 = JsonPointer.compile("/foo/baz");

        assertTrue(pointer1.equals(pointer2));
        assertFalse(pointer1.equals(pointer3));
        assertFalse(pointer1.equals(null));
        assertFalse(pointer1.equals(new Object()));
    }

    @Test
    public void testParseIndex() {
        JsonPointer pointer = JsonPointer.compile("/0");
        assertEquals(0, pointer.getMatchingIndex());

        pointer = JsonPointer.compile("/10");
        assertEquals(10, pointer.getMatchingIndex());

        pointer = JsonPointer.compile("/foo");
        assertEquals(-1, pointer.getMatchingIndex());
    }

    @Test
    public void testQuotedTail() {
        JsonPointer pointer = JsonPointer.compile("/~0/~1");
        assertEquals("~", pointer.getMatchingProperty());
        JsonPointer tail = pointer.tail();
        assertEquals("/", tail.getMatchingProperty());
    }
}