package de.progra.charting;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.awt.*;
import de.progra.charting.model.ChartDataModel;
import de.progra.charting.render.AbstractChartRenderer;
import java.util.HashMap;

public class DefaultChartTest {

    private ChartDataModel mockModel;
    private Graphics2D mockGraphics;
    private DefaultChart defaultChart;

    @Before
    public void setUp() {
        mockModel = Mockito.mock(ChartDataModel.class);
        mockGraphics = Mockito.mock(Graphics2D.class);
    }

    @Test
    public void testDefaultConstructor() {
        defaultChart = new DefaultChart(mockModel, "Test Title");
        assertNotNull(defaultChart);
        assertEquals("Test Title", defaultChart.getTitle().getText());
        assertNotNull(defaultChart.getRowColorModel());
        assertNotNull(defaultChart.getLegend());
    }

    @Test
    public void testConstructorWithCoordSystem() {
        defaultChart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        assertNotNull(defaultChart);
        assertNotNull(defaultChart.getCoordSystem());
    }

    @Test
    public void testConstructorWithCoordSystemAndAxes() {
        defaultChart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y, "X Axis", "Y Axis");
        assertNotNull(defaultChart);
        assertNotNull(defaultChart.getCoordSystem());
        assertEquals("X Axis", defaultChart.getCoordSystem().getXAxisUnit());
        assertEquals("Y Axis", defaultChart.getCoordSystem().getYAxisUnit());
    }

    @Test
    public void testRender() {
        defaultChart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        defaultChart.setChartRenderer(new HashMap<String, AbstractChartRenderer>());
        
        // Mocking the behavior of getBounds() to return a specific dimension
        Mockito.when(defaultChart.getBounds()).thenReturn(new Rectangle(0, 0, 800, 600));

        defaultChart.render(mockGraphics);

        // Verify that the graphics context was used to draw the chart
        Mockito.verify(mockGraphics).setColor(Color.white);
        Mockito.verify(mockGraphics).fillRect(0, 0, 800, 600);
        Mockito.verify(mockGraphics).setColor(Color.black);
    }

    @Test
    public void testGetPreferredSize() {
        defaultChart = new DefaultChart(mockModel, "Test Title");
        assertNull(defaultChart.getPreferredSize());
    }
}