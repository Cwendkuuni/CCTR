package org.jsoup.parser;

import org.jsoup.parser.TokenQueue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TokenQueueTest {

    private TokenQueue tokenQueue;

    @Before
    public void setUp() {
        tokenQueue = new TokenQueue("testQueue");
    }

    @Test
    public void testIsEmpty() {
        assertFalse(tokenQueue.isEmpty());
        tokenQueue.consume("testQueue");
        assertTrue(tokenQueue.isEmpty());
    }

    @Test
    public void testPeek() {
        assertEquals('t', tokenQueue.peek());
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
        tokenQueue.addFirst("XYZ");
        assertEquals('X', tokenQueue.peek());
    }

    @Test
    public void testMatches() {
        assertTrue(tokenQueue.matches("test"));
        assertFalse(tokenQueue.matches("queue"));
    }

    @Test
    public void testMatchesCS() {
        assertTrue(tokenQueue.matchesCS("test"));
        assertFalse(tokenQueue.matchesCS("Test"));
    }

    @Test
    public void testMatchesAnyString() {
        assertTrue(tokenQueue.matchesAny("test", "queue"));
        assertFalse(tokenQueue.matchesAny("not", "present"));
    }

    @Test
    public void testMatchesAnyChar() {
        assertTrue(tokenQueue.matchesAny('t', 'q'));
        assertFalse(tokenQueue.matchesAny('x', 'y'));
    }

    @Test
    public void testMatchesStartTag() {
        TokenQueue tq = new TokenQueue("<tag>");
        assertTrue(tq.matchesStartTag());
        tq.consume();
        assertFalse(tq.matchesStartTag());
    }

    @Test
    public void testMatchChomp() {
        assertTrue(tokenQueue.matchChomp("test"));
        assertFalse(tokenQueue.matchChomp("queue"));
    }

    @Test
    public void testMatchesWhitespace() {
        TokenQueue tq = new TokenQueue(" test");
        assertFalse(tq.matchesWhitespace());
        tq.consume();
        assertTrue(tq.matchesWhitespace());
    }

    @Test
    public void testMatchesWord() {
        assertTrue(tokenQueue.matchesWord());
        tokenQueue.consumeTo("Queue");
        assertFalse(tokenQueue.matchesWord());
    }

    @Test
    public void testAdvance() {
        tokenQueue.advance();
        assertEquals('e', tokenQueue.peek());
    }

    @Test
    public void testConsumeChar() {
        assertEquals('t', tokenQueue.consume());
        assertEquals('e', tokenQueue.peek());
    }

    @Test(expected = IllegalStateException.class)
    public void testConsumeString() {
        tokenQueue.consume("test");
        assertEquals('Q', tokenQueue.peek());
        tokenQueue.consume("notPresent");
    }

    @Test
    public void testConsumeTo() {
        assertEquals("test", tokenQueue.consumeTo("Queue"));
        assertEquals('Q', tokenQueue.peek());
    }

    @Test
    public void testConsumeToIgnoreCase() {
        assertEquals("test", tokenQueue.consumeToIgnoreCase("QUEUE"));
        assertEquals('Q', tokenQueue.peek());
    }

    @Test
    public void testConsumeToAny() {
        assertEquals("test", tokenQueue.consumeToAny("Queue", "NotPresent"));
        assertEquals('Q', tokenQueue.peek());
    }

    @Test
    public void testChompTo() {
        assertEquals("test", tokenQueue.chompTo("Queue"));
        assertEquals('Q', tokenQueue.peek());
    }

    @Test
    public void testChompToIgnoreCase() {
        assertEquals("test", tokenQueue.chompToIgnoreCase("QUEUE"));
        assertEquals('Q', tokenQueue.peek());
    }

    @Test
    public void testChompBalanced() {
        TokenQueue tq = new TokenQueue("(one (two) three) four");
        assertEquals("one (two) three", tq.chompBalanced('(', ')'));
        assertEquals(' ', tq.peek());
    }

    @Test
    public void testUnescape() {
        assertEquals("test", TokenQueue.unescape("test"));
        assertEquals("te\\st", TokenQueue.unescape("te\\\\st"));
    }

    @Test
    public void testConsumeWhitespace() {
        TokenQueue tq = new TokenQueue("   test");
        assertTrue(tq.consumeWhitespace());
        assertEquals('t', tq.peek());
    }

    @Test
    public void testConsumeWord() {
        assertEquals("test", tokenQueue.consumeWord());
        assertEquals('Q', tokenQueue.peek());
    }

    @Test
    public void testConsumeTagName() {
        TokenQueue tq = new TokenQueue("tag-name:123");
        assertEquals("tag-name:123", tq.consumeTagName());
    }

    @Test
    public void testConsumeElementSelector() {
        TokenQueue tq = new TokenQueue("element|name-123");
        assertEquals("element|name-123", tq.consumeElementSelector());
    }

    @Test
    public void testConsumeCssIdentifier() {
        TokenQueue tq = new TokenQueue("css-identifier_123");
        assertEquals("css-identifier_123", tq.consumeCssIdentifier());
    }

    @Test
    public void testConsumeAttributeKey() {
        TokenQueue tq = new TokenQueue("attr-key:123");
        assertEquals("attr-key:123", tq.consumeAttributeKey());
    }

    @Test
    public void testRemainder() {
        assertEquals("testQueue", tokenQueue.remainder());
        assertTrue(tokenQueue.isEmpty());
    }

    @Test
    public void testToString() {
        assertEquals("testQueue", tokenQueue.toString());
        tokenQueue.consume("test");
        assertEquals("Queue", tokenQueue.toString());
    }
}