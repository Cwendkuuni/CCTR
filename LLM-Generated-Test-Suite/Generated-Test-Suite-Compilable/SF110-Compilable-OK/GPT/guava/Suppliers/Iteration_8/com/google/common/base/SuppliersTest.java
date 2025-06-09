package com.google.common.base;

import com.google.common.base.Suppliers;
import com.google.common.base.Supplier;
import com.google.common.base.Function;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;

import java.util.concurrent.TimeUnit;

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

    @After
    public void tearDown() {
        stringSupplier = null;
        integerSupplier = null;
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
        Assert.assertEquals(Integer.valueOf(4), composedSupplier.get());
    }

    @Test
    public void testMemoize() {
        Supplier<String> memoizedSupplier = Suppliers.memoize(stringSupplier);
        Assert.assertEquals("test", memoizedSupplier.get());
        Assert.assertEquals("test", memoizedSupplier.get()); // Should return the same instance
    }

    @Test
    public void testMemoizeWithExpiration() throws InterruptedException {
        Supplier<String> expiringSupplier = Suppliers.memoizeWithExpiration(stringSupplier, 1, TimeUnit.SECONDS);
        Assert.assertEquals("test", expiringSupplier.get());
        Thread.sleep(1100); // Wait for expiration
        Assert.assertEquals("test", expiringSupplier.get()); // Should re-fetch after expiration
    }

    @Test
    public void testOfInstance() {
        Supplier<String> instanceSupplier = Suppliers.ofInstance("instance");
        Assert.assertEquals("instance", instanceSupplier.get());
    }

    @Test
    public void testSynchronizedSupplier() {
        Supplier<String> synchronizedSupplier = Suppliers.synchronizedSupplier(stringSupplier);
        Assert.assertEquals("test", synchronizedSupplier.get());
    }

    @Test
    public void testSupplierFunction() {
        Function<Supplier<String>, String> supplierFunction = Suppliers.supplierFunction();
        Assert.assertEquals("test", supplierFunction.apply(stringSupplier));
    }
}