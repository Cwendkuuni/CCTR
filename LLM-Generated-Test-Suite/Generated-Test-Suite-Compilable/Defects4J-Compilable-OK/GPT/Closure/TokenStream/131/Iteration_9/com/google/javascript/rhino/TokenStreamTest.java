package com.google.javascript.rhino;

import static org.junit.Assert.*;
import org.junit.Test;

public class TokenStreamTest {

    @Test
    public void testIsKeyword() {
        // Test with known JavaScript keywords
        assertTrue(TokenStream.isKeyword("if"));
        assertTrue(TokenStream.isKeyword("for"));
        assertTrue(TokenStream.isKeyword("while"));
        assertTrue(TokenStream.isKeyword("return"));
        assertTrue(TokenStream.isKeyword("function"));
        assertTrue(TokenStream.isKeyword("var"));
        assertTrue(TokenStream.isKeyword("const"));
        assertTrue(TokenStream.isKeyword("let"));
        assertTrue(TokenStream.isKeyword("null"));
        assertTrue(TokenStream.isKeyword("true"));
        assertTrue(TokenStream.isKeyword("false"));
        assertTrue(TokenStream.isKeyword("switch"));
        assertTrue(TokenStream.isKeyword("case"));
        assertTrue(TokenStream.isKeyword("break"));
        assertTrue(TokenStream.isKeyword("continue"));
        assertTrue(TokenStream.isKeyword("default"));
        assertTrue(TokenStream.isKeyword("throw"));
        assertTrue(TokenStream.isKeyword("catch"));
        assertTrue(TokenStream.isKeyword("try"));
        assertTrue(TokenStream.isKeyword("finally"));
        assertTrue(TokenStream.isKeyword("new"));
        assertTrue(TokenStream.isKeyword("delete"));
        assertTrue(TokenStream.isKeyword("typeof"));
        assertTrue(TokenStream.isKeyword("void"));
        assertTrue(TokenStream.isKeyword("instanceof"));
        assertTrue(TokenStream.isKeyword("in"));
        assertTrue(TokenStream.isKeyword("do"));
        assertTrue(TokenStream.isKeyword("with"));

        // Test with non-keywords
        assertFalse(TokenStream.isKeyword("hello"));
        assertFalse(TokenStream.isKeyword("world"));
        assertFalse(TokenStream.isKeyword("java"));
        assertFalse(TokenStream.isKeyword("javascript"));
        assertFalse(TokenStream.isKeyword("identifier"));
        assertFalse(TokenStream.isKeyword("keyword"));
        assertFalse(TokenStream.isKeyword("123"));
        assertFalse(TokenStream.isKeyword("!@#"));
    }

    @Test
    public void testIsJSIdentifier() {
        // Test with valid JavaScript identifiers
        assertTrue(TokenStream.isJSIdentifier("a"));
        assertTrue(TokenStream.isJSIdentifier("abc"));
        assertTrue(TokenStream.isJSIdentifier("a1"));
        assertTrue(TokenStream.isJSIdentifier("_abc"));
        assertTrue(TokenStream.isJSIdentifier("$abc"));
        assertTrue(TokenStream.isJSIdentifier("a_b_c"));
        assertTrue(TokenStream.isJSIdentifier("a$b$c"));
        assertTrue(TokenStream.isJSIdentifier("a1b2c3"));

        // Test with invalid JavaScript identifiers
        assertFalse(TokenStream.isJSIdentifier(""));
        assertFalse(TokenStream.isJSIdentifier("1abc"));
        assertFalse(TokenStream.isJSIdentifier("!abc"));
        assertFalse(TokenStream.isJSIdentifier("@abc"));
        assertFalse(TokenStream.isJSIdentifier("#abc"));
        assertFalse(TokenStream.isJSIdentifier("123"));
        assertFalse(TokenStream.isJSIdentifier("!@#"));
        assertFalse(TokenStream.isJSIdentifier(" "));
    }
}