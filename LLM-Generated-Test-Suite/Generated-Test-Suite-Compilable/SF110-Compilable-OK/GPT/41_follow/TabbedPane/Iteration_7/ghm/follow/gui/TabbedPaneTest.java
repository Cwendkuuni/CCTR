package ghm.follow.gui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import ghm.follow.config.FollowAppAttributes;
import ghm.follow.gui.TabbedPane;
import ghm.follow.gui.FileFollowingPane;

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
    public void testFindComponentAt() {
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 100, 100);
        tabbedPane.addTab("Tab1", panel);

        Component foundComponent = tabbedPane.findComponentAt(50, 50);
        assertEquals(panel, foundComponent);

        foundComponent = tabbedPane.findComponentAt(150, 150);
        assertNull(foundComponent);
    }

    @Test
    public void testSetSelectedIndex() {
        tabbedPane.addTab("Tab1", mockPane);
        tabbedPane.setSelectedIndex(0);

        verify(mockPane, times(1)).getFollowedFile();
    }

    @Test
    public void testSetSelectedComponent() {
        tabbedPane.addTab("Tab1", mockPane);
        tabbedPane.setSelectedComponent(mockPane);

        verify(mockPane, times(1)).getFollowedFile();
    }

    @Test
    public void testRemoveTabAt() {
        tabbedPane.addTab("Tab1", mockPane);
        tabbedPane.removeTabAt(0);

        verify(mockPane, times(1)).getFollowedFile();
    }

    @Test
    public void testHandleSelectedFile() {
        File mockFile = mock(File.class);
        File mockParentFile = mock(File.class);
        when(mockPane.getFollowedFile()).thenReturn(mockFile);
        when(mockFile.getParentFile()).thenReturn(mockParentFile);

        tabbedPane.addTab("Tab1", mockPane);
        tabbedPane.setSelectedComponent(mockPane);

        ArgumentCaptor<File> captor = ArgumentCaptor.forClass(File.class);
        verify(mockAttributes).setLastFileChooserDirectory(captor.capture());
        assertEquals(mockParentFile, captor.getValue());
    }
}