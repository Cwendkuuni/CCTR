package org.apache.commons.math3.linear;

import static org.junit.Assert.*;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.exception.*;
import org.junit.Before;
import org.junit.Test;

public class SchurTransformerTest {

    private RealMatrix validMatrix;
    private RealMatrix nonSquareMatrix;

    @Before
    public void setUp() {
        // Create a valid square matrix
        validMatrix = MatrixUtils.createRealMatrix(new double[][] {
            {4, 1, -2, 2},
            {1, 2, 0, 1},
            {-2, 0, 3, -2},
            {2, 1, -2, -1}
        });

        // Create a non-square matrix
        nonSquareMatrix = MatrixUtils.createRealMatrix(new double[][] {
            {1, 2, 3},
            {4, 5, 6}
        });
    }

    @Test
    public void testConstructorWithValidMatrix() {
        try {
            SchurTransformer transformer = new SchurTransformer(validMatrix);
            assertNotNull(transformer);
        } catch (Exception e) {
            fail("Constructor should not throw an exception for a valid square matrix.");
        }
    }

    @Test(expected = NonSquareMatrixException.class)
    public void testConstructorWithNonSquareMatrix() {
        new SchurTransformer(nonSquareMatrix);
    }

    @Test
    public void testGetP() {
        SchurTransformer transformer = new SchurTransformer(validMatrix);
        RealMatrix P = transformer.getP();
        assertNotNull(P);
        assertEquals(validMatrix.getRowDimension(), P.getRowDimension());
        assertEquals(validMatrix.getColumnDimension(), P.getColumnDimension());
    }

    @Test
    public void testGetPT() {
        SchurTransformer transformer = new SchurTransformer(validMatrix);
        RealMatrix PT = transformer.getPT();
        assertNotNull(PT);
        assertEquals(validMatrix.getRowDimension(), PT.getRowDimension());
        assertEquals(validMatrix.getColumnDimension(), PT.getColumnDimension());

        // Check if PT is the transpose of P
        RealMatrix P = transformer.getP();
        assertEquals(P.transpose(), PT);
    }

    @Test
    public void testGetT() {
        SchurTransformer transformer = new SchurTransformer(validMatrix);
        RealMatrix T = transformer.getT();
        assertNotNull(T);
        assertEquals(validMatrix.getRowDimension(), T.getRowDimension());
        assertEquals(validMatrix.getColumnDimension(), T.getColumnDimension());
    }

    @Test
    public void testSchurDecompositionProperties() {
        SchurTransformer transformer = new SchurTransformer(validMatrix);
        RealMatrix P = transformer.getP();
        RealMatrix PT = transformer.getPT();
        RealMatrix T = transformer.getT();

        // Check if P * T * P^T is approximately equal to the original matrix
        RealMatrix reconstructedMatrix = P.multiply(T).multiply(PT);
        assertTrue(reconstructedMatrix.subtract(validMatrix).getNorm() < 1e-10);
    }
}