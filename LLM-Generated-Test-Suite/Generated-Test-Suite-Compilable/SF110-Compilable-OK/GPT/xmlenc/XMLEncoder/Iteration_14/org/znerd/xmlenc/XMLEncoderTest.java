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

    @Test(expected = NullPointerException.class)
    public void testDeclarationWithNullWriter() throws IOException {
        utf8Encoder.declaration(null);
    }

    @Test
    public void testTextWithString() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        utf8Encoder.text(writer, "Hello <World> & Everyone", true);
        assertEquals("Hello &lt;World&gt; &amp; Everyone", writer.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testTextWithNullWriter() throws IOException, InvalidXMLException {
        utf8Encoder.text(null, "Hello", true);
    }

    @Test(expected = InvalidXMLException.class)
    public void testTextWithInvalidCharacter() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        asciiEncoder.text(writer, "\u007f", true);
    }

    @Test
    public void testTextWithChar() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        utf8Encoder.text(writer, '<');
        assertEquals("&lt;", writer.toString());
    }

    @Test
    public void testTextWithCharAndEscapeAmpersands() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        utf8Encoder.text(writer, '&', true);
        assertEquals("&amp;", writer.toString());
    }

    @Test
    public void testWhitespaceWithString() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        utf8Encoder.whitespace(writer, " \t\n");
        assertEquals(" \t\n", writer.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testWhitespaceWithNullWriter() throws IOException, InvalidXMLException {
        utf8Encoder.whitespace(null, " ");
    }

    @Test
    public void testAttribute() throws IOException {
        StringWriter writer = new StringWriter();
        utf8Encoder.attribute(writer, "name", "value", '"', true);
        assertEquals(" name=\"value\"", writer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAttributeWithInvalidQuotationMark() throws IOException {
        StringWriter writer = new StringWriter();
        utf8Encoder.attribute(writer, "name", "value", 'x', true);
    }

    @Test(expected = NullPointerException.class)
    public void testAttributeWithNullWriter() throws IOException {
        utf8Encoder.attribute(null, "name", "value", '"', true);
    }
}