package org.jsoup;

import org.junit.Test;
import org.junit.Before;
import org.jsoup.UncheckedIOException;

import java.io.IOException;

import static org.junit.Assert.*;

public class UncheckedIOExceptionTest {

    private IOException ioException;
    private UncheckedIOException uncheckedIOException;

    @Before
    public void setUp() {
        ioException = new IOException("Test IOException");
        uncheckedIOException = new UncheckedIOException(ioException);
    }

    @Test
    public void testConstructor() {
        assertNotNull("UncheckedIOException should not be null", uncheckedIOException);
        assertEquals("Cause should be the same as the IOException passed to the constructor",
                ioException, uncheckedIOException.getCause());
    }

    @Test
    public void testIoExceptionMethod() {
        assertEquals("ioException method should return the same IOException passed to the constructor",
                ioException, uncheckedIOException.ioException());
    }

    @Test
    public void testInheritance() {
        assertTrue("UncheckedIOException should be an instance of RuntimeException",
                uncheckedIOException instanceof RuntimeException);
    }
}