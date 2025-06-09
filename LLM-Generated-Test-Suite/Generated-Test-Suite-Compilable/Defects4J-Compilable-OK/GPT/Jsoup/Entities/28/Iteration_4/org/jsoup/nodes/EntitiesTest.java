package org.jsoup.nodes;

import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Entities.EscapeMode;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.Assert.*;

public class EntitiesTest {

    private static CharsetEncoder encoder;

    @BeforeClass
    public static void setUp() {
        encoder = StandardCharsets.UTF_8.newEncoder();
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
        String result = Entities.escape(input, encoder, EscapeMode.xhtml);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeBaseMode() {
        String input = "< & >";
        String expected = "&lt; &amp; &gt;";
        String result = Entities.escape(input, encoder, EscapeMode.base);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeExtendedMode() {
        String input = "< & >";
        String expected = "&lt; &amp; &gt;";
        String result = Entities.escape(input, encoder, EscapeMode.extended);
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
    public void testEscapeWithNonEncodableCharacters() {
        String input = "Hello \uD83D\uDE00";
        String expected = "Hello &#128512;";
        String result = Entities.escape(input, encoder, EscapeMode.base);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeWithNoEntities() {
        String input = "Hello World!";
        String expected = "Hello World!";
        String result = Entities.escape(input, encoder, EscapeMode.base);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeWithEmptyString() {
        String input = "";
        String expected = "";
        String result = Entities.escape(input, encoder, EscapeMode.base);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeWithNullString() {
        String input = null;
        String result = Entities.escape(input, encoder, EscapeMode.base);
        assertNull(result);
    }

    @Test
    public void testEscapeModeGetMap() {
        Map<Character, String> xhtmlMap = EscapeMode.xhtml.getMap();
        assertNotNull(xhtmlMap);
        assertTrue(xhtmlMap.containsKey('<'));
        assertEquals("lt", xhtmlMap.get('<'));

        Map<Character, String> baseMap = EscapeMode.base.getMap();
        assertNotNull(baseMap);
        assertTrue(baseMap.containsKey('<'));
        assertEquals("lt", baseMap.get('<'));

        Map<Character, String> extendedMap = EscapeMode.extended.getMap();
        assertNotNull(extendedMap);
        assertTrue(extendedMap.containsKey('<'));
        assertEquals("lt", extendedMap.get('<'));
    }
}