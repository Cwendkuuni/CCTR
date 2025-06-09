package JSci.maths.symbolic;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class SimpleCharStreamTest {

    private SimpleCharStream simpleCharStream;
    private Reader reader;

    @Before
    public void setUp() {
        reader = new StringReader("Test string for SimpleCharStream");
        simpleCharStream = new SimpleCharStream(reader);
    }

    @Test
    public void testStaticFlag() {
        Assert.assertTrue(SimpleCharStream.staticFlag);
    }

    @Test
    public void testExpandBuff() {
        SimpleCharStream.bufsize = 10;
        SimpleCharStream.bufpos = 5;
        SimpleCharStream.tokenBegin = 3;
        SimpleCharStream.buffer = new char[10];
        SimpleCharStream.bufline = new int[10];
        SimpleCharStream.bufcolumn = new int[10];

        SimpleCharStream.ExpandBuff(false);

        Assert.assertEquals(2058, SimpleCharStream.bufsize);
        Assert.assertEquals(2058, SimpleCharStream.available);
        Assert.assertEquals(0, SimpleCharStream.tokenBegin);
    }

    @Test
    public void testFillBuff() throws IOException {
        SimpleCharStream.bufsize = 10;
        SimpleCharStream.available = 10;
        SimpleCharStream.maxNextCharInd = 10;
        SimpleCharStream.tokenBegin = 5;
        SimpleCharStream.buffer = new char[10];

        SimpleCharStream.FillBuff();

        Assert.assertEquals(10, SimpleCharStream.available);
    }

    @Test
    public void testBeginToken() throws IOException {
        SimpleCharStream.tokenBegin = -1;
        char c = SimpleCharStream.BeginToken();

        Assert.assertEquals('T', c);
        Assert.assertEquals(0, SimpleCharStream.tokenBegin);
    }

    @Test
    public void testUpdateLineColumn() {
        SimpleCharStream.column = 0;
        SimpleCharStream.line = 1;
        SimpleCharStream.prevCharIsCR = false;
        SimpleCharStream.prevCharIsLF = false;
        SimpleCharStream.bufpos = 0;
        SimpleCharStream.bufline = new int[10];
        SimpleCharStream.bufcolumn = new int[10];

        SimpleCharStream.UpdateLineColumn('a');

        Assert.assertEquals(1, SimpleCharStream.column);
        Assert.assertEquals(1, SimpleCharStream.line);
        Assert.assertEquals(1, SimpleCharStream.bufcolumn[0]);
        Assert.assertEquals(1, SimpleCharStream.bufline[0]);
    }

    @Test
    public void testReadChar() throws IOException {
        char c = SimpleCharStream.readChar();

        Assert.assertEquals('T', c);
    }

    @Test
    public void testGetColumn() {
        SimpleCharStream.bufcolumn = new int[]{1, 2, 3, 4, 5};
        SimpleCharStream.bufpos = 2;

        int column = SimpleCharStream.getColumn();

        Assert.assertEquals(3, column);
    }

    @Test
    public void testGetLine() {
        SimpleCharStream.bufline = new int[]{1, 2, 3, 4, 5};
        SimpleCharStream.bufpos = 2;

        int line = SimpleCharStream.getLine();

        Assert.assertEquals(3, line);
    }

    @Test
    public void testGetEndColumn() {
        SimpleCharStream.bufcolumn = new int[]{1, 2, 3, 4, 5};
        SimpleCharStream.bufpos = 2;

        int endColumn = SimpleCharStream.getEndColumn();

        Assert.assertEquals(3, endColumn);
    }

    @Test
    public void testGetEndLine() {
        SimpleCharStream.bufline = new int[]{1, 2, 3, 4, 5};
        SimpleCharStream.bufpos = 2;

        int endLine = SimpleCharStream.getEndLine();

        Assert.assertEquals(3, endLine);
    }

    @Test
    public void testGetBeginColumn() {
        SimpleCharStream.bufcolumn = new int[]{1, 2, 3, 4, 5};
        SimpleCharStream.tokenBegin = 2;

        int beginColumn = SimpleCharStream.getBeginColumn();

        Assert.assertEquals(3, beginColumn);
    }

    @Test
    public void testGetBeginLine() {
        SimpleCharStream.bufline = new int[]{1, 2, 3, 4, 5};
        SimpleCharStream.tokenBegin = 2;

        int beginLine = SimpleCharStream.getBeginLine();

        Assert.assertEquals(3, beginLine);
    }

    @Test
    public void testBackup() {
        SimpleCharStream.inBuf = 0;
        SimpleCharStream.bufpos = 5;

        SimpleCharStream.backup(3);

        Assert.assertEquals(3, SimpleCharStream.inBuf);
        Assert.assertEquals(2, SimpleCharStream.bufpos);
    }

    @Test
    public void testReInit() {
        Reader newReader = new StringReader("New test string");
        simpleCharStream.ReInit(newReader, 1, 1, 2048);

        Assert.assertEquals(newReader, SimpleCharStream.inputStream);
        Assert.assertEquals(1, SimpleCharStream.line);
        Assert.assertEquals(0, SimpleCharStream.column);
        Assert.assertEquals(2048, SimpleCharStream.bufsize);
    }

    @Test
    public void testGetImage() {
        SimpleCharStream.buffer = new char[]{'T', 'e', 's', 't'};
        SimpleCharStream.tokenBegin = 0;
        SimpleCharStream.bufpos = 3;

        String image = SimpleCharStream.GetImage();

        Assert.assertEquals("Test", image);
    }

    @Test
    public void testGetSuffix() {
        SimpleCharStream.buffer = new char[]{'T', 'e', 's', 't'};
        SimpleCharStream.bufpos = 3;

        char[] suffix = SimpleCharStream.GetSuffix(3);

        Assert.assertArrayEquals(new char[]{'e', 's', 't'}, suffix);
    }

    @Test
    public void testDone() {
        SimpleCharStream.Done();

        Assert.assertNull(SimpleCharStream.buffer);
        Assert.assertNull(SimpleCharStream.bufline);
        Assert.assertNull(SimpleCharStream.bufcolumn);
    }

    @Test
    public void testAdjustBeginLineColumn() {
        SimpleCharStream.bufline = new int[]{1, 1, 1, 1, 1};
        SimpleCharStream.bufcolumn = new int[]{1, 2, 3, 4, 5};
        SimpleCharStream.tokenBegin = 0;
        SimpleCharStream.bufpos = 4;
        SimpleCharStream.inBuf = 0;

        SimpleCharStream.adjustBeginLineColumn(2, 1);

        Assert.assertEquals(2, SimpleCharStream.bufline[0]);
        Assert.assertEquals(1, SimpleCharStream.bufcolumn[0]);
    }
}