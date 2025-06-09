package net.virtualinfinity.atrobots.gui.renderers;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import net.virtualinfinity.atrobots.gui.renderers.RobotRenderer;
import net.virtualinfinity.atrobots.snapshots.RobotSnapshot;
import org.junit.Before;
import org.junit.Test;

public class RobotRendererTest {
    private RobotRenderer robotRenderer;
    private Graphics2D graphics2D;
    private RobotSnapshot robotSnapshot;
    private Set<Integer> selectedRobotIds;

    @Before
    public void setUp() {
        robotRenderer = new RobotRenderer();
        graphics2D = mock(Graphics2D.class);
        robotSnapshot = mock(RobotSnapshot.class);
        selectedRobotIds = new HashSet<>();
    }

    @Test
    public void testRenderDead() {
        robotRenderer.setRenderDead(false);
        when(robotSnapshot.isDead()).thenReturn(true);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, never()).fill(any());
    }

    @Test
    public void testRenderAlive() {
        robotRenderer.setRenderDead(true);
        when(robotSnapshot.isDead()).thenReturn(false);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, atLeastOnce()).fill(any());
    }

    @Test
    public void testRenderSelected() {
        when(robotSnapshot.getId()).thenReturn(1);
        selectedRobotIds.add(1);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, atLeastOnce()).fill(any());
    }

    @Test
    public void testIsRenderDead() {
        assertTrue(robotRenderer.isRenderDead());
        robotRenderer.setRenderDead(false);
        assertFalse(robotRenderer.isRenderDead());
    }

    @Test
    public void testIsShowStatusBars() {
        assertTrue(robotRenderer.isShowStatusBars());
        robotRenderer.setShowStatusBars(false);
        assertFalse(robotRenderer.isShowStatusBars());
    }

    @Test
    public void testIsShowName() {
        assertTrue(robotRenderer.isShowName());
        robotRenderer.setShowName(false);
        assertFalse(robotRenderer.isShowName());
    }

    @Test
    public void testIsFillShield() {
        assertTrue(robotRenderer.isFillShield());
        robotRenderer.setFillShield(false);
        assertFalse(robotRenderer.isFillShield());
    }
}