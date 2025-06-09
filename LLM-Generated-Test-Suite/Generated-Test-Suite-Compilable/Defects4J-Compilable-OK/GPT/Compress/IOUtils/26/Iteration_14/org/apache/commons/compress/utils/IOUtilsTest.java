package org.apache.commons.compress.utils;

import org.apache.commons.compress.utils.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IOUtilsTest {

    private InputStream inputStream;
    private ByteArrayOutputStream outputStream;

    @Before
    public void setUp() {
        byte[] data = "Test data for IOUtils".getBytes();
        inputStream = new ByteArrayInputStream(data);
        outputStream = new ByteArrayOutputStream();
    }

    @After
    public void tearDown() throws IOException {
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
    }

    @Test
    public void testCopyWithDefaultBufferSize() throws IOException {
        long bytesCopied = IOUtils.copy(inputStream, outputStream);
        assertEquals("Test data for IOUtils".length(), bytesCopied);
        assertArrayEquals("Test data for IOUtils".getBytes(), outputStream.toByteArray());
    }

    @Test
    public void testCopyWithCustomBufferSize() throws IOException {
        long bytesCopied = IOUtils.copy(inputStream, outputStream, 1024);
        assertEquals("Test data for IOUtils".length(), bytesCopied);
        assertArrayEquals("Test data for IOUtils".getBytes(), outputStream.toByteArray());
    }

    @Test
    public void testSkip() throws IOException {
        long bytesSkipped = IOUtils.skip(inputStream, 5);
        assertEquals(5, bytesSkipped);
        byte[] remainingData = new byte[inputStream.available()];
        inputStream.read(remainingData);
        assertArrayEquals("data for IOUtils".getBytes(), remainingData);
    }

    @Test
    public void testReadFully() throws IOException {
        byte[] buffer = new byte[10];
        int bytesRead = IOUtils.readFully(inputStream, buffer);
        assertEquals(10, bytesRead);
        assertArrayEquals("Test data ".getBytes(), buffer);
    }

    @Test
    public void testReadFullyWithOffsetAndLength() throws IOException {
        byte[] buffer = new byte[20];
        int bytesRead = IOUtils.readFully(inputStream, buffer, 5, 10);
        assertEquals(10, bytesRead);
        assertArrayEquals("Test data for IOUtils".getBytes(), buffer);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testReadFullyWithInvalidOffsetAndLength() throws IOException {
        byte[] buffer = new byte[10];
        IOUtils.readFully(inputStream, buffer, 5, 10);
    }

    @Test
    public void testToByteArray() throws IOException {
        byte[] result = IOUtils.toByteArray(inputStream);
        assertArrayEquals("Test data for IOUtils".getBytes(), result);
    }

    @Test
    public void testCloseQuietly() {
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
        // No exception should be thrown
        assertTrue(true);
    }
}