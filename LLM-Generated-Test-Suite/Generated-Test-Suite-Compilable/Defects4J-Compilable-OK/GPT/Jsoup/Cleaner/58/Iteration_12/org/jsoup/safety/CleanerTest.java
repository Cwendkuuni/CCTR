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
    public void testCleanWithEmptyDocument() {
        Document dirty = Document.createShell("");
        Document clean = cleaner.clean(dirty);
        assertNotNull(clean);
        assertEquals("", clean.body().html());
    }

    @Test
    public void testCleanWithValidDocument() {
        String html = "<p>Hello <b>world</b>!</p>";
        Document dirty = Parser.parseBodyFragment(html, "");
        Document clean = cleaner.clean(dirty);
        assertNotNull(clean);
        assertEquals("<p>Hello <b>world</b>!</p>", clean.body().html());
    }

    @Test
    public void testCleanWithInvalidDocument() {
        String html = "<p>Hello <script>alert('XSS')</script> world!</p>";
        Document dirty = Parser.parseBodyFragment(html, "");
        Document clean = cleaner.clean(dirty);
        assertNotNull(clean);
        assertEquals("<p>Hello  world!</p>", clean.body().html());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidWithNullDocument() {
        cleaner.isValid(null);
    }

    @Test
    public void testIsValidWithValidDocument() {
        String html = "<p>Hello <b>world</b>!</p>";
        Document dirty = Parser.parseBodyFragment(html, "");
        assertTrue(cleaner.isValid(dirty));
    }

    @Test
    public void testIsValidWithInvalidDocument() {
        String html = "<p>Hello <script>alert('XSS')</script> world!</p>";
        Document dirty = Parser.parseBodyFragment(html, "");
        assertFalse(cleaner.isValid(dirty));
    }

    @Test
    public void testIsValidWithHeadContent() {
        String html = "<head><title>Title</title></head><body><p>Hello world!</p></body>";
        Document dirty = Parser.parse(html, "");
        assertFalse(cleaner.isValid(dirty));
    }

    @Test
    public void testCleanWithEnforcedAttributes() {
        whitelist.addEnforcedAttribute("a", "rel", "nofollow");
        String html = "<a href='http://example.com'>Link</a>";
        Document dirty = Parser.parseBodyFragment(html, "");
        Document clean = cleaner.clean(dirty);
        Element link = clean.select("a").first();
        assertNotNull(link);
        assertEquals("nofollow", link.attr("rel"));
    }
}