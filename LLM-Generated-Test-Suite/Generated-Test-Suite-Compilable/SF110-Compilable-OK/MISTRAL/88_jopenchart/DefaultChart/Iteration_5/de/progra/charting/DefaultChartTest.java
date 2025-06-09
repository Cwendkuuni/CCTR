package de.progra.charting;

import de.progra.charting.model.*;
import de.progra.charting.render.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.*;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultChartTest {

    @Mock
    private ChartDataModel mockModel;

    @Mock
    private Title mockTitle;

    @Mock
    private Legend mockLegend;

    @Mock
    private CoordSystem mockCoordSystem;

    @Mock
    private RowColorModel mockRowColorModel;

    @Mock
    private Graphics2D mockGraphics2D;

    @Mock
    private Collection<AbstractChartRenderer> mockRenderers;

    private DefaultChart defaultChart;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        defaultChart = new DefaultChart(mockModel, "Test Chart");
    }

    @Test
    public void testDefaultChartConstructor() {
        DefaultChart chart = new DefaultChart(mockModel, "Test Chart");
        assertNotNull(chart);
        assertEquals("Test Chart", chart.getTitle().getText());
    }

    @Test
    public void testDefaultChartConstructorWithCoord() {
        DefaultChart chart = new DefaultChart(mockModel, "Test Chart", DefaultChart.LINEAR_X_LINEAR_Y);
        assertNotNull(chart);
        assertEquals("Test Chart", chart.getTitle().getText());
        assertNotNull(chart.getCoordSystem());
    }

    @Test
    public void testDefaultChartConstructorWithCoordAndAxis() {
        DefaultChart chart = new DefaultChart(mockModel, "Test Chart", DefaultChart.LINEAR_X_LINEAR_Y, "X-Axis", "Y-Axis");
        assertNotNull(chart);
        assertEquals("Test Chart", chart.getTitle().getText());
        assertNotNull(chart.getCoordSystem());
        assertEquals("X-Axis", chart.getCoordSystem().getXAxisUnit());
        assertEquals("Y-Axis", chart.getCoordSystem().getYAxisUnit());
    }

    @Test
    public void testGetPreferredSize() {
        assertNull(defaultChart.getPreferredSize());
    }

    @Test
    public void testRender() {
        when(defaultChart.getBounds()).thenReturn(new Rectangle(0, 0, 800, 600));
        when(mockTitle.getPreferredSize()).thenReturn(new Dimension(100, 50));
        when(mockLegend.getPreferredSize()).thenReturn(new Dimension(200, 100));
        when(defaultChart.getChartRenderer().values()).thenReturn(mockRenderers);

        defaultChart.setTitle(mockTitle);
        defaultChart.setLegend(mockLegend);
        defaultChart.setCoordSystem(mockCoordSystem);

        defaultChart.render(mockGraphics2D);

        verify(mockGraphics2D).setColor(Color.white);
        verify(mockGraphics2D).fillRect(0, 0, 800, 600);
        verify(mockGraphics2D).setColor(Color.black);
        verify(mockTitle).render(mockGraphics2D);
        verify(mockLegend).render(mockGraphics2D);
        verify(mockCoordSystem).render(mockGraphics2D);
        verify(mockRenderers).forEach(renderer -> {
            renderer.setBounds(new Rectangle(0, 50, 600, 545));
            renderer.render(mockGraphics2D);
        });
    }
}