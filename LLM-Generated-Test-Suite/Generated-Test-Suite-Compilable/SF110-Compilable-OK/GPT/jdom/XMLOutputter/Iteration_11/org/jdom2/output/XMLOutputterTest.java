package org.jdom2.output;

import org.jdom2.*;
import org.jdom2.output.*;
import org.jdom2.output.support.*;
import org.junit.*;
import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class XMLOutputterTest {

    private XMLOutputter xmlOutputter;
    private Format format;
    private XMLOutputProcessor processor;

    @Before
    public void setUp() {
        format = Format.getPrettyFormat();
        processor = new AbstractXMLOutputProcessor() {
            @Override
            protected void printDeclaration(Writer out, FormatStack fstack) throws IOException {
                // Custom implementation for testing
            }
        };
        xmlOutputter = new XMLOutputter(format, processor);
    }

    @Test
    public void testDefaultConstructor() {
        XMLOutputter defaultOutputter = new XMLOutputter();
        assertNotNull(defaultOutputter.getFormat());
        assertNotNull(defaultOutputter.getXMLOutputProcessor());
    }

    @Test
    public void testConstructorWithFormat() {
        XMLOutputter outputter = new XMLOutputter(format);
        assertEquals(format, outputter.getFormat());
    }

    @Test
    public void testConstructorWithProcessor() {
        XMLOutputter outputter = new XMLOutputter(processor);
        assertEquals(processor, outputter.getXMLOutputProcessor());
    }

    @Test
    public void testSetAndGetFormat() {
        Format newFormat = Format.getCompactFormat();
        xmlOutputter.setFormat(newFormat);
        assertEquals(newFormat, xmlOutputter.getFormat());
    }

    @Test
    public void testSetAndGetProcessor() {
        XMLOutputProcessor newProcessor = new AbstractXMLOutputProcessor() {
            @Override
            protected void printDeclaration(Writer out, FormatStack fstack) throws IOException {
                // Custom implementation for testing
            }
        };
        xmlOutputter.setXMLOutputProcessor(newProcessor);
        assertEquals(newProcessor, xmlOutputter.getXMLOutputProcessor());
    }

    @Test
    public void testOutputDocumentToString() throws IOException {
        Document doc = new Document(new Element("root"));
        String output = xmlOutputter.outputString(doc);
        assertNotNull(output);
        assertTrue(output.contains("root"));
    }

    @Test
    public void testOutputElementToString() throws IOException {
        Element element = new Element("element");
        String output = xmlOutputter.outputString(element);
        assertNotNull(output);
        assertTrue(output.contains("element"));
    }

    @Test
    public void testOutputToOutputStream() throws IOException {
        Document doc = new Document(new Element("root"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        xmlOutputter.output(doc, out);
        String output = new String(out.toByteArray());
        assertTrue(output.contains("root"));
    }

    @Test
    public void testEscapeAttributeEntities() {
        String input = "Test & < > \" '";
        String escaped = xmlOutputter.escapeAttributeEntities(input);
        assertNotNull(escaped);
        assertFalse(escaped.contains("&"));
    }

    @Test
    public void testEscapeElementEntities() {
        String input = "Test & < >";
        String escaped = xmlOutputter.escapeElementEntities(input);
        assertNotNull(escaped);
        assertFalse(escaped.contains("&"));
    }

    @Test
    public void testClone() {
        XMLOutputter clone = xmlOutputter.clone();
        assertNotSame(xmlOutputter, clone);
        assertEquals(xmlOutputter.getFormat(), clone.getFormat());
        assertEquals(xmlOutputter.getXMLOutputProcessor(), clone.getXMLOutputProcessor());
    }

    @Test
    public void testToString() {
        String description = xmlOutputter.toString();
        assertNotNull(description);
        assertTrue(description.contains("XMLOutputter"));
    }
}