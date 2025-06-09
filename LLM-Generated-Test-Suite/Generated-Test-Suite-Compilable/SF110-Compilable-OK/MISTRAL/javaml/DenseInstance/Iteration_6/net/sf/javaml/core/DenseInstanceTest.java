package net.sf.javaml.core;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class DenseInstanceTest {

    private DenseInstance instance;
    private double[] attributes;

    @Before
    public void setUp() {
        attributes = new double[]{1.0, 2.0, 3.0};
        instance = new DenseInstance(attributes);
    }

    @Test
    public void testConstructorWithAttributes() {
        assertArrayEquals(attributes, instance.attributes, 0.0);
    }

    @Test
    public void testConstructorWithAttributesAndClassValue() {
        Object classValue = "class1";
        DenseInstance instanceWithClass = new DenseInstance(attributes, classValue);
        assertArrayEquals(attributes, instanceWithClass.attributes, 0.0);
        assertEquals(classValue, instanceWithClass.classValue());
    }

    @Test
    public void testConstructorWithSize() {
        int size = 5;
        DenseInstance instanceWithSize = new DenseInstance(size);
        assertEquals(size, instanceWithSize.attributes.length);
    }

    @Test
    public void testValue() {
        assertEquals(1.0, instance.value(0), 0.0);
        assertEquals(2.0, instance.value(1), 0.0);
        assertEquals(3.0, instance.value(2), 0.0);
    }

    @Test
    public void testClear() {
        instance.clear();
        for (double value : instance.attributes) {
            assertEquals(0.0, value, 0.0);
        }
    }

    @Test
    public void testContainsKey() {
        assertTrue(instance.containsKey(0));
        assertTrue(instance.containsKey(2));
        assertFalse(instance.containsKey(3));
        assertFalse(instance.containsKey("key"));
    }

    @Test
    public void testContainsValue() {
        assertTrue(instance.containsValue(1.0));
        assertTrue(instance.containsValue(3.0));
        assertFalse(instance.containsValue(4.0));
        assertFalse(instance.containsValue("value"));
    }

    @Test
    public void testEntrySet() {
        Set<Map.Entry<Integer, Double>> entrySet = instance.entrySet();
        assertEquals(3, entrySet.size());
        assertTrue(entrySet.contains(new AbstractMap.SimpleEntry<>(0, 1.0)));
        assertTrue(entrySet.contains(new AbstractMap.SimpleEntry<>(1, 2.0)));
        assertTrue(entrySet.contains(new AbstractMap.SimpleEntry<>(2, 3.0)));
    }

    @Test
    public void testGet() {
        assertEquals(1.0, instance.get(0), 0.0);
        assertEquals(2.0, instance.get(1), 0.0);
        assertEquals(3.0, instance.get(2), 0.0);
    }

    @Test
    public void testIsEmpty() {
        assertFalse(instance.isEmpty());
    }

    @Test
    public void testKeySet() {
        SortedSet<Integer> keySet = instance.keySet();
        assertEquals(3, keySet.size());
        assertTrue(keySet.contains(0));
        assertTrue(keySet.contains(1));
        assertTrue(keySet.contains(2));
    }

    @Test
    public void testPut() {
        assertEquals(1.0, instance.put(0, 4.0), 0.0);
        assertEquals(4.0, instance.value(0), 0.0);
    }

    @Test
    public void testPutAll() {
        Map<Integer, Double> map = new HashMap<>();
        map.put(0, 4.0);
        map.put(1, 5.0);
        instance.putAll(map);
        assertEquals(4.0, instance.value(0), 0.0);
        assertEquals(5.0, instance.value(1), 0.0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        instance.remove(0);
    }

    @Test
    public void testSize() {
        assertEquals(3, instance.size());
    }

    @Test
    public void testValues() {
        Collection<Double> values = instance.values();
        assertEquals(3, values.size());
        assertTrue(values.contains(1.0));
        assertTrue(values.contains(2.0));
        assertTrue(values.contains(3.0));
    }

    @Test
    public void testNoAttributes() {
        assertEquals(3, instance.noAttributes());
    }

    @Test
    public void testToString() {
        assertEquals("{[1.0, 2.0, 3.0];null}", instance.toString());
    }

    @Test
    public void testRemoveAttribute() {
        instance.removeAttribute(1);
        assertEquals(2, instance.attributes.length);
        assertEquals(1.0, instance.value(0), 0.0);
        assertEquals(3.0, instance.value(1), 0.0);
    }

    @Test
    public void testHashCode() {
        DenseInstance anotherInstance = new DenseInstance(attributes);
        assertEquals(instance.hashCode(), anotherInstance.hashCode());
    }

    @Test
    public void testEquals() {
        DenseInstance anotherInstance = new DenseInstance(attributes);
        assertTrue(instance.equals(anotherInstance));
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(new Object()));
    }

    @Test
    public void testCopy() {
        Instance copy = instance.copy();
        assertNotSame(instance, copy);
        assertTrue(instance.equals(copy));
    }

    @Test
    public void testRemoveAttributes() {
        Set<Integer> indices = new HashSet<>(Arrays.asList(1, 2));
        instance.removeAttributes(indices);
        assertEquals(1, instance.attributes.length);
        assertEquals(1.0, instance.value(0), 0.0);
    }
}