package JSci.maths.symbolic;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.*;

public class SimpleCharStreamTest {

    private SimpleCharStream simpleCharStream;
    private Reader reader;

    @Before
    public void setUp() {
        reader = new StringReader("This is a test string.\nWith multiple lines.\nAnd some more text.");
        simpleCharStream = new SimpleCharStream(reader);
    }

    @Test
    public void testBeginToken() throws IOException {
        char firstChar = SimpleCharStream.BeginToken();
        assertEquals('T', firstChar);
    }

    @Test
    public void testReadChar() throws IOException {
        char firstChar = SimpleCharStream.readChar();
        assertEquals('T', firstChar);
        char secondChar = SimpleCharStream.readChar();
        assertEquals('h', secondChar);
    }

    @Test
    public void testGetColumn() throws IOException {
        SimpleCharStream.readChar(); // 'T'
        SimpleCharStream.readChar(); // 'h'
        assertEquals(3, SimpleCharStream.getColumn());
    }

    @Test
    public void testGetLine() throws IOException {
        SimpleCharStream.readChar(); // 'T'
        SimpleCharStream.readChar(); // 'h'
        assertEquals(1, SimpleCharStream.getLine());
    }

    @Test
    public void testGetEndColumn() throws IOException {
        SimpleCharStream.readChar(); // 'T'
        SimpleCharStream.readChar(); // 'h'
        assertEquals(3, SimpleCharStream.getEndColumn());
    }

    @Test
    public void testGetEndLine() throws IOException {
        SimpleCharStream.readChar(); // 'T'
        SimpleCharStream.readChar(); // 'h'
        assertEquals(1, SimpleCharStream.getEndLine());
    }

    @Test
    public void testGetBeginColumn() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar(); // 'T'
        assertEquals(1, SimpleCharStream.getBeginColumn());
    }

    @Test
    public void testGetBeginLine() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar(); // 'T'
        assertEquals(1, SimpleCharStream.getBeginLine());
    }

    @Test
    public void testBackup() throws IOException {
        SimpleCharStream.readChar(); // 'T'
        SimpleCharStream.readChar(); // 'h'
        SimpleCharStream.backup(1);
        assertEquals('h', SimpleCharStream.readChar());
    }

    @Test
    public void testGetImage() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar(); // 'T'
        SimpleCharStream.readChar(); // 'h'
        assertEquals("Th", SimpleCharStream.GetImage());
    }

    @Test
    public void testGetSuffix() throws IOException {
        SimpleCharStream.readChar(); // 'T'
        SimpleCharStream.readChar(); // 'h'
        char[] suffix = SimpleCharStream.GetSuffix(2);
        assertArrayEquals(new char[]{'T', 'h'}, suffix);
    }

    @Test
    public void testDone() {
        SimpleCharStream.Done();
        assertNull(SimpleCharStream.buffer);
        assertNull(SimpleCharStream.bufline);
        assertNull(SimpleCharStream.bufcolumn);
    }

    @Test
    public void testAdjustBeginLineColumn() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar(); // 'T'
        SimpleCharStream.readChar(); // 'h'
        SimpleCharStream.adjustBeginLineColumn(2, 5);
        assertEquals(2, SimpleCharStream.getBeginLine());
        assertEquals(5, SimpleCharStream.getBeginColumn());
    }

    @Test
    public void testReInitReader() {
        Reader newReader = new StringReader("New content");
        simpleCharStream.ReInit(newReader, 1, 1);
        assertEquals(1, SimpleCharStream.line);
        assertEquals(0, SimpleCharStream.column);
    }

    @Test
    public void testReInitInputStream() {
        InputStream newInputStream = new ByteArrayInputStream("New content".getBytes());
        simpleCharStream.ReInit(newInputStream, 1, 1);
        assertEquals(1, SimpleCharStream.line);
        assertEquals(0, SimpleCharStream.column);
    }
}