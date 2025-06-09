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

    @After
    public void tearDown() {
        xmlEncoderUTF8 = null;
        xmlEncoderASCII = null;
    }

    @Test
    public void testGetEncoderWithNullEncoding() throws UnsupportedEncodingException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("encoding == null");
        XMLEncoder.getEncoder(null);
    }

    @Test
    public void testGetEncoderWithUnsupportedEncoding() throws UnsupportedEncodingException {
        thrown.expect(UnsupportedEncodingException.class);
        XMLEncoder.getEncoder("UNSUPPORTED");
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
    public void testTextWithValidInput() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.text(writer, "Hello <world> & everyone!", true);
        assertEquals("Hello &lt;world&gt; &amp; everyone!", writer.toString());
    }

    @Test
    public void testTextWithInvalidXMLCharacter() throws IOException, InvalidXMLException {
        thrown.expect(InvalidXMLException.class);
        StringWriter writer = new StringWriter();
        xmlEncoderASCII.text(writer, "Invalid \u0001 character", true);
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
    public void testWhitespaceWithValidInput() throws IOException, InvalidXMLException {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.whitespace(writer, " \t\n\r");
        assertEquals(" \t\n\r", writer.toString());
    }

    @Test
    public void testWhitespaceWithInvalidInput() throws IOException, InvalidXMLException {
        thrown.expect(InvalidXMLException.class);
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.whitespace(writer, "Invalid \u0001 whitespace");
    }

    @Test
    public void testAttributeWithValidInput() throws IOException {
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.attribute(writer, "attr", "value", '"', true);
        assertEquals(" attr=\"value\"", writer.toString());
    }

    @Test
    public void testAttributeWithInvalidQuotationMark() throws IOException {
        thrown.expect(IllegalArgumentException.class);
        StringWriter writer = new StringWriter();
        xmlEncoderUTF8.attribute(writer, "attr", "value", 'x', true);
    }
}