package org.scribe.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class OAuthConfigTest {

    private static final String API_KEY = "testApiKey";
    private static final String API_SECRET = "testApiSecret";
    private static final String CALLBACK = "testCallback";
    private static final String SCOPE = "testScope";
    private static final SignatureType SIGNATURE_TYPE = SignatureType.QueryString;

    private OAuthConfig oAuthConfig;

    @Before
    public void setUp() {
        oAuthConfig = new OAuthConfig(API_KEY, API_SECRET, CALLBACK, SIGNATURE_TYPE, SCOPE);
    }

    @Test
    public void testConstructorWithAllParameters() {
        assertEquals(API_KEY, oAuthConfig.getApiKey());
        assertEquals(API_SECRET, oAuthConfig.getApiSecret());
        assertEquals(CALLBACK, oAuthConfig.getCallback());
        assertEquals(SIGNATURE_TYPE, oAuthConfig.getSignatureType());
        assertEquals(SCOPE, oAuthConfig.getScope());
        assertTrue(oAuthConfig.hasScope());
    }

    @Test
    public void testConstructorWithMinimalParameters() {
        OAuthConfig minimalConfig = new OAuthConfig(API_KEY, API_SECRET);
        assertEquals(API_KEY, minimalConfig.getApiKey());
        assertEquals(API_SECRET, minimalConfig.getApiSecret());
        assertEquals("oob", minimalConfig.getCallback());
        assertEquals(SignatureType.Header, minimalConfig.getSignatureType());
        assertNull(minimalConfig.getScope());
        assertFalse(minimalConfig.hasScope());
    }

    @Test
    public void testGetApiKey() {
        assertEquals(API_KEY, oAuthConfig.getApiKey());
    }

    @Test
    public void testGetApiSecret() {
        assertEquals(API_SECRET, oAuthConfig.getApiSecret());
    }

    @Test
    public void testGetCallback() {
        assertEquals(CALLBACK, oAuthConfig.getCallback());
    }

    @Test
    public void testGetSignatureType() {
        assertEquals(SIGNATURE_TYPE, oAuthConfig.getSignatureType());
    }

    @Test
    public void testGetScope() {
        assertEquals(SCOPE, oAuthConfig.getScope());
    }

    @Test
    public void testHasScope() {
        assertTrue(oAuthConfig.hasScope());
    }

    @Test
    public void testHasScopeWithNullScope() {
        OAuthConfig configWithoutScope = new OAuthConfig(API_KEY, API_SECRET, CALLBACK, SIGNATURE_TYPE, null);
        assertFalse(configWithoutScope.hasScope());
    }
}