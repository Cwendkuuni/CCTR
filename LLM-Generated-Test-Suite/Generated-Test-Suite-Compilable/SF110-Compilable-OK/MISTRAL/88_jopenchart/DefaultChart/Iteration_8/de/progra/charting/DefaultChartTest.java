package de.progra.charting;

import de.progra.charting.model.*;
import de.progra.charting.render.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DefaultChartTest {

    private ChartDataModel mockModel;
    private DefaultChart defaultChart;

    @Before
    public void setUp() {
        mockModel = mock(ChartDataModel.class);
        defaultChart = new DefaultChart(mockModel, "Test Chart");
    }

    @Test
    public void testDefaultChartConstructor() {
        assertNotNull(defaultChart);
        assertEquals("Test Chart", defaultChart.getTitle().getText());
        assertNotNull(defaultChart.getRowColorModel());
        assertNotNull(defaultChart.getLegend());
    }

    @Test
    public void testDefaultChartConstructorWithCoord() {
        DefaultChart chartWithCoord = new DefaultChart(mockModel, "Test Chart", DefaultChart.LINEAR_X_LINEAR_Y);
        assertNotNull(chartWithCoord.getCoordSystem());
    }

    @Test
    public void testDefaultChartConstructorWithCoordAndAxes() {
        DefaultChart chartWithCoordAndAxes = new DefaultChart(mockModel, "Test Chart", DefaultChart.LINEAR_X_LINEAR_Y, "X-Axis", "Y-Axis");
        assertEquals("X-Axis", chartWithCoordAndAxes.getCoordSystem().getXAxisUnit());
        assertEquals("Y-Axis", chartWithCoordAndAxes.getCoordSystem().getYAxisUnit());
    }

    @Test
    public void testGetPreferredSize() {
        assertNull(defaultChart.getPreferredSize());
    }

    @Test
    public void testRender() {
        Graphics2D mockGraphics = mock(Graphics2D.class);
        Title mockTitle = mock(Title.class);
        Legend mockLegend = mock(Legend.class);
        CoordSystem mockCoordSystem = mock(CoordSystem.class);
        AbstractChartRenderer mockRenderer = mock(AbstractChartRenderer.class);

        defaultChart.setTitle(mockTitle);
        defaultChart.setLegend(mockLegend);
        defaultChart.setCoordSystem(mockCoordSystem);

        Map<String, AbstractChartRenderer> renderers = new HashMap<>();
        renderers.put("renderer", mockRenderer);
        defaultChart.setChartRenderer(renderers);

        when(mockTitle.getPreferredSize()).thenReturn(new Dimension(100, 50));
        when(mockLegend.getPreferredSize()).thenReturn(new Dimension(50, 100));
        when(defaultChart.getBounds()).thenReturn(new Rectangle(0, 0, 200, 200));

        defaultChart.render(mockGraphics);

        verify(mockGraphics).setColor(Color.white);
        verify(mockGraphics).fillRect(0, 0, 200, 200);
        verify(mockGraphics).setColor(Color.black);
        verify(mockTitle).render(mockGraphics);
        verify(mockLegend).render(mockGraphics);
        verify(mockCoordSystem).render(mockGraphics);
        verify(mockRenderer).render(mockGraphics);
    }
}