package ghm.follow.gui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import ghm.follow.config.FollowAppAttributes;
import ghm.follow.gui.TabbedPane;
import ghm.follow.gui.FileFollowingPane;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TabbedPaneTest {

    private FollowAppAttributes mockAttributes;
    private TabbedPane tabbedPane;

    @Before
    public void setUp() {
        mockAttributes = mock(FollowAppAttributes.class);
        when(mockAttributes.getTabPlacement()).thenReturn(JTabbedPane.TOP);
        tabbedPane = new TabbedPane(mockAttributes);
    }

    @Test
    public void testConstructor() {
        assertEquals(JTabbedPane.TOP, tabbedPane.getTabPlacement());
        assertNotNull(tabbedPane);
    }

    @Test
    public void testFindComponentAt_NotContained() {
        Component result = tabbedPane.findComponentAt(-1, -1);
        assertNull(result);
    }

    @Test
    public void testFindComponentAt_Contained() {
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 100, 100);
        tabbedPane.addTab("Tab 1", panel);

        Component result = tabbedPane.findComponentAt(10, 10);
        assertEquals(panel, result);
    }

    @Test
    public void testSetSelectedIndex() {
        JPanel panel = new JPanel();
        tabbedPane.addTab("Tab 1", panel);

        tabbedPane.setSelectedIndex(0);
        assertEquals(0, tabbedPane.getSelectedIndex());
    }

    @Test
    public void testSetSelectedComponent() {
        FileFollowingPane pane = mock(FileFollowingPane.class);
        tabbedPane.addTab("Tab 1", pane);

        tabbedPane.setSelectedComponent(pane);
        assertEquals(pane, tabbedPane.getSelectedComponent());
    }

    @Test
    public void testRemoveTabAt() {
        JPanel panel = new JPanel();
        tabbedPane.addTab("Tab 1", panel);

        tabbedPane.removeTabAt(0);
        assertEquals(0, tabbedPane.getTabCount());
    }

    @Test
    public void testHandleSelectedFile() {
        FileFollowingPane pane = mock(FileFollowingPane.class);
        File file = mock(File.class);
        File parentFile = mock(File.class);

        when(pane.getFollowedFile()).thenReturn(file);
        when(file.getParentFile()).thenReturn(parentFile);

        tabbedPane.addTab("Tab 1", pane);
        tabbedPane.setSelectedComponent(pane);

        ArgumentCaptor<File> captor = ArgumentCaptor.forClass(File.class);
        verify(mockAttributes).setLastFileChooserDirectory(captor.capture());
        assertEquals(parentFile, captor.getValue());
    }
}