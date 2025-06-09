package org.apache.commons.math3.linear;

import static org.junit.Assert.*;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.exception.*;
import org.junit.Before;
import org.junit.Test;

public class SchurTransformerTest {

    private RealMatrix matrix;
    private SchurTransformer transformer;

    @Before
    public void setUp() {
        // Initialize a simple 3x3 matrix for testing
        double[][] data = {
            {4, -2, 1},
            {1, 1, 0},
            {0, 3, 3}
        };
        matrix = MatrixUtils.createRealMatrix(data);
        transformer = new SchurTransformer(matrix);
    }

    @Test
    public void testGetP() {
        RealMatrix P = transformer.getP();
        assertNotNull("Matrix P should not be null", P);
        assertEquals("Matrix P should have the same dimensions as the input matrix", matrix.getRowDimension(), P.getRowDimension());
        assertEquals("Matrix P should have the same dimensions as the input matrix", matrix.getColumnDimension(), P.getColumnDimension());
    }

    @Test
    public void testGetPT() {
        RealMatrix PT = transformer.getPT();
        assertNotNull("Matrix PT should not be null", PT);
        assertEquals("Matrix PT should have the same dimensions as the input matrix", matrix.getRowDimension(), PT.getRowDimension());
        assertEquals("Matrix PT should have the same dimensions as the input matrix", matrix.getColumnDimension(), PT.getColumnDimension());
    }

    @Test
    public void testGetT() {
        RealMatrix T = transformer.getT();
        assertNotNull("Matrix T should not be null", T);
        assertEquals("Matrix T should have the same dimensions as the input matrix", matrix.getRowDimension(), T.getRowDimension());
        assertEquals("Matrix T should have the same dimensions as the input matrix", matrix.getColumnDimension(), T.getColumnDimension());
    }

    @Test(expected = NonSquareMatrixException.class)
    public void testNonSquareMatrixException() {
        // Create a non-square matrix
        double[][] data = {
            {1, 2, 3},
            {4, 5, 6}
        };
        RealMatrix nonSquareMatrix = MatrixUtils.createRealMatrix(data);
        new SchurTransformer(nonSquareMatrix);
    }

    @Test(expected = MaxCountExceededException.class)
    public void testMaxCountExceededException() {
        // This test is tricky because it depends on the internal state of the algorithm.
        // We will create a matrix that is known to cause convergence issues.
        double[][] data = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
        };
        RealMatrix problematicMatrix = MatrixUtils.createRealMatrix(data);
        new SchurTransformer(problematicMatrix);
    }
}