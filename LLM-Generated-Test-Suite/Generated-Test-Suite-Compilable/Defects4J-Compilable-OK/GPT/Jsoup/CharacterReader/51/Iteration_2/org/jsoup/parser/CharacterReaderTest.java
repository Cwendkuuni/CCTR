package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterReaderTest {

    private CharacterReader reader;

    @Before
    public void setUp() {
        reader = new CharacterReader("Hello, World! 123 <tag> &amp;");
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
        while (!reader.isEmpty()) {
            reader.consume();
        }
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

    @Test
    public void testConsumeAsString() {
        assertEquals("H", reader.consumeAsString());
        assertEquals("e", reader.consumeAsString());
    }

    @Test
    public void testNextIndexOfChar() {
        assertEquals(5, reader.nextIndexOf(','));
        assertEquals(-1, reader.nextIndexOf('z'));
    }

    @Test
    public void testNextIndexOfString() {
        assertEquals(7, reader.nextIndexOf("World"));
        assertEquals(-1, reader.nextIndexOf("NotFound"));
    }

    @Test
    public void testConsumeToChar() {
        assertEquals("Hello", reader.consumeTo(','));
        assertEquals(", World! 123 <tag> &amp;", reader.toString());
    }

    @Test
    public void testConsumeToString() {
        assertEquals("Hello, ", reader.consumeTo("World"));
        assertEquals("World! 123 <tag> &amp;", reader.toString());
    }

    @Test
    public void testConsumeToAny() {
        assertEquals("Hello", reader.consumeToAny(',', ' '));
        assertEquals(", World! 123 <tag> &amp;", reader.toString());
    }

    @Test
    public void testConsumeToAnySorted() {
        assertEquals("Hello", reader.consumeToAnySorted(',', ' '));
        assertEquals(", World! 123 <tag> &amp;", reader.toString());
    }

    @Test
    public void testConsumeData() {
        reader.consumeTo(' ');
        assertEquals("World! 123 ", reader.consumeData());
        assertEquals("<tag> &amp;", reader.toString());
    }

    @Test
    public void testConsumeTagName() {
        reader.consumeTo('<');
        reader.consume();
        assertEquals("tag", reader.consumeTagName());
        assertEquals("> &amp;", reader.toString());
    }

    @Test
    public void testConsumeToEnd() {
        assertEquals("Hello, World! 123 <tag> &amp;", reader.consumeToEnd());
        assertTrue(reader.isEmpty());
    }

    @Test
    public void testConsumeLetterSequence() {
        assertEquals("Hello", reader.consumeLetterSequence());
        assertEquals(", World! 123 <tag> &amp;", reader.toString());
    }

    @Test
    public void testConsumeLetterThenDigitSequence() {
        reader.consumeTo(' ');
        reader.consume();
        assertEquals("World123", reader.consumeLetterThenDigitSequence());
        assertEquals(" <tag> &amp;", reader.toString());
    }

    @Test
    public void testConsumeHexSequence() {
        reader = new CharacterReader("1A2F3G");
        assertEquals("1A2F", reader.consumeHexSequence());
        assertEquals("3G", reader.toString());
    }

    @Test
    public void testConsumeDigitSequence() {
        reader.consumeTo('1');
        assertEquals("123", reader.consumeDigitSequence());
        assertEquals(" <tag> &amp;", reader.toString());
    }

    @Test
    public void testMatchesChar() {
        assertTrue(reader.matches('H'));
        reader.consume();
        assertFalse(reader.matches('H'));
    }

    @Test
    public void testMatchesString() {
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
        reader.consume();
        assertTrue(reader.matchesDigit());
    }

    @Test
    public void testMatchConsume() {
        assertTrue(reader.matchConsume("Hello"));
        assertEquals(", World! 123 <tag> &amp;", reader.toString());
    }

    @Test
    public void testMatchConsumeIgnoreCase() {
        assertTrue(reader.matchConsumeIgnoreCase("hello"));
        assertEquals(", World! 123 <tag> &amp;", reader.toString());
    }

    @Test
    public void testContainsIgnoreCase() {
        assertTrue(reader.containsIgnoreCase("WORLD"));
        assertFalse(reader.containsIgnoreCase("NotFound"));
    }

    @Test
    public void testToString() {
        assertEquals("Hello, World! 123 <tag> &amp;", reader.toString());
        reader.consumeTo(' ');
        assertEquals("World! 123 <tag> &amp;", reader.toString());
    }

    @Test
    public void testRangeEquals() {
        assertTrue(reader.rangeEquals(0, 5, "Hello"));
        assertFalse(reader.rangeEquals(0, 5, "World"));
    }
}