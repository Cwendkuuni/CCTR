package net.sf.javaml.core;

import static org.junit.Assert.*;
import org.junit.Test;

public class ComplexTest {

    @Test
    public void testConstructor() {
        Complex c = new Complex(3.0, 4.0);
        assertEquals(3.0, c.re, 0.0001);
        assertEquals(4.0, c.im, 0.0001);
    }

    @Test
    public void testDefaultConstructor() {
        Complex c = new Complex();
        assertEquals(0.0, c.re, 0.0001);
        assertEquals(0.0, c.im, 0.0001);
    }

    @Test
    public void testToString() {
        Complex c = new Complex(3.0, 4.0);
        assertEquals("3.0 + 4.0i", c.toString());
    }

    @Test
    public void testAbs() {
        Complex c = new Complex(3.0, 4.0);
        assertEquals(5.0, c.abs(), 0.0001);
    }

    @Test
    public void testPlus() {
        Complex a = new Complex(3.0, 4.0);
        Complex b = new Complex(1.0, 2.0);
        a.plus(b);
        assertEquals(4.0, a.re, 0.0001);
        assertEquals(6.0, a.im, 0.0001);
    }

    @Test
    public void testMinus() {
        Complex a = new Complex(3.0, 4.0);
        Complex b = new Complex(1.0, 2.0);
        a.minus(b);
        assertEquals(2.0, a.re, 0.0001);
        assertEquals(2.0, a.im, 0.0001);
    }

    @Test
    public void testTimesComplex() {
        Complex a = new Complex(3.0, 4.0);
        Complex b = new Complex(1.0, 2.0);
        a.times(b);
        assertEquals(-5.0, a.re, 0.0001);
        assertEquals(10.0, a.im, 0.0001);
    }

    @Test
    public void testTimesDouble() {
        Complex a = new Complex(3.0, 4.0);
        a.times(2.0);
        assertEquals(6.0, a.re, 0.0001);
        assertEquals(8.0, a.im, 0.0001);
    }

    @Test
    public void testConjugate() {
        Complex a = new Complex(3.0, 4.0);
        a.conjugate();
        assertEquals(3.0, a.re, 0.0001);
        assertEquals(-4.0, a.im, 0.0001);
    }

    @Test
    public void testStaticPlus() {
        Complex a = new Complex(3.0, 4.0);
        Complex b = new Complex(1.0, 2.0);
        Complex result = Complex.plus(a, b);
        assertEquals(4.0, result.re, 0.0001);
        assertEquals(6.0, result.im, 0.0001);
    }

    @Test
    public void testStaticMultiplyComplexDouble() {
        Complex a = new Complex(3.0, 4.0);
        Complex result = Complex.multiply(a, 2.0);
        assertEquals(6.0, result.re, 0.0001);
        assertEquals(8.0, result.im, 0.0001);
    }

    @Test
    public void testStaticMultiplyComplexComplex() {
        Complex a = new Complex(3.0, 4.0);
        Complex b = new Complex(1.0, 2.0);
        Complex result = Complex.multiply(a, b);
        assertEquals(-5.0, result.re, 0.0001);
        assertEquals(10.0, result.im, 0.0001);
    }

    @Test
    public void testStaticI() {
        assertEquals(0.0, Complex.I.re, 0.0001);
        assertEquals(1.0, Complex.I.im, 0.0001);
    }
}