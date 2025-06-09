package org.jdom2.output;

import org.jdom2.*;
import org.jdom2.output.*;
import org.jdom2.output.support.*;
import org.junit.*;
import org.mockito.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class XMLOutputterTest {

    private XMLOutputter xmlOutputter;
    private Format mockFormat;
    private XMLOutputProcessor mockProcessor;

    @Before
    public void setUp() {
        mockFormat = mock(Format.class);
        mockProcessor = mock(XMLOutputProcessor.class);
        xmlOutputter = new XMLOutputter(mockFormat, mockProcessor);
    }

    @Test
    public void testDefaultConstructor() {
        XMLOutputter defaultOutputter = new XMLOutputter();
        assertNotNull(defaultOutputter.getFormat());
        assertNotNull(defaultOutputter.getXMLOutputProcessor());
    }

    @Test
    public void testConstructorWithFormat() {
        XMLOutputter outputter = new XMLOutputter(mockFormat);
        assertEquals(mockFormat, outputter.getFormat());
    }

    @Test
    public void testConstructorWithProcessor() {
        XMLOutputter outputter = new XMLOutputter(mockProcessor);
        assertEquals(mockProcessor, outputter.getXMLOutputProcessor());
    }

    @Test
    public void testSetAndGetFormat() {
        Format newFormat = mock(Format.class);
        xmlOutputter.setFormat(newFormat);
        assertEquals(newFormat, xmlOutputter.getFormat());
    }

    @Test
    public void testSetAndGetXMLOutputProcessor() {
        XMLOutputProcessor newProcessor = mock(XMLOutputProcessor.class);
        xmlOutputter.setXMLOutputProcessor(newProcessor);
        assertEquals(newProcessor, xmlOutputter.getXMLOutputProcessor());
    }

    @Test
    public void testOutputDocumentToOutputStream() throws IOException {
        Document mockDoc = mock(Document.class);
        OutputStream mockOut = mock(OutputStream.class);
        xmlOutputter.output(mockDoc, mockOut);
        verify(mockProcessor).process(any(Writer.class), eq(mockFormat), eq(mockDoc));
    }

    @Test
    public void testOutputDocTypeToOutputStream() throws IOException {
        DocType mockDocType = mock(DocType.class);
        OutputStream mockOut = mock(OutputStream.class);
        xmlOutputter.output(mockDocType, mockOut);
        verify(mockProcessor).process(any(Writer.class), eq(mockFormat), eq(mockDocType));
    }

    @Test
    public void testOutputElementToOutputStream() throws IOException {
        Element mockElement = mock(Element.class);
        OutputStream mockOut = mock(OutputStream.class);
        xmlOutputter.output(mockElement, mockOut);
        verify(mockProcessor).process(any(Writer.class), eq(mockFormat), eq(mockElement));
    }

    @Test
    public void testOutputElementContentToOutputStream() throws IOException {
        Element mockElement = mock(Element.class);
        OutputStream mockOut = mock(OutputStream.class);
        xmlOutputter.outputElementContent(mockElement, mockOut);
        verify(mockProcessor).process(any(Writer.class), eq(mockFormat), eq(mockElement.getContent()));
    }

    @Test
    public void testOutputListToOutputStream() throws IOException {
        List<Content> mockList = mock(List.class);
        OutputStream mockOut = mock(OutputStream.class);
        xmlOutputter.output(mockList, mockOut);
        verify(mockProcessor).process(any(Writer.class), eq(mockFormat), eq(mockList));
    }

    @Test
    public void testOutputCDATAToOutputStream() throws IOException {
        CDATA mockCDATA = mock(CDATA.class);
        OutputStream mockOut = mock(OutputStream.class);
        xmlOutputter.output(mockCDATA, mockOut);
        verify(mockProcessor).process(any(Writer.class), eq(mockFormat), eq(mockCDATA));
    }

    @Test
    public void testOutputTextToOutputStream() throws IOException {
        Text mockText = mock(Text.class);
        OutputStream mockOut = mock(OutputStream.class);
        xmlOutputter.output(mockText, mockOut);
        verify(mockProcessor).process(any(Writer.class), eq(mockFormat), eq(mockText));
    }

    @Test
    public void testOutputCommentToOutputStream() throws IOException {
        Comment mockComment = mock(Comment.class);
        OutputStream mockOut = mock(OutputStream.class);
        xmlOutputter.output(mockComment, mockOut);
        verify(mockProcessor).process(any(Writer.class), eq(mockFormat), eq(mockComment));
    }

    @Test
    public void testOutputProcessingInstructionToOutputStream() throws IOException {
        ProcessingInstruction mockPI = mock(ProcessingInstruction.class);
        OutputStream mockOut = mock(OutputStream.class);
        xmlOutputter.output(mockPI, mockOut);
        verify(mockProcessor).process(any(Writer.class), eq(mockFormat), eq(mockPI));
    }

    @Test
    public void testOutputEntityRefToOutputStream() throws IOException {
        EntityRef mockEntity = mock(EntityRef.class);
        OutputStream mockOut = mock(OutputStream.class);
        xmlOutputter.output(mockEntity, mockOut);
        verify(mockProcessor).process(any(Writer.class), eq(mockFormat), eq(mockEntity));
    }

    @Test
    public void testOutputStringDocument() {
        Document mockDoc = mock(Document.class);
        String result = xmlOutputter.outputString(mockDoc);
        assertNotNull(result);
    }

    @Test
    public void testOutputStringDocType() {
        DocType mockDocType = mock(DocType.class);
        String result = xmlOutputter.outputString(mockDocType);
        assertNotNull(result);
    }

    @Test
    public void testOutputStringElement() {
        Element mockElement = mock(Element.class);
        String result = xmlOutputter.outputString(mockElement);
        assertNotNull(result);
    }

    @Test
    public void testOutputStringList() {
        List<Content> mockList = mock(List.class);
        String result = xmlOutputter.outputString(mockList);
        assertNotNull(result);
    }

    @Test
    public void testOutputStringCDATA() {
        CDATA mockCDATA = mock(CDATA.class);
        String result = xmlOutputter.outputString(mockCDATA);
        assertNotNull(result);
    }

    @Test
    public void testOutputStringText() {
        Text mockText = mock(Text.class);
        String result = xmlOutputter.outputString(mockText);
        assertNotNull(result);
    }

    @Test
    public void testOutputStringComment() {
        Comment mockComment = mock(Comment.class);
        String result = xmlOutputter.outputString(mockComment);
        assertNotNull(result);
    }

    @Test
    public void testOutputStringProcessingInstruction() {
        ProcessingInstruction mockPI = mock(ProcessingInstruction.class);
        String result = xmlOutputter.outputString(mockPI);
        assertNotNull(result);
    }

    @Test
    public void testOutputStringEntityRef() {
        EntityRef mockEntity = mock(EntityRef.class);
        String result = xmlOutputter.outputString(mockEntity);
        assertNotNull(result);
    }

    @Test
    public void testOutputElementContentString() {
        Element mockElement = mock(Element.class);
        String result = xmlOutputter.outputElementContentString(mockElement);
        assertNotNull(result);
    }

    @Test
    public void testEscapeAttributeEntities() {
        String input = "test";
        String result = xmlOutputter.escapeAttributeEntities(input);
        assertNotNull(result);
    }

    @Test
    public void testEscapeElementEntities() {
        String input = "test";
        String result = xmlOutputter.escapeElementEntities(input);
        assertNotNull(result);
    }

    @Test
    public void testClone() {
        XMLOutputter clonedOutputter = xmlOutputter.clone();
        assertNotSame(xmlOutputter, clonedOutputter);
        assertEquals(xmlOutputter.getFormat(), clonedOutputter.getFormat());
    }

    @Test
    public void testToString() {
        String result = xmlOutputter.toString();
        assertNotNull(result);
    }
}