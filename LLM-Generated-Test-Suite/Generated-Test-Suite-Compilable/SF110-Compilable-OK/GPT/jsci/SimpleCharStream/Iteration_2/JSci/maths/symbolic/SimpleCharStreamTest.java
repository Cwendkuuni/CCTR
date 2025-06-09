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
        reader = new StringReader("This is a test string.\nWith multiple lines.\nAnd some more text.");
        simpleCharStream = new SimpleCharStream(reader);
    }

    @After
    public void tearDown() {
        SimpleCharStream.Done();
    }

    @Test
    public void testBeginToken() throws IOException {
        char c = SimpleCharStream.BeginToken();
        assertEquals('T', c);
    }

    @Test
    public void testReadChar() throws IOException {
        char c = SimpleCharStream.readChar();
        assertEquals('T', c);
        c = SimpleCharStream.readChar();
        assertEquals('h', c);
    }

    @Test
    public void testGetColumn() throws IOException {
        SimpleCharStream.readChar(); // T
        SimpleCharStream.readChar(); // h
        assertEquals(3, SimpleCharStream.getColumn());
    }

    @Test
    public void testGetLine() throws IOException {
        SimpleCharStream.readChar(); // T
        SimpleCharStream.readChar(); // h
        assertEquals(1, SimpleCharStream.getLine());
    }

    @Test
    public void testGetEndColumn() throws IOException {
        SimpleCharStream.readChar(); // T
        SimpleCharStream.readChar(); // h
        assertEquals(3, SimpleCharStream.getEndColumn());
    }

    @Test
    public void testGetEndLine() throws IOException {
        SimpleCharStream.readChar(); // T
        SimpleCharStream.readChar(); // h
        assertEquals(1, SimpleCharStream.getEndLine());
    }

    @Test
    public void testGetBeginColumn() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar(); // T
        SimpleCharStream.readChar(); // h
        assertEquals(1, SimpleCharStream.getBeginColumn());
    }

    @Test
    public void testGetBeginLine() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar(); // T
        SimpleCharStream.readChar(); // h
        assertEquals(1, SimpleCharStream.getBeginLine());
    }

    @Test
    public void testBackup() throws IOException {
        SimpleCharStream.readChar(); // T
        SimpleCharStream.readChar(); // h
        SimpleCharStream.backup(1);
        assertEquals('h', SimpleCharStream.readChar());
    }

    @Test
    public void testGetImage() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar(); // T
        SimpleCharStream.readChar(); // h
        assertEquals("Th", SimpleCharStream.GetImage());
    }

    @Test
    public void testGetSuffix() throws IOException {
        SimpleCharStream.readChar(); // T
        SimpleCharStream.readChar(); // h
        char[] suffix = SimpleCharStream.GetSuffix(2);
        assertArrayEquals(new char[]{'T', 'h'}, suffix);
    }

    @Test
    public void testAdjustBeginLineColumn() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar(); // T
        SimpleCharStream.readChar(); // h
        SimpleCharStream.adjustBeginLineColumn(2, 5);
        assertEquals(2, SimpleCharStream.getBeginLine());
        assertEquals(5, SimpleCharStream.getBeginColumn());
    }

    @Test
    public void testExpandBuff() {
        // This method is protected and static, so we need to test it indirectly
        // through methods that use it, such as FillBuff.
        // We will simulate a condition that requires buffer expansion.
        SimpleCharStream.bufsize = 10;
        SimpleCharStream.available = 10;
        SimpleCharStream.maxNextCharInd = 10;
        SimpleCharStream.tokenBegin = 0;
        SimpleCharStream.bufpos = 9;
        SimpleCharStream.buffer = new char[10];
        SimpleCharStream.bufline = new int[10];
        SimpleCharStream.bufcolumn = new int[10];

        try {
            SimpleCharStream.FillBuff();
        } catch (IOException e) {
            // Expected as the input stream will be exhausted
        }

        assertTrue(SimpleCharStream.bufsize > 10);
    }
}