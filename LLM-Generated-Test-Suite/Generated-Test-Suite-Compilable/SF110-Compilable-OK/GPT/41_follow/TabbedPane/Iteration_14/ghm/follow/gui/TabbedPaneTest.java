package ghm.follow.gui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import ghm.follow.config.FollowAppAttributes;
import ghm.follow.gui.TabbedPane;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TabbedPaneTest {

    private FollowAppAttributes mockAttributes;
    private TabbedPane tabbedPane;
    private FileFollowingPane mockPane;

    @Before
    public void setUp() {
        mockAttributes = mock(FollowAppAttributes.class);
        when(mockAttributes.getTabPlacement()).thenReturn(JTabbedPane.TOP);
        tabbedPane = new TabbedPane(mockAttributes);
        mockPane = mock(FileFollowingPane.class);
    }

    @Test
    public void testConstructor() {
        assertEquals(JTabbedPane.TOP, tabbedPane.getTabPlacement());
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
        tabbedPane.add(panel);

        Component result = tabbedPane.findComponentAt(50, 50);
        assertEquals(panel, result);
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
    public void testHandleSelectedFile_NullPane() {
        tabbedPane.handleSelectedFile();
        verify(mockAttributes, never()).setLastFileChooserDirectory(any(File.class));
    }

    @Test
    public void testHandleSelectedFile_ValidPane() {
        File mockFile = mock(File.class);
        File mockParentFile = mock(File.class);
        when(mockPane.getFollowedFile()).thenReturn(mockFile);
        when(mockFile.getParentFile()).thenReturn(mockParentFile);

        tabbedPane.add(mockPane);
        tabbedPane.setSelectedComponent(mockPane);

        ArgumentCaptor<File> captor = ArgumentCaptor.forClass(File.class);
        verify(mockAttributes).setLastFileChooserDirectory(captor.capture());
        assertEquals(mockParentFile, captor.getValue());
    }
}