package com.google.common.base;

import com.google.common.base.Suppliers;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.concurrent.TimeUnit;

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

    @Test(expected = NullPointerException.class)
    public void testCompose_NullFunction() {
        Suppliers.compose(null, mockSupplier);
    }

    @Test(expected = NullPointerException.class)
    public void testCompose_NullSupplier() {
        Suppliers.compose(mockFunction, null);
    }

    @Test
    public void testCompose() {
        when(mockSupplier.get()).thenReturn("42");
        when(mockFunction.apply("42")).thenReturn(42);

        Supplier<Integer> composedSupplier = Suppliers.compose(mockFunction, mockSupplier);
        assertEquals(Integer.valueOf(42), composedSupplier.get());
    }

    @Test
    public void testMemoize() {
        when(mockSupplier.get()).thenReturn("memoized");

        Supplier<String> memoizedSupplier = Suppliers.memoize(mockSupplier);
        assertEquals("memoized", memoizedSupplier.get());
        assertEquals("memoized", memoizedSupplier.get());

        verify(mockSupplier, times(1)).get();
    }

    @Test
    public void testMemoizeWithExpiration() throws InterruptedException {
        when(mockSupplier.get()).thenReturn("expiring");

        Supplier<String> expiringSupplier = Suppliers.memoizeWithExpiration(mockSupplier, 1, TimeUnit.SECONDS);
        assertEquals("expiring", expiringSupplier.get());

        Thread.sleep(1100); // Sleep to ensure expiration
        assertEquals("expiring", expiringSupplier.get());

        verify(mockSupplier, times(2)).get();
    }

    @Test
    public void testOfInstance() {
        Supplier<String> instanceSupplier = Suppliers.ofInstance("instance");
        assertEquals("instance", instanceSupplier.get());
    }

    @Test
    public void testSynchronizedSupplier() {
        when(mockSupplier.get()).thenReturn("synchronized");

        Supplier<String> synchronizedSupplier = Suppliers.synchronizedSupplier(mockSupplier);
        assertEquals("synchronized", synchronizedSupplier.get());

        verify(mockSupplier, times(1)).get();
    }

    @Test
    public void testSupplierFunction() {
        Supplier<String> instanceSupplier = Suppliers.ofInstance("function");
        Function<Supplier<String>, String> supplierFunction = Suppliers.supplierFunction();

        assertEquals("function", supplierFunction.apply(instanceSupplier));
    }
}