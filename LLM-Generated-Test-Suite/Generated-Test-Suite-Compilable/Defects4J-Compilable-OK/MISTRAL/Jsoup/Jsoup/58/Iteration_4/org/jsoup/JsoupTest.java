package org.jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.helper.DataUtil;
import org.jsoup.helper.HttpConnection;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.*;

public class JsoupTest {

    private static final String HTML_CONTENT = "<html><head><title>Test</title></head><body><p>Hello World</p></body></html>";
    private static final String BASE_URI = "http://example.com";
    private static final String BODY_FRAGMENT = "<p>Hello World</p>";
    private static final Whitelist WHITELIST = Whitelist.basic();

    @Before
    public void setUp() {
        // Setup code if needed
    }

    @After
    public void tearDown() {
        // Teardown code if needed
    }

    @Test
    public void testParseStringBaseUri() {
        Document doc = Jsoup.parse(HTML_CONTENT, BASE_URI);
        assertNotNull(doc);
        assertEquals("Test", doc.title());
    }

    @Test
    public void testParseStringBaseUriParser() {
        Parser parser = Parser.xmlParser();
        Document doc = Jsoup.parse(HTML_CONTENT, BASE_URI, parser);
        assertNotNull(doc);
        assertEquals("Test", doc.title());
    }

    @Test
    public void testParseString() {
        Document doc = Jsoup.parse(HTML_CONTENT);
        assertNotNull(doc);
        assertEquals("Test", doc.title());
    }

    @Test
    public void testConnect() {
        assertNotNull(Jsoup.connect(BASE_URI));
    }

    @Test
    public void testParseFileCharsetNameBaseUri() throws IOException {
        File file = new File("test.html");
        Document doc = Jsoup.parse(file, "UTF-8", BASE_URI);
        assertNotNull(doc);
    }

    @Test
    public void testParseFileCharsetName() throws IOException {
        File file = new File("test.html");
        Document doc = Jsoup.parse(file, "UTF-8");
        assertNotNull(doc);
    }

    @Test
    public void testParseInputStreamCharsetNameBaseUri() throws IOException {
        InputStream in = getClass().getResourceAsStream("/test.html");
        Document doc = Jsoup.parse(in, "UTF-8", BASE_URI);
        assertNotNull(doc);
    }

    @Test
    public void testParseInputStreamCharsetNameBaseUriParser() throws IOException {
        InputStream in = getClass().getResourceAsStream("/test.html");
        Parser parser = Parser.xmlParser();
        Document doc = Jsoup.parse(in, "UTF-8", BASE_URI, parser);
        assertNotNull(doc);
    }

    @Test
    public void testParseBodyFragmentStringBaseUri() {
        Document doc = Jsoup.parseBodyFragment(BODY_FRAGMENT, BASE_URI);
        assertNotNull(doc);
        assertEquals(BODY_FRAGMENT, doc.body().html());
    }

    @Test
    public void testParseBodyFragmentString() {
        Document doc = Jsoup.parseBodyFragment(BODY_FRAGMENT);
        assertNotNull(doc);
        assertEquals(BODY_FRAGMENT, doc.body().html());
    }

    @Test
    public void testParseURLTimeoutMillis() throws IOException {
        URL url = new URL(BASE_URI);
        Document doc = Jsoup.parse(url, 10000);
        assertNotNull(doc);
    }

    @Test
    public void testCleanStringBaseUriWhitelist() {
        String cleanHtml = Jsoup.clean(BODY_FRAGMENT, BASE_URI, WHITELIST);
        assertNotNull(cleanHtml);
        assertEquals(BODY_FRAGMENT, cleanHtml);
    }

    @Test
    public void testCleanStringWhitelist() {
        String cleanHtml = Jsoup.clean(BODY_FRAGMENT, WHITELIST);
        assertNotNull(cleanHtml);
        assertEquals(BODY_FRAGMENT, cleanHtml);
    }

    @Test
    public void testCleanStringBaseUriWhitelistOutputSettings() {
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        String cleanHtml = Jsoup.clean(BODY_FRAGMENT, BASE_URI, WHITELIST, outputSettings);
        assertNotNull(cleanHtml);
        assertEquals(BODY_FRAGMENT, cleanHtml);
    }

    @Test
    public void testIsValidStringWhitelist() {
        boolean isValid = Jsoup.isValid(BODY_FRAGMENT, WHITELIST);
        assertTrue(isValid);
    }
}