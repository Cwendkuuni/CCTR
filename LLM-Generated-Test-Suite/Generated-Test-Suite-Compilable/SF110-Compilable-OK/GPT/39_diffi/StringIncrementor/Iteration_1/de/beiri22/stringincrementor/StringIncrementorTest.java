package de.beiri22.stringincrementor;

import static org.junit.Assert.*;
import org.junit.Test;
import de.beiri22.stringincrementor.StringIncrementor;
import de.beiri22.stringincrementor.relativestring.RelativeString;

public class StringIncrementorTest {

    @Test
    public void testDiffWithIdenticalStrings() {
        String a = "HelloWorld";
        String b = "HelloWorld";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals("", result.getAbsolute());
        assertEquals(1, result.linksCount());
        assertEquals(0, result.getLink(0).getPosOrig());
        assertEquals(0, result.getLink(0).getPosNew());
        assertEquals(a.length(), result.getLink(0).getLen());
    }

    @Test
    public void testDiffWithDifferentStrings() {
        String a = "HelloWorld";
        String b = "HelloJava";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals("Java", result.getAbsolute());
        assertEquals(1, result.linksCount());
        assertEquals(0, result.getLink(0).getPosOrig());
        assertEquals(0, result.getLink(0).getPosNew());
        assertEquals(5, result.getLink(0).getLen());
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
    public void testDiffWithVerbose() {
        String a = "HelloWorld";
        String b = "HelloJava";
        RelativeString result = StringIncrementor.diff(a, b, true);
        assertEquals("Java", result.getAbsolute());
        assertEquals(1, result.linksCount());
        assertEquals(0, result.getLink(0).getPosOrig());
        assertEquals(0, result.getLink(0).getPosNew());
        assertEquals(5, result.getLink(0).getLen());
    }

    @Test
    public void testPatchWithIdenticalStrings() {
        String a = "HelloWorld";
        RelativeString r = StringIncrementor.diff(a, a);
        String result = StringIncrementor.patch(a, r);
        assertEquals(a, result);
    }

    @Test
    public void testPatchWithDifferentStrings() {
        String a = "HelloWorld";
        String b = "HelloJava";
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
    public void testPatchWithVerbose() {
        String a = "HelloWorld";
        String b = "HelloJava";
        RelativeString r = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, r, true);
        assertEquals(b, result);
    }
}