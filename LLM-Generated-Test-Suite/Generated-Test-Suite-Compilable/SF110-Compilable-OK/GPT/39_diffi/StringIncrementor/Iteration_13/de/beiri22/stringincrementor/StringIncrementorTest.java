package de.beiri22.stringincrementor;

import de.beiri22.stringincrementor.StringIncrementor;
import de.beiri22.stringincrementor.helper.ExtBoolArray;
import de.beiri22.stringincrementor.relativestring.RelativeString;
import de.beiri22.stringincrementor.relativestring.StringLink;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringIncrementorTest {

    private String a;
    private String b;

    @Before
    public void setUp() {
        a = "The quick brown fox jumps over the lazy dog";
        b = "The quick brown fox jumps over the lazy dog and runs away";
    }

    @Test
    public void testDiffBasic() {
        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertTrue(result.linksCount() > 0);
    }

    @Test
    public void testDiffWithVerbose() {
        RelativeString result = StringIncrementor.diff(a, b, true);
        assertNotNull(result);
        assertTrue(result.linksCount() > 0);
    }

    @Test
    public void testDiffIdenticalStrings() {
        RelativeString result = StringIncrementor.diff(a, a);
        assertNotNull(result);
        assertEquals(0, result.getAbsolute().length());
    }

    @Test
    public void testDiffEmptyStrings() {
        RelativeString result = StringIncrementor.diff("", "");
        assertNotNull(result);
        assertEquals(0, result.linksCount());
        assertEquals("", result.getAbsolute());
    }

    @Test
    public void testPatchBasic() {
        RelativeString diffResult = StringIncrementor.diff(a, b);
        String patchedString = StringIncrementor.patch(a, diffResult);
        assertEquals(b, patchedString);
    }

    @Test
    public void testPatchWithVerbose() {
        RelativeString diffResult = StringIncrementor.diff(a, b);
        String patchedString = StringIncrementor.patch(a, diffResult, true);
        assertEquals(b, patchedString);
    }

    @Test
    public void testPatchWithEmptyRelativeString() {
        RelativeString emptyRelativeString = new RelativeString();
        String patchedString = StringIncrementor.patch(a, emptyRelativeString);
        assertEquals(a, patchedString);
    }

    @Test
    public void testPatchWithIdenticalStrings() {
        RelativeString diffResult = StringIncrementor.diff(a, a);
        String patchedString = StringIncrementor.patch(a, diffResult);
        assertEquals(a, patchedString);
    }

    @Test
    public void testPatchWithEmptyOriginalString() {
        RelativeString diffResult = StringIncrementor.diff("", b);
        String patchedString = StringIncrementor.patch("", diffResult);
        assertEquals(b, patchedString);
    }
}