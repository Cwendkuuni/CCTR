package org.jsoup.nodes;

import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Map;

import static org.junit.Assert.*;

public class EntitiesTest {

    private Document.OutputSettings outputSettings;

    @Before
    public void setUp() {
        outputSettings = new Document.OutputSettings();
        outputSettings.charset(Charset.forName("UTF-8"));
        outputSettings.escapeMode(Entities.EscapeMode.base);
    }

    @Test
    public void testIsNamedEntity() {
        assertTrue(Entities.isNamedEntity("lt"));
        assertTrue(Entities.isNamedEntity("amp"));
        assertFalse(Entities.isNamedEntity("nonexistent"));
    }

    @Test
    public void testIsBaseNamedEntity() {
        assertTrue(Entities.isBaseNamedEntity("lt"));
        assertTrue(Entities.isBaseNamedEntity("amp"));
        assertFalse(Entities.isBaseNamedEntity("nonexistent"));
    }

    @Test
    public void testGetCharacterByName() {
        assertEquals(Character.valueOf('<'), Entities.getCharacterByName("lt"));
        assertEquals(Character.valueOf('&'), Entities.getCharacterByName("amp"));
        assertNull(Entities.getCharacterByName("nonexistent"));
    }

    @Test
    public void testEscape() {
        String input = "This & that < those > these \"quotes\"";
        String expected = "This &amp; that &lt; those &gt; these &quot;quotes&quot;";
        assertEquals(expected, Entities.escape(input, outputSettings));
    }

    @Test
    public void testEscapeWithXhtmlMode() {
        outputSettings.escapeMode(Entities.EscapeMode.xhtml);
        String input = "This & that < those > these \"quotes\"";
        String expected = "This &amp; that &lt; those &gt; these &quot;quotes&quot;";
        assertEquals(expected, Entities.escape(input, outputSettings));
    }

    @Test
    public void testUnescape() {
        String input = "This &amp; that &lt; those &gt; these &quot;quotes&quot;";
        String expected = "This & that < those > these \"quotes\"";
        assertEquals(expected, Entities.unescape(input));
    }

    @Test
    public void testUnescapeStrict() {
        String input = "This &amp; that &lt; those &gt; these &quot;quotes&quot;";
        String expected = "This & that < those > these \"quotes\"";
        assertEquals(expected, Entities.unescape(input, true));
    }

    @Test
    public void testEscapeNonAsciiCharacters() {
        String input = "Café & Restaurant";
        String expected = "Caf&#xe9; &amp; Restaurant";
        assertEquals(expected, Entities.escape(input, outputSettings));
    }

    @Test
    public void testEscapeWithDifferentCharset() {
        outputSettings.charset(Charset.forName("US-ASCII"));
        String input = "Café & Restaurant";
        String expected = "Caf&#xe9; &amp; Restaurant";
        assertEquals(expected, Entities.escape(input, outputSettings));
    }

    @Test
    public void testEscapeWithSupplementaryCharacters() {
        String input = "Emoji: \uD83D\uDE00";
        String expected = "Emoji: &#x1f600;";
        assertEquals(expected, Entities.escape(input, outputSettings));
    }

    @Test
    public void testEscapeWithWhitespaceNormalization() {
        String input = "   Leading and trailing spaces   ";
        String expected = " Leading and trailing spaces ";
        StringBuilder accum = new StringBuilder();
        Entities.escape(accum, input, outputSettings, false, true, true);
        assertEquals(expected, accum.toString());
    }

    @Test
    public void testEscapeWithInAttribute() {
        String input = "This & that < those > these \"quotes\"";
        String expected = "This &amp; that < those > these &quot;quotes&quot;";
        StringBuilder accum = new StringBuilder();
        Entities.escape(accum, input, outputSettings, true, false, false);
        assertEquals(expected, accum.toString());
    }
}