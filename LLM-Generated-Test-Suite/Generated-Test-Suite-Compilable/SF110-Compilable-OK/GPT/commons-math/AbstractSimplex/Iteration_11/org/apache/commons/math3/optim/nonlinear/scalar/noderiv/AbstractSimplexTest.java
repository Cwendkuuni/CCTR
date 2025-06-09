package org.apache.commons.math3.optim.nonlinear.scalar.noderiv;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
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
            // Simple implementation for testing
        }
    }

    private TestSimplex simplex;

    @Before
    public void setUp() {
        simplex = new TestSimplex(2, 1.0);
    }

    @Test(expected = NullArgumentException.class)
    public void testConstructorWithNullSteps() {
        new TestSimplex((double[]) null);
    }

    @Test(expected = ZeroException.class)
    public void testConstructorWithZeroLengthSteps() {
        new TestSimplex(new double[]{});
    }

    @Test(expected = ZeroException.class)
    public void testConstructorWithZeroStepValue() {
        new TestSimplex(new double[]{1.0, 0.0});
    }

    @Test(expected = NotStrictlyPositiveException.class)
    public void testConstructorWithEmptyReferenceSimplex() {
        new TestSimplex(new double[][]{});
    }

    @Test(expected = DimensionMismatchException.class)
    public void testConstructorWithMismatchedReferenceSimplex() {
        new TestSimplex(new double[][]{{1.0, 2.0}, {1.0}});
    }

    @Test
    public void testGetDimension() {
        assertEquals(2, simplex.getDimension());
    }

    @Test
    public void testBuild() {
        double[] startPoint = {0.0, 0.0};
        simplex.build(startPoint);
        PointValuePair[] points = simplex.getPoints();
        assertEquals(3, points.length);
        assertArrayEquals(new double[]{0.0, 0.0}, points[0].getPointRef(), 1e-10);
    }

    @Test(expected = DimensionMismatchException.class)
    public void testBuildWithMismatchedStartPoint() {
        simplex.build(new double[]{0.0});
    }

    @Test
    public void testEvaluate() {
        double[] startPoint = {0.0, 0.0};
        simplex.build(startPoint);
        MultivariateFunction function = point -> point[0] + point[1];
        Comparator<PointValuePair> comparator = Comparator.comparingDouble(PointValuePair::getValue);
        simplex.evaluate(function, comparator);
        PointValuePair[] points = simplex.getPoints();
        assertEquals(0.0, points[0].getValue(), 1e-10);
    }

    @Test
    public void testReplaceWorstPoint() {
        double[] startPoint = {0.0, 0.0};
        simplex.build(startPoint);
        Comparator<PointValuePair> comparator = Comparator.comparingDouble(PointValuePair::getValue);
        PointValuePair newPoint = new PointValuePair(new double[]{1.0, 1.0}, 2.0);
        simplex.replaceWorstPoint(newPoint, comparator);
        PointValuePair[] points = simplex.getPoints();
        assertEquals(newPoint, points[2]);
    }

    @Test
    public void testGetPoint() {
        double[] startPoint = {0.0, 0.0};
        simplex.build(startPoint);
        PointValuePair point = simplex.getPoint(0);
        assertArrayEquals(new double[]{0.0, 0.0}, point.getPointRef(), 1e-10);
    }

    @Test(expected = OutOfRangeException.class)
    public void testGetPointOutOfRange() {
        simplex.getPoint(3);
    }

    @Test
    public void testSetPoint() {
        double[] startPoint = {0.0, 0.0};
        simplex.build(startPoint);
        PointValuePair newPoint = new PointValuePair(new double[]{1.0, 1.0}, 2.0);
        simplex.setPoint(0, newPoint);
        PointValuePair point = simplex.getPoint(0);
        assertEquals(newPoint, point);
    }

    @Test(expected = OutOfRangeException.class)
    public void testSetPointOutOfRange() {
        PointValuePair newPoint = new PointValuePair(new double[]{1.0, 1.0}, 2.0);
        simplex.setPoint(3, newPoint);
    }

    @Test
    public void testSetPoints() {
        double[] startPoint = {0.0, 0.0};
        simplex.build(startPoint);
        PointValuePair[] newPoints = {
            new PointValuePair(new double[]{1.0, 1.0}, 2.0),
            new PointValuePair(new double[]{2.0, 2.0}, 4.0),
            new PointValuePair(new double[]{3.0, 3.0}, 6.0)
        };
        simplex.setPoints(newPoints);
        PointValuePair[] points = simplex.getPoints();
        assertArrayEquals(newPoints, points);
    }

    @Test(expected = DimensionMismatchException.class)
    public void testSetPointsWithMismatchedLength() {
        PointValuePair[] newPoints = {
            new PointValuePair(new double[]{1.0, 1.0}, 2.0)
        };
        simplex.setPoints(newPoints);
    }
}