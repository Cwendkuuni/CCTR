package de.beiri22.stringincrementor;

import static org.junit.Assert.*;
import org.junit.Test;
import de.beiri22.stringincrementor.StringIncrementor;
import de.beiri22.stringincrementor.relativestring.RelativeString;
import de.beiri22.stringincrementor.relativestring.StringLink;

public class StringIncrementorTest {

    @Test
    public void testDiffWithIdenticalStrings() {
        String a = "Hello, World!";
        String b = "Hello, World!";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals(0, result.linksCount());
        assertEquals("", result.getAbsolute());
    }

    @Test
    public void testDiffWithDifferentStrings() {
        String a = "Hello, World!";
        String b = "Hello, Java!";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals(1, result.linksCount());
        assertEquals("Java!", result.getAbsolute());
    }

    @Test
    public void testDiffWithEmptyStrings() {
        String a = "";
        String b = "";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals(0, result.linksCount());
        assertEquals("", result.getAbsolute());
    }

    @Test
    public void testDiffWithOneEmptyString() {
        String a = "Hello, World!";
        String b = "";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals(0, result.linksCount());
        assertEquals("", result.getAbsolute());
    }

    @Test
    public void testPatchWithIdenticalStrings() {
        String a = "Hello, World!";
        RelativeString r = StringIncrementor.diff(a, a);
        String result = StringIncrementor.patch(a, r);
        assertEquals(a, result);
    }

    @Test
    public void testPatchWithDifferentStrings() {
        String a = "Hello, World!";
        String b = "Hello, Java!";
        RelativeString r = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);
    }

    @Test
    public void testPatchWithEmptyStrings() {
        String a = "";
        RelativeString r = StringIncrementor.diff(a, a);
        String result = StringIncrementor.patch(a, r);
        assertEquals(a, result);
    }

    @Test
    public void testPatchWithOneEmptyString() {
        String a = "Hello, World!";
        String b = "";
        RelativeString r = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);
    }

    @Test
    public void testDiffWithVerbose() {
        String a = "Hello, World!";
        String b = "Hello, Java!";
        RelativeString result = StringIncrementor.diff(a, b, true);
        assertEquals(1, result.linksCount());
        assertEquals("Java!", result.getAbsolute());
    }

    @Test
    public void testPatchWithVerbose() {
        String a = "Hello, World!";
        String b = "Hello, Java!";
        RelativeString r = StringIncrementor.diff(a, b, true);
        String result = StringIncrementor.patch(a, r, true);
        assertEquals(b, result);
    }
}