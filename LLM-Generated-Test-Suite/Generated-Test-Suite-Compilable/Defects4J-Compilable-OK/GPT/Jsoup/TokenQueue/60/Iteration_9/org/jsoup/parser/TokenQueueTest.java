package org.jsoup.parser;

import org.jsoup.parser.TokenQueue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TokenQueueTest {

    private TokenQueue tokenQueue;

    @Before
    public void setUp() {
        tokenQueue = new TokenQueue("Hello World");
    }

    @Test
    public void testIsEmpty() {
        assertFalse(tokenQueue.isEmpty());
        tokenQueue.consume("Hello World");
        assertTrue(tokenQueue.isEmpty());
    }

    @Test
    public void testPeek() {
        assertEquals('H', tokenQueue.peek());
        tokenQueue.consume();
        assertEquals('e', tokenQueue.peek());
    }

    @Test
    public void testAddFirstCharacter() {
        tokenQueue.addFirst('X');
        assertEquals('X', tokenQueue.peek());
    }

    @Test
    public void testAddFirstString() {
        tokenQueue.addFirst("Test");
        assertTrue(tokenQueue.matches("Test"));
    }

    @Test
    public void testMatches() {
        assertTrue(tokenQueue.matches("Hello"));
        assertFalse(tokenQueue.matches("World"));
    }

    @Test
    public void testMatchesCS() {
        assertTrue(tokenQueue.matchesCS("Hello"));
        assertFalse(tokenQueue.matchesCS("hello"));
    }

    @Test
    public void testMatchesAnyString() {
        assertTrue(tokenQueue.matchesAny("Hello", "World"));
        assertFalse(tokenQueue.matchesAny("Foo", "Bar"));
    }

    @Test
    public void testMatchesAnyChar() {
        assertTrue(tokenQueue.matchesAny('H', 'W'));
        assertFalse(tokenQueue.matchesAny('X', 'Y'));
    }

    @Test
    public void testMatchesStartTag() {
        TokenQueue tq = new TokenQueue("<html>");
        assertTrue(tq.matchesStartTag());
        tq.consume("<h");
        assertFalse(tq.matchesStartTag());
    }

    @Test
    public void testMatchChomp() {
        assertTrue(tokenQueue.matchChomp("Hello"));
        assertFalse(tokenQueue.matchChomp("World"));
    }

    @Test
    public void testMatchesWhitespace() {
        tokenQueue.consume("Hello");
        assertTrue(tokenQueue.matchesWhitespace());
    }

    @Test
    public void testMatchesWord() {
        assertTrue(tokenQueue.matchesWord());
        tokenQueue.consume("Hello");
        assertFalse(tokenQueue.matchesWord());
    }

    @Test
    public void testAdvance() {
        tokenQueue.advance();
        assertEquals('e', tokenQueue.peek());
    }

    @Test
    public void testConsumeChar() {
        assertEquals('H', tokenQueue.consume());
        assertEquals('e', tokenQueue.peek());
    }

    @Test(expected = IllegalStateException.class)
    public void testConsumeStringThrowsException() {
        tokenQueue.consume("Foo");
    }

    @Test
    public void testConsumeString() {
        tokenQueue.consume("Hello");
        assertEquals(' ', tokenQueue.peek());
    }

    @Test
    public void testConsumeTo() {
        assertEquals("Hello", tokenQueue.consumeTo(" "));
    }

    @Test
    public void testConsumeToIgnoreCase() {
        assertEquals("Hello", tokenQueue.consumeToIgnoreCase(" "));
    }

    @Test
    public void testConsumeToAny() {
        assertEquals("Hello", tokenQueue.consumeToAny(" ", "W"));
    }

    @Test
    public void testChompTo() {
        assertEquals("Hello", tokenQueue.chompTo(" "));
        assertEquals('W', tokenQueue.peek());
    }

    @Test
    public void testChompToIgnoreCase() {
        assertEquals("Hello", tokenQueue.chompToIgnoreCase(" "));
        assertEquals('W', tokenQueue.peek());
    }

    @Test
    public void testChompBalanced() {
        TokenQueue tq = new TokenQueue("(one (two) three) four");
        assertEquals("one (two) three", tq.chompBalanced('(', ')'));
    }

    @Test
    public void testUnescape() {
        assertEquals("Hello", TokenQueue.unescape("Hello"));
        assertEquals("Hello\\World", TokenQueue.unescape("Hello\\\\World"));
    }

    @Test
    public void testConsumeWhitespace() {
        tokenQueue.consume("Hello");
        assertTrue(tokenQueue.consumeWhitespace());
        assertEquals('W', tokenQueue.peek());
    }

    @Test
    public void testConsumeWord() {
        assertEquals("Hello", tokenQueue.consumeWord());
        assertEquals(' ', tokenQueue.peek());
    }

    @Test
    public void testConsumeTagName() {
        TokenQueue tq = new TokenQueue("tag-name");
        assertEquals("tag-name", tq.consumeTagName());
    }

    @Test
    public void testConsumeElementSelector() {
        TokenQueue tq = new TokenQueue("tag|name");
        assertEquals("tag|name", tq.consumeElementSelector());
    }

    @Test
    public void testConsumeCssIdentifier() {
        TokenQueue tq = new TokenQueue("identifier-123");
        assertEquals("identifier-123", tq.consumeCssIdentifier());
    }

    @Test
    public void testConsumeAttributeKey() {
        TokenQueue tq = new TokenQueue("attr-key");
        assertEquals("attr-key", tq.consumeAttributeKey());
    }

    @Test
    public void testRemainder() {
        assertEquals("Hello World", tokenQueue.remainder());
        assertTrue(tokenQueue.isEmpty());
    }

    @Test
    public void testToString() {
        assertEquals("Hello World", tokenQueue.toString());
        tokenQueue.consume("Hello");
        assertEquals(" World", tokenQueue.toString());
    }
}