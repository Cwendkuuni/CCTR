package JSci.maths.symbolic;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.*;

public class SimpleCharStreamTest {

    private SimpleCharStream stream;
    private Reader reader;

    @Before
    public void setUp() {
        // Reset the static fields before each test
        SimpleCharStream.Done();
        reader = new StringReader("Hello\nWorld\r\nTest\t123");
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
        SimpleCharStream.readChar(); // l
        SimpleCharStream.readChar(); // l
        SimpleCharStream.readChar(); // o
        SimpleCharStream.readChar(); // \n
        assertEquals(2, SimpleCharStream.getLine());
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
        SimpleCharStream.readChar(); // H
        SimpleCharStream.readChar(); // e
        SimpleCharStream.adjustBeginLineColumn(5, 10);
        assertEquals(5, SimpleCharStream.getBeginLine());
        assertEquals(10, SimpleCharStream.getBeginColumn());
    }

    @Test(expected = IOException.class)
    public void testFillBuffIOException() throws IOException {
        SimpleCharStream.Done();
        SimpleCharStream.FillBuff();
    }

    @Test
    public void testExpandBuff() {
        SimpleCharStream.ExpandBuff(false);
        assertTrue(SimpleCharStream.bufsize > 4096);
    }

    @Test
    public void testReInit() throws IOException {
        SimpleCharStream.readChar(); // H
        SimpleCharStream.ReInit(new StringReader("New Stream"));
        assertEquals('N', SimpleCharStream.readChar());
    }

    @Test
    public void testDone() {
        SimpleCharStream.Done();
        assertNull(SimpleCharStream.buffer);
        assertNull(SimpleCharStream.bufline);
        assertNull(SimpleCharStream.bufcolumn);
    }
}