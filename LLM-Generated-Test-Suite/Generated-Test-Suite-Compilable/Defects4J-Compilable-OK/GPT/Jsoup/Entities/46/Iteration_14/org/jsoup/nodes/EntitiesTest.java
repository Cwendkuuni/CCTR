package org.jsoup.nodes;

import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Document;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import static org.junit.Assert.*;

public class EntitiesTest {

    private static Document.OutputSettings outputSettings;

    @BeforeClass
    public static void setUp() {
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
        String input = "<div>Sample & text</div>";
        String expected = "&lt;div&gt;Sample &amp; text&lt;/div&gt;";
        assertEquals(expected, Entities.escape(input, outputSettings));
    }

    @Test
    public void testEscapeWithXhtmlMode() {
        outputSettings.escapeMode(Entities.EscapeMode.xhtml);
        String input = "<div>Sample & text</div>";
        String expected = "&lt;div&gt;Sample &amp; text&lt;/div&gt;";
        assertEquals(expected, Entities.escape(input, outputSettings));
    }

    @Test
    public void testUnescape() {
        String input = "&lt;div&gt;Sample &amp; text&lt;/div&gt;";
        String expected = "<div>Sample & text</div>";
        assertEquals(expected, Entities.unescape(input));
    }

    @Test
    public void testUnescapeStrict() {
        String input = "&lt;div&gt;Sample &amp; text&lt;/div&gt;";
        String expected = "<div>Sample & text</div>";
        assertEquals(expected, Entities.unescape(input, true));
    }

    @Test
    public void testUnescapeNonStrict() {
        String input = "&ltdiv&gtSample &amp text&lt/div&gt";
        String expected = "<div>Sample & text</div>";
        assertEquals(expected, Entities.unescape(input, false));
    }

    @Test
    public void testEscapeWithNonAsciiCharacters() {
        String input = "Sample text with emoji 😊";
        String expected = "Sample text with emoji 😊";
        assertEquals(expected, Entities.escape(input, outputSettings));
    }

    @Test
    public void testEscapeWithSupplementaryCharacters() {
        String input = "Sample text with supplementary character \uD83D\uDE00";
        String expected = "Sample text with supplementary character \uD83D\uDE00";
        assertEquals(expected, Entities.escape(input, outputSettings));
    }

    @Test
    public void testEscapeWithDifferentCharsets() {
        CharsetEncoder encoder = Charset.forName("US-ASCII").newEncoder();
        outputSettings.charset(encoder.charset());
        String input = "Sample text with emoji 😊";
        String expected = "Sample text with emoji &#x1f60a;";
        assertEquals(expected, Entities.escape(input, outputSettings));
    }
}