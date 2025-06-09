package org.jsoup.helper;

import org.jsoup.helper.DataUtil;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class DataUtilTest {

    @Test
    public void testLoadFile() throws IOException {
        File tempFile = File.createTempFile("test", ".html");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("<html><head><meta charset=\"UTF-8\"></head><body>Test</body></html>");
        }

        Document doc = DataUtil.load(tempFile, "UTF-8", "http://example.com");
        assertNotNull(doc);
        assertEquals("Test", doc.body().text());
    }

    @Test
    public void testLoadInputStream() throws IOException {
        String html = "<html><head><meta charset=\"UTF-8\"></head><body>Test</body></html>";
        InputStream inputStream = new ByteArrayInputStream(html.getBytes(Charset.forName("UTF-8")));

        Document doc = DataUtil.load(inputStream, "UTF-8", "http://example.com");
        assertNotNull(doc);
        assertEquals("Test", doc.body().text());
    }

    @Test
    public void testLoadInputStreamWithParser() throws IOException {
        String html = "<html><head><meta charset=\"UTF-8\"></head><body>Test</body></html>";
        InputStream inputStream = new ByteArrayInputStream(html.getBytes(Charset.forName("UTF-8")));

        Document doc = DataUtil.load(inputStream, "UTF-8", "http://example.com", Parser.xmlParser());
        assertNotNull(doc);
        assertEquals("Test", doc.body().text());
    }

    @Test
    public void testCrossStreams() throws IOException {
        String data = "Test data";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        DataUtil.crossStreams(inputStream, outputStream);
        assertEquals(data, outputStream.toString());
    }

    @Test
    public void testParseByteData() {
        String html = "<html><head><meta charset=\"UTF-8\"></head><body>Test</body></html>";
        ByteBuffer byteBuffer = ByteBuffer.wrap(html.getBytes(Charset.forName("UTF-8")));

        Document doc = DataUtil.parseByteData(byteBuffer, "UTF-8", "http://example.com", Parser.htmlParser());
        assertNotNull(doc);
        assertEquals("Test", doc.body().text());
    }

    @Test
    public void testReadToByteBuffer() throws IOException {
        String data = "Test data";
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());

        ByteBuffer byteBuffer = DataUtil.readToByteBuffer(inputStream);
        assertEquals(data, new String(byteBuffer.array(), Charset.forName("UTF-8")));
    }

    @Test
    public void testReadFileToByteBuffer() throws IOException {
        File tempFile = File.createTempFile("test", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("Test data");
        }

        ByteBuffer byteBuffer = DataUtil.readFileToByteBuffer(tempFile);
        assertEquals("Test data", new String(byteBuffer.array(), Charset.forName("UTF-8")));
    }

    @Test
    public void testEmptyByteBuffer() {
        ByteBuffer byteBuffer = DataUtil.emptyByteBuffer();
        assertEquals(0, byteBuffer.capacity());
    }

    @Test
    public void testGetCharsetFromContentType() {
        String contentType = "text/html; charset=UTF-8";
        String charset = DataUtil.getCharsetFromContentType(contentType);
        assertEquals("UTF-8", charset);
    }

    @Test
    public void testMimeBoundary() {
        String boundary1 = DataUtil.mimeBoundary();
        String boundary2 = DataUtil.mimeBoundary();
        assertNotNull(boundary1);
        assertNotNull(boundary2);
        assertNotEquals(boundary1, boundary2);
        assertEquals(DataUtil.boundaryLength, boundary1.length());
    }
}