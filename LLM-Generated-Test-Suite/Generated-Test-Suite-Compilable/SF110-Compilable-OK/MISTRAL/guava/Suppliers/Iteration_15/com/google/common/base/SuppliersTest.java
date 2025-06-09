package com.google.common.base;

import static org.junit.Assert.*;
import org.junit.Test;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.util.concurrent.TimeUnit;

public class SuppliersTest {

    @Test
    public void testCompose() {
        Function<Integer, String> function = new Function<Integer, String>() {
            @Override
            public String apply(Integer input) {
                return input.toString();
            }
        };

        Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 42;
            }
        };

        Supplier<String> composedSupplier = Suppliers.compose(function, supplier);
        assertEquals("42", composedSupplier.get());
    }

    @Test
    public void testMemoize() {
        Supplier<Integer> delegate = new Supplier<Integer>() {
            private int count = 0;

            @Override
            public Integer get() {
                return count++;
            }
        };

        Supplier<Integer> memoizedSupplier = Suppliers.memoize(delegate);
        assertEquals(0, (int) memoizedSupplier.get());
        assertEquals(0, (int) memoizedSupplier.get()); // Should still be 0 due to memoization
    }

    @Test
    public void testMemoizeWithExpiration() {
        Supplier<Integer> delegate = new Supplier<Integer>() {
            private int count = 0;

            @Override
            public Integer get() {
                return count++;
            }
        };

        Supplier<Integer> expiringMemoizedSupplier = Suppliers.memoizeWithExpiration(delegate, 1, TimeUnit.SECONDS);
        assertEquals(0, (int) expiringMemoizedSupplier.get());
        assertEquals(0, (int) expiringMemoizedSupplier.get()); // Should still be 0 due to memoization

        try {
            Thread.sleep(1000); // Wait for expiration
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(1, (int) expiringMemoizedSupplier.get()); // Should be 1 after expiration
    }

    @Test
    public void testOfInstance() {
        Supplier<String> supplier = Suppliers.ofInstance("test");
        assertEquals("test", supplier.get());
    }

    @Test
    public void testSynchronizedSupplier() {
        Supplier<Integer> delegate = new Supplier<Integer>() {
            private int count = 0;

            @Override
            public synchronized Integer get() {
                return count++;
            }
        };

        Supplier<Integer> synchronizedSupplier = Suppliers.synchronizedSupplier(delegate);
        assertEquals(0, (int) synchronizedSupplier.get());
        assertEquals(1, (int) synchronizedSupplier.get());
    }

    @Test
    public void testSupplierFunction() {
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "test";
            }
        };

        Function<Supplier<String>, String> supplierFunction = Suppliers.supplierFunction();
        assertEquals("test", supplierFunction.apply(supplier));
    }
}