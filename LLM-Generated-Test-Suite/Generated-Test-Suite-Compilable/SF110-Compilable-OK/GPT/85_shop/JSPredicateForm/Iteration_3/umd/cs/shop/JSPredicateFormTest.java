package umd.cs.shop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringReader;
import java.io.StreamTokenizer;

public class JSPredicateFormTest {

    private JSPredicateForm predicateForm;
    private StreamTokenizer tokenizer;

    @Before
    public void setUp() {
        // Initialize a StreamTokenizer with a simple predicate string
        tokenizer = new StreamTokenizer(new StringReader("(predicate arg1 arg2)"));
        predicateForm = new JSPredicateForm(tokenizer);
    }

    @Test
    public void testConstructorWithString() {
        JSPredicateForm pf = new JSPredicateForm("(predicate arg1 arg2)");
        assertNotNull(pf);
        assertEquals(3, pf.size());
    }

    @Test
    public void testConstructorWithTokenizer() {
        assertNotNull(predicateForm);
        assertEquals(3, predicateForm.size());
    }

    @Test(expected = JSParserError.class)
    public void testConstructorWithNullTokenizer() {
        new JSPredicateForm((StreamTokenizer) null);
    }

    @Test
    public void testPrint() {
        // This test assumes JSUtil.print() writes to a known output stream
        // Here we just ensure no exceptions are thrown
        predicateForm.print();
    }

    @Test
    public void testToStr() {
        StringBuffer result = predicateForm.toStr();
        assertEquals("(predicate arg1 arg2)", result.toString());
    }

    @Test
    public void testClonePF() {
        JSPredicateForm clone = predicateForm.clonePF();
        assertNotSame(predicateForm, clone);
        assertEquals(predicateForm, clone);
    }

    @Test
    public void testApplySubstitutionPF() {
        JSSubstitution substitution = new JSSubstitution();
        JSPredicateForm substituted = predicateForm.applySubstitutionPF(substitution);
        assertNotNull(substituted);
        assertEquals(predicateForm, substituted);
    }

    @Test
    public void testMatches() {
        JSPredicateForm other = new JSPredicateForm("(predicate arg1 arg2)");
        JSSubstitution result = predicateForm.matches(other);
        assertNotNull(result);
        assertFalse(result.fail());
    }

    @Test
    public void testMatchesWithDifferentSize() {
        JSPredicateForm other = new JSPredicateForm("(predicate arg1)");
        JSSubstitution result = predicateForm.matches(other);
        assertTrue(result.fail());
    }

    @Test
    public void testEquals() {
        JSPredicateForm other = new JSPredicateForm("(predicate arg1 arg2)");
        assertTrue(predicateForm.equals(other));
    }

    @Test
    public void testEqualsWithDifferentPredicate() {
        JSPredicateForm other = new JSPredicateForm("(differentPredicate arg1 arg2)");
        assertFalse(predicateForm.equals(other));
    }

    @Test
    public void testStandarizerPredicateForm() {
        JSPredicateForm standardized = predicateForm.standarizerPredicateForm();
        assertNotNull(standardized);
        assertEquals(predicateForm, standardized);
    }
}