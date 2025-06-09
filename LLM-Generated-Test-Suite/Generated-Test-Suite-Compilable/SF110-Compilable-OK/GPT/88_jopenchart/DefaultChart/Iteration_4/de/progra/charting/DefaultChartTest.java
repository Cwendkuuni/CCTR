package de.progra.charting;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import de.progra.charting.DefaultChart;
import de.progra.charting.model.ChartDataModel;
import de.progra.charting.render.AbstractChartRenderer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DefaultChartTest {

    @Mock
    private ChartDataModel mockModel;
    @Mock
    private Graphics2D mockGraphics;
    @Mock
    private AbstractChartRenderer mockRenderer;

    private DefaultChart defaultChart;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
        String xAxis = "X-Axis";
        String yAxis = "Y-Axis";
        defaultChart = new DefaultChart(mockModel, title, DefaultChart.LINEAR_X_LINEAR_Y, xAxis, yAxis);

        assertNotNull(defaultChart.getCoordSystem());
        assertEquals(xAxis, defaultChart.getCoordSystem().getXAxisUnit());
        assertEquals(yAxis, defaultChart.getCoordSystem().getYAxisUnit());
    }

    @Test
    public void testGetPreferredSize() {
        defaultChart = new DefaultChart(mockModel, "Test Chart");
        assertNull(defaultChart.getPreferredSize());
    }

    @Test
    public void testRender() {
        defaultChart = new DefaultChart(mockModel, "Test Chart");
        Map<String, AbstractChartRenderer> rendererMap = new HashMap<>();
        rendererMap.put("mockRenderer", mockRenderer);
        defaultChart.setChartRenderer(rendererMap);

        when(mockGraphics.getColor()).thenReturn(Color.white);
        when(mockGraphics.getClipBounds()).thenReturn(new Rectangle(0, 0, 100, 100));

        defaultChart.render(mockGraphics);

        verify(mockGraphics).setColor(Color.white);
        verify(mockGraphics).fillRect(0, 0, 100, 100);
        verify(mockGraphics).setColor(Color.black);
        verify(mockRenderer).render(mockGraphics);
    }
}