package de.beiri22.stringincrementor;

import de.beiri22.stringincrementor.helper.*;
import de.beiri22.stringincrementor.relativestring.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class StringIncrementorTest {

    @Test
    public void testDiff() {
        String a = "abcdefghijklmnopqrstuvwxyz";
        String b = "abcdefghijklmnopqrstuvwxyz12345";

        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(0, result.linksCount());
        assertEquals("12345", result.getAbsolute());

        result = StringIncrementor.diff(a, b, true);
        assertNotNull(result);
        assertEquals(0, result.linksCount());
        assertEquals("12345", result.getAbsolute());
    }

    @Test
    public void testDiffWithCommonSubstring() {
        String a = "abcdefghijklmnopqrstuvwxyz";
        String b = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";

        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        assertEquals("", result.getAbsolute());

        result = StringIncrementor.diff(a, b, true);
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        assertEquals("", result.getAbsolute());
    }

    @Test
    public void testPatch() {
        String a = "abcdefghijklmnopqrstuvwxyz";
        String b = "abcdefghijklmnopqrstuvwxyz12345";
        RelativeString r = StringIncrementor.diff(a, b);

        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);

        result = StringIncrementor.patch(a, r, true);
        assertEquals(b, result);
    }

    @Test
    public void testPatchWithCommonSubstring() {
        String a = "abcdefghijklmnopqrstuvwxyz";
        String b = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
        RelativeString r = StringIncrementor.diff(a, b);

        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);

        result = StringIncrementor.patch(a, r, true);
        assertEquals(b, result);
    }

    @Test
    public void testDiffWithEmptyStrings() {
        String a = "";
        String b = "";

        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(0, result.linksCount());
        assertEquals("", result.getAbsolute());

        result = StringIncrementor.diff(a, b, true);
        assertNotNull(result);
        assertEquals(0, result.linksCount());
        assertEquals("", result.getAbsolute());
    }

    @Test
    public void testPatchWithEmptyStrings() {
        String a = "";
        String b = "";
        RelativeString r = StringIncrementor.diff(a, b);

        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);

        result = StringIncrementor.patch(a, r, true);
        assertEquals(b, result);
    }
}