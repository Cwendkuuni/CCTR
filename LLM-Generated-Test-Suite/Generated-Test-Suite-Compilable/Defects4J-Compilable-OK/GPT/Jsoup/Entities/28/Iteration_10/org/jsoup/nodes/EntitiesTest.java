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
import static org.mockito.Mockito.*;

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
    public void testEscape() {
        // Mock Document.OutputSettings
        Document.OutputSettings mockOutputSettings = mock(Document.OutputSettings.class);
        when(mockOutputSettings.encoder()).thenReturn(encoder);
        when(mockOutputSettings.escapeMode()).thenReturn(EscapeMode.base);

        String input = "<div>Sample & text</div>";
        String expected = "&lt;div&gt;Sample &amp; text&lt;/div&gt;";
        assertEquals(expected, Entities.escape(input, mockOutputSettings));
    }

    @Test
    public void testEscapeWithDifferentEscapeMode() {
        // Mock Document.OutputSettings
        Document.OutputSettings mockOutputSettings = mock(Document.OutputSettings.class);
        when(mockOutputSettings.encoder()).thenReturn(encoder);
        when(mockOutputSettings.escapeMode()).thenReturn(EscapeMode.xhtml);

        String input = "<div>Sample & text</div>";
        String expected = "&lt;div&gt;Sample &amp; text&lt;/div&gt;";
        assertEquals(expected, Entities.escape(input, mockOutputSettings));
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
    public void testUnescapeWithoutSemicolon() {
        String input = "&ltdiv&gtSample &amp text&ltdiv&gt";
        String expected = "<div>Sample & text<div>";
        assertEquals(expected, Entities.unescape(input, false));
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