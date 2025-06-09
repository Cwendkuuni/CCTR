package de.progra.charting;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import de.progra.charting.*;
import de.progra.charting.model.ChartDataModel;
import de.progra.charting.render.AbstractChartRenderer;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

public class DefaultChartTest {

    private ChartDataModel mockModel;
    private Graphics2D mockGraphics;
    private DefaultChart chart;

    @Before
    public void setUp() {
        mockModel = mock(ChartDataModel.class);
        mockGraphics = mock(Graphics2D.class);
    }

    @Test
    public void testDefaultConstructor() {
        chart = new DefaultChart(mockModel, "Test Title");
        assertNotNull(chart);
        assertEquals("Test Title", chart.getTitle().getText());
        assertNotNull(chart.getRowColorModel());
        assertNotNull(chart.getLegend());
    }

    @Test
    public void testConstructorWithCoordSystem() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        assertNotNull(chart);
        assertNotNull(chart.getCoordSystem());
    }

    @Test
    public void testConstructorWithCoordSystemAndAxes() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y, "X Axis", "Y Axis");
        assertNotNull(chart);
        assertNotNull(chart.getCoordSystem());
        assertEquals("X Axis", chart.getCoordSystem().getXAxisUnit());
        assertEquals("Y Axis", chart.getCoordSystem().getYAxisUnit());
    }

    @Test
    public void testRender() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        chart.setChartRenderer(new HashMap<String, AbstractChartRenderer>());
        
        // Mocking the behavior of getBounds() to return a specific dimension
        when(chart.getBounds()).thenReturn(new Rectangle(0, 0, 800, 600));
        
        chart.render(mockGraphics);
        
        // Verify that the graphics context was used to draw
        verify(mockGraphics).setColor(Color.white);
        verify(mockGraphics).fillRect(0, 0, 800, 600);
        verify(mockGraphics).setColor(Color.black);
    }

    @Test
    public void testGetPreferredSize() {
        chart = new DefaultChart(mockModel, "Test Title");
        assertNull(chart.getPreferredSize());
    }
}