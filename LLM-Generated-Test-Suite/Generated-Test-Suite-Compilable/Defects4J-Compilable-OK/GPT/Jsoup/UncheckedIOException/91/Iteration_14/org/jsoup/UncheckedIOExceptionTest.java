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
        assertEquals("Cause should be the same as the IOException passed to the constructor", ioException, uncheckedIOException.getCause());
    }

    @Test
    public void testIoExceptionMethod() {
        IOException ioException = new IOException("Test IO Exception");
        UncheckedIOException uncheckedIOException = new UncheckedIOException(ioException);

        assertEquals("ioException method should return the same IOException passed to the constructor", ioException, uncheckedIOException.ioException());
    }

    @Test
    public void testUncheckedIOExceptionIsRuntimeException() {
        IOException ioException = new IOException("Test IO Exception");
        UncheckedIOException uncheckedIOException = new UncheckedIOException(ioException);

        assertTrue("UncheckedIOException should be an instance of RuntimeException", uncheckedIOException instanceof RuntimeException);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullIOException() {
        new UncheckedIOException(null);
    }
}