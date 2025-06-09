package de.beiri22.stringincrementor;

import de.beiri22.stringincrementor.helper.*;
import de.beiri22.stringincrementor.relativestring.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StringIncrementorTest {

    private String a;
    private String b;
    private RelativeString relativeString;

    @Before
    public void setUp() {
        a = "This is a test string.";
        b = "This is another test string.";
        relativeString = new RelativeString();
    }

    @Test
    public void testDiff() {
        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        // Add more specific assertions based on expected behavior
    }

    @Test
    public void testDiffVerbose() {
        RelativeString result = StringIncrementor.diff(a, b, true);
        assertNotNull(result);
        // Add more specific assertions based on expected behavior
    }

    @Test
    public void testPatch() {
        String result = StringIncrementor.patch(a, relativeString);
        assertNotNull(result);
        // Add more specific assertions based on expected behavior
    }

    @Test
    public void testPatchVerbose() {
        String result = StringIncrementor.patch(a, relativeString, true);
        assertNotNull(result);
        // Add more specific assertions based on expected behavior
    }

    @Test
    public void testDiffWithEmptyStrings() {
        RelativeString result = StringIncrementor.diff("", "");
        assertNotNull(result);
        assertEquals(0, result.linksCount());
        assertEquals("", result.getAbsolute());
    }

    @Test
    public void testDiffWithIdenticalStrings() {
        RelativeString result = StringIncrementor.diff(a, a);
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        assertEquals("", result.getAbsolute());
    }

    @Test
    public void testPatchWithEmptyRelativeString() {
        String result = StringIncrementor.patch(a, new RelativeString());
        assertNotNull(result);
        assertEquals("", result);
    }

    @Test
    public void testPatchWithIdenticalRelativeString() {
        RelativeString rs = new RelativeString();
        rs.addLink(new StringLink(0, 0, a.length()));
        String result = StringIncrementor.patch(a, rs);
        assertNotNull(result);
        assertEquals(a, result);
    }

    @Test
    public void testDiffWithDifferentStrings() {
        RelativeString result = StringIncrementor.diff("abcdef", "abcxyz");
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        assertEquals("xyz", result.getAbsolute());
    }

    @Test
    public void testPatchWithDifferentStrings() {
        RelativeString rs = new RelativeString();
        rs.addLink(new StringLink(0, 0, 3));
        rs.setAbsolute("xyz");
        String result = StringIncrementor.patch("abcdef", rs);
        assertNotNull(result);
        assertEquals("abcxyz", result);
    }
}