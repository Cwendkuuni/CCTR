package org.apache.commons.compress.utils;

import org.apache.commons.compress.utils.ChecksumCalculatingInputStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class ChecksumCalculatingInputStreamTest {

    private Checksum checksum;
    private ByteArrayInputStream inputStream;
    private ChecksumCalculatingInputStream checksumInputStream;

    @Before
    public void setUp() {
        checksum = new Adler32();
        inputStream = new ByteArrayInputStream("test data".getBytes());
        checksumInputStream = new ChecksumCalculatingInputStream(checksum, inputStream);
    }

    @Test
    public void testReadSingleByte() throws IOException {
        int data = checksumInputStream.read();
        Assert.assertEquals('t', data);
        Assert.assertEquals(checksum.getValue(), checksumInputStream.getValue());
    }

    @Test
    public void testReadByteArray() throws IOException {
        byte[] buffer = new byte[4];
        int bytesRead = checksumInputStream.read(buffer);
        Assert.assertEquals(4, bytesRead);
        Assert.assertEquals("test", new String(buffer, 0, bytesRead));
        Assert.assertEquals(checksum.getValue(), checksumInputStream.getValue());
    }

    @Test
    public void testReadByteArrayWithOffsetAndLength() throws IOException {
        byte[] buffer = new byte[10];
        int bytesRead = checksumInputStream.read(buffer, 2, 4);
        Assert.assertEquals(4, bytesRead);
        Assert.assertEquals("test", new String(buffer, 2, bytesRead));
        Assert.assertEquals(checksum.getValue(), checksumInputStream.getValue());
    }

    @Test
    public void testSkip() throws IOException {
        long skipped = checksumInputStream.skip(1);
        Assert.assertEquals(1, skipped);
        Assert.assertEquals(checksum.getValue(), checksumInputStream.getValue());
    }

    @Test
    public void testGetValue() throws IOException {
        checksumInputStream.read();
        long value = checksumInputStream.getValue();
        Assert.assertEquals(checksum.getValue(), value);
    }

    @Test
    public void testReadUntilEnd() throws IOException {
        byte[] buffer = new byte[100];
        int bytesRead;
        while ((bytesRead = checksumInputStream.read(buffer)) != -1) {
            // Read until the end
        }
        Assert.assertEquals(checksum.getValue(), checksumInputStream.getValue());
    }
}