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
        Supplier<String> delegate = new Supplier<String>() {
            @Override
            public String get() {
                return "Hello";
            }
        };

        Supplier<String> memoizedSupplier = Suppliers.memoize(delegate);
        assertEquals("Hello", memoizedSupplier.get());
        assertEquals("Hello", memoizedSupplier.get()); // Should return the same value without re-evaluating
    }

    @Test
    public void testMemoizeWithExpiration() {
        Supplier<String> delegate = new Supplier<String>() {
            @Override
            public String get() {
                return "Hello";
            }
        };

        Supplier<String> memoizedSupplier = Suppliers.memoizeWithExpiration(delegate, 1, TimeUnit.SECONDS);
        assertEquals("Hello", memoizedSupplier.get());

        try {
            Thread.sleep(1100); // Sleep for more than 1 second to trigger expiration
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("Hello", memoizedSupplier.get()); // Should re-evaluate after expiration
    }

    @Test
    public void testOfInstance() {
        Supplier<String> supplier = Suppliers.ofInstance("Hello");
        assertEquals("Hello", supplier.get());
    }

    @Test
    public void testSynchronizedSupplier() {
        Supplier<String> delegate = new Supplier<String>() {
            @Override
            public String get() {
                return "Hello";
            }
        };

        Supplier<String> synchronizedSupplier = Suppliers.synchronizedSupplier(delegate);
        assertEquals("Hello", synchronizedSupplier.get());
    }

    @Test
    public void testSupplierFunction() {
        Function<Supplier<String>, String> supplierFunction = Suppliers.supplierFunction();
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "Hello";
            }
        };

        assertEquals("Hello", supplierFunction.apply(supplier));
    }
}