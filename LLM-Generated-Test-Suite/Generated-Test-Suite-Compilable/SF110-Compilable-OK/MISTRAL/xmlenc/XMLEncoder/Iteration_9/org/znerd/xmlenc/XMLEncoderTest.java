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
    public void setUp() throws Exception {
        encoder = XMLEncoder.getEncoder("UTF-8");
        writer = new StringWriter();
    }

    @Test
    public void testGetEncoder() throws Exception {
        XMLEncoder encoder = XMLEncoder.getEncoder("UTF-8");
        assertNotNull(encoder);
        assertEquals("UTF-8", encoder.getEncoding());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEncoderNullEncoding() throws Exception {
        XMLEncoder.getEncoder(null);
    }

    @Test(expected = UnsupportedEncodingException.class)
    public void testGetEncoderUnsupportedEncoding() throws Exception {
        XMLEncoder.getEncoder("UNSUPPORTED");
    }

    @Test
    public void testDeclaration() throws Exception {
        encoder.declaration(writer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", writer.toString());
    }

    @Test
    public void testTextString() throws Exception {
        encoder.text(writer, "test", true);
        assertEquals("test", writer.toString());
    }

    @Test
    public void testTextCharArray() throws Exception {
        encoder.text(writer, "test".toCharArray(), 0, 4, true);
        assertEquals("test", writer.toString());
    }

    @Test
    public void testTextChar() throws Exception {
        encoder.text(writer, 'a');
        assertEquals("a", writer.toString());
    }

    @Test
    public void testTextCharEscapeAmpersands() throws Exception {
        encoder.text(writer, '&', true);
        assertEquals("&amp;", writer.toString());
    }

    @Test
    public void testWhitespaceString() throws Exception {
        encoder.whitespace(writer, " \t\n\r");
        assertEquals(" \t\n\r", writer.toString());
    }

    @Test
    public void testWhitespaceCharArray() throws Exception {
        encoder.whitespace(writer, " \t\n\r".toCharArray(), 0, 4);
        assertEquals(" \t\n\r", writer.toString());
    }

    @Test
    public void testAttribute() throws Exception {
        encoder.attribute(writer, "name", "value", '"', true);
        assertEquals(" name=\"value\"", writer.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAttributeInvalidQuotationMark() throws Exception {
        encoder.attribute(writer, "name", "value", 'x', true);
    }

    @Test
    public void testEscapeCharacters() throws Exception {
        encoder.text(writer, "<>&\"'", true);
        assertEquals("&lt;&gt;&amp;&quot;&apos;", writer.toString());
    }

    @Test
    public void testInvalidXMLCharacter() throws Exception {
        thrown.expect(InvalidXMLException.class);
        thrown.expectMessage("The character 0x1 is not valid.");
        encoder.text(writer, "\u0001", true);
    }

    @Test
    public void testSevenBitEncoding() throws Exception {
        XMLEncoder sevenBitEncoder = XMLEncoder.getEncoder("US-ASCII");
        sevenBitEncoder.text(writer, "test", true);
        assertEquals("test", writer.toString());
    }

    @Test(expected = UnsupportedEncodingException.class)
    public void testUnsupportedSevenBitEncoding() throws Exception {
        XMLEncoder.getEncoder("ISO-2022-JP");
    }
}