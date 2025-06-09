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
import static org.junit.Assert.assertTrue;

public class IOUtilsTest {

    private InputStream inputStream;
    private OutputStream outputStream;

    @Before
    public void setUp() {
        // Initialize streams before each test
        inputStream = new ByteArrayInputStream("Test data for IOUtils".getBytes());
        outputStream = new ByteArrayOutputStream();
    }

    @After
    public void tearDown() throws IOException {
        // Close streams after each test
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
    }

    @Test
    public void testCopyWithDefaultBufferSize() throws IOException {
        long bytesCopied = IOUtils.copy(inputStream, outputStream);
        assertEquals("Test data for IOUtils".length(), bytesCopied);
        assertEquals("Test data for IOUtils", outputStream.toString());
    }

    @Test
    public void testCopyWithCustomBufferSize() throws IOException {
        long bytesCopied = IOUtils.copy(inputStream, outputStream, 1024);
        assertEquals("Test data for IOUtils".length(), bytesCopied);
        assertEquals("Test data for IOUtils", outputStream.toString());
    }

    @Test
    public void testSkip() throws IOException {
        long bytesSkipped = IOUtils.skip(inputStream, 5);
        assertEquals(5, bytesSkipped);
        byte[] remainingData = new byte[inputStream.available()];
        inputStream.read(remainingData);
        assertEquals("data for IOUtils", new String(remainingData));
    }

    @Test
    public void testReadFully() throws IOException {
        byte[] buffer = new byte[10];
        int bytesRead = IOUtils.readFully(inputStream, buffer);
        assertEquals(10, bytesRead);
        assertEquals("Test data ", new String(buffer));
    }

    @Test
    public void testReadFullyWithOffsetAndLength() throws IOException {
        byte[] buffer = new byte[20];
        int bytesRead = IOUtils.readFully(inputStream, buffer, 5, 10);
        assertEquals(10, bytesRead);
        assertEquals("Test data f", new String(buffer, 5, 10));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testReadFullyWithInvalidOffsetAndLength() throws IOException {
        byte[] buffer = new byte[10];
        IOUtils.readFully(inputStream, buffer, 5, 10);
    }

    @Test
    public void testToByteArray() throws IOException {
        byte[] byteArray = IOUtils.toByteArray(inputStream);
        assertArrayEquals("Test data for IOUtils".getBytes(), byteArray);
    }

    @Test
    public void testCloseQuietly() {
        // Test with a non-null closeable
        IOUtils.closeQuietly(inputStream);
        // Test with a null closeable
        IOUtils.closeQuietly(null);
        // No exception should be thrown
        assertTrue(true);
    }
}