package com.ib.client;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExecutionFilterTest {

    private ExecutionFilter executionFilter;
    private ExecutionFilter anotherExecutionFilter;

    @Before
    public void setUp() {
        executionFilter = new ExecutionFilter(1, "ACCT123", "2023-10-01T10:00:00", "AAPL", "STK", "NYSE", "BUY");
        anotherExecutionFilter = new ExecutionFilter(1, "ACCT123", "2023-10-01T10:00:00", "AAPL", "STK", "NYSE", "BUY");
    }

    @Test
    public void testDefaultConstructor() {
        ExecutionFilter defaultFilter = new ExecutionFilter();
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
        assertEquals(1, executionFilter.m_clientId);
        assertEquals("ACCT123", executionFilter.m_acctCode);
        assertEquals("2023-10-01T10:00:00", executionFilter.m_time);
        assertEquals("AAPL", executionFilter.m_symbol);
        assertEquals("STK", executionFilter.m_secType);
        assertEquals("NYSE", executionFilter.m_exchange);
        assertEquals("BUY", executionFilter.m_side);
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(executionFilter.equals(executionFilter));
    }

    @Test
    public void testEquals_DifferentObjectSameValues() {
        assertTrue(executionFilter.equals(anotherExecutionFilter));
    }

    @Test
    public void testEquals_DifferentClientId() {
        ExecutionFilter differentClientIdFilter = new ExecutionFilter(2, "ACCT123", "2023-10-01T10:00:00", "AAPL", "STK", "NYSE", "BUY");
        assertFalse(executionFilter.equals(differentClientIdFilter));
    }

    @Test
    public void testEquals_DifferentAcctCode() {
        ExecutionFilter differentAcctCodeFilter = new ExecutionFilter(1, "ACCT456", "2023-10-01T10:00:00", "AAPL", "STK", "NYSE", "BUY");
        assertFalse(executionFilter.equals(differentAcctCodeFilter));
    }

    @Test
    public void testEquals_DifferentTime() {
        ExecutionFilter differentTimeFilter = new ExecutionFilter(1, "ACCT123", "2023-10-01T11:00:00", "AAPL", "STK", "NYSE", "BUY");
        assertFalse(executionFilter.equals(differentTimeFilter));
    }

    @Test
    public void testEquals_DifferentSymbol() {
        ExecutionFilter differentSymbolFilter = new ExecutionFilter(1, "ACCT123", "2023-10-01T10:00:00", "GOOG", "STK", "NYSE", "BUY");
        assertFalse(executionFilter.equals(differentSymbolFilter));
    }

    @Test
    public void testEquals_DifferentSecType() {
        ExecutionFilter differentSecTypeFilter = new ExecutionFilter(1, "ACCT123", "2023-10-01T10:00:00", "AAPL", "OPT", "NYSE", "BUY");
        assertFalse(executionFilter.equals(differentSecTypeFilter));
    }

    @Test
    public void testEquals_DifferentExchange() {
        ExecutionFilter differentExchangeFilter = new ExecutionFilter(1, "ACCT123", "2023-10-01T10:00:00", "AAPL", "STK", "NASDAQ", "BUY");
        assertFalse(executionFilter.equals(differentExchangeFilter));
    }

    @Test
    public void testEquals_DifferentSide() {
        ExecutionFilter differentSideFilter = new ExecutionFilter(1, "ACCT123", "2023-10-01T10:00:00", "AAPL", "STK", "NYSE", "SELL");
        assertFalse(executionFilter.equals(differentSideFilter));
    }

    @Test
    public void testEquals_NullObject() {
        assertFalse(executionFilter.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(executionFilter.equals(new Object()));
    }
}