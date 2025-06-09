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
    private TrigPoint mockTrigPoint;
    private Landmark mockLandmark;

    @Before
    public void setUp() {
        huntDisplay = new HuntDisplay();
        mockTrigPoint = Mockito.mock(TrigPoint.class);
        mockLandmark = Mockito.mock(Landmark.class);
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
        Mockito.when(mockTrigPoint.getX()).thenReturn(180.0);
        Mockito.when(mockTrigPoint.getY()).thenReturn(90.0);
        Mockito.when(mockTrigPoint.getTheta()).thenReturn(0.0);
        Mockito.when(mockTrigPoint.isFox()).thenReturn(false);

        huntDisplay.addPoint(mockTrigPoint);

        Vector points = huntDisplay.points;
        assertEquals(1, points.size());
        TrigPoint addedPoint = (TrigPoint) points.elementAt(0);
        assertEquals(1.0, addedPoint.getX(), 0.001);
        assertEquals(1.0, addedPoint.getY(), 0.001);
        assertEquals(0.0, addedPoint.getTheta(), 0.001);
        assertFalse(addedPoint.isFox());
    }

    @Test
    public void testAddLandMark() {
        Mockito.when(mockLandmark.getLat()).thenReturn(90.0);
        Mockito.when(mockLandmark.getLon()).thenReturn(180.0);
        ImageIcon mockIcon = Mockito.mock(ImageIcon.class);
        Mockito.when(mockLandmark.getIcon()).thenReturn(mockIcon);

        huntDisplay.addLandMark(mockLandmark);

        Vector landmarks = huntDisplay.landmarks;
        assertEquals(1, landmarks.size());
        Landmark addedLandmark = (Landmark) landmarks.elementAt(0);
        assertEquals(1.0, addedLandmark.getLat(), 0.001);
        assertEquals(1.0, addedLandmark.getLon(), 0.001);
        assertEquals(mockIcon, addedLandmark.getIcon());
    }

    @Test
    public void testPaintComponent() {
        Graphics mockGraphics = Mockito.mock(Graphics.class);
        huntDisplay.paintComponent(mockGraphics);
        // No exception should be thrown, and we can verify interactions if needed
        Mockito.verify(mockGraphics, Mockito.atLeastOnce()).setColor(Mockito.any(Color.class));
    }
}