package org.apache.commons.compress.compressors.deflate;

import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;
import org.apache.commons.compress.compressors.deflate.DeflateParameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import static org.junit.Assert.*;

public class DeflateCompressorInputStreamTest {

    private InputStream compressedInputStream;
    private DeflateCompressorInputStream deflateCompressorInputStream;

    @Before
    public void setUp() throws Exception {
        // Create a sample compressed input stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Deflater deflater = new Deflater();
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater);
        deflaterOutputStream.write("Sample data".getBytes());
        deflaterOutputStream.close();
        byte[] compressedData = byteArrayOutputStream.toByteArray();
        compressedInputStream = new ByteArrayInputStream(compressedData);

        deflateCompressorInputStream = new DeflateCompressorInputStream(compressedInputStream);
    }

    @After
    public void tearDown() throws Exception {
        deflateCompressorInputStream.close();
        compressedInputStream.close();
    }

    @Test
    public void testRead() throws IOException {
        int data;
        StringBuilder result = new StringBuilder();
        while ((data = deflateCompressorInputStream.read()) != -1) {
            result.append((char) data);
        }
        assertEquals("Sample data", result.toString());
    }

    @Test
    public void testReadByteArray() throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = deflateCompressorInputStream.read(buffer, 0, buffer.length);
        assertEquals("Sample data", new String(buffer, 0, bytesRead));
    }

    @Test
    public void testSkip() throws IOException {
        long skipped = deflateCompressorInputStream.skip(5);
        assertEquals(5, skipped);
        int data;
        StringBuilder result = new StringBuilder();
        while ((data = deflateCompressorInputStream.read()) != -1) {
            result.append((char) data);
        }
        assertEquals(" data", result.toString());
    }

    @Test
    public void testAvailable() throws IOException {
        assertTrue(deflateCompressorInputStream.available() > 0);
    }

    @Test
    public void testClose() throws IOException {
        deflateCompressorInputStream.close();
        assertThrows(IOException.class, () -> deflateCompressorInputStream.read());
    }

    @Test
    public void testConstructorWithParameters() throws IOException {
        DeflateParameters parameters = new DeflateParameters();
        DeflateCompressorInputStream streamWithParams = new DeflateCompressorInputStream(compressedInputStream, parameters);
        int data;
        StringBuilder result = new StringBuilder();
        while ((data = streamWithParams.read()) != -1) {
            result.append((char) data);
        }
        assertEquals("Sample data", result.toString());
        streamWithParams.close();
    }
}