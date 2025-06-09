package org.jsoup.select;

import org.jsoup.select.QueryParser;
import org.jsoup.select.Evaluator;
import org.jsoup.select.Selector;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class QueryParserTest {

    private QueryParser queryParser;

    @Before
    public void setUp() {
        // This setup is not directly used since QueryParser's constructor is private.
        // Tests will directly use the static parse method.
    }

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
    public void testParseImmediateSiblingCombinator() {
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

    @Test(expected = Selector.SelectorParseException.class)
    public void testParseInvalidSelector() {
        QueryParser.parse("div > > p");
    }

    @Test
    public void testParsePseudoClass() {
        Evaluator evaluator = QueryParser.parse(":contains(text)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.ContainsText);
    }

    @Test
    public void testParsePseudoClassOwn() {
        Evaluator evaluator = QueryParser.parse(":containsOwn(text)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.ContainsOwnText);
    }

    @Test
    public void testParsePseudoClassMatches() {
        Evaluator evaluator = QueryParser.parse(":matches(regex)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.Matches);
    }

    @Test
    public void testParsePseudoClassMatchesOwn() {
        Evaluator evaluator = QueryParser.parse(":matchesOwn(regex)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.MatchesOwn);
    }

    @Test
    public void testParsePseudoClassNot() {
        Evaluator evaluator = QueryParser.parse(":not(div)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof StructuralEvaluator.Not);
    }

    @Test
    public void testParsePseudoClassHas() {
        Evaluator evaluator = QueryParser.parse(":has(div)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof StructuralEvaluator.Has);
    }

    @Test
    public void testParseIndexLessThan() {
        Evaluator evaluator = QueryParser.parse(":lt(3)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IndexLessThan);
    }

    @Test
    public void testParseIndexGreaterThan() {
        Evaluator evaluator = QueryParser.parse(":gt(3)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IndexGreaterThan);
    }

    @Test
    public void testParseIndexEquals() {
        Evaluator evaluator = QueryParser.parse(":eq(3)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IndexEquals);
    }
}