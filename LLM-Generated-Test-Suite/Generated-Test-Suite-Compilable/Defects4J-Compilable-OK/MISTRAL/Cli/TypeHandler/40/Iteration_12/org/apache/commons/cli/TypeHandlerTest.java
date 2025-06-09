package org.apache.commons.cli;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.TypeHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TypeHandlerTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"testString", String.class, "testString"},
                {"java.lang.Object", Object.class, Object.class},
                {"123", Number.class, 123L},
                {"123.45", Number.class, 123.45},
                {"java.lang.String", Class.class, String.class},
                {"http://example.com", URL.class, "http://example.com"},
                {"/path/to/file", File.class, "/path/to/file"},
                {"/path/to/existing/file", FileInputStream.class, "/path/to/existing/file"},
        });
    }

    private final String input;
    private final Class<?> expectedType;
    private final Object expectedValue;

    public TypeHandlerTest(String input, Class<?> expectedType, Object expectedValue) {
        this.input = input;
        this.expectedType = expectedType;
        this.expectedValue = expectedValue;
    }

    @Test
    public void testCreateValue() throws ParseException {
        Object result = TypeHandler.createValue(input, expectedType);
        assertEquals(expectedValue, result);
    }

    @Test
    public void testCreateObject() throws ParseException {
        Object result = TypeHandler.createObject("java.lang.String");
        assertTrue(result instanceof String);
    }

    @Test
    public void testCreateNumber() throws ParseException {
        Number result = TypeHandler.createNumber("123");
        assertEquals(Long.valueOf(123), result);

        result = TypeHandler.createNumber("123.45");
        assertEquals(Double.valueOf(123.45), result);
    }

    @Test
    public void testCreateClass() throws ParseException {
        Class<?> result = TypeHandler.createClass("java.lang.String");
        assertEquals(String.class, result);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateDate() {
        TypeHandler.createDate("2023-10-01");
    }

    @Test
    public void testCreateURL() throws ParseException {
        URL result = TypeHandler.createURL("http://example.com");
        assertEquals("http://example.com", result.toString());
    }

    @Test
    public void testCreateFile() {
        File result = TypeHandler.createFile("/path/to/file");
        assertEquals("/path/to/file", result.getPath());
    }

    @Test
    public void testOpenFile() throws ParseException {
        // Assuming the file exists and is readable
        FileInputStream result = TypeHandler.openFile("/path/to/existing/file");
        assertNotNull(result);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateFiles() {
        TypeHandler.createFiles("/path/to/files");
    }
}