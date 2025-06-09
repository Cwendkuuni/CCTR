package com.google.common.base;

import com.google.common.base.Suppliers;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SuppliersTest {

    private Supplier<Integer> mockSupplier;
    private Function<Integer, String> mockFunction;

    @Before
    public void setUp() {
        mockSupplier = mock(Supplier.class);
        mockFunction = mock(Function.class);
    }

    @After
    public void tearDown() {
        mockSupplier = null;
        mockFunction = null;
    }

    @Test
    public void testCompose() {
        when(mockSupplier.get()).thenReturn(5);
        when(mockFunction.apply(5)).thenReturn("Five");

        Supplier<String> composedSupplier = Suppliers.compose(mockFunction, mockSupplier);
        assertEquals("Five", composedSupplier.get());

        verify(mockSupplier, times(1)).get();
        verify(mockFunction, times(1)).apply(5);
    }

    @Test
    public void testMemoize() {
        AtomicInteger counter = new AtomicInteger(0);
        Supplier<Integer> incrementingSupplier = () -> counter.incrementAndGet();

        Supplier<Integer> memoizedSupplier = Suppliers.memoize(incrementingSupplier);
        assertEquals(1, (int) memoizedSupplier.get());
        assertEquals(1, (int) memoizedSupplier.get());

        assertEquals(1, counter.get());
    }

    @Test
    public void testMemoizeWithExpiration() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        Supplier<Integer> incrementingSupplier = () -> counter.incrementAndGet();

        Supplier<Integer> expiringMemoizedSupplier = Suppliers.memoizeWithExpiration(incrementingSupplier, 100, TimeUnit.MILLISECONDS);
        assertEquals(1, (int) expiringMemoizedSupplier.get());
        assertEquals(1, (int) expiringMemoizedSupplier.get());

        Thread.sleep(150);
        assertEquals(2, (int) expiringMemoizedSupplier.get());
    }

    @Test
    public void testOfInstance() {
        Supplier<String> instanceSupplier = Suppliers.ofInstance("Hello");
        assertEquals("Hello", instanceSupplier.get());
    }

    @Test
    public void testSynchronizedSupplier() {
        Supplier<Integer> synchronizedSupplier = Suppliers.synchronizedSupplier(mockSupplier);
        when(mockSupplier.get()).thenReturn(10);

        assertEquals(10, (int) synchronizedSupplier.get());
        verify(mockSupplier, times(1)).get();
    }

    @Test
    public void testSupplierFunction() {
        Supplier<String> stringSupplier = Suppliers.ofInstance("Test");
        Function<Supplier<String>, String> supplierFunction = Suppliers.supplierFunction();

        assertEquals("Test", supplierFunction.apply(stringSupplier));
    }
}