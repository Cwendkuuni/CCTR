package org.jsoup.parser;

import org.jsoup.parser.TokenQueue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TokenQueueTest {
    private TokenQueue tokenQueue;

    @Before
    public void setUp() {
        tokenQueue = new TokenQueue("Hello World! This is a test.");
    }

    @Test
    public void testIsEmpty() {
        assertFalse(tokenQueue.isEmpty());
        tokenQueue.consumeTo("test.");
        tokenQueue.consume("test.");
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
        tokenQueue.addFirst('A');
        assertEquals('A', tokenQueue.peek());
    }

    @Test
    public void testAddFirstString() {
        tokenQueue.addFirst("Test ");
        assertTrue(tokenQueue.matches("Test Hello"));
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
        assertFalse(tokenQueue.matchesAny("world", "test"));
    }

    @Test
    public void testMatchesAnyChar() {
        assertTrue(tokenQueue.matchesAny('H', 'W'));
        assertFalse(tokenQueue.matchesAny('w', 't'));
    }

    @Test
    public void testMatchesStartTag() {
        TokenQueue tq = new TokenQueue("<html>");
        assertTrue(tq.matchesStartTag());
        tq.consume("<html>");
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
        tokenQueue.consumeWhitespace();
        assertTrue(tokenQueue.matchesWord());
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
    public void testConsumeStringException() {
        tokenQueue.consume("World");
    }

    @Test
    public void testConsumeString() {
        tokenQueue.consume("Hello");
        assertEquals(' ', tokenQueue.peek());
    }

    @Test
    public void testConsumeTo() {
        String consumed = tokenQueue.consumeTo("World");
        assertEquals("Hello ", consumed);
    }

    @Test
    public void testConsumeToIgnoreCase() {
        String consumed = tokenQueue.consumeToIgnoreCase("world");
        assertEquals("Hello ", consumed);
    }

    @Test
    public void testConsumeToAny() {
        String consumed = tokenQueue.consumeToAny("World", "test");
        assertEquals("Hello ", consumed);
    }

    @Test
    public void testChompTo() {
        String chomped = tokenQueue.chompTo("World");
        assertEquals("Hello ", chomped);
        assertEquals('W', tokenQueue.peek());
    }

    @Test
    public void testChompToIgnoreCase() {
        String chomped = tokenQueue.chompToIgnoreCase("world");
        assertEquals("Hello ", chomped);
        assertEquals('W', tokenQueue.peek());
    }

    @Test
    public void testChompBalanced() {
        TokenQueue tq = new TokenQueue("(one (two) three) four");
        String balanced = tq.chompBalanced('(', ')');
        assertEquals("one (two) three", balanced);
    }

    @Test
    public void testUnescape() {
        String escaped = "This \\is a \\test";
        String unescaped = TokenQueue.unescape(escaped);
        assertEquals("This is a test", unescaped);
    }

    @Test
    public void testConsumeWhitespace() {
        tokenQueue.consume("Hello");
        assertTrue(tokenQueue.consumeWhitespace());
        assertEquals('W', tokenQueue.peek());
    }

    @Test
    public void testConsumeWord() {
        String word = tokenQueue.consumeWord();
        assertEquals("Hello", word);
    }

    @Test
    public void testConsumeTagName() {
        TokenQueue tq = new TokenQueue("tag-name");
        String tagName = tq.consumeTagName();
        assertEquals("tag-name", tagName);
    }

    @Test
    public void testConsumeElementSelector() {
        TokenQueue tq = new TokenQueue("tag|name");
        String selector = tq.consumeElementSelector();
        assertEquals("tag|name", selector);
    }

    @Test
    public void testConsumeCssIdentifier() {
        TokenQueue tq = new TokenQueue("identifier-123");
        String identifier = tq.consumeCssIdentifier();
        assertEquals("identifier-123", identifier);
    }

    @Test
    public void testConsumeAttributeKey() {
        TokenQueue tq = new TokenQueue("attr-key");
        String attrKey = tq.consumeAttributeKey();
        assertEquals("attr-key", attrKey);
    }

    @Test
    public void testRemainder() {
        String remainder = tokenQueue.remainder();
        assertEquals("Hello World! This is a test.", remainder);
    }

    @Test
    public void testToString() {
        assertEquals("Hello World! This is a test.", tokenQueue.toString());
    }
}