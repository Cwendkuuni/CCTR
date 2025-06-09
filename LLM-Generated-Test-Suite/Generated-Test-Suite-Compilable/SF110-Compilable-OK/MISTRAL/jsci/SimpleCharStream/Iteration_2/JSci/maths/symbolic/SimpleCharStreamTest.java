package JSci.maths.symbolic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;

public class SimpleCharStreamTest {

    private SimpleCharStream simpleCharStream;
    private StringReader stringReader;

    @Before
    public void setUp() {
        stringReader = new StringReader("Test string for SimpleCharStream");
        simpleCharStream = new SimpleCharStream(stringReader);
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
        assertEquals('e', c);
    }

    @Test
    public void testGetColumn() throws IOException {
        SimpleCharStream.readChar();
        int column = SimpleCharStream.getColumn();
        assertEquals(1, column);
    }

    @Test
    public void testGetLine() throws IOException {
        SimpleCharStream.readChar();
        int line = SimpleCharStream.getLine();
        assertEquals(1, line);
    }

    @Test
    public void testGetEndColumn() throws IOException {
        SimpleCharStream.readChar();
        int endColumn = SimpleCharStream.getEndColumn();
        assertEquals(1, endColumn);
    }

    @Test
    public void testGetEndLine() throws IOException {
        SimpleCharStream.readChar();
        int endLine = SimpleCharStream.getEndLine();
        assertEquals(1, endLine);
    }

    @Test
    public void testGetBeginColumn() throws IOException {
        SimpleCharStream.BeginToken();
        int beginColumn = SimpleCharStream.getBeginColumn();
        assertEquals(1, beginColumn);
    }

    @Test
    public void testGetBeginLine() throws IOException {
        SimpleCharStream.BeginToken();
        int beginLine = SimpleCharStream.getBeginLine();
        assertEquals(1, beginLine);
    }

    @Test
    public void testBackup() throws IOException {
        SimpleCharStream.readChar();
        SimpleCharStream.backup(1);
        char c = SimpleCharStream.readChar();
        assertEquals('T', c);
    }

    @Test
    public void testGetImage() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar();
        SimpleCharStream.readChar();
        String image = SimpleCharStream.GetImage();
        assertEquals("Te", image);
    }

    @Test
    public void testGetSuffix() throws IOException {
        SimpleCharStream.readChar();
        SimpleCharStream.readChar();
        char[] suffix = SimpleCharStream.GetSuffix(2);
        assertEquals('T', suffix[0]);
        assertEquals('e', suffix[1]);
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
        SimpleCharStream.adjustBeginLineColumn(2, 5);
        assertEquals(2, SimpleCharStream.getLine());
        assertEquals(5, SimpleCharStream.getColumn());
    }

    @Test
    public void testReInit() throws IOException {
        SimpleCharStream.ReInit(new StringReader("New test string"), 1, 1);
        char c = SimpleCharStream.readChar();
        assertEquals('N', c);
    }

    @Test
    public void testExpandBuff() {
        SimpleCharStream.bufsize = 10;
        SimpleCharStream.available = 10;
        SimpleCharStream.tokenBegin = 5;
        SimpleCharStream.bufpos = 5;
        SimpleCharStream.buffer = new char[10];
        SimpleCharStream.bufline = new int[10];
        SimpleCharStream.bufcolumn = new int[10];

        SimpleCharStream.ExpandBuff(false);

        assertEquals(2058, SimpleCharStream.bufsize);
        assertEquals(2058, SimpleCharStream.available);
        assertEquals(0, SimpleCharStream.tokenBegin);
    }

    @Test
    public void testFillBuff() throws IOException {
        SimpleCharStream.maxNextCharInd = SimpleCharStream.available;
        SimpleCharStream.available = SimpleCharStream.bufsize;
        SimpleCharStream.tokenBegin = 2049;

        SimpleCharStream.FillBuff();

        assertTrue(SimpleCharStream.maxNextCharInd > 0);
    }

    @Test
    public void testUpdateLineColumn() {
        SimpleCharStream.UpdateLineColumn('\n');
        assertEquals(2, SimpleCharStream.line);
        assertEquals(1, SimpleCharStream.column);

        SimpleCharStream.UpdateLineColumn('\r');
        assertEquals(2, SimpleCharStream.line);
        assertEquals(1, SimpleCharStream.column);

        SimpleCharStream.UpdateLineColumn('\t');
        assertEquals(2, SimpleCharStream.line);
        assertEquals(9, SimpleCharStream.column);
    }
}