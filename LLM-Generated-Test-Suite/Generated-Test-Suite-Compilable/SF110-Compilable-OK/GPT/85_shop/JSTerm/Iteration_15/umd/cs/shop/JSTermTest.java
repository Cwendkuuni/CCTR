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
    public void testConstructorWithStreamTokenizerVariable() {
        String input = "?x";
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(input));
        JSTerm term = new JSTerm(tokenizer);
        assertTrue(term.isVariable());
        assertFalse(term.isConstant());
        assertFalse(term.isFunction());
    }

    @Test
    public void testConstructorWithStreamTokenizerConstant() {
        String input = "constant";
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(input));
        JSTerm term = new JSTerm(tokenizer);
        assertFalse(term.isVariable());
        assertTrue(term.isConstant());
        assertFalse(term.isFunction());
    }

    @Test
    public void testConstructorWithStreamTokenizerFunction() {
        String input = "(function)";
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(input));
        JSTerm term = new JSTerm(tokenizer);
        assertFalse(term.isVariable());
        assertFalse(term.isConstant());
        assertTrue(term.isFunction());
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
    public void testIsGround() {
        assertTrue(term.isGround());
    }

    @Test
    public void testStandardizerTerm() {
        JSTerm standardizedTerm = term.standardizerTerm();
        assertNotNull(standardizedTerm);
    }

    @Test
    public void testCall() {
        JSTerm result = term.call();
        assertNotNull(result);
    }

    @Test
    public void testMakeFunction() {
        term.makeFunction();
        assertTrue(term.isFunction());
        assertFalse(term.isVariable());
        assertFalse(term.isConstant());
    }

    @Test
    public void testMakeVariable() {
        term.makeVariable();
        assertTrue(term.isVariable());
        assertFalse(term.isConstant());
        assertFalse(term.isFunction());
    }

    @Test
    public void testMakeConstant() {
        term.makeConstant();
        assertTrue(term.isConstant());
        assertFalse(term.isVariable());
        assertFalse(term.isFunction());
    }

    @Test
    public void testMakeEval() {
        term.makeEval(true);
        assertTrue(term.isEval());
    }

    @Test
    public void testToStr() {
        StringBuffer str = term.toStr();
        assertNotNull(str);
    }
}