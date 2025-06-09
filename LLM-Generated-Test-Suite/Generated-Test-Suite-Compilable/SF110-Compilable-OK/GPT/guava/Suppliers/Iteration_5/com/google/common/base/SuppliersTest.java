package com.google.common.base;

import com.google.common.base.Suppliers;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SuppliersTest {

    private Supplier<String> stringSupplier;
    private Supplier<Integer> integerSupplier;

    @Before
    public void setUp() {
        stringSupplier = new Supplier<String>() {
            @Override
            public String get() {
                return "test";
            }
        };

        integerSupplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 42;
            }
        };
    }

    @Test
    public void testCompose() {
        Function<String, Integer> lengthFunction = new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return input.length();
            }
        };

        Supplier<Integer> composedSupplier = Suppliers.compose(lengthFunction, stringSupplier);
        assertEquals(Integer.valueOf(4), composedSupplier.get());
    }

    @Test
    public void testMemoize() {
        Supplier<String> memoizedSupplier = Suppliers.memoize(stringSupplier);
        assertEquals("test", memoizedSupplier.get());
        assertEquals("test", memoizedSupplier.get()); // Should return the same instance
    }

    @Test
    public void testMemoizeWithExpiration() throws InterruptedException {
        Supplier<String> expiringSupplier = Suppliers.memoizeWithExpiration(stringSupplier, 1, TimeUnit.SECONDS);
        assertEquals("test", expiringSupplier.get());
        Thread.sleep(1100); // Wait for expiration
        assertEquals("test", expiringSupplier.get()); // Should re-fetch after expiration
    }

    @Test
    public void testOfInstance() {
        Supplier<String> instanceSupplier = Suppliers.ofInstance("instance");
        assertEquals("instance", instanceSupplier.get());
    }

    @Test
    public void testSynchronizedSupplier() {
        Supplier<String> synchronizedSupplier = Suppliers.synchronizedSupplier(stringSupplier);
        assertEquals("test", synchronizedSupplier.get());
    }

    @Test
    public void testSupplierFunction() {
        Function<Supplier<String>, String> supplierFunction = Suppliers.supplierFunction();
        assertEquals("test", supplierFunction.apply(stringSupplier));
    }

    @After
    public void tearDown() {
        stringSupplier = null;
        integerSupplier = null;
    }
}