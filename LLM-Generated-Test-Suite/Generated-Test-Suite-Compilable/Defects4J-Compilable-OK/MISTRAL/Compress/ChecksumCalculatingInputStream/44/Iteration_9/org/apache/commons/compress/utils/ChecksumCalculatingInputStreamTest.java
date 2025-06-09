package org.apache.commons.compress.utils;

import org.apache.commons.compress.utils.ChecksumCalculatingInputStream;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

import static org.junit.Assert.*;

public class ChecksumCalculatingInputStreamTest {

    private Checksum checksum;
    private InputStream inputStream;
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
        assertEquals('t', data);
        assertEquals(checksum.getValue(), checksumInputStream.getValue());
    }

    @Test
    public void testReadByteArray() throws IOException {
        byte[] buffer = new byte[4];
        int bytesRead = checksumInputStream.read(buffer);
        assertEquals(4, bytesRead);
        assertEquals("test", new String(buffer, 0, bytesRead));
        assertEquals(checksum.getValue(), checksumInputStream.getValue());
    }

    @Test
    public void testReadByteArrayWithOffsetAndLength() throws IOException {
        byte[] buffer = new byte[10];
        int bytesRead = checksumInputStream.read(buffer, 2, 4);
        assertEquals(4, bytesRead);
        assertEquals("test", new String(buffer, 2, bytesRead));
        assertEquals(checksum.getValue(), checksumInputStream.getValue());
    }

    @Test
    public void testSkip() throws IOException {
        long skipped = checksumInputStream.skip(1);
        assertEquals(1, skipped);
        assertEquals(checksum.getValue(), checksumInputStream.getValue());
    }

    @Test
    public void testGetValue() {
        assertEquals(checksum.getValue(), checksumInputStream.getValue());
    }

    @Test
    public void testReadEOF() throws IOException {
        inputStream = new ByteArrayInputStream(new byte[0]);
        checksumInputStream = new ChecksumCalculatingInputStream(checksum, inputStream);
        int data = checksumInputStream.read();
        assertEquals(-1, data);
    }

    @Test
    public void testReadByteArrayEOF() throws IOException {
        inputStream = new ByteArrayInputStream(new byte[0]);
        checksumInputStream = new ChecksumCalculatingInputStream(checksum, inputStream);
        byte[] buffer = new byte[4];
        int bytesRead = checksumInputStream.read(buffer);
        assertEquals(-1, bytesRead);
    }

    @Test
    public void testReadByteArrayWithOffsetAndLengthEOF() throws IOException {
        inputStream = new ByteArrayInputStream(new byte[0]);
        checksumInputStream = new ChecksumCalculatingInputStream(checksum, inputStream);
        byte[] buffer = new byte[10];
        int bytesRead = checksumInputStream.read(buffer, 2, 4);
        assertEquals(-1, bytesRead);
    }

    @Test
    public void testSkipEOF() throws IOException {
        inputStream = new ByteArrayInputStream(new byte[0]);
        checksumInputStream = new ChecksumCalculatingInputStream(checksum, inputStream);
        long skipped = checksumInputStream.skip(1);
        assertEquals(0, skipped);
    }

    @Test(expected = IOException.class)
    public void testReadIOException() throws IOException {
        InputStream mockInputStream = Mockito.mock(InputStream.class);
        Mockito.when(mockInputStream.read()).thenThrow(new IOException());
        checksumInputStream = new ChecksumCalculatingInputStream(checksum, mockInputStream);
        checksumInputStream.read();
    }

    @Test(expected = IOException.class)
    public void testReadByteArrayIOException() throws IOException {
        InputStream mockInputStream = Mockito.mock(InputStream.class);
        Mockito.when(mockInputStream.read(Mockito.any(byte[].class), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new IOException());
        checksumInputStream = new ChecksumCalculatingInputStream(checksum, mockInputStream);
        checksumInputStream.read(new byte[4]);
    }

    @Test(expected = IOException.class)
    public void testReadByteArrayWithOffsetAndLengthIOException() throws IOException {
        InputStream mockInputStream = Mockito.mock(InputStream.class);
        Mockito.when(mockInputStream.read(Mockito.any(byte[].class), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new IOException());
        checksumInputStream = new ChecksumCalculatingInputStream(checksum, mockInputStream);
        checksumInputStream.read(new byte[10], 2, 4);
    }

    @Test(expected = IOException.class)
    public void testSkipIOException() throws IOException {
        InputStream mockInputStream = Mockito.mock(InputStream.class);
        Mockito.when(mockInputStream.read()).thenThrow(new IOException());
        checksumInputStream = new ChecksumCalculatingInputStream(checksum, mockInputStream);
        checksumInputStream.skip(1);
    }
}