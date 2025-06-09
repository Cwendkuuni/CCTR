package org.apache.commons.math3.ode.nonstiff;

import org.apache.commons.math3.ode.ExpandableStatefulODE;
import org.apache.commons.math3.ode.nonstiff.EmbeddedRungeKuttaIntegrator;
import org.apache.commons.math3.ode.nonstiff.RungeKuttaStepInterpolator;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NoBracketingException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EmbeddedRungeKuttaIntegratorTest {

    private EmbeddedRungeKuttaIntegrator integrator;
    private ExpandableStatefulODE equations;
    private RungeKuttaStepInterpolator interpolator;

    @Before
    public void setUp() {
        equations = mock(ExpandableStatefulODE.class);
        interpolator = mock(RungeKuttaStepInterpolator.class);
        integrator = new EmbeddedRungeKuttaIntegrator("TestIntegrator", true, new double[]{0.5}, new double[][]{{0.5}}, new double[]{1.0}, interpolator, 0.01, 1.0, 1e-10, 1e-10) {
            @Override
            public int getOrder() {
                return 1;
            }

            @Override
            protected double estimateError(double[][] p0, double[] p1, double[] p2, double p3) {
                return 0.5;
            }
        };
    }

    @Test
    public void testGetSafety() {
        assertEquals(0.9, integrator.getSafety(), 0.0);
    }

    @Test
    public void testSetSafety() {
        integrator.setSafety(0.8);
        assertEquals(0.8, integrator.getSafety(), 0.0);
    }

    @Test
    public void testGetMinReduction() {
        assertEquals(0.2, integrator.getMinReduction(), 0.0);
    }

    @Test
    public void testSetMinReduction() {
        integrator.setMinReduction(0.1);
        assertEquals(0.1, integrator.getMinReduction(), 0.0);
    }

    @Test
    public void testGetMaxGrowth() {
        assertEquals(10.0, integrator.getMaxGrowth(), 0.0);
    }

    @Test
    public void testSetMaxGrowth() {
        integrator.setMaxGrowth(5.0);
        assertEquals(5.0, integrator.getMaxGrowth(), 0.0);
    }

    @Test
    public void testIntegrate() throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException {
        when(equations.getTime()).thenReturn(0.0);
        when(equations.getCompleteState()).thenReturn(new double[]{1.0});
        when(equations.getPrimaryMapper()).thenReturn(null);
        when(equations.getSecondaryMappers()).thenReturn(null);

        integrator.integrate(equations, 1.0);

        verify(equations, times(1)).setTime(anyDouble());
        verify(equations, times(1)).setCompleteState(any(double[].class));
    }

    @Test
    public void testEstimateError() {
        double[][] yDotK = new double[][]{{1.0}, {2.0}};
        double[] y2 = new double[]{1.0};
        double[] yTmp = new double[]{2.0};
        double stepSize = 1.0;

        double error = integrator.estimateError(yDotK, y2, yTmp, stepSize);

        assertEquals(0.5, error, 0.0);
    }
}