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

public class SuppliersTest {

    private Supplier<Integer> supplier;
    private Function<Integer, String> function;

    @Before
    public void setUp() {
        supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 42;
            }
        };

        function = new Function<Integer, String>() {
            @Override
            public String apply(Integer input) {
                return "Number: " + input;
            }
        };
    }

    @After
    public void tearDown() {
        supplier = null;
        function = null;
    }

    @Test
    public void testCompose() {
        Supplier<String> composedSupplier = Suppliers.compose(function, supplier);
        assertEquals("Number: 42", composedSupplier.get());
    }

    @Test
    public void testMemoize() {
        AtomicInteger counter = new AtomicInteger(0);
        Supplier<Integer> countingSupplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return counter.incrementAndGet();
            }
        };

        Supplier<Integer> memoizedSupplier = Suppliers.memoize(countingSupplier);
        assertEquals(1, (int) memoizedSupplier.get());
        assertEquals(1, (int) memoizedSupplier.get());
    }

    @Test
    public void testMemoizeWithExpiration() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        Supplier<Integer> countingSupplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return counter.incrementAndGet();
            }
        };

        Supplier<Integer> expiringSupplier = Suppliers.memoizeWithExpiration(countingSupplier, 1, TimeUnit.SECONDS);
        assertEquals(1, (int) expiringSupplier.get());
        assertEquals(1, (int) expiringSupplier.get());
        Thread.sleep(1100);
        assertEquals(2, (int) expiringSupplier.get());
    }

    @Test
    public void testOfInstance() {
        Supplier<String> instanceSupplier = Suppliers.ofInstance("Hello");
        assertEquals("Hello", instanceSupplier.get());
    }

    @Test
    public void testSynchronizedSupplier() {
        Supplier<Integer> synchronizedSupplier = Suppliers.synchronizedSupplier(supplier);
        assertEquals(42, (int) synchronizedSupplier.get());
    }

    @Test
    public void testSupplierFunction() {
        Function<Supplier<Integer>, Integer> supplierFunction = Suppliers.supplierFunction();
        assertEquals(42, (int) supplierFunction.apply(supplier));
    }
}