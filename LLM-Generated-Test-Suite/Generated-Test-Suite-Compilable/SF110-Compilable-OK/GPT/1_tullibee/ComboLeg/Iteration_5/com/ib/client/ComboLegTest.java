package com.ib.client;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ComboLegTest {

    @Before
    public void setUp() {
        // Mocking Util.StringCompareIgnCase method
        Mockito.mockStatic(Util.class);
        Mockito.when(Util.StringCompareIgnCase(Mockito.anyString(), Mockito.anyString()))
               .thenAnswer(invocation -> {
                   String str1 = invocation.getArgument(0);
                   String str2 = invocation.getArgument(1);
                   if (str1 == null && str2 == null) return 0;
                   if (str1 == null || str2 == null) return -1;
                   return str1.equalsIgnoreCase(str2) ? 0 : -1;
               });
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
    public void testConstructorWithAllParameters() {
        ComboLeg comboLeg = new ComboLeg(1, 2, "SELL", "NASDAQ", ComboLeg.CLOSE, 1, "Location");
        assertEquals(1, comboLeg.m_conId);
        assertEquals(2, comboLeg.m_ratio);
        assertEquals("SELL", comboLeg.m_action);
        assertEquals("NASDAQ", comboLeg.m_exchange);
        assertEquals(ComboLeg.CLOSE, comboLeg.m_openClose);
        assertEquals(1, comboLeg.m_shortSaleSlot);
        assertEquals("Location", comboLeg.m_designatedLocation);
    }

    @Test
    public void testEqualsSameObject() {
        ComboLeg comboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN);
        assertTrue(comboLeg.equals(comboLeg));
    }

    @Test
    public void testEqualsNullObject() {
        ComboLeg comboLeg = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN);
        assertFalse(comboLeg.equals(null));
    }

    @Test
    public void testEqualsDifferentObject() {
        ComboLeg comboLeg1 = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN);
        ComboLeg comboLeg2 = new ComboLeg(1, 2, "SELL", "NASDAQ", ComboLeg.CLOSE);
        assertFalse(comboLeg1.equals(comboLeg2));
    }

    @Test
    public void testEqualsEqualObjects() {
        ComboLeg comboLeg1 = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN);
        ComboLeg comboLeg2 = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN);
        assertTrue(comboLeg1.equals(comboLeg2));
    }

    @Test
    public void testEqualsDifferentCaseStrings() {
        ComboLeg comboLeg1 = new ComboLeg(1, 2, "buy", "nyse", ComboLeg.OPEN);
        ComboLeg comboLeg2 = new ComboLeg(1, 2, "BUY", "NYSE", ComboLeg.OPEN);
        assertTrue(comboLeg1.equals(comboLeg2));
    }
}