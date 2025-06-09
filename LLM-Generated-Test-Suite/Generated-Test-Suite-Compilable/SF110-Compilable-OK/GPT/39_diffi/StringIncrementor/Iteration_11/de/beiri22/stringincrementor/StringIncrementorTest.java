package de.beiri22.stringincrementor;

import static org.junit.Assert.*;
import org.junit.Test;
import de.beiri22.stringincrementor.StringIncrementor;
import de.beiri22.stringincrementor.relativestring.RelativeString;

public class StringIncrementorTest {

    @Test
    public void testDiffWithIdenticalStrings() {
        String a = "Hello, World!";
        String b = "Hello, World!";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals("", result.getAbsolute());
        assertEquals(1, result.linksCount());
        assertEquals(0, result.getLink(0).getPosOrig());
        assertEquals(0, result.getLink(0).getPosNew());
        assertEquals(a.length(), result.getLink(0).getLen());
    }

    @Test
    public void testDiffWithCompletelyDifferentStrings() {
        String a = "Hello, World!";
        String b = "Goodbye, World!";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals("Goodbye, World!", result.getAbsolute());
        assertEquals(0, result.linksCount());
    }

    @Test
    public void testDiffWithPartialOverlap() {
        String a = "Hello, World!";
        String b = "Hello, Universe!";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals(" Universe!", result.getAbsolute());
        assertEquals(1, result.linksCount());
        assertEquals(0, result.getLink(0).getPosOrig());
        assertEquals(0, result.getLink(0).getPosNew());
        assertEquals(7, result.getLink(0).getLen());
    }

    @Test
    public void testPatchWithIdenticalStrings() {
        String a = "Hello, World!";
        RelativeString r = StringIncrementor.diff(a, a);
        String result = StringIncrementor.patch(a, r);
        assertEquals(a, result);
    }

    @Test
    public void testPatchWithCompletelyDifferentStrings() {
        String a = "Hello, World!";
        String b = "Goodbye, World!";
        RelativeString r = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);
    }

    @Test
    public void testPatchWithPartialOverlap() {
        String a = "Hello, World!";
        String b = "Hello, Universe!";
        RelativeString r = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);
    }

    @Test
    public void testDiffWithEmptyStrings() {
        String a = "";
        String b = "";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals("", result.getAbsolute());
        assertEquals(0, result.linksCount());
    }

    @Test
    public void testPatchWithEmptyStrings() {
        String a = "";
        RelativeString r = StringIncrementor.diff(a, a);
        String result = StringIncrementor.patch(a, r);
        assertEquals(a, result);
    }

    @Test
    public void testDiffWithVerbose() {
        String a = "Hello, World!";
        String b = "Hello, Universe!";
        RelativeString result = StringIncrementor.diff(a, b, true);
        assertEquals(" Universe!", result.getAbsolute());
        assertEquals(1, result.linksCount());
        assertEquals(0, result.getLink(0).getPosOrig());
        assertEquals(0, result.getLink(0).getPosNew());
        assertEquals(7, result.getLink(0).getLen());
    }

    @Test
    public void testPatchWithVerbose() {
        String a = "Hello, World!";
        String b = "Hello, Universe!";
        RelativeString r = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, r, true);
        assertEquals(b, result);
    }
}