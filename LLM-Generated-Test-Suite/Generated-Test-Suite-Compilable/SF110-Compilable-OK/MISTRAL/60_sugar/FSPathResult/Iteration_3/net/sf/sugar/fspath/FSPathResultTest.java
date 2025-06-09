package net.sf.sugar.fspath;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.io.File;
import java.net.URI;
import java.util.Date;

public class FSPathResultTest {

    private File testFile;
    private String testString;
    private Double testDouble;
    private Date testDate;
    private URI testURI;
    private Boolean testBoolean;

    @Before
    public void setUp() {
        testFile = new File("testFile.txt");
        testString = "testString";
        testDouble = 123.45;
        testDate = new Date();
        try {
            testURI = new URI("http://example.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
        testBoolean = true;
    }

    @Test
    public void testFileConstructor() {
        FSPathResult result = new FSPathResult(testFile);
        assertEquals(testFile, result.getFile());
    }

    @Test
    public void testStringConstructor() {
        FSPathResult result = new FSPathResult(testString);
        assertEquals(testString, result.getString());
    }

    @Test
    public void testDoubleConstructor() {
        FSPathResult result = new FSPathResult(testDouble);
        assertEquals(testDouble, result.getDouble());
    }

    @Test
    public void testDateConstructor() {
        FSPathResult result = new FSPathResult(testDate);
        assertEquals(testDate, result.getDate());
    }

    @Test
    public void testURIConstructor() {
        FSPathResult result = new FSPathResult(testURI);
        assertEquals(testURI, result.getURI());
    }

    @Test
    public void testBooleanConstructor() {
        FSPathResult result = new FSPathResult(testBoolean);
        assertEquals(testBoolean, result.getBoolean());
    }

    @Test
    public void testToStringFile() {
        FSPathResult result = new FSPathResult(testFile);
        assertEquals(testFile.getAbsolutePath(), result.toString());
    }

    @Test
    public void testToStringString() {
        FSPathResult result = new FSPathResult(testString);
        assertEquals(testString, result.toString());
    }

    @Test
    public void testToStringDouble() {
        FSPathResult result = new FSPathResult(testDouble);
        assertEquals(testDouble.toString(), result.toString());
    }

    @Test
    public void testToStringBoolean() {
        FSPathResult result = new FSPathResult(testBoolean);
        assertEquals(testBoolean.toString(), result.toString());
    }

    @Test
    public void testToStringDate() {
        FSPathResult result = new FSPathResult(testDate);
        assertEquals(testDate.toString(), result.toString());
    }

    @Test
    public void testToStringURI() {
        FSPathResult result = new FSPathResult(testURI);
        assertEquals(testURI.toString(), result.toString());
    }

    @Test
    public void testToStringEmpty() {
        FSPathResult result = new FSPathResult((String) null);
        assertEquals("FSPathResult : empty", result.toString());
    }
}