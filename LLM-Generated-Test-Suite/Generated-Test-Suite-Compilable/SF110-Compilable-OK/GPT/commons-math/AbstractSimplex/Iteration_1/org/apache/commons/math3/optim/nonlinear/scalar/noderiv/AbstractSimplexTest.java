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

    private AbstractSimplex simplex;

    @Before
    public void setUp() {
        // Create a concrete subclass for testing
        simplex = new AbstractSimplex(2, 1.0) {
            @Override
            public void iterate(MultivariateFunction p0, Comparator<PointValuePair> p1) {
                // Dummy implementation for testing
            }
        };
    }

    @Test(expected = NullArgumentException.class)
    public void testConstructorWithNullSteps() {
        new AbstractSimplex((double[]) null) {
            @Override
            public void iterate(MultivariateFunction p0, Comparator<PointValuePair> p1) {
            }
        };
    }

    @Test(expected = ZeroException.class)
    public void testConstructorWithZeroLengthSteps() {
        new AbstractSimplex(new double[0]) {
            @Override
            public void iterate(MultivariateFunction p0, Comparator<PointValuePair> p1) {
            }
        };
    }

    @Test(expected = ZeroException.class)
    public void testConstructorWithZeroStepValue() {
        new AbstractSimplex(new double[]{0.0, 1.0}) {
            @Override
            public void iterate(MultivariateFunction p0, Comparator<PointValuePair> p1) {
            }
        };
    }

    @Test(expected = NotStrictlyPositiveException.class)
    public void testConstructorWithEmptyReferenceSimplex() {
        new AbstractSimplex(new double[0][0]) {
            @Override
            public void iterate(MultivariateFunction p0, Comparator<PointValuePair> p1) {
            }
        };
    }

    @Test(expected = DimensionMismatchException.class)
    public void testConstructorWithMismatchedReferenceSimplex() {
        new AbstractSimplex(new double[][]{{1.0, 2.0}, {1.0}}) {
            @Override
            public void iterate(MultivariateFunction p0, Comparator<PointValuePair> p1) {
            }
        };
    }

    @Test
    public void testGetDimension() {
        assertEquals(2, simplex.getDimension());
    }

    @Test
    public void testBuild() {
        double[] startPoint = {1.0, 1.0};
        simplex.build(startPoint);
        PointValuePair[] points = simplex.getPoints();
        assertEquals(3, points.length);
        assertArrayEquals(startPoint, points[0].getPointRef(), 1e-10);
    }

    @Test(expected = DimensionMismatchException.class)
    public void testBuildWithMismatchedStartPoint() {
        simplex.build(new double[]{1.0});
    }

    @Test
    public void testEvaluate() {
        double[] startPoint = {1.0, 1.0};
        simplex.build(startPoint);
        MultivariateFunction function = point -> point[0] + point[1];
        Comparator<PointValuePair> comparator = Comparator.comparingDouble(PointValuePair::getValue);
        simplex.evaluate(function, comparator);
        PointValuePair[] points = simplex.getPoints();
        assertNotNull(points[0].getValue());
    }

    @Test
    public void testReplaceWorstPoint() {
        double[] startPoint = {1.0, 1.0};
        simplex.build(startPoint);
        Comparator<PointValuePair> comparator = Comparator.comparingDouble(PointValuePair::getValue);
        PointValuePair newPoint = new PointValuePair(new double[]{0.5, 0.5}, 0.5);
        simplex.replaceWorstPoint(newPoint, comparator);
        PointValuePair[] points = simplex.getPoints();
        assertEquals(newPoint, points[2]);
    }

    @Test
    public void testGetPoint() {
        double[] startPoint = {1.0, 1.0};
        simplex.build(startPoint);
        PointValuePair point = simplex.getPoint(0);
        assertArrayEquals(startPoint, point.getPointRef(), 1e-10);
    }

    @Test(expected = OutOfRangeException.class)
    public void testGetPointOutOfRange() {
        simplex.getPoint(3);
    }

    @Test
    public void testSetPoint() {
        double[] startPoint = {1.0, 1.0};
        simplex.build(startPoint);
        PointValuePair newPoint = new PointValuePair(new double[]{0.5, 0.5}, 0.5);
        simplex.setPoint(0, newPoint);
        assertEquals(newPoint, simplex.getPoint(0));
    }

    @Test(expected = OutOfRangeException.class)
    public void testSetPointOutOfRange() {
        PointValuePair newPoint = new PointValuePair(new double[]{0.5, 0.5}, 0.5);
        simplex.setPoint(3, newPoint);
    }

    @Test
    public void testSetPoints() {
        double[] startPoint = {1.0, 1.0};
        simplex.build(startPoint);
        PointValuePair[] newPoints = {
            new PointValuePair(new double[]{0.5, 0.5}, 0.5),
            new PointValuePair(new double[]{0.6, 0.6}, 0.6),
            new PointValuePair(new double[]{0.7, 0.7}, 0.7)
        };
        simplex.setPoints(newPoints);
        assertArrayEquals(newPoints, simplex.getPoints());
    }

    @Test(expected = DimensionMismatchException.class)
    public void testSetPointsWithMismatchedLength() {
        PointValuePair[] newPoints = {
            new PointValuePair(new double[]{0.5, 0.5}, 0.5),
            new PointValuePair(new double[]{0.6, 0.6}, 0.6)
        };
        simplex.setPoints(newPoints);
    }
}