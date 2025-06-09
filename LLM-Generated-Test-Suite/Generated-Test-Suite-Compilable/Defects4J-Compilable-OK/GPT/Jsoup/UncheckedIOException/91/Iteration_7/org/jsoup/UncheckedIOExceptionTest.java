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

        assertNotNull("UncheckedIOException should not be null", uncheckedIOException);
        assertEquals("Cause should be the same as the IOException passed", ioException, uncheckedIOException.getCause());
    }

    @Test
    public void testIoExceptionMethod() {
        IOException ioException = new IOException("Test IO Exception");
        UncheckedIOException uncheckedIOException = new UncheckedIOException(ioException);

        assertEquals("ioException method should return the original IOException", ioException, uncheckedIOException.ioException());
    }

    @Test
    public void testConstructorWithNull() {
        UncheckedIOException uncheckedIOException = new UncheckedIOException(null);

        assertNotNull("UncheckedIOException should not be null", uncheckedIOException);
        assertNull("Cause should be null when null is passed to the constructor", uncheckedIOException.getCause());
    }

    @Test
    public void testIoExceptionMethodWithNull() {
        UncheckedIOException uncheckedIOException = new UncheckedIOException(null);

        assertNull("ioException method should return null when constructed with null", uncheckedIOException.ioException());
    }
}