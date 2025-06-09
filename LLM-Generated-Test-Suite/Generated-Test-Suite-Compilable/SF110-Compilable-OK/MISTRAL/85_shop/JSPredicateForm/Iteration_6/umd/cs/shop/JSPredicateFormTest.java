package umd.cs.shop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringReader;
import java.io.StreamTokenizer;

public class JSPredicateFormTest {

    private JSPredicateForm jspf;

    @Before
    public void setUp() {
        jspf = new JSPredicateForm();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(jspf);
    }

    @Test
    public void testConstructorWithString() {
        String text = "(not (predicate))";
        JSPredicateForm jspfWithText = new JSPredicateForm(text);
        assertNotNull(jspfWithText);
    }

    @Test
    public void testConstructorWithStreamTokenizer() {
        String text = "(not (predicate))";
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(text));
        JSPredicateForm jspfWithTokenizer = new JSPredicateForm(tokenizer);
        assertNotNull(jspfWithTokenizer);
    }

    @Test(expected = JSParserError.class)
    public void testJSPredicateFormInitWithNullTokenizer() {
        jspf.JSPredicateFormInit(null);
    }

    @Test(expected = JSParserError.class)
    public void testJSPredicateFormInitWithInvalidToken() {
        String text = "invalid";
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(text));
        jspf.JSPredicateFormInit(tokenizer);
    }

    @Test
    public void testPrint() {
        String text = "(not (predicate))";
        JSPredicateForm jspfWithText = new JSPredicateForm(text);
        jspfWithText.print(); // This will print to the console, so we can't assert the output directly
    }

    @Test
    public void testToStr() {
        String text = "(not (predicate))";
        JSPredicateForm jspfWithText = new JSPredicateForm(text);
        StringBuffer result = jspfWithText.toStr();
        assertEquals("(not (predicate))", result.toString());
    }

    @Test
    public void testClonePF() {
        String text = "(not (predicate))";
        JSPredicateForm jspfWithText = new JSPredicateForm(text);
        JSPredicateForm cloned = jspfWithText.clonePF();
        assertNotNull(cloned);
        assertEquals(jspfWithText.toStr().toString(), cloned.toStr().toString());
    }

    @Test
    public void testApplySubstitutionPF() {
        String text = "(not (predicate))";
        JSPredicateForm jspfWithText = new JSPredicateForm(text);
        JSSubstitution alpha = new JSSubstitution();
        JSPredicateForm result = jspfWithText.applySubstitutionPF(alpha);
        assertNotNull(result);
        assertEquals(jspfWithText.toStr().toString(), result.toStr().toString());
    }

    @Test
    public void testMatches() {
        String text1 = "(not (predicate))";
        String text2 = "(not (predicate))";
        JSPredicateForm jspf1 = new JSPredicateForm(text1);
        JSPredicateForm jspf2 = new JSPredicateForm(text2);
        JSSubstitution result = jspf1.matches(jspf2);
        assertNotNull(result);
        assertFalse(result.fail());
    }

    @Test
    public void testMatchesWithSubstitution() {
        String text1 = "(not (predicate))";
        String text2 = "(not (predicate))";
        JSPredicateForm jspf1 = new JSPredicateForm(text1);
        JSPredicateForm jspf2 = new JSPredicateForm(text2);
        JSSubstitution alpha = new JSSubstitution();
        JSSubstitution result = jspf1.matches(jspf2, alpha);
        assertNotNull(result);
        assertFalse(result.fail());
    }

    @Test
    public void testEquals() {
        String text1 = "(not (predicate))";
        String text2 = "(not (predicate))";
        JSPredicateForm jspf1 = new JSPredicateForm(text1);
        JSPredicateForm jspf2 = new JSPredicateForm(text2);
        assertTrue(jspf1.equals(jspf2));
    }

    @Test
    public void testStandarizerPredicateForm() {
        String text = "(not (predicate))";
        JSPredicateForm jspfWithText = new JSPredicateForm(text);
        JSPredicateForm standardized = jspfWithText.standarizerPredicateForm();
        assertNotNull(standardized);
        assertEquals(jspfWithText.toStr().toString(), standardized.toStr().toString());
    }
}