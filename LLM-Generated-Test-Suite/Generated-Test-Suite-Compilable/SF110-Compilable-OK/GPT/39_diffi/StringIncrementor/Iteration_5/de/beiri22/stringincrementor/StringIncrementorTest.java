package de.beiri22.stringincrementor;

import static org.junit.Assert.*;
import org.junit.Test;
import de.beiri22.stringincrementor.StringIncrementor;
import de.beiri22.stringincrementor.relativestring.RelativeString;
import de.beiri22.stringincrementor.relativestring.StringLink;

public class StringIncrementorTest {

    @Test
    public void testDiffBasic() {
        String a = "abcdefghijklm";
        String b = "abcdefghijklm";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals(0, result.linksCount());
        assertEquals("", result.getAbsolute());
    }

    @Test
    public void testDiffWithDifference() {
        String a = "abcdefghijklm";
        String b = "abcxyzghijklm";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals(2, result.linksCount());
        assertEquals("xyz", result.getAbsolute());
    }

    @Test
    public void testDiffVerbose() {
        String a = "abcdefghijklm";
        String b = "abcxyzghijklm";
        RelativeString result = StringIncrementor.diff(a, b, true);
        assertEquals(2, result.linksCount());
        assertEquals("xyz", result.getAbsolute());
    }

    @Test
    public void testPatchBasic() {
        String a = "abcdefghijklm";
        RelativeString r = new RelativeString();
        r.setAbsolute("");
        String result = StringIncrementor.patch(a, r);
        assertEquals("abcdefghijklm", result);
    }

    @Test
    public void testPatchWithDifference() {
        String a = "abcdefghijklm";
        RelativeString r = new RelativeString();
        r.setAbsolute("xyz");
        r.addLink(new StringLink(0, 0, 3));
        r.addLink(new StringLink(6, 6, 7));
        String result = StringIncrementor.patch(a, r);
        assertEquals("abcxyzghijklm", result);
    }

    @Test
    public void testPatchVerbose() {
        String a = "abcdefghijklm";
        RelativeString r = new RelativeString();
        r.setAbsolute("xyz");
        r.addLink(new StringLink(0, 0, 3));
        r.addLink(new StringLink(6, 6, 7));
        String result = StringIncrementor.patch(a, r, true);
        assertEquals("abcxyzghijklm", result);
    }

    @Test
    public void testDiffAndPatch() {
        String a = "abcdefghijklm";
        String b = "abcxyzghijklm";
        RelativeString diffResult = StringIncrementor.diff(a, b);
        String patchResult = StringIncrementor.patch(a, diffResult);
        assertEquals(b, patchResult);
    }
}