package org.jsoup.nodes;

import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

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
        String input = "This is a <test> & \"example\".";
        String expected = "This is a &lt;test&gt; &amp; &quot;example&quot;.";
        assertEquals(expected, Entities.escape(input, outputSettings));
    }

    @Test
    public void testEscapeWithXhtmlMode() {
        outputSettings.escapeMode(Entities.EscapeMode.xhtml);
        String input = "This is a <test> & \"example\".";
        String expected = "This is a &lt;test&gt; &amp; &quot;example&quot;.";
        assertEquals(expected, Entities.escape(input, outputSettings));
    }

    @Test
    public void testUnescape() {
        String input = "This is a &lt;test&gt; &amp; &quot;example&quot;.";
        String expected = "This is a <test> & \"example\".";
        assertEquals(expected, Entities.unescape(input));
    }

    @Test
    public void testUnescapeStrict() {
        String input = "This is a &lt;test&gt; &amp; &quot;example&quot;.";
        String expected = "This is a <test> & \"example\".";
        assertEquals(expected, Entities.unescape(input, true));
    }

    @Test
    public void testCanEncodeAscii() {
        CharsetEncoder encoder = Charset.forName("US-ASCII").newEncoder();
        assertTrue(encoder.canEncode('A'));
        assertFalse(encoder.canEncode('é'));
    }

    @Test
    public void testCanEncodeUtf() {
        CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
        assertTrue(encoder.canEncode('A'));
        assertTrue(encoder.canEncode('é'));
    }
}