package org.apache.commons.csv;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExtendedBufferedReaderTest {

    private ExtendedBufferedReader reader;

    @Before
    public void setUp() {
        String testString = "Hello\nWorld\r\nThis is a test\rAnother line";
        reader = new ExtendedBufferedReader(new StringReader(testString));
    }

    @Test
    public void testReadSingleCharacter() throws IOException {
        assertEquals('H', reader.read());
        assertEquals('e', reader.read());
        assertEquals('l', reader.read());
        assertEquals('l', reader.read());
        assertEquals('o', reader.read());
        assertEquals('\n', reader.read());
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
        assertEquals('H', buffer[0]);
        assertEquals('e', buffer[1]);
        assertEquals('l', buffer[2]);
        assertEquals('l', buffer[3]);
        assertEquals('o', buffer[4]);
    }

    @Test
    public void testReadLine() throws IOException {
        assertEquals("Hello", reader.readLine());
        assertEquals("World", reader.readLine());
        assertEquals("This is a test", reader.readLine());
        assertEquals("Another line", reader.readLine());
        assertEquals(null, reader.readLine());
    }

    @Test
    public void testLookAhead() throws IOException {
        assertEquals('H', reader.lookAhead());
        assertEquals('H', reader.read());
        assertEquals('e', reader.lookAhead());
        assertEquals('e', reader.read());
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
        reader.readLine();
        assertEquals(4, reader.getLineNumber());
    }

    @Test
    public void testEndOfStream() throws IOException {
        while (reader.read() != ExtendedBufferedReader.END_OF_STREAM) {
            // Read until the end of stream
        }
        assertEquals(ExtendedBufferedReader.END_OF_STREAM, reader.readAgain());
    }
}