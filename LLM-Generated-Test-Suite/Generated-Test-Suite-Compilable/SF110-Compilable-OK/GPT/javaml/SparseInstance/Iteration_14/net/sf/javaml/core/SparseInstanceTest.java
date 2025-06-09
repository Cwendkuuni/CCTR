package net.sf.javaml.core;

import net.sf.javaml.core.SparseInstance;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SparseInstanceTest {

    private SparseInstance instance;

    @Before
    public void setUp() {
        instance = new SparseInstance(5, 0.0);
    }

    @Test
    public void testDefaultConstructor() {
        SparseInstance defaultInstance = new SparseInstance();
        assertEquals(0, defaultInstance.noAttributes());
        assertTrue(defaultInstance.isEmpty());
    }

    @Test
    public void testConstructorWithNoAttributes() {
        SparseInstance instance = new SparseInstance(5);
        assertEquals(5, instance.noAttributes());
        assertTrue(instance.isEmpty());
    }

    @Test
    public void testConstructorWithNoAttributesAndDefaultValue() {
        SparseInstance instance = new SparseInstance(5, 1.0);
        assertEquals(5, instance.noAttributes());
        assertTrue(instance.isEmpty());
        assertEquals(1.0, instance.value(0), 0.0);
    }

    @Test
    public void testConstructorWithDataVector() {
        double[] dataVector = {1.0, 0.0, 2.0};
        SparseInstance instance = new SparseInstance(dataVector);
        assertEquals(3, instance.noAttributes());
        assertEquals(1.0, instance.value(0), 0.0);
        assertEquals(0.0, instance.value(1), 0.0);
        assertEquals(2.0, instance.value(2), 0.0);
    }

    @Test
    public void testValueMethod() {
        instance.put(0, 1.0);
        assertEquals(1.0, instance.value(0), 0.0);
        assertEquals(0.0, instance.value(1), 0.0);
    }

    @Test
    public void testClearMethod() {
        instance.put(0, 1.0);
        instance.clear();
        assertTrue(instance.isEmpty());
    }

    @Test
    public void testContainsKeyMethod() {
        instance.put(0, 1.0);
        assertTrue(instance.containsKey(0));
        assertFalse(instance.containsKey(1));
    }

    @Test
    public void testContainsValueMethod() {
        instance.put(0, 1.0);
        assertTrue(instance.containsValue(1.0));
        assertFalse(instance.containsValue(2.0));
    }

    @Test
    public void testEntrySetMethod() {
        instance.put(0, 1.0);
        Set<Map.Entry<Integer, Double>> entrySet = instance.entrySet();
        assertEquals(1, entrySet.size());
    }

    @Test
    public void testGetMethod() {
        instance.put(0, 1.0);
        assertEquals(1.0, instance.get(0), 0.0);
        assertEquals(0.0, instance.get(1), 0.0);
    }

    @Test
    public void testIsEmptyMethod() {
        assertTrue(instance.isEmpty());
        instance.put(0, 1.0);
        assertFalse(instance.isEmpty());
    }

    @Test
    public void testKeySetMethod() {
        instance.put(0, 1.0);
        instance.put(2, 2.0);
        TreeSet<Integer> keys = instance.keySet();
        assertEquals(2, keys.size());
        assertTrue(keys.contains(0));
        assertTrue(keys.contains(2));
    }

    @Test
    public void testPutMethod() {
        instance.put(0, 1.0);
        assertEquals(1.0, instance.get(0), 0.0);
    }

    @Test
    public void testPutAllMethod() {
        Map<Integer, Double> map = new HashMap<>();
        map.put(0, 1.0);
        map.put(1, 2.0);
        instance.putAll(map);
        assertEquals(1.0, instance.get(0), 0.0);
        assertEquals(2.0, instance.get(1), 0.0);
    }

    @Test
    public void testRemoveMethod() {
        instance.put(0, 1.0);
        instance.remove(0);
        assertFalse(instance.containsKey(0));
    }

    @Test
    public void testSizeMethod() {
        assertEquals(0, instance.size());
        instance.put(0, 1.0);
        assertEquals(1, instance.size());
    }

    @Test
    public void testValuesMethod() {
        instance.put(0, 1.0);
        instance.put(1, 2.0);
        Collection<Double> values = instance.values();
        assertEquals(2, values.size());
        assertTrue(values.contains(1.0));
        assertTrue(values.contains(2.0));
    }

    @Test
    public void testNoAttributesMethod() {
        assertEquals(5, instance.noAttributes());
    }

    @Test
    public void testRemoveAttributeMethod() {
        instance.put(0, 1.0);
        instance.put(1, 2.0);
        instance.removeAttribute(0);
        assertFalse(instance.containsKey(0));
        assertEquals(2.0, instance.get(0), 0.0);
    }

    @Test
    public void testToStringMethod() {
        instance.put(0, 1.0);
        String expected = "{0=1.0;null}";
        assertEquals(expected, instance.toString());
    }

    @Test
    public void testHashCodeMethod() {
        SparseInstance instance1 = new SparseInstance(5, 0.0);
        SparseInstance instance2 = new SparseInstance(5, 0.0);
        assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    @Test
    public void testEqualsMethod() {
        SparseInstance instance1 = new SparseInstance(5, 0.0);
        SparseInstance instance2 = new SparseInstance(5, 0.0);
        assertTrue(instance1.equals(instance2));
        instance2.put(0, 1.0);
        assertFalse(instance1.equals(instance2));
    }

    @Test
    public void testCopyMethod() {
        instance.put(0, 1.0);
        SparseInstance copy = (SparseInstance) instance.copy();
        assertEquals(instance, copy);
        assertNotSame(instance, copy);
    }

    @Test
    public void testRemoveAttributesMethod() {
        instance.put(0, 1.0);
        instance.put(1, 2.0);
        Set<Integer> indices = new HashSet<>();
        indices.add(0);
        instance.removeAttributes(indices);
        assertFalse(instance.containsKey(0));
        assertEquals(2.0, instance.get(0), 0.0);
    }
}