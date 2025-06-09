package org.apache.commons.math3.dfp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DfpDecTest {

    private DfpField field;
    private DfpDec dfpDec;

    @Before
    public void setUp() {
        field = new DfpField(10);
        dfpDec = new DfpDec(field);
    }

    @Test
    public void testConstructorWithDfpField() {
        assertNotNull(dfpDec);
    }

    @Test
    public void testConstructorWithDfpFieldAndByte() {
        DfpDec dfpDecByte = new DfpDec(field, (byte) 10);
        assertNotNull(dfpDecByte);
    }

    @Test
    public void testConstructorWithDfpFieldAndInt() {
        DfpDec dfpDecInt = new DfpDec(field, 10);
        assertNotNull(dfpDecInt);
    }

    @Test
    public void testConstructorWithDfpFieldAndLong() {
        DfpDec dfpDecLong = new DfpDec(field, 10L);
        assertNotNull(dfpDecLong);
    }

    @Test
    public void testConstructorWithDfpFieldAndDouble() {
        DfpDec dfpDecDouble = new DfpDec(field, 10.0);
        assertNotNull(dfpDecDouble);
    }

    @Test
    public void testConstructorWithDfp() {
        Dfp dfp = new Dfp(field, 10);
        DfpDec dfpDecDfp = new DfpDec(dfp);
        assertNotNull(dfpDecDfp);
    }

    @Test
    public void testConstructorWithDfpFieldAndString() {
        DfpDec dfpDecString = new DfpDec(field, "10");
        assertNotNull(dfpDecString);
    }

    @Test
    public void testConstructorWithDfpFieldAndSignAndNans() {
        DfpDec dfpDecSignNans = new DfpDec(field, (byte) 1, (byte) 0);
        assertNotNull(dfpDecSignNans);
    }

    @Test
    public void testNewInstance() {
        Dfp newInstance = dfpDec.newInstance();
        assertNotNull(newInstance);
        assertTrue(newInstance instanceof DfpDec);
    }

    @Test
    public void testNewInstanceWithByte() {
        Dfp newInstance = dfpDec.newInstance((byte) 10);
        assertNotNull(newInstance);
        assertTrue(newInstance instanceof DfpDec);
    }

    @Test
    public void testNewInstanceWithInt() {
        Dfp newInstance = dfpDec.newInstance(10);
        assertNotNull(newInstance);
        assertTrue(newInstance instanceof DfpDec);
    }

    @Test
    public void testNewInstanceWithLong() {
        Dfp newInstance = dfpDec.newInstance(10L);
        assertNotNull(newInstance);
        assertTrue(newInstance instanceof DfpDec);
    }

    @Test
    public void testNewInstanceWithDouble() {
        Dfp newInstance = dfpDec.newInstance(10.0);
        assertNotNull(newInstance);
        assertTrue(newInstance instanceof DfpDec);
    }

    @Test
    public void testNewInstanceWithDfp() {
        Dfp dfp = new Dfp(field, 10);
        Dfp newInstance = dfpDec.newInstance(dfp);
        assertNotNull(newInstance);
        assertTrue(newInstance instanceof DfpDec);
    }

    @Test
    public void testNewInstanceWithString() {
        Dfp newInstance = dfpDec.newInstance("10");
        assertNotNull(newInstance);
        assertTrue(newInstance instanceof DfpDec);
    }

    @Test
    public void testNewInstanceWithSignAndNans() {
        Dfp newInstance = dfpDec.newInstance((byte) 1, (byte) 0);
        assertNotNull(newInstance);
        assertTrue(newInstance instanceof DfpDec);
    }

    @Test
    public void testGetDecimalDigits() {
        int decimalDigits = dfpDec.getDecimalDigits();
        assertEquals(1, decimalDigits);
    }

    @Test
    public void testRound() {
        int result = dfpDec.round(0);
        assertEquals(0, result);
    }

    @Test
    public void testNextAfter() {
        Dfp x = new Dfp(field, 10);
        Dfp result = dfpDec.nextAfter(x);
        assertNotNull(result);
    }
}