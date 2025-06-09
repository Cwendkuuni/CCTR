package org.apache.commons.math3.optim.nonlinear.scalar.noderiv;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.ZeroException;
import org.apache.commons.math3.optim.PointValuePair;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class AbstractSimplexTest {

    private static class TestSimplex extends AbstractSimplex {
        public TestSimplex(int n) {
            super(n);
        }

        public TestSimplex(int n, double sideLength) {
            super(n, sideLength);
        }

        public TestSimplex(double[] steps) {
            super(steps);
        }

        public TestSimplex(double[][] referenceSimplex) {
            super(referenceSimplex);
        }

        @Override
        public void iterate(MultivariateFunction function, Comparator<PointValuePair> comparator) {
            // Dummy implementation for testing
        }
    }

    private TestSimplex simplex;

    @Before
    public void setUp() {
        simplex = new TestSimplex(3, 1.0);
    }

    @Test(expected = NullArgumentException.class)
    public void testConstructorWithNullSteps() {
        new TestSimplex((double[]) null);
    }

    @Test(expected = ZeroException.class)
    public void testConstructorWithZeroLengthSteps() {
        new TestSimplex(new double[0]);
    }

    @Test(expected = ZeroException.class)
    public void testConstructorWithZeroStepValue() {
        new TestSimplex(new double[]{1.0, 0.0, 1.0});
    }

    @Test(expected = DimensionMismatchException.class)
    public void testConstructorWithMismatchedReferenceSimplex() {
        new TestSimplex(new double[][]{{1.0, 2.0}, {1.0, 2.0, 3.0}});
    }

    @Test(expected = OutOfRangeException.class)
    public void testGetPointOutOfRange() {
        simplex.build(new double[]{0.0, 0.0, 0.0});
        simplex.getPoint(4);
    }

    @Test
    public void testGetDimension() {
        assertEquals(3, simplex.getDimension());
    }

    @Test
    public void testBuild() {
        double[] startPoint = {1.0, 1.0, 1.0};
        simplex.build(startPoint);
        PointValuePair[] points = simplex.getPoints();
        assertEquals(4, points.length);
        assertArrayEquals(startPoint, points[0].getPointRef(), 1e-10);
    }

    @Test
    public void testEvaluate() {
        double[] startPoint = {1.0, 1.0, 1.0};
        simplex.build(startPoint);
        simplex.evaluate(point -> point[0] + point[1] + point[2], Comparator.comparingDouble(PointValuePair::getValue));
        PointValuePair[] points = simplex.getPoints();
        assertNotNull(points[0].getValue());
    }

    @Test
    public void testReplaceWorstPoint() {
        double[] startPoint = {1.0, 1.0, 1.0};
        simplex.build(startPoint);
        PointValuePair newPoint = new PointValuePair(new double[]{0.5, 0.5, 0.5}, 1.5);
        simplex.replaceWorstPoint(newPoint, Comparator.comparingDouble(PointValuePair::getValue));
        PointValuePair[] points = simplex.getPoints();
        assertEquals(newPoint, points[3]);
    }

    @Test
    public void testSetPoint() {
        double[] startPoint = {1.0, 1.0, 1.0};
        simplex.build(startPoint);
        PointValuePair newPoint = new PointValuePair(new double[]{0.5, 0.5, 0.5}, 1.5);
        simplex.setPoint(0, newPoint);
        assertEquals(newPoint, simplex.getPoint(0));
    }

    @Test(expected = DimensionMismatchException.class)
    public void testSetPointsWithDimensionMismatch() {
        double[] startPoint = {1.0, 1.0, 1.0};
        simplex.build(startPoint);
        simplex.setPoints(new PointValuePair[2]);
    }
}