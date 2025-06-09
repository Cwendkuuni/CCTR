package org.znerd.xmlenc;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import static org.junit.Assert.*;

public class XMLEncoderTest {

    private XMLEncoder xmlEncoderUTF8;
    private XMLEncoder xmlEncoderASCII;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws UnsupportedEncodingException {
        xmlEncoderUTF8 = XMLEncoder.getEncoder("UTF-8");
        xmlEncoderASCII = XMLEncoder.getEncoder("US-ASCII");
    }

    @After
    public void tearDown() {
        xmlEncoderUTF8 = null;
        xmlEncoderASCII = null;
    }

    @Test
    public void testGetEncoderValidEncoding() throws UnsupportedEncodingException {
        assertNotNull(XMLEncoder.getEncoder("UTF-8"));
        assertNotNull(XMLEncoder.getEncoder("US-ASCII"));
        assertNotNull(XMLEncoder.getEncoder("ISO-8859-1"));
    }

    @Test
    public void testGetEncoderInvalidEncoding() throws UnsupportedEncodingException {
        thrown.expect(UnsupportedEncodingException.class);
        XMLEncoder.getEncoder("INVALID-ENCODING");
    }

    @Test
    public void testGetEncoderNullEncoding() throws UnsupportedEncodingException {
        thrown.expect(IllegalArgumentException.class);
        XMLEncoder.getEncoder(null);
    }

    @Test
    public void testGetEncoding() {
        assertEquals("UTF-8", xmlEncoderUTF8.getEncoding());
        assertEquals("US-ASCII", xmlEncoderASCII.getEncoding());
    }

    @Test
    public void testDeclaration() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.declaration(writer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", writer.toString());
    }

    @Test
    public void testTextWithEscapeAmpersands() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.text(writer, "Hello & World", true);
        assertEquals("Hello &amp; World", writer.toString());
    }

    @Test
    public void testTextWithoutEscapeAmpersands() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.text(writer, "Hello & World", false);
        assertEquals("Hello & World", writer.toString());
    }

    @Test
    public void testTextWithInvalidXMLCharacter() throws Exception {
        thrown.expect(InvalidXMLException.class);
        StringWriter writer = new StringWriter();
        xmlEncoderASCII.text(writer, "Invalid \u001F Character", true);
    }

    @Test
    public void testTextChar() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.text(writer, '<');
        assertEquals("&lt;", writer.toString());
    }

    @Test
    public void testTextCharWithEscapeAmpersands() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.text(writer, '&', true);
        assertEquals("&amp;", writer.toString());
    }

    @Test
    public void testWhitespace() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.whitespace(writer, " \t\n\r");
        assertEquals(" \t\n\r", writer.toString());
    }

    @Test
    public void testWhitespaceWithInvalidXMLCharacter() throws Exception {
        thrown.expect(InvalidXMLException.class);
        StringWriter writer = new StringWriter();
        xmlEncoderASCII.whitespace(writer, "Invalid \u001F Character");
    }

    @Test
    public void testAttributeWithValidQuotationMark() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.attribute(writer, "name", "value", '"', true);
        assertEquals(" name=\"value\"", writer.toString());
    }

    @Test
    public void testAttributeWithInvalidQuotationMark() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.attribute(writer, "name", "value", 'x', true);
    }

    @Test
    public void testAttributeWithEscapeAmpersands() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.attribute(writer, "name", "value & more", '"', true);
        assertEquals(" name=\"value &amp; more\"", writer.toString());
    }

    @Test
    public void testAttributeWithoutEscapeAmpersands() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.attribute(writer, "name", "value & more", '"', false);
        assertEquals(" name=\"value & more\"", writer.toString());
    }
}