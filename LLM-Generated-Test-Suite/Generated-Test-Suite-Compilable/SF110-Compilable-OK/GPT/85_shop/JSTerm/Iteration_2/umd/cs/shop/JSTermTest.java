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
    public void testConstructorWithTokenizerVariable() throws Exception {
        String input = "?x";
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(input));
        tokenizer.nextToken();
        JSTerm term = new JSTerm(tokenizer);
        assertTrue(term.isVariable());
        assertFalse(term.isConstant());
        assertFalse(term.isFunction());
        assertEquals("?x", term.elementAt(0));
    }

    @Test
    public void testConstructorWithTokenizerConstant() throws Exception {
        String input = "42";
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(input));
        tokenizer.nextToken();
        JSTerm term = new JSTerm(tokenizer);
        assertFalse(term.isVariable());
        assertTrue(term.isConstant());
        assertFalse(term.isFunction());
        assertEquals("42.0", term.elementAt(0)); // Double is converted to string
    }

    @Test
    public void testConstructorWithTokenizerFunction() throws Exception {
        String input = "(add 1 2)";
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(input));
        tokenizer.nextToken();
        JSTerm term = new JSTerm(tokenizer);
        assertFalse(term.isVariable());
        assertFalse(term.isConstant());
        assertTrue(term.isFunction());
        assertEquals("add", term.elementAt(0));
    }

    @Test
    public void testCloneT() {
        term.makeConstant();
        term.addElement("42");
        JSTerm clonedTerm = term.cloneT();
        assertTrue(clonedTerm.isConstant());
        assertEquals("42", clonedTerm.elementAt(0));
    }

    @Test
    public void testApplySubstitutionT() {
        term.makeVariable();
        term.addElement("?x");
        JSSubstitution substitution = new JSSubstitution();
        substitution.addElement(new JSPairVarTerm(term, new JSTerm()));
        JSTerm substitutedTerm = term.applySubstitutionT(substitution);
        assertTrue(substitutedTerm.isVariable());
    }

    @Test
    public void testMatches() {
        JSTerm term1 = new JSTerm();
        term1.makeConstant();
        term1.addElement("42");

        JSTerm term2 = new JSTerm();
        term2.makeConstant();
        term2.addElement("42");

        JSSubstitution substitution = term1.matches(term2);
        assertFalse(substitution.fail());
    }

    @Test
    public void testEquals() {
        JSTerm term1 = new JSTerm();
        term1.makeConstant();
        term1.addElement("42");

        JSTerm term2 = new JSTerm();
        term2.makeConstant();
        term2.addElement("42");

        assertTrue(term1.equals(term2));
    }

    @Test
    public void testIsGround() {
        term.makeConstant();
        term.addElement("42");
        assertTrue(term.isGround());
    }

    @Test
    public void testStandardizerTerm() {
        term.makeVariable();
        term.addElement("?x");
        JSTerm standardizedTerm = term.standardizerTerm();
        assertTrue(standardizedTerm.isVariable());
    }

    @Test
    public void testCall() {
        term.makeFunction();
        term.addElement("add");
        JSTerm result = term.call();
        assertTrue(result.isFunction());
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
        term.makeConstant();
        term.addElement("42");
        assertEquals("42 ", term.toStr().toString());
    }
}