package ghm.follow.search;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.util.List;
import java.util.logging.Logger;

public class SearchableTextPaneTest {

    private SearchableTextPane textPane;
    private Document mockDocument;

    @Before
    public void setUp() {
        textPane = new SearchableTextPane(new Font("Arial", Font.PLAIN, 12), 4);
        mockDocument = Mockito.mock(Document.class);
        textPane.setDocument(mockDocument);
    }

    @Test
    public void testGetScrollableTracksViewportWidth() {
        assertTrue(textPane.getScrollableTracksViewportWidth());
    }

    @Test
    public void testHighlight() throws BadLocationException {
        Mockito.when(mockDocument.getText(0, 0)).thenReturn("This is a test text for highlighting.");
        List<LineResult> results = textPane.highlight("test", 0);
        assertNotNull(results);
        assertFalse(results.isEmpty());
    }

    @Test
    public void testHighlightWithNoTerm() throws BadLocationException {
        List<LineResult> results = textPane.highlight("", 0);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void testRemoveHighlights() {
        textPane.removeHighlights();
        // Assuming removeHighlights works if no exception is thrown
    }

    @Test
    public void testSearch() throws BadLocationException {
        Mockito.when(mockDocument.getText(0, 0)).thenReturn("This is a test text for searching.");
        int pos = textPane.search("test");
        assertEquals(10, pos);
    }

    @Test
    public void testSearchWithNoTerm() {
        int pos = textPane.search("");
        assertEquals(-1, pos);
    }

    @Test
    public void testSearchWithStartPos() throws BadLocationException {
        Mockito.when(mockDocument.getText(0, 0)).thenReturn("This is a test text for searching.");
        int pos = textPane.search("test", 5);
        assertEquals(10, pos);
    }

    @Test
    public void testSearchWithInvalidStartPos() throws BadLocationException {
        Mockito.when(mockDocument.getText(0, 0)).thenReturn("This is a test text for searching.");
        int pos = textPane.search("test", 50);
        assertEquals(-1, pos);
    }

    @Test
    public void testLoggerInitialization() {
        Logger logger = Logger.getLogger(SearchableTextPane.class.getName());
        assertNotNull(logger);
    }
}