package net.virtualinfinity.atrobots.gui.renderers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
    public void testRenderDeadRobotWhenRenderDeadIsFalse() {
        when(robotSnapshot.isDead()).thenReturn(true);
        robotRenderer.setRenderDead(false);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, never()).fill(any(Shape.class));
    }

    @Test
    public void testRenderAliveRobot() {
        when(robotSnapshot.isDead()).thenReturn(false);
        when(robotSnapshot.getId()).thenReturn(1);
        selectedRobotIds.add(1);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, atLeastOnce()).fill(any(Shape.class));
        verify(graphics2D, atLeastOnce()).draw(any(Shape.class));
    }

    @Test
    public void testRenderWithShowStatusBars() {
        when(robotSnapshot.isDead()).thenReturn(false);
        robotRenderer.setShowStatusBars(true);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, atLeastOnce()).fill(any(Shape.class));
    }

    @Test
    public void testRenderWithoutShowStatusBars() {
        when(robotSnapshot.isDead()).thenReturn(false);
        robotRenderer.setShowStatusBars(false);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, never()).fill(any(Rectangle.class));
    }

    @Test
    public void testRenderWithShowName() {
        when(robotSnapshot.isDead()).thenReturn(false);
        when(robotSnapshot.getName()).thenReturn("TestRobot");
        robotRenderer.setShowName(true);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, atLeastOnce()).drawString(eq("TestRobot"), anyFloat(), anyFloat());
    }

    @Test
    public void testRenderWithoutShowName() {
        when(robotSnapshot.isDead()).thenReturn(false);
        robotRenderer.setShowName(false);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, never()).drawString(anyString(), anyFloat(), anyFloat());
    }

    @Test
    public void testRenderWithFillShield() {
        when(robotSnapshot.isDead()).thenReturn(false);
        when(robotSnapshot.isActiveShield()).thenReturn(true);
        robotRenderer.setFillShield(true);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, atLeastOnce()).fill(any(Shape.class));
    }

    @Test
    public void testRenderWithoutFillShield() {
        when(robotSnapshot.isDead()).thenReturn(false);
        when(robotSnapshot.isActiveShield()).thenReturn(true);
        robotRenderer.setFillShield(false);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, atLeastOnce()).draw(any(Shape.class));
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