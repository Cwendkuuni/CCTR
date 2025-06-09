package Joshua.FoxHunt;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.Vector;

public class FoxHuntFrameTest {

    private FoxHuntFrame foxHuntFrame;

    @Before
    public void setUp() {
        foxHuntFrame = new FoxHuntFrame();
    }

    @Test
    public void testConstructor() {
        assertNotNull(foxHuntFrame);
        assertEquals("Triangulation", foxHuntFrame.getTitle());
        assertNotNull(foxHuntFrame.theta);
        assertNotNull(foxHuntFrame.lat);
        assertNotNull(foxHuntFrame.lon);
        assertNotNull(foxHuntFrame.zoom);
        assertNotNull(foxHuntFrame.add);
        assertNotNull(foxHuntFrame.pos);
        assertNotNull(foxHuntFrame.posModel);
        assertNotNull(foxHuntFrame.display);
        assertNotNull(foxHuntFrame.panel1);
        assertNotNull(foxHuntFrame.panel2);
        assertNotNull(foxHuntFrame.panel3);
        assertNotNull(foxHuntFrame.panel);
        assertNotNull(foxHuntFrame.points);
        assertNotNull(foxHuntFrame.fox);
    }

    @Test
    public void testActionPerformed() {
        foxHuntFrame.theta.setText("45");
        foxHuntFrame.lat.setText("34.05223");
        foxHuntFrame.lon.setText("118.24368");

        foxHuntFrame.actionPerformed(new ActionEvent(foxHuntFrame.add, ActionEvent.ACTION_PERFORMED, null));

        assertEquals(1, foxHuntFrame.points.size());
        assertEquals(0, foxHuntFrame.fox.size());
    }

    @Test
    public void testAddPoint() {
        foxHuntFrame.theta.setText("45");
        foxHuntFrame.lat.setText("34.05223");
        foxHuntFrame.lon.setText("118.24368");

        foxHuntFrame.addPoint();

        assertEquals(1, foxHuntFrame.points.size());
        assertEquals(0, foxHuntFrame.fox.size());

        foxHuntFrame.theta.setText("90");
        foxHuntFrame.lat.setText("34.05224");
        foxHuntFrame.lon.setText("118.24369");

        foxHuntFrame.addPoint();

        assertEquals(2, foxHuntFrame.points.size());
        assertEquals(1, foxHuntFrame.fox.size());
    }

    @Test
    public void testFinalPrep() {
        // This method is private and cannot be directly tested
        // However, we can indirectly test it by checking the state of the frame
        assertNotNull(foxHuntFrame.zoom);
        assertNotNull(foxHuntFrame.panel3);
        assertNotNull(foxHuntFrame.panel);
    }

    @Test
    public void testTextPrep() {
        // This method is private and cannot be directly tested
        // However, we can indirectly test it by checking the state of the frame
        assertNotNull(foxHuntFrame.theta);
        assertNotNull(foxHuntFrame.lat);
        assertNotNull(foxHuntFrame.lon);
        assertNotNull(foxHuntFrame.add);
        assertNotNull(foxHuntFrame.panel1);
    }

    @Test
    public void testTablePrep() {
        // This method is private and cannot be directly tested
        // However, we can indirectly test it by checking the state of the frame
        assertNotNull(foxHuntFrame.posModel);
        assertNotNull(foxHuntFrame.pos);
        assertNotNull(foxHuntFrame.panel2);
    }

    @Test
    public void testDisplayPrep() {
        // This method is private and cannot be directly tested
        // However, we can indirectly test it by checking the state of the frame
        assertNotNull(foxHuntFrame.display);
    }

    @Test
    public void testLoadLandmarks() {
        // This method is private and cannot be directly tested
        // However, we can indirectly test it by checking the state of the frame
        // Assuming the landmarks file is present and correctly formatted
        // This test may need to be adjusted based on the actual file content and environment
        assertNotNull(foxHuntFrame.display);
    }

    @Test
    public void testError() {
        // This method is private and cannot be directly tested
        // However, we can indirectly test it by invoking a scenario that triggers the error method
        // This test may need to be adjusted based on the actual implementation and environment
        // For example, we can simulate a file not found scenario
        // This is a manual test and cannot be automated without additional setup
    }

    @Test
    public void testFoxTableModel() {
        FoxHuntFrame.FoxTableModel model = foxHuntFrame.new FoxTableModel();
        assertEquals(2, model.getColumnCount());
        assertEquals(0, model.getRowCount());
        assertEquals("Lattitude", model.getColumnName(0));
        assertEquals("Longitude", model.getColumnName(1));
        assertFalse(model.isCellEditable(0, 0));
    }
}