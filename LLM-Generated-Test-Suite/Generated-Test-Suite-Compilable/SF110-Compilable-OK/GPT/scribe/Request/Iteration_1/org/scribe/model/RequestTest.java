package org.scribe.model;

import org.junit.*;
import org.scribe.model.*;
import org.scribe.exceptions.*;
import org.scribe.utils.*;
import java.net.*;
import java.util.*;
import java.io.*;
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
    public void testConstructor() {
        assertEquals("http://example.com", request.getUrl());
        assertEquals(Verb.GET, request.getVerb());
        assertTrue(request.getQueryStringParams().isEmpty());
        assertTrue(request.getBodyParams().isEmpty());
        assertTrue(request.getHeaders().isEmpty());
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
    public void testAddPayloadByteArray() {
        byte[] payload = "payload".getBytes();
        request.addPayload(payload);
        assertArrayEquals(payload, request.getByteBodyContents());
    }

    @Test
    public void testGetSanitizedUrl() {
        Request requestWithPort = new Request(Verb.GET, "http://example.com:8080/path?query=1");
        assertEquals("http://example.com/path", requestWithPort.getSanitizedUrl());
    }

    @Test
    public void testGetBodyContents() {
        request.addBodyParameter("key", "value");
        assertEquals("key=value", request.getBodyContents());
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
    public void testSetConnectionKeepAlive() {
        request.setConnectionKeepAlive(true);
        assertTrue(System.getProperty("http.keepAlive").equals("true"));
    }

    @Test
    public void testToString() {
        assertEquals("@Request(GET http://example.com)", request.toString());
    }

    @Test(expected = OAuthException.class)
    public void testSendThrowsExceptionOnMalformedUrl() throws Exception {
        Request badRequest = new Request(Verb.GET, "malformed-url");
        badRequest.send();
    }

    @Test(expected = OAuthException.class)
    public void testSendThrowsExceptionOnIOException() throws Exception {
        doThrow(new IOException()).when(mockConnection).setRequestMethod(anyString());
        request.send();
    }

    @Test
    public void testSendSuccess() throws Exception {
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        Response response = request.send();
        assertEquals(HttpURLConnection.HTTP_OK, response.getCode());
    }
}