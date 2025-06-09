package org.jsoup.parser;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TokenQueueTest {

    private TokenQueue tokenQueue;

    @Before
    public void setUp() {
        tokenQueue = new TokenQueue("test data");
    }

    @Test
    public void testConstructor() {
        assertNotNull(tokenQueue);
        assertEquals("test data", tokenQueue.toString());
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
        assertEquals('t', tokenQueue.peek());
        tokenQueue.consume();
        assertEquals('e', tokenQueue.peek());
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
        assertTrue(tokenQueue.matches("test"));
        assertFalse(tokenQueue.matches("data"));
    }

    @Test
    public void testMatchesCS() {
        assertTrue(tokenQueue.matchesCS("test"));
        assertFalse(tokenQueue.matchesCS("Test"));
    }

    @Test
    public void testMatchesAnyString() {
        assertTrue(tokenQueue.matchesAny("test", "data"));
        assertFalse(tokenQueue.matchesAny("foo", "bar"));
    }

    @Test
    public void testMatchesAnyChar() {
        assertTrue(tokenQueue.matchesAny('t', 'd'));
        assertFalse(tokenQueue.matchesAny('x', 'y'));
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
        assertTrue(tokenQueue.matchChomp("test"));
        assertEquals(" data", tokenQueue.toString());
        assertFalse(tokenQueue.matchChomp("foo"));
    }

    @Test
    public void testMatchesWhitespace() {
        TokenQueue tq = new TokenQueue(" test");
        assertTrue(tq.matchesWhitespace());
        tq = new TokenQueue("test");
        assertFalse(tq.matchesWhitespace());
    }

    @Test
    public void testMatchesWord() {
        assertTrue(tokenQueue.matchesWord());
        TokenQueue tq = new TokenQueue(" 123");
        assertTrue(tq.matchesWord());
        tq = new TokenQueue(" ");
        assertFalse(tq.matchesWord());
    }

    @Test
    public void testAdvance() {
        tokenQueue.advance();
        assertEquals('e', tokenQueue.peek());
    }

    @Test
    public void testConsume() {
        assertEquals('t', tokenQueue.consume());
        assertEquals('e', tokenQueue.peek());
    }

    @Test
    public void testConsumeString() {
        tokenQueue.consume("test");
        assertEquals(" data", tokenQueue.toString());
    }

    @Test(expected = IllegalStateException.class)
    public void testConsumeStringMismatch() {
        tokenQueue.consume("foo");
    }

    @Test
    public void testConsumeTo() {
        TokenQueue tq = new TokenQueue("test data");
        assertEquals("test ", tq.consumeTo("data"));
        assertEquals("data", tq.toString());
    }

    @Test
    public void testConsumeToIgnoreCase() {
        TokenQueue tq = new TokenQueue("test DATA");
        assertEquals("test ", tq.consumeToIgnoreCase("data"));
        assertEquals("DATA", tq.toString());
    }

    @Test
    public void testConsumeToAny() {
        TokenQueue tq = new TokenQueue("test data");
        assertEquals("test ", tq.consumeToAny("data", "foo"));
        assertEquals("data", tq.toString());
    }

    @Test
    public void testChompTo() {
        TokenQueue tq = new TokenQueue("test data");
        assertEquals("test ", tq.chompTo("data"));
        assertEquals("", tq.toString());
    }

    @Test
    public void testChompToIgnoreCase() {
        TokenQueue tq = new TokenQueue("test DATA");
        assertEquals("test ", tq.chompToIgnoreCase("data"));
        assertEquals("", tq.toString());
    }

    @Test
    public void testChompBalanced() {
        TokenQueue tq = new TokenQueue("(one (two) three) four");
        assertEquals("one (two) three", tq.chompBalanced('(', ')'));
        assertEquals(" four", tq.toString());
    }

    @Test
    public void testUnescape() {
        assertEquals("test", TokenQueue.unescape("test"));
        assertEquals("test", TokenQueue.unescape("\\test"));
    }

    @Test
    public void testConsumeWhitespace() {
        TokenQueue tq = new TokenQueue("  test");
        assertTrue(tq.consumeWhitespace());
        assertEquals("test", tq.toString());
    }

    @Test
    public void testConsumeWord() {
        assertEquals("test", tokenQueue.consumeWord());
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
        TokenQueue tq = new TokenQueue("id-name");
        assertEquals("id-name", tq.consumeCssIdentifier());
        assertEquals("", tq.toString());
    }

    @Test
    public void testConsumeAttributeKey() {
        TokenQueue tq = new TokenQueue("attr:key");
        assertEquals("attr:key", tq.consumeAttributeKey());
        assertEquals("", tq.toString());
    }

    @Test
    public void testRemainder() {
        assertEquals("test data", tokenQueue.remainder());
        assertEquals("", tokenQueue.toString());
    }

    @Test
    public void testToString() {
        assertEquals("test data", tokenQueue.toString());
    }
}