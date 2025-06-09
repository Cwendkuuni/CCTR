package org.apache.commons.csv;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LexerTest {

    private static final char DELIMITER = ',';
    private static final char ESCAPE = '\\';
    private static final char QUOTE_CHAR = '"';
    private static final char COMMENT_START = '#';

    private Lexer lexer;
    private CSVFormat format;
    private ExtendedBufferedReader in;

    @Before
    public void setUp() {
        format = mock(CSVFormat.class);
        in = mock(ExtendedBufferedReader.class);

        when(format.getDelimiter()).thenReturn(DELIMITER);
        when(format.getEscape()).thenReturn(ESCAPE);
        when(format.getQuoteChar()).thenReturn(QUOTE_CHAR);
        when(format.getCommentStart()).thenReturn(COMMENT_START);
        when(format.getIgnoreSurroundingSpaces()).thenReturn(true);
        when(format.getIgnoreEmptyLines()).thenReturn(true);

        lexer = new ConcreteLexer(format, in);
    }

    @Test
    public void testGetLineNumber() throws IOException {
        when(in.getLineNumber()).thenReturn(42L);
        assertEquals(42L, lexer.getLineNumber());
    }

    @Test
    public void testReadEscape() throws IOException {
        when(in.read()).thenReturn((int) 'n');
        assertEquals(Constants.LF, lexer.readEscape());

        when(in.read()).thenReturn((int) 'r');
        assertEquals(Constants.CR, lexer.readEscape());

        when(in.read()).thenReturn((int) 't');
        assertEquals(Constants.TAB, lexer.readEscape());

        when(in.read()).thenReturn((int) 'b');
        assertEquals(Constants.BACKSPACE, lexer.readEscape());

        when(in.read()).thenReturn((int) 'f');
        assertEquals(Constants.FF, lexer.readEscape());

        when(in.read()).thenReturn(Constants.END_OF_STREAM);
        try {
            lexer.readEscape();
            fail("Expected IOException");
        } catch (IOException e) {
            assertEquals("EOF whilst processing escape sequence", e.getMessage());
        }
    }

    @Test
    public void testTrimTrailingSpaces() {
        StringBuilder buffer = new StringBuilder("test   ");
        lexer.trimTrailingSpaces(buffer);
        assertEquals("test", buffer.toString());

        buffer = new StringBuilder("   ");
        lexer.trimTrailingSpaces(buffer);
        assertEquals("", buffer.toString());

        buffer = new StringBuilder("test");
        lexer.trimTrailingSpaces(buffer);
        assertEquals("test", buffer.toString());
    }

    @Test
    public void testReadEndOfLine() throws IOException {
        when(in.lookAhead()).thenReturn((int) '\n');
        when(in.read()).thenReturn((int) '\n');
        assertTrue(lexer.readEndOfLine('\r'));

        assertTrue(lexer.readEndOfLine('\n'));
        assertTrue(lexer.readEndOfLine('\r'));
        assertFalse(lexer.readEndOfLine('a'));
    }

    @Test
    public void testIsWhitespace() {
        assertTrue(lexer.isWhitespace(' '));
        assertTrue(lexer.isWhitespace('\t'));
        assertFalse(lexer.isWhitespace(DELIMITER));
    }

    @Test
    public void testIsStartOfLine() {
        assertTrue(lexer.isStartOfLine('\n'));
        assertTrue(lexer.isStartOfLine('\r'));
        assertTrue(lexer.isStartOfLine(Constants.UNDEFINED));
        assertFalse(lexer.isStartOfLine('a'));
    }

    @Test
    public void testIsEndOfFile() {
        assertTrue(lexer.isEndOfFile(Constants.END_OF_STREAM));
        assertFalse(lexer.isEndOfFile('a'));
    }

    @Test
    public void testIsDelimiter() {
        assertTrue(lexer.isDelimiter(DELIMITER));
        assertFalse(lexer.isDelimiter('a'));
    }

    @Test
    public void testIsEscape() {
        assertTrue(lexer.isEscape(ESCAPE));
        assertFalse(lexer.isEscape('a'));
    }

    @Test
    public void testIsQuoteChar() {
        assertTrue(lexer.isQuoteChar(QUOTE_CHAR));
        assertFalse(lexer.isQuoteChar('a'));
    }

    @Test
    public void testIsCommentStart() {
        assertTrue(lexer.isCommentStart(COMMENT_START));
        assertFalse(lexer.isCommentStart('a'));
    }

    // Concrete implementation of Lexer for testing purposes
    private static class ConcreteLexer extends Lexer {
        ConcreteLexer(CSVFormat format, ExtendedBufferedReader in) {
            super(format, in);
        }

        @Override
        Token nextToken(Token reusableToken) throws IOException {
            return null; // Not needed for these tests
        }
    }
}