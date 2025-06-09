package com.ib.client;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ComboLegTest {

    private ComboLeg comboLeg;

    @Before
    public void setUp() {
        comboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN, 1, "LocationA");
    }

    @Test
    public void testDefaultConstructor() {
        ComboLeg defaultComboLeg = new ComboLeg();
        assertEquals(0, defaultComboLeg.m_conId);
        assertEquals(0, defaultComboLeg.m_ratio);
        assertNull(defaultComboLeg.m_action);
        assertNull(defaultComboLeg.m_exchange);
        assertEquals(0, defaultComboLeg.m_openClose);
        assertEquals(0, defaultComboLeg.m_shortSaleSlot);
        assertNull(defaultComboLeg.m_designatedLocation);
    }

    @Test
    public void testConstructorWithoutOptionalFields() {
        ComboLeg comboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN);
        assertEquals(1, comboLeg.m_conId);
        assertEquals(2, comboLeg.m_ratio);
        assertEquals("BUY", comboLeg.m_action);
        assertEquals("NYSE", comboLeg.m_exchange);
        assertEquals(ComboLeg.OPEN, comboLeg.m_openClose);
        assertEquals(0, comboLeg.m_shortSaleSlot);
        assertNull(comboLeg.m_designatedLocation);
    }

    @Test
    public void testConstructorWithAllFields() {
        assertEquals(1, comboLeg.m_conId);
        assertEquals(2, comboLeg.m_ratio);
        assertEquals("BUY", comboLeg.m_action);
        assertEquals("NYSE", comboLeg.m_exchange);
        assertEquals(ComboLeg.OPEN, comboLeg.m_openClose);
        assertEquals(1, comboLeg.m_shortSaleSlot);
        assertEquals("LocationA", comboLeg.m_designatedLocation);
    }

    @Test
    public void testEqualsSameObject() {
        assertTrue(comboLeg.equals(comboLeg));
    }

    @Test
    public void testEqualsDifferentObject() {
        ComboLeg anotherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN, 1, "LocationA");
        assertTrue(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEqualsDifferentConId() {
        ComboLeg anotherComboLeg = new ComboLeg(2, 2, "BUY", "NYSE", ComboLeg.OPEN, 1, "LocationA");
        assertFalse(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEqualsDifferentRatio() {
        ComboLeg anotherComboLeg = new ComboLeg(1, 3, "BUY", "NYSE", ComboLeg.OPEN, 1, "LocationA");
        assertFalse(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEqualsDifferentAction() {
        ComboLeg anotherComboLeg = new ComboLeg(1, 2, "SELL", "NYSE", ComboLeg.OPEN, 1, "LocationA");
        assertFalse(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEqualsDifferentExchange() {
        ComboLeg anotherComboLeg = new ComboLeg(1, 2, "BUY", "NASDAQ", ComboLeg.OPEN, 1, "LocationA");
        assertFalse(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEqualsDifferentOpenClose() {
        ComboLeg anotherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.CLOSE, 1, "LocationA");
        assertFalse(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEqualsDifferentShortSaleSlot() {
        ComboLeg anotherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN, 2, "LocationA");
        assertFalse(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEqualsDifferentDesignatedLocation() {
        ComboLeg anotherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN, 1, "LocationB");
        assertFalse(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEqualsNullObject() {
        assertFalse(comboLeg.equals(null));
    }
}