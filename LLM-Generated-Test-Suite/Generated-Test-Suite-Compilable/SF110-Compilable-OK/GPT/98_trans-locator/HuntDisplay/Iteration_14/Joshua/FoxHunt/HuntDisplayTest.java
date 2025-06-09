package Joshua.FoxHunt;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.awt.*;
import javax.swing.*;

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

        assertEquals(1, huntDisplay.points.size());
        TrigPoint addedPoint = (TrigPoint) huntDisplay.points.get(0);
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

        assertEquals(1, huntDisplay.landmarks.size());
        Landmark addedLandmark = (Landmark) huntDisplay.landmarks.get(0);
        assertEquals(1.0, addedLandmark.getLat(), 0.001);
        assertEquals(1.0, addedLandmark.getLon(), 0.001);
        assertEquals(mockIcon, addedLandmark.getIcon());
    }

    @Test
    public void testPaintComponent() {
        Graphics mockGraphics = Mockito.mock(Graphics.class);
        TrigPoint mockPoint = Mockito.mock(TrigPoint.class);
        Mockito.when(mockPoint.getX()).thenReturn(0.5);
        Mockito.when(mockPoint.getY()).thenReturn(0.5);
        Mockito.when(mockPoint.isFox()).thenReturn(true);

        huntDisplay.addPoint(mockPoint);

        Landmark mockLandmark = Mockito.mock(Landmark.class);
        Mockito.when(mockLandmark.getLat()).thenReturn(0.5);
        Mockito.when(mockLandmark.getLon()).thenReturn(0.5);
        ImageIcon mockIcon = Mockito.mock(ImageIcon.class);
        Image mockImage = Mockito.mock(Image.class);
        Mockito.when(mockIcon.getImage()).thenReturn(mockImage);
        Mockito.when(mockLandmark.getIcon()).thenReturn(mockIcon);

        huntDisplay.addLandMark(mockLandmark);

        huntDisplay.paintComponent(mockGraphics);

        Mockito.verify(mockGraphics, Mockito.atLeastOnce()).setColor(Color.GREEN);
        Mockito.verify(mockGraphics, Mockito.atLeastOnce()).fillOval(Mockito.anyInt(), Mockito.anyInt(), Mockito.eq(5), Mockito.eq(5));
        Mockito.verify(mockGraphics, Mockito.atLeastOnce()).drawImage(Mockito.eq(mockImage), Mockito.anyInt(), Mockito.anyInt(), Mockito.eq(10), Mockito.eq(10), Mockito.any(), Mockito.eq(huntDisplay));
    }
}