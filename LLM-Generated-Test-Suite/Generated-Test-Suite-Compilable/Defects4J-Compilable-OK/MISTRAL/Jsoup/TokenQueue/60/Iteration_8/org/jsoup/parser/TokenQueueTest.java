package org.jsoup.parser;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TokenQueueTest {

    private TokenQueue tokenQueue;

    @Before
    public void setUp() {
        tokenQueue = new TokenQueue("example data");
    }

    @Test
    public void testConstructor() {
        assertNotNull(tokenQueue);
        assertEquals("example data", tokenQueue.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNull() {
        new TokenQueue(null);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(new TokenQueue("").isEmpty());
        assertFalse(tokenQueue.isEmpty());
    }

    @Test
    public void testPeek() {
        assertEquals('e', tokenQueue.peek());
        tokenQueue.consume();
        assertEquals('x', tokenQueue.peek());
    }

    @Test
    public void testAddFirstCharacter() {
        tokenQueue.addFirst('a');
        assertEquals('a', tokenQueue.peek());
    }

    @Test
    public void testAddFirstString() {
        tokenQueue.addFirst("abc");
        assertEquals('a', tokenQueue.peek());
        tokenQueue.consume();
        assertEquals('b', tokenQueue.peek());
    }

    @Test
    public void testMatches() {
        assertTrue(tokenQueue.matches("example"));
        assertFalse(tokenQueue.matches("Example"));
    }

    @Test
    public void testMatchesCS() {
        assertTrue(tokenQueue.matchesCS("example"));
        assertFalse(tokenQueue.matchesCS("Example"));
    }

    @Test
    public void testMatchesAnyStrings() {
        assertTrue(tokenQueue.matchesAny("example", "data"));
        assertFalse(tokenQueue.matchesAny("data", "test"));
    }

    @Test
    public void testMatchesAnyChars() {
        assertTrue(tokenQueue.matchesAny('e', 'x'));
        assertFalse(tokenQueue.matchesAny('a', 'b'));
    }

    @Test
    public void testMatchesStartTag() {
        TokenQueue tq = new TokenQueue("<tag");
        assertTrue(tq.matchesStartTag());
        tq = new TokenQueue("tag");
        assertFalse(tq.matchesStartTag());
    }

    @Test
    public void testMatchChomp() {
        assertTrue(tokenQueue.matchChomp("example"));
        assertEquals(' ', tokenQueue.peek());
        assertFalse(tokenQueue.matchChomp("data"));
    }

    @Test
    public void testMatchesWhitespace() {
        TokenQueue tq = new TokenQueue(" example");
        assertTrue(tq.matchesWhitespace());
        tq = new TokenQueue("example");
        assertFalse(tq.matchesWhitespace());
    }

    @Test
    public void testMatchesWord() {
        assertTrue(tokenQueue.matchesWord());
        tokenQueue.consume();
        assertTrue(tokenQueue.matchesWord());
        tokenQueue.consume();
        assertFalse(tokenQueue.matchesWord());
    }

    @Test
    public void testAdvance() {
        tokenQueue.advance();
        assertEquals('x', tokenQueue.peek());
    }

    @Test
    public void testConsume() {
        assertEquals('e', tokenQueue.consume());
        assertEquals('x', tokenQueue.consume());
    }

    @Test
    public void testConsumeString() {
        tokenQueue.consume("example");
        assertEquals(' ', tokenQueue.peek());
    }

    @Test(expected = IllegalStateException.class)
    public void testConsumeStringException() {
        tokenQueue.consume("data");
    }

    @Test
    public void testConsumeTo() {
        assertEquals("example", tokenQueue.consumeTo("data"));
        assertEquals("data", tokenQueue.toString());
    }

    @Test
    public void testConsumeToIgnoreCase() {
        assertEquals("example", tokenQueue.consumeToIgnoreCase("DATA"));
        assertEquals("data", tokenQueue.toString());
    }

    @Test
    public void testConsumeToAny() {
        assertEquals("example", tokenQueue.consumeToAny("data", "test"));
        assertEquals("data", tokenQueue.toString());
    }

    @Test
    public void testChompTo() {
        assertEquals("example", tokenQueue.chompTo("data"));
        assertEquals("", tokenQueue.toString());
    }

    @Test
    public void testChompToIgnoreCase() {
        assertEquals("example", tokenQueue.chompToIgnoreCase("DATA"));
        assertEquals("", tokenQueue.toString());
    }

    @Test
    public void testChompBalanced() {
        TokenQueue tq = new TokenQueue("(one (two) three) four");
        assertEquals("one (two) three", tq.chompBalanced('(', ')'));
        assertEquals(" four", tq.toString());
    }

    @Test
    public void testUnescape() {
        assertEquals("example", TokenQueue.unescape("example"));
        assertEquals("example", TokenQueue.unescape("ex\\ample"));
    }

    @Test
    public void testConsumeWhitespace() {
        TokenQueue tq = new TokenQueue(" example");
        assertTrue(tq.consumeWhitespace());
        assertEquals("example", tq.toString());
    }

    @Test
    public void testConsumeWord() {
        assertEquals("example", tokenQueue.consumeWord());
        assertEquals(" data", tokenQueue.toString());
    }

    @Test
    public void testConsumeTagName() {
        TokenQueue tq = new TokenQueue("tag-name");
        assertEquals("tag-name", tq.consumeTagName());
        assertEquals("", tq.toString());
    }

    @Test
    public void testConsumeElementSelector() {
        TokenQueue tq = new TokenQueue("tag|name");
        assertEquals("tag|name", tq.consumeElementSelector());
        assertEquals("", tq.toString());
    }

    @Test
    public void testConsumeCssIdentifier() {
        TokenQueue tq = new TokenQueue("css-identifier");
        assertEquals("css-identifier", tq.consumeCssIdentifier());
        assertEquals("", tq.toString());
    }

    @Test
    public void testConsumeAttributeKey() {
        TokenQueue tq = new TokenQueue("attr-key");
        assertEquals("attr-key", tq.consumeAttributeKey());
        assertEquals("", tq.toString());
    }

    @Test
    public void testRemainder() {
        assertEquals("example data", tokenQueue.remainder());
        assertEquals("", tokenQueue.toString());
    }

    @Test
    public void testToString() {
        assertEquals("example data", tokenQueue.toString());
    }
}