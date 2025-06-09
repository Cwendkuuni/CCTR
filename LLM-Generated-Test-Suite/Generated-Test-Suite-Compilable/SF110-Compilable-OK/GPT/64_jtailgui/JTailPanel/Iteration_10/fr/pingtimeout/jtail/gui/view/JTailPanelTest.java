package fr.pingtimeout.jtail.gui.view;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Observable;

import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import fr.pingtimeout.jtail.gui.model.JTailModel;
import fr.pingtimeout.jtail.gui.model.JTailModelEvent;
import fr.pingtimeout.jtail.gui.model.JTailModelEvent.Type;

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
        assertTrue("Character height should be greater than 0", characterHeight > 0);
    }

    @Test
    public void testGetVerticalScrollBar() {
        JScrollBar scrollBar = jTailPanel.getVerticalScrollBar();
        assertNotNull("Vertical scroll bar should not be null", scrollBar);
    }

    @Test
    public void testUpdateWithResizedEvent() {
        JTailModelEvent event = new JTailModelEvent(Type.RESIZED, 0, 10, 5, 15);
        when(mockModel.getLineNumbers()).thenReturn("1\n2\n3\n4\n5");
        when(mockModel.getContent()).thenReturn("Line1\nLine2\nLine3\nLine4\nLine5");

        jTailPanel.update(mockModel, event);

        JScrollBar scrollBar = jTailPanel.getVerticalScrollBar();
        assertEquals(0, scrollBar.getModel().getMinimum());
        assertEquals(10, scrollBar.getModel().getValue());
        assertEquals(5, scrollBar.getModel().getExtent());
        assertEquals(15, scrollBar.getModel().getMaximum());

        DefaultTableModel tableModel = (DefaultTableModel) jTailPanel.dataTable.getModel();
        assertEquals(5, tableModel.getRowCount());
        assertEquals("1", tableModel.getValueAt(0, 0));
        assertEquals("Line1", tableModel.getValueAt(0, 1));
    }

    @Test
    public void testUpdateWithScrolledEvent() {
        JTailModelEvent event = new JTailModelEvent(Type.SCROLLED, 0, 10, 5, 15);
        when(mockModel.getLineNumbers()).thenReturn("1\n2\n3\n4\n5");
        when(mockModel.getContent()).thenReturn("Line1\nLine2\nLine3\nLine4\nLine5");

        jTailPanel.update(mockModel, event);

        DefaultTableModel tableModel = (DefaultTableModel) jTailPanel.dataTable.getModel();
        assertEquals(5, tableModel.getRowCount());
        assertEquals("1", tableModel.getValueAt(0, 0));
        assertEquals("Line1", tableModel.getValueAt(0, 1));
    }

    @Test
    public void testUpdateWithNonModelObservable() {
        Observable nonModelObservable = mock(Observable.class);
        JTailModelEvent event = new JTailModelEvent(Type.RESIZED, 0, 10, 5, 15);

        jTailPanel.update(nonModelObservable, event);

        // Ensure no interaction with the model or UI components
        verify(mockModel, never()).getLineNumbers();
        verify(mockModel, never()).getContent();
    }
}