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
    private DeflateCompressorInputStream deflateCompressorInputStream;

    @Before
    public void setUp() {
        mockInputStream = mock(InputStream.class);
    }

    @After
    public void tearDown() throws IOException {
        if (deflateCompressorInputStream != null) {
            deflateCompressorInputStream.close();
        }
    }

    @Test
    public void testReadSingleByte() throws IOException {
        when(mockInputStream.read()).thenReturn(100, -1);

        deflateCompressorInputStream = new DeflateCompressorInputStream(mockInputStream);

        int result = deflateCompressorInputStream.read();
        assertEquals(100, result);

        result = deflateCompressorInputStream.read();
        assertEquals(-1, result);
    }

    @Test
    public void testReadByteArray() throws IOException {
        byte[] buffer = new byte[10];
        when(mockInputStream.read(buffer, 0, 10)).thenReturn(5);

        deflateCompressorInputStream = new DeflateCompressorInputStream(mockInputStream);

        int bytesRead = deflateCompressorInputStream.read(buffer, 0, 10);
        assertEquals(5, bytesRead);
    }

    @Test
    public void testSkip() throws IOException {
        when(mockInputStream.skip(5)).thenReturn(5L);

        deflateCompressorInputStream = new DeflateCompressorInputStream(mockInputStream);

        long skipped = deflateCompressorInputStream.skip(5);
        assertEquals(5L, skipped);
    }

    @Test
    public void testAvailable() throws IOException {
        when(mockInputStream.available()).thenReturn(10);

        deflateCompressorInputStream = new DeflateCompressorInputStream(mockInputStream);

        int available = deflateCompressorInputStream.available();
        assertEquals(10, available);
    }

    @Test
    public void testClose() throws IOException {
        deflateCompressorInputStream = new DeflateCompressorInputStream(mockInputStream);
        deflateCompressorInputStream.close();

        verify(mockInputStream, times(1)).close();
    }

    @Test
    public void testConstructorWithParameters() {
        DeflateParameters parameters = new DeflateParameters();
        parameters.setWithZlibHeader(true);

        deflateCompressorInputStream = new DeflateCompressorInputStream(mockInputStream, parameters);

        assertNotNull(deflateCompressorInputStream);
    }

    @Test
    public void testConstructorWithoutParameters() {
        deflateCompressorInputStream = new DeflateCompressorInputStream(mockInputStream);

        assertNotNull(deflateCompressorInputStream);
    }
}