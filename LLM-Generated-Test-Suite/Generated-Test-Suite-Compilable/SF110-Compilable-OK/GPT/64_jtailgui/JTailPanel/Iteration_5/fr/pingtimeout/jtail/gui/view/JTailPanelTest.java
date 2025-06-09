package fr.pingtimeout.jtail.gui.view;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import fr.pingtimeout.jtail.gui.model.JTailModel;
import fr.pingtimeout.jtail.gui.model.JTailModelEvent;
import fr.pingtimeout.jtail.gui.model.JTailModelEvent.Type;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.Observable;

public class JTailPanelTest {

    private JTailModel mockModel;
    private JTailPanel jTailPanel;

    @Before
    public void setUp() {
        mockModel = mock(JTailModel.class);
        jTailPanel = new JTailPanel(mockModel);
    }

    @Test
    public void testGetCharacterHeight() {
        int characterHeight = jTailPanel.getCharacterHeight();
        assertTrue("Character height should be greater than zero", characterHeight > 0);
    }

    @Test
    public void testGetVerticalScrollBar() {
        JScrollBar verticalScrollBar = jTailPanel.getVerticalScrollBar();
        assertNotNull("Vertical scroll bar should not be null", verticalScrollBar);
    }

    @Test
    public void testUpdateWithResizedEvent() {
        JTailModelEvent event = new JTailModelEvent(Type.RESIZED, 0, 10, 100, 10);
        when(mockModel.getLineNumbers()).thenReturn("1\n2\n3\n4\n5\n6\n7\n8\n9\n10");
        when(mockModel.getContent()).thenReturn("Line1\nLine2\nLine3\nLine4\nLine5\nLine6\nLine7\nLine8\nLine9\nLine10");

        jTailPanel.update(mockModel, event);

        assertEquals("Vertical scrollbar minimum should be set", 0, jTailPanel.getVerticalScrollBar().getModel().getMinimum());
        assertEquals("Vertical scrollbar maximum should be set", 100, jTailPanel.getVerticalScrollBar().getModel().getMaximum());
        assertEquals("Vertical scrollbar extent should be set", 10, jTailPanel.getVerticalScrollBar().getModel().getExtent());
        assertEquals("Vertical scrollbar value should be set", 10, jTailPanel.getVerticalScrollBar().getModel().getValue());
    }

    @Test
    public void testUpdateWithScrolledEvent() {
        JTailModelEvent event = new JTailModelEvent(Type.SCROLLED, 0, 10, 100, 10);
        when(mockModel.getLineNumbers()).thenReturn("1\n2\n3\n4\n5\n6\n7\n8\n9\n10");
        when(mockModel.getContent()).thenReturn("Line1\nLine2\nLine3\nLine4\nLine5\nLine6\nLine7\nLine8\nLine9\nLine10");

        jTailPanel.update(mockModel, event);

        // Verify that the table model is updated correctly
        assertEquals("Table model should have 10 rows", 10, jTailPanel.tableModel.getRowCount());
        assertEquals("First row line number should be 1", "1", jTailPanel.tableModel.getValueAt(0, 0));
        assertEquals("First row content should be Line1", "Line1", jTailPanel.tableModel.getValueAt(0, 1));
    }
}