package com.ib.client;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.ib.client.ExecutionFilter;

public class ExecutionFilterTest {

    private ExecutionFilter defaultFilter;
    private ExecutionFilter parameterizedFilter;

    @Before
    public void setUp() {
        defaultFilter = new ExecutionFilter();
        parameterizedFilter = new ExecutionFilter(1, "ACC123", "2023-10-01", "AAPL", "STK", "NASDAQ", "BUY");
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(0, defaultFilter.m_clientId);
        assertNull(defaultFilter.m_acctCode);
        assertNull(defaultFilter.m_time);
        assertNull(defaultFilter.m_symbol);
        assertNull(defaultFilter.m_secType);
        assertNull(defaultFilter.m_exchange);
        assertNull(defaultFilter.m_side);
    }

    @Test
    public void testParameterizedConstructor() {
        assertEquals(1, parameterizedFilter.m_clientId);
        assertEquals("ACC123", parameterizedFilter.m_acctCode);
        assertEquals("2023-10-01", parameterizedFilter.m_time);
        assertEquals("AAPL", parameterizedFilter.m_symbol);
        assertEquals("STK", parameterizedFilter.m_secType);
        assertEquals("NASDAQ", parameterizedFilter.m_exchange);
        assertEquals("BUY", parameterizedFilter.m_side);
    }

    @Test
    public void testEqualsSameObject() {
        assertTrue(parameterizedFilter.equals(parameterizedFilter));
    }

    @Test
    public void testEqualsNullObject() {
        assertFalse(parameterizedFilter.equals(null));
    }

    @Test
    public void testEqualsDifferentClass() {
        assertFalse(parameterizedFilter.equals("Some String"));
    }

    @Test
    public void testEqualsEqualObjects() {
        ExecutionFilter anotherFilter = new ExecutionFilter(1, "ACC123", "2023-10-01", "AAPL", "STK", "NASDAQ", "BUY");
        assertTrue(parameterizedFilter.equals(anotherFilter));
    }

    @Test
    public void testEqualsDifferentClientId() {
        ExecutionFilter anotherFilter = new ExecutionFilter(2, "ACC123", "2023-10-01", "AAPL", "STK", "NASDAQ", "BUY");
        assertFalse(parameterizedFilter.equals(anotherFilter));
    }

    @Test
    public void testEqualsDifferentAcctCode() {
        ExecutionFilter anotherFilter = new ExecutionFilter(1, "ACC456", "2023-10-01", "AAPL", "STK", "NASDAQ", "BUY");
        assertFalse(parameterizedFilter.equals(anotherFilter));
    }

    @Test
    public void testEqualsDifferentTime() {
        ExecutionFilter anotherFilter = new ExecutionFilter(1, "ACC123", "2023-10-02", "AAPL", "STK", "NASDAQ", "BUY");
        assertFalse(parameterizedFilter.equals(anotherFilter));
    }

    @Test
    public void testEqualsDifferentSymbol() {
        ExecutionFilter anotherFilter = new ExecutionFilter(1, "ACC123", "2023-10-01", "GOOG", "STK", "NASDAQ", "BUY");
        assertFalse(parameterizedFilter.equals(anotherFilter));
    }

    @Test
    public void testEqualsDifferentSecType() {
        ExecutionFilter anotherFilter = new ExecutionFilter(1, "ACC123", "2023-10-01", "AAPL", "OPT", "NASDAQ", "BUY");
        assertFalse(parameterizedFilter.equals(anotherFilter));
    }

    @Test
    public void testEqualsDifferentExchange() {
        ExecutionFilter anotherFilter = new ExecutionFilter(1, "ACC123", "2023-10-01", "AAPL", "STK", "NYSE", "BUY");
        assertFalse(parameterizedFilter.equals(anotherFilter));
    }

    @Test
    public void testEqualsDifferentSide() {
        ExecutionFilter anotherFilter = new ExecutionFilter(1, "ACC123", "2023-10-01", "AAPL", "STK", "NASDAQ", "SELL");
        assertFalse(parameterizedFilter.equals(anotherFilter));
    }
}