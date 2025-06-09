package de.huxhorn.lilith.data.access;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;

public class AccessEventTest {

    private AccessEvent accessEvent;
    private LoggerContext loggerContext;

    @Before
    public void setUp() {
        accessEvent = new AccessEvent();
        loggerContext = new LoggerContext(); // Assuming LoggerContext has a default constructor
    }

    @Test
    public void testGetSetTimeStamp() {
        Long timeStamp = 123456789L;
        accessEvent.setTimeStamp(timeStamp);
        assertEquals(timeStamp, accessEvent.getTimeStamp());
    }

    @Test
    public void testGetSetLoggerContext() {
        accessEvent.setLoggerContext(loggerContext);
        assertEquals(loggerContext, accessEvent.getLoggerContext());
    }

    @Test
    public void testGetSetRequestURI() {
        String requestURI = "/test";
        accessEvent.setRequestURI(requestURI);
        assertEquals(requestURI, accessEvent.getRequestURI());
    }

    @Test
    public void testGetSetRequestURL() {
        String requestURL = "http://test.com";
        accessEvent.setRequestURL(requestURL);
        assertEquals(requestURL, accessEvent.getRequestURL());
    }

    @Test
    public void testGetSetRemoteHost() {
        String remoteHost = "192.168.1.1";
        accessEvent.setRemoteHost(remoteHost);
        assertEquals(remoteHost, accessEvent.getRemoteHost());
    }

    @Test
    public void testGetSetRemoteUser() {
        String remoteUser = "testUser";
        accessEvent.setRemoteUser(remoteUser);
        assertEquals(remoteUser, accessEvent.getRemoteUser());
    }

    @Test
    public void testGetSetProtocol() {
        String protocol = "HTTP";
        accessEvent.setProtocol(protocol);
        assertEquals(protocol, accessEvent.getProtocol());
    }

    @Test
    public void testGetSetMethod() {
        String method = "GET";
        accessEvent.setMethod(method);
        assertEquals(method, accessEvent.getMethod());
    }

    @Test
    public void testGetSetServerName() {
        String serverName = "testServer";
        accessEvent.setServerName(serverName);
        assertEquals(serverName, accessEvent.getServerName());
    }

    @Test
    public void testGetSetRemoteAddress() {
        String remoteAddress = "192.168.1.2";
        accessEvent.setRemoteAddress(remoteAddress);
        assertEquals(remoteAddress, accessEvent.getRemoteAddress());
    }

    @Test
    public void testGetSetRequestHeaders() {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Header1", "Value1");
        accessEvent.setRequestHeaders(requestHeaders);
        assertEquals(requestHeaders, accessEvent.getRequestHeaders());
    }

    @Test
    public void testGetSetResponseHeaders() {
        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put("Header1", "Value1");
        accessEvent.setResponseHeaders(responseHeaders);
        assertEquals(responseHeaders, accessEvent.getResponseHeaders());
    }

    @Test
    public void testGetSetRequestParameters() {
        Map<String, String[]> requestParameters = new HashMap<>();
        requestParameters.put("Param1", new String[]{"Value1"});
        accessEvent.setRequestParameters(requestParameters);
        assertEquals(requestParameters, accessEvent.getRequestParameters());
    }

    @Test
    public void testGetSetLocalPort() {
        int localPort = 8080;
        accessEvent.setLocalPort(localPort);
        assertEquals(localPort, accessEvent.getLocalPort());
    }

    @Test
    public void testGetSetStatusCode() {
        int statusCode = 200;
        accessEvent.setStatusCode(statusCode);
        assertEquals(statusCode, accessEvent.getStatusCode());
    }

    @Test
    public void testEquals() {
        AccessEvent event1 = new AccessEvent();
        AccessEvent event2 = new AccessEvent();

        assertTrue(event1.equals(event1));
        assertFalse(event1.equals(null));
        assertFalse(event1.equals(new Object()));

        event1.setTimeStamp(123456789L);
        event2.setTimeStamp(123456789L);
        assertTrue(event1.equals(event2));

        event2.setTimeStamp(987654321L);
        assertFalse(event1.equals(event2));
    }

    @Test
    public void testHashCode() {
        AccessEvent event1 = new AccessEvent();
        AccessEvent event2 = new AccessEvent();

        assertEquals(event1.hashCode(), event2.hashCode());

        event1.setTimeStamp(123456789L);
        event2.setTimeStamp(123456789L);
        assertEquals(event1.hashCode(), event2.hashCode());

        event2.setTimeStamp(987654321L);
        assertNotEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    public void testToString() {
        accessEvent.setTimeStamp(123456789L);
        accessEvent.setLoggerContext(loggerContext);
        String expected = "AccessEvent[loggerContext=" + loggerContext + ", timeStamp=123456789]";
        assertEquals(expected, accessEvent.toString());
    }
}