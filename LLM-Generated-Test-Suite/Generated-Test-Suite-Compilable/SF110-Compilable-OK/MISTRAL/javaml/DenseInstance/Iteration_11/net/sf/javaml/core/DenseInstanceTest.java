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
        attributes = new double[]{1.0, 2.0, 3.0, 4.0};
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
        assertEquals(2.0, instance.value(1), 0.0);
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
        assertTrue(instance.containsKey(1));
        assertFalse(instance.containsKey(5));
        assertFalse(instance.containsKey("key"));
    }

    @Test
    public void testContainsValue() {
        assertTrue(instance.containsValue(2.0));
        assertFalse(instance.containsValue(5.0));
        assertFalse(instance.containsValue("value"));
    }

    @Test
    public void testEntrySet() {
        Set<Map.Entry<Integer, Double>> entrySet = instance.entrySet();
        assertEquals(attributes.length, entrySet.size());
        for (int i = 0; i < attributes.length; i++) {
            assertTrue(entrySet.contains(new AbstractMap.SimpleEntry<>(i, attributes[i])));
        }
    }

    @Test
    public void testGet() {
        assertEquals(2.0, instance.get(1), 0.0);
    }

    @Test
    public void testIsEmpty() {
        assertFalse(instance.isEmpty());
    }

    @Test
    public void testKeySet() {
        SortedSet<Integer> keySet = instance.keySet();
        assertEquals(attributes.length, keySet.size());
        for (int i = 0; i < attributes.length; i++) {
            assertTrue(keySet.contains(i));
        }
    }

    @Test
    public void testPut() {
        Double oldValue = instance.put(1, 5.0);
        assertEquals(2.0, oldValue, 0.0);
        assertEquals(5.0, instance.value(1), 0.0);
    }

    @Test
    public void testPutAll() {
        Map<Integer, Double> map = new HashMap<>();
        map.put(1, 5.0);
        map.put(2, 6.0);
        instance.putAll(map);
        assertEquals(5.0, instance.value(1), 0.0);
        assertEquals(6.0, instance.value(2), 0.0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        instance.remove(1);
    }

    @Test
    public void testSize() {
        assertEquals(attributes.length, instance.size());
    }

    @Test
    public void testValues() {
        Collection<Double> values = instance.values();
        assertEquals(attributes.length, values.size());
        for (double value : attributes) {
            assertTrue(values.contains(value));
        }
    }

    @Test
    public void testNoAttributes() {
        assertEquals(attributes.length, instance.noAttributes());
    }

    @Test
    public void testToString() {
        String expected = "{" + Arrays.toString(attributes) + ";" + instance.classValue() + "}";
        assertEquals(expected, instance.toString());
    }

    @Test
    public void testRemoveAttribute() {
        instance.removeAttribute(1);
        assertEquals(3, instance.attributes.length);
        assertArrayEquals(new double[]{1.0, 3.0, 4.0}, instance.attributes, 0.0);
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
        assertTrue(copy instanceof DenseInstance);
        assertArrayEquals(attributes, ((DenseInstance) copy).attributes, 0.0);
        assertEquals(instance.classValue(), copy.classValue());
    }

    @Test
    public void testRemoveAttributes() {
        Set<Integer> indices = new HashSet<>(Arrays.asList(1, 2));
        instance.removeAttributes(indices);
        assertEquals(2, instance.attributes.length);
        assertArrayEquals(new double[]{1.0, 4.0}, instance.attributes, 0.0);
    }
}