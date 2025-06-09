package JSci.maths.symbolic;

import JSci.maths.symbolic.SimpleCharStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.*;

public class SimpleCharStreamTest {

    private SimpleCharStream simpleCharStream;
    private Reader reader;

    @Before
    public void setUp() {
        reader = new StringReader("Hello\nWorld\r\nTest");
        simpleCharStream = new SimpleCharStream(reader);
    }

    @After
    public void tearDown() {
        SimpleCharStream.Done();
    }

    @Test
    public void testBeginToken() throws IOException {
        char c = SimpleCharStream.BeginToken();
        assertEquals('H', c);
    }

    @Test
    public void testReadChar() throws IOException {
        char c = SimpleCharStream.readChar();
        assertEquals('H', c);
        c = SimpleCharStream.readChar();
        assertEquals('e', c);
    }

    @Test
    public void testGetColumn() throws IOException {
        SimpleCharStream.readChar(); // H
        SimpleCharStream.readChar(); // e
        assertEquals(3, SimpleCharStream.getColumn());
    }

    @Test
    public void testGetLine() throws IOException {
        SimpleCharStream.readChar(); // H
        SimpleCharStream.readChar(); // e
        assertEquals(1, SimpleCharStream.getLine());
    }

    @Test
    public void testGetEndColumn() throws IOException {
        SimpleCharStream.readChar(); // H
        SimpleCharStream.readChar(); // e
        assertEquals(3, SimpleCharStream.getEndColumn());
    }

    @Test
    public void testGetEndLine() throws IOException {
        SimpleCharStream.readChar(); // H
        SimpleCharStream.readChar(); // e
        assertEquals(1, SimpleCharStream.getEndLine());
    }

    @Test
    public void testGetBeginColumn() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar(); // H
        assertEquals(1, SimpleCharStream.getBeginColumn());
    }

    @Test
    public void testGetBeginLine() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar(); // H
        assertEquals(1, SimpleCharStream.getBeginLine());
    }

    @Test
    public void testBackup() throws IOException {
        SimpleCharStream.readChar(); // H
        SimpleCharStream.readChar(); // e
        SimpleCharStream.backup(1);
        assertEquals('e', SimpleCharStream.readChar());
    }

    @Test
    public void testGetImage() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar(); // H
        SimpleCharStream.readChar(); // e
        assertEquals("He", SimpleCharStream.GetImage());
    }

    @Test
    public void testGetSuffix() throws IOException {
        SimpleCharStream.readChar(); // H
        SimpleCharStream.readChar(); // e
        char[] suffix = SimpleCharStream.GetSuffix(2);
        assertArrayEquals(new char[]{'H', 'e'}, suffix);
    }

    @Test
    public void testAdjustBeginLineColumn() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar(); // H
        SimpleCharStream.readChar(); // e
        SimpleCharStream.adjustBeginLineColumn(2, 5);
        assertEquals(2, SimpleCharStream.getBeginLine());
        assertEquals(5, SimpleCharStream.getBeginColumn());
    }

    @Test
    public void testReInitReader() throws IOException {
        Reader newReader = new StringReader("New Content");
        simpleCharStream.ReInit(newReader);
        assertEquals('N', SimpleCharStream.readChar());
    }

    @Test
    public void testReInitInputStream() throws IOException {
        Reader newReader = new StringReader("New Content");
        simpleCharStream.ReInit(newReader);
        assertEquals('N', SimpleCharStream.readChar());
    }
}