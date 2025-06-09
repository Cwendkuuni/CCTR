package net.sf.javaml.core;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class SparseInstanceTest {

    private SparseInstance instance;

    @Before
    public void setUp() {
        instance = new SparseInstance(5, 0.0);
    }

    @Test
    public void testDefaultConstructor() {
        SparseInstance defaultInstance = new SparseInstance();
        assertEquals(-1, defaultInstance.noAttributes());
        assertEquals(0.0, defaultInstance.value(0), 0.0);
    }

    @Test
    public void testConstructorWithAttributes() {
        SparseInstance instanceWithAttributes = new SparseInstance(10);
        assertEquals(10, instanceWithAttributes.noAttributes());
    }

    @Test
    public void testConstructorWithAttributesAndDefaultValue() {
        SparseInstance instanceWithDefaultValue = new SparseInstance(10, 1.0);
        assertEquals(10, instanceWithDefaultValue.noAttributes());
        assertEquals(1.0, instanceWithDefaultValue.value(0), 0.0);
    }

    @Test
    public void testConstructorWithDataVector() {
        double[] dataVector = {1.0, 0.0, 2.0};
        SparseInstance instanceWithDataVector = new SparseInstance(dataVector);
        assertEquals(3, instanceWithDataVector.noAttributes());
        assertEquals(1.0, instanceWithDataVector.value(0), 0.0);
        assertEquals(0.0, instanceWithDataVector.value(1), 0.0);
        assertEquals(2.0, instanceWithDataVector.value(2), 0.0);
    }

    @Test
    public void testValue() {
        instance.put(0, 1.0);
        assertEquals(1.0, instance.value(0), 0.0);
        assertEquals(0.0, instance.value(1), 0.0);
    }

    @Test
    public void testClear() {
        instance.put(0, 1.0);
        instance.clear();
        assertTrue(instance.isEmpty());
    }

    @Test
    public void testContainsKey() {
        instance.put(0, 1.0);
        assertTrue(instance.containsKey(0));
        assertFalse(instance.containsKey(1));
    }

    @Test
    public void testContainsValue() {
        instance.put(0, 1.0);
        assertTrue(instance.containsValue(1.0));
        assertFalse(instance.containsValue(0.0));
    }

    @Test
    public void testEntrySet() {
        instance.put(0, 1.0);
        Set<Map.Entry<Integer, Double>> entrySet = instance.entrySet();
        assertEquals(1, entrySet.size());
    }

    @Test
    public void testGet() {
        instance.put(0, 1.0);
        assertEquals(1.0, instance.get(0), 0.0);
        assertEquals(0.0, instance.get(1), 0.0);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(instance.isEmpty());
        instance.put(0, 1.0);
        assertFalse(instance.isEmpty());
    }

    @Test
    public void testKeySet() {
        instance.put(0, 1.0);
        instance.put(2, 2.0);
        Set<Integer> keys = instance.keySet();
        assertEquals(2, keys.size());
        assertTrue(keys.contains(0));
        assertTrue(keys.contains(2));
    }

    @Test
    public void testPut() {
        instance.put(0, 1.0);
        assertEquals(1.0, instance.get(0), 0.0);
    }

    @Test
    public void testPutAll() {
        Map<Integer, Double> map = new HashMap<>();
        map.put(0, 1.0);
        map.put(2, 2.0);
        instance.putAll(map);
        assertEquals(1.0, instance.get(0), 0.0);
        assertEquals(2.0, instance.get(2), 0.0);
    }

    @Test
    public void testRemove() {
        instance.put(0, 1.0);
        instance.remove(0);
        assertFalse(instance.containsKey(0));
    }

    @Test
    public void testSize() {
        assertEquals(0, instance.size());
        instance.put(0, 1.0);
        assertEquals(1, instance.size());
    }

    @Test
    public void testValues() {
        instance.put(0, 1.0);
        Collection<Double> values = instance.values();
        assertEquals(1, values.size());
        assertTrue(values.contains(1.0));
    }

    @Test
    public void testNoAttributes() {
        assertEquals(5, instance.noAttributes());
    }

    @Test
    public void testRemoveAttribute() {
        instance.put(0, 1.0);
        instance.put(1, 2.0);
        instance.removeAttribute(0);
        assertFalse(instance.containsKey(0));
        assertEquals(2.0, instance.get(0), 0.0);
    }

    @Test
    public void testToString() {
        instance.put(0, 1.0);
        String expected = "{0=1.0;null}";
        assertEquals(expected, instance.toString());
    }

    @Test
    public void testHashCode() {
        SparseInstance anotherInstance = new SparseInstance(5, 0.0);
        assertEquals(instance.hashCode(), anotherInstance.hashCode());
    }

    @Test
    public void testEquals() {
        SparseInstance anotherInstance = new SparseInstance(5, 0.0);
        assertTrue(instance.equals(anotherInstance));
        anotherInstance.put(0, 1.0);
        assertFalse(instance.equals(anotherInstance));
    }

    @Test
    public void testCopy() {
        instance.put(0, 1.0);
        SparseInstance copy = (SparseInstance) instance.copy();
        assertEquals(instance, copy);
        assertNotSame(instance, copy);
    }

    @Test
    public void testRemoveAttributes() {
        instance.put(0, 1.0);
        instance.put(1, 2.0);
        Set<Integer> indices = new HashSet<>();
        indices.add(0);
        instance.removeAttributes(indices);
        assertFalse(instance.containsKey(0));
        assertEquals(2.0, instance.get(0), 0.0);
    }
}