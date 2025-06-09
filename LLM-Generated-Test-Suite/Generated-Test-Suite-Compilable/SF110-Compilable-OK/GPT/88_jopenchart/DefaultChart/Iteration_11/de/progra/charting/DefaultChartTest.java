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
    }

    @Test
    public void testConstructorWithModelTitleAndCoord() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        assertNotNull(chart.getCoordSystem());
    }

    @Test
    public void testConstructorWithModelTitleCoordAndAxes() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y, "X Axis", "Y Axis");
        assertEquals("X Axis", chart.getCoordSystem().getXAxisUnit());
        assertEquals("Y Axis", chart.getCoordSystem().getYAxisUnit());
    }

    @Test
    public void testRender() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        chart.setChartRenderer(new HashMap<String, AbstractChartRenderer>());

        // Mocking the behavior of getBounds
        Rectangle mockBounds = new Rectangle(0, 0, 100, 100);
        when(chart.getBounds()).thenReturn(mockBounds);

        chart.render(mockGraphics);

        // Verify that the graphics context is set up correctly
        verify(mockGraphics).setColor(Color.white);
        verify(mockGraphics).fillRect(0, 0, 100, 100);
        verify(mockGraphics).setColor(Color.black);
    }

    @Test
    public void testRenderWithTitleAndLegend() {
        chart = new DefaultChart(mockModel, "Test Title", DefaultChart.LINEAR_X_LINEAR_Y);
        chart.setChartRenderer(new HashMap<String, AbstractChartRenderer>());

        // Mocking the behavior of getBounds
        Rectangle mockBounds = new Rectangle(0, 0, 100, 100);
        when(chart.getBounds()).thenReturn(mockBounds);

        chart.render(mockGraphics);

        // Verify that the title and legend are rendered
        ArgumentCaptor<Rectangle> captor = ArgumentCaptor.forClass(Rectangle.class);
        verify(chart.getTitle()).setBounds(captor.capture());
        verify(chart.getLegend()).setBounds(captor.capture());
    }

    @Test
    public void testGetPreferredSize() {
        chart = new DefaultChart(mockModel, "Test Title");
        assertNull(chart.getPreferredSize());
    }
}