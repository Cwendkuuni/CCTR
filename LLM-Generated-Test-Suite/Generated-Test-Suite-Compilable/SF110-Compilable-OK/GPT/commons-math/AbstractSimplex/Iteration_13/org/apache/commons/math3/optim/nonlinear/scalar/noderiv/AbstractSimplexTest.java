package org.apache.commons.math3.optim.nonlinear.scalar.noderiv;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
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
        protected TestSimplex(int n) {
            super(n);
        }

        protected TestSimplex(int n, double sideLength) {
            super(n, sideLength);
        }

        protected TestSimplex(double[] steps) {
            super(steps);
        }

        protected TestSimplex(double[][] referenceSimplex) {
            super(referenceSimplex);
        }

        @Override
        public void iterate(MultivariateFunction function, Comparator<PointValuePair> comparator) {
            // No-op implementation for testing
        }
    }

    private TestSimplex simplex;

    @Before
    public void setUp() {
        simplex = new TestSimplex(2, 1.0);
    }

    @Test
    public void testConstructorWithDimension() {
        TestSimplex simplex = new TestSimplex(3);
        assertEquals(3, simplex.getDimension());
    }

    @Test
    public void testConstructorWithDimensionAndSideLength() {
        TestSimplex simplex = new TestSimplex(3, 2.0);
        assertEquals(3, simplex.getDimension());
    }

    @Test(expected = NullArgumentException.class)
    public void testConstructorWithNullSteps() {
        new TestSimplex((double[]) null);
    }

    @Test(expected = ZeroException.class)
    public void testConstructorWithEmptySteps() {
        new TestSimplex(new double[]{});
    }

    @Test(expected = ZeroException.class)
    public void testConstructorWithZeroStep() {
        new TestSimplex(new double[]{0.0, 1.0});
    }

    @Test(expected = NotStrictlyPositiveException.class)
    public void testConstructorWithEmptyReferenceSimplex() {
        new TestSimplex(new double[][]{});
    }

    @Test(expected = DimensionMismatchException.class)
    public void testConstructorWithMismatchedReferenceSimplex() {
        new TestSimplex(new double[][]{{1.0, 2.0}, {3.0}});
    }

    @Test
    public void testGetDimension() {
        assertEquals(2, simplex.getDimension());
    }

    @Test
    public void testBuild() {
        double[] startPoint = {1.0, 1.0};
        simplex.build(startPoint);
        assertEquals(3, simplex.getSize());
    }

    @Test(expected = DimensionMismatchException.class)
    public void testBuildWithMismatchedStartPoint() {
        double[] startPoint = {1.0};
        simplex.build(startPoint);
    }

    @Test
    public void testEvaluate() {
        double[] startPoint = {1.0, 1.0};
        simplex.build(startPoint);
        MultivariateFunction function = point -> point[0] + point[1];
        Comparator<PointValuePair> comparator = Comparator.comparingDouble(PointValuePair::getValue);
        simplex.evaluate(function, comparator);
        PointValuePair[] points = simplex.getPoints();
        assertNotNull(points);
        assertEquals(3, points.length);
    }

    @Test
    public void testReplaceWorstPoint() {
        double[] startPoint = {1.0, 1.0};
        simplex.build(startPoint);
        Comparator<PointValuePair> comparator = Comparator.comparingDouble(PointValuePair::getValue);
        PointValuePair newPoint = new PointValuePair(new double[]{0.5, 0.5}, 1.0);
        simplex.replaceWorstPoint(newPoint, comparator);
        PointValuePair[] points = simplex.getPoints();
        assertEquals(newPoint, points[2]);
    }

    @Test
    public void testGetPoints() {
        double[] startPoint = {1.0, 1.0};
        simplex.build(startPoint);
        PointValuePair[] points = simplex.getPoints();
        assertNotNull(points);
        assertEquals(3, points.length);
    }

    @Test
    public void testGetPoint() {
        double[] startPoint = {1.0, 1.0};
        simplex.build(startPoint);
        PointValuePair point = simplex.getPoint(0);
        assertNotNull(point);
    }

    @Test(expected = OutOfRangeException.class)
    public void testGetPointOutOfRange() {
        simplex.getPoint(10);
    }

    @Test
    public void testSetPoint() {
        double[] startPoint = {1.0, 1.0};
        simplex.build(startPoint);
        PointValuePair newPoint = new PointValuePair(new double[]{0.5, 0.5}, 1.0);
        simplex.setPoint(0, newPoint);
        assertEquals(newPoint, simplex.getPoint(0));
    }

    @Test(expected = OutOfRangeException.class)
    public void testSetPointOutOfRange() {
        PointValuePair newPoint = new PointValuePair(new double[]{0.5, 0.5}, 1.0);
        simplex.setPoint(10, newPoint);
    }

    @Test
    public void testSetPoints() {
        double[] startPoint = {1.0, 1.0};
        simplex.build(startPoint);
        PointValuePair[] newPoints = new PointValuePair[]{
            new PointValuePair(new double[]{0.5, 0.5}, 1.0),
            new PointValuePair(new double[]{1.5, 1.5}, 2.0),
            new PointValuePair(new double[]{2.0, 2.0}, 3.0)
        };
        simplex.setPoints(newPoints);
        assertArrayEquals(newPoints, simplex.getPoints());
    }

    @Test(expected = DimensionMismatchException.class)
    public void testSetPointsWithMismatchedLength() {
        PointValuePair[] newPoints = new PointValuePair[]{
            new PointValuePair(new double[]{0.5, 0.5}, 1.0)
        };
        simplex.setPoints(newPoints);
    }
}