package org.apache.commons.math3.linear;

import static org.junit.Assert.*;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.exception.*;
import org.junit.Before;
import org.junit.Test;

public class SchurTransformerTest {

    private RealMatrix squareMatrix;
    private RealMatrix nonSquareMatrix;
    private SchurTransformer transformer;

    @Before
    public void setUp() {
        // Initialize a simple 2x2 square matrix
        squareMatrix = MatrixUtils.createRealMatrix(new double[][] {
            {4, 1},
            {2, 3}
        });

        // Initialize a non-square matrix
        nonSquareMatrix = MatrixUtils.createRealMatrix(new double[][] {
            {1, 2, 3},
            {4, 5, 6}
        });

        // Initialize the SchurTransformer with the square matrix
        transformer = new SchurTransformer(squareMatrix);
    }

    @Test
    public void testGetP() {
        RealMatrix P = transformer.getP();
        assertNotNull("Matrix P should not be null", P);
        assertEquals("Matrix P should have the same dimensions as the input matrix", 
                     squareMatrix.getRowDimension(), P.getRowDimension());
        assertEquals("Matrix P should have the same dimensions as the input matrix", 
                     squareMatrix.getColumnDimension(), P.getColumnDimension());
    }

    @Test
    public void testGetPT() {
        RealMatrix PT = transformer.getPT();
        assertNotNull("Matrix PT should not be null", PT);
        assertEquals("Matrix PT should have the same dimensions as the input matrix", 
                     squareMatrix.getRowDimension(), PT.getRowDimension());
        assertEquals("Matrix PT should have the same dimensions as the input matrix", 
                     squareMatrix.getColumnDimension(), PT.getColumnDimension());
    }

    @Test
    public void testGetT() {
        RealMatrix T = transformer.getT();
        assertNotNull("Matrix T should not be null", T);
        assertEquals("Matrix T should have the same dimensions as the input matrix", 
                     squareMatrix.getRowDimension(), T.getRowDimension());
        assertEquals("Matrix T should have the same dimensions as the input matrix", 
                     squareMatrix.getColumnDimension(), T.getColumnDimension());
    }

    @Test(expected = NonSquareMatrixException.class)
    public void testConstructorWithNonSquareMatrix() {
        new SchurTransformer(nonSquareMatrix);
    }

    @Test
    public void testTransformationProperties() {
        RealMatrix P = transformer.getP();
        RealMatrix PT = transformer.getPT();
        RealMatrix T = transformer.getT();

        // Check if P * T * P^T is approximately equal to the original matrix
        RealMatrix reconstructedMatrix = P.multiply(T).multiply(PT);
        assertTrue("Reconstructed matrix should be approximately equal to the original matrix",
                   squareMatrix.subtract(reconstructedMatrix).getNorm() < 1e-10);
    }
}