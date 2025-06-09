package org.apache.commons.compress.utils;

import org.apache.commons.compress.utils.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IOUtilsTest {

    private InputStream inputStream;
    private OutputStream outputStream;

    @Before
    public void setUp() {
        inputStream = new ByteArrayInputStream("Test data".getBytes());
        outputStream = new ByteArrayOutputStream();
    }

    @After
    public void tearDown() throws IOException {
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
    }

    @Test
    public void testCopy() throws IOException {
        long bytesCopied = IOUtils.copy(inputStream, outputStream);
        assertEquals("Test data".length(), bytesCopied);
        assertEquals("Test data", outputStream.toString());
    }

    @Test
    public void testCopyWithBufferSize() throws IOException {
        long bytesCopied = IOUtils.copy(inputStream, outputStream, 5);
        assertEquals("Test data".length(), bytesCopied);
        assertEquals("Test data", outputStream.toString());
    }

    @Test
    public void testSkip() throws IOException {
        long bytesSkipped = IOUtils.skip(inputStream, 5);
        assertEquals(5, bytesSkipped);
        assertEquals(4, inputStream.available());
    }

    @Test
    public void testReadFully() throws IOException {
        byte[] buffer = new byte[9];
        int bytesRead = IOUtils.readFully(inputStream, buffer);
        assertEquals("Test data".length(), bytesRead);
        assertArrayEquals("Test data".getBytes(), buffer);
    }

    @Test
    public void testReadFullyWithOffsetAndLength() throws IOException {
        byte[] buffer = new byte[15];
        int bytesRead = IOUtils.readFully(inputStream, buffer, 3, 9);
        assertEquals("Test data".length(), bytesRead);
        byte[] expected = new byte[15];
        System.arraycopy("   Test data".getBytes(), 0, expected, 0, 15);
        assertArrayEquals(expected, buffer);
    }

    @Test
    public void testToByteArray() throws IOException {
        byte[] byteArray = IOUtils.toByteArray(inputStream);
        assertNotNull(byteArray);
        assertArrayEquals("Test data".getBytes(), byteArray);
    }

    @Test
    public void testCloseQuietly() {
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
        // No exceptions should be thrown
    }
}