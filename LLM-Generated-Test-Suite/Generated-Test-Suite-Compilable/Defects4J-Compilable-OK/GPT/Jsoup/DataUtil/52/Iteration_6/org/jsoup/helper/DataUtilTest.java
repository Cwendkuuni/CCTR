package org.jsoup.helper;

import org.jsoup.helper.DataUtil;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class DataUtilTest {

    private File testFile;
    private InputStream testInputStream;
    private ByteArrayOutputStream testOutputStream;

    @Before
    public void setUp() throws IOException {
        // Setup a temporary file for testing
        testFile = File.createTempFile("test", ".html");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("<html><head><meta charset=\"UTF-8\"></head><body>Test</body></html>");
        }

        // Setup an input stream for testing
        testInputStream = new ByteArrayInputStream("<html><head><meta charset=\"UTF-8\"></head><body>Test</body></html>".getBytes());

        // Setup an output stream for testing
        testOutputStream = new ByteArrayOutputStream();
    }

    @Test
    public void testLoadFile() throws IOException {
        Document doc = DataUtil.load(testFile, "UTF-8", "http://example.com");
        assertNotNull(doc);
        assertEquals("Test", doc.body().text());
    }

    @Test
    public void testLoadInputStream() throws IOException {
        Document doc = DataUtil.load(testInputStream, "UTF-8", "http://example.com");
        assertNotNull(doc);
        assertEquals("Test", doc.body().text());
    }

    @Test
    public void testLoadInputStreamWithParser() throws IOException {
        Document doc = DataUtil.load(testInputStream, "UTF-8", "http://example.com", Parser.htmlParser());
        assertNotNull(doc);
        assertEquals("Test", doc.body().text());
    }

    @Test
    public void testCrossStreams() throws IOException {
        DataUtil.crossStreams(testInputStream, testOutputStream);
        assertEquals("<html><head><meta charset=\"UTF-8\"></head><body>Test</body></html>", testOutputStream.toString());
    }

    @Test
    public void testParseByteData() {
        ByteBuffer byteData = ByteBuffer.wrap("<html><head><meta charset=\"UTF-8\"></head><body>Test</body></html>".getBytes());
        Document doc = DataUtil.parseByteData(byteData, "UTF-8", "http://example.com", Parser.htmlParser());
        assertNotNull(doc);
        assertEquals("Test", doc.body().text());
    }

    @Test
    public void testReadToByteBufferWithMaxSize() throws IOException {
        ByteBuffer buffer = DataUtil.readToByteBuffer(testInputStream, 100);
        assertNotNull(buffer);
        assertTrue(buffer.remaining() > 0);
    }

    @Test
    public void testReadToByteBuffer() throws IOException {
        ByteBuffer buffer = DataUtil.readToByteBuffer(testInputStream);
        assertNotNull(buffer);
        assertTrue(buffer.remaining() > 0);
    }

    @Test
    public void testReadFileToByteBuffer() throws IOException {
        ByteBuffer buffer = DataUtil.readFileToByteBuffer(testFile);
        assertNotNull(buffer);
        assertTrue(buffer.remaining() > 0);
    }

    @Test
    public void testEmptyByteBuffer() {
        ByteBuffer buffer = DataUtil.emptyByteBuffer();
        assertNotNull(buffer);
        assertEquals(0, buffer.remaining());
    }

    @Test
    public void testGetCharsetFromContentType() {
        String charset = DataUtil.getCharsetFromContentType("text/html; charset=UTF-8");
        assertEquals("UTF-8", charset);

        charset = DataUtil.getCharsetFromContentType("text/html");
        assertNull(charset);
    }

    @Test
    public void testMimeBoundary() {
        String boundary = DataUtil.mimeBoundary();
        assertNotNull(boundary);
        assertEquals(DataUtil.boundaryLength, boundary.length());
    }
}