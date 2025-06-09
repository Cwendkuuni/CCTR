package org.jsoup.safety;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CleanerTest {

    private Cleaner cleaner;
    private Whitelist whitelist;

    @Before
    public void setUp() {
        whitelist = Whitelist.basic();
        cleaner = new Cleaner(whitelist);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullWhitelist() {
        new Cleaner(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCleanWithNullDocument() {
        cleaner.clean(null);
    }

    @Test
    public void testCleanWithValidDocument() {
        String html = "<div><p>Hello, <b>world</b>!</p></div>";
        Document dirtyDocument = Parser.parseBodyFragment(html, "");
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertNotNull(cleanDocument);
        assertEquals("<p>Hello, <b>world</b>!</p>", cleanDocument.body().html());
    }

    @Test
    public void testCleanWithInvalidDocument() {
        String html = "<div><script>alert('xss')</script><p>Hello, <b>world</b>!</p></div>";
        Document dirtyDocument = Parser.parseBodyFragment(html, "");
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertNotNull(cleanDocument);
        assertEquals("<p>Hello, <b>world</b>!</p>", cleanDocument.body().html());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidWithNullDocument() {
        cleaner.isValid(null);
    }

    @Test
    public void testIsValidWithValidDocument() {
        String html = "<div><p>Hello, <b>world</b>!</p></div>";
        Document dirtyDocument = Parser.parseBodyFragment(html, "");
        boolean isValid = cleaner.isValid(dirtyDocument);

        assertTrue(isValid);
    }

    @Test
    public void testIsValidWithInvalidDocument() {
        String html = "<div><script>alert('xss')</script><p>Hello, <b>world</b>!</p></div>";
        Document dirtyDocument = Parser.parseBodyFragment(html, "");
        boolean isValid = cleaner.isValid(dirtyDocument);

        assertFalse(isValid);
    }

    @Test
    public void testCleanWithEmptyBody() {
        String html = "<html><head><title>Title</title></head><body></body></html>";
        Document dirtyDocument = Parser.parse(html, "");
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertNotNull(cleanDocument);
        assertEquals("", cleanDocument.body().html());
    }

    @Test
    public void testCleanWithFramesetDocument() {
        String html = "<html><head><title>Title</title></head><frameset></frameset></html>";
        Document dirtyDocument = Parser.parse(html, "");
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertNotNull(cleanDocument);
        assertEquals("", cleanDocument.body().html());
    }
}