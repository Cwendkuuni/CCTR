package org.scribe.model;

import org.junit.Before;
import org.junit.Test;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;

import java.util.Map;

import static org.junit.Assert.*;

public class OAuthRequestTest {

    private OAuthRequest request;

    @Before
    public void setUp() {
        request = new OAuthRequest(Verb.GET, "http://example.com");
    }

    @Test
    public void testConstructor() {
        assertNotNull(request);
        assertEquals(Verb.GET, request.getVerb());
        assertEquals("http://example.com", request.getUrl());
        assertTrue(request.getOauthParameters().isEmpty());
    }

    @Test
    public void testAddOAuthParameterWithValidKey() {
        request.addOAuthParameter("oauth_token", "tokenValue");
        Map<String, String> params = request.getOauthParameters();
        assertEquals(1, params.size());
        assertEquals("tokenValue", params.get("oauth_token"));
    }

    @Test
    public void testAddOAuthParameterWithScopeKey() {
        request.addOAuthParameter("scope", "read");
        Map<String, String> params = request.getOauthParameters();
        assertEquals(1, params.size());
        assertEquals("read", params.get("scope"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddOAuthParameterWithInvalidKey() {
        request.addOAuthParameter("invalid_key", "value");
    }

    @Test
    public void testGetOauthParameters() {
        request.addOAuthParameter("oauth_token", "tokenValue");
        request.addOAuthParameter("scope", "read");
        Map<String, String> params = request.getOauthParameters();
        assertEquals(2, params.size());
        assertEquals("tokenValue", params.get("oauth_token"));
        assertEquals("read", params.get("scope"));
    }

    @Test
    public void testToString() {
        String expected = "@OAuthRequest(GET, http://example.com)";
        assertEquals(expected, request.toString());
    }
}