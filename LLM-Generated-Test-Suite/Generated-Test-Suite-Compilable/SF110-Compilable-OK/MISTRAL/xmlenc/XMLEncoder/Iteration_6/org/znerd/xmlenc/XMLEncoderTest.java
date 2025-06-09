package org.znerd.xmlenc;

import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class XMLEncoderTest {

    private XMLEncoder encoder;
    private StringWriter writer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws UnsupportedEncodingException {
        encoder = XMLEncoder.getEncoder("UTF-8");
        writer = new StringWriter();
    }

    @Test
    public void testGetEncoder() throws UnsupportedEncodingException {
        assertNotNull(XMLEncoder.getEncoder("UTF-8"));
        assertNotNull(XMLEncoder.getEncoder("UTF-16"));
        assertNotNull(XMLEncoder.getEncoder("US-ASCII"));
        assertNotNull(XMLEncoder.getEncoder("ISO-8859-1"));

        thrown.expect(UnsupportedEncodingException.class);
        XMLEncoder.getEncoder("UnknownEncoding");
    }

    @Test
    public void testConstructor() throws UnsupportedEncodingException {
        assertNotNull(new XMLEncoder("UTF-8"));
        assertNotNull(new XMLEncoder("UTF-16"));
        assertNotNull(new XMLEncoder("US-ASCII"));
        assertNotNull(new XMLEncoder("ISO-8859-1"));

        thrown.expect(UnsupportedEncodingException.class);
        new XMLEncoder("UnknownEncoding");

        thrown.expect(IllegalArgumentException.class);
        new XMLEncoder(null);
    }

    @Test
    public void testGetEncoding() {
        assertEquals("UTF-8", encoder.getEncoding());
    }

    @Test
    public void testDeclaration() throws IOException {
        encoder.declaration(writer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", writer.toString());
    }

    @Test
    public void testTextString() throws IOException, InvalidXMLException {
        encoder.text(writer, "test", true);
        assertEquals("test", writer.toString());

        writer.getBuffer().setLength(0); // Clear the writer
        encoder.text(writer, "<test>", true);
        assertEquals("&lt;test&gt;", writer.toString());
    }

    @Test
    public void testTextCharArray() throws IOException, InvalidXMLException {
        encoder.text(writer, "test".toCharArray(), 0, 4, true);
        assertEquals("test", writer.toString());

        writer.getBuffer().setLength(0); // Clear the writer
        encoder.text(writer, "<test>".toCharArray(), 0, 6, true);
        assertEquals("&lt;test&gt;", writer.toString());
    }

    @Test
    public void testTextChar() throws IOException, InvalidXMLException {
        encoder.text(writer, '<');
        assertEquals("&lt;", writer.toString());

        writer.getBuffer().setLength(0); // Clear the writer
        encoder.text(writer, '>');
        assertEquals("&gt;", writer.toString());

        writer.getBuffer().setLength(0); // Clear the writer
        encoder.text(writer, '&');
        assertEquals("&", writer.toString());
    }

    @Test
    public void testTextCharWithEscapeAmpersands() throws IOException, InvalidXMLException {
        encoder.text(writer, '<', true);
        assertEquals("&lt;", writer.toString());

        writer.getBuffer().setLength(0); // Clear the writer
        encoder.text(writer, '>', true);
        assertEquals("&gt;", writer.toString());

        writer.getBuffer().setLength(0); // Clear the writer
        encoder.text(writer, '&', true);
        assertEquals("&amp;", writer.toString());
    }

    @Test
    public void testWhitespaceString() throws IOException, InvalidXMLException {
        encoder.whitespace(writer, " \t\n\r");
        assertEquals(" \t\n\r", writer.toString());
    }

    @Test
    public void testWhitespaceCharArray() throws IOException, InvalidXMLException {
        encoder.whitespace(writer, " \t\n\r".toCharArray(), 0, 4);
        assertEquals(" \t\n\r", writer.toString());
    }

    @Test
    public void testAttribute() throws IOException {
        encoder.attribute(writer, "name", "value", '"', true);
        assertEquals(" name=\"value\"", writer.toString());

        writer.getBuffer().setLength(0); // Clear the writer
        encoder.attribute(writer, "name", "value", '\'', true);
        assertEquals(" name='value'", writer.toString());

        thrown.expect(IllegalArgumentException.class);
        encoder.attribute(writer, "name", "value", 'x', true);
    }
}