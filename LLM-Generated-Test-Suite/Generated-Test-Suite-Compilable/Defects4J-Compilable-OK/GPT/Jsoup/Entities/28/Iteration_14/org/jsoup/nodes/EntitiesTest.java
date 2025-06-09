package org.jsoup.nodes;

import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.nodes.Document;
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
        assertFalse(Entities.isNamedEntity("nonexistent"));
    }

    @Test
    public void testGetCharacterByName() {
        assertEquals(Character.valueOf('<'), Entities.getCharacterByName("lt"));
        assertEquals(Character.valueOf('&'), Entities.getCharacterByName("amp"));
        assertNull(Entities.getCharacterByName("nonexistent"));
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
        String input = "&nonexistent;";
        String expected = "&nonexistent;";
        String result = Entities.unescape(input);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeNonAsciiCharacter() {
        String input = "Hello, 你好";
        String expected = "Hello, &#20320;&#22909;";
        String result = Entities.escape(input, asciiEncoder, EscapeMode.base);
        assertEquals(expected, result);
    }
}