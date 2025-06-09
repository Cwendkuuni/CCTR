package org.scribe.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.scribe.model.OAuthConfig;
import org.scribe.model.SignatureType;

public class OAuthConfigTest {

    private static final String API_KEY = "testApiKey";
    private static final String API_SECRET = "testApiSecret";
    private static final String CALLBACK = "http://callback.com";
    private static final SignatureType SIGNATURE_TYPE = SignatureType.QueryString;
    private static final String SCOPE = "testScope";

    private OAuthConfig configWithAllParams;
    private OAuthConfig configWithMandatoryParams;

    @Before
    public void setUp() {
        configWithAllParams = new OAuthConfig(API_KEY, API_SECRET, CALLBACK, SIGNATURE_TYPE, SCOPE);
        configWithMandatoryParams = new OAuthConfig(API_KEY, API_SECRET);
    }

    @Test
    public void testGetApiKey() {
        assertEquals(API_KEY, configWithAllParams.getApiKey());
        assertEquals(API_KEY, configWithMandatoryParams.getApiKey());
    }

    @Test
    public void testGetApiSecret() {
        assertEquals(API_SECRET, configWithAllParams.getApiSecret());
        assertEquals(API_SECRET, configWithMandatoryParams.getApiSecret());
    }

    @Test
    public void testGetCallback() {
        assertEquals(CALLBACK, configWithAllParams.getCallback());
        assertEquals("oob", configWithMandatoryParams.getCallback());
    }

    @Test
    public void testGetSignatureType() {
        assertEquals(SIGNATURE_TYPE, configWithAllParams.getSignatureType());
        assertEquals(SignatureType.Header, configWithMandatoryParams.getSignatureType());
    }

    @Test
    public void testGetScope() {
        assertEquals(SCOPE, configWithAllParams.getScope());
        assertNull(configWithMandatoryParams.getScope());
    }

    @Test
    public void testHasScope() {
        assertTrue(configWithAllParams.hasScope());
        assertFalse(configWithMandatoryParams.hasScope());
    }
}