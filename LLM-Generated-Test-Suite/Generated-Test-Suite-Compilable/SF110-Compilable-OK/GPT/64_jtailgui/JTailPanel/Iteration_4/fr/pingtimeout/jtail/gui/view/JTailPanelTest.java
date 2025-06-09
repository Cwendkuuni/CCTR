package fr.pingtimeout.jtail.gui.view;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import fr.pingtimeout.jtail.gui.model.JTailModel;
import fr.pingtimeout.jtail.gui.model.JTailModelEvent;
import fr.pingtimeout.jtail.gui.model.JTailModelEvent.Type;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

public class JTailPanelTest {

    private JTailPanel jTailPanel;
    private JTailModel mockModel;

    @Before
    public void setUp() {
        mockModel = mock(JTailModel.class);
        jTailPanel = new JTailPanel(mockModel);
    }

    @Test
    public void testGetCharacterHeight() {
        FontMetrics metrics = jTailPanel.dataTable.getFontMetrics(jTailPanel.dataTable.getFont());
        int expectedCharacterHeight = metrics.getHeight() - 1;
        assertEquals(expectedCharacterHeight, jTailPanel.getCharacterHeight());
    }

    @Test
    public void testGetVerticalScrollBar() {
        JScrollBar verticalScrollBar = jTailPanel.getVerticalScrollBar();
        assertNotNull(verticalScrollBar);
        assertEquals(jTailPanel.verticalScrollBar, verticalScrollBar);
    }

    @Test
    public void testUpdateWithResizedEvent() {
        JTailModelEvent event = new JTailModelEvent(Type.RESIZED, 0, 10, 100, 10);
        when(mockModel.getLineNumbers()).thenReturn("1\n2\n3\n4\n5\n6\n7\n8\n9\n10");
        when(mockModel.getContent()).thenReturn("Line 1\nLine 2\nLine 3\nLine 4\nLine 5\nLine 6\nLine 7\nLine 8\nLine 9\nLine 10");

        jTailPanel.update(mockModel, event);

        BoundedRangeModel rangeModel = jTailPanel.getVerticalScrollBar().getModel();
        assertEquals(0, rangeModel.getMinimum());
        assertEquals(10, rangeModel.getValue());
        assertEquals(10, rangeModel.getExtent());
        assertEquals(100, rangeModel.getMaximum());
        assertFalse(jTailPanel.getVerticalScrollBar().isEnabled());
    }

    @Test
    public void testUpdateWithScrolledEvent() {
        JTailModelEvent event = new JTailModelEvent(Type.SCROLLED, 0, 10, 100, 10);
        when(mockModel.getLineNumbers()).thenReturn("1\n2\n3\n4\n5\n6\n7\n8\n9\n10");
        when(mockModel.getContent()).thenReturn("Line 1\nLine 2\nLine 3\nLine 4\nLine 5\nLine 6\nLine 7\nLine 8\nLine 9\nLine 10");

        jTailPanel.update(mockModel, event);

        assertEquals(10, jTailPanel.tableModel.getRowCount());
        assertEquals("1", jTailPanel.tableModel.getValueAt(0, 0));
        assertEquals("Line 1", jTailPanel.tableModel.getValueAt(0, 1));
    }
}