package org.jsoup;

import org.jsoup.UncheckedIOException;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

public class UncheckedIOExceptionTest {

    @Test
    public void testConstructorWithIOException() {
        IOException ioException = new IOException("Test IO Exception");
        UncheckedIOException uncheckedIOException = new UncheckedIOException(ioException);

        // Test that the cause is correctly set
        assertEquals(ioException, uncheckedIOException.getCause());
    }

    @Test
    public void testIoExceptionMethod() {
        IOException ioException = new IOException("Test IO Exception");
        UncheckedIOException uncheckedIOException = new UncheckedIOException(ioException);

        // Test that ioException() returns the correct IOException
        assertEquals(ioException, uncheckedIOException.ioException());
    }

    @Test
    public void testConstructorWithNull() {
        UncheckedIOException uncheckedIOException = new UncheckedIOException(null);

        // Test that the cause is null
        assertNull(uncheckedIOException.getCause());
    }

    @Test
    public void testIoExceptionMethodWithNull() {
        UncheckedIOException uncheckedIOException = new UncheckedIOException(null);

        // Test that ioException() returns null when the cause is null
        assertNull(uncheckedIOException.ioException());
    }
}