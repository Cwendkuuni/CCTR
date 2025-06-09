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
    public void testConstructorWithFiveArgs() {
        ComboLeg comboLegFiveArgs = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN);
        assertEquals(1, comboLegFiveArgs.m_conId);
        assertEquals(2, comboLegFiveArgs.m_ratio);
        assertEquals("BUY", comboLegFiveArgs.m_action);
        assertEquals("NYSE", comboLegFiveArgs.m_exchange);
        assertEquals(ComboLeg.OPEN, comboLegFiveArgs.m_openClose);
        assertEquals(0, comboLegFiveArgs.m_shortSaleSlot);
        assertNull(comboLegFiveArgs.m_designatedLocation);
    }

    @Test
    public void testConstructorWithSevenArgs() {
        assertEquals(1, comboLeg.m_conId);
        assertEquals(2, comboLeg.m_ratio);
        assertEquals("BUY", comboLeg.m_action);
        assertEquals("NYSE", comboLeg.m_exchange);
        assertEquals(ComboLeg.OPEN, comboLeg.m_openClose);
        assertEquals(1, comboLeg.m_shortSaleSlot);
        assertEquals("Location1", comboLeg.m_designatedLocation);
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(comboLeg.equals(comboLeg));
    }

    @Test
    public void testEquals_NullObject() {
        assertFalse(comboLeg.equals(null));
    }

    @Test
    public void testEquals_DifferentObject() {
        assertFalse(comboLeg.equals(new Object()));
    }

    @Test
    public void testEquals_SameValues() {
        ComboLeg anotherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN, 1, "Location1");
        assertTrue(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEquals_DifferentConId() {
        ComboLeg anotherComboLeg = new ComboLeg(2, 2, "BUY", "NYSE", ComboLeg.OPEN, 1, "Location1");
        assertFalse(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEquals_DifferentRatio() {
        ComboLeg anotherComboLeg = new ComboLeg(1, 3, "BUY", "NYSE", ComboLeg.OPEN, 1, "Location1");
        assertFalse(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEquals_DifferentAction() {
        ComboLeg anotherComboLeg = new ComboLeg(1, 2, "SELL", "NYSE", ComboLeg.OPEN, 1, "Location1");
        assertFalse(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEquals_DifferentExchange() {
        ComboLeg anotherComboLeg = new ComboLeg(1, 2, "BUY", "NASDAQ", ComboLeg.OPEN, 1, "Location1");
        assertFalse(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEquals_DifferentOpenClose() {
        ComboLeg anotherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.CLOSE, 1, "Location1");
        assertFalse(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEquals_DifferentShortSaleSlot() {
        ComboLeg anotherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN, 2, "Location1");
        assertFalse(comboLeg.equals(anotherComboLeg));
    }

    @Test
    public void testEquals_DifferentDesignatedLocation() {
        ComboLeg anotherComboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN, 1, "Location2");
        assertFalse(comboLeg.equals(anotherComboLeg));
    }
}