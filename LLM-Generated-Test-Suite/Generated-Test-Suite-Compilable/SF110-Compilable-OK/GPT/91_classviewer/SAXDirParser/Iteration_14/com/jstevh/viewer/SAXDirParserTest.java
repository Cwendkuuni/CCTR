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
    public void testConstructor() {
        assertNotNull(parser);
        assertEquals("", parser.getWebData());
    }

    @Test
    public void testStartElementBrowserLoc() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "BrowserLoc", atts);
        assertTrue(parser.check);
        assertEquals(0, parser.level);
    }

    @Test
    public void testStartElementEditor() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "Editor", atts);
        assertTrue(parser.check);
        assertEquals(10, parser.level);
    }

    @Test
    public void testStartElementAcceptsLineNumber() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "acceptsLineNumber", atts);
        assertTrue(parser.check);
        assertEquals(11, parser.level);
    }

    @Test
    public void testStartElementParameter() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "parameter", atts);
        assertTrue(parser.check);
        assertEquals(12, parser.level);
    }

    @Test
    public void testStartElementGroup() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "Group", atts);
        assertFalse(parser.check);
        assertEquals(2, parser.level);
    }

    @Test
    public void testStartElementWeb() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "Web", atts);
        assertTrue(parser.check);
        assertEquals(30, parser.level);
    }

    @Test
    public void testStartElementLocal() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "Local", atts);
        assertTrue(parser.check);
        assertEquals(31, parser.level);
    }

    @Test
    public void testStartElementNames() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "Names", atts);
        assertTrue(parser.check);
    }

    @Test
    public void testStartElementPkg() throws Exception {
        Attributes atts = new AttributesImpl();
        parser.startElement("", "", "pkg", atts);
        assertEquals(4, parser.level);
    }

    @Test
    public void testCharactersLocalBrowser() {
        parser.check = true;
        parser.level = 0;
        char[] chars = "testBrowser".toCharArray();
        parser.characters(chars, 0, chars.length);
        assertEquals("testBrowser", SAXDirParser.getLocalBrowser());
    }

    @Test
    public void testCharactersFileEditor() {
        parser.check = true;
        parser.level = 10;
        char[] chars = "testEditor".toCharArray();
        parser.characters(chars, 0, chars.length);
        assertEquals("testEditor", SAXDirParser.getEditor());
    }

    @Test
    public void testCharactersLineNumAcceptedYes() {
        parser.check = true;
        parser.level = 11;
        char[] chars = "Yes".toCharArray();
        parser.characters(chars, 0, chars.length);
        assertTrue(SAXDirParser.acceptsLineNumber());
    }

    @Test
    public void testCharactersLineNumAcceptedNo() {
        parser.check = true;
        parser.level = 11;
        char[] chars = "No".toCharArray();
        parser.characters(chars, 0, chars.length);
        assertFalse(SAXDirParser.acceptsLineNumber());
    }

    @Test
    public void testCharactersLineNumParam() {
        parser.check = true;
        parser.level = 12;
        char[] chars = "param".toCharArray();
        parser.characters(chars, 0, chars.length);
        assertEquals("param", SAXDirParser.lineNumberParameter());
    }

    @Test
    public void testCharactersLocation() {
        parser.check = true;
        parser.level = 30;
        char[] chars = "location".toCharArray();
        parser.characters(chars, 0, chars.length);
        assertEquals("location", parser.location);
    }

    @Test
    public void testCharactersLocalTrue() {
        parser.check = true;
        parser.level = 31;
        char[] chars = "Yes".toCharArray();
        parser.characters(chars, 0, chars.length);
        assertTrue(parser.local);
    }

    @Test
    public void testCharactersLocalFalse() {
        parser.check = true;
        parser.level = 31;
        char[] chars = "No".toCharArray();
        parser.characters(chars, 0, chars.length);
        assertFalse(parser.local);
    }

    @Test
    public void testCharactersPkg() {
        parser.check = true;
        parser.level = 4;
        parser.location = "testLocation";
        char[] chars = "test.pkg.".toCharArray();
        parser.characters(chars, 0, chars.length);
        assertEquals("testLocation", SAXDirParser.getDirectory().get("test.pkg"));
        assertTrue(SAXDirParser.getLocalPackages().contains("test.pkg"));
    }

    @Test
    public void testEndElementGroup() throws Exception {
        parser.check = true;
        parser.endElement("", "", "Group");
        assertFalse(parser.check);
    }

    @Test
    public void testGetDirectory() {
        assertNotNull(SAXDirParser.getDirectory());
    }

    @Test
    public void testGetLocalPackages() {
        assertNotNull(SAXDirParser.getLocalPackages());
    }

    @Test
    public void testGetLocalBrowser() {
        assertNotNull(SAXDirParser.getLocalBrowser());
    }

    @Test
    public void testGetEditor() {
        assertNotNull(SAXDirParser.getEditor());
    }

    @Test
    public void testAcceptsLineNumber() {
        assertFalse(SAXDirParser.acceptsLineNumber());
    }

    @Test
    public void testLineNumberParameter() {
        assertNotNull(SAXDirParser.lineNumberParameter());
    }
}