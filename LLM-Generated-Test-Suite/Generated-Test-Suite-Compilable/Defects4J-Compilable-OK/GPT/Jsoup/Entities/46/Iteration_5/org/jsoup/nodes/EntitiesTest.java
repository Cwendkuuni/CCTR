package org.jsoup.nodes;

import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Document;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class EntitiesTest {

    @BeforeClass
    public static void setUp() {
        // Initialize any necessary data before tests
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
        Document.OutputSettings out = new Document.OutputSettings();
        out.escapeMode(Entities.EscapeMode.base);
        String input = "<div>Test & \"escape\"</div>";
        String expected = "&lt;div&gt;Test &amp; &quot;escape&quot;&lt;/div&gt;";
        assertEquals(expected, Entities.escape(input, out));
    }

    @Test
    public void testUnescape() {
        String input = "&lt;div&gt;Test &amp; &quot;escape&quot;&lt;/div&gt;";
        String expected = "<div>Test & \"escape\"</div>";
        assertEquals(expected, Entities.unescape(input));
    }

    @Test
    public void testUnescapeStrict() {
        String input = "&lt;div&gt;Test &amp; &quot;escape&quot;&lt;/div&gt;";
        String expected = "<div>Test & \"escape\"</div>";
        assertEquals(expected, Entities.unescape(input, true));
    }

    @Test
    public void testEscapeModeGetMap() {
        Map<Character, String> xhtmlMap = Entities.EscapeMode.xhtml.getMap();
        assertNotNull(xhtmlMap);
        assertTrue(xhtmlMap.containsKey('<'));
        assertEquals("lt", xhtmlMap.get('<'));

        Map<Character, String> baseMap = Entities.EscapeMode.base.getMap();
        assertNotNull(baseMap);
        assertTrue(baseMap.containsKey('<'));
        assertEquals("lt", baseMap.get('<'));

        Map<Character, String> extendedMap = Entities.EscapeMode.extended.getMap();
        assertNotNull(extendedMap);
        assertTrue(extendedMap.containsKey('<'));
        assertEquals("lt", extendedMap.get('<'));
    }
}