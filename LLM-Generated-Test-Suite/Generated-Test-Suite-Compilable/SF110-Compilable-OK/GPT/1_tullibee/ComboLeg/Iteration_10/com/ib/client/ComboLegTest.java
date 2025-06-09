package com.ib.client;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ComboLegTest {

    private ComboLeg comboLeg1;
    private ComboLeg comboLeg2;

    @Before
    public void setUp() {
        comboLeg1 = new ComboLeg(1, 100, "BUY", "NYSE", ComboLeg.OPEN, 0, "Location1");
        comboLeg2 = new ComboLeg(1, 100, "BUY", "NYSE", ComboLeg.OPEN, 0, "Location1");
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
    public void testConstructorWithFiveParameters() {
        ComboLeg comboLeg = new ComboLeg(2, 200, "SELL", "NASDAQ", ComboLeg.CLOSE);
        assertEquals(2, comboLeg.m_conId);
        assertEquals(200, comboLeg.m_ratio);
        assertEquals("SELL", comboLeg.m_action);
        assertEquals("NASDAQ", comboLeg.m_exchange);
        assertEquals(ComboLeg.CLOSE, comboLeg.m_openClose);
        assertEquals(0, comboLeg.m_shortSaleSlot);
        assertNull(comboLeg.m_designatedLocation);
    }

    @Test
    public void testConstructorWithAllParameters() {
        ComboLeg comboLeg = new ComboLeg(3, 300, "HOLD", "AMEX", ComboLeg.UNKNOWN, 1, "Location2");
        assertEquals(3, comboLeg.m_conId);
        assertEquals(300, comboLeg.m_ratio);
        assertEquals("HOLD", comboLeg.m_action);
        assertEquals("AMEX", comboLeg.m_exchange);
        assertEquals(ComboLeg.UNKNOWN, comboLeg.m_openClose);
        assertEquals(1, comboLeg.m_shortSaleSlot);
        assertEquals("Location2", comboLeg.m_designatedLocation);
    }

    @Test
    public void testEqualsSameObject() {
        assertTrue(comboLeg1.equals(comboLeg1));
    }

    @Test
    public void testEqualsNullObject() {
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
    public void testEqualsDifferentConId() {
        ComboLeg differentComboLeg = new ComboLeg(2, 100, "BUY", "NYSE", ComboLeg.OPEN, 0, "Location1");
        assertFalse(comboLeg1.equals(differentComboLeg));
    }

    @Test
    public void testEqualsDifferentRatio() {
        ComboLeg differentComboLeg = new ComboLeg(1, 200, "BUY", "NYSE", ComboLeg.OPEN, 0, "Location1");
        assertFalse(comboLeg1.equals(differentComboLeg));
    }

    @Test
    public void testEqualsDifferentOpenClose() {
        ComboLeg differentComboLeg = new ComboLeg(1, 100, "BUY", "NYSE", ComboLeg.CLOSE, 0, "Location1");
        assertFalse(comboLeg1.equals(differentComboLeg));
    }

    @Test
    public void testEqualsDifferentShortSaleSlot() {
        ComboLeg differentComboLeg = new ComboLeg(1, 100, "BUY", "NYSE", ComboLeg.OPEN, 1, "Location1");
        assertFalse(comboLeg1.equals(differentComboLeg));
    }

    @Test
    public void testEqualsDifferentAction() {
        ComboLeg differentComboLeg = new ComboLeg(1, 100, "SELL", "NYSE", ComboLeg.OPEN, 0, "Location1");
        assertFalse(comboLeg1.equals(differentComboLeg));
    }

    @Test
    public void testEqualsDifferentExchange() {
        ComboLeg differentComboLeg = new ComboLeg(1, 100, "BUY", "NASDAQ", ComboLeg.OPEN, 0, "Location1");
        assertFalse(comboLeg1.equals(differentComboLeg));
    }

    @Test
    public void testEqualsDifferentDesignatedLocation() {
        ComboLeg differentComboLeg = new ComboLeg(1, 100, "BUY", "NYSE", ComboLeg.OPEN, 0, "Location2");
        assertFalse(comboLeg1.equals(differentComboLeg));
    }
}