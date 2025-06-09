package JSci.maths.symbolic;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Assert;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class SimpleCharStreamTest {

    private SimpleCharStream simpleCharStream;
    private Reader reader;

    @Before
    public void setUp() {
        reader = new StringReader("Test data for SimpleCharStream");
        simpleCharStream = new SimpleCharStream(reader);
    }

    @After
    public void tearDown() {
        simpleCharStream.Done();
    }

    @Test
    public void testBeginToken() throws IOException {
        char c = simpleCharStream.BeginToken();
        Assert.assertEquals('T', c);
    }

    @Test
    public void testReadChar() throws IOException {
        char c = simpleCharStream.readChar();
        Assert.assertEquals('T', c);
        c = simpleCharStream.readChar();
        Assert.assertEquals('e', c);
    }

    @Test
    public void testGetColumn() throws IOException {
        simpleCharStream.readChar();
        int column = simpleCharStream.getColumn();
        Assert.assertEquals(1, column);
    }

    @Test
    public void testGetLine() throws IOException {
        simpleCharStream.readChar();
        int line = simpleCharStream.getLine();
        Assert.assertEquals(1, line);
    }

    @Test
    public void testGetEndColumn() throws IOException {
        simpleCharStream.readChar();
        int endColumn = simpleCharStream.getEndColumn();
        Assert.assertEquals(1, endColumn);
    }

    @Test
    public void testGetEndLine() throws IOException {
        simpleCharStream.readChar();
        int endLine = simpleCharStream.getEndLine();
        Assert.assertEquals(1, endLine);
    }

    @Test
    public void testGetBeginColumn() throws IOException {
        simpleCharStream.BeginToken();
        int beginColumn = simpleCharStream.getBeginColumn();
        Assert.assertEquals(1, beginColumn);
    }

    @Test
    public void testGetBeginLine() throws IOException {
        simpleCharStream.BeginToken();
        int beginLine = simpleCharStream.getBeginLine();
        Assert.assertEquals(1, beginLine);
    }

    @Test
    public void testBackup() throws IOException {
        simpleCharStream.readChar();
        simpleCharStream.backup(1);
        char c = simpleCharStream.readChar();
        Assert.assertEquals('T', c);
    }

    @Test
    public void testGetImage() throws IOException {
        simpleCharStream.BeginToken();
        simpleCharStream.readChar();
        simpleCharStream.readChar();
        String image = simpleCharStream.GetImage();
        Assert.assertEquals("Te", image);
    }

    @Test
    public void testGetSuffix() throws IOException {
        simpleCharStream.readChar();
        simpleCharStream.readChar();
        char[] suffix = simpleCharStream.GetSuffix(2);
        Assert.assertArrayEquals(new char[]{'e', 's'}, suffix);
    }

    @Test
    public void testAdjustBeginLineColumn() throws IOException {
        simpleCharStream.BeginToken();
        simpleCharStream.readChar();
        simpleCharStream.adjustBeginLineColumn(2, 5);
        int line = simpleCharStream.getLine();
        int column = simpleCharStream.getColumn();
        Assert.assertEquals(2, line);
        Assert.assertEquals(5, column);
    }

    @Test
    public void testReInit() throws IOException {
        simpleCharStream.ReInit(new StringReader("New data"), 1, 1);
        char c = simpleCharStream.readChar();
        Assert.assertEquals('N', c);
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

        Assert.assertEquals(2058, SimpleCharStream.bufsize);
        Assert.assertEquals(2058, SimpleCharStream.available);
        Assert.assertEquals(0, SimpleCharStream.tokenBegin);
        Assert.assertEquals(5, SimpleCharStream.bufpos);
    }

    @Test
    public void testFillBuff() throws IOException {
        SimpleCharStream.maxNextCharInd = SimpleCharStream.available;
        SimpleCharStream.available = SimpleCharStream.bufsize;
        SimpleCharStream.tokenBegin = 0;

        SimpleCharStream.FillBuff();

        Assert.assertEquals(0, SimpleCharStream.bufpos);
        Assert.assertEquals(0, SimpleCharStream.maxNextCharInd);
    }

    @Test
    public void testUpdateLineColumn() {
        SimpleCharStream.column = 0;
        SimpleCharStream.line = 1;
        SimpleCharStream.prevCharIsCR = false;
        SimpleCharStream.prevCharIsLF = false;

        SimpleCharStream.UpdateLineColumn('a');

        Assert.assertEquals(1, SimpleCharStream.column);
        Assert.assertEquals(1, SimpleCharStream.line);
    }
}