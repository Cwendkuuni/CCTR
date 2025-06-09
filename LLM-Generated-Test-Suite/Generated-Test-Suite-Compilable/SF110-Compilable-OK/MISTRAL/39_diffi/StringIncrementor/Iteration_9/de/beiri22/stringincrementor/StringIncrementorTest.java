package de.beiri22.stringincrementor;

import de.beiri22.stringincrementor.helper.*;
import de.beiri22.stringincrementor.relativestring.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class StringIncrementorTest {

    @Test
    public void testDiff() {
        String a = "abcdefghijklmnopqrstuvwxyz";
        String b = "abcdefghijklmnopqrstuvwxyz";
        RelativeString result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(0, result.linksCount());
        assertEquals(b, result.getAbsolute());

        b = "abcdefghijklmnopqrstuvwxyz123";
        result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        assertEquals("123", result.getAbsolute());

        b = "abcdefghijklmnopqrstuvwxyz1234567890";
        result = StringIncrementor.diff(a, b);
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        assertEquals("1234567890", result.getAbsolute());
    }

    @Test
    public void testDiffVerbose() {
        String a = "abcdefghijklmnopqrstuvwxyz";
        String b = "abcdefghijklmnopqrstuvwxyz";
        RelativeString result = StringIncrementor.diff(a, b, true);
        assertNotNull(result);
        assertEquals(0, result.linksCount());
        assertEquals(b, result.getAbsolute());

        b = "abcdefghijklmnopqrstuvwxyz123";
        result = StringIncrementor.diff(a, b, true);
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        assertEquals("123", result.getAbsolute());

        b = "abcdefghijklmnopqrstuvwxyz1234567890";
        result = StringIncrementor.diff(a, b, true);
        assertNotNull(result);
        assertEquals(1, result.linksCount());
        assertEquals("1234567890", result.getAbsolute());
    }

    @Test
    public void testPatch() {
        String a = "abcdefghijklmnopqrstuvwxyz";
        String b = "abcdefghijklmnopqrstuvwxyz";
        RelativeString r = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, r);
        assertEquals(b, result);

        b = "abcdefghijklmnopqrstuvwxyz123";
        r = StringIncrementor.diff(a, b);
        result = StringIncrementor.patch(a, r);
        assertEquals(b, result);

        b = "abcdefghijklmnopqrstuvwxyz1234567890";
        r = StringIncrementor.diff(a, b);
        result = StringIncrementor.patch(a, r);
        assertEquals(b, result);
    }

    @Test
    public void testPatchVerbose() {
        String a = "abcdefghijklmnopqrstuvwxyz";
        String b = "abcdefghijklmnopqrstuvwxyz";
        RelativeString r = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, r, true);
        assertEquals(b, result);

        b = "abcdefghijklmnopqrstuvwxyz123";
        r = StringIncrementor.diff(a, b);
        result = StringIncrementor.patch(a, r, true);
        assertEquals(b, result);

        b = "abcdefghijklmnopqrstuvwxyz1234567890";
        r = StringIncrementor.diff(a, b);
        result = StringIncrementor.patch(a, r, true);
        assertEquals(b, result);
    }
}