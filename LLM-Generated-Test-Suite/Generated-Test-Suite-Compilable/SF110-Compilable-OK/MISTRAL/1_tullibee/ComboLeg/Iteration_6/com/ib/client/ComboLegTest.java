package com.ib.client;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ComboLegTest {

    private ComboLeg comboLeg;

    @Before
    public void setUp() {
        comboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN, 1, "Location1");
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
    public void testConstructorWithFiveParameters() {
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
    public void testConstructorWithSevenParameters() {
        assertEquals(1, comboLeg.m_conId);
        assertEquals(2, comboLeg.m_ratio);
        assertEquals("BUY", comboLeg.m_action);
        assertEquals("NYSE", comboLeg.m_exchange);
        assertEquals(ComboLeg.OPEN, comboLeg.m_openClose);
        assertEquals(1, comboLeg.m_shortSaleSlot);
        assertEquals("Location1", comboLeg.m_designatedLocation);
    }

    @Test
    public void testEquals() {
        ComboLeg otherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN, 1, "Location1");
        assertTrue(comboLeg.equals(otherComboLeg));

        otherComboLeg = new ComboLeg(2, 2, "BUY", "NYSE", ComboLeg.OPEN, 1, "Location1");
        assertFalse(comboLeg.equals(otherComboLeg));

        otherComboLeg = new ComboLeg(1, 3, "BUY", "NYSE", ComboLeg.OPEN, 1, "Location1");
        assertFalse(comboLeg.equals(otherComboLeg));

        otherComboLeg = new ComboLeg(1, 2, "SELL", "NYSE", ComboLeg.OPEN, 1, "Location1");
        assertFalse(comboLeg.equals(otherComboLeg));

        otherComboLeg = new ComboLeg(1, 2, "BUY", "NASDAQ", ComboLeg.OPEN, 1, "Location1");
        assertFalse(comboLeg.equals(otherComboLeg));

        otherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.CLOSE, 1, "Location1");
        assertFalse(comboLeg.equals(otherComboLeg));

        otherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN, 2, "Location1");
        assertFalse(comboLeg.equals(otherComboLeg));

        otherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN, 1, "Location2");
        assertFalse(comboLeg.equals(otherComboLeg));
    }

    @Test
    public void testEqualsWithNull() {
        assertFalse(comboLeg.equals(null));
    }

    @Test
    public void testEqualsWithSameObject() {
        assertTrue(comboLeg.equals(comboLeg));
    }
}