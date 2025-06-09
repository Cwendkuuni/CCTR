package org.apache.commons.compress.compressors.deflate;

import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;
import org.apache.commons.compress.compressors.deflate.DeflateParameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import static org.junit.Assert.*;

public class DeflateCompressorInputStreamTest {

    private ByteArrayInputStream compressedInputStream;
    private DeflateCompressorInputStream deflateInputStream;

    @Before
    public void setUp() throws IOException {
        // Create a sample compressed data
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Deflater deflater = new Deflater();
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater);
        String sampleData = "Sample data to compress";
        deflaterOutputStream.write(sampleData.getBytes());
        deflaterOutputStream.close();

        byte[] compressedData = byteArrayOutputStream.toByteArray();
        compressedInputStream = new ByteArrayInputStream(compressedData);
        deflateInputStream = new DeflateCompressorInputStream(compressedInputStream);
    }

    @After
    public void tearDown() throws IOException {
        if (deflateInputStream != null) {
            deflateInputStream.close();
        }
        if (compressedInputStream != null) {
            compressedInputStream.close();
        }
    }

    @Test
    public void testConstructorWithInputStream() throws IOException {
        assertNotNull(deflateInputStream);
    }

    @Test
    public void testConstructorWithInputStreamAndParameters() throws IOException {
        DeflateParameters parameters = new DeflateParameters();
        DeflateCompressorInputStream stream = new DeflateCompressorInputStream(compressedInputStream, parameters);
        assertNotNull(stream);
    }

    @Test
    public void testRead() throws IOException {
        int data;
        StringBuilder decompressedData = new StringBuilder();
        while ((data = deflateInputStream.read()) != -1) {
            decompressedData.append((char) data);
        }
        assertEquals("Sample data to compress", decompressedData.toString());
    }

    @Test
    public void testReadByteArray() throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = deflateInputStream.read(buffer, 0, buffer.length);
        String decompressedData = new String(buffer, 0, bytesRead);
        assertEquals("Sample data to compress", decompressedData);
    }

    @Test
    public void testSkip() throws IOException {
        long skipped = deflateInputStream.skip(5);
        assertEquals(5, skipped);
    }

    @Test
    public void testAvailable() throws IOException {
        assertTrue(deflateInputStream.available() > 0);
    }

    @Test
    public void testClose() throws IOException {
        deflateInputStream.close();
        assertTrue(compressedInputStream.available() == 0);
    }
}