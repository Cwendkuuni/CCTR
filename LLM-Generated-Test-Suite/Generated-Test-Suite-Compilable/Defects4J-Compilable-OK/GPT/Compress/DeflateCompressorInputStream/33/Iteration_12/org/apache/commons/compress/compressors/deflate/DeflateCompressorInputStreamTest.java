package org.apache.commons.compress.compressors.deflate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;
import org.apache.commons.compress.compressors.deflate.DeflateParameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DeflateCompressorInputStreamTest {

    private InputStream mockInputStream;
    private DeflateCompressorInputStream deflateStream;

    @Before
    public void setUp() {
        mockInputStream = mock(InputStream.class);
    }

    @After
    public void tearDown() throws IOException {
        if (deflateStream != null) {
            deflateStream.close();
        }
    }

    @Test
    public void testConstructorWithInputStream() throws IOException {
        deflateStream = new DeflateCompressorInputStream(mockInputStream);
        assertNotNull(deflateStream);
    }

    @Test
    public void testConstructorWithInputStreamAndParameters() throws IOException {
        DeflateParameters parameters = new DeflateParameters();
        deflateStream = new DeflateCompressorInputStream(mockInputStream, parameters);
        assertNotNull(deflateStream);
    }

    @Test
    public void testReadSingleByte() throws IOException {
        when(mockInputStream.read()).thenReturn(100);
        deflateStream = new DeflateCompressorInputStream(mockInputStream);
        int result = deflateStream.read();
        assertEquals(100, result);
    }

    @Test
    public void testReadSingleByteEndOfStream() throws IOException {
        when(mockInputStream.read()).thenReturn(-1);
        deflateStream = new DeflateCompressorInputStream(mockInputStream);
        int result = deflateStream.read();
        assertEquals(-1, result);
    }

    @Test
    public void testReadByteArray() throws IOException {
        byte[] buffer = new byte[10];
        when(mockInputStream.read(buffer, 0, 10)).thenReturn(10);
        deflateStream = new DeflateCompressorInputStream(mockInputStream);
        int result = deflateStream.read(buffer, 0, 10);
        assertEquals(10, result);
    }

    @Test
    public void testReadByteArrayEndOfStream() throws IOException {
        byte[] buffer = new byte[10];
        when(mockInputStream.read(buffer, 0, 10)).thenReturn(-1);
        deflateStream = new DeflateCompressorInputStream(mockInputStream);
        int result = deflateStream.read(buffer, 0, 10);
        assertEquals(-1, result);
    }

    @Test
    public void testSkip() throws IOException {
        when(mockInputStream.skip(5)).thenReturn(5L);
        deflateStream = new DeflateCompressorInputStream(mockInputStream);
        long result = deflateStream.skip(5);
        assertEquals(5L, result);
    }

    @Test
    public void testAvailable() throws IOException {
        when(mockInputStream.available()).thenReturn(3);
        deflateStream = new DeflateCompressorInputStream(mockInputStream);
        int result = deflateStream.available();
        assertEquals(3, result);
    }

    @Test
    public void testClose() throws IOException {
        deflateStream = new DeflateCompressorInputStream(mockInputStream);
        deflateStream.close();
        verify(mockInputStream, times(1)).close();
    }
}