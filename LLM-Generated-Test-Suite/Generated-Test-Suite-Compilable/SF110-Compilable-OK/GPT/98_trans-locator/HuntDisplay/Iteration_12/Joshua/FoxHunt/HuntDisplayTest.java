package Joshua.FoxHunt;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.*;
import java.util.Vector;
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
        TrigPoint point = new TrigPoint(90.0, 180.0, 0.0, false);
        huntDisplay.addPoint(point);
        
        Vector points = huntDisplay.points;
        assertEquals(1, points.size());
        
        TrigPoint addedPoint = (TrigPoint) points.get(0);
        assertEquals(1.0, addedPoint.getY(), 0.001);
        assertEquals(1.0, addedPoint.getX(), 0.001);
        assertEquals(0.0, addedPoint.getTheta(), 0.001);
        assertFalse(addedPoint.isFox());
    }

    @Test
    public void testAddLandMark() {
        ImageIcon icon = new ImageIcon();
        Landmark landmark = new Landmark(90.0, 180.0, icon);
        huntDisplay.addLandMark(landmark);
        
        Vector landmarks = huntDisplay.landmarks;
        assertEquals(1, landmarks.size());
        
        Landmark addedLandmark = (Landmark) landmarks.get(0);
        assertEquals(1.0, addedLandmark.getLat(), 0.001);
        assertEquals(1.0, addedLandmark.getLon(), 0.001);
        assertEquals(icon, addedLandmark.getIcon());
    }
}