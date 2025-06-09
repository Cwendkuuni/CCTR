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

    private Checksum mockChecksum;
    private InputStream mockInputStream;
    private ChecksumCalculatingInputStream checksumStream;

    @Before
    public void setUp() {
        mockChecksum = mock(Checksum.class);
        mockInputStream = mock(InputStream.class);
        checksumStream = new ChecksumCalculatingInputStream(mockChecksum, mockInputStream);
    }

    @Test
    public void testReadSingleByte() throws IOException {
        when(mockInputStream.read()).thenReturn(100, -1);

        int result = checksumStream.read();
        assertEquals(100, result);
        verify(mockChecksum).update(100);

        result = checksumStream.read();
        assertEquals(-1, result);
    }

    @Test
    public void testReadByteArray() throws IOException {
        byte[] buffer = new byte[10];
        when(mockInputStream.read(buffer, 0, 10)).thenReturn(10);

        int bytesRead = checksumStream.read(buffer);
        assertEquals(10, bytesRead);
        verify(mockChecksum).update(buffer, 0, 10);
    }

    @Test
    public void testReadByteArrayWithOffsetAndLength() throws IOException {
        byte[] buffer = new byte[10];
        when(mockInputStream.read(buffer, 2, 5)).thenReturn(5);

        int bytesRead = checksumStream.read(buffer, 2, 5);
        assertEquals(5, bytesRead);
        verify(mockChecksum).update(buffer, 2, 5);
    }

    @Test
    public void testSkip() throws IOException {
        when(mockInputStream.read()).thenReturn(100, -1);

        long skipped = checksumStream.skip(1);
        assertEquals(1, skipped);
        verify(mockChecksum).update(100);

        skipped = checksumStream.skip(1);
        assertEquals(0, skipped);
    }

    @Test
    public void testGetValue() {
        when(mockChecksum.getValue()).thenReturn(12345L);

        long value = checksumStream.getValue();
        assertEquals(12345L, value);
    }

    @Test
    public void testReadEndOfStream() throws IOException {
        when(mockInputStream.read()).thenReturn(-1);

        int result = checksumStream.read();
        assertEquals(-1, result);
        verify(mockChecksum, never()).update(anyInt());
    }

    @Test
    public void testReadByteArrayEndOfStream() throws IOException {
        byte[] buffer = new byte[10];
        when(mockInputStream.read(buffer, 0, 10)).thenReturn(-1);

        int bytesRead = checksumStream.read(buffer);
        assertEquals(-1, bytesRead);
        verify(mockChecksum, never()).update(any(byte[].class), anyInt(), anyInt());
    }
}