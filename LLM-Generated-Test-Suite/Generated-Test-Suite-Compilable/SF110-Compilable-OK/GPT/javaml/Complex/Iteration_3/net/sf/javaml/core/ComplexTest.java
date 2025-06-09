package net.sf.javaml.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import net.sf.javaml.core.Complex;

public class ComplexTest {

    private Complex complex1;
    private Complex complex2;

    @Before
    public void setUp() {
        complex1 = new Complex(3.0, 4.0);
        complex2 = new Complex(1.0, 2.0);
    }

    @Test
    public void testConstructorWithParameters() {
        Complex complex = new Complex(5.0, 6.0);
        assertEquals(5.0, complex.re, 0.001);
        assertEquals(6.0, complex.im, 0.001);
    }

    @Test
    public void testDefaultConstructor() {
        Complex complex = new Complex();
        assertEquals(0.0, complex.re, 0.001);
        assertEquals(0.0, complex.im, 0.001);
    }

    @Test
    public void testToString() {
        assertEquals("3.0 + 4.0i", complex1.toString());
    }

    @Test
    public void testAbs() {
        assertEquals(5.0, complex1.abs(), 0.001);
    }

    @Test
    public void testPlusInstanceMethod() {
        complex1.plus(complex2);
        assertEquals(4.0, complex1.re, 0.001);
        assertEquals(6.0, complex1.im, 0.001);
    }

    @Test
    public void testMinus() {
        complex1.minus(complex2);
        assertEquals(2.0, complex1.re, 0.001);
        assertEquals(2.0, complex1.im, 0.001);
    }

    @Test
    public void testTimesComplex() {
        complex1.times(complex2);
        assertEquals(-5.0, complex1.re, 0.001);
        assertEquals(10.0, complex1.im, 0.001);
    }

    @Test
    public void testTimesDouble() {
        complex1.times(2.0);
        assertEquals(6.0, complex1.re, 0.001);
        assertEquals(8.0, complex1.im, 0.001);
    }

    @Test
    public void testConjugate() {
        complex1.conjugate();
        assertEquals(3.0, complex1.re, 0.001);
        assertEquals(-4.0, complex1.im, 0.001);
    }

    @Test
    public void testStaticPlus() {
        Complex result = Complex.plus(complex1, complex2);
        assertEquals(4.0, result.re, 0.001);
        assertEquals(6.0, result.im, 0.001);
    }

    @Test
    public void testStaticMultiplyComplex() {
        Complex result = Complex.multiply(complex1, complex2);
        assertEquals(-5.0, result.re, 0.001);
        assertEquals(10.0, result.im, 0.001);
    }

    @Test
    public void testStaticMultiplyDouble() {
        Complex result = Complex.multiply(complex1, 2.0);
        assertEquals(6.0, result.re, 0.001);
        assertEquals(8.0, result.im, 0.001);
    }

    @Test
    public void testStaticComplexI() {
        assertNotNull(Complex.I);
        assertEquals(0.0, Complex.I.re, 0.001);
        assertEquals(1.0, Complex.I.im, 0.001);
    }
}