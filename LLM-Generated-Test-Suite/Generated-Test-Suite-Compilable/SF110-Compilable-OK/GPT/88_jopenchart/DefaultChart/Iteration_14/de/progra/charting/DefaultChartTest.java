package de.progra.charting;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import de.progra.charting.*;
import de.progra.charting.model.ChartDataModel;
import de.progra.charting.render.AbstractChartRenderer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

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
    public void testGetPreferredSize() {
        chart = new DefaultChart(mockModel, "Test Chart");
        assertNull(chart.getPreferredSize());
    }

    @Test
    public void testRender() {
        chart = new DefaultChart(mockModel, "Test Chart");
        chart.setChartRenderer(new HashMap<>());

        chart.render(mockGraphics);

        ArgumentCaptor<Rectangle> rectangleCaptor = ArgumentCaptor.forClass(Rectangle.class);
        verify(mockGraphics).fillRect(0, 0, (int) chart.getBounds().getWidth(), (int) chart.getBounds().getHeight());
        verify(mockGraphics).setColor(Color.white);
        verify(mockGraphics).setColor(Color.black);
    }

    @Test
    public void testRenderWithTitleAndLegend() {
        String title = "Test Chart";
        chart = new DefaultChart(mockModel, title);
        chart.setChartRenderer(new HashMap<>());

        chart.render(mockGraphics);

        assertNotNull(chart.getTitle());
        assertNotNull(chart.getLegend());
    }

    @Test
    public void testRenderWithCoordSystem() {
        chart = new DefaultChart(mockModel, "Test Chart", DefaultChart.LINEAR_X_LINEAR_Y);
        chart.setChartRenderer(new HashMap<>());

        chart.render(mockGraphics);

        assertNotNull(chart.getCoordSystem());
    }

    @Test
    public void testRenderWithRenderers() {
        chart = new DefaultChart(mockModel, "Test Chart");
        AbstractChartRenderer mockRenderer = mock(AbstractChartRenderer.class);
        HashMap<String, AbstractChartRenderer> renderers = new HashMap<>();
        renderers.put("mockRenderer", mockRenderer);
        chart.setChartRenderer(renderers);

        chart.render(mockGraphics);

        verify(mockRenderer).render(mockGraphics);
    }
}