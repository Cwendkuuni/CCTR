package com.com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LineColumn;

public class FileTextTest {

    private File testFile;
    private FileText fileText;
    private final String charsetName = "UTF-8";

    @Before
    public void setUp() throws IOException {
        testFile = File.createTempFile("testFile", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("Line 1\nLine 2\nLine 3");
        }
        fileText = new FileText(testFile, charsetName);
    }

    @Test
    public void testConstructorWithFileAndCharset() throws IOException {
        assertNotNull(fileText);
        assertEquals(testFile, fileText.getFile());
        assertEquals(Charset.forName(charsetName), fileText.getCharset());
        assertEquals("Line 1\nLine 2\nLine 3", fileText.getFullText().toString());
    }

    @Test
    public void testConstructorWithFileText() {
        FileText copy = new FileText(fileText);
        assertNotNull(copy);
        assertEquals(fileText.getFile(), copy.getFile());
        assertEquals(fileText.getCharset(), copy.getCharset());
        assertEquals(fileText.getFullText().toString(), copy.getFullText().toString());
        assertArrayEquals(fileText.toLinesArray(), copy.toLinesArray());
    }

    @Test
    public void testFromLines() {
        List<String> lines = new ArrayList<>();
        lines.add("Line 1");
        lines.add("Line 2");
        lines.add("Line 3");
        FileText fromLines = FileText.fromLines(testFile, lines);
        assertNotNull(fromLines);
        assertEquals(testFile, fromLines.getFile());
        assertNull(fromLines.getCharset());
        assertEquals("Line 1\nLine 2\nLine 3\n", fromLines.getFullText().toString());
    }

    @Test
    public void testGetFile() {
        assertEquals(testFile, fileText.getFile());
    }

    @Test
    public void testGetCharset() {
        assertEquals(Charset.forName(charsetName), fileText.getCharset());
    }

    @Test
    public void testGetFullText() {
        assertEquals("Line 1\nLine 2\nLine 3", fileText.getFullText().toString());
    }

    @Test
    public void testToLinesArray() {
        String[] expectedLines = {"Line 1", "Line 2", "Line 3"};
        assertArrayEquals(expectedLines, fileText.toLinesArray());
    }

    @Test
    public void testLineColumn() {
        LineColumn lineColumn = fileText.lineColumn(8); // Position of 'L' in "Line 2"
        assertEquals(2, lineColumn.getLine());
        assertEquals(0, lineColumn.getColumn());
    }

    @Test
    public void testGet() {
        assertEquals("Line 1", fileText.get(0));
        assertEquals("Line 2", fileText.get(1));
        assertEquals("Line 3", fileText.get(2));
    }

    @Test
    public void testSize() {
        assertEquals(3, fileText.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testUnsupportedCharsetException() throws IOException {
        new FileText(testFile, "UNSUPPORTED_CHARSET");
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileNotFoundException() throws IOException {
        File nonExistentFile = new File("nonExistentFile.txt");
        new FileText(nonExistentFile, charsetName);
    }
}