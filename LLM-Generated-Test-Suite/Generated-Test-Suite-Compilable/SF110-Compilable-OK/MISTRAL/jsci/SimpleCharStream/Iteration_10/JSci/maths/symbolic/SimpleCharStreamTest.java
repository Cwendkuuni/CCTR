package JSci.maths.symbolic;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class SimpleCharStreamTest {

    private SimpleCharStream simpleCharStream;

    @Before
    public void setUp() {
        Reader reader = new StringReader("Test data for SimpleCharStream");
        simpleCharStream = new SimpleCharStream(reader);
    }

    @Test
    public void testStaticFlag() {
        Assert.assertTrue(SimpleCharStream.staticFlag);
    }

    @Test
    public void testBeginToken() throws IOException {
        char c = SimpleCharStream.BeginToken();
        Assert.assertEquals('T', c);
    }

    @Test
    public void testReadChar() throws IOException {
        char c = SimpleCharStream.readChar();
        Assert.assertEquals('T', c);
        c = SimpleCharStream.readChar();
        Assert.assertEquals('e', c);
    }

    @Test
    public void testGetColumn() throws IOException {
        SimpleCharStream.readChar();
        int column = SimpleCharStream.getColumn();
        Assert.assertEquals(1, column);
    }

    @Test
    public void testGetLine() throws IOException {
        SimpleCharStream.readChar();
        int line = SimpleCharStream.getLine();
        Assert.assertEquals(1, line);
    }

    @Test
    public void testGetEndColumn() throws IOException {
        SimpleCharStream.readChar();
        int endColumn = SimpleCharStream.getEndColumn();
        Assert.assertEquals(1, endColumn);
    }

    @Test
    public void testGetEndLine() throws IOException {
        SimpleCharStream.readChar();
        int endLine = SimpleCharStream.getEndLine();
        Assert.assertEquals(1, endLine);
    }

    @Test
    public void testGetBeginColumn() throws IOException {
        SimpleCharStream.BeginToken();
        int beginColumn = SimpleCharStream.getBeginColumn();
        Assert.assertEquals(1, beginColumn);
    }

    @Test
    public void testGetBeginLine() throws IOException {
        SimpleCharStream.BeginToken();
        int beginLine = SimpleCharStream.getBeginLine();
        Assert.assertEquals(1, beginLine);
    }

    @Test
    public void testBackup() throws IOException {
        SimpleCharStream.readChar();
        SimpleCharStream.backup(1);
        char c = SimpleCharStream.readChar();
        Assert.assertEquals('T', c);
    }

    @Test
    public void testGetImage() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar();
        SimpleCharStream.readChar();
        String image = SimpleCharStream.GetImage();
        Assert.assertEquals("Te", image);
    }

    @Test
    public void testGetSuffix() throws IOException {
        SimpleCharStream.readChar();
        SimpleCharStream.readChar();
        char[] suffix = SimpleCharStream.GetSuffix(2);
        Assert.assertArrayEquals(new char[]{'e', 's'}, suffix);
    }

    @Test
    public void testDone() {
        SimpleCharStream.Done();
        Assert.assertNull(SimpleCharStream.buffer);
        Assert.assertNull(SimpleCharStream.bufline);
        Assert.assertNull(SimpleCharStream.bufcolumn);
    }

    @Test
    public void testAdjustBeginLineColumn() throws IOException {
        SimpleCharStream.BeginToken();
        SimpleCharStream.readChar();
        SimpleCharStream.adjustBeginLineColumn(2, 5);
        int line = SimpleCharStream.getLine();
        int column = SimpleCharStream.getColumn();
        Assert.assertEquals(2, line);
        Assert.assertEquals(5, column);
    }

    @Test
    public void testReInit() throws IOException {
        Reader newReader = new StringReader("New data for SimpleCharStream");
        simpleCharStream.ReInit(newReader);
        char c = SimpleCharStream.readChar();
        Assert.assertEquals('N', c);
    }

    @Test
    public void testReInitWithInputStream() throws IOException {
        Reader newReader = new StringReader("New data for SimpleCharStream");
        simpleCharStream.ReInit(newReader);
        char c = SimpleCharStream.readChar();
        Assert.assertEquals('N', c);
    }
}