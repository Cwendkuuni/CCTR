package com.pmdesigns.jvc.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TokenMgrErrorTest {

    @Test
    public void testAddEscapes() {
        assertEquals("", TokenMgrError.addEscapes(""));
        assertEquals("\\b", TokenMgrError.addEscapes("\b"));
        assertEquals("\\t", TokenMgrError.addEscapes("\t"));
        assertEquals("\\n", TokenMgrError.addEscapes("\n"));
        assertEquals("\\f", TokenMgrError.addEscapes("\f"));
        assertEquals("\\r", TokenMgrError.addEscapes("\r"));
        assertEquals("\\\"", TokenMgrError.addEscapes("\""));
        assertEquals("\\'", TokenMgrError.addEscapes("'"));
        assertEquals("\\\\", TokenMgrError.addEscapes("\\"));
        assertEquals("abc", TokenMgrError.addEscapes("abc"));
        assertEquals("\\u0000", TokenMgrError.addEscapes("\0"));
        assertEquals("\\u007f", TokenMgrError.addEscapes("\u007f"));
    }

    @Test
    public void testLexicalError() {
        String expected = "Lexical error at line 1, column 2.  Encountered: \"a\" (97), after : \"test\"";
        assertEquals(expected, TokenMgrError.LexicalError(false, 0, 1, 2, "test", 'a'));

        expected = "Lexical error at line 1, column 2.  Encountered: <EOF> after : \"test\"";
        assertEquals(expected, TokenMgrError.LexicalError(true, 0, 1, 2, "test", 'a'));
    }

    @Test
    public void testDefaultConstructor() {
        TokenMgrError error = new TokenMgrError();
        assertTrue(error instanceof TokenMgrError);
    }

    @Test
    public void testConstructorWithMessageAndReason() {
        TokenMgrError error = new TokenMgrError("Test message", TokenMgrError.LEXICAL_ERROR);
        assertEquals("Test message", error.getMessage());
        assertEquals(TokenMgrError.LEXICAL_ERROR, error.errorCode);
    }

    @Test
    public void testConstructorWithLexicalError() {
        TokenMgrError error = new TokenMgrError(false, 0, 1, 2, "test", 'a', TokenMgrError.LEXICAL_ERROR);
        String expectedMessage = "Lexical error at line 1, column 2.  Encountered: \"a\" (97), after : \"test\"";
        assertEquals(expectedMessage, error.getMessage());
        assertEquals(TokenMgrError.LEXICAL_ERROR, error.errorCode);
    }
}