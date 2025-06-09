package ghm.follow.gui;

import ghm.follow.config.FollowAppAttributes;
import ghm.follow.gui.FileFollowingPane;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TabbedPaneTest {

    private TabbedPane tabbedPane;
    private FollowAppAttributes attributes;
    private FileFollowingPane fileFollowingPane;

    @Before
    public void setUp() {
        attributes = mock(FollowAppAttributes.class);
        tabbedPane = new TabbedPane(attributes);
        fileFollowingPane = mock(FileFollowingPane.class);
    }

    @Test
    public void testFindComponentAt() {
        // Mock components
        JPanel panel1 = mock(JPanel.class);
        JPanel panel2 = mock(JPanel.class);

        // Add components to the tabbed pane
        tabbedPane.addTab("Tab 1", panel1);
        tabbedPane.addTab("Tab 2", panel2);

        // Mock component positions
        when(panel1.getX()).thenReturn(0);
        when(panel1.getY()).thenReturn(0);
        when(panel2.getX()).thenReturn(100);
        when(panel2.getY()).thenReturn(100);

        // Test finding component at specific coordinates
        Component comp = tabbedPane.findComponentAt(50, 50);
        assertNotNull(comp);
        assertEquals(panel1, comp);

        comp = tabbedPane.findComponentAt(150, 150);
        assertNotNull(comp);
        assertEquals(panel2, comp);

        comp = tabbedPane.findComponentAt(200, 200);
        assertNull(comp);
    }

    @Test
    public void testSetSelectedIndex() {
        tabbedPane.setSelectedIndex(1);
        verify(attributes).setLastFileChooserDirectory(any(File.class));
    }

    @Test
    public void testSetSelectedComponent() {
        when(fileFollowingPane.getFollowedFile()).thenReturn(mock(File.class));
        tabbedPane.setSelectedComponent(fileFollowingPane);
        verify(attributes).setLastFileChooserDirectory(any(File.class));
    }

    @Test
    public void testRemoveTabAt() {
        tabbedPane.removeTabAt(0);
        verify(attributes).setLastFileChooserDirectory(any(File.class));
    }

    @Test
    public void testHandleSelectedFile() {
        when(fileFollowingPane.getFollowedFile()).thenReturn(mock(File.class));
        when(tabbedPane.getSelectedComponent()).thenReturn(fileFollowingPane);

        // Call the private method using reflection
        try {
            java.lang.reflect.Method method = TabbedPane.class.getDeclaredMethod("handleSelectedFile");
            method.setAccessible(true);
            method.invoke(tabbedPane);
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(attributes).setLastFileChooserDirectory(any(File.class));
    }
}