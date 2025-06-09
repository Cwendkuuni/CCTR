package org.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Whitelist;
import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.*;

public class JsoupTest {

    @Test
    public void testParseStringString() {
        String html = "<html><head><title>Test</title></head><body><p>Hello, World!</p></body></html>";
        String baseUri = "http://example.com";
        Document doc = Jsoup.parse(html, baseUri);
        assertNotNull(doc);
        assertEquals("Test", doc.title());
    }

    @Test
    public void testParseStringStringParser() {
        String html = "<html><head><title>Test</title></head><body><p>Hello, World!</p></body></html>";
        String baseUri = "http://example.com";
        Parser parser = Parser.htmlParser();
        Document doc = Jsoup.parse(html, baseUri, parser);
        assertNotNull(doc);
        assertEquals("Test", doc.title());
    }

    @Test
    public void testParseString() {
        String html = "<html><head><title>Test</title></head><body><p>Hello, World!</p></body></html>";
        Document doc = Jsoup.parse(html);
        assertNotNull(doc);
        assertEquals("Test", doc.title());
    }

    @Test
    public void testConnect() {
        String url = "http://example.com";
        Connection connection = Jsoup.connect(url);
        assertNotNull(connection);
        assertTrue(connection instanceof HttpConnection);
    }

    @Test
    public void testParseFileStringString() throws IOException {
        File file = new File("test.html");
        String charsetName = "UTF-8";
        String baseUri = "http://example.com";
        Document doc = Jsoup.parse(file, charsetName, baseUri);
        assertNotNull(doc);
    }

    @Test
    public void testParseFileString() throws IOException {
        File file = new File("test.html");
        String charsetName = "UTF-8";
        Document doc = Jsoup.parse(file, charsetName);
        assertNotNull(doc);
    }

    @Test
    public void testParseInputStreamStringString() throws IOException {
        InputStream in = getClass().getResourceAsStream("/test.html");
        String charsetName = "UTF-8";
        String baseUri = "http://example.com";
        Document doc = Jsoup.parse(in, charsetName, baseUri);
        assertNotNull(doc);
    }

    @Test
    public void testParseInputStreamStringStringParser() throws IOException {
        InputStream in = getClass().getResourceAsStream("/test.html");
        String charsetName = "UTF-8";
        String baseUri = "http://example.com";
        Parser parser = Parser.htmlParser();
        Document doc = Jsoup.parse(in, charsetName, baseUri, parser);
        assertNotNull(doc);
    }

    @Test
    public void testParseBodyFragmentStringString() {
        String bodyHtml = "<p>Hello, World!</p>";
        String baseUri = "http://example.com";
        Document doc = Jsoup.parseBodyFragment(bodyHtml, baseUri);
        assertNotNull(doc);
        assertEquals("Hello, World!", doc.body().text());
    }

    @Test
    public void testParseBodyFragmentString() {
        String bodyHtml = "<p>Hello, World!</p>";
        Document doc = Jsoup.parseBodyFragment(bodyHtml);
        assertNotNull(doc);
        assertEquals("Hello, World!", doc.body().text());
    }

    @Test
    public void testParseURLInt() throws IOException {
        URL url = new URL("http://example.com");
        int timeoutMillis = 1000;
        Document doc = Jsoup.parse(url, timeoutMillis);
        assertNotNull(doc);
    }

    @Test
    public void testCleanStringStringWhitelist() {
        String bodyHtml = "<p>Hello, <b>World!</b></p>";
        String baseUri = "http://example.com";
        Whitelist whitelist = Whitelist.basic();
        String cleanHtml = Jsoup.clean(bodyHtml, baseUri, whitelist);
        assertEquals("<p>Hello, <b>World!</b></p>", cleanHtml);
    }

    @Test
    public void testCleanStringWhitelist() {
        String bodyHtml = "<p>Hello, <b>World!</b></p>";
        Whitelist whitelist = Whitelist.basic();
        String cleanHtml = Jsoup.clean(bodyHtml, whitelist);
        assertEquals("<p>Hello, <b>World!</b></p>", cleanHtml);
    }

    @Test
    public void testIsValid() {
        String bodyHtml = "<p>Hello, <b>World!</b></p>";
        Whitelist whitelist = Whitelist.basic();
        boolean isValid = Jsoup.isValid(bodyHtml, whitelist);
        assertTrue(isValid);
    }
}