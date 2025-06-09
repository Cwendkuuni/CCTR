package org.znerd.xmlenc;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.IOException;

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

    @Test
    public void testGetEncoderWithValidEncoding() throws UnsupportedEncodingException {
        XMLEncoder encoder = XMLEncoder.getEncoder("UTF-8");
        assertNotNull(encoder);
    }

    @Test
    public void testGetEncoderWithInvalidEncoding() throws UnsupportedEncodingException {
        thrown.expect(UnsupportedEncodingException.class);
        XMLEncoder.getEncoder("INVALID-ENCODING");
    }

    @Test
    public void testGetEncoderWithNullEncoding() throws UnsupportedEncodingException {
        thrown.expect(IllegalArgumentException.class);
        XMLEncoder.getEncoder(null);
    }

    @Test
    public void testGetEncoding() {
        assertEquals("UTF-8", xmlEncoderUTF8.getEncoding());
        assertEquals("US-ASCII", xmlEncoderASCII.getEncoding());
    }

    @Test
    public void testDeclaration() throws IOException {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.declaration(writer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", writer.toString());
    }

    @Test
    public void testTextWithString() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.text(writer, "Hello & World <XML>", true);
        assertEquals("Hello &amp; World &lt;XML&gt;", writer.toString());
    }

    @Test
    public void testTextWithCharArray() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        char[] text = "Hello & World <XML>".toCharArray();
        xmlEncoderUTF8.text(writer, text, 0, text.length, true);
        assertEquals("Hello &amp; World &lt;XML&gt;", writer.toString());
    }

    @Test
    public void testTextWithInvalidXMLCharacter() throws IOException, InvalidXMLException {
        thrown.expect(InvalidXMLException.class);
        StringWriter writer = new StringWriter();
        xmlEncoderASCII.text(writer, "Invalid \u001F Character", true);
    }

    @Test
    public void testTextWithChar() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.text(writer, '<');
        assertEquals("&lt;", writer.toString());
    }

    @Test
    public void testTextWithCharAndEscapeAmpersands() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.text(writer, '&', true);
        assertEquals("&amp;", writer.toString());
    }

    @Test
    public void testWhitespaceWithString() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.whitespace(writer, "   ");
        assertEquals("   ", writer.toString());
    }

    @Test
    public void testWhitespaceWithCharArray() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        char[] whitespace = "   ".toCharArray();
        xmlEncoderUTF8.whitespace(writer, whitespace, 0, whitespace.length);
        assertEquals("   ", writer.toString());
    }

    @Test
    public void testAttributeWithValidQuotationMark() throws IOException {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.attribute(writer, "attr", "value", '\"', true);
        assertEquals(" attr=\"value\"", writer.toString());
    }

    @Test
    public void testAttributeWithInvalidQuotationMark() throws IOException {
        thrown.expect(IllegalArgumentException.class);
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.attribute(writer, "attr", "value", 'x', true);
    }

    @After
    public void tearDown() {
        xmlEncoderUTF8 = null;
        xmlEncoderASCII = null;
    }
}