package de.beiri22.stringincrementor;

import de.beiri22.stringincrementor.helper.*;
import de.beiri22.stringincrementor.relativestring.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StringIncrementorTest {

    private String a;
    private String b;
    private RelativeString r;

    @Before
    public void setUp() {
        a = "abcdefghijklmnopqrstuvwxyz";
        b = "abcdefghijklmnopqrstuvwxyz1234567890";
        r = new RelativeString();
        r.setAbsolute("1234567890");
        r.addLink(new StringLink(0, 0, 26));
    }

    @Test
    public void testDiff() {
        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        assertEquals("1234567890", result.getAbsolute());
    }

    @Test
    public void testDiffVerbose() {
        RelativeString result = StringIncrementor.diff(a, b, true);
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        assertEquals("1234567890", result.getAbsolute());
    }

    @Test
    public void testPatch() {
        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);
    }

    @Test
    public void testPatchVerbose() {
        String result = StringIncrementor.patch(a, r, true);
        assertEquals(b, result);
    }

    @Test
    public void testDiffEmptyStrings() {
        RelativeString result = StringIncrementor.diff("", "");
        assertNotNull(result);
        assertEquals(0, result.linksCount());
        assertEquals("", result.getAbsolute());
    }

    @Test
    public void testPatchEmptyStrings() {
        RelativeString emptyR = new RelativeString();
        emptyR.setAbsolute("");
        String result = StringIncrementor.patch("", emptyR);
        assertEquals("", result);
    }

    @Test
    public void testDiffDifferentStrings() {
        String a = "hello";
        String b = "world";
        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(0, result.linksCount());
        assertEquals("world", result.getAbsolute());
    }

    @Test
    public void testPatchDifferentStrings() {
        String a = "hello";
        String b = "world";
        RelativeString r = new RelativeString();
        r.setAbsolute("world");
        String result = StringIncrementor.patch(a, r);
        assertEquals("world", result);
    }
}