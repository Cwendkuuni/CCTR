package org.scribe.model;

import org.junit.Test;
import static org.junit.Assert.*;
import org.scribe.model.OAuthConfig;
import org.scribe.model.SignatureType;

public class OAuthConfigTest {

    @Test
    public void testConstructorWithTwoParameters() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret");
        assertEquals("apiKey", config.getApiKey());
        assertEquals("apiSecret", config.getApiSecret());
        assertEquals("oob", config.getCallback());
        assertEquals(SignatureType.Header, config.getSignatureType());
        assertNull(config.getScope());
    }

    @Test
    public void testConstructorWithAllParameters() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret", "callback", SignatureType.QueryString, "scope");
        assertEquals("apiKey", config.getApiKey());
        assertEquals("apiSecret", config.getApiSecret());
        assertEquals("callback", config.getCallback());
        assertEquals(SignatureType.QueryString, config.getSignatureType());
        assertEquals("scope", config.getScope());
    }

    @Test
    public void testConstructorWithNullCallbackAndSignatureType() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret", null, null, "scope");
        assertEquals("apiKey", config.getApiKey());
        assertEquals("apiSecret", config.getApiSecret());
        assertEquals("oob", config.getCallback());
        assertEquals(SignatureType.Header, config.getSignatureType());
        assertEquals("scope", config.getScope());
    }

    @Test
    public void testGetApiKey() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret");
        assertEquals("apiKey", config.getApiKey());
    }

    @Test
    public void testGetApiSecret() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret");
        assertEquals("apiSecret", config.getApiSecret());
    }

    @Test
    public void testGetCallback() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret", "callback", null, null);
        assertEquals("callback", config.getCallback());
    }

    @Test
    public void testGetSignatureType() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret", null, SignatureType.QueryString, null);
        assertEquals(SignatureType.QueryString, config.getSignatureType());
    }

    @Test
    public void testGetScope() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret", null, null, "scope");
        assertEquals("scope", config.getScope());
    }

    @Test
    public void testHasScopeWhenScopeIsNotNull() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret", null, null, "scope");
        assertTrue(config.hasScope());
    }

    @Test
    public void testHasScopeWhenScopeIsNull() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret");
        assertFalse(config.hasScope());
    }
}