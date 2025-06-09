package ghm.follow.gui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import ghm.follow.config.FollowAppAttributes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TabbedPaneTest {

    private TabbedPane tabbedPane;
    private FollowAppAttributes mockAttributes;
    private FileFollowingPane mockPane;
    private File mockFile;
    private File mockParentFile;

    @Before
    public void setUp() {
        mockAttributes = mock(FollowAppAttributes.class);
        when(mockAttributes.getTabPlacement()).thenReturn(JTabbedPane.TOP);
        tabbedPane = new TabbedPane(mockAttributes);

        mockPane = mock(FileFollowingPane.class);
        mockFile = mock(File.class);
        mockParentFile = mock(File.class);

        when(mockPane.getFollowedFile()).thenReturn(mockFile);
        when(mockFile.getParentFile()).thenReturn(mockParentFile);
    }

    @Test
    public void testConstructor() {
        assertNotNull(tabbedPane);
        assertEquals(JTabbedPane.TOP, tabbedPane.getTabPlacement());
    }

    @Test
    public void testFindComponentAt() {
        Component component = new JButton();
        tabbedPane.add(component);
        component.setBounds(10, 10, 100, 100);

        assertNull(tabbedPane.findComponentAt(5, 5)); // Outside component bounds
        assertEquals(component, tabbedPane.findComponentAt(15, 15)); // Inside component bounds
    }

    @Test
    public void testSetSelectedIndex() {
        tabbedPane.add(mockPane);
        tabbedPane.setSelectedIndex(0);

        verify(mockAttributes, times(1)).setLastFileChooserDirectory(mockParentFile);
    }

    @Test
    public void testSetSelectedComponent() {
        tabbedPane.add(mockPane);
        tabbedPane.setSelectedComponent(mockPane);

        verify(mockAttributes, times(1)).setLastFileChooserDirectory(mockParentFile);
    }

    @Test
    public void testRemoveTabAt() {
        tabbedPane.add(mockPane);
        tabbedPane.removeTabAt(0);

        verify(mockAttributes, times(1)).setLastFileChooserDirectory(mockParentFile);
    }

    @Test
    public void testHandleSelectedFileWithNullPane() {
        tabbedPane.handleSelectedFile();
        verify(mockAttributes, never()).setLastFileChooserDirectory(any(File.class));
    }
}