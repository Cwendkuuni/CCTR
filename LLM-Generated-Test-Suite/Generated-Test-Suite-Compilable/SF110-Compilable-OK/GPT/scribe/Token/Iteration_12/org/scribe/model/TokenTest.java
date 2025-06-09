package org.scribe.model;

import org.junit.Test;
import org.scribe.model.Token;

import static org.junit.Assert.*;

public class TokenTest {

    @Test
    public void testConstructorWithTwoParameters() {
        String tokenValue = "testToken";
        String secretValue = "testSecret";
        
        Token token = new Token(tokenValue, secretValue);
        
        assertEquals(tokenValue, token.getToken());
        assertEquals(secretValue, token.getSecret());
        try {
            token.getRawResponse();
            fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            assertEquals("This token object was not constructed by scribe and does not have a rawResponse", e.getMessage());
        }
    }

    @Test
    public void testConstructorWithThreeParameters() {
        String tokenValue = "testToken";
        String secretValue = "testSecret";
        String rawResponseValue = "testRawResponse";
        
        Token token = new Token(tokenValue, secretValue, rawResponseValue);
        
        assertEquals(tokenValue, token.getToken());
        assertEquals(secretValue, token.getSecret());
        assertEquals(rawResponseValue, token.getRawResponse());
    }

    @Test
    public void testGetToken() {
        String tokenValue = "testToken";
        Token token = new Token(tokenValue, "testSecret");
        
        assertEquals(tokenValue, token.getToken());
    }

    @Test
    public void testGetSecret() {
        String secretValue = "testSecret";
        Token token = new Token("testToken", secretValue);
        
        assertEquals(secretValue, token.getSecret());
    }

    @Test
    public void testGetRawResponseWithNull() {
        Token token = new Token("testToken", "testSecret");
        
        try {
            token.getRawResponse();
            fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            assertEquals("This token object was not constructed by scribe and does not have a rawResponse", e.getMessage());
        }
    }

    @Test
    public void testGetRawResponseWithNonNull() {
        String rawResponseValue = "testRawResponse";
        Token token = new Token("testToken", "testSecret", rawResponseValue);
        
        assertEquals(rawResponseValue, token.getRawResponse());
    }

    @Test
    public void testToString() {
        String tokenValue = "testToken";
        String secretValue = "testSecret";
        Token token = new Token(tokenValue, secretValue);
        
        assertEquals("Token[testToken , testSecret]", token.toString());
    }
}