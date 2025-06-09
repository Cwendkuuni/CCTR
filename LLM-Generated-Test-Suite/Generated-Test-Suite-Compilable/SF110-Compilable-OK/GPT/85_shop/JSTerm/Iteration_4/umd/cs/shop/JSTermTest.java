package umd.cs.shop;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.StreamTokenizer;
import java.io.StringReader;

public class JSTermTest {

    private JSTerm term;

    @Before
    public void setUp() {
        term = new JSTerm();
    }

    @Test
    public void testDefaultConstructor() {
        assertFalse(term.isVariable());
        assertFalse(term.isConstant());
        assertFalse(term.isFunction());
        assertFalse(term.isEval());
    }

    @Test
    public void testConstructorWithStreamTokenizer() {
        String input = "(list ?x)";
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(input));
        JSTerm term = new JSTerm(tokenizer);
        assertTrue(term.isFunction());
        assertFalse(term.isConstant());
        assertFalse(term.isVariable());
    }

    @Test(expected = JSParserError.class)
    public void testConstructorWithInvalidStreamTokenizer() {
        String input = "(invalid";
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(input));
        new JSTerm(tokenizer);
    }

    @Test
    public void testParseList() {
        String input = "(list ?x . ?y)";
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(input));
        JSTerm term = new JSTerm();
        JSTerm listTerm = term.parseList(tokenizer);
        assertTrue(listTerm.isFunction());
    }

    @Test
    public void testCloneT() {
        term.makeConstant();
        JSTerm clonedTerm = term.cloneT();
        assertTrue(clonedTerm.isConstant());
        assertFalse(clonedTerm.isVariable());
        assertFalse(clonedTerm.isFunction());
    }

    @Test
    public void testApplySubstitutionT() {
        JSSubstitution substitution = new JSSubstitution();
        JSTerm substitutedTerm = term.applySubstitutionT(substitution);
        assertNotNull(substitutedTerm);
    }

    @Test
    public void testMatches() {
        JSTerm otherTerm = new JSTerm();
        JSSubstitution substitution = term.matches(otherTerm);
        assertNotNull(substitution);
    }

    @Test
    public void testEquals() {
        JSTerm otherTerm = new JSTerm();
        assertTrue(term.equals(otherTerm));
    }

    @Test
    public void testIsVariable() {
        term.makeVariable();
        assertTrue(term.isVariable());
    }

    @Test
    public void testIsConstant() {
        term.makeConstant();
        assertTrue(term.isConstant());
    }

    @Test
    public void testIsFunction() {
        term.makeFunction();
        assertTrue(term.isFunction());
    }

    @Test
    public void testIsEval() {
        term.makeEval(true);
        assertTrue(term.isEval());
    }

    @Test
    public void testMakeFunction() {
        term.makeFunction();
        assertTrue(term.isFunction());
    }

    @Test
    public void testMakeVariable() {
        term.makeVariable();
        assertTrue(term.isVariable());
    }

    @Test
    public void testMakeConstant() {
        term.makeConstant();
        assertTrue(term.isConstant());
    }

    @Test
    public void testMakeEval() {
        term.makeEval(true);
        assertTrue(term.isEval());
    }

    @Test
    public void testToStr() {
        term.makeConstant();
        assertNotNull(term.toStr());
    }

    @Test
    public void testIsGround() {
        term.makeConstant();
        assertTrue(term.isGround());
    }

    @Test
    public void testStandardizerTerm() {
        JSTerm standardizedTerm = term.standardizerTerm();
        assertNotNull(standardizedTerm);
    }

    @Test
    public void testCall() {
        JSTerm calledTerm = term.call();
        assertNotNull(calledTerm);
    }
}