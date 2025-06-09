package com.pmdesigns.jvc.tools;

import static org.junit.Assert.*;
import org.junit.Test;

public class TokenMgrErrorTest {

    @Test
    public void testAddEscapes() {
        assertEquals("\\b\\t\\n\\f\\r\\\"\\'\\\\", TokenMgrError.addEscapes("\b\t\n\f\r\"\'\\"));
        assertEquals("\\u0000", TokenMgrError.addEscapes("\0"));
        assertEquals("A", TokenMgrError.addEscapes("A"));
        assertEquals("\\u00e9", TokenMgrError.addEscapes("\u00e9"));
    }

    @Test
    public void testLexicalError() {
        String expected = "Lexical error at line 1, column 1.  Encountered: \"\\t\" (9), after : \"\"";
        assertEquals(expected, TokenMgrError.LexicalError(false, 0, 1, 1, "", '\t'));

        expected = "Lexical error at line 2, column 3.  Encountered: <EOF> , after : \"abc\"";
        assertEquals(expected, TokenMgrError.LexicalError(true, 0, 2, 3, "abc", '\0'));
    }

    @Test
    public void testGetMessage() {
        TokenMgrError error = new TokenMgrError("Test Message", 0);
        assertEquals("Test Message", error.getMessage());
    }

    @Test
    public void testTokenMgrErrorConstructor() {
        TokenMgrError error = new TokenMgrError();
        assertNull(error.getMessage());
        assertEquals(0, error.errorCode);

        error = new TokenMgrError("Test Message", 1);
        assertEquals("Test Message", error.getMessage());
        assertEquals(1, error.errorCode);

        error = new TokenMgrError(false, 0, 1, 1, "", '\t', 2);
        String expected = "Lexical error at line 1, column 1.  Encountered: \"\\t\" (9), after : \"\"";
        assertEquals(expected, error.getMessage());
        assertEquals(2, error.errorCode);
    }
}