package com.jstevh.viewer;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;
import com.jstevh.viewer.SAXDirParser;
import com.jstevh.tools.StringList;

import java.util.HashMap;

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
    public void testStartElementAcceptsLineNumber() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "acceptsLineNumber", atts);
        parser.characters("Yes".toCharArray(), 0, "Yes".length());
        assertTrue(SAXDirParser.acceptsLineNumber());
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
        // Assuming location is a public field or has a getter
        assertEquals("http://example.com", parser.location);
    }

    @Test
    public void testStartElementLocal() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "Local", atts);
        parser.characters("Yes".toCharArray(), 0, "Yes".length());
        // Assuming local is a public field or has a getter
        assertTrue(parser.local);
    }

    @Test
    public void testStartElementPkg() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "Web", atts);
        parser.characters("http://example.com".toCharArray(), 0, "http://example.com".length());
        parser.startElement("", "", "Local", atts);
        parser.characters("Yes".toCharArray(), 0, "Yes".length());
        parser.startElement("", "", "pkg", atts);
        parser.characters("com.example.".toCharArray(), 0, "com.example.".length());
        HashMap<String, String> directory = SAXDirParser.getDirectory();
        assertEquals("http://example.com", directory.get("com.example"));
        StringList localPackages = SAXDirParser.getLocalPackages();
        assertNotNull(localPackages);
        assertTrue(localPackages.contains("com.example"));
    }

    @Test
    public void testEndElementGroup() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "Group", atts);
        parser.endElement("", "", "Group");
        // Assuming check is a public field or has a getter
        assertFalse(parser.check);
    }
}