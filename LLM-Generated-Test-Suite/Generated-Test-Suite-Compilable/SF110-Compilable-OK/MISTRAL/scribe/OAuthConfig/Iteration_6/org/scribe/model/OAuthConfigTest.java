package org.scribe.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class OAuthConfigTest {

    private OAuthConfig config;
    private OAuthConfig configWithCallback;
    private OAuthConfig configWithScope;

    @Before
    public void setUp() {
        config = new OAuthConfig("apiKey", "apiSecret");
        configWithCallback = new OAuthConfig("apiKey", "apiSecret", "callbackUrl", SignatureType.QueryString, null);
        configWithScope = new OAuthConfig("apiKey", "apiSecret", null, null, "scope");
    }

    @Test
    public void testConstructorWithKeyAndSecret() {
        assertEquals("apiKey", config.getApiKey());
        assertEquals("apiSecret", config.getApiSecret());
        assertEquals("oob", config.getCallback());
        assertEquals(SignatureType.Header, config.getSignatureType());
        assertNull(config.getScope());
        assertFalse(config.hasScope());
    }

    @Test
    public void testConstructorWithAllParameters() {
        assertEquals("apiKey", configWithCallback.getApiKey());
        assertEquals("apiSecret", configWithCallback.getApiSecret());
        assertEquals("callbackUrl", configWithCallback.getCallback());
        assertEquals(SignatureType.QueryString, configWithCallback.getSignatureType());
        assertNull(configWithCallback.getScope());
        assertFalse(configWithCallback.hasScope());
    }

    @Test
    public void testConstructorWithScope() {
        assertEquals("apiKey", configWithScope.getApiKey());
        assertEquals("apiSecret", configWithScope.getApiSecret());
        assertEquals("oob", configWithScope.getCallback());
        assertEquals(SignatureType.Header, configWithScope.getSignatureType());
        assertEquals("scope", configWithScope.getScope());
        assertTrue(configWithScope.hasScope());
    }

    @Test
    public void testGetApiKey() {
        assertEquals("apiKey", config.getApiKey());
    }

    @Test
    public void testGetApiSecret() {
        assertEquals("apiSecret", config.getApiSecret());
    }

    @Test
    public void testGetCallback() {
        assertEquals("oob", config.getCallback());
        assertEquals("callbackUrl", configWithCallback.getCallback());
    }

    @Test
    public void testGetSignatureType() {
        assertEquals(SignatureType.Header, config.getSignatureType());
        assertEquals(SignatureType.QueryString, configWithCallback.getSignatureType());
    }

    @Test
    public void testGetScope() {
        assertNull(config.getScope());
        assertEquals("scope", configWithScope.getScope());
    }

    @Test
    public void testHasScope() {
        assertFalse(config.hasScope());
        assertTrue(configWithScope.hasScope());
    }
}