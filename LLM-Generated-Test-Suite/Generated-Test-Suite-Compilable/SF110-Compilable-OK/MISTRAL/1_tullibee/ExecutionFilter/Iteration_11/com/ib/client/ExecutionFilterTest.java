package com.ib.client;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExecutionFilterTest {

    @Test
    public void testDefaultConstructor() {
        ExecutionFilter filter = new ExecutionFilter();
        assertEquals(0, filter.m_clientId);
        assertNull(filter.m_acctCode);
        assertNull(filter.m_time);
        assertNull(filter.m_symbol);
        assertNull(filter.m_secType);
        assertNull(filter.m_exchange);
        assertNull(filter.m_side);
    }

    @Test
    public void testParameterizedConstructor() {
        int clientId = 1;
        String acctCode = "ACCT123";
        String time = "2023-10-01T10:00:00";
        String symbol = "AAPL";
        String secType = "STK";
        String exchange = "NYSE";
        String side = "BUY";

        ExecutionFilter filter = new ExecutionFilter(clientId, acctCode, time, symbol, secType, exchange, side);

        assertEquals(clientId, filter.m_clientId);
        assertEquals(acctCode, filter.m_acctCode);
        assertEquals(time, filter.m_time);
        assertEquals(symbol, filter.m_symbol);
        assertEquals(secType, filter.m_secType);
        assertEquals(exchange, filter.m_exchange);
        assertEquals(side, filter.m_side);
    }

    @Test
    public void testEquals_SameObject() {
        ExecutionFilter filter = new ExecutionFilter();
        assertTrue(filter.equals(filter));
    }

    @Test
    public void testEquals_DifferentObjectNull() {
        ExecutionFilter filter = new ExecutionFilter();
        assertFalse(filter.equals(null));
    }

    @Test
    public void testEquals_DifferentObjectDifferentClass() {
        ExecutionFilter filter = new ExecutionFilter();
        assertFalse(filter.equals("Not an ExecutionFilter"));
    }

    @Test
    public void testEquals_DifferentObjectSameValues() {
        ExecutionFilter filter1 = new ExecutionFilter(1, "ACCT123", "2023-10-01T10:00:00", "AAPL", "STK", "NYSE", "BUY");
        ExecutionFilter filter2 = new ExecutionFilter(1, "ACCT123", "2023-10-01T10:00:00", "AAPL", "STK", "NYSE", "BUY");
        assertTrue(filter1.equals(filter2));
    }

    @Test
    public void testEquals_DifferentObjectDifferentValues() {
        ExecutionFilter filter1 = new ExecutionFilter(1, "ACCT123", "2023-10-01T10:00:00", "AAPL", "STK", "NYSE", "BUY");
        ExecutionFilter filter2 = new ExecutionFilter(2, "ACCT456", "2023-10-01T11:00:00", "GOOG", "STK", "NASDAQ", "SELL");
        assertFalse(filter1.equals(filter2));
    }

    @Test
    public void testEquals_CaseInsensitive() {
        ExecutionFilter filter1 = new ExecutionFilter(1, "ACCT123", "2023-10-01T10:00:00", "AAPL", "STK", "NYSE", "BUY");
        ExecutionFilter filter2 = new ExecutionFilter(1, "acct123", "2023-10-01T10:00:00", "aapl", "stk", "nyse", "buy");
        assertTrue(filter1.equals(filter2));
    }
}