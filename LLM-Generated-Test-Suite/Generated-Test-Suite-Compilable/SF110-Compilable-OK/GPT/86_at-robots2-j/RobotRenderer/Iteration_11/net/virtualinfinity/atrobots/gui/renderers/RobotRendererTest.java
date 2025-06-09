package net.virtualinfinity.atrobots.gui.renderers;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import net.virtualinfinity.atrobots.gui.renderers.RobotRenderer;
import net.virtualinfinity.atrobots.snapshots.RobotSnapshot;

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
    public void testRenderDeadRobotWhenRenderDeadIsFalse() {
        when(robotSnapshot.isDead()).thenReturn(true);
        robotRenderer.setRenderDead(false);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, never()).fill(any());
    }

    @Test
    public void testRenderAliveRobot() {
        when(robotSnapshot.isDead()).thenReturn(false);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, atLeastOnce()).fill(any());
    }

    @Test
    public void testRenderSelectedRobot() {
        when(robotSnapshot.getId()).thenReturn(1);
        selectedRobotIds.add(1);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, atLeastOnce()).fill(any());
    }

    @Test
    public void testIsRenderDead() {
        assertTrue(robotRenderer.isRenderDead());
    }

    @Test
    public void testSetRenderDead() {
        robotRenderer.setRenderDead(false);
        assertFalse(robotRenderer.isRenderDead());
    }

    @Test
    public void testIsShowStatusBars() {
        assertTrue(robotRenderer.isShowStatusBars());
    }

    @Test
    public void testSetShowStatusBars() {
        robotRenderer.setShowStatusBars(false);
        assertFalse(robotRenderer.isShowStatusBars());
    }

    @Test
    public void testIsShowName() {
        assertTrue(robotRenderer.isShowName());
    }

    @Test
    public void testSetShowName() {
        robotRenderer.setShowName(false);
        assertFalse(robotRenderer.isShowName());
    }

    @Test
    public void testIsFillShield() {
        assertTrue(robotRenderer.isFillShield());
    }

    @Test
    public void testSetFillShield() {
        robotRenderer.setFillShield(false);
        assertFalse(robotRenderer.isFillShield());
    }

    @Test
    public void testPaintBodyForDeadRobot() {
        when(robotSnapshot.isDead()).thenReturn(true);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        ArgumentCaptor<Stroke> strokeCaptor = ArgumentCaptor.forClass(Stroke.class);
        verify(graphics2D).setStroke(strokeCaptor.capture());
        assertTrue(strokeCaptor.getValue() instanceof BasicStroke);
    }

    @Test
    public void testPaintBodyForAliveRobot() {
        when(robotSnapshot.isDead()).thenReturn(false);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, atLeastOnce()).fill(any());
    }
}