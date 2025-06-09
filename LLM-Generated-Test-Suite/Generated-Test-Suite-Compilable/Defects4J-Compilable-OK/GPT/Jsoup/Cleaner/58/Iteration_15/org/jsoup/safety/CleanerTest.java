package org.jsoup.safety;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CleanerTest {

    private Cleaner cleaner;
    private Whitelist whitelist;

    @Before
    public void setUp() {
        whitelist = mock(Whitelist.class);
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
        Document dirtyDoc = Parser.parse("", "");
        Document cleanDoc = cleaner.clean(dirtyDoc);

        assertNotNull(cleanDoc);
        assertEquals("", cleanDoc.body().html());
    }

    @Test
    public void testCleanWithSafeTags() {
        when(whitelist.isSafeTag("p")).thenReturn(true);
        when(whitelist.isSafeTag("span")).thenReturn(true);

        Document dirtyDoc = Parser.parse("<p>Hello <span>world</span></p>", "");
        Document cleanDoc = cleaner.clean(dirtyDoc);

        assertNotNull(cleanDoc);
        assertEquals("<p>Hello <span>world</span></p>", cleanDoc.body().html());
    }

    @Test
    public void testCleanWithUnsafeTags() {
        when(whitelist.isSafeTag("p")).thenReturn(true);
        when(whitelist.isSafeTag("script")).thenReturn(false);

        Document dirtyDoc = Parser.parse("<p>Hello <script>alert('xss')</script></p>", "");
        Document cleanDoc = cleaner.clean(dirtyDoc);

        assertNotNull(cleanDoc);
        assertEquals("<p>Hello </p>", cleanDoc.body().html());
    }

    @Test
    public void testCleanWithSafeAttributes() {
        when(whitelist.isSafeTag("a")).thenReturn(true);
        when(whitelist.isSafeAttribute(eq("a"), any(Element.class), any())).thenReturn(true);

        Document dirtyDoc = Parser.parse("<a href='http://example.com'>Link</a>", "");
        Document cleanDoc = cleaner.clean(dirtyDoc);

        assertNotNull(cleanDoc);
        assertEquals("<a href=\"http://example.com\">Link</a>", cleanDoc.body().html());
    }

    @Test
    public void testCleanWithUnsafeAttributes() {
        when(whitelist.isSafeTag("a")).thenReturn(true);
        when(whitelist.isSafeAttribute(eq("a"), any(Element.class), any())).thenReturn(false);

        Document dirtyDoc = Parser.parse("<a href='http://example.com'>Link</a>", "");
        Document cleanDoc = cleaner.clean(dirtyDoc);

        assertNotNull(cleanDoc);
        assertEquals("<a>Link</a>", cleanDoc.body().html());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidWithNullDocument() {
        cleaner.isValid(null);
    }

    @Test
    public void testIsValidWithValidDocument() {
        when(whitelist.isSafeTag("p")).thenReturn(true);

        Document dirtyDoc = Parser.parse("<p>Valid content</p>", "");
        boolean isValid = cleaner.isValid(dirtyDoc);

        assertTrue(isValid);
    }

    @Test
    public void testIsValidWithInvalidDocument() {
        when(whitelist.isSafeTag("p")).thenReturn(true);
        when(whitelist.isSafeTag("script")).thenReturn(false);

        Document dirtyDoc = Parser.parse("<p>Invalid <script>alert('xss')</script></p>", "");
        boolean isValid = cleaner.isValid(dirtyDoc);

        assertFalse(isValid);
    }
}