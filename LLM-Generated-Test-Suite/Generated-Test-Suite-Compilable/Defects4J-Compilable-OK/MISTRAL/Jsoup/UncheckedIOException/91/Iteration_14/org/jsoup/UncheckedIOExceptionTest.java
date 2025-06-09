package org.jsoup;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

public class UncheckedIOExceptionTest {

    @Test
    public void testConstructor() {
        IOException cause = new IOException("Test IOException");
        UncheckedIOException exception = new UncheckedIOException(cause);

        assertNotNull(exception);
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testIoException() {
        IOException cause = new IOException("Test IOException");
        UncheckedIOException exception = new UncheckedIOException(cause);

        IOException result = exception.ioException();

        assertNotNull(result);
        assertEquals(cause, result);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullCause() {
        new UncheckedIOException(null);
    }

    @Test
    public void testIoExceptionWithNullCause() {
        UncheckedIOException exception = new UncheckedIOException(null) {
            @Override
            public IOException ioException() {
                return super.ioException();
            }
        };

        IOException result = exception.ioException();

        assertNull(result);
    }
}