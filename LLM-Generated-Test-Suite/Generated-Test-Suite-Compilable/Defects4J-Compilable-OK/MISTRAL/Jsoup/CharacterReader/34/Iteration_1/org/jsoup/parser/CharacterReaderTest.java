package org.jsoup.parser;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CharacterReaderTest {

    private CharacterReader reader;

    @Before
    public void setUp() {
        reader = new CharacterReader("Test String");
    }

    @Test
    public void testPos() {
        assertEquals(0, reader.pos());
        reader.advance();
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
        assertEquals('T', reader.current());
        reader.consume();
        assertEquals('e', reader.current());
    }

    @Test
    public void testConsume() {
        assertEquals('T', reader.consume());
        assertEquals('e', reader.consume());
    }

    @Test
    public void testUnconsume() {
        reader.consume();
        reader.unconsume();
        assertEquals('T', reader.current());
    }

    @Test
    public void testAdvance() {
        reader.advance();
        assertEquals('e', reader.current());
    }

    @Test
    public void testMark() {
        reader.mark();
        reader.advance();
        reader.rewindToMark();
        assertEquals('T', reader.current());
    }

    @Test
    public void testRewindToMark() {
        reader.mark();
        reader.advance();
        reader.rewindToMark();
        assertEquals('T', reader.current());
    }

    @Test
    public void testConsumeAsString() {
        assertEquals("T", reader.consumeAsString());
        assertEquals("e", reader.consumeAsString());
    }

    @Test
    public void testNextIndexOfChar() {
        assertEquals(4, reader.nextIndexOf('S'));
        assertEquals(-1, reader.nextIndexOf('Z'));
    }

    @Test
    public void testNextIndexOfCharSequence() {
        assertEquals(5, reader.nextIndexOf("String"));
        assertEquals(-1, reader.nextIndexOf("Nonexistent"));
    }

    @Test
    public void testConsumeToChar() {
        reader = new CharacterReader("Test String");
        assertEquals("Test ", reader.consumeTo('S'));
    }

    @Test
    public void testConsumeToString() {
        reader = new CharacterReader("Test String");
        assertEquals("Test ", reader.consumeTo("String"));
    }

    @Test
    public void testConsumeToAny() {
        reader = new CharacterReader("Test String");
        assertEquals("Test ", reader.consumeToAny('S', 'T'));
    }

    @Test
    public void testConsumeToEnd() {
        reader = new CharacterReader("Test String");
        assertEquals("Test String", reader.consumeToEnd());
    }

    @Test
    public void testConsumeLetterSequence() {
        reader = new CharacterReader("Test123");
        assertEquals("Test", reader.consumeLetterSequence());
    }

    @Test
    public void testConsumeLetterThenDigitSequence() {
        reader = new CharacterReader("Test123");
        assertEquals("Test123", reader.consumeLetterThenDigitSequence());
    }

    @Test
    public void testConsumeHexSequence() {
        reader = new CharacterReader("123ABC");
        assertEquals("123ABC", reader.consumeHexSequence());
    }

    @Test
    public void testConsumeDigitSequence() {
        reader = new CharacterReader("123ABC");
        assertEquals("123", reader.consumeDigitSequence());
    }

    @Test
    public void testMatchesChar() {
        assertTrue(reader.matches('T'));
        reader.consume();
        assertFalse(reader.matches('T'));
    }

    @Test
    public void testMatchesString() {
        assertTrue(reader.matches("Test"));
        reader.consume();
        assertFalse(reader.matches("Test"));
    }

    @Test
    public void testMatchesIgnoreCase() {
        assertTrue(reader.matchesIgnoreCase("test"));
        reader.consume();
        assertFalse(reader.matchesIgnoreCase("Test"));
    }

    @Test
    public void testMatchesAny() {
        assertTrue(reader.matchesAny('T', 'e'));
        reader.consume();
        assertTrue(reader.matchesAny('T', 'e'));
    }

    @Test
    public void testMatchesLetter() {
        assertTrue(reader.matchesLetter());
        reader.consume();
        assertTrue(reader.matchesLetter());
    }

    @Test
    public void testMatchesDigit() {
        reader = new CharacterReader("123Test");
        assertTrue(reader.matchesDigit());
        reader.consume();
        assertTrue(reader.matchesDigit());
    }

    @Test
    public void testMatchConsume() {
        assertTrue(reader.matchConsume("Test"));
        assertFalse(reader.matchConsume("Test"));
    }

    @Test
    public void testMatchConsumeIgnoreCase() {
        assertTrue(reader.matchConsumeIgnoreCase("test"));
        assertFalse(reader.matchConsumeIgnoreCase("test"));
    }

    @Test
    public void testContainsIgnoreCase() {
        assertTrue(reader.containsIgnoreCase("string"));
        assertFalse(reader.containsIgnoreCase("nonexistent"));
    }

    @Test
    public void testToString() {
        assertEquals("Test String", reader.toString());
        reader.consume();
        assertEquals("est String", reader.toString());
    }
}