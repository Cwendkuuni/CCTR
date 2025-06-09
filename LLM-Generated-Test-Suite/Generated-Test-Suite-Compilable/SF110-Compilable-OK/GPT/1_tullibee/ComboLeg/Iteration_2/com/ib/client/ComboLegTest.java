package com.ib.client;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.ib.client.ComboLeg;

public class ComboLegTest {

    private ComboLeg comboLeg1;
    private ComboLeg comboLeg2;
    private ComboLeg comboLeg3;

    @Before
    public void setUp() {
        comboLeg1 = new ComboLeg(1, 100, "BUY", "NYSE", ComboLeg.OPEN, 0, "Location1");
        comboLeg2 = new ComboLeg(1, 100, "BUY", "NYSE", ComboLeg.OPEN, 0, "Location1");
        comboLeg3 = new ComboLeg(2, 200, "SELL", "NASDAQ", ComboLeg.CLOSE, 1, "Location2");
    }

    @Test
    public void testDefaultConstructor() {
        ComboLeg comboLeg = new ComboLeg();
        assertEquals(0, comboLeg.m_conId);
        assertEquals(0, comboLeg.m_ratio);
        assertNull(comboLeg.m_action);
        assertNull(comboLeg.m_exchange);
        assertEquals(0, comboLeg.m_openClose);
        assertEquals(0, comboLeg.m_shortSaleSlot);
        assertNull(comboLeg.m_designatedLocation);
    }

    @Test
    public void testPartialConstructor() {
        ComboLeg comboLeg = new ComboLeg(1, 100, "BUY", "NYSE", ComboLeg.OPEN);
        assertEquals(1, comboLeg.m_conId);
        assertEquals(100, comboLeg.m_ratio);
        assertEquals("BUY", comboLeg.m_action);
        assertEquals("NYSE", comboLeg.m_exchange);
        assertEquals(ComboLeg.OPEN, comboLeg.m_openClose);
        assertEquals(0, comboLeg.m_shortSaleSlot);
        assertNull(comboLeg.m_designatedLocation);
    }

    @Test
    public void testFullConstructor() {
        assertEquals(1, comboLeg1.m_conId);
        assertEquals(100, comboLeg1.m_ratio);
        assertEquals("BUY", comboLeg1.m_action);
        assertEquals("NYSE", comboLeg1.m_exchange);
        assertEquals(ComboLeg.OPEN, comboLeg1.m_openClose);
        assertEquals(0, comboLeg1.m_shortSaleSlot);
        assertEquals("Location1", comboLeg1.m_designatedLocation);
    }

    @Test
    public void testEqualsSameObject() {
        assertTrue(comboLeg1.equals(comboLeg1));
    }

    @Test
    public void testEqualsNull() {
        assertFalse(comboLeg1.equals(null));
    }

    @Test
    public void testEqualsDifferentType() {
        assertFalse(comboLeg1.equals("Some String"));
    }

    @Test
    public void testEqualsEqualObjects() {
        assertTrue(comboLeg1.equals(comboLeg2));
    }

    @Test
    public void testEqualsDifferentObjects() {
        assertFalse(comboLeg1.equals(comboLeg3));
    }

    @Test
    public void testEqualsDifferentConId() {
        ComboLeg differentConId = new ComboLeg(2, 100, "BUY", "NYSE", ComboLeg.OPEN, 0, "Location1");
        assertFalse(comboLeg1.equals(differentConId));
    }

    @Test
    public void testEqualsDifferentRatio() {
        ComboLeg differentRatio = new ComboLeg(1, 200, "BUY", "NYSE", ComboLeg.OPEN, 0, "Location1");
        assertFalse(comboLeg1.equals(differentRatio));
    }

    @Test
    public void testEqualsDifferentAction() {
        ComboLeg differentAction = new ComboLeg(1, 100, "SELL", "NYSE", ComboLeg.OPEN, 0, "Location1");
        assertFalse(comboLeg1.equals(differentAction));
    }

    @Test
    public void testEqualsDifferentExchange() {
        ComboLeg differentExchange = new ComboLeg(1, 100, "BUY", "NASDAQ", ComboLeg.OPEN, 0, "Location1");
        assertFalse(comboLeg1.equals(differentExchange));
    }

    @Test
    public void testEqualsDifferentOpenClose() {
        ComboLeg differentOpenClose = new ComboLeg(1, 100, "BUY", "NYSE", ComboLeg.CLOSE, 0, "Location1");
        assertFalse(comboLeg1.equals(differentOpenClose));
    }

    @Test
    public void testEqualsDifferentShortSaleSlot() {
        ComboLeg differentShortSaleSlot = new ComboLeg(1, 100, "BUY", "NYSE", ComboLeg.OPEN, 1, "Location1");
        assertFalse(comboLeg1.equals(differentShortSaleSlot));
    }

    @Test
    public void testEqualsDifferentDesignatedLocation() {
        ComboLeg differentDesignatedLocation = new ComboLeg(1, 100, "BUY", "NYSE", ComboLeg.OPEN, 0, "Location2");
        assertFalse(comboLeg1.equals(differentDesignatedLocation));
    }
}