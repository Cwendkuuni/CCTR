package de.progra.charting;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.awt.*;
import de.progra.charting.model.ChartDataModel;
import de.progra.charting.render.AbstractChartRenderer;
import java.util.HashMap;

public class DefaultChartTest {

    private ChartDataModel mockModel;
    private DefaultChart chart;
    private Graphics2D mockGraphics;

    @Before
    public void setUp() {
        mockModel = Mockito.mock(ChartDataModel.class);
        mockGraphics = Mockito.mock(Graphics2D.class);
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
        chart.setChartRenderer(new HashMap<String, AbstractChartRenderer>());
        chart.render(mockGraphics);

        Mockito.verify(mockGraphics).setColor(Color.white);
        Mockito.verify(mockGraphics).fillRect(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.verify(mockGraphics).setColor(Color.black);
    }
}