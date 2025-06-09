package org.apache.commons.csv;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.StringReader;
import static org.junit.Assert.*;

public class ExtendedBufferedReaderTest {

    private ExtendedBufferedReader reader;

    @Before
    public void setUp() {
        // Initialize with a simple string for testing
        reader = new ExtendedBufferedReader(new StringReader("Hello\nWorld\r\nTest"));
    }

    @Test
    public void testReadSingleCharacter() throws IOException {
        assertEquals('H', reader.read());
        assertEquals('e', reader.read());
        assertEquals('l', reader.read());
    }

    @Test
    public void testReadAgain() throws IOException {
        assertEquals(ExtendedBufferedReader.UNDEFINED, reader.readAgain());
        reader.read();
        assertEquals('H', reader.readAgain());
        reader.read();
        assertEquals('e', reader.readAgain());
    }

    @Test
    public void testReadCharArray() throws IOException {
        char[] buffer = new char[5];
        int len = reader.read(buffer, 0, buffer.length);
        assertEquals(5, len);
        assertArrayEquals(new char[]{'H', 'e', 'l', 'l', 'o'}, buffer);
        assertEquals('o', reader.readAgain());
    }

    @Test
    public void testReadLine() throws IOException {
        assertEquals("Hello", reader.readLine());
        assertEquals("World", reader.readLine());
        assertEquals("Test", reader.readLine());
        assertNull(reader.readLine());
    }

    @Test
    public void testLookAhead() throws IOException {
        assertEquals('H', reader.lookAhead());
        assertEquals('H', reader.read());
        assertEquals('e', reader.lookAhead());
    }

    @Test
    public void testGetLineNumber() throws IOException {
        assertEquals(0, reader.getLineNumber());
        reader.readLine();
        assertEquals(1, reader.getLineNumber());
        reader.readLine();
        assertEquals(2, reader.getLineNumber());
        reader.readLine();
        assertEquals(3, reader.getLineNumber());
    }

    @Test
    public void testEndOfStream() throws IOException {
        reader.readLine();
        reader.readLine();
        reader.readLine();
        assertEquals(ExtendedBufferedReader.END_OF_STREAM, reader.read());
        assertEquals(ExtendedBufferedReader.END_OF_STREAM, reader.readAgain());
    }
}