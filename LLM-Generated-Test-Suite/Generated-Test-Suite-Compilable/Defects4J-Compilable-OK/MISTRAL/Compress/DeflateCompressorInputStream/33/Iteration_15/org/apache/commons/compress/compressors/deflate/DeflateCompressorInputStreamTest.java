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
    private DeflateCompressorInputStream deflateInputStream;

    @Before
    public void setUp() throws Exception {
        // Create a sample compressed input stream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Deflater deflater = new Deflater();
        DeflaterOutputStream dos = new DeflaterOutputStream(baos, deflater);
        dos.write("Sample data".getBytes());
        dos.close();
        byte[] compressedData = baos.toByteArray();
        compressedInputStream = new ByteArrayInputStream(compressedData);

        deflateInputStream = new DeflateCompressorInputStream(compressedInputStream);
    }

    @After
    public void tearDown() throws Exception {
        if (deflateInputStream != null) {
            deflateInputStream.close();
        }
        if (compressedInputStream != null) {
            compressedInputStream.close();
        }
    }

    @Test
    public void testRead() throws IOException {
        int data;
        StringBuilder sb = new StringBuilder();
        while ((data = deflateInputStream.read()) != -1) {
            sb.append((char) data);
        }
        assertEquals("Sample data", sb.toString());
    }

    @Test
    public void testReadByteArray() throws IOException {
        byte[] buffer = new byte[10];
        int bytesRead = deflateInputStream.read(buffer, 0, buffer.length);
        assertEquals("Sample data".getBytes(), new String(buffer, 0, bytesRead).getBytes());
    }

    @Test
    public void testSkip() throws IOException {
        long skipped = deflateInputStream.skip(5);
        assertEquals(5, skipped);
        byte[] buffer = new byte[5];
        int bytesRead = deflateInputStream.read(buffer, 0, buffer.length);
        assertEquals(" data", new String(buffer, 0, bytesRead));
    }

    @Test
    public void testAvailable() throws IOException {
        assertTrue(deflateInputStream.available() > 0);
    }

    @Test
    public void testClose() throws IOException {
        deflateInputStream.close();
        try {
            deflateInputStream.read();
            fail("Expected IOException after close");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testConstructorWithParameters() throws IOException {
        DeflateParameters parameters = new DeflateParameters();
        parameters.setWithZlibHeader(true);
        DeflateCompressorInputStream streamWithParams = new DeflateCompressorInputStream(compressedInputStream, parameters);
        int data;
        StringBuilder sb = new StringBuilder();
        while ((data = streamWithParams.read()) != -1) {
            sb.append((char) data);
        }
        assertEquals("Sample data", sb.toString());
        streamWithParams.close();
    }
}