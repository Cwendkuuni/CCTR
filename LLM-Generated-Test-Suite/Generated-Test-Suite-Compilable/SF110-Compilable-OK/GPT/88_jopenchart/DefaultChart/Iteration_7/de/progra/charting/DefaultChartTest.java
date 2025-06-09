package de.progra.charting;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import de.progra.charting.DefaultChart;
import de.progra.charting.model.ChartDataModel;
import de.progra.charting.render.AbstractChartRenderer;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

public class DefaultChartTest {

    private ChartDataModel mockModel;
    private Graphics2D mockGraphics;
    private AbstractChartRenderer mockRenderer;

    @Before
    public void setUp() {
        mockModel = mock(ChartDataModel.class);
        mockGraphics = mock(Graphics2D.class);
        mockRenderer = mock(AbstractChartRenderer.class);
    }

    @Test
    public void testConstructorWithModelAndTitle() {
        DefaultChart chart = new DefaultChart(mockModel, "Test Title");
        assertNotNull(chart);
        assertEquals("Test Title", chart.getTitle().getText());
        assertNotNull(chart.getRowColorModel());
        assertNotNull(chart.getLegend());
    }

    @Test
    public void testConstructorWithModelTitleAndCoord() {
        DefaultChart chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        assertNotNull(chart);
        assertNotNull(chart.getCoordSystem());
    }

    @Test
    public void testConstructorWithModelTitleCoordAndAxes() {
        DefaultChart chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y, "X Axis", "Y Axis");
        assertNotNull(chart);
        assertEquals("X Axis", chart.getCoordSystem().getXAxisUnit());
        assertEquals("Y Axis", chart.getCoordSystem().getYAxisUnit());
    }

    @Test
    public void testRender() {
        DefaultChart chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        chart.setChartRenderer(new HashMap<String, AbstractChartRenderer>() {{
            put("mockRenderer", mockRenderer);
        }});

        chart.render(mockGraphics);

        verify(mockGraphics).setColor(Color.white);
        verify(mockGraphics).fillRect(0, 0, (int) chart.getBounds().getWidth(), (int) chart.getBounds().getHeight());
        verify(mockGraphics).setColor(Color.black);
        verify(mockRenderer).render(mockGraphics);
    }
}