package com.jstevh.viewer;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.HashMap;

import static org.junit.Assert.*;

public class SAXDirParserTest {

    private SAXDirParser saxDirParser;

    @Before
    public void setUp() throws Exception {
        saxDirParser = new SAXDirParser();
    }

    @Test
    public void testGetWebData() {
        assertEquals("", saxDirParser.getWebData());
    }

    @Test
    public void testStartElementBrowserLoc() throws SAXException {
        Attributes attributes = null;
        saxDirParser.startElement("", "", "BrowserLoc", attributes);
        assertTrue(SAXDirParser.getLocalBrowser().isEmpty());
    }

    @Test
    public void testStartElementEditor() throws SAXException {
        Attributes attributes = null;
        saxDirParser.startElement("", "", "Editor", attributes);
        assertTrue(SAXDirParser.getEditor().isEmpty());
    }

    @Test
    public void testStartElementAcceptsLineNumber() throws SAXException {
        Attributes attributes = null;
        saxDirParser.startElement("", "", "acceptsLineNumber", attributes);
        assertFalse(SAXDirParser.acceptsLineNumber());
    }

    @Test
    public void testStartElementParameter() throws SAXException {
        Attributes attributes = null;
        saxDirParser.startElement("", "", "parameter", attributes);
        assertTrue(SAXDirParser.lineNumberParameter().isEmpty());
    }

    @Test
    public void testStartElementGroup() throws SAXException {
        Attributes attributes = null;
        saxDirParser.startElement("", "", "Group", attributes);
        // No specific state change to assert
    }

    @Test
    public void testStartElementWeb() throws SAXException {
        Attributes attributes = null;
        saxDirParser.startElement("", "", "Web", attributes);
        // No specific state change to assert
    }

    @Test
    public void testStartElementLocal() throws SAXException {
        Attributes attributes = null;
        saxDirParser.startElement("", "", "Local", attributes);
        // No specific state change to assert
    }

    @Test
    public void testStartElementNames() throws SAXException {
        Attributes attributes = null;
        saxDirParser.startElement("", "", "Names", attributes);
        // No specific state change to assert
    }

    @Test
    public void testStartElementPkg() throws SAXException {
        Attributes attributes = null;
        saxDirParser.startElement("", "", "pkg", attributes);
        // No specific state change to assert
    }

    @Test
    public void testCharacters() {
        char[] charArray = "testValue".toCharArray();
        saxDirParser.characters(charArray, 0, charArray.length);
        // No specific state change to assert
    }

    @Test
    public void testEndElement() throws SAXException {
        saxDirParser.endElement("", "", "Group");
        // No specific state change to assert
    }

    @Test
    public void testGetDirectory() {
        HashMap<String, String> directory = SAXDirParser.getDirectory();
        assertNotNull(directory);
    }

    @Test
    public void testGetLocalPackages() {
        assertNull(SAXDirParser.getLocalPackages());
    }

    @Test
    public void testGetLocalBrowser() {
        assertEquals("", SAXDirParser.getLocalBrowser());
    }

    @Test
    public void testGetEditor() {
        assertEquals("", SAXDirParser.getEditor());
    }

    @Test
    public void testAcceptsLineNumber() {
        assertFalse(SAXDirParser.acceptsLineNumber());
    }

    @Test
    public void testLineNumberParameter() {
        assertEquals("", SAXDirParser.lineNumberParameter());
    }
}