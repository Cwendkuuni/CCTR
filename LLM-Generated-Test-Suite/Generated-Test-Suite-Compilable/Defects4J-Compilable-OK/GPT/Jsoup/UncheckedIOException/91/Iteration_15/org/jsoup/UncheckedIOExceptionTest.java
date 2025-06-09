package org.jsoup;

import org.jsoup.UncheckedIOException;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

public class UncheckedIOExceptionTest {

    @Test
    public void testConstructorWithIOException() {
        IOException ioException = new IOException("Test IOException");
        UncheckedIOException uncheckedIOException = new UncheckedIOException(ioException);

        assertNotNull("UncheckedIOException should not be null", uncheckedIOException);
        assertEquals("Cause should be the provided IOException", ioException, uncheckedIOException.getCause());
    }

    @Test
    public void testIoExceptionMethod() {
        IOException ioException = new IOException("Test IOException");
        UncheckedIOException uncheckedIOException = new UncheckedIOException(ioException);

        assertEquals("ioException method should return the original IOException", ioException, uncheckedIOException.ioException());
    }

    @Test
    public void testUncheckedIOExceptionIsRuntimeException() {
        IOException ioException = new IOException("Test IOException");
        UncheckedIOException uncheckedIOException = new UncheckedIOException(ioException);

        assertTrue("UncheckedIOException should be an instance of RuntimeException", uncheckedIOException instanceof RuntimeException);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNull() {
        new UncheckedIOException(null);
    }
}