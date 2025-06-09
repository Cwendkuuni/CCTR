package com.pmdesigns.jvc.tools;

import org.junit.Test;
import static org.junit.Assert.*;

public class TokenMgrErrorTest {

    @Test
    public void testAddEscapes() {
        assertEquals("\\b\\t\\n\\f\\r\\\"\\'\\\\", TokenMgrError.addEscapes("\b\t\n\f\r\"\'\\"));
        assertEquals("\\u0000", TokenMgrError.addEscapes("\0"));
        assertEquals("A", TokenMgrError.addEscapes("A"));
        assertEquals("\\u00e9", TokenMgrError.addEscapes("é"));
    }

    @Test
    public void testLexicalError() {
        String result = TokenMgrError.LexicalError(true, 0, 1, 1, "", '\0');
        assertTrue(result.contains("Lexical error at line 1, column 1.  Encountered: <EOF> after : \"\""));

        result = TokenMgrError.LexicalError(false, 0, 1, 1, "", 'a');
        assertTrue(result.contains("Lexical error at line 1, column 1.  Encountered: \"a\" (97), after : \"\""));
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

        error = new TokenMgrError(true, 0, 1, 1, "", '\0', 2);
        assertTrue(error.getMessage().contains("Lexical error at line 1, column 1.  Encountered: <EOF> after : \"\""));
        assertEquals(2, error.errorCode);
    }
}