package org.apache.commons.collections4.keyvalue;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class MultiKeyTest {

    private MultiKey<String> multiKey2;
    private MultiKey<String> multiKey3;
    private MultiKey<String> multiKey4;
    private MultiKey<String> multiKey5;
    private MultiKey<String> multiKeyArray;

    @Before
    public void setUp() {
        multiKey2 = new MultiKey<>("key1", "key2");
        multiKey3 = new MultiKey<>("key1", "key2", "key3");
        multiKey4 = new MultiKey<>("key1", "key2", "key3", "key4");
        multiKey5 = new MultiKey<>("key1", "key2", "key3", "key4", "key5");
        multiKeyArray = new MultiKey<>(new String[]{"key1", "key2", "key3"});
    }

    @Test
    public void testConstructor2Keys() {
        assertNotNull(multiKey2);
        assertEquals(2, multiKey2.size());
    }

    @Test
    public void testConstructor3Keys() {
        assertNotNull(multiKey3);
        assertEquals(3, multiKey3.size());
    }

    @Test
    public void testConstructor4Keys() {
        assertNotNull(multiKey4);
        assertEquals(4, multiKey4.size());
    }

    @Test
    public void testConstructor5Keys() {
        assertNotNull(multiKey5);
        assertEquals(5, multiKey5.size());
    }

    @Test
    public void testConstructorArrayKeys() {
        assertNotNull(multiKeyArray);
        assertEquals(3, multiKeyArray.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorArrayKeysNull() {
        new MultiKey<>(null);
    }

    @Test
    public void testGetKeys() {
        String[] keys = multiKey2.getKeys();
        assertNotNull(keys);
        assertEquals(2, keys.length);
        assertEquals("key1", keys[0]);
        assertEquals("key2", keys[1]);
    }

    @Test
    public void testGetKey() {
        assertEquals("key1", multiKey2.getKey(0));
        assertEquals("key2", multiKey2.getKey(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetKeyInvalidIndex() {
        multiKey2.getKey(2);
    }

    @Test
    public void testSize() {
        assertEquals(2, multiKey2.size());
        assertEquals(3, multiKey3.size());
        assertEquals(4, multiKey4.size());
        assertEquals(5, multiKey5.size());
        assertEquals(3, multiKeyArray.size());
    }

    @Test
    public void testEquals() {
        MultiKey<String> otherMultiKey2 = new MultiKey<>("key1", "key2");
        assertTrue(multiKey2.equals(otherMultiKey2));
        assertFalse(multiKey2.equals(multiKey3));
    }

    @Test
    public void testHashCode() {
        MultiKey<String> otherMultiKey2 = new MultiKey<>("key1", "key2");
        assertEquals(multiKey2.hashCode(), otherMultiKey2.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("MultiKey[key1, key2]", multiKey2.toString());
        assertEquals("MultiKey[key1, key2, key3]", multiKey3.toString());
        assertEquals("MultiKey[key1, key2, key3, key4]", multiKey4.toString());
        assertEquals("MultiKey[key1, key2, key3, key4, key5]", multiKey5.toString());
        assertEquals("MultiKey[key1, key2, key3]", multiKeyArray.toString());
    }
}