package org.scribe.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.scribe.model.OAuthConfig;
import org.scribe.model.SignatureType;

public class OAuthConfigTest {

    @Test
    public void testConstructorWithMandatoryFields() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret");
        
        assertEquals("apiKey", config.getApiKey());
        assertEquals("apiSecret", config.getApiSecret());
        assertEquals("oob", config.getCallback());
        assertEquals(SignatureType.Header, config.getSignatureType());
        assertNull(config.getScope());
        assertFalse(config.hasScope());
    }

    @Test
    public void testConstructorWithAllFields() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret", "callback", SignatureType.QueryString, "scope");
        
        assertEquals("apiKey", config.getApiKey());
        assertEquals("apiSecret", config.getApiSecret());
        assertEquals("callback", config.getCallback());
        assertEquals(SignatureType.QueryString, config.getSignatureType());
        assertEquals("scope", config.getScope());
        assertTrue(config.hasScope());
    }

    @Test
    public void testConstructorWithNullCallback() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret", null, SignatureType.QueryString, "scope");
        
        assertEquals("oob", config.getCallback());
    }

    @Test
    public void testConstructorWithNullSignatureType() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret", "callback", null, "scope");
        
        assertEquals(SignatureType.Header, config.getSignatureType());
    }

    @Test
    public void testConstructorWithNullScope() {
        OAuthConfig config = new OAuthConfig("apiKey", "apiSecret", "callback", SignatureType.QueryString, null);
        
        assertNull(config.getScope());
        assertFalse(config.hasScope());
    }
}