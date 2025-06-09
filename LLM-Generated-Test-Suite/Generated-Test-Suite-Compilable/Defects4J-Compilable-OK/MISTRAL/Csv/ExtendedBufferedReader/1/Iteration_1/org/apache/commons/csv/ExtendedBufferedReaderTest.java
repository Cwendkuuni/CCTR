package org.apache.commons.csv;

import org.apache.commons.csv.ExtendedBufferedReader;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class ExtendedBufferedReaderTest {

    private ExtendedBufferedReader reader;

    @Before
    public void setUp() {
        String data = "Hello\nWorld\n";
        Reader stringReader = new StringReader(data);
        reader = new ExtendedBufferedReader(stringReader);
    }

    @Test
    public void testRead() throws IOException {
        assertEquals('H', reader.read());
        assertEquals('e', reader.read());
        assertEquals('l', reader.read());
        assertEquals('l', reader.read());
        assertEquals('o', reader.read());
        assertEquals('\n', reader.read());
        assertEquals('W', reader.read());
        assertEquals('o', reader.read());
        assertEquals('r', reader.read());
        assertEquals('l', reader.read());
        assertEquals('d', reader.read());
        assertEquals('\n', reader.read());
        assertEquals(ExtendedBufferedReader.END_OF_STREAM, reader.read());
    }

    @Test
    public void testReadAgain() throws IOException {
        reader.read(); // 'H'
        assertEquals('H', reader.readAgain());
        reader.read(); // 'e'
        assertEquals('e', reader.readAgain());
        reader.read(); // 'l'
        assertEquals('l', reader.readAgain());
        reader.read(); // 'l'
        assertEquals('l', reader.readAgain());
        reader.read(); // 'o'
        assertEquals('o', reader.readAgain());
        reader.read(); // '\n'
        assertEquals('\n', reader.readAgain());
        reader.read(); // 'W'
        assertEquals('W', reader.readAgain());
        reader.read(); // 'o'
        assertEquals('o', reader.readAgain());
        reader.read(); // 'r'
        assertEquals('r', reader.readAgain());
        reader.read(); // 'l'
        assertEquals('l', reader.readAgain());
        reader.read(); // 'd'
        assertEquals('d', reader.readAgain());
        reader.read(); // '\n'
        assertEquals('\n', reader.readAgain());
        reader.read(); // END_OF_STREAM
        assertEquals(ExtendedBufferedReader.END_OF_STREAM, reader.readAgain());
    }

    @Test
    public void testReadArray() throws IOException {
        char[] buffer = new char[5];
        assertEquals(5, reader.read(buffer, 0, 5));
        assertArrayEquals(new char[]{'H', 'e', 'l', 'l', 'o'}, buffer);
        assertEquals(5, reader.read(buffer, 0, 5));
        assertArrayEquals(new char[]{'W', 'o', 'r', 'l', 'd'}, buffer);
        assertEquals(1, reader.read(buffer, 0, 5));
        assertArrayEquals(new char[]{'W', 'o', 'r', 'l', '\n'}, buffer);
        assertEquals(ExtendedBufferedReader.END_OF_STREAM, reader.read(buffer, 0, 5));
    }

    @Test
    public void testReadLine() throws IOException {
        assertEquals("Hello", reader.readLine());
        assertEquals("World", reader.readLine());
        assertNull(reader.readLine());
    }

    @Test
    public void testLookAhead() throws IOException {
        assertEquals('H', reader.lookAhead());
        assertEquals('H', reader.read());
        assertEquals('e', reader.lookAhead());
        assertEquals('e', reader.read());
        assertEquals('l', reader.lookAhead());
        assertEquals('l', reader.read());
        assertEquals('l', reader.lookAhead());
        assertEquals('l', reader.read());
        assertEquals('o', reader.lookAhead());
        assertEquals('o', reader.read());
        assertEquals('\n', reader.lookAhead());
        assertEquals('\n', reader.read());
        assertEquals('W', reader.lookAhead());
        assertEquals('W', reader.read());
        assertEquals('o', reader.lookAhead());
        assertEquals('o', reader.read());
        assertEquals('r', reader.lookAhead());
        assertEquals('r', reader.read());
        assertEquals('l', reader.lookAhead());
        assertEquals('l', reader.read());
        assertEquals('d', reader.lookAhead());
        assertEquals('d', reader.read());
        assertEquals('\n', reader.lookAhead());
        assertEquals('\n', reader.read());
        assertEquals(ExtendedBufferedReader.END_OF_STREAM, reader.lookAhead());
    }

    @Test
    public void testGetLineNumber() throws IOException {
        assertEquals(0, reader.getLineNumber());
        reader.readLine(); // "Hello"
        assertEquals(1, reader.getLineNumber());
        reader.readLine(); // "World"
        assertEquals(2, reader.getLineNumber());
        reader.readLine(); // null
        assertEquals(2, reader.getLineNumber());
    }
}