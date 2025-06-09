package org.scribe.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TokenTest {

    private Token token;
    private Token tokenWithRawResponse;

    @Before
    public void setUp() {
        token = new Token("testToken", "testSecret");
        tokenWithRawResponse = new Token("testToken", "testSecret", "testRawResponse");
    }

    @Test
    public void testConstructorWithoutRawResponse() {
        assertNotNull(token);
        assertEquals("testToken", token.getToken());
        assertEquals("testSecret", token.getSecret());
        assertNull(token.getRawResponse());
    }

    @Test
    public void testConstructorWithRawResponse() {
        assertNotNull(tokenWithRawResponse);
        assertEquals("testToken", tokenWithRawResponse.getToken());
        assertEquals("testSecret", tokenWithRawResponse.getSecret());
        assertEquals("testRawResponse", tokenWithRawResponse.getRawResponse());
    }

    @Test
    public void testGetToken() {
        assertEquals("testToken", token.getToken());
    }

    @Test
    public void testGetSecret() {
        assertEquals("testSecret", token.getSecret());
    }

    @Test
    public void testGetRawResponse() {
        assertEquals("testRawResponse", tokenWithRawResponse.getRawResponse());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetRawResponseThrowsException() {
        token.getRawResponse();
    }

    @Test
    public void testToString() {
        assertEquals("Token[testToken , testSecret]", token.toString());
    }
}