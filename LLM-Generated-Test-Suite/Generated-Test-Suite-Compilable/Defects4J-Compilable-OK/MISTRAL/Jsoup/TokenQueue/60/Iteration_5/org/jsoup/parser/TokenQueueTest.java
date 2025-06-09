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
    public void testConstructorWithNull() {
        new TokenQueue(null);
    }

    @Test
    public void testIsEmpty() {
        assertFalse(tokenQueue.isEmpty());
        tokenQueue.consume();
        tokenQueue.consume();
        tokenQueue.consume();
        tokenQueue.consume();
        tokenQueue.consume();
        tokenQueue.consume();
        tokenQueue.consume();
        tokenQueue.consume();
        tokenQueue.consume();
        tokenQueue.consume();
        tokenQueue.consume();
        tokenQueue.consume();
        assertTrue(tokenQueue.isEmpty());
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
        tokenQueue.addFirst("test");
        assertEquals('t', tokenQueue.peek());
    }

    @Test
    public void testMatches() {
        assertTrue(tokenQueue.matches("example"));
        assertFalse(tokenQueue.matches("test"));
    }

    @Test
    public void testMatchesCS() {
        assertTrue(tokenQueue.matchesCS("example"));
        assertFalse(tokenQueue.matchesCS("Example"));
    }

    @Test
    public void testMatchesAnyString() {
        assertTrue(tokenQueue.matchesAny("example", "test"));
        assertFalse(tokenQueue.matchesAny("test", "data"));
    }

    @Test
    public void testMatchesAnyChar() {
        assertTrue(tokenQueue.matchesAny('e', 'x'));
        assertFalse(tokenQueue.matchesAny('t', 'd'));
    }

    @Test
    public void testMatchesStartTag() {
        tokenQueue = new TokenQueue("<tag>");
        assertTrue(tokenQueue.matchesStartTag());
        tokenQueue = new TokenQueue("tag");
        assertFalse(tokenQueue.matchesStartTag());
    }

    @Test
    public void testMatchChomp() {
        assertTrue(tokenQueue.matchChomp("example"));
        assertEquals(" data", tokenQueue.toString());
        assertFalse(tokenQueue.matchChomp("test"));
    }

    @Test
    public void testMatchesWhitespace() {
        tokenQueue = new TokenQueue(" example data");
        assertTrue(tokenQueue.matchesWhitespace());
        tokenQueue = new TokenQueue("example data");
        assertFalse(tokenQueue.matchesWhitespace());
    }

    @Test
    public void testMatchesWord() {
        assertTrue(tokenQueue.matchesWord());
        tokenQueue = new TokenQueue(" 123");
        assertTrue(tokenQueue.matchesWord());
        tokenQueue = new TokenQueue(" ");
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
        assertEquals(" data", tokenQueue.toString());
    }

    @Test(expected = IllegalStateException.class)
    public void testConsumeStringException() {
        tokenQueue.consume("test");
    }

    @Test
    public void testConsumeTo() {
        assertEquals("example ", tokenQueue.consumeTo("data"));
        assertEquals("data", tokenQueue.toString());
    }

    @Test
    public void testConsumeToIgnoreCase() {
        assertEquals("example ", tokenQueue.consumeToIgnoreCase("DATA"));
        assertEquals("data", tokenQueue.toString());
    }

    @Test
    public void testConsumeToAny() {
        assertEquals("example ", tokenQueue.consumeToAny("data", "test"));
        assertEquals("data", tokenQueue.toString());
    }

    @Test
    public void testChompTo() {
        assertEquals("example ", tokenQueue.chompTo("data"));
        assertEquals("", tokenQueue.toString());
    }

    @Test
    public void testChompToIgnoreCase() {
        assertEquals("example ", tokenQueue.chompToIgnoreCase("DATA"));
        assertEquals("", tokenQueue.toString());
    }

    @Test
    public void testChompBalanced() {
        tokenQueue = new TokenQueue("(one (two) three) four");
        assertEquals("one (two) three", tokenQueue.chompBalanced('(', ')'));
        assertEquals(" four", tokenQueue.toString());
    }

    @Test
    public void testUnescape() {
        assertEquals("example", TokenQueue.unescape("example"));
        assertEquals("example", TokenQueue.unescape("\\example"));
    }

    @Test
    public void testConsumeWhitespace() {
        tokenQueue = new TokenQueue(" example data");
        assertTrue(tokenQueue.consumeWhitespace());
        assertEquals("example data", tokenQueue.toString());
    }

    @Test
    public void testConsumeWord() {
        assertEquals("example", tokenQueue.consumeWord());
        assertEquals(" data", tokenQueue.toString());
    }

    @Test
    public void testConsumeTagName() {
        tokenQueue = new TokenQueue("tag-name");
        assertEquals("tag-name", tokenQueue.consumeTagName());
    }

    @Test
    public void testConsumeElementSelector() {
        tokenQueue = new TokenQueue("tag|name");
        assertEquals("tag|name", tokenQueue.consumeElementSelector());
    }

    @Test
    public void testConsumeCssIdentifier() {
        tokenQueue = new TokenQueue("css-identifier");
        assertEquals("css-identifier", tokenQueue.consumeCssIdentifier());
    }

    @Test
    public void testConsumeAttributeKey() {
        tokenQueue = new TokenQueue("attr-key:");
        assertEquals("attr-key:", tokenQueue.consumeAttributeKey());
    }

    @Test
    public void testRemainder() {
        assertEquals("example data", tokenQueue.remainder());
        assertTrue(tokenQueue.isEmpty());
    }

    @Test
    public void testToString() {
        assertEquals("example data", tokenQueue.toString());
    }
}