package com.google.common.base;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.concurrent.TimeUnit;

public class SuppliersTest {

    private Supplier<String> supplier;
    private Function<String, Integer> function;

    @Before
    public void setUp() {
        supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "test";
            }
        };

        function = new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return input.length();
            }
        };
    }

    @Test
    public void testCompose() {
        Supplier<Integer> composedSupplier = Suppliers.compose(function, supplier);
        assertEquals(Integer.valueOf(4), composedSupplier.get());
    }

    @Test
    public void testMemoize() {
        Supplier<String> memoizedSupplier = Suppliers.memoize(supplier);
        assertEquals("test", memoizedSupplier.get());
        assertEquals("test", memoizedSupplier.get()); // Should return the same instance
    }

    @Test
    public void testMemoizeWithExpiration() {
        Supplier<String> expiringSupplier = Suppliers.memoizeWithExpiration(supplier, 1, TimeUnit.SECONDS);
        assertEquals("test", expiringSupplier.get());
        try {
            Thread.sleep(1100); // Sleep for more than 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("test", expiringSupplier.get()); // Should return a new instance
    }

    @Test
    public void testOfInstance() {
        Supplier<String> instanceSupplier = Suppliers.ofInstance("test");
        assertEquals("test", instanceSupplier.get());
    }

    @Test
    public void testSynchronizedSupplier() {
        Supplier<String> synchronizedSupplier = Suppliers.synchronizedSupplier(supplier);
        assertEquals("test", synchronizedSupplier.get());
    }

    @Test
    public void testSupplierFunction() {
        Function<Supplier<String>, String> supplierFunction = Suppliers.supplierFunction();
        assertEquals("test", supplierFunction.apply(supplier));
    }

    @Test
    public void testSupplierCompositionEquals() {
        Supplier<Integer> composedSupplier1 = Suppliers.compose(function, supplier);
        Supplier<Integer> composedSupplier2 = Suppliers.compose(function, supplier);
        assertEquals(composedSupplier1, composedSupplier2);
    }

    @Test
    public void testSupplierCompositionHashCode() {
        Supplier<Integer> composedSupplier1 = Suppliers.compose(function, supplier);
        Supplier<Integer> composedSupplier2 = Suppliers.compose(function, supplier);
        assertEquals(composedSupplier1.hashCode(), composedSupplier2.hashCode());
    }

    @Test
    public void testSupplierCompositionToString() {
        Supplier<Integer> composedSupplier = Suppliers.compose(function, supplier);
        assertTrue(composedSupplier.toString().contains("Suppliers.compose"));
    }

    @Test
    public void testMemoizingSupplierToString() {
        Supplier<String> memoizedSupplier = Suppliers.memoize(supplier);
        assertTrue(memoizedSupplier.toString().contains("Suppliers.memoize"));
    }

    @Test
    public void testExpiringMemoizingSupplierToString() {
        Supplier<String> expiringSupplier = Suppliers.memoizeWithExpiration(supplier, 1, TimeUnit.SECONDS);
        assertTrue(expiringSupplier.toString().contains("Suppliers.memoizeWithExpiration"));
    }

    @Test
    public void testSupplierOfInstanceToString() {
        Supplier<String> instanceSupplier = Suppliers.ofInstance("test");
        assertTrue(instanceSupplier.toString().contains("Suppliers.ofInstance"));
    }

    @Test
    public void testThreadSafeSupplierToString() {
        Supplier<String> synchronizedSupplier = Suppliers.synchronizedSupplier(supplier);
        assertTrue(synchronizedSupplier.toString().contains("Suppliers.synchronizedSupplier"));
    }

    @Test
    public void testSupplierFunctionToString() {
        Function<Supplier<String>, String> supplierFunction = Suppliers.supplierFunction();
        assertTrue(supplierFunction.toString().contains("Suppliers.supplierFunction"));
    }
}