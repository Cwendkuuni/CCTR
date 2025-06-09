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
        for (int i = 0; i < 25; i++) {
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
    public void testNextIndexOfSequence() {
        assertEquals(7, reader.nextIndexOf("World"));
        assertEquals(-1, reader.nextIndexOf("NotHere"));
    }

    @Test
    public void testConsumeToChar() {
        assertEquals("Hello", reader.consumeTo(','));
        assertEquals(", World! 123 <tag> &amp;", reader.consumeToEnd());
    }

    @Test
    public void testConsumeToSequence() {
        assertEquals("Hello, ", reader.consumeTo("World"));
        assertEquals("World! 123 <tag> &amp;", reader.consumeToEnd());
    }

    @Test
    public void testConsumeToAny() {
        assertEquals("Hello", reader.consumeToAny(',', ' '));
        assertEquals(", ", reader.consumeToAny('W'));
    }

    @Test
    public void testConsumeToAnySorted() {
        assertEquals("Hello", reader.consumeToAnySorted(',', ' '));
        assertEquals(", ", reader.consumeToAnySorted('W'));
    }

    @Test
    public void testConsumeData() {
        reader.consumeTo(' ');
        assertEquals("World! 123 ", reader.consumeData());
    }

    @Test
    public void testConsumeTagName() {
        reader.consumeTo('<');
        reader.consume();
        assertEquals("tag", reader.consumeTagName());
    }

    @Test
    public void testConsumeToEnd() {
        assertEquals("Hello, World! 123 <tag> &amp;", reader.consumeToEnd());
        assertEquals("", reader.consumeToEnd());
    }

    @Test
    public void testConsumeLetterSequence() {
        assertEquals("Hello", reader.consumeLetterSequence());
        reader.consumeTo(' ');
        assertEquals("World", reader.consumeLetterSequence());
    }

    @Test
    public void testConsumeLetterThenDigitSequence() {
        reader.consumeTo(' ');
        assertEquals("World123", reader.consumeLetterThenDigitSequence());
    }

    @Test
    public void testConsumeHexSequence() {
        reader = new CharacterReader("1a2F");
        assertEquals("1a2F", reader.consumeHexSequence());
    }

    @Test
    public void testConsumeDigitSequence() {
        reader.consumeTo('1');
        assertEquals("123", reader.consumeDigitSequence());
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
        assertFalse(reader.matchesAny('x', 'y'));
    }

    @Test
    public void testMatchesAnySorted() {
        assertTrue(reader.matchesAnySorted(new char[]{'H', 'e'}));
        assertFalse(reader.matchesAnySorted(new char[]{'x', 'y'}));
    }

    @Test
    public void testMatchesLetter() {
        assertTrue(reader.matchesLetter());
        reader.consumeTo(' ');
        assertFalse(reader.matchesLetter());
    }

    @Test
    public void testMatchesDigit() {
        reader.consumeTo('1');
        assertTrue(reader.matchesDigit());
        reader.consume();
        assertFalse(reader.matchesDigit());
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
        assertTrue(reader.containsIgnoreCase("WORLD"));
        assertFalse(reader.containsIgnoreCase("notHere"));
    }

    @Test
    public void testToString() {
        assertEquals("Hello, World! 123 <tag> &amp;", reader.toString());
        reader.consumeTo('W');
        assertEquals("World! 123 <tag> &amp;", reader.toString());
    }

    @Test
    public void testRangeEquals() {
        assertTrue(reader.rangeEquals(0, 5, "Hello"));
        assertFalse(reader.rangeEquals(0, 5, "World"));
    }
}