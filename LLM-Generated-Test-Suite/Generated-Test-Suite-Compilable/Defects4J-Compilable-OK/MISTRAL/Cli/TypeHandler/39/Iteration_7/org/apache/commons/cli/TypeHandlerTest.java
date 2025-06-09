package org.apache.commons.cli;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PatternOptionBuilder;
import org.apache.commons.cli.TypeHandler;
import org.junit.Test;
import static org.junit.Assert.*;

public class TypeHandlerTest {

    @Test
    public void testCreateValueString() throws ParseException {
        assertEquals("test", TypeHandler.createValue("test", String.class));
    }

    @Test
    public void testCreateValueObject() throws ParseException {
        Object obj = TypeHandler.createValue("java.lang.String", PatternOptionBuilder.OBJECT_VALUE);
        assertTrue(obj instanceof String);
    }

    @Test
    public void testCreateValueNumber() throws ParseException {
        Object number = TypeHandler.createValue("123", PatternOptionBuilder.NUMBER_VALUE);
        assertTrue(number instanceof Long);

        number = TypeHandler.createValue("123.45", PatternOptionBuilder.NUMBER_VALUE);
        assertTrue(number instanceof Double);
    }

    @Test(expected = ParseException.class)
    public void testCreateValueNumberInvalid() throws ParseException {
        TypeHandler.createValue("invalid", PatternOptionBuilder.NUMBER_VALUE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateValueDate() throws ParseException {
        TypeHandler.createValue("2023-10-01", PatternOptionBuilder.DATE_VALUE);
    }

    @Test
    public void testCreateValueClass() throws ParseException {
        Object clazz = TypeHandler.createValue("java.lang.String", PatternOptionBuilder.CLASS_VALUE);
        assertTrue(clazz instanceof Class);
    }

    @Test
    public void testCreateValueFile() throws ParseException {
        Object file = TypeHandler.createValue("/path/to/file", PatternOptionBuilder.FILE_VALUE);
        assertTrue(file instanceof java.io.File);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateValueFiles() throws ParseException {
        TypeHandler.createValue("/path/to/files", PatternOptionBuilder.FILES_VALUE);
    }

    @Test
    public void testCreateValueURL() throws ParseException {
        Object url = TypeHandler.createValue("http://example.com", PatternOptionBuilder.URL_VALUE);
        assertTrue(url instanceof java.net.URL);
    }

    @Test(expected = ParseException.class)
    public void testCreateValueURLInvalid() throws ParseException {
        TypeHandler.createValue("invalid_url", PatternOptionBuilder.URL_VALUE);
    }

    @Test
    public void testCreateObject() throws ParseException {
        Object obj = TypeHandler.createObject("java.lang.String");
        assertTrue(obj instanceof String);
    }

    @Test(expected = ParseException.class)
    public void testCreateObjectInvalidClass() throws ParseException {
        TypeHandler.createObject("invalid.class.Name");
    }

    @Test
    public void testCreateNumber() throws ParseException {
        Number number = TypeHandler.createNumber("123");
        assertTrue(number instanceof Long);

        number = TypeHandler.createNumber("123.45");
        assertTrue(number instanceof Double);
    }

    @Test(expected = ParseException.class)
    public void testCreateNumberInvalid() throws ParseException {
        TypeHandler.createNumber("invalid");
    }

    @Test
    public void testCreateClass() throws ParseException {
        Class<?> clazz = TypeHandler.createClass("java.lang.String");
        assertEquals(String.class, clazz);
    }

    @Test(expected = ParseException.class)
    public void testCreateClassInvalid() throws ParseException {
        TypeHandler.createClass("invalid.class.Name");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateDate() {
        TypeHandler.createDate("2023-10-01");
    }

    @Test
    public void testCreateURL() throws ParseException {
        java.net.URL url = TypeHandler.createURL("http://example.com");
        assertNotNull(url);
    }

    @Test(expected = ParseException.class)
    public void testCreateURLInvalid() throws ParseException {
        TypeHandler.createURL("invalid_url");
    }

    @Test
    public void testCreateFile() {
        java.io.File file = TypeHandler.createFile("/path/to/file");
        assertNotNull(file);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCreateFiles() {
        TypeHandler.createFiles("/path/to/files");
    }
}