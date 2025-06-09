package org.jsoup.safety;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

    @Test
    public void testCleanValidDocument() {
        String html = "<div><p>Hello World</p></div>";
        Document dirtyDocument = Jsoup.parseBodyFragment(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertEquals(html, cleanDocument.body().html());
    }

    @Test
    public void testCleanInvalidDocument() {
        String html = "<div><script>alert('xss')</script><p>Hello World</p></div>";
        Document dirtyDocument = Jsoup.parseBodyFragment(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertEquals("<div><p>Hello World</p></div>", cleanDocument.body().html());
    }

    @Test
    public void testCleanDocumentWithUnsafeAttributes() {
        String html = "<div onclick='alert(1)'><p>Hello World</p></div>";
        Document dirtyDocument = Jsoup.parseBodyFragment(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertEquals("<div><p>Hello World</p></div>", cleanDocument.body().html());
    }

    @Test
    public void testIsValidValidDocument() {
        String html = "<div><p>Hello World</p></div>";
        Document dirtyDocument = Jsoup.parseBodyFragment(html);

        assertTrue(cleaner.isValid(dirtyDocument));
    }

    @Test
    public void testIsValidInvalidDocument() {
        String html = "<div><script>alert('xss')</script><p>Hello World</p></div>";
        Document dirtyDocument = Jsoup.parseBodyFragment(html);

        assertFalse(cleaner.isValid(dirtyDocument));
    }

    @Test
    public void testIsValidDocumentWithUnsafeAttributes() {
        String html = "<div onclick='alert(1)'><p>Hello World</p></div>";
        Document dirtyDocument = Jsoup.parseBodyFragment(html);

        assertFalse(cleaner.isValid(dirtyDocument));
    }

    @Test
    public void testCleanDocumentWithDataNode() {
        String html = "<div><script type='text/template'>alert('xss')</script><p>Hello World</p></div>";
        Document dirtyDocument = Jsoup.parseBodyFragment(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertEquals("<div><p>Hello World</p></div>", cleanDocument.body().html());
    }

    @Test
    public void testCleanDocumentWithTextNode() {
        String html = "<div>Hello <b>World</b></div>";
        Document dirtyDocument = Jsoup.parseBodyFragment(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertEquals("<div>Hello <b>World</b></div>", cleanDocument.body().html());
    }

    @Test
    public void testCleanDocumentWithEmptyBody() {
        String html = "<html><body></body></html>";
        Document dirtyDocument = Jsoup.parse(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertEquals("<html><head></head><body></body></html>", cleanDocument.html());
    }

    @Test
    public void testCleanDocumentWithFrameset() {
        String html = "<html><frameset></frameset></html>";
        Document dirtyDocument = Jsoup.parse(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertEquals("<html><head></head><body></body></html>", cleanDocument.html());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCleanWithNullDocument() {
        cleaner.clean(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidWithNullDocument() {
        cleaner.isValid(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullWhitelist() {
        new Cleaner(null);
    }
}