package org.jsoup.parser;

import org.jsoup.UncheckedIOException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

public class CharacterReaderTest {
    private CharacterReader reader;

    @Before
    public void setUp() {
        reader = new CharacterReader(new StringReader("Hello, World! 1234 <tag> &amp;"), 32);
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
        assertEquals(5, reader.nextIndexOf(','));
        assertEquals(-1, reader.nextIndexOf('x'));
    }

    @Test
    public void testNextIndexOfSequence() {
        assertEquals(7, reader.nextIndexOf("World"));
        assertEquals(-1, reader.nextIndexOf("xyz"));
    }

    @Test
    public void testConsumeToChar() {
        assertEquals("Hello", reader.consumeTo(','));
        assertEquals(", World! 1234 <tag> &amp;", reader.consumeToEnd());
    }

    @Test
    public void testConsumeToSequence() {
        assertEquals("Hello, ", reader.consumeTo("World"));
        assertEquals("World! 1234 <tag> &amp;", reader.consumeToEnd());
    }

    @Test
    public void testConsumeToAny() {
        assertEquals("Hello", reader.consumeToAny(',', '!'));
        assertEquals(", World", reader.consumeToAny('!', '1'));
    }

    @Test
    public void testConsumeToAnySorted() {
        assertEquals("Hello", reader.consumeToAnySorted(',', '!'));
        assertEquals(", World", reader.consumeToAnySorted('!', '1'));
    }

    @Test
    public void testConsumeData() {
        assertEquals("Hello, World! 1234 ", reader.consumeData());
        assertEquals("<tag> &amp;", reader.consumeToEnd());
    }

    @Test
    public void testConsumeTagName() {
        reader.consumeTo('<');
        reader.consume();
        assertEquals("tag", reader.consumeTagName());
    }

    @Test
    public void testConsumeToEnd() {
        assertEquals("Hello, World! 1234 <tag> &amp;", reader.consumeToEnd());
        assertTrue(reader.isEmpty());
    }

    @Test
    public void testConsumeLetterSequence() {
        assertEquals("Hello", reader.consumeLetterSequence());
        assertEquals(", World! 1234 <tag> &amp;", reader.consumeToEnd());
    }

    @Test
    public void testConsumeLetterThenDigitSequence() {
        reader.consumeTo(' ');
        reader.consume();
        assertEquals("1234", reader.consumeLetterThenDigitSequence());
    }

    @Test
    public void testConsumeHexSequence() {
        reader = new CharacterReader(new StringReader("1a2F"), 32);
        assertEquals("1a2F", reader.consumeHexSequence());
    }

    @Test
    public void testConsumeDigitSequence() {
        reader.consumeTo('1');
        assertEquals("1234", reader.consumeDigitSequence());
    }

    @Test
    public void testMatchesChar() {
        assertTrue(reader.matches('H'));
        reader.consume();
        assertFalse(reader.matches('H'));
    }

    @Test
    public void testMatchesSequence() {
        assertTrue(reader.matches("Hello"));
        reader.consume();
        assertFalse(reader.matches("Hello"));
    }

    @Test
    public void testMatchesIgnoreCase() {
        assertTrue(reader.matchesIgnoreCase("hello"));
        reader.consume();
        assertFalse(reader.matchesIgnoreCase("hello"));
    }

    @Test
    public void testMatchesAny() {
        assertTrue(reader.matchesAny('H', 'e'));
        reader.consume();
        assertTrue(reader.matchesAny('H', 'e'));
    }

    @Test
    public void testMatchesAnySorted() {
        assertTrue(reader.matchesAnySorted(new char[]{'H', 'e'}));
        reader.consume();
        assertTrue(reader.matchesAnySorted(new char[]{'H', 'e'}));
    }

    @Test
    public void testMatchesLetter() {
        assertTrue(reader.matchesLetter());
        reader.consumeTo(',');
        reader.consume();
        assertFalse(reader.matchesLetter());
    }

    @Test
    public void testMatchesDigit() {
        reader.consumeTo('1');
        assertTrue(reader.matchesDigit());
    }

    @Test
    public void testMatchConsume() {
        assertTrue(reader.matchConsume("Hello"));
        assertFalse(reader.matchConsume("Hello"));
    }

    @Test
    public void testMatchConsumeIgnoreCase() {
        assertTrue(reader.matchConsumeIgnoreCase("hello"));
        assertFalse(reader.matchConsumeIgnoreCase("hello"));
    }

    @Test
    public void testContainsIgnoreCase() {
        assertTrue(reader.containsIgnoreCase("WORLD"));
        assertFalse(reader.containsIgnoreCase("xyz"));
    }

    @Test
    public void testToString() {
        assertEquals("Hello, World! 1234 <tag> &amp;", reader.toString());
        reader.consumeTo(',');
        reader.consume();
        assertEquals(" World! 1234 <tag> &amp;", reader.toString());
    }

    @Test
    public void testRangeEquals() {
        assertTrue(reader.rangeEquals(0, 5, "Hello"));
        assertFalse(reader.rangeEquals(0, 5, "World"));
    }
}