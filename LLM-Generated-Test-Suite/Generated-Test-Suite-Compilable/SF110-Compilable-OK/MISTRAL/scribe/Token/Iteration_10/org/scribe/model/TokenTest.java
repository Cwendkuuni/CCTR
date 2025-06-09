package org.scribe.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TokenTest {

    private Token tokenWithRawResponse;
    private Token tokenWithoutRawResponse;

    @Before
    public void setUp() {
        tokenWithRawResponse = new Token("testToken", "testSecret", "testRawResponse");
        tokenWithoutRawResponse = new Token("testToken", "testSecret");
    }

    @Test
    public void testConstructorWithRawResponse() {
        assertEquals("testToken", tokenWithRawResponse.getToken());
        assertEquals("testSecret", tokenWithRawResponse.getSecret());
        assertEquals("testRawResponse", tokenWithRawResponse.getRawResponse());
    }

    @Test
    public void testConstructorWithoutRawResponse() {
        assertEquals("testToken", tokenWithoutRawResponse.getToken());
        assertEquals("testSecret", tokenWithoutRawResponse.getSecret());
        try {
            tokenWithoutRawResponse.getRawResponse();
            fail("Expected IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("This token object was not constructed by scribe and does not have a rawResponse", e.getMessage());
        }
    }

    @Test
    public void testGetToken() {
        assertEquals("testToken", tokenWithRawResponse.getToken());
        assertEquals("testToken", tokenWithoutRawResponse.getToken());
    }

    @Test
    public void testGetSecret() {
        assertEquals("testSecret", tokenWithRawResponse.getSecret());
        assertEquals("testSecret", tokenWithoutRawResponse.getSecret());
    }

    @Test
    public void testGetRawResponse() {
        assertEquals("testRawResponse", tokenWithRawResponse.getRawResponse());
        try {
            tokenWithoutRawResponse.getRawResponse();
            fail("Expected IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("This token object was not constructed by scribe and does not have a rawResponse", e.getMessage());
        }
    }

    @Test
    public void testToString() {
        assertEquals("Token[testToken , testSecret]", tokenWithRawResponse.toString());
        assertEquals("Token[testToken , testSecret]", tokenWithoutRawResponse.toString());
    }
}