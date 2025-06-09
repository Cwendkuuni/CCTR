package org.apache.commons.csv;

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
        String data = "line1\nline2\r\nline3\n";
        Reader stringReader = new StringReader(data);
        reader = new ExtendedBufferedReader(stringReader);
    }

    @Test
    public void testRead() throws IOException {
        assertEquals('l', reader.read());
        assertEquals('i', reader.read());
        assertEquals('n', reader.read());
        assertEquals('e', reader.read());
        assertEquals('1', reader.read());
        assertEquals('\n', reader.read());
        assertEquals(1, reader.getLineNumber());
        assertEquals('l', reader.read());
        assertEquals('i', reader.read());
        assertEquals('n', reader.read());
        assertEquals('e', reader.read());
        assertEquals('2', reader.read());
        assertEquals('\r', reader.read());
        assertEquals('\n', reader.read());
        assertEquals(2, reader.getLineNumber());
        assertEquals('l', reader.read());
        assertEquals('i', reader.read());
        assertEquals('n', reader.read());
        assertEquals('e', reader.read());
        assertEquals('3', reader.read());
        assertEquals('\n', reader.read());
        assertEquals(3, reader.getLineNumber());
        assertEquals(ExtendedBufferedReader.END_OF_STREAM, reader.read());
    }

    @Test
    public void testReadAgain() throws IOException {
        assertEquals('l', reader.read());
        assertEquals('l', reader.readAgain());
        assertEquals('i', reader.read());
        assertEquals('i', reader.readAgain());
        assertEquals('\n', reader.read());
        assertEquals('\n', reader.readAgain());
        assertEquals(1, reader.getLineNumber());
    }

    @Test
    public void testReadArray() throws IOException {
        char[] buffer = new char[5];
        assertEquals(5, reader.read(buffer, 0, 5));
        assertEquals("line1", new String(buffer, 0, 5));
        assertEquals(0, reader.getLineNumber());
        assertEquals(1, reader.read(buffer, 0, 1));
        assertEquals('\n', buffer[0]);
        assertEquals(1, reader.getLineNumber());
    }

    @Test
    public void testReadLine() throws IOException {
        assertEquals("line1", reader.readLine());
        assertEquals(1, reader.getLineNumber());
        assertEquals("line2", reader.readLine());
        assertEquals(2, reader.getLineNumber());
        assertEquals("line3", reader.readLine());
        assertEquals(3, reader.getLineNumber());
        assertNull(reader.readLine());
        assertEquals(ExtendedBufferedReader.END_OF_STREAM, reader.readAgain());
    }

    @Test
    public void testLookAhead() throws IOException {
        assertEquals('l', reader.lookAhead());
        assertEquals('l', reader.read());
        assertEquals('i', reader.lookAhead());
        assertEquals('i', reader.read());
        assertEquals('\n', reader.lookAhead());
        assertEquals('\n', reader.read());
        assertEquals(1, reader.getLineNumber());
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
}