package umd.cs.shop;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.StringReader;
import java.io.StreamTokenizer;

public class JSPredicateFormTest {

    @Test
    public void testDefaultConstructor() {
        JSPredicateForm pf = new JSPredicateForm();
        assertNotNull(pf);
        assertEquals(0, pf.size());
    }

    @Test
    public void testStringConstructor() {
        JSPredicateForm pf = new JSPredicateForm("(not (predicate))");
        assertNotNull(pf);
        assertEquals(2, pf.size());
    }

    @Test(expected = JSParserError.class)
    public void testStringConstructorWithInvalidInput() {
        new JSPredicateForm("invalid input");
    }

    @Test
    public void testStreamTokenizerConstructor() {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader("(not (predicate))"));
        JSPredicateForm pf = new JSPredicateForm(tokenizer);
        assertNotNull(pf);
        assertEquals(2, pf.size());
    }

    @Test(expected = JSParserError.class)
    public void testStreamTokenizerConstructorWithNull() {
        new JSPredicateForm((StreamTokenizer) null);
    }

    @Test
    public void testPrint() {
        JSPredicateForm pf = new JSPredicateForm("(not (predicate))");
        pf.print(); // This should print to the console, verify manually
    }

    @Test
    public void testToStr() {
        JSPredicateForm pf = new JSPredicateForm("(not (predicate))");
        StringBuffer result = pf.toStr();
        assertEquals("(not(predicate))", result.toString());
    }

    @Test
    public void testClonePF() {
        JSPredicateForm pf = new JSPredicateForm("(not (predicate))");
        JSPredicateForm clone = pf.clonePF();
        assertNotSame(pf, clone);
        assertEquals(pf, clone);
    }

    @Test
    public void testApplySubstitutionPF() {
        JSPredicateForm pf = new JSPredicateForm("(predicate)");
        JSSubstitution substitution = new JSSubstitution();
        JSPredicateForm result = pf.applySubstitutionPF(substitution);
        assertNotNull(result);
        assertEquals(pf, result); // Assuming no substitution changes
    }

    @Test
    public void testMatches() {
        JSPredicateForm pf1 = new JSPredicateForm("(predicate)");
        JSPredicateForm pf2 = new JSPredicateForm("(predicate)");
        JSSubstitution result = pf1.matches(pf2);
        assertNotNull(result);
        assertFalse(result.fail());
    }

    @Test
    public void testEquals() {
        JSPredicateForm pf1 = new JSPredicateForm("(predicate)");
        JSPredicateForm pf2 = new JSPredicateForm("(predicate)");
        assertTrue(pf1.equals(pf2));
    }

    @Test
    public void testStandarizerPredicateForm() {
        JSPredicateForm pf = new JSPredicateForm("(predicate)");
        JSPredicateForm result = pf.standarizerPredicateForm();
        assertNotNull(result);
        assertEquals(pf, result); // Assuming no changes in standardization
    }
}