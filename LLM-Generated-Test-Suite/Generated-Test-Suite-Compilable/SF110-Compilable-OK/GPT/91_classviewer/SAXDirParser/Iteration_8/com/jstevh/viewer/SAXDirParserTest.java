package com.jstevh.viewer;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

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
    public void testStartElementBrowserLoc() throws Exception {
        Attributes attributes = new AttributesImpl();
        saxDirParser.startElement("", "", "BrowserLoc", attributes);
        saxDirParser.characters("http://example.com".toCharArray(), 0, "http://example.com".length());
        assertEquals("http://example.com", SAXDirParser.getLocalBrowser());
    }

    @Test
    public void testStartElementEditor() throws Exception {
        Attributes attributes = new AttributesImpl();
        saxDirParser.startElement("", "", "Editor", attributes);
        saxDirParser.characters("vim".toCharArray(), 0, "vim".length());
        assertEquals("vim", SAXDirParser.getEditor());
    }

    @Test
    public void testStartElementAcceptsLineNumberYes() throws Exception {
        Attributes attributes = new AttributesImpl();
        saxDirParser.startElement("", "", "acceptsLineNumber", attributes);
        saxDirParser.characters("Yes".toCharArray(), 0, "Yes".length());
        assertTrue(SAXDirParser.acceptsLineNumber());
    }

    @Test
    public void testStartElementAcceptsLineNumberNo() throws Exception {
        Attributes attributes = new AttributesImpl();
        saxDirParser.startElement("", "", "acceptsLineNumber", attributes);
        saxDirParser.characters("No".toCharArray(), 0, "No".length());
        assertFalse(SAXDirParser.acceptsLineNumber());
    }

    @Test
    public void testStartElementParameter() throws Exception {
        Attributes attributes = new AttributesImpl();
        saxDirParser.startElement("", "", "parameter", attributes);
        saxDirParser.characters("paramValue".toCharArray(), 0, "paramValue".length());
        assertEquals("paramValue", SAXDirParser.lineNumberParameter());
    }

    @Test
    public void testStartElementWeb() throws Exception {
        Attributes attributes = new AttributesImpl();
        saxDirParser.startElement("", "", "Web", attributes);
        saxDirParser.characters("http://example.com".toCharArray(), 0, "http://example.com".length());
        assertEquals("http://example.com", SAXDirParser.getDirectory().get("http://example.com"));
    }

    @Test
    public void testStartElementLocalYes() throws Exception {
        Attributes attributes = new AttributesImpl();
        saxDirParser.startElement("", "", "Local", attributes);
        saxDirParser.characters("Yes".toCharArray(), 0, "Yes".length());
        assertTrue(SAXDirParser.getLocalPackages() != null);
    }

    @Test
    public void testStartElementLocalNo() throws Exception {
        Attributes attributes = new AttributesImpl();
        saxDirParser.startElement("", "", "Local", attributes);
        saxDirParser.characters("No".toCharArray(), 0, "No".length());
        assertNull(SAXDirParser.getLocalPackages());
    }

    @Test
    public void testStartElementPkg() throws Exception {
        Attributes attributes = new AttributesImpl();
        saxDirParser.startElement("", "", "Web", attributes);
        saxDirParser.characters("http://example.com".toCharArray(), 0, "http://example.com".length());
        saxDirParser.startElement("", "", "pkg", attributes);
        saxDirParser.characters("com.example.".toCharArray(), 0, "com.example.".length());
        assertEquals("http://example.com", SAXDirParser.getDirectory().get("com.example"));
    }

    @Test
    public void testEndElementGroup() throws Exception {
        Attributes attributes = new AttributesImpl();
        saxDirParser.startElement("", "", "Group", attributes);
        saxDirParser.endElement("", "", "Group");
        // No specific state change to verify, just ensuring no exceptions
    }
}