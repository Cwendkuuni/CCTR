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
    public void testParseAttributeWithValueNot() {
        Evaluator evaluator = QueryParser.parse("[href!='example.com']");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.AttributeWithValueNot);
    }

    @Test
    public void testParseAttributeWithValueStarting() {
        Evaluator evaluator = QueryParser.parse("[href^='http']");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.AttributeWithValueStarting);
    }

    @Test
    public void testParseAttributeWithValueEnding() {
        Evaluator evaluator = QueryParser.parse("[href$='.com']");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.AttributeWithValueEnding);
    }

    @Test
    public void testParseAttributeWithValueContaining() {
        Evaluator evaluator = QueryParser.parse("[href*='example']");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.AttributeWithValueContaining);
    }

    @Test
    public void testParseAttributeWithValueMatching() {
        Evaluator evaluator = QueryParser.parse("[href~='regex']");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.AttributeWithValueMatching);
    }

    @Test
    public void testParseAllElements() {
        Evaluator evaluator = QueryParser.parse("*");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.AllElements);
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

    @Test
    public void testParseNthChild() {
        Evaluator evaluator = QueryParser.parse(":nth-child(2n+1)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsNthChild);
    }

    @Test
    public void testParseNthLastChild() {
        Evaluator evaluator = QueryParser.parse(":nth-last-child(2n+1)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsNthLastChild);
    }

    @Test
    public void testParseNthOfType() {
        Evaluator evaluator = QueryParser.parse(":nth-of-type(2n+1)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsNthOfType);
    }

    @Test
    public void testParseNthLastOfType() {
        Evaluator evaluator = QueryParser.parse(":nth-last-of-type(2n+1)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsNthLastOfType);
    }

    @Test
    public void testParseFirstChild() {
        Evaluator evaluator = QueryParser.parse(":first-child");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsFirstChild);
    }

    @Test
    public void testParseLastChild() {
        Evaluator evaluator = QueryParser.parse(":last-child");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsLastChild);
    }

    @Test
    public void testParseFirstOfType() {
        Evaluator evaluator = QueryParser.parse(":first-of-type");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsFirstOfType);
    }

    @Test
    public void testParseLastOfType() {
        Evaluator evaluator = QueryParser.parse(":last-of-type");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsLastOfType);
    }

    @Test
    public void testParseOnlyChild() {
        Evaluator evaluator = QueryParser.parse(":only-child");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsOnlyChild);
    }

    @Test
    public void testParseOnlyOfType() {
        Evaluator evaluator = QueryParser.parse(":only-of-type");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsOnlyOfType);
    }

    @Test
    public void testParseEmpty() {
        Evaluator evaluator = QueryParser.parse(":empty");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsEmpty);
    }

    @Test
    public void testParseRoot() {
        Evaluator evaluator = QueryParser.parse(":root");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.IsRoot);
    }

    @Test
    public void testParseHas() {
        Evaluator evaluator = QueryParser.parse(":has(div)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof StructuralEvaluator.Has);
    }

    @Test
    public void testParseContains() {
        Evaluator evaluator = QueryParser.parse(":contains(text)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.ContainsText);
    }

    @Test
    public void testParseContainsOwn() {
        Evaluator evaluator = QueryParser.parse(":containsOwn(text)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.ContainsOwnText);
    }

    @Test
    public void testParseContainsData() {
        Evaluator evaluator = QueryParser.parse(":containsData(data)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.ContainsData);
    }

    @Test
    public void testParseMatches() {
        Evaluator evaluator = QueryParser.parse(":matches(regex)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.Matches);
    }

    @Test
    public void testParseMatchesOwn() {
        Evaluator evaluator = QueryParser.parse(":matchesOwn(regex)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof Evaluator.MatchesOwn);
    }

    @Test
    public void testParseNot() {
        Evaluator evaluator = QueryParser.parse(":not(div)");
        assertNotNull(evaluator);
        assertTrue(evaluator instanceof StructuralEvaluator.Not);
    }

    @Test(expected = Selector.SelectorParseException.class)
    public void testParseInvalidQuery() {
        QueryParser.parse(":unknown");
    }

    @Test(expected = Selector.SelectorParseException.class)
    public void testParseEmptyQuery() {
        QueryParser.parse("");
    }
}