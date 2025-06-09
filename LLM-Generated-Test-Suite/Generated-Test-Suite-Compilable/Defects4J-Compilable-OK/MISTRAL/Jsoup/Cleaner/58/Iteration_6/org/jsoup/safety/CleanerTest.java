package org.jsoup.safety;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
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
        Document dirtyDocument = Jsoup.parse(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertEquals("Hello World", cleanDocument.select("p").text());
    }

    @Test
    public void testCleanInvalidDocument() {
        String html = "<div><script>alert('xss')</script></div>";
        Document dirtyDocument = Jsoup.parse(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertFalse(cleanDocument.select("script").isEmpty());
    }

    @Test
    public void testIsValidValidDocument() {
        String html = "<div><p>Hello World</p></div>";
        Document dirtyDocument = Jsoup.parse(html);

        assertTrue(cleaner.isValid(dirtyDocument));
    }

    @Test
    public void testIsValidInvalidDocument() {
        String html = "<div><script>alert('xss')</script></div>";
        Document dirtyDocument = Jsoup.parse(html);

        assertFalse(cleaner.isValid(dirtyDocument));
    }

    @Test
    public void testCleanDocumentWithTextNode() {
        String html = "<div>Hello World</div>";
        Document dirtyDocument = Jsoup.parse(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertEquals("Hello World", cleanDocument.body().text());
    }

    @Test
    public void testCleanDocumentWithDataNode() {
        String html = "<div><script type=\"text/template\">Hello World</script></div>";
        Document dirtyDocument = Jsoup.parse(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertTrue(cleanDocument.select("script").isEmpty());
    }

    @Test
    public void testCleanDocumentWithUnsafeAttributes() {
        String html = "<div onclick=\"alert('xss')\">Hello World</div>";
        Document dirtyDocument = Jsoup.parse(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertTrue(cleanDocument.select("div[onclick]").isEmpty());
    }

    @Test
    public void testCleanDocumentWithEnforcedAttributes() {
        whitelist.addAttributes("div", "data-enforced", "enforced-value");
        String html = "<div>Hello World</div>";
        Document dirtyDocument = Jsoup.parse(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertEquals("enforced-value", cleanDocument.select("div").attr("data-enforced"));
    }

    @Test
    public void testCleanDocumentWithNestedElements() {
        String html = "<div><p><span>Hello World</span></p></div>";
        Document dirtyDocument = Jsoup.parse(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertEquals("Hello World", cleanDocument.select("span").text());
    }

    @Test
    public void testCleanDocumentWithFrameset() {
        String html = "<frameset><frame src=\"frame.html\"></frameset>";
        Document dirtyDocument = Jsoup.parse(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertTrue(cleanDocument.select("frameset").isEmpty());
    }

    @Test
    public void testCleanDocumentWithEmptyBody() {
        String html = "<html><body></body></html>";
        Document dirtyDocument = Jsoup.parse(html);
        Document cleanDocument = cleaner.clean(dirtyDocument);

        assertTrue(cleanDocument.body().children().isEmpty());
    }
}