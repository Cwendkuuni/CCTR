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

    private Supplier<Integer> integerSupplier;
    private Function<Integer, String> intToStringFunction;

    @Before
    public void setUp() {
        integerSupplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 42;
            }
        };

        intToStringFunction = new Function<Integer, String>() {
            @Override
            public String apply(Integer input) {
                return input.toString();
            }
        };
    }

    @After
    public void tearDown() {
        integerSupplier = null;
        intToStringFunction = null;
    }

    @Test
    public void testCompose() {
        Supplier<String> composedSupplier = Suppliers.compose(intToStringFunction, integerSupplier);
        assertEquals("42", composedSupplier.get());
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
        assertEquals(Integer.valueOf(1), memoizedSupplier.get());
        assertEquals(Integer.valueOf(1), memoizedSupplier.get());
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

        Supplier<Integer> expiringSupplier = Suppliers.memoizeWithExpiration(countingSupplier, 100, TimeUnit.MILLISECONDS);
        assertEquals(Integer.valueOf(1), expiringSupplier.get());
        assertEquals(Integer.valueOf(1), expiringSupplier.get());
        Thread.sleep(150);
        assertEquals(Integer.valueOf(2), expiringSupplier.get());
    }

    @Test
    public void testOfInstance() {
        Supplier<String> instanceSupplier = Suppliers.ofInstance("test");
        assertEquals("test", instanceSupplier.get());
    }

    @Test
    public void testSynchronizedSupplier() {
        Supplier<Integer> synchronizedSupplier = Suppliers.synchronizedSupplier(integerSupplier);
        assertEquals(Integer.valueOf(42), synchronizedSupplier.get());
    }

    @Test
    public void testSupplierFunction() {
        Function<Supplier<Integer>, Integer> supplierFunction = Suppliers.supplierFunction();
        assertEquals(Integer.valueOf(42), supplierFunction.apply(integerSupplier));
    }
}