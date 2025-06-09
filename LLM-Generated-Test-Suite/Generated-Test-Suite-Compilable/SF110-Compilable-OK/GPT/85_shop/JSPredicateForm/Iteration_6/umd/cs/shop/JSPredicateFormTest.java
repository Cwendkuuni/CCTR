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
        // Initialize a StreamTokenizer for testing
        tokenizer = new StreamTokenizer(new StringReader("(testPredicate)"));
    }

    @Test
    public void testDefaultConstructor() {
        JSPredicateForm pf = new JSPredicateForm();
        assertNotNull(pf);
        assertEquals(0, pf.size());
    }

    @Test
    public void testStringConstructor() {
        JSPredicateForm pf = new JSPredicateForm("(testPredicate)");
        assertNotNull(pf);
        assertEquals(1, pf.size());
    }

    @Test
    public void testStreamTokenizerConstructor() {
        JSPredicateForm pf = new JSPredicateForm(tokenizer);
        assertNotNull(pf);
        assertEquals(1, pf.size());
    }

    @Test(expected = JSParserError.class)
    public void testJSPredicateFormInitWithNullTokenizer() {
        new JSPredicateForm((StreamTokenizer) null);
    }

    @Test
    public void testPrint() {
        JSPredicateForm pf = new JSPredicateForm("(testPredicate)");
        pf.print(); // This should print to the console, no assertion needed
    }

    @Test
    public void testToStr() {
        JSPredicateForm pf = new JSPredicateForm("(testPredicate)");
        StringBuffer result = pf.toStr();
        assertEquals("(testPredicate)", result.toString());
    }

    @Test
    public void testClonePF() {
        JSPredicateForm pf = new JSPredicateForm("(testPredicate)");
        JSPredicateForm cloned = pf.clonePF();
        assertNotSame(pf, cloned);
        assertEquals(pf.toStr().toString(), cloned.toStr().toString());
    }

    @Test
    public void testApplySubstitutionPF() {
        JSPredicateForm pf = new JSPredicateForm("(testPredicate)");
        JSSubstitution substitution = new JSSubstitution();
        JSPredicateForm result = pf.applySubstitutionPF(substitution);
        assertNotNull(result);
        assertEquals(pf.toStr().toString(), result.toStr().toString());
    }

    @Test
    public void testMatches() {
        JSPredicateForm pf1 = new JSPredicateForm("(testPredicate)");
        JSPredicateForm pf2 = new JSPredicateForm("(testPredicate)");
        JSSubstitution result = pf1.matches(pf2);
        assertNotNull(result);
        assertFalse(result.fail());
    }

    @Test
    public void testEquals() {
        JSPredicateForm pf1 = new JSPredicateForm("(testPredicate)");
        JSPredicateForm pf2 = new JSPredicateForm("(testPredicate)");
        assertTrue(pf1.equals(pf2));
    }

    @Test
    public void testStandarizerPredicateForm() {
        JSPredicateForm pf = new JSPredicateForm("(testPredicate)");
        JSPredicateForm result = pf.standarizerPredicateForm();
        assertNotNull(result);
        assertEquals(pf.toStr().toString(), result.toStr().toString());
    }
}