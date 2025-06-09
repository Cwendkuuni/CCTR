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
    public void testParseIdSelector() {
        Evaluator evaluator = QueryParser.parse("#myId");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.Id);
    }

    @Test
    public void testParseClassSelector() {
        Evaluator evaluator = QueryParser.parse(".myClass");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.Class);
    }

    @Test
    public void testParseAttributeSelector() {
        Evaluator evaluator = QueryParser.parse("[href]");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.Attribute);
    }

    @Test
    public void testParseAttributeWithValueSelector() {
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
    public void testParseGroupSelector() {
        Evaluator evaluator = QueryParser.parse("div, p");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof CombiningEvaluator.Or);
    }

    @Test
    public void testParsePseudoFirstChild() {
        Evaluator evaluator = QueryParser.parse(":first-child");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsFirstChild);
    }

    @Test
    public void testParsePseudoLastChild() {
        Evaluator evaluator = QueryParser.parse(":last-child");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsLastChild);
    }

    @Test
    public void testParsePseudoNthChild() {
        Evaluator evaluator = QueryParser.parse(":nth-child(2)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsNthChild);
    }

    @Test
    public void testParsePseudoNthLastChild() {
        Evaluator evaluator = QueryParser.parse(":nth-last-child(2)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsNthLastChild);
    }

    @Test
    public void testParsePseudoOnlyChild() {
        Evaluator evaluator = QueryParser.parse(":only-child");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsOnlyChild);
    }

    @Test
    public void testParsePseudoEmpty() {
        Evaluator evaluator = QueryParser.parse(":empty");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsEmpty);
    }

    @Test
    public void testParsePseudoRoot() {
        Evaluator evaluator = QueryParser.parse(":root");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsRoot);
    }

    @Test
    public void testParsePseudoContains() {
        Evaluator evaluator = QueryParser.parse(":contains(text)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.ContainsText);
    }

    @Test
    public void testParsePseudoContainsOwn() {
        Evaluator evaluator = QueryParser.parse(":containsOwn(text)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.ContainsOwnText);
    }

    @Test
    public void testParsePseudoHas() {
        Evaluator evaluator = QueryParser.parse(":has(div)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof StructuralEvaluator.Has);
    }

    @Test
    public void testParsePseudoNot() {
        Evaluator evaluator = QueryParser.parse(":not(div)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof StructuralEvaluator.Not);
    }

    @Test(expected = Selector.SelectorParseException.class)
    public void testParseInvalidSelector() {
        QueryParser.parse("div > > p");
    }
}