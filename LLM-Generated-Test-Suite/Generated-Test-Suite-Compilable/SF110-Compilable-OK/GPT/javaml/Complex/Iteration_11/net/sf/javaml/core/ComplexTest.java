package net.sf.javaml.core;

import static org.junit.Assert.*;
import org.junit.Test;
import net.sf.javaml.core.Complex;

public class ComplexTest {

    @Test
    public void testConstructorWithParameters() {
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
    public void testPlusInstanceMethod() {
        Complex c1 = new Complex(1.0, 2.0);
        Complex c2 = new Complex(3.0, 4.0);
        c1.plus(c2);
        assertEquals(4.0, c1.re, 0.0001);
        assertEquals(6.0, c1.im, 0.0001);
    }

    @Test
    public void testMinus() {
        Complex c1 = new Complex(5.0, 6.0);
        Complex c2 = new Complex(3.0, 4.0);
        c1.minus(c2);
        assertEquals(2.0, c1.re, 0.0001);
        assertEquals(2.0, c1.im, 0.0001);
    }

    @Test
    public void testTimesComplex() {
        Complex c1 = new Complex(1.0, 2.0);
        Complex c2 = new Complex(3.0, 4.0);
        c1.times(c2);
        assertEquals(-5.0, c1.re, 0.0001);
        assertEquals(10.0, c1.im, 0.0001);
    }

    @Test
    public void testTimesDouble() {
        Complex c = new Complex(1.0, 2.0);
        c.times(2.0);
        assertEquals(2.0, c.re, 0.0001);
        assertEquals(4.0, c.im, 0.0001);
    }

    @Test
    public void testConjugate() {
        Complex c = new Complex(1.0, 2.0);
        c.conjugate();
        assertEquals(1.0, c.re, 0.0001);
        assertEquals(-2.0, c.im, 0.0001);
    }

    @Test
    public void testStaticPlus() {
        Complex c1 = new Complex(1.0, 2.0);
        Complex c2 = new Complex(3.0, 4.0);
        Complex result = Complex.plus(c1, c2);
        assertEquals(4.0, result.re, 0.0001);
        assertEquals(6.0, result.im, 0.0001);
    }

    @Test
    public void testStaticMultiplyComplexDouble() {
        Complex c = new Complex(1.0, 2.0);
        Complex result = Complex.multiply(c, 2.0);
        assertEquals(2.0, result.re, 0.0001);
        assertEquals(4.0, result.im, 0.0001);
    }

    @Test
    public void testStaticMultiplyComplexComplex() {
        Complex c1 = new Complex(1.0, 2.0);
        Complex c2 = new Complex(3.0, 4.0);
        Complex result = Complex.multiply(c1, c2);
        assertEquals(-5.0, result.re, 0.0001);
        assertEquals(10.0, result.im, 0.0001);
    }

    @Test
    public void testStaticI() {
        assertEquals(0.0, Complex.I.re, 0.0001);
        assertEquals(1.0, Complex.I.im, 0.0001);
    }
}