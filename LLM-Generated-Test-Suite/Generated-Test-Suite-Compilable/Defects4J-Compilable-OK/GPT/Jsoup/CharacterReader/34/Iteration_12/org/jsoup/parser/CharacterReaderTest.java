package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterReaderTest {

    private CharacterReader reader;

    @Before
    public void setUp() {
        reader = new CharacterReader("Hello World 1234! <tag> </tag>");
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
        assertEquals('H', reader.consume());
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
        assertEquals(4, reader.nextIndexOf('o'));
        assertEquals(-1, reader.nextIndexOf('z'));
    }

    @Test
    public void testNextIndexOfSequence() {
        assertEquals(6, reader.nextIndexOf("World"));
        assertEquals(-1, reader.nextIndexOf("NotInString"));
    }

    @Test
    public void testConsumeToChar() {
        assertEquals("Hello", reader.consumeTo(' '));
        assertEquals("World", reader.consumeTo(' '));
    }

    @Test
    public void testConsumeToSequence() {
        assertEquals("Hello ", reader.consumeTo("World"));
        assertEquals("World 1234! <tag> ", reader.consumeTo("</tag>"));
    }

    @Test
    public void testConsumeToAny() {
        assertEquals("Hello", reader.consumeToAny(' ', '!'));
        assertEquals(" World 1234", reader.consumeToAny('!', '<'));
    }

    @Test
    public void testConsumeToEnd() {
        reader.consumeTo('!');
        assertEquals("! <tag> </tag>", reader.consumeToEnd());
    }

    @Test
    public void testConsumeLetterSequence() {
        assertEquals("Hello", reader.consumeLetterSequence());
        reader.consume(); // consume space
        assertEquals("World", reader.consumeLetterSequence());
    }

    @Test
    public void testConsumeLetterThenDigitSequence() {
        assertEquals("Hello", reader.consumeLetterThenDigitSequence());
        reader.consume(); // consume space
        assertEquals("World", reader.consumeLetterThenDigitSequence());
        reader.consume(); // consume space
        assertEquals("1234", reader.consumeLetterThenDigitSequence());
    }

    @Test
    public void testConsumeHexSequence() {
        reader = new CharacterReader("1a2b3c");
        assertEquals("1a2b3c", reader.consumeHexSequence());
    }

    @Test
    public void testConsumeDigitSequence() {
        reader.consumeTo('1');
        assertEquals("1234", reader.consumeDigitSequence());
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
        assertTrue(reader.matchesAny('H', 'e', 'l'));
        assertFalse(reader.matchesAny('x', 'y', 'z'));
    }

    @Test
    public void testMatchesLetter() {
        assertTrue(reader.matchesLetter());
        reader.consumeTo(' ');
        reader.consume(); // consume space
        assertTrue(reader.matchesLetter());
    }

    @Test
    public void testMatchesDigit() {
        reader.consumeTo('1');
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
        assertTrue(reader.containsIgnoreCase("world"));
        assertFalse(reader.containsIgnoreCase("notInString"));
    }

    @Test
    public void testToString() {
        assertEquals("Hello World 1234! <tag> </tag>", reader.toString());
        reader.consumeTo('!');
        assertEquals("! <tag> </tag>", reader.toString());
    }
}