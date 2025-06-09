package de.beiri22.stringincrementor;

import static org.junit.Assert.*;
import org.junit.Test;
import de.beiri22.stringincrementor.StringIncrementor;
import de.beiri22.stringincrementor.relativestring.RelativeString;

public class StringIncrementorTest {

    @Test
    public void testDiffBasic() {
        String a = "abcdefg";
        String b = "abcxyzg";
        RelativeString result = StringIncrementor.diff(a, b);
        
        // Assuming RelativeString has methods to verify its contents
        assertNotNull(result);
        assertEquals("xyz", result.getAbsolute());
        assertEquals(2, result.linksCount());
    }

    @Test
    public void testDiffWithVerbose() {
        String a = "abcdefg";
        String b = "abcxyzg";
        RelativeString result = StringIncrementor.diff(a, b, true);
        
        assertNotNull(result);
        assertEquals("xyz", result.getAbsolute());
        assertEquals(2, result.linksCount());
    }

    @Test
    public void testDiffNoDifference() {
        String a = "abcdefg";
        String b = "abcdefg";
        RelativeString result = StringIncrementor.diff(a, b);
        
        assertNotNull(result);
        assertEquals("", result.getAbsolute());
        assertEquals(1, result.linksCount());
    }

    @Test
    public void testPatchBasic() {
        String a = "abcdefg";
        String b = "abcxyzg";
        RelativeString diff = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, diff);
        
        assertEquals(b, result);
    }

    @Test
    public void testPatchWithVerbose() {
        String a = "abcdefg";
        String b = "abcxyzg";
        RelativeString diff = StringIncrementor.diff(a, b);
        String result = StringIncrementor.patch(a, diff, true);
        
        assertEquals(b, result);
    }

    @Test
    public void testPatchNoDifference() {
        String a = "abcdefg";
        RelativeString diff = StringIncrementor.diff(a, a);
        String result = StringIncrementor.patch(a, diff);
        
        assertEquals(a, result);
    }

    @Test
    public void testDiffEmptyStrings() {
        String a = "";
        String b = "";
        RelativeString result = StringIncrementor.diff(a, b);
        
        assertNotNull(result);
        assertEquals("", result.getAbsolute());
        assertEquals(0, result.linksCount());
    }

    @Test
    public void testPatchEmptyStrings() {
        String a = "";
        RelativeString diff = StringIncrementor.diff(a, a);
        String result = StringIncrementor.patch(a, diff);
        
        assertEquals("", result);
    }
}