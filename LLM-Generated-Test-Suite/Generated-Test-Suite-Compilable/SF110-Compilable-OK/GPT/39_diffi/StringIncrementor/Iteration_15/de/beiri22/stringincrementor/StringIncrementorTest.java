package de.beiri22.stringincrementor;

import static org.junit.Assert.*;
import org.junit.Test;
import de.beiri22.stringincrementor.StringIncrementor;
import de.beiri22.stringincrementor.relativestring.RelativeString;
import de.beiri22.stringincrementor.relativestring.StringLink;

public class StringIncrementorTest {

    @Test
    public void testDiffBasic() {
        String a = "Hello, this is a test string.";
        String b = "Hello, this is a test string.";
        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(0, result.getAbsolute().length());
        assertEquals(1, result.linksCount());
        StringLink link = result.getLink(0);
        assertEquals(0, link.getPosOrig());
        assertEquals(0, link.getPosNew());
        assertEquals(a.length(), link.getLen());
    }

    @Test
    public void testDiffWithChanges() {
        String a = "Hello, this is a test string.";
        String b = "Hello, this is a different test string.";
        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertTrue(result.getAbsolute().contains("different"));
        assertTrue(result.linksCount() > 0);
    }

    @Test
    public void testDiffVerbose() {
        String a = "Hello, this is a test string.";
        String b = "Hello, this is a different test string.";
        RelativeString result = StringIncrementor.diff(a, b, true);
        assertNotNull(result);
        assertTrue(result.getAbsolute().contains("different"));
        assertTrue(result.linksCount() > 0);
    }

    @Test
    public void testPatchBasic() {
        String a = "Hello, this is a test string.";
        String b = "Hello, this is a test string.";
        RelativeString diff = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, diff);
        assertEquals(b, result);
    }

    @Test
    public void testPatchWithChanges() {
        String a = "Hello, this is a test string.";
        String b = "Hello, this is a different test string.";
        RelativeString diff = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, diff);
        assertEquals(b, result);
    }

    @Test
    public void testPatchVerbose() {
        String a = "Hello, this is a test string.";
        String b = "Hello, this is a different test string.";
        RelativeString diff = StringIncrementor.diff(a, b, true);
        String result = StringIncrementor.patch(a, diff, true);
        assertEquals(b, result);
    }

    @Test
    public void testDiffEmptyStrings() {
        String a = "";
        String b = "";
        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(0, result.getAbsolute().length());
        assertEquals(0, result.linksCount());
    }

    @Test
    public void testPatchEmptyStrings() {
        String a = "";
        String b = "";
        RelativeString diff = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, diff);
        assertEquals(b, result);
    }

    @Test
    public void testDiffWithNull() {
        try {
            StringIncrementor.diff(null, null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    @Test
    public void testPatchWithNull() {
        try {
            StringIncrementor.patch(null, null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}