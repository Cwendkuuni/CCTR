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

    private XMLEncoder xmlEncoder;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws UnsupportedEncodingException {
        xmlEncoder = XMLEncoder.getEncoder("UTF-8");
    }

    @After
    public void tearDown() {
        xmlEncoder = null;
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
        assertEquals("UTF-8", xmlEncoder.getEncoding());
    }

    @Test
    public void testDeclaration() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoder.declaration(writer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", writer.toString());
    }

    @Test
    public void testTextWithValidInput() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoder.text(writer, "Hello <World> & Everyone", true);
        assertEquals("Hello &lt;World&gt; &amp; Everyone", writer.toString());
    }

    @Test
    public void testTextWithInvalidXMLCharacter() throws Exception {
        thrown.expect(InvalidXMLException.class);
        StringWriter writer = new StringWriter();
        xmlEncoder.text(writer, "\u0001", true);
    }

    @Test
    public void testTextWithChar() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoder.text(writer, '<');
        assertEquals("&lt;", writer.toString());
    }

    @Test
    public void testTextWithCharAndEscapeAmpersands() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoder.text(writer, '&', true);
        assertEquals("&amp;", writer.toString());
    }

    @Test
    public void testWhitespaceWithValidInput() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoder.whitespace(writer, " \t\n\r");
        assertEquals(" \t\n\r", writer.toString());
    }

    @Test
    public void testWhitespaceWithInvalidXMLCharacter() throws Exception {
        thrown.expect(InvalidXMLException.class);
        StringWriter writer = new StringWriter();
        xmlEncoder.whitespace(writer, "\u0001");
    }

    @Test
    public void testAttributeWithValidInput() throws Exception {
        StringWriter writer = new StringWriter();
        xmlEncoder.attribute(writer, "name", "value", '"', true);
        assertEquals(" name=\"value\"", writer.toString());
    }

    @Test
    public void testAttributeWithInvalidQuotationMark() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        StringWriter writer = new StringWriter();
        xmlEncoder.attribute(writer, "name", "value", 'x', true);
    }
}