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
    private File mockFile;
    private File mockParentFile;

    @Before
    public void setUp() {
        mockAttributes = mock(FollowAppAttributes.class);
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
        verify(mockAttributes).getTabPlacement();
    }

    @Test
    public void testFindComponentAt() {
        Component mockComponent = mock(Component.class);
        when(mockComponent.isVisible()).thenReturn(true);
        tabbedPane.add(mockComponent);

        Component result = tabbedPane.findComponentAt(0, 0);
        assertEquals(mockComponent, result);
    }

    @Test
    public void testFindComponentAt_NotContains() {
        Component result = tabbedPane.findComponentAt(-1, -1);
        assertNull(result);
    }

    @Test
    public void testSetSelectedIndex() {
        tabbedPane.add(mockPane);
        tabbedPane.setSelectedIndex(0);

        verify(mockAttributes).setLastFileChooserDirectory(mockParentFile);
    }

    @Test
    public void testSetSelectedComponent() {
        tabbedPane.add(mockPane);
        tabbedPane.setSelectedComponent(mockPane);

        verify(mockAttributes).setLastFileChooserDirectory(mockParentFile);
    }

    @Test
    public void testRemoveTabAt() {
        tabbedPane.add(mockPane);
        tabbedPane.removeTabAt(0);

        verify(mockAttributes).setLastFileChooserDirectory(mockParentFile);
    }

    @Test
    public void testHandleSelectedFile_NullPane() {
        tabbedPane.handleSelectedFile();
        verify(mockAttributes, never()).setLastFileChooserDirectory(any(File.class));
    }
}