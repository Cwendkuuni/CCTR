package fr.pingtimeout.jtail.gui.view;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import fr.pingtimeout.jtail.gui.model.JTailModel;
import fr.pingtimeout.jtail.gui.model.JTailModelEvent;
import fr.pingtimeout.jtail.gui.model.JTailModelEvent.Type;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
        when(mockModel.getContent()).thenReturn("Line1\nLine2\nLine3\nLine4\nLine5\nLine6\nLine7\nLine8\nLine9\nLine10");

        jTailPanel.update(mockModel, event);

        BoundedRangeModel rangeModel = jTailPanel.verticalScrollBar.getModel();
        assertEquals(0, rangeModel.getMinimum());
        assertEquals(10, rangeModel.getValue());
        assertEquals(10, rangeModel.getExtent());
        assertEquals(100, rangeModel.getMaximum());

        DefaultTableModel tableModel = jTailPanel.tableModel;
        assertEquals(10, tableModel.getRowCount());
        assertEquals("1", tableModel.getValueAt(0, 0));
        assertEquals("Line1", tableModel.getValueAt(0, 1));
    }

    @Test
    public void testUpdateWithScrolledEvent() {
        JTailModelEvent event = new JTailModelEvent(Type.SCROLLED, 0, 10, 100, 10);
        when(mockModel.getLineNumbers()).thenReturn("1\n2\n3\n4\n5\n6\n7\n8\n9\n10");
        when(mockModel.getContent()).thenReturn("Line1\nLine2\nLine3\nLine4\nLine5\nLine6\nLine7\nLine8\nLine9\nLine10");

        jTailPanel.update(mockModel, event);

        DefaultTableModel tableModel = jTailPanel.tableModel;
        assertEquals(10, tableModel.getRowCount());
        assertEquals("1", tableModel.getValueAt(0, 0));
        assertEquals("Line1", tableModel.getValueAt(0, 1));
    }

    @Test
    public void testVerticalScrollBarEnabledState() {
        JTailModelEvent event = new JTailModelEvent(Type.RESIZED, 0, 0, 0, 0);
        jTailPanel.update(mockModel, event);
        assertFalse(jTailPanel.getVerticalScrollBar().isEnabled());

        event = new JTailModelEvent(Type.RESIZED, 0, 10, 100, 10);
        jTailPanel.update(mockModel, event);
        assertTrue(jTailPanel.getVerticalScrollBar().isEnabled());
    }
}