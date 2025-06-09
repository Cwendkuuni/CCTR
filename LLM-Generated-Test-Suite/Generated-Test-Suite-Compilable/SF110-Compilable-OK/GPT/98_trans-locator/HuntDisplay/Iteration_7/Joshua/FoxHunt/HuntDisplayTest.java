package Joshua.FoxHunt;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class HuntDisplayTest {

    private HuntDisplay huntDisplay;

    @Before
    public void setUp() {
        huntDisplay = new HuntDisplay();
    }

    @Test
    public void testGetPreferredSize() {
        Dimension expected = new Dimension(200, 200);
        assertEquals(expected, huntDisplay.getPreferredSize());
    }

    @Test
    public void testGetMinimumSize() {
        Dimension expected = new Dimension(50, 50);
        assertEquals(expected, huntDisplay.getMinimumSize());
    }

    @Test
    public void testAddPoint() {
        TrigPoint mockPoint = Mockito.mock(TrigPoint.class);
        Mockito.when(mockPoint.getX()).thenReturn(180.0);
        Mockito.when(mockPoint.getY()).thenReturn(90.0);
        Mockito.when(mockPoint.getTheta()).thenReturn(0.0);
        Mockito.when(mockPoint.isFox()).thenReturn(false);

        huntDisplay.addPoint(mockPoint);

        Vector points = huntDisplay.points;
        assertEquals(1, points.size());
        TrigPoint addedPoint = (TrigPoint) points.get(0);
        assertEquals(1.0, addedPoint.getX(), 0.001);
        assertEquals(1.0, addedPoint.getY(), 0.001);
        assertEquals(0.0, addedPoint.getTheta(), 0.001);
        assertFalse(addedPoint.isFox());
    }

    @Test
    public void testAddLandMark() {
        Landmark mockLandmark = Mockito.mock(Landmark.class);
        Mockito.when(mockLandmark.getLat()).thenReturn(90.0);
        Mockito.when(mockLandmark.getLon()).thenReturn(180.0);
        ImageIcon mockIcon = Mockito.mock(ImageIcon.class);
        Mockito.when(mockLandmark.getIcon()).thenReturn(mockIcon);

        huntDisplay.addLandMark(mockLandmark);

        Vector landmarks = huntDisplay.landmarks;
        assertEquals(1, landmarks.size());
        Landmark addedLandmark = (Landmark) landmarks.get(0);
        assertEquals(1.0, addedLandmark.getLat(), 0.001);
        assertEquals(1.0, addedLandmark.getLon(), 0.001);
        assertEquals(mockIcon, addedLandmark.getIcon());
    }

    @Test
    public void testPaintComponent() {
        // This test is a basic structure. UI testing tools are recommended for comprehensive testing.
        Graphics mockGraphics = Mockito.mock(Graphics.class);
        huntDisplay.paintComponent(mockGraphics);

        // Verify that the graphics context is used, but detailed verification would require a UI testing tool.
        Mockito.verify(mockGraphics, Mockito.atLeastOnce()).setColor(Mockito.any(Color.class));
    }
}