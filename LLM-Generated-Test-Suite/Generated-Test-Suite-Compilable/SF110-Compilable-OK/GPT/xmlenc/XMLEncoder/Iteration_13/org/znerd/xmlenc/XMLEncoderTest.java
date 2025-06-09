package org.znerd.xmlenc;

import org.junit.Before;
import org.junit.Test;
import org.znerd.xmlenc.XMLEncoder;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class XMLEncoderTest {

    private XMLEncoder utf8Encoder;
    private XMLEncoder asciiEncoder;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        utf8Encoder = XMLEncoder.getEncoder("UTF-8");
        asciiEncoder = XMLEncoder.getEncoder("US-ASCII");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEncoderWithNullEncoding() throws UnsupportedEncodingException {
        XMLEncoder.getEncoder(null);
    }

    @Test(expected = UnsupportedEncodingException.class)
    public void testGetEncoderWithUnsupportedEncoding() throws UnsupportedEncodingException {
        XMLEncoder.getEncoder("UNSUPPORTED");
    }

    @Test
    public void testGetEncoding() {
        assertEquals("UTF-8", utf8Encoder.getEncoding());
        assertEquals("US-ASCII", asciiEncoder.getEncoding());
    }

    @Test
    public void testDeclaration() throws IOException {
        StringWriter writer = new StringWriter();
        utf8Encoder.declaration(writer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", writer.toString());
    }

    @Test
    public void testTextWithString() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        utf8Encoder.text(writer, "Hello & World <XML>", true);
        assertEquals("Hello &amp; World &lt;XML&gt;", writer.toString());
    }

    @Test
    public void testTextWithCharArray() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        char[] text = "Hello & World <XML>".toCharArray();
        utf8Encoder.text(writer, text, 0, text.length, true);
        assertEquals("Hello &amp; World &lt;XML&gt;", writer.toString());
    }

    @Test(expected = InvalidXMLException.class)
    public void testTextWithInvalidCharacter() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        utf8Encoder.text(writer, '\u001F');
    }

    @Test
    public void testTextWithValidCharacter() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        utf8Encoder.text(writer, 'A');
        assertEquals("A", writer.toString());
    }

    @Test
    public void testWhitespaceWithString() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        utf8Encoder.whitespace(writer, " \t\n");
        assertEquals(" \t\n", writer.toString());
    }

    @Test
    public void testWhitespaceWithCharArray() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        char[] whitespace = " \t\n".toCharArray();
        utf8Encoder.whitespace(writer, whitespace, 0, whitespace.length);
        assertEquals(" \t\n", writer.toString());
    }

    @Test
    public void testAttribute() throws IOException {
        StringWriter writer = new StringWriter();
        utf8Encoder.attribute(writer, "name", "value & more", '"', true);
        assertEquals(" name=\"value &amp; more\"", writer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAttributeWithInvalidQuotationMark() throws IOException {
        StringWriter writer = new StringWriter();
        utf8Encoder.attribute(writer, "name", "value", 'x', true);
    }
}