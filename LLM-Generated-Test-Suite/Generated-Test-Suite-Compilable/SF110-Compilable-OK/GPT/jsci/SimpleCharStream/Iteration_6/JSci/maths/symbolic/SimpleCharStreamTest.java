package JSci.maths.symbolic;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.*;

public class SimpleCharStreamTest {

    private SimpleCharStream stream;
    private Reader reader;

    @Before
    public void setUp() throws Exception {
        String input = "Hello\nWorld\r\nThis is a test.";
        reader = new StringReader(input);
        stream = new SimpleCharStream(reader);
    }

    @Test
    public void testBeginToken() throws IOException {
        char c = SimpleCharStream.BeginToken();
        assertEquals('H', c);
        assertEquals(0, SimpleCharStream.tokenBegin);
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
    public void testDone() {
        SimpleCharStream.Done();
        assertNull(SimpleCharStream.buffer);
        assertNull(SimpleCharStream.bufline);
        assertNull(SimpleCharStream.bufcolumn);
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
    public void testReInit() throws IOException {
        SimpleCharStream.readChar(); // H
        SimpleCharStream.readChar(); // e
        SimpleCharStream.ReInit(reader);
        assertEquals(1, SimpleCharStream.getLine());
        assertEquals(1, SimpleCharStream.getColumn());
    }
}