package com.jstevh.viewer;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;
import com.jstevh.viewer.SAXDirParser;
import com.jstevh.tools.StringList;

public class SAXDirParserTest {

    private SAXDirParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new SAXDirParser();
    }

    @Test
    public void testGetWebData() {
        assertEquals("", parser.getWebData());
    }

    @Test
    public void testStartElementBrowserLoc() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "BrowserLoc", atts);
        parser.characters("http://example.com".toCharArray(), 0, "http://example.com".length());
        assertEquals("http://example.com", SAXDirParser.getLocalBrowser());
    }

    @Test
    public void testStartElementEditor() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "Editor", atts);
        parser.characters("vim".toCharArray(), 0, "vim".length());
        assertEquals("vim", SAXDirParser.getEditor());
    }

    @Test
    public void testStartElementAcceptsLineNumberYes() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "acceptsLineNumber", atts);
        parser.characters("Yes".toCharArray(), 0, "Yes".length());
        assertTrue(SAXDirParser.acceptsLineNumber());
    }

    @Test
    public void testStartElementAcceptsLineNumberNo() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "acceptsLineNumber", atts);
        parser.characters("No".toCharArray(), 0, "No".length());
        assertFalse(SAXDirParser.acceptsLineNumber());
    }

    @Test
    public void testStartElementParameter() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "parameter", atts);
        parser.characters("paramValue".toCharArray(), 0, "paramValue".length());
        assertEquals("paramValue", SAXDirParser.lineNumberParameter());
    }

    @Test
    public void testStartElementWeb() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "Web", atts);
        parser.characters("http://example.com".toCharArray(), 0, "http://example.com".length());
        assertEquals("http://example.com", parser.location);
    }

    @Test
    public void testStartElementLocalYes() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "Local", atts);
        parser.characters("Yes".toCharArray(), 0, "Yes".length());
        assertTrue(parser.local);
    }

    @Test
    public void testStartElementLocalNo() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "Local", atts);
        parser.characters("No".toCharArray(), 0, "No".length());
        assertFalse(parser.local);
    }

    @Test
    public void testStartElementPkg() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "Web", atts);
        parser.characters("http://example.com".toCharArray(), 0, "http://example.com".length());
        parser.startElement("", "", "pkg", atts);
        parser.characters("com.example.".toCharArray(), 0, "com.example.".length());
        assertEquals("http://example.com", SAXDirParser.getDirectory().get("com.example"));
    }

    @Test
    public void testGetDirectory() {
        assertNotNull(SAXDirParser.getDirectory());
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