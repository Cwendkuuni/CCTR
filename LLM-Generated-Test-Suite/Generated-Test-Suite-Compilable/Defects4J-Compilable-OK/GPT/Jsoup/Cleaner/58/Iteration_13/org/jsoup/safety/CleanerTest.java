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
    private Whitelist mockWhitelist;

    @Before
    public void setUp() {
        mockWhitelist = mock(Whitelist.class);
        cleaner = new Cleaner(mockWhitelist);
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
        when(mockWhitelist.isSafeTag("p")).thenReturn(true);
        when(mockWhitelist.isSafeAttribute(anyString(), any(Element.class), any())).thenReturn(true);

        Document dirtyDoc = Parser.parse("<p>Safe content</p>", "");
        Document cleanDoc = cleaner.clean(dirtyDoc);

        assertNotNull(cleanDoc);
        assertEquals("<p>Safe content</p>", cleanDoc.body().html());
    }

    @Test
    public void testCleanWithUnsafeTags() {
        when(mockWhitelist.isSafeTag("p")).thenReturn(false);

        Document dirtyDoc = Parser.parse("<p>Unsafe content</p>", "");
        Document cleanDoc = cleaner.clean(dirtyDoc);

        assertNotNull(cleanDoc);
        assertEquals("", cleanDoc.body().html());
    }

    @Test
    public void testIsValidWithValidDocument() {
        when(mockWhitelist.isSafeTag("p")).thenReturn(true);
        when(mockWhitelist.isSafeAttribute(anyString(), any(Element.class), any())).thenReturn(true);

        Document dirtyDoc = Parser.parse("<p>Valid content</p>", "");
        boolean isValid = cleaner.isValid(dirtyDoc);

        assertTrue(isValid);
    }

    @Test
    public void testIsValidWithInvalidDocument() {
        when(mockWhitelist.isSafeTag("p")).thenReturn(false);

        Document dirtyDoc = Parser.parse("<p>Invalid content</p>", "");
        boolean isValid = cleaner.isValid(dirtyDoc);

        assertFalse(isValid);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsValidWithNullDocument() {
        cleaner.isValid(null);
    }
}