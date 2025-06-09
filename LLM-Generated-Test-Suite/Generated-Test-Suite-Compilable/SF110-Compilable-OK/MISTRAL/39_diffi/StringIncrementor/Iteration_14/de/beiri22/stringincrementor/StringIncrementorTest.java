package de.beiri22.stringincrementor;

import de.beiri22.stringincrementor.helper.ExtBoolArray;
import de.beiri22.stringincrementor.helper.IndexedString;
import de.beiri22.stringincrementor.relativestring.RelativeString;
import de.beiri22.stringincrementor.relativestring.StringLink;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StringIncrementorTest {

    private String a;
    private String b;
    private RelativeString relativeString;

    @Before
    public void setUp() {
        a = "abcdefghijklmnopqrstuvwxyz";
        b = "abcdefghijklmnopqrstuvwxyz12345";
        relativeString = new RelativeString();
        relativeString.addLink(new StringLink(0, 0, 26));
        relativeString.setAbsolute("12345");
    }

    @Test
    public void testDiff() {
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals(1, result.linksCount());
        assertEquals("12345", result.getAbsolute());
    }

    @Test
    public void testDiffVerbose() {
        RelativeString result = StringIncrementor.diff(a, b, true);
        assertEquals(1, result.linksCount());
        assertEquals("12345", result.getAbsolute());
    }

    @Test
    public void testPatch() {
        String result = StringIncrementor.patch(a, relativeString);
        assertEquals(b, result);
    }

    @Test
    public void testPatchVerbose() {
        String result = StringIncrementor.patch(a, relativeString, true);
        assertEquals(b, result);
    }

    @Test
    public void testDiffWithNoChanges() {
        RelativeString result = StringIncrementor.diff(a, a);
        assertEquals(0, result.linksCount());
        assertEquals("", result.getAbsolute());
    }

    @Test
    public void testPatchWithNoChanges() {
        RelativeString noChangeRelativeString = new RelativeString();
        noChangeRelativeString.addLink(new StringLink(0, 0, a.length()));
        noChangeRelativeString.setAbsolute("");
        String result = StringIncrementor.patch(a, noChangeRelativeString);
        assertEquals(a, result);
    }

    @Test
    public void testDiffWithEmptyStrings() {
        RelativeString result = StringIncrementor.diff("", "");
        assertEquals(0, result.linksCount());
        assertEquals("", result.getAbsolute());
    }

    @Test
    public void testPatchWithEmptyStrings() {
        RelativeString emptyRelativeString = new RelativeString();
        emptyRelativeString.setAbsolute("");
        String result = StringIncrementor.patch("", emptyRelativeString);
        assertEquals("", result);
    }

    @Test
    public void testDiffWithDifferentStrings() {
        String a = "hello";
        String b = "world";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals(0, result.linksCount());
        assertEquals("world", result.getAbsolute());
    }

    @Test
    public void testPatchWithDifferentStrings() {
        String a = "hello";
        String b = "world";
        RelativeString relativeString = new RelativeString();
        relativeString.setAbsolute(b);
        String result = StringIncrementor.patch(a, relativeString);
        assertEquals(b, result);
    }
}