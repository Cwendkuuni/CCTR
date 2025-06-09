package org.apache.commons.cli;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PatternOptionBuilder;
import org.apache.commons.cli.TypeHandler;
import org.junit.Test;
import static org.junit.Assert.*;

public class TypeHandlerTest {

    @Test
    public void testCreateValueString() throws ParseException {
        String str = "test";
        Object obj = PatternOptionBuilder.STRING_VALUE;
        Object result = TypeHandler.createValue(str, obj);
        assertEquals(str, result);
    }

    @Test
    public void testCreateValueObject() throws ParseException {
        String str = "java.lang.String";
        Object obj = PatternOptionBuilder.OBJECT_VALUE;
        Object result = TypeHandler.createValue(str, obj);
        assertTrue(result instanceof String);
    }

    @Test
    public void testCreateValueNumber() throws ParseException {
        String str = "123.45";
        Object obj = PatternOptionBuilder.NUMBER_VALUE;
        Object result = TypeHandler.createValue(str, obj);
        assertTrue(result instanceof Double);
        assertEquals(123.45, ((Double) result).doubleValue(), 0.001);
    }

    @Test
    public void testCreateValueDate() {
        String str = "2023-10-01";
        Object obj = PatternOptionBuilder.DATE_VALUE;
        try {
            TypeHandler.createValue(str, obj);
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        } catch (ParseException e) {
            fail("Unexpected ParseException");
        }
    }

    @Test
    public void testCreateValueClass() throws ParseException {
        String str = "java.lang.String";
        Object obj = PatternOptionBuilder.CLASS_VALUE;
        Object result = TypeHandler.createValue(str, obj);
        assertEquals(String.class, result);
    }

    @Test
    public void testCreateValueFile() throws ParseException {
        String str = "test.txt";
        Object obj = PatternOptionBuilder.FILE_VALUE;
        Object result = TypeHandler.createValue(str, obj);
        assertTrue(result instanceof java.io.File);
        assertEquals("test.txt", ((java.io.File) result).getName());
    }

    @Test
    public void testCreateValueExistingFile() throws ParseException {
        String str = "test.txt";
        Object obj = PatternOptionBuilder.EXISTING_FILE_VALUE;
        Object result = TypeHandler.createValue(str, obj);
        assertTrue(result instanceof java.io.File);
        assertEquals("test.txt", ((java.io.File) result).getName());
    }

    @Test
    public void testCreateValueFiles() {
        String str = "test.txt";
        Object obj = PatternOptionBuilder.FILES_VALUE;
        try {
            TypeHandler.createValue(str, obj);
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        } catch (ParseException e) {
            fail("Unexpected ParseException");
        }
    }

    @Test
    public void testCreateValueURL() throws ParseException {
        String str = "http://example.com";
        Object obj = PatternOptionBuilder.URL_VALUE;
        Object result = TypeHandler.createValue(str, obj);
        assertTrue(result instanceof java.net.URL);
        assertEquals("http://example.com", ((java.net.URL) result).toString());
    }

    @Test
    public void testCreateObject() throws ParseException {
        String classname = "java.lang.String";
        Object result = TypeHandler.createObject(classname);
        assertTrue(result instanceof String);
    }

    @Test
    public void testCreateNumber() throws ParseException {
        String str = "123.45";
        Number result = TypeHandler.createNumber(str);
        assertTrue(result instanceof Double);
        assertEquals(123.45, result.doubleValue(), 0.001);
    }

    @Test
    public void testCreateClass() throws ParseException {
        String classname = "java.lang.String";
        Class<?> result = TypeHandler.createClass(classname);
        assertEquals(String.class, result);
    }

    @Test
    public void testCreateDate() {
        String str = "2023-10-01";
        try {
            TypeHandler.createDate(str);
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    @Test
    public void testCreateURL() throws ParseException {
        String str = "http://example.com";
        java.net.URL result = TypeHandler.createURL(str);
        assertEquals("http://example.com", result.toString());
    }

    @Test
    public void testCreateFile() {
        String str = "test.txt";
        java.io.File result = TypeHandler.createFile(str);
        assertEquals("test.txt", result.getName());
    }

    @Test
    public void testCreateFiles() {
        String str = "test.txt";
        try {
            TypeHandler.createFiles(str);
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}