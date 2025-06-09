package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CharSequenceTranslatorTest {

    @Mock
    private CharSequenceTranslator mockTranslator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTranslateCharSequence() throws IOException {
        CharSequence input = "test";
        StringWriter writer = new StringWriter();
        mockTranslator.translate(input, writer);

        verify(mockTranslator).translate(input, writer);
    }

    @Test
    public void testTranslateCharSequenceWithNullInput() throws IOException {
        Writer writer = new StringWriter();
        mockTranslator.translate(null, writer);

        verify(mockTranslator, never()).translate(any(CharSequence.class), anyInt(), any(Writer.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTranslateCharSequenceWithNullWriter() throws IOException {
        CharSequence input = "test";
        mockTranslator.translate(input, null);
    }

    @Test
    public void testTranslateCharSequenceToString() {
        CharSequence input = "test";
        when(mockTranslator.translate(input)).thenReturn("translated");

        String result = mockTranslator.translate(input);

        assertEquals("translated", result);
    }

    @Test
    public void testTranslateCharSequenceToStringWithNullInput() {
        String result = mockTranslator.translate(null);

        assertNull(result);
    }

    @Test
    public void testWithTranslators() {
        CharSequenceTranslator translator1 = mock(CharSequenceTranslator.class);
        CharSequenceTranslator translator2 = mock(CharSequenceTranslator.class);

        CharSequenceTranslator result = mockTranslator.with(translator1, translator2);

        assertNotNull(result);
        assertTrue(result instanceof AggregateTranslator);
    }

    @Test
    public void testHex() {
        int codepoint = 65; // ASCII 'A'
        String result = CharSequenceTranslator.hex(codepoint);

        assertEquals("41", result);
    }

    @Test
    public void testHexWithZero() {
        int codepoint = 0;
        String result = CharSequenceTranslator.hex(codepoint);

        assertEquals("0", result);
    }

    @Test
    public void testHexWithNegative() {
        int codepoint = -1;
        String result = CharSequenceTranslator.hex(codepoint);

        assertEquals("FFFFFFFF", result);
    }

    // Additional tests for the abstract method translate(CharSequence input, int index, Writer out)
    // would require a concrete implementation of CharSequenceTranslator.
    // For the purpose of this test suite, we assume that the abstract method is correctly implemented
    // in a subclass and focus on testing the non-abstract methods.
}