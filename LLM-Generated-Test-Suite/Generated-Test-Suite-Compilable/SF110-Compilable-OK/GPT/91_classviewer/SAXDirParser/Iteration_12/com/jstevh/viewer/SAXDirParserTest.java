package com.jstevh.viewer;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;
import java.util.HashMap;

import static org.junit.Assert.*;

public class SAXDirParserTest {

    private SAXDirParser saxDirParser;

    @Before
    public void setUp() throws Exception {
        saxDirParser = new SAXDirParser();
    }

    @Test
    public void testConstructor() {
        assertNotNull(saxDirParser);
        assertEquals("", saxDirParser.getWebData());
    }

    @Test
    public void testStartElementAndCharacters() throws Exception {
        String xmlInput = "<root>" +
                "<BrowserLoc>http://example.com</BrowserLoc>" +
                "<Editor>vim</Editor>" +
                "<acceptsLineNumber>Yes</acceptsLineNumber>" +
                "<parameter>lineNum</parameter>" +
                "<Web>http://web.com</Web>" +
                "<Local>Yes</Local>" +
                "<pkg>com.example.</pkg>" +
                "</root>";

        parseXML(xmlInput);

        assertEquals("http://example.com", SAXDirParser.getLocalBrowser());
        assertEquals("vim", SAXDirParser.getEditor());
        assertTrue(SAXDirParser.acceptsLineNumber());
        assertEquals("lineNum", SAXDirParser.lineNumberParameter());
        assertEquals("http://web.com", saxDirParser.location);
        assertTrue(saxDirParser.local);
        assertTrue(SAXDirParser.getDirectory().containsKey("com.example"));
        assertTrue(SAXDirParser.getLocalPackages().contains("com.example"));
    }

    @Test
    public void testEndElement() throws Exception {
        String xmlInput = "<root><Group></Group></root>";

        parseXML(xmlInput);

        // Since endElement only sets check to false, we ensure no exceptions occur
        assertFalse(saxDirParser.check);
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

    private void parseXML(String xmlInput) throws Exception {
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser myParser = saxFactory.newSAXParser();
        XMLReader myReader = myParser.getXMLReader();
        myReader.setContentHandler(saxDirParser);
        myReader.parse(new InputSource(new StringReader(xmlInput)));
    }
}