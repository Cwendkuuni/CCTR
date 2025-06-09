package umd.cs.shop;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.StreamTokenizer;
import java.io.StringReader;

public class JSTermTest {

    private StreamTokenizer tokenizer;

    @Before
    public void setUp() {
        // Initialize a StreamTokenizer for testing
        tokenizer = new StreamTokenizer(new StringReader(""));
    }

    @Test
    public void testDefaultConstructor() {
        JSTerm term = new JSTerm();
        assertFalse(term.isVariable());
        assertFalse(term.isConstant());
        assertFalse(term.isFunction());
        assertFalse(term.isEval());
    }

    @Test
    public void testConstructorWithTokenizer() {
        // Example test for constructor with tokenizer
        tokenizer = new StreamTokenizer(new StringReader("(list a b c)"));
        tokenizer.ordinaryChar('(');
        tokenizer.ordinaryChar(')');
        JSTerm term = new JSTerm(tokenizer);
        assertTrue(term.isFunction());
        assertFalse(term.isVariable());
        assertFalse(term.isConstant());
    }

    @Test
    public void testParseList() {
        tokenizer = new StreamTokenizer(new StringReader("(a . b)"));
        tokenizer.ordinaryChar('(');
        tokenizer.ordinaryChar(')');
        JSTerm term = new JSTerm();
        JSTerm list = term.parseList(tokenizer);
        assertTrue(list.isFunction());
    }

    @Test
    public void testCloneT() {
        JSTerm term = new JSTerm();
        term.makeConstant();
        JSTerm clonedTerm = term.cloneT();
        assertTrue(clonedTerm.isConstant());
        assertFalse(clonedTerm.isVariable());
    }

    @Test
    public void testApplySubstitutionT() {
        JSTerm term = new JSTerm();
        term.makeVariable();
        JSSubstitution substitution = new JSSubstitution();
        JSTerm substitutedTerm = term.applySubstitutionT(substitution);
        assertTrue(substitutedTerm.isVariable());
    }

    @Test
    public void testMatches() {
        JSTerm term1 = new JSTerm();
        term1.makeConstant();
        JSTerm term2 = new JSTerm();
        term2.makeConstant();
        JSSubstitution substitution = term1.matches(term2);
        assertFalse(substitution.fail());
    }

    @Test
    public void testEquals() {
        JSTerm term1 = new JSTerm();
        term1.makeConstant();
        JSTerm term2 = new JSTerm();
        term2.makeConstant();
        assertTrue(term1.equals(term2));
    }

    @Test
    public void testIsVariable() {
        JSTerm term = new JSTerm();
        term.makeVariable();
        assertTrue(term.isVariable());
    }

    @Test
    public void testIsConstant() {
        JSTerm term = new JSTerm();
        term.makeConstant();
        assertTrue(term.isConstant());
    }

    @Test
    public void testIsFunction() {
        JSTerm term = new JSTerm();
        term.makeFunction();
        assertTrue(term.isFunction());
    }

    @Test
    public void testIsEval() {
        JSTerm term = new JSTerm();
        term.makeEval(true);
        assertTrue(term.isEval());
    }

    @Test
    public void testMakeFunction() {
        JSTerm term = new JSTerm();
        term.makeFunction();
        assertTrue(term.isFunction());
    }

    @Test
    public void testMakeVariable() {
        JSTerm term = new JSTerm();
        term.makeVariable();
        assertTrue(term.isVariable());
    }

    @Test
    public void testMakeConstant() {
        JSTerm term = new JSTerm();
        term.makeConstant();
        assertTrue(term.isConstant());
    }

    @Test
    public void testMakeEval() {
        JSTerm term = new JSTerm();
        term.makeEval(true);
        assertTrue(term.isEval());
    }

    @Test
    public void testToStr() {
        JSTerm term = new JSTerm();
        term.makeConstant();
        assertNotNull(term.toStr());
    }

    @Test
    public void testIsGround() {
        JSTerm term = new JSTerm();
        term.makeConstant();
        assertTrue(term.isGround());
    }

    @Test
    public void testStandardizerTerm() {
        JSTerm term = new JSTerm();
        term.makeVariable();
        JSTerm standardizedTerm = term.standardizerTerm();
        assertTrue(standardizedTerm.isVariable());
    }

    @Test
    public void testCall() {
        JSTerm term = new JSTerm();
        term.makeFunction();
        JSTerm result = term.call();
        assertTrue(result.isFunction());
    }
}