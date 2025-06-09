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

    private Supplier<String> mockSupplier;
    private Function<String, Integer> mockFunction;

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
        when(mockSupplier.get()).thenReturn("42");
        when(mockFunction.apply("42")).thenReturn(42);

        Supplier<Integer> composedSupplier = Suppliers.compose(mockFunction, mockSupplier);
        assertEquals(Integer.valueOf(42), composedSupplier.get());

        verify(mockSupplier, times(1)).get();
        verify(mockFunction, times(1)).apply("42");
    }

    @Test
    public void testMemoize() {
        AtomicInteger counter = new AtomicInteger(0);
        Supplier<Integer> countingSupplier = () -> counter.incrementAndGet();

        Supplier<Integer> memoizedSupplier = Suppliers.memoize(countingSupplier);
        assertEquals(Integer.valueOf(1), memoizedSupplier.get());
        assertEquals(Integer.valueOf(1), memoizedSupplier.get());

        assertEquals(1, counter.get());
    }

    @Test
    public void testMemoizeWithExpiration() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        Supplier<Integer> countingSupplier = () -> counter.incrementAndGet();

        Supplier<Integer> expiringSupplier = Suppliers.memoizeWithExpiration(countingSupplier, 100, TimeUnit.MILLISECONDS);
        assertEquals(Integer.valueOf(1), expiringSupplier.get());
        assertEquals(Integer.valueOf(1), expiringSupplier.get());

        assertEquals(1, counter.get());

        Thread.sleep(150);

        assertEquals(Integer.valueOf(2), expiringSupplier.get());
        assertEquals(2, counter.get());
    }

    @Test
    public void testOfInstance() {
        Supplier<String> instanceSupplier = Suppliers.ofInstance("test");
        assertEquals("test", instanceSupplier.get());
    }

    @Test
    public void testSynchronizedSupplier() {
        Supplier<String> synchronizedSupplier = Suppliers.synchronizedSupplier(mockSupplier);
        when(mockSupplier.get()).thenReturn("synchronized");

        assertEquals("synchronized", synchronizedSupplier.get());
        verify(mockSupplier, times(1)).get();
    }

    @Test
    public void testSupplierFunction() {
        Supplier<String> instanceSupplier = Suppliers.ofInstance("functionTest");
        Function<Supplier<String>, String> supplierFunction = Suppliers.supplierFunction();

        assertEquals("functionTest", supplierFunction.apply(instanceSupplier));
    }
}