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
    public void testAddElementsToState() {
        jsListLogicalAtoms.addElement(new JSPredicateForm("atom1"));
        jsListLogicalAtoms.addElement(new JSPredicateForm("atom2"));

        jsState.addElementsToState(jsListLogicalAtoms);

        assertEquals(2, jsState.size());
        assertTrue(jsState.contains(new JSPredicateForm("atom1")));
        assertTrue(jsState.contains(new JSPredicateForm("atom2")));
    }

    @Test
    public void testApply() {
        JSState result = jsState.apply(jsPlan);
        assertNotNull(result);
    }

    @Test
    public void testApplyOp() {
        JSTState result = jsState.applyOp(jsOperator, jsSubstitution, jsListLogicalAtoms, jsListLogicalAtoms);
        assertNotNull(result);
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

    @Test
    public void testConstructorWithTokenizer() throws Exception {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader("test"));
        JSState state = new JSState(tokenizer);
        assertNotNull(state);
    }
}