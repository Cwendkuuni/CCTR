package org.jsoup.select;

import org.jsoup.select.QueryParser;
import org.jsoup.select.Evaluator;
import org.jsoup.select.Selector;
import org.junit.Test;
import static org.junit.Assert.*;

public class QueryParserTest {

    @Test
    public void testParseValidIdSelector() {
        Evaluator evaluator = QueryParser.parse("#myId");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.Id);
    }

    @Test
    public void testParseValidClassSelector() {
        Evaluator evaluator = QueryParser.parse(".myClass");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.Class);
    }

    @Test
    public void testParseValidTagSelector() {
        Evaluator evaluator = QueryParser.parse("div");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.Tag);
    }

    @Test
    public void testParseValidAttributeSelector() {
        Evaluator evaluator = QueryParser.parse("[href]");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.Attribute);
    }

    @Test
    public void testParseValidAttributeWithValueSelector() {
        Evaluator evaluator = QueryParser.parse("[href='example.com']");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.AttributeWithValue);
    }

    @Test
    public void testParseValidChildCombinator() {
        Evaluator evaluator = QueryParser.parse("div > p");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof CombiningEvaluator.And);
    }

    @Test
    public void testParseValidDescendantCombinator() {
        Evaluator evaluator = QueryParser.parse("div p");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof CombiningEvaluator.And);
    }

    @Test
    public void testParseValidAdjacentSiblingCombinator() {
        Evaluator evaluator = QueryParser.parse("div + p");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof CombiningEvaluator.And);
    }

    @Test
    public void testParseValidGeneralSiblingCombinator() {
        Evaluator evaluator = QueryParser.parse("div ~ p");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof CombiningEvaluator.And);
    }

    @Test
    public void testParseValidGroupSelector() {
        Evaluator evaluator = QueryParser.parse("div, p");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof CombiningEvaluator.Or);
    }

    @Test(expected = Selector.SelectorParseException.class)
    public void testParseInvalidSelector() {
        QueryParser.parse("div > > p");
    }

    @Test(expected = Selector.SelectorParseException.class)
    public void testParseEmptySelector() {
        QueryParser.parse("");
    }

    @Test
    public void testParseValidPseudoClassFirstChild() {
        Evaluator evaluator = QueryParser.parse(":first-child");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsFirstChild);
    }

    @Test
    public void testParseValidPseudoClassLastChild() {
        Evaluator evaluator = QueryParser.parse(":last-child");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsLastChild);
    }

    @Test
    public void testParseValidPseudoClassNthChild() {
        Evaluator evaluator = QueryParser.parse(":nth-child(2)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsNthChild);
    }

    @Test
    public void testParseValidPseudoClassNot() {
        Evaluator evaluator = QueryParser.parse(":not(.excluded)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof StructuralEvaluator.Not);
    }

    @Test
    public void testParseValidPseudoClassContains() {
        Evaluator evaluator = QueryParser.parse(":contains(text)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.ContainsText);
    }

    @Test
    public void testParseValidPseudoClassHas() {
        Evaluator evaluator = QueryParser.parse(":has(p)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof StructuralEvaluator.Has);
    }

    @Test
    public void testParseValidUniversalSelector() {
        Evaluator evaluator = QueryParser.parse("*");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.AllElements);
    }
}