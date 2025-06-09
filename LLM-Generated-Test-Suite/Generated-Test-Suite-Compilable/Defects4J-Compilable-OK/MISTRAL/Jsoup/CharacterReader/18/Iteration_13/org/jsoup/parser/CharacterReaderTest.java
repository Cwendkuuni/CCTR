package org.jsoup.parser;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CharacterReaderTest {

    private CharacterReader reader;

    @Before
    public void setUp() {
        reader = new CharacterReader("abc123ABC");
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
        assertEquals('a', reader.current());
        reader.consume();
        assertEquals('b', reader.current());
    }

    @Test
    public void testConsume() {
        assertEquals('a', reader.consume());
        assertEquals('b', reader.consume());
    }

    @Test
    public void testUnconsume() {
        reader.consume();
        reader.unconsume();
        assertEquals(0, reader.pos());
    }

    @Test
    public void testAdvance() {
        reader.advance();
        assertEquals(1, reader.pos());
    }

    @Test
    public void testMark() {
        reader.mark();
        reader.consume();
        reader.rewindToMark();
        assertEquals(0, reader.pos());
    }

    @Test
    public void testRewindToMark() {
        reader.mark();
        reader.consume();
        reader.rewindToMark();
        assertEquals(0, reader.pos());
    }

    @Test
    public void testConsumeAsString() {
        assertEquals("a", reader.consumeAsString());
        assertEquals("b", reader.consumeAsString());
    }

    @Test
    public void testConsumeToChar() {
        reader = new CharacterReader("abc123ABC");
        assertEquals("abc", reader.consumeTo('1'));
    }

    @Test
    public void testConsumeToString() {
        reader = new CharacterReader("abc123ABC");
        assertEquals("abc", reader.consumeTo("123"));
    }

    @Test
    public void testConsumeToAny() {
        reader = new CharacterReader("abc123ABC");
        assertEquals("abc", reader.consumeToAny('1', 'A'));
    }

    @Test
    public void testConsumeToEnd() {
        reader = new CharacterReader("abc123ABC");
        assertEquals("abc123AB", reader.consumeToEnd());
    }

    @Test
    public void testConsumeLetterSequence() {
        assertEquals("abc", reader.consumeLetterSequence());
    }

    @Test
    public void testConsumeHexSequence() {
        reader = new CharacterReader("abc123ABC");
        reader.consumeLetterSequence();
        assertEquals("123", reader.consumeHexSequence());
    }

    @Test
    public void testConsumeDigitSequence() {
        reader = new CharacterReader("abc123ABC");
        reader.consumeLetterSequence();
        assertEquals("123", reader.consumeDigitSequence());
    }

    @Test
    public void testMatchesChar() {
        assertTrue(reader.matches('a'));
        reader.consume();
        assertFalse(reader.matches('a'));
    }

    @Test
    public void testMatchesString() {
        assertTrue(reader.matches("abc"));
        reader.consume();
        assertFalse(reader.matches("abc"));
    }

    @Test
    public void testMatchesIgnoreCase() {
        assertTrue(reader.matchesIgnoreCase("ABC"));
        reader.consume();
        assertFalse(reader.matchesIgnoreCase("ABC"));
    }

    @Test
    public void testMatchesAny() {
        assertTrue(reader.matchesAny('a', 'b'));
        reader.consume();
        assertTrue(reader.matchesAny('a', 'b'));
        reader.consume();
        assertFalse(reader.matchesAny('a', 'b'));
    }

    @Test
    public void testMatchesLetter() {
        assertTrue(reader.matchesLetter());
        reader.consumeLetterSequence();
        assertFalse(reader.matchesLetter());
    }

    @Test
    public void testMatchesDigit() {
        reader = new CharacterReader("123abc");
        assertTrue(reader.matchesDigit());
        reader.consumeDigitSequence();
        assertFalse(reader.matchesDigit());
    }

    @Test
    public void testMatchConsume() {
        assertTrue(reader.matchConsume("abc"));
        assertFalse(reader.matchConsume("abc"));
    }

    @Test
    public void testMatchConsumeIgnoreCase() {
        assertTrue(reader.matchConsumeIgnoreCase("ABC"));
        assertFalse(reader.matchConsumeIgnoreCase("ABC"));
    }

    @Test
    public void testContainsIgnoreCase() {
        assertTrue(reader.containsIgnoreCase("ABC"));
        reader.consumeLetterSequence();
        assertFalse(reader.containsIgnoreCase("ABC"));
    }

    @Test
    public void testToString() {
        assertEquals("abc123ABC", reader.toString());
        reader.consume();
        assertEquals("bc123ABC", reader.toString());
    }
}