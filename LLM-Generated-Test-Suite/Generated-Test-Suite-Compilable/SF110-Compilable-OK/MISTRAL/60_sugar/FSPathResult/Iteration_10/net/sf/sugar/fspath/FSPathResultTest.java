package net.sf.sugar.fspath;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.net.URI;
import java.util.Date;

public class FSPathResultTest {

    @Test
    public void testFileConstructorAndGetter() {
        File file = new File("test.txt");
        FSPathResult result = new FSPathResult(file);
        assertEquals(file, result.getFile());
        assertEquals(file.getAbsolutePath(), result.toString());
    }

    @Test
    public void testStringConstructorAndGetter() {
        String str = "testString";
        FSPathResult result = new FSPathResult(str);
        assertEquals(str, result.getString());
        assertEquals(str, result.toString());
    }

    @Test
    public void testDoubleConstructorAndGetter() {
        Double dbl = 123.45;
        FSPathResult result = new FSPathResult(dbl);
        assertEquals(dbl, result.getDouble());
        assertEquals(dbl.toString(), result.toString());
    }

    @Test
    public void testDateConstructorAndGetter() {
        Date date = new Date();
        FSPathResult result = new FSPathResult(date);
        assertEquals(date, result.getDate());
        assertEquals(date.toString(), result.toString());
    }

    @Test
    public void testURIConstructorAndGetter() throws Exception {
        URI uri = new URI("http://example.com");
        FSPathResult result = new FSPathResult(uri);
        assertEquals(uri, result.getURI());
        assertEquals(uri.toString(), result.toString());
    }

    @Test
    public void testBooleanConstructorAndGetter() {
        Boolean bool = true;
        FSPathResult result = new FSPathResult(bool);
        assertEquals(bool, result.getBoolean());
        assertEquals(bool.toString(), result.toString());
    }

    @Test
    public void testEmptyFSPathResult() {
        FSPathResult result = new FSPathResult((File) null);
        assertNull(result.getFile());
        assertNull(result.getString());
        assertNull(result.getDouble());
        assertNull(result.getDate());
        assertNull(result.getURI());
        assertNull(result.getBoolean());
        assertEquals("FSPathResult : empty", result.toString());
    }
}