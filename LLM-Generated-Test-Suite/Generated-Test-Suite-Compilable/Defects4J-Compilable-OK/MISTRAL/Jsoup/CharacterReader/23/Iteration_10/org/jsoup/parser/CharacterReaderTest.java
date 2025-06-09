package org.jsoup.parser;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CharacterReaderTest {

    private CharacterReader reader;

    @Before
    public void setUp() {
        reader = new CharacterReader("Hello World\r\nThis is a test.");
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
        reader = new CharacterReader("");
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
    public void testMark() {
        reader.mark();
        reader.consume();
        reader.rewindToMark();
        assertEquals('H', reader.current());
    }

    @Test
    public void testRewindToMark() {
        reader.mark();
        reader.consume();
        reader.rewindToMark();
        assertEquals('H', reader.current());
    }

    @Test
    public void testConsumeAsString() {
        assertEquals("H", reader.consumeAsString());
    }

    @Test
    public void testConsumeToChar() {
        reader = new CharacterReader("Hello World");
        assertEquals("Hello ", reader.consumeTo('W'));
    }

    @Test
    public void testConsumeToString() {
        reader = new CharacterReader("Hello World");
        assertEquals("Hello ", reader.consumeTo("World"));
    }

    @Test
    public void testConsumeToAny() {
        reader = new CharacterReader("Hello World");
        assertEquals("Hello ", reader.consumeToAny('W', 'o'));
    }

    @Test
    public void testConsumeToEnd() {
        reader = new CharacterReader("Hello World");
        assertEquals("Hello World", reader.consumeToEnd());
    }

    @Test
    public void testConsumeLetterSequence() {
        reader = new CharacterReader("Hello123");
        assertEquals("Hello", reader.consumeLetterSequence());
    }

    @Test
    public void testConsumeHexSequence() {
        reader = new CharacterReader("123abcDEF");
        assertEquals("123abcDEF", reader.consumeHexSequence());
    }

    @Test
    public void testConsumeDigitSequence() {
        reader = new CharacterReader("123abc");
        assertEquals("123", reader.consumeDigitSequence());
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
        assertFalse(reader.matchesIgnoreCase("Hello"));
    }

    @Test
    public void testMatchesAny() {
        assertTrue(reader.matchesAny('H', 'W'));
        reader.consume();
        assertFalse(reader.matchesAny('H', 'W'));
    }

    @Test
    public void testMatchesLetter() {
        assertTrue(reader.matchesLetter());
        reader.consume();
        assertTrue(reader.matchesLetter());
        reader = new CharacterReader("123");
        assertFalse(reader.matchesLetter());
    }

    @Test
    public void testMatchesDigit() {
        reader = new CharacterReader("123");
        assertTrue(reader.matchesDigit());
        reader.consume();
        assertTrue(reader.matchesDigit());
        reader = new CharacterReader("abc");
        assertFalse(reader.matchesDigit());
    }

    @Test
    public void testMatchConsume() {
        assertTrue(reader.matchConsume("Hello"));
        assertFalse(reader.matchConsume("Hello"));
    }

    @Test
    public void testMatchConsumeIgnoreCase() {
        assertTrue(reader.matchConsumeIgnoreCase("hello"));
        assertFalse(reader.matchConsumeIgnoreCase("Hello"));
    }

    @Test
    public void testContainsIgnoreCase() {
        assertTrue(reader.containsIgnoreCase("world"));
        reader = new CharacterReader("Hello");
        assertFalse(reader.containsIgnoreCase("world"));
    }

    @Test
    public void testToString() {
        assertEquals("Hello World\nThis is a test.", reader.toString());
        reader.consume();
        assertEquals("ello World\nThis is a test.", reader.toString());
    }
}