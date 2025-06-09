package ghm.follow.gui;

import ghm.follow.config.FollowAppAttributes;
import ghm.follow.gui.FileFollowingPane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TabbedPaneTest {

    @Mock
    private FollowAppAttributes attributes;

    @Mock
    private FileFollowingPane fileFollowingPane;

    @Mock
    private File file;

    private TabbedPane tabbedPane;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tabbedPane = new TabbedPane(attributes);
    }

    @Test
    public void testFindComponentAt() {
        // Mock components
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        tabbedPane.addTab("Tab 1", panel1);
        tabbedPane.addTab("Tab 2", panel2);

        // Test when no component is found
        Component comp = tabbedPane.findComponentAt(100, 100);
        assertNull(comp);

        // Test when a component is found
        panel1.setBounds(0, 0, 200, 200);
        panel2.setBounds(200, 0, 200, 200);
        comp = tabbedPane.findComponentAt(50, 50);
        assertEquals(panel1, comp);
    }

    @Test
    public void testSetSelectedIndex() {
        tabbedPane.setSelectedIndex(0);
        verify(attributes).setLastFileChooserDirectory(any(File.class));
    }

    @Test
    public void testSetSelectedComponent() {
        when(fileFollowingPane.getFollowedFile()).thenReturn(file);
        when(file.getParentFile()).thenReturn(new File("parent"));

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
        when(fileFollowingPane.getFollowedFile()).thenReturn(file);
        when(file.getParentFile()).thenReturn(new File("parent"));

        tabbedPane.setSelectedComponent(fileFollowingPane);
        tabbedPane.handleSelectedFile();
        verify(attributes).setLastFileChooserDirectory(any(File.class));
    }
}