package org.apache.commons.compress.compressors.deflate;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class DeflateCompressorInputStreamTest {

    private byte[] compressedData;
    private byte[] originalData;

    @Before
    public void setUp() throws IOException {
        originalData = "Hello, World!".getBytes();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Deflater deflater = new Deflater();
        DeflaterOutputStream dos = new DeflaterOutputStream(baos, deflater);
        dos.write(originalData);
        dos.close();
        compressedData = baos.toByteArray();
    }

    @Test
    public void testConstructorWithInputStream() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        DeflateCompressorInputStream dcis = new DeflateCompressorInputStream(bais);
        Assert.assertNotNull(dcis);
    }

    @Test
    public void testConstructorWithInputStreamAndParameters() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        DeflateParameters parameters = new DeflateParameters();
        DeflateCompressorInputStream dcis = new DeflateCompressorInputStream(bais, parameters);
        Assert.assertNotNull(dcis);
    }

    @Test
    public void testRead() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        DeflateCompressorInputStream dcis = new DeflateCompressorInputStream(bais);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;
        while ((b = dcis.read()) != -1) {
            baos.write(b);
        }
        byte[] decompressedData = baos.toByteArray();
        Assert.assertArrayEquals(originalData, decompressedData);
    }

    @Test
    public void testReadByteArray() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        DeflateCompressorInputStream dcis = new DeflateCompressorInputStream(bais);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = dcis.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        byte[] decompressedData = baos.toByteArray();
        Assert.assertArrayEquals(originalData, decompressedData);
    }

    @Test
    public void testSkip() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        DeflateCompressorInputStream dcis = new DeflateCompressorInputStream(bais);

        long skipped = dcis.skip(5);
        Assert.assertTrue(skipped > 0);
    }

    @Test
    public void testAvailable() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        DeflateCompressorInputStream dcis = new DeflateCompressorInputStream(bais);

        int available = dcis.available();
        Assert.assertTrue(available >= 0);
    }

    @Test
    public void testClose() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        DeflateCompressorInputStream dcis = new DeflateCompressorInputStream(bais);

        dcis.close();
        // Attempting to read after close should throw IOException
        try {
            dcis.read();
            Assert.fail("Expected IOException after close");
        } catch (IOException e) {
            // Expected
        }
    }
}