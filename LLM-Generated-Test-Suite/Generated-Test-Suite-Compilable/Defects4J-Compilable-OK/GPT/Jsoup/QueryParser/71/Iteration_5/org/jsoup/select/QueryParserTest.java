package org.jsoup.select;

import org.jsoup.select.QueryParser;
import org.jsoup.select.Evaluator;
import org.jsoup.select.Selector;
import org.junit.Test;
import static org.junit.Assert.*;

public class QueryParserTest {

    @Test
    public void testParseSimpleTag() {
        Evaluator evaluator = QueryParser.parse("div");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.Tag);
    }

    @Test
    public void testParseId() {
        Evaluator evaluator = QueryParser.parse("#myId");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.Id);
    }

    @Test
    public void testParseClass() {
        Evaluator evaluator = QueryParser.parse(".myClass");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.Class);
    }

    @Test
    public void testParseAttribute() {
        Evaluator evaluator = QueryParser.parse("[href]");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.Attribute);
    }

    @Test
    public void testParseAttributeWithValue() {
        Evaluator evaluator = QueryParser.parse("[href='example.com']");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.AttributeWithValue);
    }

    @Test
    public void testParseChildCombinator() {
        Evaluator evaluator = QueryParser.parse("div > p");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof CombiningEvaluator.And);
    }

    @Test
    public void testParseDescendantCombinator() {
        Evaluator evaluator = QueryParser.parse("div p");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof CombiningEvaluator.And);
    }

    @Test
    public void testParseAdjacentSiblingCombinator() {
        Evaluator evaluator = QueryParser.parse("div + p");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof CombiningEvaluator.And);
    }

    @Test
    public void testParseGeneralSiblingCombinator() {
        Evaluator evaluator = QueryParser.parse("div ~ p");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof CombiningEvaluator.And);
    }

    @Test
    public void testParseGroupCombinator() {
        Evaluator evaluator = QueryParser.parse("div, p");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof CombiningEvaluator.Or);
    }

    @Test
    public void testParsePseudoClassFirstChild() {
        Evaluator evaluator = QueryParser.parse(":first-child");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsFirstChild);
    }

    @Test
    public void testParsePseudoClassLastChild() {
        Evaluator evaluator = QueryParser.parse(":last-child");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsLastChild);
    }

    @Test
    public void testParsePseudoClassNthChild() {
        Evaluator evaluator = QueryParser.parse(":nth-child(2)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsNthChild);
    }

    @Test
    public void testParsePseudoClassOnlyChild() {
        Evaluator evaluator = QueryParser.parse(":only-child");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsOnlyChild);
    }

    @Test
    public void testParsePseudoClassEmpty() {
        Evaluator evaluator = QueryParser.parse(":empty");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsEmpty);
    }

    @Test
    public void testParsePseudoClassRoot() {
        Evaluator evaluator = QueryParser.parse(":root");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsRoot);
    }

    @Test(expected = Selector.SelectorParseException.class)
    public void testParseInvalidQuery() {
        QueryParser.parse("div >");
    }

    @Test(expected = Selector.SelectorParseException.class)
    public void testParseInvalidAttribute() {
        QueryParser.parse("[href='']");
    }

    @Test(expected = Selector.SelectorParseException.class)
    public void testParseInvalidPseudoClass() {
        QueryParser.parse(":unknown");
    }
}