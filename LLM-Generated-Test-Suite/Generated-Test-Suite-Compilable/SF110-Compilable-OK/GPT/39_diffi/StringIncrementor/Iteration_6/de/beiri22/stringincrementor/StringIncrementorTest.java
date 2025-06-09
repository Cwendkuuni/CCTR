package de.beiri22.stringincrementor;

import static org.junit.Assert.*;
import org.junit.Test;
import de.beiri22.stringincrementor.StringIncrementor;
import de.beiri22.stringincrementor.relativestring.RelativeString;

public class StringIncrementorTest {

    @Test
    public void testDiffBasic() {
        String a = "Hello World!";
        String b = "Hello World!";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals("", result.getAbsolute());
        assertEquals(1, result.linksCount());
    }

    @Test
    public void testDiffWithDifference() {
        String a = "Hello World!";
        String b = "Hello Java World!";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals("Java ", result.getAbsolute());
        assertEquals(1, result.linksCount());
    }

    @Test
    public void testDiffEmptyStrings() {
        String a = "";
        String b = "";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals("", result.getAbsolute());
        assertEquals(0, result.linksCount());
    }

    @Test
    public void testDiffWithVerbose() {
        String a = "Hello World!";
        String b = "Hello Java World!";
        RelativeString result = StringIncrementor.diff(a, b, true);
        assertEquals("Java ", result.getAbsolute());
        assertEquals(1, result.linksCount());
    }

    @Test
    public void testPatchBasic() {
        String a = "Hello World!";
        RelativeString r = StringIncrementor.diff(a, "Hello World!");
        String result = StringIncrementor.patch(a, r);
        assertEquals("Hello World!", result);
    }

    @Test
    public void testPatchWithDifference() {
        String a = "Hello World!";
        RelativeString r = StringIncrementor.diff(a, "Hello Java World!");
        String result = StringIncrementor.patch(a, r);
        assertEquals("Hello Java World!", result);
    }

    @Test
    public void testPatchEmptyStrings() {
        String a = "";
        RelativeString r = StringIncrementor.diff(a, "");
        String result = StringIncrementor.patch(a, r);
        assertEquals("", result);
    }

    @Test
    public void testPatchWithVerbose() {
        String a = "Hello World!";
        RelativeString r = StringIncrementor.diff(a, "Hello Java World!");
        String result = StringIncrementor.patch(a, r, true);
        assertEquals("Hello Java World!", result);
    }

    @Test
    public void testDiffAndPatchComplex() {
        String a = "The quick brown fox jumps over the lazy dog.";
        String b = "The quick brown fox jumps over the very lazy dog.";
        RelativeString r = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);
    }
}