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
    public void testRead() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        DeflateCompressorInputStream dcis = new DeflateCompressorInputStream(bais);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;
        while ((b = dcis.read()) != -1) {
            baos.write(b);
        }
        dcis.close();

        byte[] decompressedData = baos.toByteArray();
        Assert.assertArrayEquals(originalData, decompressedData);
    }

    @Test
    public void testReadWithBuffer() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        DeflateCompressorInputStream dcis = new DeflateCompressorInputStream(bais);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = dcis.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        dcis.close();

        byte[] decompressedData = baos.toByteArray();
        Assert.assertArrayEquals(originalData, decompressedData);
    }

    @Test
    public void testSkip() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        DeflateCompressorInputStream dcis = new DeflateCompressorInputStream(bais);

        long skipped = dcis.skip(5);
        Assert.assertEquals(5, skipped);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;
        while ((b = dcis.read()) != -1) {
            baos.write(b);
        }
        dcis.close();

        byte[] decompressedData = baos.toByteArray();
        Assert.assertArrayEquals("World!".getBytes(), decompressedData);
    }

    @Test
    public void testAvailable() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        DeflateCompressorInputStream dcis = new DeflateCompressorInputStream(bais);

        int available = dcis.available();
        Assert.assertTrue(available > 0);

        dcis.close();
    }

    @Test
    public void testClose() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        DeflateCompressorInputStream dcis = new DeflateCompressorInputStream(bais);

        dcis.close();
        Assert.assertTrue(bais.available() == 0);
    }

    @Test
    public void testConstructorWithParameters() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        DeflateParameters parameters = new DeflateParameters();
        DeflateCompressorInputStream dcis = new DeflateCompressorInputStream(bais, parameters);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;
        while ((b = dcis.read()) != -1) {
            baos.write(b);
        }
        dcis.close();

        byte[] decompressedData = baos.toByteArray();
        Assert.assertArrayEquals(originalData, decompressedData);
    }
}