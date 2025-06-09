package org.jsoup;

import org.jsoup.UncheckedIOException;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

public class UncheckedIOExceptionTest {

    @Test
    public void testConstructorAndGetCause() {
        IOException ioException = new IOException("Test IO Exception");
        UncheckedIOException uncheckedIOException = new UncheckedIOException(ioException);

        // Verify that the cause is correctly set
        assertEquals(ioException, uncheckedIOException.getCause());
    }

    @Test
    public void testIoExceptionMethod() {
        IOException ioException = new IOException("Test IO Exception");
        UncheckedIOException uncheckedIOException = new UncheckedIOException(ioException);

        // Verify that ioException method returns the correct IOException
        assertEquals(ioException, uncheckedIOException.ioException());
    }

    @Test
    public void testExceptionMessage() {
        IOException ioException = new IOException("Test IO Exception");
        UncheckedIOException uncheckedIOException = new UncheckedIOException(ioException);

        // Verify that the message of the UncheckedIOException is the same as the cause
        assertEquals(ioException.getMessage(), uncheckedIOException.getMessage());
    }

    @Test
    public void testNullCause() {
        UncheckedIOException uncheckedIOException = new UncheckedIOException(null);

        // Verify that the cause is null
        assertNull(uncheckedIOException.getCause());

        // Verify that ioException method returns null
        assertNull(uncheckedIOException.ioException());
    }
}