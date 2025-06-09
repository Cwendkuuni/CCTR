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

        RelativeString resultVerbose = StringIncrementor.diff(a, b, true);
        assertNotNull(resultVerbose);
        assertEquals(0, resultVerbose.linksCount());
        assertEquals("12345", resultVerbose.getAbsolute());
    }

    @Test
    public void testDiffWithLinks() {
        String a = "abcdefghijklmnopqrstuvwxyz";
        String b = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";

        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        StringLink link = result.getLink(0);
        assertEquals(0, link.getPosOrig());
        assertEquals(0, link.getPosNew());
        assertEquals(26, link.getLen());
        assertEquals("", result.getAbsolute());

        RelativeString resultVerbose = StringIncrementor.diff(a, b, true);
        assertNotNull(resultVerbose);
        assertEquals(1, resultVerbose.linksCount());
        StringLink linkVerbose = resultVerbose.getLink(0);
        assertEquals(0, linkVerbose.getPosOrig());
        assertEquals(0, linkVerbose.getPosNew());
        assertEquals(26, linkVerbose.getLen());
        assertEquals("", resultVerbose.getAbsolute());
    }

    @Test
    public void testPatch() {
        String a = "abcdefghijklmnopqrstuvwxyz";
        String b = "abcdefghijklmnopqrstuvwxyz12345";
        RelativeString r = StringIncrementor.diff(a, b);

        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);

        String resultVerbose = StringIncrementor.patch(a, r, true);
        assertEquals(b, resultVerbose);
    }

    @Test
    public void testPatchWithLinks() {
        String a = "abcdefghijklmnopqrstuvwxyz";
        String b = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
        RelativeString r = StringIncrementor.diff(a, b);

        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);

        String resultVerbose = StringIncrementor.patch(a, r, true);
        assertEquals(b, resultVerbose);
    }
}