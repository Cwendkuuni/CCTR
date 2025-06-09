package org.jsoup.parser;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TokenQueueTest {

    private TokenQueue tokenQueue;

    @Before
    public void setUp() {
        tokenQueue = new TokenQueue("example data");
    }

    @Test
    public void testConstructor() {
        assertNotNull(tokenQueue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNull() {
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
        tokenQueue = new TokenQueue("");
        assertEquals(0, tokenQueue.peek());
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
    public void testMatchesAnyString() {
        assertTrue(tokenQueue.matchesAny("example", "data"));
        assertFalse(tokenQueue.matchesAny("data", "test"));
    }

    @Test
    public void testMatchesAnyChar() {
        assertTrue(tokenQueue.matchesAny('e', 'x'));
        assertFalse(tokenQueue.matchesAny('a', 'b'));
    }

    @Test
    public void testMatchesStartTag() {
        tokenQueue = new TokenQueue("<tag");
        assertTrue(tokenQueue.matchesStartTag());
        tokenQueue = new TokenQueue("tag");
        assertFalse(tokenQueue.matchesStartTag());
    }

    @Test
    public void testMatchChomp() {
        assertTrue(tokenQueue.matchChomp("example"));
        assertEquals(' ', tokenQueue.peek());
        assertFalse(tokenQueue.matchChomp("data"));
    }

    @Test
    public void testMatchesWhitespace() {
        tokenQueue = new TokenQueue(" example");
        assertTrue(tokenQueue.matchesWhitespace());
        tokenQueue = new TokenQueue("example");
        assertFalse(tokenQueue.matchesWhitespace());
    }

    @Test
    public void testMatchesWord() {
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
        assertEquals('x', tokenQueue.peek());
    }

    @Test(expected = IllegalStateException.class)
    public void testConsumeString() {
        tokenQueue.consume("example");
        assertEquals(' ', tokenQueue.peek());
        tokenQueue.consume("data");
    }

    @Test
    public void testConsumeTo() {
        assertEquals("example", tokenQueue.consumeTo("data"));
        assertEquals('d', tokenQueue.peek());
    }

    @Test
    public void testConsumeToIgnoreCase() {
        assertEquals("example", tokenQueue.consumeToIgnoreCase("DATA"));
        assertEquals('d', tokenQueue.peek());
    }

    @Test
    public void testConsumeToAny() {
        assertEquals("example", tokenQueue.consumeToAny("data", "test"));
        assertEquals('d', tokenQueue.peek());
    }

    @Test
    public void testChompTo() {
        assertEquals("example", tokenQueue.chompTo("data"));
        assertEquals(' ', tokenQueue.peek());
    }

    @Test
    public void testChompToIgnoreCase() {
        assertEquals("example", tokenQueue.chompToIgnoreCase("DATA"));
        assertEquals(' ', tokenQueue.peek());
    }

    @Test
    public void testChompBalanced() {
        tokenQueue = new TokenQueue("(one (two) three) four");
        assertEquals("one (two) three", tokenQueue.chompBalanced('(', ')'));
        assertEquals('f', tokenQueue.peek());
    }

    @Test
    public void testUnescape() {
        assertEquals("example", TokenQueue.unescape("example"));
        assertEquals("example", TokenQueue.unescape("ex\\ample"));
    }

    @Test
    public void testConsumeWhitespace() {
        tokenQueue = new TokenQueue(" example data");
        assertTrue(tokenQueue.consumeWhitespace());
        assertEquals('e', tokenQueue.peek());
    }

    @Test
    public void testConsumeWord() {
        assertEquals("example", tokenQueue.consumeWord());
        assertEquals(' ', tokenQueue.peek());
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
        tokenQueue = new TokenQueue("id-name");
        assertEquals("id-name", tokenQueue.consumeCssIdentifier());
    }

    @Test
    public void testConsumeAttributeKey() {
        tokenQueue = new TokenQueue("attr-key");
        assertEquals("attr-key", tokenQueue.consumeAttributeKey());
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