package umd.cs.shop;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.StreamTokenizer;
import java.io.StringReader;

public class JSStateTest {

    private JSState jsState;
    private JSListLogicalAtoms jsListLogicalAtoms;
    private JSPlan jsPlan;
    private JSOperator jsOperator;
    private JSSubstitution jsSubstitution;
    private JSListAxioms jsListAxioms;
    private JSPredicateForm jsPredicateForm;

    @Before
    public void setUp() {
        jsState = new JSState();
        jsListLogicalAtoms = new JSListLogicalAtoms();
        jsPlan = new JSPlan();
        jsOperator = new JSOperator();
        jsSubstitution = new JSSubstitution();
        jsListAxioms = new JSListAxioms();
        jsPredicateForm = new JSPredicateForm();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(jsState);
    }

    @Test
    public void testConstructorWithTokenizer() {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader("test"));
        JSState jsStateWithTokenizer = new JSState(tokenizer);
        assertNotNull(jsStateWithTokenizer);
    }

    @Test
    public void testAddElementsToState() {
        jsListLogicalAtoms.addElement(new JSPredicateForm());
        jsState.addElementsToState(jsListLogicalAtoms);
        assertEquals(1, jsState.size());
    }

    @Test
    public void testApply() {
        JSState newState = jsState.apply(jsPlan);
        assertNotNull(newState);
    }

    @Test
    public void testApplyOp() {
        JSTState newState = jsState.applyOp(jsOperator, jsSubstitution, jsListLogicalAtoms, jsListLogicalAtoms);
        assertNotNull(newState);
    }

    @Test
    public void testSatisfies() {
        JSSubstitution result = jsState.satisfies(jsListLogicalAtoms, jsSubstitution, jsListAxioms);
        assertNotNull(result);
    }

    @Test
    public void testSatisfiesAll() {
        JSListSubstitution result = jsState.satisfiesAll(jsListLogicalAtoms, jsSubstitution, jsListAxioms);
        assertNotNull(result);
    }

    @Test
    public void testSatisfiesTAm() {
        JSListSubstitution result = jsState.satisfiesTAm(jsPredicateForm, jsSubstitution);
        assertNotNull(result);
    }
}