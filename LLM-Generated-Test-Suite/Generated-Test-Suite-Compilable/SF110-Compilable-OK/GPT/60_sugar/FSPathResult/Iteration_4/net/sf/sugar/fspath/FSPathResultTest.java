package net.sf.sugar.fspath;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

public class FSPathResultTest {

    @Test
    public void testFileConstructorAndGetter() {
        File file = new File("test.txt");
        FSPathResult result = new FSPathResult(file);
        assertEquals(file, result.getFile());
        assertNull(result.getString());
        assertNull(result.getDouble());
        assertNull(result.getDate());
        assertNull(result.getURI());
        assertNull(result.getBoolean());
    }

    @Test
    public void testStringConstructorAndGetter() {
        String str = "test string";
        FSPathResult result = new FSPathResult(str);
        assertEquals(str, result.getString());
        assertNull(result.getFile());
        assertNull(result.getDouble());
        assertNull(result.getDate());
        assertNull(result.getURI());
        assertNull(result.getBoolean());
    }

    @Test
    public void testDoubleConstructorAndGetter() {
        Double dbl = 123.45;
        FSPathResult result = new FSPathResult(dbl);
        assertEquals(dbl, result.getDouble());
        assertNull(result.getFile());
        assertNull(result.getString());
        assertNull(result.getDate());
        assertNull(result.getURI());
        assertNull(result.getBoolean());
    }

    @Test
    public void testDateConstructorAndGetter() {
        Date date = new Date();
        FSPathResult result = new FSPathResult(date);
        assertEquals(date, result.getDate());
        assertNull(result.getFile());
        assertNull(result.getString());
        assertNull(result.getDouble());
        assertNull(result.getURI());
        assertNull(result.getBoolean());
    }

    @Test
    public void testURIConstructorAndGetter() throws URISyntaxException {
        URI uri = new URI("http://example.com");
        FSPathResult result = new FSPathResult(uri);
        assertEquals(uri, result.getURI());
        assertNull(result.getFile());
        assertNull(result.getString());
        assertNull(result.getDouble());
        assertNull(result.getDate());
        assertNull(result.getBoolean());
    }

    @Test
    public void testBooleanConstructorAndGetter() {
        Boolean bool = true;
        FSPathResult result = new FSPathResult(bool);
        assertEquals(bool, result.getBoolean());
        assertNull(result.getFile());
        assertNull(result.getString());
        assertNull(result.getDouble());
        assertNull(result.getDate());
        assertNull(result.getURI());
    }

    @Test
    public void testToStringWithFile() {
        File file = new File("test.txt");
        FSPathResult result = new FSPathResult(file);
        assertEquals(file.getAbsolutePath(), result.toString());
    }

    @Test
    public void testToStringWithString() {
        String str = "test string";
        FSPathResult result = new FSPathResult(str);
        assertEquals(str, result.toString());
    }

    @Test
    public void testToStringWithDouble() {
        Double dbl = 123.45;
        FSPathResult result = new FSPathResult(dbl);
        assertEquals(dbl.toString(), result.toString());
    }

    @Test
    public void testToStringWithDate() {
        Date date = new Date();
        FSPathResult result = new FSPathResult(date);
        assertEquals(date.toString(), result.toString());
    }

    @Test
    public void testToStringWithURI() throws URISyntaxException {
        URI uri = new URI("http://example.com");
        FSPathResult result = new FSPathResult(uri);
        assertEquals(uri.toString(), result.toString());
    }

    @Test
    public void testToStringWithBoolean() {
        Boolean bool = true;
        FSPathResult result = new FSPathResult(bool);
        assertEquals(bool.toString(), result.toString());
    }

    @Test
    public void testToStringWithEmptyResult() {
        FSPathResult result = new FSPathResult((File) null);
        assertEquals("FSPathResult : empty", result.toString());
    }
}