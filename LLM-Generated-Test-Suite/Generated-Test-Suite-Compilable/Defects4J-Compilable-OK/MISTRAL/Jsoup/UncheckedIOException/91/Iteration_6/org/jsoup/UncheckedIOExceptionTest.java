package org.jsoup;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

public class UncheckedIOExceptionTest {

    @Test
    public void testConstructorWithIOException() {
        IOException ioException = new IOException("Test IOException");
        UncheckedIOException uncheckedIOException = new UncheckedIOException(ioException);

        assertNotNull(uncheckedIOException);
        assertEquals(ioException, uncheckedIOException.getCause());
    }

    @Test
    public void testIoExceptionMethod() {
        IOException ioException = new IOException("Test IOException");
        UncheckedIOException uncheckedIOException = new UncheckedIOException(ioException);

        assertEquals(ioException, uncheckedIOException.ioException());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullIOException() {
        new UncheckedIOException(null);
    }

    @Test
    public void testIoExceptionMethodWithNullCause() {
        UncheckedIOException uncheckedIOException = new UncheckedIOException(null) {
            @Override
            public Throwable getCause() {
                return null;
            }
        };

        assertNull(uncheckedIOException.ioException());
    }
}