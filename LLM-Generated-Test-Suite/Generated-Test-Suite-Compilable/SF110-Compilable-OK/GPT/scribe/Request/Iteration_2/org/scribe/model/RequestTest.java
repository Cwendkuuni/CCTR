package org.scribe.model;

import org.junit.*;
import org.scribe.model.*;
import org.scribe.exceptions.*;
import org.scribe.utils.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.charset.*;
import java.util.concurrent.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RequestTest {

    private Request request;
    private HttpURLConnection mockConnection;

    @Before
    public void setUp() throws Exception {
        request = new Request(Verb.GET, "http://example.com");
        mockConnection = mock(HttpURLConnection.class);
        request.setConnection(mockConnection);
    }

    @Test
    public void testAddHeader() {
        request.addHeader("Content-Type", "application/json");
        assertEquals("application/json", request.getHeaders().get("Content-Type"));
    }

    @Test
    public void testAddBodyParameter() {
        request.addBodyParameter("key", "value");
        assertEquals("value", request.getBodyParams().get("key"));
    }

    @Test
    public void testAddQuerystringParameter() {
        request.addQuerystringParameter("key", "value");
        assertEquals("value", request.getQueryStringParams().get("key"));
    }

    @Test
    public void testAddPayloadString() {
        request.addPayload("payload");
        assertEquals("payload", request.getBodyContents());
    }

    @Test
    public void testAddPayloadBytes() {
        byte[] payload = "payload".getBytes();
        request.addPayload(payload);
        assertArrayEquals(payload, request.getByteBodyContents());
    }

    @Test
    public void testGetQueryStringParams() {
        request.addQuerystringParameter("key", "value");
        Map<String, String> params = request.getQueryStringParams();
        assertEquals("value", params.get("key"));
    }

    @Test
    public void testGetBodyParams() {
        request.addBodyParameter("key", "value");
        Map<String, String> params = request.getBodyParams();
        assertEquals("value", params.get("key"));
    }

    @Test
    public void testGetUrl() {
        assertEquals("http://example.com", request.getUrl());
    }

    @Test
    public void testGetSanitizedUrl() {
        Request requestWithQuery = new Request(Verb.GET, "http://example.com?param=value");
        assertEquals("http://example.com", requestWithQuery.getSanitizedUrl());
    }

    @Test
    public void testGetBodyContents() {
        request.addPayload("payload");
        assertEquals("payload", request.getBodyContents());
    }

    @Test
    public void testGetByteBodyContents() {
        request.addPayload("payload");
        assertArrayEquals("payload".getBytes(), request.getByteBodyContents());
    }

    @Test
    public void testGetVerb() {
        assertEquals(Verb.GET, request.getVerb());
    }

    @Test
    public void testGetHeaders() {
        request.addHeader("Content-Type", "application/json");
        assertEquals("application/json", request.getHeaders().get("Content-Type"));
    }

    @Test
    public void testGetCharset() {
        assertEquals(Charset.defaultCharset().name(), request.getCharset());
        request.setCharset("UTF-8");
        assertEquals("UTF-8", request.getCharset());
    }

    @Test
    public void testSetConnectTimeout() throws Exception {
        request.setConnectTimeout(10, TimeUnit.SECONDS);
        verify(mockConnection).setConnectTimeout(10000);
    }

    @Test
    public void testSetReadTimeout() throws Exception {
        request.setReadTimeout(10, TimeUnit.SECONDS);
        verify(mockConnection).setReadTimeout(10000);
    }

    @Test
    public void testSetCharset() {
        request.setCharset("UTF-8");
        assertEquals("UTF-8", request.getCharset());
    }

    @Test
    public void testSetConnectionKeepAlive() {
        request.setConnectionKeepAlive(true);
        assertTrue(request.connectionKeepAlive);
    }

    @Test
    public void testToString() {
        assertEquals("@Request(GET http://example.com)", request.toString());
    }

    @Test(expected = OAuthException.class)
    public void testSendThrowsOAuthExceptionOnIOException() throws Exception {
        doThrow(new IOException()).when(mockConnection).setRequestMethod(anyString());
        request.send();
    }

    @Test(expected = OAuthException.class)
    public void testGetQueryStringParamsThrowsOAuthExceptionOnMalformedURLException() throws Exception {
        Request malformedRequest = new Request(Verb.GET, "malformed:url");
        malformedRequest.getQueryStringParams();
    }

    @Test(expected = OAuthException.class)
    public void testGetBodyContentsThrowsOAuthExceptionOnUnsupportedEncodingException() throws Exception {
        request.setCharset("unsupported-charset");
        request.addPayload("payload");
        request.getBodyContents();
    }
}