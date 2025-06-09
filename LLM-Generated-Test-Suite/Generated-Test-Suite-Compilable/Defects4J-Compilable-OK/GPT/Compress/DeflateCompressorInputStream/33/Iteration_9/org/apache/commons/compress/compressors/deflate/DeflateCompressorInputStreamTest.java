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
    public void setUp() throws IOException {
        mockInputStream = mock(InputStream.class);
        deflateCompressorInputStream = new DeflateCompressorInputStream(mockInputStream);
    }

    @After
    public void tearDown() throws IOException {
        deflateCompressorInputStream.close();
    }

    @Test
    public void testReadSingleByte() throws IOException {
        when(mockInputStream.read()).thenReturn(100, -1);

        int result = deflateCompressorInputStream.read();
        assertEquals(100, result);

        result = deflateCompressorInputStream.read();
        assertEquals(-1, result);

        verify(mockInputStream, times(2)).read();
    }

    @Test
    public void testReadByteArray() throws IOException {
        byte[] buffer = new byte[10];
        when(mockInputStream.read(buffer, 0, 10)).thenReturn(10, -1);

        int result = deflateCompressorInputStream.read(buffer, 0, 10);
        assertEquals(10, result);

        result = deflateCompressorInputStream.read(buffer, 0, 10);
        assertEquals(-1, result);

        verify(mockInputStream, times(2)).read(buffer, 0, 10);
    }

    @Test
    public void testSkip() throws IOException {
        when(mockInputStream.skip(5)).thenReturn(5L);

        long skipped = deflateCompressorInputStream.skip(5);
        assertEquals(5L, skipped);

        verify(mockInputStream, times(1)).skip(5);
    }

    @Test
    public void testAvailable() throws IOException {
        when(mockInputStream.available()).thenReturn(3);

        int available = deflateCompressorInputStream.available();
        assertEquals(3, available);

        verify(mockInputStream, times(1)).available();
    }

    @Test
    public void testClose() throws IOException {
        deflateCompressorInputStream.close();
        verify(mockInputStream, times(1)).close();
    }

    @Test
    public void testConstructorWithParameters() throws IOException {
        DeflateParameters parameters = new DeflateParameters();
        parameters.setWithZlibHeader(true);

        DeflateCompressorInputStream streamWithParams = new DeflateCompressorInputStream(mockInputStream, parameters);
        assertNotNull(streamWithParams);

        streamWithParams.close();
    }

    // Additional tests for edge cases and exceptions can be added here
}