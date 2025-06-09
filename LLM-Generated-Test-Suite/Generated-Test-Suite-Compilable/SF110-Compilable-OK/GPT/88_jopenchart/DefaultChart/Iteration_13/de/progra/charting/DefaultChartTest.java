package de.progra.charting;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import de.progra.charting.model.ChartDataModel;
import de.progra.charting.render.AbstractChartRenderer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
        chart = new DefaultChart(mockModel, "Test Title");

        assertNotNull(chart.getChartDataModel());
        assertNotNull(chart.getRowColorModel());
        assertNotNull(chart.getLegend());
        assertNotNull(chart.getTitle());
        assertEquals("Test Title", chart.getTitle().getText());
    }

    @Test
    public void testConstructorWithModelTitleAndCoordSystem() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);

        assertNotNull(chart.getCoordSystem());
    }

    @Test
    public void testConstructorWithModelTitleCoordSystemAndAxes() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y, "X-Axis", "Y-Axis");

        assertNotNull(chart.getCoordSystem());
        assertEquals("X-Axis", chart.getCoordSystem().getXAxisUnit());
        assertEquals("Y-Axis", chart.getCoordSystem().getYAxisUnit());
    }

    @Test
    public void testRender() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        chart.setChartRenderer(new HashMap<String, AbstractChartRenderer>());

        chart.render(mockGraphics);

        ArgumentCaptor<Rectangle> captor = ArgumentCaptor.forClass(Rectangle.class);
        verify(mockGraphics, atLeastOnce()).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
        verify(mockGraphics, atLeastOnce()).setColor(any(Color.class));
    }

    @Test
    public void testRenderWithTitleAndLegend() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        chart.setChartRenderer(new HashMap<String, AbstractChartRenderer>());

        chart.render(mockGraphics);

        assertNotNull(chart.getTitle());
        assertNotNull(chart.getLegend());
    }

    @Test
    public void testRenderWithChartRenderer() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);

        Map<String, AbstractChartRenderer> renderers = new HashMap<>();
        AbstractChartRenderer mockRenderer = mock(AbstractChartRenderer.class);
        renderers.put("mockRenderer", mockRenderer);
        chart.setChartRenderer(renderers);

        chart.render(mockGraphics);

        verify(mockRenderer, atLeastOnce()).render(mockGraphics);
    }
}