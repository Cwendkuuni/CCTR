package org.jsoup.safety;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CleanerTest {

    private Whitelist whitelist;
    private Cleaner cleaner;

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
        when(whitelist.isSafeAttribute(anyString(), any(Element.class), any())).thenReturn(true);

        Document dirtyDoc = Parser.parse("<p>Safe content</p>", "");
        Document cleanDoc = cleaner.clean(dirtyDoc);

        assertEquals("<p>Safe content</p>", cleanDoc.body().html());
    }

    @Test
    public void testCleanWithUnsafeTags() {
        when(whitelist.isSafeTag("p")).thenReturn(false);

        Document dirtyDoc = Parser.parse("<p>Unsafe content</p>", "");
        Document cleanDoc = cleaner.clean(dirtyDoc);

        assertEquals("", cleanDoc.body().html());
    }

    @Test
    public void testCleanWithMixedContent() {
        when(whitelist.isSafeTag("p")).thenReturn(true);
        when(whitelist.isSafeTag("script")).thenReturn(false);
        when(whitelist.isSafeAttribute(anyString(), any(Element.class), any())).thenReturn(true);

        Document dirtyDoc = Parser.parse("<p>Safe content</p><script>Unsafe script</script>", "");
        Document cleanDoc = cleaner.clean(dirtyDoc);

        assertEquals("<p>Safe content</p>", cleanDoc.body().html());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidWithNullDocument() {
        cleaner.isValid(null);
    }

    @Test
    public void testIsValidWithValidDocument() {
        when(whitelist.isSafeTag("p")).thenReturn(true);
        when(whitelist.isSafeAttribute(anyString(), any(Element.class), any())).thenReturn(true);

        Document dirtyDoc = Parser.parse("<p>Valid content</p>", "");
        assertTrue(cleaner.isValid(dirtyDoc));
    }

    @Test
    public void testIsValidWithInvalidDocument() {
        when(whitelist.isSafeTag("p")).thenReturn(false);

        Document dirtyDoc = Parser.parse("<p>Invalid content</p>", "");
        assertFalse(cleaner.isValid(dirtyDoc));
    }

    @Test
    public void testIsValidWithMixedContent() {
        when(whitelist.isSafeTag("p")).thenReturn(true);
        when(whitelist.isSafeTag("script")).thenReturn(false);
        when(whitelist.isSafeAttribute(anyString(), any(Element.class), any())).thenReturn(true);

        Document dirtyDoc = Parser.parse("<p>Valid content</p><script>Invalid script</script>", "");
        assertFalse(cleaner.isValid(dirtyDoc));
    }
}