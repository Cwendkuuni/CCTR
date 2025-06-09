package org.jsoup.parser;

import org.junit.Before;
import org.junit.Test;
import org.jsoup.UncheckedIOException;
import org.jsoup.helper.Validate;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.*;

public class CharacterReaderTest {

    private CharacterReader characterReader;

    @Before
    public void setUp() {
        String input = "This is a test string.";
        characterReader = new CharacterReader(new StringReader(input), input.length());
    }

    @Test
    public void testConstructorWithReaderAndSize() {
        String input = "Test";
        Reader reader = new StringReader(input);
        CharacterReader cr = new CharacterReader(reader, input.length());
        assertNotNull(cr);
    }

    @Test
    public void testConstructorWithReader() {
        String input = "Test";
        Reader reader = new StringReader(input);
        CharacterReader cr = new CharacterReader(reader);
        assertNotNull(cr);
    }

    @Test
    public void testConstructorWithString() {
        String input = "Test";
        CharacterReader cr = new CharacterReader(input);
        assertNotNull(cr);
    }

    @Test
    public void testPos() {
        assertEquals(0, characterReader.pos());
        characterReader.advance();
        assertEquals(1, characterReader.pos());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(characterReader.isEmpty());
        characterReader.consumeToEnd();
        assertTrue(characterReader.isEmpty());
    }

    @Test
    public void testCurrent() {
        assertEquals('T', characterReader.current());
        characterReader.advance();
        assertEquals('h', characterReader.current());
    }

    @Test
    public void testConsume() {
        assertEquals('T', characterReader.consume());
        assertEquals('h', characterReader.consume());
    }

    @Test
    public void testUnconsume() {
        characterReader.consume();
        characterReader.unconsume();
        assertEquals('T', characterReader.current());
    }

    @Test
    public void testAdvance() {
        characterReader.advance();
        assertEquals('h', characterReader.current());
    }

    @Test
    public void testMarkAndRewindToMark() {
        characterReader.mark();
        characterReader.advance();
        characterReader.rewindToMark();
        assertEquals('T', characterReader.current());
    }

    @Test
    public void testNextIndexOfChar() {
        assertEquals(2, characterReader.nextIndexOf('i'));
    }

    @Test
    public void testNextIndexOfCharSequence() {
        assertEquals(10, characterReader.nextIndexOf("test"));
    }

    @Test
    public void testConsumeToChar() {
        assertEquals("This is a ", characterReader.consumeTo('t'));
    }

    @Test
    public void testConsumeToString() {
        assertEquals("This is a ", characterReader.consumeTo("test"));
    }

    @Test
    public void testConsumeToAny() {
        assertEquals("This is a ", characterReader.consumeToAny('t', 's'));
    }

    @Test
    public void testConsumeToAnySorted() {
        assertEquals("This is a ", characterReader.consumeToAnySorted(new char[]{'s', 't'}));
    }

    @Test
    public void testConsumeData() {
        characterReader = new CharacterReader("This is a test string & < nullChar");
        assertEquals("This is a test string ", characterReader.consumeData());
    }

    @Test
    public void testConsumeTagName() {
        characterReader = new CharacterReader("This is a test string <tag>");
        assertEquals("This is a test string ", characterReader.consumeTagName());
    }

    @Test
    public void testConsumeToEnd() {
        assertEquals("This is a test string.", characterReader.consumeToEnd());
    }

    @Test
    public void testConsumeLetterSequence() {
        characterReader = new CharacterReader("This123");
        assertEquals("This", characterReader.consumeLetterSequence());
    }

    @Test
    public void testConsumeLetterThenDigitSequence() {
        characterReader = new CharacterReader("This123");
        assertEquals("This123", characterReader.consumeLetterThenDigitSequence());
    }

    @Test
    public void testConsumeHexSequence() {
        characterReader = new CharacterReader("123ABCabc");
        assertEquals("123ABCabc", characterReader.consumeHexSequence());
    }

    @Test
    public void testConsumeDigitSequence() {
        characterReader = new CharacterReader("123ABC");
        assertEquals("123", characterReader.consumeDigitSequence());
    }

    @Test
    public void testMatchesChar() {
        assertTrue(characterReader.matches('T'));
        assertFalse(characterReader.matches('X'));
    }

    @Test
    public void testMatchesString() {
        assertTrue(characterReader.matches("This"));
        assertFalse(characterReader.matches("That"));
    }

    @Test
    public void testMatchesIgnoreCase() {
        assertTrue(characterReader.matchesIgnoreCase("this"));
        assertFalse(characterReader.matchesIgnoreCase("that"));
    }

    @Test
    public void testMatchesAny() {
        assertTrue(characterReader.matchesAny('T', 'X'));
        assertFalse(characterReader.matchesAny('X', 'Y'));
    }

    @Test
    public void testMatchesAnySorted() {
        assertTrue(characterReader.matchesAnySorted(new char[]{'T', 'X'}));
        assertFalse(characterReader.matchesAnySorted(new char[]{'X', 'Y'}));
    }

    @Test
    public void testMatchesLetter() {
        assertTrue(characterReader.matchesLetter());
        characterReader = new CharacterReader("123");
        assertFalse(characterReader.matchesLetter());
    }

    @Test
    public void testMatchesDigit() {
        characterReader = new CharacterReader("123");
        assertTrue(characterReader.matchesDigit());
        characterReader = new CharacterReader("abc");
        assertFalse(characterReader.matchesDigit());
    }

    @Test
    public void testMatchConsume() {
        assertTrue(characterReader.matchConsume("This"));
        assertFalse(characterReader.matchConsume("That"));
    }

    @Test
    public void testMatchConsumeIgnoreCase() {
        assertTrue(characterReader.matchConsumeIgnoreCase("this"));
        assertFalse(characterReader.matchConsumeIgnoreCase("that"));
    }

    @Test
    public void testContainsIgnoreCase() {
        assertTrue(characterReader.containsIgnoreCase("test"));
        assertFalse(characterReader.containsIgnoreCase("that"));
    }

    @Test
    public void testToString() {
        assertEquals("This is a test string.", characterReader.toString());
    }

    @Test
    public void testCacheString() {
        String input = "Test";
        CharacterReader cr = new CharacterReader(input);
        assertEquals("Test", cr.consumeToEnd());
    }

    @Test
    public void testRangeEquals() {
        assertTrue(CharacterReader.rangeEquals(new char[]{'T', 'e', 's', 't'}, 0, 4, "Test"));
        assertFalse(CharacterReader.rangeEquals(new char[]{'T', 'e', 's', 't'}, 0, 4, "That"));
    }
}