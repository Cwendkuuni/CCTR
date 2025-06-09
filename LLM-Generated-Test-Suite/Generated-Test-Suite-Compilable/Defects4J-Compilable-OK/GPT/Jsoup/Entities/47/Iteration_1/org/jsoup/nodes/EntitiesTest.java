package org.jsoup.nodes;

import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EntitiesTest {

    private Document.OutputSettings mockOutputSettings;
    private CharsetEncoder mockEncoder;

    @Before
    public void setUp() {
        mockOutputSettings = mock(Document.OutputSettings.class);
        mockEncoder = mock(CharsetEncoder.class);
        when(mockOutputSettings.escapeMode()).thenReturn(Entities.EscapeMode.base);
        when(mockOutputSettings.encoder()).thenReturn(mockEncoder);
        when(mockEncoder.charset()).thenReturn(Charset.forName("UTF-8"));
        when(mockEncoder.canEncode(anyChar())).thenReturn(true);
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
        String result = Entities.escape(input, mockOutputSettings);
        assertEquals(expected, result);
    }

    @Test
    public void testUnescape() {
        String input = "This is a &lt;test&gt; &amp; &quot;example&quot;.";
        String expected = "This is a <test> & \"example\".";
        String result = Entities.unescape(input);
        assertEquals(expected, result);
    }

    @Test
    public void testUnescapeStrict() {
        String input = "This is a &lt;test&gt; &amp; &quot;example&quot;.";
        String expected = "This is a <test> & \"example\".";
        String result = Entities.unescape(input, true);
        assertEquals(expected, result);
    }
}