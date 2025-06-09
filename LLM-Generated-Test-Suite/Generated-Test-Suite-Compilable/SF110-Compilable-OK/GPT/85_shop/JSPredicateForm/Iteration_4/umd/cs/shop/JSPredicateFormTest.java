package umd.cs.shop;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.StringReader;
import java.io.StreamTokenizer;

public class JSPredicateFormTest {

    private JSPredicateForm predicateForm;
    private StreamTokenizer tokenizer;

    @Before
    public void setUp() {
        // Initialize a basic tokenizer for testing
        tokenizer = new StreamTokenizer(new StringReader("(test)"));
    }

    @Test
    public void testDefaultConstructor() {
        predicateForm = new JSPredicateForm();
        assertNotNull(predicateForm);
        assertEquals(0, predicateForm.size());
    }

    @Test
    public void testStringConstructor() {
        predicateForm = new JSPredicateForm("(test)");
        assertNotNull(predicateForm);
        assertEquals(1, predicateForm.size());
        assertEquals("test", predicateForm.elementAt(0));
    }

    @Test
    public void testStreamTokenizerConstructor() {
        predicateForm = new JSPredicateForm(tokenizer);
        assertNotNull(predicateForm);
        assertEquals(1, predicateForm.size());
        assertEquals("test", predicateForm.elementAt(0));
    }

    @Test(expected = JSParserError.class)
    public void testJSPredicateFormInitWithNullTokenizer() {
        predicateForm = new JSPredicateForm();
        predicateForm.JSPredicateFormInit(null);
    }

    @Test
    public void testPrint() {
        predicateForm = new JSPredicateForm("(test)");
        predicateForm.print(); // This should print to the console, no assertion needed
    }

    @Test
    public void testToStr() {
        predicateForm = new JSPredicateForm("(test)");
        StringBuffer result = predicateForm.toStr();
        assertEquals("(test)", result.toString());
    }

    @Test
    public void testClonePF() {
        predicateForm = new JSPredicateForm("(test)");
        JSPredicateForm cloned = predicateForm.clonePF();
        assertNotSame(predicateForm, cloned);
        assertEquals(predicateForm, cloned);
    }

    @Test
    public void testApplySubstitutionPF() {
        predicateForm = new JSPredicateForm("(test)");
        JSSubstitution substitution = new JSSubstitution();
        JSPredicateForm result = predicateForm.applySubstitutionPF(substitution);
        assertNotNull(result);
        assertEquals(predicateForm, result);
    }

    @Test
    public void testMatches() {
        predicateForm = new JSPredicateForm("(test)");
        JSPredicateForm other = new JSPredicateForm("(test)");
        JSSubstitution result = predicateForm.matches(other);
        assertNotNull(result);
        assertFalse(result.fail());
    }

    @Test
    public void testEquals() {
        predicateForm = new JSPredicateForm("(test)");
        JSPredicateForm other = new JSPredicateForm("(test)");
        assertTrue(predicateForm.equals(other));
    }

    @Test
    public void testStandarizerPredicateForm() {
        predicateForm = new JSPredicateForm("(test)");
        JSPredicateForm result = predicateForm.standarizerPredicateForm();
        assertNotNull(result);
        assertEquals(predicateForm, result);
    }
}