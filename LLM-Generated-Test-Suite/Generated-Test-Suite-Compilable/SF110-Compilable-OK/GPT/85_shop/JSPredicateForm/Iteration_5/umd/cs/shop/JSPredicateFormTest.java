package umd.cs.shop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringReader;
import java.io.StreamTokenizer;

public class JSPredicateFormTest {

    private JSPredicateForm predicateForm;

    @Before
    public void setUp() {
        predicateForm = new JSPredicateForm();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(predicateForm);
        assertEquals(0, predicateForm.size());
    }

    @Test
    public void testStringConstructor() {
        JSPredicateForm pf = new JSPredicateForm("(not (call someFunction))");
        assertNotNull(pf);
        assertEquals(2, pf.size());
    }

    @Test(expected = JSParserError.class)
    public void testStringConstructorWithInvalidInput() {
        new JSPredicateForm("invalid input");
    }

    @Test
    public void testStreamTokenizerConstructor() {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader("(not (call someFunction))"));
        JSPredicateForm pf = new JSPredicateForm(tokenizer);
        assertNotNull(pf);
        assertEquals(2, pf.size());
    }

    @Test(expected = JSParserError.class)
    public void testStreamTokenizerConstructorWithInvalidInput() {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader("invalid input"));
        new JSPredicateForm(tokenizer);
    }

    @Test
    public void testPrint() {
        JSPredicateForm pf = new JSPredicateForm("(not (call someFunction))");
        pf.print(); // This should print the predicate form to the console
    }

    @Test
    public void testToStr() {
        JSPredicateForm pf = new JSPredicateForm("(not (call someFunction))");
        StringBuffer result = pf.toStr();
        assertEquals("(not(call someFunction))", result.toString());
    }

    @Test
    public void testClonePF() {
        JSPredicateForm pf = new JSPredicateForm("(not (call someFunction))");
        JSPredicateForm cloned = pf.clonePF();
        assertNotNull(cloned);
        assertEquals(pf, cloned);
    }

    @Test
    public void testApplySubstitutionPF() {
        JSPredicateForm pf = new JSPredicateForm("(call someFunction)");
        JSSubstitution substitution = new JSSubstitution();
        JSPredicateForm result = pf.applySubstitutionPF(substitution);
        assertNotNull(result);
        assertEquals(pf, result);
    }

    @Test
    public void testMatches() {
        JSPredicateForm pf1 = new JSPredicateForm("(call someFunction)");
        JSPredicateForm pf2 = new JSPredicateForm("(call someFunction)");
        JSSubstitution result = pf1.matches(pf2);
        assertNotNull(result);
        assertFalse(result.fail());
    }

    @Test
    public void testMatchesWithDifferentForms() {
        JSPredicateForm pf1 = new JSPredicateForm("(call someFunction)");
        JSPredicateForm pf2 = new JSPredicateForm("(call anotherFunction)");
        JSSubstitution result = pf1.matches(pf2);
        assertNotNull(result);
        assertTrue(result.fail());
    }

    @Test
    public void testEquals() {
        JSPredicateForm pf1 = new JSPredicateForm("(call someFunction)");
        JSPredicateForm pf2 = new JSPredicateForm("(call someFunction)");
        assertTrue(pf1.equals(pf2));
    }

    @Test
    public void testEqualsWithDifferentForms() {
        JSPredicateForm pf1 = new JSPredicateForm("(call someFunction)");
        JSPredicateForm pf2 = new JSPredicateForm("(call anotherFunction)");
        assertFalse(pf1.equals(pf2));
    }

    @Test
    public void testStandarizerPredicateForm() {
        JSPredicateForm pf = new JSPredicateForm("(call someFunction)");
        JSPredicateForm standardized = pf.standarizerPredicateForm();
        assertNotNull(standardized);
        assertEquals(pf, standardized);
    }
}