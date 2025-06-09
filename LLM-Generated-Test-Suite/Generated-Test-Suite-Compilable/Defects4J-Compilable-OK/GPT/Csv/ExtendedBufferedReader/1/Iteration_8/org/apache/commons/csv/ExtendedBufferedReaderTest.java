package org.apache.commons.csv;

import org.junit.Before;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

public class ExtendedBufferedReaderTest {

    private ExtendedBufferedReader extendedBufferedReader;

    @Before
    public void setUp() {
        String testString = "Hello\nWorld\r\nThis is a test\rAnother line";
        extendedBufferedReader = new ExtendedBufferedReader(new StringReader(testString));
    }

    @Test
    public void testReadSingleCharacter() throws IOException {
        int charRead = extendedBufferedReader.read();
        assertEquals('H', charRead);
        assertEquals('H', extendedBufferedReader.readAgain());
    }

    @Test
    public void testReadMultipleCharacters() throws IOException {
        char[] buffer = new char[5];
        int len = extendedBufferedReader.read(buffer, 0, 5);
        assertEquals(5, len);
        assertArrayEquals(new char[]{'H', 'e', 'l', 'l', 'o'}, buffer);
        assertEquals('o', extendedBufferedReader.readAgain());
    }

    @Test
    public void testReadLine() throws IOException {
        String line = extendedBufferedReader.readLine();
        assertEquals("Hello", line);
        assertEquals('o', extendedBufferedReader.readAgain());
    }

    @Test
    public void testReadLineWithCRLF() throws IOException {
        extendedBufferedReader.readLine(); // Read first line
        String line = extendedBufferedReader.readLine();
        assertEquals("World", line);
        assertEquals('d', extendedBufferedReader.readAgain());
    }

    @Test
    public void testReadLineWithCR() throws IOException {
        extendedBufferedReader.readLine(); // Read first line
        extendedBufferedReader.readLine(); // Read second line
        String line = extendedBufferedReader.readLine();
        assertEquals("This is a test", line);
        assertEquals('t', extendedBufferedReader.readAgain());
    }

    @Test
    public void testLookAhead() throws IOException {
        int lookAheadChar = extendedBufferedReader.lookAhead();
        assertEquals('H', lookAheadChar);
        int charRead = extendedBufferedReader.read();
        assertEquals('H', charRead);
    }

    @Test
    public void testGetLineNumber() throws IOException {
        assertEquals(0, extendedBufferedReader.getLineNumber());
        extendedBufferedReader.readLine();
        assertEquals(1, extendedBufferedReader.getLineNumber());
        extendedBufferedReader.readLine();
        assertEquals(2, extendedBufferedReader.getLineNumber());
    }

    @Test
    public void testReadAgainBeforeAnyRead() {
        assertEquals(ExtendedBufferedReader.UNDEFINED, extendedBufferedReader.readAgain());
    }

    @Test
    public void testReadEndOfStream() throws IOException {
        while (extendedBufferedReader.read() != ExtendedBufferedReader.END_OF_STREAM) {
            // Read until end of stream
        }
        assertEquals(ExtendedBufferedReader.END_OF_STREAM, extendedBufferedReader.readAgain());
    }
}