package org.scribe.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class TokenTest {

    @Test
    public void testConstructorWithTokenAndSecret() {
        String token = "testToken";
        String secret = "testSecret";
        Token tokenObj = new Token(token, secret);

        assertEquals(token, tokenObj.getToken());
        assertEquals(secret, tokenObj.getSecret());
        assertNull(tokenObj.getRawResponse());
    }

    @Test
    public void testConstructorWithTokenSecretAndRawResponse() {
        String token = "testToken";
        String secret = "testSecret";
        String rawResponse = "testRawResponse";
        Token tokenObj = new Token(token, secret, rawResponse);

        assertEquals(token, tokenObj.getToken());
        assertEquals(secret, tokenObj.getSecret());
        assertEquals(rawResponse, tokenObj.getRawResponse());
    }

    @Test
    public void testGetToken() {
        String token = "testToken";
        String secret = "testSecret";
        Token tokenObj = new Token(token, secret);

        assertEquals(token, tokenObj.getToken());
    }

    @Test
    public void testGetSecret() {
        String token = "testToken";
        String secret = "testSecret";
        Token tokenObj = new Token(token, secret);

        assertEquals(secret, tokenObj.getSecret());
    }

    @Test
    public void testGetRawResponse() {
        String token = "testToken";
        String secret = "testSecret";
        String rawResponse = "testRawResponse";
        Token tokenObj = new Token(token, secret, rawResponse);

        assertEquals(rawResponse, tokenObj.getRawResponse());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetRawResponseThrowsException() {
        String token = "testToken";
        String secret = "testSecret";
        Token tokenObj = new Token(token, secret);

        tokenObj.getRawResponse();
    }

    @Test
    public void testToString() {
        String token = "testToken";
        String secret = "testSecret";
        Token tokenObj = new Token(token, secret);

        assertEquals("Token[testToken , testSecret]", tokenObj.toString());
    }
}