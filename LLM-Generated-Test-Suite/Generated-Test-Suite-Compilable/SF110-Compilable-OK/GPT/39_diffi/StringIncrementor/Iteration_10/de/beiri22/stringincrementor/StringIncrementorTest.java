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
        assertNotNull(result);
        assertEquals(0, result.getAbsolute().length());
        assertEquals(1, result.linksCount());
        StringLink link = result.getLink(0);
        assertEquals(0, link.getPosOrig());
        assertEquals(0, link.getPosNew());
        assertEquals(13, link.getLen());
    }

    @Test
    public void testDiffWithDifference() {
        String a = "abcdefghijklm";
        String b = "abcxyzghijklm";
        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals("xyz", result.getAbsolute());
        assertEquals(2, result.linksCount());
        StringLink link1 = result.getLink(0);
        assertEquals(0, link1.getPosOrig());
        assertEquals(0, link1.getPosNew());
        assertEquals(3, link1.getLen());
        StringLink link2 = result.getLink(1);
        assertEquals(6, link2.getPosOrig());
        assertEquals(6, link2.getPosNew());
        assertEquals(7, link2.getLen());
    }

    @Test
    public void testDiffVerbose() {
        String a = "abcdefghijklm";
        String b = "abcxyzghijklm";
        RelativeString result = StringIncrementor.diff(a, b, true);
        assertNotNull(result);
        assertEquals("xyz", result.getAbsolute());
        assertEquals(2, result.linksCount());
    }

    @Test
    public void testPatchBasic() {
        String a = "abcdefghijklm";
        RelativeString r = new RelativeString();
        r.addLink(new StringLink(0, 0, 13));
        String result = StringIncrementor.patch(a, r);
        assertEquals(a, result);
    }

    @Test
    public void testPatchWithDifference() {
        String a = "abcdefghijklm";
        RelativeString r = new RelativeString();
        r.addLink(new StringLink(0, 0, 3));
        r.setAbsolute("xyz");
        r.addLink(new StringLink(6, 6, 7));
        String result = StringIncrementor.patch(a, r);
        assertEquals("abcxyzghijklm", result);
    }

    @Test
    public void testPatchVerbose() {
        String a = "abcdefghijklm";
        RelativeString r = new RelativeString();
        r.addLink(new StringLink(0, 0, 3));
        r.setAbsolute("xyz");
        r.addLink(new StringLink(6, 6, 7));
        String result = StringIncrementor.patch(a, r, true);
        assertEquals("abcxyzghijklm", result);
    }
}