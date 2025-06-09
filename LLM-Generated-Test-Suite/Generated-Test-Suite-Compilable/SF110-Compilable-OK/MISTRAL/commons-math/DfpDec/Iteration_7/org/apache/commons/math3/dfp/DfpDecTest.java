package org.apache.commons.math3.dfp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DfpDecTest {

    private DfpField field;

    @Before
    public void setUp() {
        field = new DfpField(10);
    }

    @Test
    public void testConstructorWithDfpField() {
        DfpDec dfpDec = new DfpDec(field);
        assertNotNull(dfpDec);
    }

    @Test
    public void testConstructorWithDfpFieldAndByte() {
        DfpDec dfpDec = new DfpDec(field, (byte) 10);
        assertNotNull(dfpDec);
    }

    @Test
    public void testConstructorWithDfpFieldAndInt() {
        DfpDec dfpDec = new DfpDec(field, 10);
        assertNotNull(dfpDec);
    }

    @Test
    public void testConstructorWithDfpFieldAndLong() {
        DfpDec dfpDec = new DfpDec(field, 10L);
        assertNotNull(dfpDec);
    }

    @Test
    public void testConstructorWithDfpFieldAndDouble() {
        DfpDec dfpDec = new DfpDec(field, 10.0);
        assertNotNull(dfpDec);
    }

    @Test
    public void testConstructorWithDfp() {
        Dfp dfp = new Dfp(field, 10);
        DfpDec dfpDec = new DfpDec(dfp);
        assertNotNull(dfpDec);
    }

    @Test
    public void testConstructorWithDfpFieldAndString() {
        DfpDec dfpDec = new DfpDec(field, "10");
        assertNotNull(dfpDec);
    }

    @Test
    public void testConstructorWithDfpFieldAndByteSignAndNans() {
        DfpDec dfpDec = new DfpDec(field, (byte) 1, (byte) 0);
        assertNotNull(dfpDec);
    }

    @Test
    public void testNewInstance() {
        DfpDec dfpDec = new DfpDec(field);
        Dfp newInstance = dfpDec.newInstance();
        assertNotNull(newInstance);
    }

    @Test
    public void testNewInstanceWithByte() {
        DfpDec dfpDec = new DfpDec(field);
        Dfp newInstance = dfpDec.newInstance((byte) 10);
        assertNotNull(newInstance);
    }

    @Test
    public void testNewInstanceWithInt() {
        DfpDec dfpDec = new DfpDec(field);
        Dfp newInstance = dfpDec.newInstance(10);
        assertNotNull(newInstance);
    }

    @Test
    public void testNewInstanceWithLong() {
        DfpDec dfpDec = new DfpDec(field);
        Dfp newInstance = dfpDec.newInstance(10L);
        assertNotNull(newInstance);
    }

    @Test
    public void testNewInstanceWithDouble() {
        DfpDec dfpDec = new DfpDec(field);
        Dfp newInstance = dfpDec.newInstance(10.0);
        assertNotNull(newInstance);
    }

    @Test
    public void testNewInstanceWithDfp() {
        Dfp dfp = new Dfp(field, 10);
        DfpDec dfpDec = new DfpDec(field);
        Dfp newInstance = dfpDec.newInstance(dfp);
        assertNotNull(newInstance);
    }

    @Test
    public void testNewInstanceWithString() {
        DfpDec dfpDec = new DfpDec(field);
        Dfp newInstance = dfpDec.newInstance("10");
        assertNotNull(newInstance);
    }

    @Test
    public void testNewInstanceWithByteSignAndNans() {
        DfpDec dfpDec = new DfpDec(field);
        Dfp newInstance = dfpDec.newInstance((byte) 1, (byte) 0);
        assertNotNull(newInstance);
    }

    @Test
    public void testGetDecimalDigits() {
        DfpDec dfpDec = new DfpDec(field);
        int decimalDigits = dfpDec.getDecimalDigits();
        assertEquals(1, decimalDigits);
    }

    @Test
    public void testRound() {
        DfpDec dfpDec = new DfpDec(field, 10.0);
        int result = dfpDec.round(0);
        assertEquals(0, result);
    }

    @Test
    public void testNextAfter() {
        DfpDec dfpDec = new DfpDec(field, 10.0);
        Dfp x = new Dfp(field, 11.0);
        Dfp result = dfpDec.nextAfter(x);
        assertNotNull(result);
    }
}