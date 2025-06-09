package org.jsoup.nodes;

import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Entities.EscapeMode;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import static org.junit.Assert.*;

public class EntitiesTest {

    private static CharsetEncoder asciiEncoder;

    @BeforeClass
    public static void setUp() {
        asciiEncoder = Charset.forName("US-ASCII").newEncoder();
    }

    @Test
    public void testIsNamedEntity() {
        assertTrue(Entities.isNamedEntity("lt"));
        assertTrue(Entities.isNamedEntity("amp"));
        assertFalse(Entities.isNamedEntity("unknown"));
    }

    @Test
    public void testGetCharacterByName() {
        assertEquals(Character.valueOf('<'), Entities.getCharacterByName("lt"));
        assertEquals(Character.valueOf('&'), Entities.getCharacterByName("amp"));
        assertNull(Entities.getCharacterByName("unknown"));
    }

    @Test
    public void testEscapeXhtmlMode() {
        String input = "< & >";
        String expected = "&lt; &amp; &gt;";
        String result = Entities.escape(input, asciiEncoder, EscapeMode.xhtml);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeBaseMode() {
        String input = "< & >";
        String expected = "&lt; &amp; &gt;";
        String result = Entities.escape(input, asciiEncoder, EscapeMode.base);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeExtendedMode() {
        String input = "< & >";
        String expected = "&lt; &amp; &gt;";
        String result = Entities.escape(input, asciiEncoder, EscapeMode.extended);
        assertEquals(expected, result);
    }

    @Test
    public void testUnescape() {
        String input = "&lt; &amp; &gt;";
        String expected = "< & >";
        String result = Entities.unescape(input);
        assertEquals(expected, result);
    }

    @Test
    public void testUnescapeStrict() {
        String input = "&lt; &amp; &gt;";
        String expected = "< & >";
        String result = Entities.unescape(input, true);
        assertEquals(expected, result);
    }

    @Test
    public void testUnescapeNonStrict() {
        String input = "&lt &amp &gt";
        String expected = "< & >";
        String result = Entities.unescape(input, false);
        assertEquals(expected, result);
    }

    @Test
    public void testUnescapeInvalidEntity() {
        String input = "&unknown;";
        String expected = "&unknown;";
        String result = Entities.unescape(input);
        assertEquals(expected, result);
    }

    @Test
    public void testUnescapeNumericEntity() {
        String input = "&#60; &#62;";
        String expected = "< >";
        String result = Entities.unescape(input);
        assertEquals(expected, result);
    }

    @Test
    public void testUnescapeHexEntity() {
        String input = "&#x3C; &#x3E;";
        String expected = "< >";
        String result = Entities.unescape(input);
        assertEquals(expected, result);
    }
}