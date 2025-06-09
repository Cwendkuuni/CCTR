package org.jsoup.nodes;

import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Entities.EscapeMode;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EntitiesTest {

    private static CharsetEncoder mockEncoder;

    @BeforeClass
    public static void setUp() {
        mockEncoder = mock(CharsetEncoder.class);
        when(mockEncoder.canEncode(anyChar())).thenReturn(true);
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
    public void testEscapeWithXhtmlMode() {
        String input = "< & >";
        String expected = "&lt; &amp; &gt;";
        String result = Entities.escape(input, mockEncoder, EscapeMode.xhtml);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeWithBaseMode() {
        String input = "< & >";
        String expected = "&lt; &amp; &gt;";
        String result = Entities.escape(input, mockEncoder, EscapeMode.base);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeWithExtendedMode() {
        String input = "< & >";
        String expected = "&lt; &amp; &gt;";
        String result = Entities.escape(input, mockEncoder, EscapeMode.extended);
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
    public void testEscapeWithNonEncodableCharacter() {
        when(mockEncoder.canEncode(anyChar())).thenReturn(false);
        String input = "abc";
        String expected = "&#97;&#98;&#99;";
        String result = Entities.escape(input, mockEncoder, EscapeMode.base);
        assertEquals(expected, result);
    }

    @Test
    public void testEscapeWithMixedEncodableCharacters() {
        when(mockEncoder.canEncode('a')).thenReturn(true);
        when(mockEncoder.canEncode('b')).thenReturn(false);
        when(mockEncoder.canEncode('c')).thenReturn(true);
        String input = "abc";
        String expected = "a&#98;c";
        String result = Entities.escape(input, mockEncoder, EscapeMode.base);
        assertEquals(expected, result);
    }
}