package de.progra.charting;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import de.progra.charting.*;
import de.progra.charting.model.*;
import de.progra.charting.render.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.*;
import java.util.*;

public class DefaultChartTest {

    private ChartDataModel mockModel;
    private DefaultChart chart;
    private Graphics2D mockGraphics;

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
    public void testGetPreferredSize() {
        chart = new DefaultChart(mockModel, "Test Title");
        assertNull(chart.getPreferredSize());
    }

    @Test
    public void testRender() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        chart.render(mockGraphics);

        verify(mockGraphics).setColor(Color.white);
        verify(mockGraphics).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
        verify(mockGraphics).setColor(Color.black);
    }

    @Test
    public void testRenderWithTitleAndLegend() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        Title mockTitle = mock(Title.class);
        Legend mockLegend = mock(Legend.class);
        CoordSystem mockCoordSystem = mock(CoordSystem.class);

        chart.setTitle(mockTitle);
        chart.setLegend(mockLegend);
        chart.setCoordSystem(mockCoordSystem);

        chart.render(mockGraphics);

        verify(mockTitle).render(mockGraphics);
        verify(mockLegend).render(mockGraphics);
        verify(mockCoordSystem).render(mockGraphics);
    }
}