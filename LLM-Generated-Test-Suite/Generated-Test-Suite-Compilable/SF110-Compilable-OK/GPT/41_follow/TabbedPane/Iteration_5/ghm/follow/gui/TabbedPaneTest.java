package ghm.follow.gui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import ghm.follow.config.FollowAppAttributes;
import ghm.follow.gui.TabbedPane;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TabbedPaneTest {

    private TabbedPane tabbedPane;
    private FollowAppAttributes mockAttributes;
    private FileFollowingPane mockPane;

    @Before
    public void setUp() {
        mockAttributes = mock(FollowAppAttributes.class);
        when(mockAttributes.getTabPlacement()).thenReturn(JTabbedPane.TOP);
        tabbedPane = new TabbedPane(mockAttributes);

        mockPane = mock(FileFollowingPane.class);
        when(mockPane.getFollowedFile()).thenReturn(new File("test.txt"));
    }

    @Test
    public void testConstructor() {
        assertEquals(JTabbedPane.TOP, tabbedPane.getTabPlacement());
    }

    @Test
    public void testFindComponentAt() {
        Component component = new JButton();
        tabbedPane.add(component);
        Point point = component.getLocation();
        assertEquals(component, tabbedPane.findComponentAt(point.x, point.y));
    }

    @Test
    public void testFindComponentAtOutsideBounds() {
        assertNull(tabbedPane.findComponentAt(-1, -1));
    }

    @Test
    public void testSetSelectedIndex() {
        tabbedPane.add(mockPane);
        tabbedPane.setSelectedIndex(0);
        verify(mockAttributes, times(1)).setLastFileChooserDirectory(any(File.class));
    }

    @Test
    public void testSetSelectedComponent() {
        tabbedPane.add(mockPane);
        tabbedPane.setSelectedComponent(mockPane);
        verify(mockAttributes, times(1)).setLastFileChooserDirectory(any(File.class));
    }

    @Test
    public void testRemoveTabAt() {
        tabbedPane.add(mockPane);
        tabbedPane.removeTabAt(0);
        verify(mockAttributes, times(1)).setLastFileChooserDirectory(any(File.class));
    }

    @Test
    public void testHandleSelectedFileWithNullPane() {
        tabbedPane.handleSelectedFile();
        verify(mockAttributes, never()).setLastFileChooserDirectory(any(File.class));
    }
}