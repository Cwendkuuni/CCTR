package JSci.maths.symbolic;

import JSci.maths.symbolic.SimpleCharStream;
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
        reader = new StringReader("Hello\nWorld\r\nTest\tString");
        simpleCharStream = new SimpleCharStream(reader, 1, 1, 10);
    }

    @Test
    public void testConstructorWithReader() {
        SimpleCharStream stream = new SimpleCharStream(new StringReader("Test"));
        assertNotNull(stream);
    }

    @Test
    public void testConstructorWithReaderAndLineColumn() {
        SimpleCharStream stream = new SimpleCharStream(new StringReader("Test"), 1, 1);
        assertNotNull(stream);
    }

    @Test
    public void testConstructorWithReaderLineColumnAndBufferSize() {
        SimpleCharStream stream = new SimpleCharStream(new StringReader("Test"), 1, 1, 10);
        assertNotNull(stream);
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
        SimpleCharStream.readChar();
        assertEquals(1, SimpleCharStream.getColumn());
    }

    @Test
    public void testGetLine() throws IOException {
        SimpleCharStream.readChar();
        assertEquals(1, SimpleCharStream.getLine());
    }

    @Test
    public void testGetEndColumn() throws IOException {
        SimpleCharStream.readChar();
        assertEquals(1, SimpleCharStream.getEndColumn());
    }

    @Test
    public void testGetEndLine() throws IOException {
        SimpleCharStream.readChar();
        assertEquals(1, SimpleCharStream.getEndLine());
    }

    @Test
    public void testGetBeginColumn() throws IOException {
        SimpleCharStream.BeginToken();
        assertEquals(1, SimpleCharStream.getBeginColumn());
    }

    @Test
    public void testGetBeginLine() throws IOException {
        SimpleCharStream.BeginToken();
        assertEquals(1, SimpleCharStream.getBeginLine());
    }

    @Test
    public void testBackup() throws IOException {
        SimpleCharStream.readChar();
        SimpleCharStream.backup(1);
        char c = SimpleCharStream.readChar();
        assertEquals('H', c);
    }

    @Test
    public void testGetImage() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar();
        SimpleCharStream.readChar();
        String image = SimpleCharStream.GetImage();
        assertEquals("He", image);
    }

    @Test
    public void testGetSuffix() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar();
        SimpleCharStream.readChar();
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
        SimpleCharStream.readChar();
        SimpleCharStream.adjustBeginLineColumn(2, 2);
        assertEquals(2, SimpleCharStream.getBeginLine());
        assertEquals(2, SimpleCharStream.getBeginColumn());
    }

    @Test(expected = IOException.class)
    public void testFillBuffIOException() throws IOException {
        SimpleCharStream.Done();
        SimpleCharStream.FillBuff();
    }

    @Test
    public void testExpandBuff() {
        SimpleCharStream.ExpandBuff(false);
        assertTrue(SimpleCharStream.bufsize > 10);
    }

    @Test
    public void testReInitWithReader() {
        simpleCharStream.ReInit(new StringReader("New String"));
        assertNotNull(SimpleCharStream.inputStream);
    }

    @Test
    public void testReInitWithReaderAndLineColumn() {
        simpleCharStream.ReInit(new StringReader("New String"), 1, 1);
        assertNotNull(SimpleCharStream.inputStream);
    }

    @Test
    public void testReInitWithReaderLineColumnAndBufferSize() {
        simpleCharStream.ReInit(new StringReader("New String"), 1, 1, 10);
        assertNotNull(SimpleCharStream.inputStream);
    }
}