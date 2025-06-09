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
        mockConnection = mock(HttpURLConnection.class);
        request = new Request(Verb.GET, "http://example.com");
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
    public void testGetCharset() {
        assertEquals(Charset.defaultCharset().name(), request.getCharset());
        request.setCharset("UTF-8");
        assertEquals("UTF-8", request.getCharset());
    }

    @Test
    public void testSetConnectTimeout() {
        request.setConnectTimeout(10, TimeUnit.SECONDS);
        verify(mockConnection).setConnectTimeout(10000);
    }

    @Test
    public void testSetReadTimeout() {
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
    public void testSendThrowsOAuthExceptionOnIOException() throws Exception {
        doThrow(new IOException()).when(mockConnection).setRequestMethod(anyString());
        request.send();
    }

    @Test
    public void testDoSend() throws Exception {
        request.addHeader("Content-Type", "application/json");
        request.doSend();
        verify(mockConnection).setRequestMethod("GET");
        verify(mockConnection).setRequestProperty("Content-Type", "application/json");
    }

    @Test
    public void testAddBody() throws Exception {
        byte[] content = "content".getBytes();
        request.addBody(mockConnection, content);
        verify(mockConnection).setRequestProperty("Content-Length", String.valueOf(content.length));
        verify(mockConnection).setDoOutput(true);
        verify(mockConnection.getOutputStream()).write(content);
    }

    @Test(expected = OAuthException.class)
    public void testGetQueryStringParamsThrowsOAuthExceptionOnMalformedUrl() {
        Request malformedRequest = new Request(Verb.GET, "htp://malformed-url");
        malformedRequest.getQueryStringParams();
    }

    @Test(expected = OAuthException.class)
    public void testGetBodyContentsThrowsOAuthExceptionOnUnsupportedEncoding() {
        request.setCharset("unsupported-charset");
        request.getBodyContents();
    }
}