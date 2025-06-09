package org.apache.commons.compress.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Checksum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ChecksumCalculatingInputStreamTest {

    private Checksum checksum;
    private InputStream inputStream;
    private ChecksumCalculatingInputStream checksumStream;

    @Before
    public void setUp() {
        checksum = mock(Checksum.class);
        inputStream = new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5});
        checksumStream = new ChecksumCalculatingInputStream(checksum, inputStream);
    }

    @Test
    public void testReadSingleByte() throws IOException {
        int result = checksumStream.read();
        assertEquals(1, result);
        verify(checksum).update(1);
    }

    @Test
    public void testReadByteArray() throws IOException {
        byte[] buffer = new byte[5];
        int bytesRead = checksumStream.read(buffer);
        assertEquals(5, bytesRead);
        verify(checksum).update(buffer, 0, 5);
    }

    @Test
    public void testReadByteArrayWithOffsetAndLength() throws IOException {
        byte[] buffer = new byte[5];
        int bytesRead = checksumStream.read(buffer, 1, 3);
        assertEquals(3, bytesRead);
        verify(checksum).update(buffer, 1, 3);
    }

    @Test
    public void testSkip() throws IOException {
        long skipped = checksumStream.skip(1);
        assertEquals(1, skipped);
        verify(checksum).update(1);
    }

    @Test
    public void testGetValue() {
        when(checksum.getValue()).thenReturn(123L);
        long value = checksumStream.getValue();
        assertEquals(123L, value);
    }

    @Test
    public void testReadEndOfStream() throws IOException {
        byte[] buffer = new byte[5];
        checksumStream.read(buffer); // Read all bytes
        int result = checksumStream.read();
        assertEquals(-1, result);
    }

    @Test(expected = IOException.class)
    public void testIOExceptionOnReadSingleByte() throws IOException {
        InputStream mockInputStream = mock(InputStream.class);
        when(mockInputStream.read()).thenThrow(new IOException("Test Exception"));
        ChecksumCalculatingInputStream stream = new ChecksumCalculatingInputStream(checksum, mockInputStream);
        stream.read();
    }

    @Test(expected = IOException.class)
    public void testIOExceptionOnReadByteArray() throws IOException {
        InputStream mockInputStream = mock(InputStream.class);
        when(mockInputStream.read(any(byte[].class), anyInt(), anyInt())).thenThrow(new IOException("Test Exception"));
        ChecksumCalculatingInputStream stream = new ChecksumCalculatingInputStream(checksum, mockInputStream);
        stream.read(new byte[5]);
    }

    @Test(expected = IOException.class)
    public void testIOExceptionOnReadByteArrayWithOffsetAndLength() throws IOException {
        InputStream mockInputStream = mock(InputStream.class);
        when(mockInputStream.read(any(byte[].class), anyInt(), anyInt())).thenThrow(new IOException("Test Exception"));
        ChecksumCalculatingInputStream stream = new ChecksumCalculatingInputStream(checksum, mockInputStream);
        stream.read(new byte[5], 0, 5);
    }
}