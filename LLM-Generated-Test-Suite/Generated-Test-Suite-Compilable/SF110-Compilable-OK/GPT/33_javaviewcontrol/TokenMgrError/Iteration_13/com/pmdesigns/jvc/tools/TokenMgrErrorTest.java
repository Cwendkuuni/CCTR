package com.pmdesigns.jvc.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import com.pmdesigns.jvc.tools.TokenMgrError;

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
        assertEquals("\\u001F", TokenMgrError.addEscapes("\u001F"));
        assertEquals("~", TokenMgrError.addEscapes("~"));
        assertEquals("\\u007F", TokenMgrError.addEscapes("\u007F"));
    }

    @Test
    public void testLexicalError() {
        String expected = "Lexical error at line 1, column 1.  Encountered: \"a\" (97), after : \"\"";
        assertEquals(expected, TokenMgrError.LexicalError(false, 0, 1, 1, "", 'a'));

        expected = "Lexical error at line 2, column 3.  Encountered: <EOF> after : \"abc\"";
        assertEquals(expected, TokenMgrError.LexicalError(true, 0, 2, 3, "abc", '\0'));
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
        TokenMgrError error = new TokenMgrError(false, 0, 1, 1, "", 'a', TokenMgrError.LEXICAL_ERROR);
        String expectedMessage = "Lexical error at line 1, column 1.  Encountered: \"a\" (97), after : \"\"";
        assertEquals(expectedMessage, error.getMessage());
        assertEquals(TokenMgrError.LEXICAL_ERROR, error.errorCode);
    }
}