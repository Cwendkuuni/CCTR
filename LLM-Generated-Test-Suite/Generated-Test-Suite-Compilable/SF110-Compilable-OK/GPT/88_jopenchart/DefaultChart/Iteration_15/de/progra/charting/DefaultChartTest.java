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
    public void testConstructorWithModelAndTitle() {
        chart = new DefaultChart(mockModel, "Test Title");
        assertNotNull(chart.getChartDataModel());
        assertNotNull(chart.getRowColorModel());
        assertNotNull(chart.getLegend());
        assertNotNull(chart.getTitle());
        assertNull(chart.getCoordSystem());
    }

    @Test
    public void testConstructorWithModelTitleAndCoordSystem() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        assertNotNull(chart.getCoordSystem());
    }

    @Test
    public void testConstructorWithModelTitleCoordSystemAndAxes() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y, "X Axis", "Y Axis");
        assertNotNull(chart.getCoordSystem());
        assertEquals("X Axis", chart.getCoordSystem().getXAxisUnit());
        assertEquals("Y Axis", chart.getCoordSystem().getYAxisUnit());
    }

    @Test
    public void testRenderWithTitleAndLegend() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        chart.setChartRenderer(new HashMap<String, AbstractChartRenderer>());

        Title mockTitle = mock(Title.class);
        Legend mockLegend = mock(Legend.class);
        CoordSystem mockCoordSystem = mock(CoordSystem.class);

        when(mockTitle.getPreferredSize()).thenReturn(new Dimension(100, 20));
        when(mockLegend.getPreferredSize()).thenReturn(new Dimension(50, 100));

        chart.setTitle(mockTitle);
        chart.setLegend(mockLegend);
        chart.setCoordSystem(mockCoordSystem);

        chart.render(mockGraphics);

        verify(mockGraphics).setColor(Color.white);
        verify(mockGraphics).fillRect(0, 0, 0, 0);
        verify(mockGraphics).setColor(Color.black);
        verify(mockTitle).render(mockGraphics);
        verify(mockLegend).render(mockGraphics);
        verify(mockCoordSystem).render(mockGraphics);
    }

    @Test
    public void testRenderWithRenderers() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        AbstractChartRenderer mockRenderer = mock(AbstractChartRenderer.class);
        HashMap<String, AbstractChartRenderer> renderers = new HashMap<>();
        renderers.put("renderer1", mockRenderer);
        chart.setChartRenderer(renderers);

        chart.render(mockGraphics);

        verify(mockRenderer).render(mockGraphics);
    }
}