package Joshua.FoxHunt;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.*;
import javax.swing.*;
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
        TrigPoint point = new TrigPoint(45.0, 90.0, 0.0, false);
        huntDisplay.addPoint(point);

        Vector points = huntDisplay.points;
        assertEquals(1, points.size());

        TrigPoint addedPoint = (TrigPoint) points.elementAt(0);
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

        Vector landmarks = huntDisplay.landmarks;
        assertEquals(1, landmarks.size());

        Landmark addedLandmark = (Landmark) landmarks.elementAt(0);
        assertEquals(0.5, addedLandmark.getLat(), 0.01);
        assertEquals(0.5, addedLandmark.getLon(), 0.01);
        assertEquals(icon, addedLandmark.getIcon());
    }
}