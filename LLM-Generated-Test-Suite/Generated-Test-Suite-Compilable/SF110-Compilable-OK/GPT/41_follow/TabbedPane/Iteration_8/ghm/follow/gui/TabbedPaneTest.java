package ghm.follow.gui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import ghm.follow.config.FollowAppAttributes;
import ghm.follow.gui.TabbedPane;
import ghm.follow.gui.FileFollowingPane;

@RunWith(MockitoJUnitRunner.class)
public class TabbedPaneTest {

    @Mock
    private FollowAppAttributes mockAttributes;

    @Mock
    private FileFollowingPane mockPane;

    private TabbedPane tabbedPane;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
        tabbedPane.addTab("Tab 1", panel);
        Component result = tabbedPane.findComponentAt(0, 0);
        assertEquals(panel, result);
    }

    @Test
    public void testSetSelectedIndex() {
        tabbedPane.addTab("Tab 1", mockPane);
        tabbedPane.setSelectedIndex(0);
        verify(mockAttributes, times(1)).setLastFileChooserDirectory(any(File.class));
    }

    @Test
    public void testSetSelectedComponent() {
        tabbedPane.addTab("Tab 1", mockPane);
        tabbedPane.setSelectedComponent(mockPane);
        verify(mockAttributes, times(1)).setLastFileChooserDirectory(any(File.class));
    }

    @Test
    public void testRemoveTabAt() {
        tabbedPane.addTab("Tab 1", mockPane);
        tabbedPane.removeTabAt(0);
        verify(mockAttributes, times(1)).setLastFileChooserDirectory(any(File.class));
    }

    @Test
    public void testHandleSelectedFile_NullPane() {
        tabbedPane.handleSelectedFile();
        verify(mockAttributes, never()).setLastFileChooserDirectory(any(File.class));
    }

    @Test
    public void testHandleSelectedFile_NonNullPane() {
        File mockFile = mock(File.class);
        File mockParentFile = mock(File.class);
        when(mockPane.getFollowedFile()).thenReturn(mockFile);
        when(mockFile.getParentFile()).thenReturn(mockParentFile);

        tabbedPane.addTab("Tab 1", mockPane);
        tabbedPane.setSelectedComponent(mockPane);
        tabbedPane.handleSelectedFile();

        verify(mockAttributes, times(1)).setLastFileChooserDirectory(mockParentFile);
    }
}