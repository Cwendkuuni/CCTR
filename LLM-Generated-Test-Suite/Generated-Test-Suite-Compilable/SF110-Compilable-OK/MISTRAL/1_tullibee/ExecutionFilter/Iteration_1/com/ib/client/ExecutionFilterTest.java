package com.ib.client;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExecutionFilterTest {

    private ExecutionFilter defaultFilter;
    private ExecutionFilter customFilter;

    @Before
    public void setUp() {
        defaultFilter = new ExecutionFilter();
        customFilter = new ExecutionFilter(1, "ACCT123", "2023-10-01 10:00:00", "AAPL", "STK", "NYSE", "BUY");
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
        assertEquals(1, customFilter.m_clientId);
        assertEquals("ACCT123", customFilter.m_acctCode);
        assertEquals("2023-10-01 10:00:00", customFilter.m_time);
        assertEquals("AAPL", customFilter.m_symbol);
        assertEquals("STK", customFilter.m_secType);
        assertEquals("NYSE", customFilter.m_exchange);
        assertEquals("BUY", customFilter.m_side);
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(customFilter.equals(customFilter));
    }

    @Test
    public void testEquals_DifferentObject() {
        ExecutionFilter anotherFilter = new ExecutionFilter(1, "ACCT123", "2023-10-01 10:00:00", "AAPL", "STK", "NYSE", "BUY");
        assertTrue(customFilter.equals(anotherFilter));
    }

    @Test
    public void testEquals_DifferentClientId() {
        ExecutionFilter anotherFilter = new ExecutionFilter(2, "ACCT123", "2023-10-01 10:00:00", "AAPL", "STK", "NYSE", "BUY");
        assertFalse(customFilter.equals(anotherFilter));
    }

    @Test
    public void testEquals_DifferentAcctCode() {
        ExecutionFilter anotherFilter = new ExecutionFilter(1, "ACCT456", "2023-10-01 10:00:00", "AAPL", "STK", "NYSE", "BUY");
        assertFalse(customFilter.equals(anotherFilter));
    }

    @Test
    public void testEquals_DifferentTime() {
        ExecutionFilter anotherFilter = new ExecutionFilter(1, "ACCT123", "2023-10-01 11:00:00", "AAPL", "STK", "NYSE", "BUY");
        assertFalse(customFilter.equals(anotherFilter));
    }

    @Test
    public void testEquals_DifferentSymbol() {
        ExecutionFilter anotherFilter = new ExecutionFilter(1, "ACCT123", "2023-10-01 10:00:00", "GOOG", "STK", "NYSE", "BUY");
        assertFalse(customFilter.equals(anotherFilter));
    }

    @Test
    public void testEquals_DifferentSecType() {
        ExecutionFilter anotherFilter = new ExecutionFilter(1, "ACCT123", "2023-10-01 10:00:00", "AAPL", "OPT", "NYSE", "BUY");
        assertFalse(customFilter.equals(anotherFilter));
    }

    @Test
    public void testEquals_DifferentExchange() {
        ExecutionFilter anotherFilter = new ExecutionFilter(1, "ACCT123", "2023-10-01 10:00:00", "AAPL", "STK", "NASDAQ", "BUY");
        assertFalse(customFilter.equals(anotherFilter));
    }

    @Test
    public void testEquals_DifferentSide() {
        ExecutionFilter anotherFilter = new ExecutionFilter(1, "ACCT123", "2023-10-01 10:00:00", "AAPL", "STK", "NYSE", "SELL");
        assertFalse(customFilter.equals(anotherFilter));
    }

    @Test
    public void testEquals_NullObject() {
        assertFalse(customFilter.equals(null));
    }
}