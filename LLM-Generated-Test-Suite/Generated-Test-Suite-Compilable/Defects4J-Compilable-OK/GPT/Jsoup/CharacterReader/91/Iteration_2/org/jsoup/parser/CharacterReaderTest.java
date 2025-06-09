package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.UncheckedIOException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.*;

public class CharacterReaderTest {

    private CharacterReader reader;

    @Before
    public void setUp() {
        reader = new CharacterReader("Hello, World!");
    }

    @Test
    public void testPos() {
        assertEquals(0, reader.pos());
        reader.consume();
        assertEquals(1, reader.pos());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(reader.isEmpty());
        reader.consumeToEnd();
        assertTrue(reader.isEmpty());
    }

    @Test
    public void testCurrent() {
        assertEquals('H', reader.current());
        reader.consume();
        assertEquals('e', reader.current());
    }

    @Test
    public void testConsume() {
        assertEquals('H', reader.consume());
        assertEquals('e', reader.consume());
    }

    @Test(expected = UncheckedIOException.class)
    public void testUnconsumeException() {
        reader.unconsume();
    }

    @Test
    public void testUnconsume() {
        reader.consume();
        reader.unconsume();
        assertEquals('H', reader.current());
    }

    @Test
    public void testAdvance() {
        reader.advance();
        assertEquals('e', reader.current());
    }

    @Test
    public void testMarkAndRewindToMark() {
        reader.consume();
        reader.mark();
        reader.consume();
        reader.consume();
        reader.rewindToMark();
        assertEquals('e', reader.current());
    }

    @Test(expected = UncheckedIOException.class)
    public void testRewindToMarkException() {
        reader.rewindToMark();
    }

    @Test
    public void testNextIndexOfChar() {
        assertEquals(7, reader.nextIndexOf('W'));
        assertEquals(-1, reader.nextIndexOf('z'));
    }

    @Test
    public void testNextIndexOfSequence() {
        assertEquals(7, reader.nextIndexOf("World"));
        assertEquals(-1, reader.nextIndexOf("world"));
    }

    @Test
    public void testConsumeToChar() {
        assertEquals("Hello", reader.consumeTo(','));
        assertEquals(", World!", reader.consumeToEnd());
    }

    @Test
    public void testConsumeToSequence() {
        assertEquals("Hello", reader.consumeTo(", "));
        assertEquals(", World!", reader.consumeToEnd());
    }

    @Test
    public void testConsumeToAny() {
        assertEquals("Hello", reader.consumeToAny(',', '!'));
        assertEquals(", World", reader.consumeToAny('!'));
    }

    @Test
    public void testConsumeToAnySorted() {
        assertEquals("Hello", reader.consumeToAnySorted(',', '!'));
        assertEquals(", World", reader.consumeToAnySorted('!'));
    }

    @Test
    public void testConsumeData() {
        assertEquals("Hello, World", reader.consumeData());
    }

    @Test
    public void testConsumeTagName() {
        assertEquals("Hello", reader.consumeTagName());
    }

    @Test
    public void testConsumeToEnd() {
        assertEquals("Hello, World!", reader.consumeToEnd());
        assertTrue(reader.isEmpty());
    }

    @Test
    public void testConsumeLetterSequence() {
        assertEquals("Hello", reader.consumeLetterSequence());
    }

    @Test
    public void testConsumeLetterThenDigitSequence() {
        reader = new CharacterReader("Hello123");
        assertEquals("Hello123", reader.consumeLetterThenDigitSequence());
    }

    @Test
    public void testConsumeHexSequence() {
        reader = new CharacterReader("1a2b3c");
        assertEquals("1a2b3c", reader.consumeHexSequence());
    }

    @Test
    public void testConsumeDigitSequence() {
        reader = new CharacterReader("12345");
        assertEquals("12345", reader.consumeDigitSequence());
    }

    @Test
    public void testMatchesChar() {
        assertTrue(reader.matches('H'));
        assertFalse(reader.matches('e'));
    }

    @Test
    public void testMatchesSequence() {
        assertTrue(reader.matches("Hello"));
        assertFalse(reader.matches("World"));
    }

    @Test
    public void testMatchesIgnoreCase() {
        assertTrue(reader.matchesIgnoreCase("hello"));
        assertFalse(reader.matchesIgnoreCase("world"));
    }

    @Test
    public void testMatchesAny() {
        assertTrue(reader.matchesAny('H', 'e'));
        assertFalse(reader.matchesAny('W', 'o'));
    }

    @Test
    public void testMatchesAnySorted() {
        assertTrue(reader.matchesAnySorted(new char[]{'H', 'e'}));
        assertFalse(reader.matchesAnySorted(new char[]{'W', 'o'}));
    }

    @Test
    public void testMatchesLetter() {
        assertTrue(reader.matchesLetter());
        reader.consume();
        assertTrue(reader.matchesLetter());
    }

    @Test
    public void testMatchesDigit() {
        reader = new CharacterReader("12345");
        assertTrue(reader.matchesDigit());
    }

    @Test
    public void testMatchConsume() {
        assertTrue(reader.matchConsume("Hello"));
        assertFalse(reader.matchConsume("World"));
    }

    @Test
    public void testMatchConsumeIgnoreCase() {
        assertTrue(reader.matchConsumeIgnoreCase("hello"));
        assertFalse(reader.matchConsumeIgnoreCase("world"));
    }

    @Test
    public void testContainsIgnoreCase() {
        assertTrue(reader.containsIgnoreCase("hello"));
        assertTrue(reader.containsIgnoreCase("WORLD"));
        assertFalse(reader.containsIgnoreCase("foo"));
    }

    @Test
    public void testToString() {
        assertEquals("Hello, World!", reader.toString());
        reader.consumeTo(',');
        assertEquals(", World!", reader.toString());
    }

    @Test
    public void testRangeEquals() {
        assertTrue(reader.rangeEquals(0, 5, "Hello"));
        assertFalse(reader.rangeEquals(0, 5, "World"));
    }
}