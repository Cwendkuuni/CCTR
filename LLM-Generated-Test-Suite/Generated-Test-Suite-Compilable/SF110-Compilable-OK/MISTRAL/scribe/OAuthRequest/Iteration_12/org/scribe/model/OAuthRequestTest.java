package org.scribe.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class OAuthRequestTest {

    private OAuthRequest oAuthRequest;

    @Before
    public void setUp() {
        oAuthRequest = new OAuthRequest(Verb.GET, "http://example.com");
    }

    @Test
    public void testConstructor() {
        assertNotNull(oAuthRequest);
        assertEquals(Verb.GET, oAuthRequest.getVerb());
        assertEquals("http://example.com", oAuthRequest.getUrl());
        assertNotNull(oAuthRequest.getOauthParameters());
        assertTrue(oAuthRequest.getOauthParameters().isEmpty());
    }

    @Test
    public void testAddOAuthParameter() {
        oAuthRequest.addOAuthParameter("oauth_token", "token123");
        assertEquals("token123", oAuthRequest.getOauthParameters().get("oauth_token"));

        oAuthRequest.addOAuthParameter("scope", "read write");
        assertEquals("read write", oAuthRequest.getOauthParameters().get("scope"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddOAuthParameterInvalidKey() {
        oAuthRequest.addOAuthParameter("invalid_key", "value");
    }

    @Test
    public void testGetOauthParameters() {
        Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("oauth_token", "token123");
        expectedParameters.put("scope", "read write");

        oAuthRequest.addOAuthParameter("oauth_token", "token123");
        oAuthRequest.addOAuthParameter("scope", "read write");

        assertEquals(expectedParameters, oAuthRequest.getOauthParameters());
    }

    @Test
    public void testToString() {
        String expectedString = "@OAuthRequest(GET, http://example.com)";
        assertEquals(expectedString, oAuthRequest.toString());
    }
}