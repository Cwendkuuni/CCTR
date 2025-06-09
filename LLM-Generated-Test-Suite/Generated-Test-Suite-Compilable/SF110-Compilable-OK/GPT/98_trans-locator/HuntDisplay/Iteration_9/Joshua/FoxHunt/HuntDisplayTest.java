package Joshua.FoxHunt;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.*;
import javax.swing.*;

public class HuntDisplayTest {

    private HuntDisplay huntDisplay;

    @Before
    public void setUp() {
        huntDisplay = new HuntDisplay();
    }

    @Test
    public void testConstructor() {
        assertNotNull(huntDisplay);
        assertEquals(new Dimension(50, 50), huntDisplay.getMinimumSize());
        assertEquals(new Dimension(200, 200), huntDisplay.getPreferredSize());
        assertEquals(Color.WHITE, huntDisplay.getBackground());
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
        TrigPoint point = new TrigPoint(45.0, 90.0, 0.0, false);
        huntDisplay.addPoint(point);
        assertEquals(1, huntDisplay.points.size());
        TrigPoint addedPoint = (TrigPoint) huntDisplay.points.elementAt(0);
        assertEquals(0.5, addedPoint.getX(), 0.01);
        assertEquals(0.5, addedPoint.getY(), 0.01);
        assertEquals(0.0, addedPoint.getTheta(), 0.01);
        assertFalse(addedPoint.isFox());
    }

    @Test
    public void testAddLandMark() {
        ImageIcon icon = new ImageIcon();
        Landmark landmark = new Landmark(45.0, 90.0, icon);
        huntDisplay.addLandMark(landmark);
        assertEquals(1, huntDisplay.landmarks.size());
        Landmark addedLandmark = (Landmark) huntDisplay.landmarks.elementAt(0);
        assertEquals(0.5, addedLandmark.getLat(), 0.01);
        assertEquals(0.5, addedLandmark.getLon(), 0.01);
        assertEquals(icon, addedLandmark.getIcon());
    }

    // Note: Testing paintComponent would require a more complex setup, possibly involving
    // a mocking framework or a visual testing tool to verify graphical output.
}