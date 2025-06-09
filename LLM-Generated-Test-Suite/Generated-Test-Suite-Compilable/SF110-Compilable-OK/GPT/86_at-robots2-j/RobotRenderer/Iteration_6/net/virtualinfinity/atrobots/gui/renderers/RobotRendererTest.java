package net.virtualinfinity.atrobots.gui.renderers;

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
    public void testRenderDeadRobotWhenRenderDeadIsFalse() {
        when(robotSnapshot.isDead()).thenReturn(true);
        robotRenderer.setRenderDead(false);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verifyNoInteractions(graphics2D);
    }

    @Test
    public void testRenderAliveRobot() {
        when(robotSnapshot.isDead()).thenReturn(false);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, atLeastOnce()).fill(any(Shape.class));
    }

    @Test
    public void testRenderWithSelectedRobot() {
        when(robotSnapshot.isDead()).thenReturn(false);
        when(robotSnapshot.getId()).thenReturn(1);
        selectedRobotIds.add(1);

        robotRenderer.render(graphics2D, robotSnapshot, selectedRobotIds);

        verify(graphics2D, atLeastOnce()).fill(any(Shape.class));
    }

    @Test
    public void testSetAndGetRenderDead() {
        robotRenderer.setRenderDead(false);
        assert !robotRenderer.isRenderDead();

        robotRenderer.setRenderDead(true);
        assert robotRenderer.isRenderDead();
    }

    @Test
    public void testSetAndGetShowStatusBars() {
        robotRenderer.setShowStatusBars(false);
        assert !robotRenderer.isShowStatusBars();

        robotRenderer.setShowStatusBars(true);
        assert robotRenderer.isShowStatusBars();
    }

    @Test
    public void testSetAndGetShowName() {
        robotRenderer.setShowName(false);
        assert !robotRenderer.isShowName();

        robotRenderer.setShowName(true);
        assert robotRenderer.isShowName();
    }

    @Test
    public void testSetAndGetFillShield() {
        robotRenderer.setFillShield(false);
        assert !robotRenderer.isFillShield();

        robotRenderer.setFillShield(true);
        assert robotRenderer.isFillShield();
    }
}