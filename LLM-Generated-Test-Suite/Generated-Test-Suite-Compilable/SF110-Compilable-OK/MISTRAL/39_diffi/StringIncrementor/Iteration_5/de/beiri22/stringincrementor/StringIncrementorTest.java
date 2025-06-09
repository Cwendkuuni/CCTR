package de.beiri22.stringincrementor;

import de.beiri22.stringincrementor.helper.*;
import de.beiri22.stringincrementor.relativestring.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class StringIncrementorTest {

    @Test
    public void testDiff() {
        String a = "abcdef";
        String b = "abcxyz";
        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        assertEquals("xyz", result.getAbsolute());
    }

    @Test
    public void testDiffVerbose() {
        String a = "abcdef";
        String b = "abcxyz";
        RelativeString result = StringIncrementor.diff(a, b, true);
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        assertEquals("xyz", result.getAbsolute());
    }

    @Test
    public void testPatch() {
        String a = "abcdef";
        String b = "abcxyz";
        RelativeString r = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);
    }

    @Test
    public void testPatchVerbose() {
        String a = "abcdef";
        String b = "abcxyz";
        RelativeString r = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, r, true);
        assertEquals(b, result);
    }

    @Test
    public void testDiffEmptyStrings() {
        String a = "";
        String b = "";
        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(0, result.linksCount());
        assertEquals("", result.getAbsolute());
    }

    @Test
    public void testPatchEmptyStrings() {
        String a = "";
        String b = "";
        RelativeString r = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);
    }

    @Test
    public void testDiffSameStrings() {
        String a = "abcdef";
        String b = "abcdef";
        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        assertEquals("", result.getAbsolute());
    }

    @Test
    public void testPatchSameStrings() {
        String a = "abcdef";
        String b = "abcdef";
        RelativeString r = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);
    }

    @Test
    public void testDiffDifferentLengths() {
        String a = "abcdef";
        String b = "abcxyz123";
        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        assertEquals("xyz123", result.getAbsolute());
    }

    @Test
    public void testPatchDifferentLengths() {
        String a = "abcdef";
        String b = "abcxyz123";
        RelativeString r = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);
    }
}