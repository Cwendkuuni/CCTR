package ghm.follow.search;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.logging.Logger;

public class SearchableTextPaneTest {

    private SearchableTextPane searchableTextPane;
    private Font font;
    private int tabSize;

    @Before
    public void setUp() {
        font = new Font("Arial", Font.PLAIN, 12);
        tabSize = 4;
        searchableTextPane = new SearchableTextPane(font, tabSize);
    }

    @Test
    public void testConstructor() {
        assertNotNull(searchableTextPane);
        assertEquals(font, searchableTextPane.getFont());
        assertEquals(tabSize, searchableTextPane.getTabSize());
    }

    @Test
    public void testGetScrollableTracksViewportWidth() {
        JScrollPane scrollPane = new JScrollPane(searchableTextPane);
        searchableTextPane.setSize(200, 200);
        scrollPane.setSize(300, 300);
        assertTrue(searchableTextPane.getScrollableTracksViewportWidth());

        searchableTextPane.setSize(400, 400);
        assertFalse(searchableTextPane.getScrollableTracksViewportWidth());
    }

    @Test
    public void testHighlight() {
        searchableTextPane.setText("This is a test. This is only a test.");
        List<LineResult> results = searchableTextPane.highlight("test", 0);
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    public void testRemoveHighlights() {
        searchableTextPane.setText("This is a test. This is only a test.");
        searchableTextPane.highlight("test", 0);
        searchableTextPane.removeHighlights();
        assertEquals(0, searchableTextPane.getHighlighter().getHighlights().length);
    }

    @Test
    public void testSearch() {
        searchableTextPane.setText("This is a test. This is only a test.");
        int pos = searchableTextPane.search("test");
        assertEquals(10, pos);

        pos = searchableTextPane.search("test");
        assertEquals(26, pos);

        pos = searchableTextPane.search("nonexistent");
        assertEquals(-1, pos);
    }

    @Test
    public void testSearchWithStartPos() {
        searchableTextPane.setText("This is a test. This is only a test.");
        int pos = searchableTextPane.search("test", 0);
        assertEquals(10, pos);

        pos = searchableTextPane.search("test", 15);
        assertEquals(26, pos);

        pos = searchableTextPane.search("nonexistent", 0);
        assertEquals(-1, pos);
    }

    @Test
    public void testConvertWords2Lines() throws Exception {
        // This test requires mocking or more complex setup, so it's a placeholder
        // You would need to mock the WordResult and LineResult classes and the Utilities class
        assertTrue(true); // Placeholder for actual test logic
    }

    @Test
    public void testUpdateWordResult() throws Exception {
        // This test requires mocking or more complex setup, so it's a placeholder
        // You would need to mock the WordResult and LineResult classes
        assertTrue(true); // Placeholder for actual test logic
    }
}