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
    public void testDiffWithCompletelyDifferentStrings() {
        String a = "Hello, World!";
        String b = "Goodbye, Universe!";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals(0, result.linksCount());
        assertEquals(b, result.getAbsolute());
    }

    @Test
    public void testDiffWithPartialOverlap() {
        String a = "Hello, World!";
        String b = "Hello, Universe!";
        RelativeString result = StringIncrementor.diff(a, b);
        assertEquals(1, result.linksCount());
        assertEquals(" Universe!", result.getAbsolute());
        StringLink link = result.getLink(0);
        assertEquals(0, link.getPosOrig());
        assertEquals(0, link.getPosNew());
        assertEquals(7, link.getLen());
    }

    @Test
    public void testDiffWithVerbose() {
        String a = "Hello, World!";
        String b = "Hello, Universe!";
        RelativeString result = StringIncrementor.diff(a, b, true);
        assertEquals(1, result.linksCount());
        assertEquals(" Universe!", result.getAbsolute());
    }

    @Test
    public void testPatchWithIdenticalStrings() {
        String a = "Hello, World!";
        RelativeString r = new RelativeString();
        String result = StringIncrementor.patch(a, r);
        assertEquals(a, result);
    }

    @Test
    public void testPatchWithCompletelyDifferentStrings() {
        String a = "Hello, World!";
        RelativeString r = new RelativeString();
        r.setAbsolute("Goodbye, Universe!");
        String result = StringIncrementor.patch(a, r);
        assertEquals("Goodbye, Universe!", result);
    }

    @Test
    public void testPatchWithPartialOverlap() {
        String a = "Hello, World!";
        RelativeString r = new RelativeString();
        r.addLink(new StringLink(0, 0, 7));
        r.setAbsolute(" Universe!");
        String result = StringIncrementor.patch(a, r);
        assertEquals("Hello, Universe!", result);
    }

    @Test
    public void testPatchWithVerbose() {
        String a = "Hello, World!";
        RelativeString r = new RelativeString();
        r.addLink(new StringLink(0, 0, 7));
        r.setAbsolute(" Universe!");
        String result = StringIncrementor.patch(a, r, true);
        assertEquals("Hello, Universe!", result);
    }
}