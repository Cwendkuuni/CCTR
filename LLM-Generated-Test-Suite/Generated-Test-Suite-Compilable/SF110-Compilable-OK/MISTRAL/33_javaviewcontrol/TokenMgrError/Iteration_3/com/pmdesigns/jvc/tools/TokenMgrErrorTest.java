package com.pmdesigns.jvc.tools;

import org.junit.Test;
import static org.junit.Assert.*;

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
        String expected = "Lexical error at line 1, column 1.  Encountered: \"a\" (97), after : \"\"";
        assertEquals(expected, TokenMgrError.LexicalError(false, 0, 1, 1, "", 'a'));

        expected = "Lexical error at line 1, column 1.  Encountered: <EOF> , after : \"\"";
        assertEquals(expected, TokenMgrError.LexicalError(true, 0, 1, 1, "", '\0'));
    }

    @Test
    public void testGetMessage() {
        TokenMgrError error = new TokenMgrError("Test Message", 0);
        assertEquals("Test Message", error.getMessage());
    }

    @Test
    public void testConstructorNoArgs() {
        TokenMgrError error = new TokenMgrError();
        assertNull(error.getMessage());
        assertEquals(0, error.errorCode);
    }

    @Test
    public void testConstructorWithMessageAndReason() {
        TokenMgrError error = new TokenMgrError("Test Message", 1);
        assertEquals("Test Message", error.getMessage());
        assertEquals(1, error.errorCode);
    }

    @Test
    public void testConstructorWithLexicalError() {
        TokenMgrError error = new TokenMgrError(false, 0, 1, 1, "", 'a', 2);
        String expectedMessage = "Lexical error at line 1, column 1.  Encountered: \"a\" (97), after : \"\"";
        assertEquals(expectedMessage, error.getMessage());
        assertEquals(2, error.errorCode);
    }
}