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
    private DefaultChart defaultChart;

    @Before
    public void setUp() {
        mockModel = mock(ChartDataModel.class);
        mockGraphics = mock(Graphics2D.class);
    }

    @Test
    public void testConstructorWithModelAndTitle() {
        String title = "Test Chart";
        defaultChart = new DefaultChart(mockModel, title);

        assertNotNull(defaultChart.getChartDataModel());
        assertNotNull(defaultChart.getRowColorModel());
        assertNotNull(defaultChart.getLegend());
        assertNotNull(defaultChart.getTitle());
        assertEquals(title, defaultChart.getTitle().getText());
    }

    @Test
    public void testConstructorWithModelTitleAndCoord() {
        String title = "Test Chart";
        defaultChart = new DefaultChart(mockModel, title, DefaultChart.LINEAR_X_LINEAR_Y);

        assertNotNull(defaultChart.getCoordSystem());
    }

    @Test
    public void testConstructorWithModelTitleCoordAndAxes() {
        String title = "Test Chart";
        String xAxis = "X Axis";
        String yAxis = "Y Axis";
        defaultChart = new DefaultChart(mockModel, title, DefaultChart.LINEAR_X_LINEAR_Y, xAxis, yAxis);

        assertNotNull(defaultChart.getCoordSystem());
        assertEquals(xAxis, defaultChart.getCoordSystem().getXAxisUnit());
        assertEquals(yAxis, defaultChart.getCoordSystem().getYAxisUnit());
    }

    @Test
    public void testRender() {
        String title = "Test Chart";
        defaultChart = new DefaultChart(mockModel, title, DefaultChart.LINEAR_X_LINEAR_Y);
        defaultChart.setChartRenderer(new HashMap<String, AbstractChartRenderer>());

        // Mocking the behavior of getBounds() method
        when(defaultChart.getBounds()).thenReturn(new Rectangle(0, 0, 800, 600));

        defaultChart.render(mockGraphics);

        // Verify that the graphics context is used to draw the chart
        verify(mockGraphics).setColor(Color.white);
        verify(mockGraphics).fillRect(0, 0, 800, 600);
        verify(mockGraphics).setColor(Color.black);
    }

    @Test
    public void testGetPreferredSize() {
        defaultChart = new DefaultChart(mockModel, "Test Chart");
        assertNull(defaultChart.getPreferredSize());
    }
}