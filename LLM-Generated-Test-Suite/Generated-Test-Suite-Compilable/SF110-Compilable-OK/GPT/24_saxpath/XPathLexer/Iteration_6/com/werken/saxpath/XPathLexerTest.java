package com.werken.saxpath;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class XPathLexerTest {

    private XPathLexer lexer;

    @Before
    public void setUp() {
        lexer = new XPathLexer();
    }

    @Test
    public void testSetAndGetXPath() {
        String xpath = "/bookstore/book";
        lexer.setXPath(xpath);
        assertEquals(xpath, lexer.getXPath());
    }

    @Test
    public void testNextTokenDollar() {
        lexer.setXPath("$variable");
        Token token = lexer.nextToken();
        assertEquals(26, token.getTokenType());
    }

    @Test
    public void testNextTokenLiteral() {
        lexer.setXPath("\"literal\"");
        Token token = lexer.nextToken();
        assertEquals(27, token.getTokenType());
    }

    @Test
    public void testNextTokenSlashes() {
        lexer.setXPath("//");
        Token token = lexer.nextToken();
        assertEquals(12, token.getTokenType());
    }

    @Test
    public void testNextTokenComma() {
        lexer.setXPath(",");
        Token token = lexer.nextToken();
        assertEquals(32, token.getTokenType());
    }

    @Test
    public void testNextTokenLeftParen() {
        lexer.setXPath("(");
        Token token = lexer.nextToken();
        assertEquals(1, token.getTokenType());
    }

    @Test
    public void testNextTokenRightParen() {
        lexer.setXPath(")");
        Token token = lexer.nextToken();
        assertEquals(2, token.getTokenType());
    }

    @Test
    public void testNextTokenLeftBracket() {
        lexer.setXPath("[");
        Token token = lexer.nextToken();
        assertEquals(3, token.getTokenType());
    }

    @Test
    public void testNextTokenRightBracket() {
        lexer.setXPath("]");
        Token token = lexer.nextToken();
        assertEquals(4, token.getTokenType());
    }

    @Test
    public void testNextTokenPlus() {
        lexer.setXPath("+");
        Token token = lexer.nextToken();
        assertEquals(5, token.getTokenType());
    }

    @Test
    public void testNextTokenMinus() {
        lexer.setXPath("-");
        Token token = lexer.nextToken();
        assertEquals(6, token.getTokenType());
    }

    @Test
    public void testNextTokenRelationalOperator() {
        lexer.setXPath("<=");
        Token token = lexer.nextToken();
        assertEquals(8, token.getTokenType());
    }

    @Test
    public void testNextTokenEquals() {
        lexer.setXPath("=");
        Token token = lexer.nextToken();
        assertEquals(21, token.getTokenType());
    }

    @Test
    public void testNextTokenNotEquals() {
        lexer.setXPath("!=");
        Token token = lexer.nextToken();
        assertEquals(22, token.getTokenType());
    }

    @Test
    public void testNextTokenPipe() {
        lexer.setXPath("|");
        Token token = lexer.nextToken();
        assertEquals(17, token.getTokenType());
    }

    @Test
    public void testNextTokenAt() {
        lexer.setXPath("@");
        Token token = lexer.nextToken();
        assertEquals(16, token.getTokenType());
    }

    @Test
    public void testNextTokenColon() {
        lexer.setXPath(":");
        Token token = lexer.nextToken();
        assertEquals(18, token.getTokenType());
    }

    @Test
    public void testNextTokenDoubleColon() {
        lexer.setXPath("::");
        Token token = lexer.nextToken();
        assertEquals(19, token.getTokenType());
    }

    @Test
    public void testNextTokenStar() {
        lexer.setXPath("*");
        Token token = lexer.nextToken();
        assertEquals(20, token.getTokenType());
    }

    @Test
    public void testNextTokenNumber() {
        lexer.setXPath("123");
        Token token = lexer.nextToken();
        assertEquals(30, token.getTokenType());
    }

    @Test
    public void testNextTokenWhitespace() {
        lexer.setXPath(" ");
        Token token = lexer.nextToken();
        assertEquals(-2, token.getTokenType());
    }

    @Test
    public void testNextTokenIdentifier() {
        lexer.setXPath("identifier");
        Token token = lexer.nextToken();
        assertEquals(15, token.getTokenType());
    }

    @Test
    public void testNextTokenOperatorName() {
        lexer.setXPath("and");
        Token token = lexer.nextToken();
        assertEquals(28, token.getTokenType());
    }

    @Test
    public void testNextTokenDots() {
        lexer.setXPath("..");
        Token token = lexer.nextToken();
        assertEquals(14, token.getTokenType());
    }

    @Test
    public void testNextTokenNot() {
        lexer.setXPath("!");
        Token token = lexer.nextToken();
        assertEquals(23, token.getTokenType());
    }

    @Test
    public void testNextTokenDiv() {
        lexer.setXPath("div");
        Token token = lexer.nextToken();
        assertEquals(24, token.getTokenType());
    }

    @Test
    public void testNextTokenMod() {
        lexer.setXPath("mod");
        Token token = lexer.nextToken();
        assertEquals(25, token.getTokenType());
    }
}