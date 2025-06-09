package umd.cs.shop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.StreamTokenizer;
import java.io.StringReader;

public class JSTermTest {

    private JSTerm term;
    private StreamTokenizer tokenizer;

    @Before
    public void setUp() {
        term = new JSTerm();
        tokenizer = new StreamTokenizer(new StringReader("(call (add 1 2))"));
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(term);
    }

    @Test
    public void testConstructorWithTokenizer() {
        try {
            tokenizer.nextToken();
            term = new JSTerm(tokenizer);
            assertNotNull(term);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testParseList() {
        try {
            tokenizer.nextToken();
            JSTerm listTerm = term.parseList(tokenizer);
            assertNotNull(listTerm);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testPrint() {
        // This test is more of a visual check, as it prints to the console
        term.print();
    }

    @Test
    public void testCloneT() {
        JSTerm clonedTerm = term.cloneT();
        assertNotNull(clonedTerm);
        assertEquals(term.toStr().toString(), clonedTerm.toStr().toString());
    }

    @Test
    public void testApplySubstitutionT() {
        JSSubstitution alpha = new JSSubstitution();
        JSTerm substitutedTerm = term.applySubstitutionT(alpha);
        assertNotNull(substitutedTerm);
    }

    @Test
    public void testMatches() {
        JSTerm otherTerm = new JSTerm();
        JSSubstitution result = term.matches(otherTerm);
        assertNotNull(result);
    }

    @Test
    public void testMatchesWithSubstitution() {
        JSSubstitution alpha = new JSSubstitution();
        JSTerm otherTerm = new JSTerm();
        JSSubstitution result = term.matches(otherTerm, alpha);
        assertNotNull(result);
    }

    @Test
    public void testEquals() {
        JSTerm otherTerm = new JSTerm();
        boolean result = term.equals(otherTerm);
        assertTrue(result);
    }

    @Test
    public void testIsVariable() {
        assertFalse(term.isVariable());
    }

    @Test
    public void testIsConstant() {
        assertFalse(term.isConstant());
    }

    @Test
    public void testIsFunction() {
        assertFalse(term.isFunction());
    }

    @Test
    public void testIsEval() {
        assertFalse(term.isEval());
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
        StringBuffer result = term.toStr();
        assertNotNull(result);
    }

    @Test
    public void testIsGround() {
        boolean result = term.isGround();
        assertFalse(result);
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