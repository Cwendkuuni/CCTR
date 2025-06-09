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
        // Initialize a basic tokenizer for testing
        tokenizer = new StreamTokenizer(new StringReader("(testPredicate arg1 arg2)"));
    }

    @Test
    public void testDefaultConstructor() {
        predicateForm = new JSPredicateForm();
        assertNotNull(predicateForm);
        assertEquals(0, predicateForm.size());
    }

    @Test
    public void testStringConstructor() {
        predicateForm = new JSPredicateForm("(testPredicate arg1 arg2)");
        assertNotNull(predicateForm);
        assertEquals(3, predicateForm.size());
    }

    @Test
    public void testStreamTokenizerConstructor() {
        predicateForm = new JSPredicateForm(tokenizer);
        assertNotNull(predicateForm);
        assertEquals(3, predicateForm.size());
    }

    @Test(expected = JSParserError.class)
    public void testJSPredicateFormInitWithNullTokenizer() {
        predicateForm = new JSPredicateForm();
        predicateForm.JSPredicateFormInit(null);
    }

    @Test
    public void testPrint() {
        predicateForm = new JSPredicateForm("(testPredicate arg1 arg2)");
        predicateForm.print(); // This should print to the console
    }

    @Test
    public void testToStr() {
        predicateForm = new JSPredicateForm("(testPredicate arg1 arg2)");
        StringBuffer result = predicateForm.toStr();
        assertEquals("(testPredicate arg1 arg2)", result.toString());
    }

    @Test
    public void testClonePF() {
        predicateForm = new JSPredicateForm("(testPredicate arg1 arg2)");
        JSPredicateForm cloned = predicateForm.clonePF();
        assertEquals(predicateForm, cloned);
    }

    @Test
    public void testApplySubstitutionPF() {
        predicateForm = new JSPredicateForm("(testPredicate arg1 arg2)");
        JSSubstitution substitution = new JSSubstitution();
        JSPredicateForm substituted = predicateForm.applySubstitutionPF(substitution);
        assertNotNull(substituted);
    }

    @Test
    public void testMatches() {
        predicateForm = new JSPredicateForm("(testPredicate arg1 arg2)");
        JSPredicateForm other = new JSPredicateForm("(testPredicate arg1 arg2)");
        JSSubstitution result = predicateForm.matches(other);
        assertNotNull(result);
        assertFalse(result.fail());
    }

    @Test
    public void testEquals() {
        predicateForm = new JSPredicateForm("(testPredicate arg1 arg2)");
        JSPredicateForm other = new JSPredicateForm("(testPredicate arg1 arg2)");
        assertTrue(predicateForm.equals(other));
    }

    @Test
    public void testStandarizerPredicateForm() {
        predicateForm = new JSPredicateForm("(testPredicate arg1 arg2)");
        JSPredicateForm standardized = predicateForm.standarizerPredicateForm();
        assertNotNull(standardized);
    }
}