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
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class JsoupTest {

    private static final String HTML = "<html><head><title>Test</title></head><body><p>Hello World</p></body></html>";
    private static final String BODY_HTML = "<p>Hello World</p>";
    private static final String BASE_URI = "http://example.com";
    private static final String FILE_PATH = "test.html";
    private static final String CHARSET_NAME = "UTF-8";
    private static final String URL_STRING = "http://example.com";
    private static final int TIMEOUT_MILLIS = 5000;

    @Before
    public void setUp() throws Exception {
        // Setup code if needed
    }

    @After
    public void tearDown() throws Exception {
        // Teardown code if needed
    }

    @Test
    public void testParseStringBaseUri() {
        Document doc = Jsoup.parse(HTML, BASE_URI);
        Assert.assertNotNull(doc);
        Assert.assertEquals("Test", doc.title());
    }

    @Test
    public void testParseStringBaseUriParser() {
        Parser parser = Parser.xmlParser();
        Document doc = Jsoup.parse(HTML, BASE_URI, parser);
        Assert.assertNotNull(doc);
        Assert.assertEquals("Test", doc.title());
    }

    @Test
    public void testParseString() {
        Document doc = Jsoup.parse(HTML);
        Assert.assertNotNull(doc);
        Assert.assertEquals("Test", doc.title());
    }

    @Test
    public void testConnect() {
        Connection connection = Jsoup.connect(URL_STRING);
        Assert.assertNotNull(connection);
    }

    @Test
    public void testParseFileCharsetNameBaseUri() throws IOException {
        File file = new File(FILE_PATH);
        Document doc = Jsoup.parse(file, CHARSET_NAME, BASE_URI);
        Assert.assertNotNull(doc);
    }

    @Test
    public void testParseFileCharsetName() throws IOException {
        File file = new File(FILE_PATH);
        Document doc = Jsoup.parse(file, CHARSET_NAME);
        Assert.assertNotNull(doc);
    }

    @Test
    public void testParseInputStreamCharsetNameBaseUri() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/test.html");
        Document doc = Jsoup.parse(inputStream, CHARSET_NAME, BASE_URI);
        Assert.assertNotNull(doc);
    }

    @Test
    public void testParseInputStreamCharsetNameBaseUriParser() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/test.html");
        Parser parser = Parser.xmlParser();
        Document doc = Jsoup.parse(inputStream, CHARSET_NAME, BASE_URI, parser);
        Assert.assertNotNull(doc);
    }

    @Test
    public void testParseBodyFragmentStringBaseUri() {
        Document doc = Jsoup.parseBodyFragment(BODY_HTML, BASE_URI);
        Assert.assertNotNull(doc);
        Assert.assertEquals(BODY_HTML, doc.body().html());
    }

    @Test
    public void testParseBodyFragmentString() {
        Document doc = Jsoup.parseBodyFragment(BODY_HTML);
        Assert.assertNotNull(doc);
        Assert.assertEquals(BODY_HTML, doc.body().html());
    }

    @Test
    public void testParseURLTimeoutMillis() throws IOException {
        URL url = new URL(URL_STRING);
        Document doc = Jsoup.parse(url, TIMEOUT_MILLIS);
        Assert.assertNotNull(doc);
    }

    @Test
    public void testCleanStringBaseUriWhitelist() {
        Whitelist whitelist = Whitelist.basic();
        String cleanHtml = Jsoup.clean(BODY_HTML, BASE_URI, whitelist);
        Assert.assertNotNull(cleanHtml);
    }

    @Test
    public void testCleanStringWhitelist() {
        Whitelist whitelist = Whitelist.basic();
        String cleanHtml = Jsoup.clean(BODY_HTML, whitelist);
        Assert.assertNotNull(cleanHtml);
    }

    @Test
    public void testCleanStringBaseUriWhitelistOutputSettings() {
        Whitelist whitelist = Whitelist.basic();
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        String cleanHtml = Jsoup.clean(BODY_HTML, BASE_URI, whitelist, outputSettings);
        Assert.assertNotNull(cleanHtml);
    }

    @Test
    public void testIsValidStringWhitelist() {
        Whitelist whitelist = Whitelist.basic();
        boolean isValid = Jsoup.isValid(BODY_HTML, whitelist);
        Assert.assertTrue(isValid);
    }
}