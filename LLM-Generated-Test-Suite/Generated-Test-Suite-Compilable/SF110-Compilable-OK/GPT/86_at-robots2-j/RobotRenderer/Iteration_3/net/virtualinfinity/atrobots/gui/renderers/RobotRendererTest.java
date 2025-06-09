package net.virtualinfinity.atrobots.gui.renderers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

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
    public void testDefaultSettings() {
        assertTrue(robotRenderer.isRenderDead());
        assertTrue(robotRenderer.isShowStatusBars());
        assertTrue(robotRenderer.isShowName());
        assertTrue(robotRenderer.isFillShield());
    }

    @Test
    public void testSetRenderDead() {
        robotRenderer.setRenderDead(false);
        assertFalse(robotRenderer.isRenderDead());
    }

    @Test
    public void testSetShowStatusBars() {
        robotRenderer.setShowStatusBars(false);
        assertFalse(robotRenderer.isShowStatusBars());
    }

    @Test
    public void testSetShowName() {
        robotRenderer.setShowName(false);
        assertFalse(robotRenderer.isShowName());
    }

    @Test
    public void testSetFillShield() {
        robotRenderer.setFillShield(false);
        assertFalse(robotRenderer.isFillShield());
    }

    @Test
    public void testRenderDeadRobotWhenRenderDeadIsFalse() {
        when(robotSnapshot.isDead()).thenReturn(true);
        robotRenderer.setRenderDead(false);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(robotSnapshot, never()).getId();
        verify(graphics2D, never()).fill(any());
    }

    @Test
    public void testRenderAliveRobot() {
        when(robotSnapshot.isDead()).thenReturn(false);
        when(robotSnapshot.getId()).thenReturn(1);
        selectedRobotIds.add(1);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, atLeastOnce()).fill(any());
        verify(graphics2D, atLeastOnce()).draw(any());
    }

    @Test
    public void testRenderSelectedRobot() {
        when(robotSnapshot.isDead()).thenReturn(false);
        when(robotSnapshot.getId()).thenReturn(1);
        selectedRobotIds.add(1);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, atLeastOnce()).setPaint(any(Color.class));
    }

    @Test
    public void testRenderUnselectedRobot() {
        when(robotSnapshot.isDead()).thenReturn(false);
        when(robotSnapshot.getId()).thenReturn(1);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, never()).setPaint(new Color(1.0f, 1.0f, 0.0f, 0.25f));
    }
}