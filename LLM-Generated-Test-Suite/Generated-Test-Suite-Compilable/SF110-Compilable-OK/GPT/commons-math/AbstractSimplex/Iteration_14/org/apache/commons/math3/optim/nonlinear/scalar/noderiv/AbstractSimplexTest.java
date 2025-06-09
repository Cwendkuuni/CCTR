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
        simplex = new TestSimplex(3);
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
    public void testConstructorWithZeroLengthSteps() {
        new TestSimplex(new double[]{});
    }

    @Test(expected = ZeroException.class)
    public void testConstructorWithZeroStepValue() {
        new TestSimplex(new double[]{1.0, 0.0, 1.0});
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
    public void testBuild() {
        double[] startPoint = {1.0, 2.0, 3.0};
        simplex.build(startPoint);
        assertEquals(4, simplex.getSize());
    }

    @Test(expected = DimensionMismatchException.class)
    public void testBuildWithMismatchedStartPoint() {
        double[] startPoint = {1.0, 2.0};
        simplex.build(startPoint);
    }

    @Test
    public void testEvaluate() {
        double[] startPoint = {1.0, 2.0, 3.0};
        simplex.build(startPoint);
        MultivariateFunction function = point -> point[0] + point[1] + point[2];
        Comparator<PointValuePair> comparator = Comparator.comparingDouble(PointValuePair::getValue);
        simplex.evaluate(function, comparator);
        assertNotNull(simplex.getPoints()[0].getValue());
    }

    @Test
    public void testReplaceWorstPoint() {
        double[] startPoint = {1.0, 2.0, 3.0};
        simplex.build(startPoint);
        Comparator<PointValuePair> comparator = Comparator.comparingDouble(PointValuePair::getValue);
        PointValuePair newPoint = new PointValuePair(new double[]{0.0, 0.0, 0.0}, 0.0);
        simplex.replaceWorstPoint(newPoint, comparator);
        assertEquals(newPoint, simplex.getPoints()[3]);
    }

    @Test
    public void testGetPoint() {
        double[] startPoint = {1.0, 2.0, 3.0};
        simplex.build(startPoint);
        PointValuePair point = simplex.getPoint(0);
        assertArrayEquals(startPoint, point.getPointRef(), 0.0);
    }

    @Test(expected = OutOfRangeException.class)
    public void testGetPointOutOfRange() {
        simplex.getPoint(10);
    }

    @Test
    public void testSetPoint() {
        double[] startPoint = {1.0, 2.0, 3.0};
        simplex.build(startPoint);
        PointValuePair newPoint = new PointValuePair(new double[]{0.0, 0.0, 0.0}, 0.0);
        simplex.setPoint(0, newPoint);
        assertEquals(newPoint, simplex.getPoint(0));
    }

    @Test(expected = OutOfRangeException.class)
    public void testSetPointOutOfRange() {
        PointValuePair newPoint = new PointValuePair(new double[]{0.0, 0.0, 0.0}, 0.0);
        simplex.setPoint(10, newPoint);
    }

    @Test
    public void testSetPoints() {
        double[] startPoint = {1.0, 2.0, 3.0};
        simplex.build(startPoint);
        PointValuePair[] newPoints = new PointValuePair[]{
                new PointValuePair(new double[]{0.0, 0.0, 0.0}, 0.0),
                new PointValuePair(new double[]{1.0, 1.0, 1.0}, 1.0),
                new PointValuePair(new double[]{2.0, 2.0, 2.0}, 2.0),
                new PointValuePair(new double[]{3.0, 3.0, 3.0}, 3.0)
        };
        simplex.setPoints(newPoints);
        assertArrayEquals(newPoints, simplex.getPoints());
    }

    @Test(expected = DimensionMismatchException.class)
    public void testSetPointsWithMismatchedLength() {
        double[] startPoint = {1.0, 2.0, 3.0};
        simplex.build(startPoint);
        PointValuePair[] newPoints = new PointValuePair[]{
                new PointValuePair(new double[]{0.0, 0.0, 0.0}, 0.0)
        };
        simplex.setPoints(newPoints);
    }
}