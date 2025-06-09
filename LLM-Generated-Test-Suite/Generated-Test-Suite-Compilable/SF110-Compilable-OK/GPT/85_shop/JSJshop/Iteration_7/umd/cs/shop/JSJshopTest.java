package umd.cs.shop;

import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class JSJshopTest {

    private JSJshop jsJshop;
    private JSPlanningDomain mockDomain;
    private JSPlanningProblem mockProblem;
    private JSListPlanningProblem mockProbSet;
    private JSPlan mockPlan;
    private JSJshopNode mockTree;
    private JSPairPlanTSListNodes mockSolution;
    private JSTaskAtom mockTaskAtom;

    @Before
    public void setUp() {
        mockDomain = mock(JSPlanningDomain.class);
        mockProblem = mock(JSPlanningProblem.class);
        mockProbSet = mock(JSListPlanningProblem.class);
        mockPlan = mock(JSPlan.class);
        mockTree = mock(JSJshopNode.class);
        mockSolution = mock(JSPairPlanTSListNodes.class);
        mockTaskAtom = mock(JSTaskAtom.class);

        jsJshop = new JSJshop();
    }

    @Test
    public void testConstructorWithDomainAndProblemFiles() {
        JSJshop jsJshop = new JSJshop("domainFile", "problemFile");
        assertNotNull(jsJshop);
    }

    @Test
    public void testConstructorWithFileAndTaskAtom() {
        JSJshop jsJshop = new JSJshop("file", mockTaskAtom);
        assertNotNull(jsJshop);
    }

    @Test
    public void testGetTree() {
        assertNull(jsJshop.getTree());
    }

    @Test
    public void testGetSolution() {
        assertNull(jsJshop.getSolution());
    }

    @Test
    public void testGetAddList() {
        assertNull(jsJshop.getAddList());
    }

    @Test
    public void testGetDeleteList() {
        assertNull(jsJshop.getDeleteList());
    }

    @Test
    public void testSetFile() {
        JSPairPlanTSListNodes result = jsJshop.setFile("file", mockTaskAtom);
        assertNull(result);
    }

    @Test
    public void testParserFile() {
        boolean result = jsJshop.parserFile("libraryFile");
        assertFalse(result);
    }

    @Test
    public void testGetBufferedReader() {
        BufferedReader br = jsJshop.getBufferedReader("dir", "file");
        assertNull(br);
    }

    @Test
    public void testGetBufferedReaderWithApplet() {
        BufferedReader br = jsJshop.getBufferedReader("dir", "file", new JApplet());
        assertNull(br);
    }

    @Test
    public void testGetAppletURL() {
        URL url = jsJshop.getAppletURL("file", new JApplet());
        assertNull(url);
    }

    @Test
    public void testProcessToken() {
        StreamTokenizer tokenizer = mock(StreamTokenizer.class);
        when(tokenizer.ttype).thenReturn(JSJshopVars.leftPar);
        when(tokenizer.sval).thenReturn("defdomain");

        try {
            jsJshop.processToken(tokenizer);
        } catch (JSParserError e) {
            fail("JSParserError should not be thrown");
        }
    }

    @Test
    public void testDom() {
        assertNull(jsJshop.dom());
    }

    @Test
    public void testProb() {
        assertNull(jsJshop.prob());
    }

    @Test
    public void testSol() {
        assertNull(jsJshop.sol());
    }

    @Test
    public void testTree() {
        assertNull(jsJshop.tree());
    }
}