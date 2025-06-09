package freemind.extensions;

import freemind.view.mindmapview.MapView;
import javax.swing.filechooser.FileFilter;
import javax.swing.*;
import freemind.modes.ModeController;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.MessageFormat;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ExportHookTest {

    private ExportHook exportHook;
    private ModeController mockController;
    private MapView mockView;

    @Before
    public void setUp() {
        exportHook = new ExportHook();
        mockController = mock(ModeController.class);
        mockView = mock(MapView.class);
        when(mockController.getView()).thenReturn(mockView);
        exportHook.setController(mockController);
    }

    @Test
    public void testChooseFile() {
        String type = "png";
        String description = "PNG Image";
        String nameExtension = ".png";
        File file = exportHook.chooseFile(type, description, nameExtension);
        assertNull(file); // Assuming the file chooser dialog is not shown in tests
    }

    @Test
    public void testChooseImageFile() {
        String type = "png";
        String description = "PNG Image";
        String nameExtension = ".png";
        File file = ExportHook.chooseImageFile(type, description, nameExtension, mockController);
        assertNull(file); // Assuming the file chooser dialog is not shown in tests
    }

    @Test
    public void testGetTranslatableResourceString() {
        String resourceName = "%test";
        when(mockController.getText("test")).thenReturn("Test String");
        String result = exportHook.getTranslatableResourceString(resourceName);
        assertEquals("Test String", result);
    }

    @Test
    public void testCreateBufferedImage() {
        when(mockView.getInnerBounds()).thenReturn(new Rectangle(0, 0, 100, 100));
        BufferedImage image = exportHook.createBufferedImage();
        assertNotNull(image);
        assertEquals(100, image.getWidth());
        assertEquals(100, image.getHeight());
    }

    @Test
    public void testCopyFromResource() {
        String prefix = "prefix/";
        String fileName = "test.txt";
        String destinationDirectory = "destination/";
        exportHook.copyFromResource(prefix, fileName, destinationDirectory);
        // Verify logging or file copying behavior
    }

    @Test
    public void testCopyFromFile() {
        String dir = "source/";
        String fileName = "test.txt";
        String destinationDirectory = "destination/";
        exportHook.copyFromFile(dir, fileName, destinationDirectory);
        // Verify logging or file copying behavior
    }

    @Test
    public void testImageFilterAccept() {
        FileFilter filter = new ExportHook.ImageFilter("png", "PNG Image");
        File file = new File("test.png");
        assertTrue(filter.accept(file));
    }

    @Test
    public void testImageFilterGetDescription() {
        FileFilter filter = new ExportHook.ImageFilter("png", "PNG Image");
        assertEquals("PNG Image", filter.getDescription());
    }
}