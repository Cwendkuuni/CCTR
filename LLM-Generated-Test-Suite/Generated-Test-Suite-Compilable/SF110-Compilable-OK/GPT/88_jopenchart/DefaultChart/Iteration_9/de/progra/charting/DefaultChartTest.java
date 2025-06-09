package de.progra.charting;

import de.progra.charting.*;
import de.progra.charting.model.*;
import de.progra.charting.render.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
    public void testConstructorWithModelAndTitle() {
        String title = "Test Chart";
        chart = new DefaultChart(mockModel, title);

        assertNotNull(chart.getChartDataModel());
        assertNotNull(chart.getRowColorModel());
        assertNotNull(chart.getLegend());
        assertNotNull(chart.getTitle());
        assertEquals(title, chart.getTitle().getText());
    }

    @Test
    public void testConstructorWithModelTitleAndCoord() {
        String title = "Test Chart";
        chart = new DefaultChart(mockModel, title, DefaultChart.LINEAR_X_LINEAR_Y);

        assertNotNull(chart.getCoordSystem());
    }

    @Test
    public void testConstructorWithModelTitleCoordAndAxes() {
        String title = "Test Chart";
        String xAxis = "X Axis";
        String yAxis = "Y Axis";
        chart = new DefaultChart(mockModel, title, DefaultChart.LINEAR_X_LINEAR_Y, xAxis, yAxis);

        assertNotNull(chart.getCoordSystem());
        assertEquals(xAxis, chart.getCoordSystem().getXAxisUnit());
        assertEquals(yAxis, chart.getCoordSystem().getYAxisUnit());
    }

    @Test
    public void testRender() {
        chart = new DefaultChart(mockModel, "Test Chart");
        chart.setChartRenderer(new HashMap<>());

        chart.render(mockGraphics);

        verify(mockGraphics).setColor(Color.white);
        verify(mockGraphics).fillRect(0, 0, (int) chart.getBounds().getWidth(), (int) chart.getBounds().getHeight());
        verify(mockGraphics).setColor(Color.black);
    }

    @Test
    public void testGetPreferredSize() {
        chart = new DefaultChart(mockModel, "Test Chart");
        assertNull(chart.getPreferredSize());
    }
}