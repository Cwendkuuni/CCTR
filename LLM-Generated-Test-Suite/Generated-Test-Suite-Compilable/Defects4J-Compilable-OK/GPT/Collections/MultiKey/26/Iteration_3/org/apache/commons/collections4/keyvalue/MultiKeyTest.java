package org.apache.commons.collections4.keyvalue;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.junit.Before;
import org.junit.Test;

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
    public void testGetKeys() {
        assertArrayEquals(new String[]{"key1", "key2"}, multiKey2.getKeys());
        assertArrayEquals(new String[]{"key1", "key2", "key3"}, multiKey3.getKeys());
        assertArrayEquals(new String[]{"key1", "key2", "key3", "key4"}, multiKey4.getKeys());
        assertArrayEquals(new String[]{"key1", "key2", "key3", "key4", "key5"}, multiKey5.getKeys());
        assertArrayEquals(new String[]{"key1", "key2", "key3"}, multiKeyArray.getKeys());
    }

    @Test
    public void testGetKey() {
        assertEquals("key1", multiKey2.getKey(0));
        assertEquals("key2", multiKey2.getKey(1));

        assertEquals("key3", multiKey3.getKey(2));

        assertEquals("key4", multiKey4.getKey(3));

        assertEquals("key5", multiKey5.getKey(4));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetKeyOutOfBounds() {
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
        MultiKey<String> anotherMultiKey2 = new MultiKey<>("key1", "key2");
        MultiKey<String> differentMultiKey2 = new MultiKey<>("key1", "key3");

        assertTrue(multiKey2.equals(anotherMultiKey2));
        assertFalse(multiKey2.equals(differentMultiKey2));
        assertFalse(multiKey2.equals(multiKey3));
        assertFalse(multiKey2.equals(null));
        assertFalse(multiKey2.equals("Some String"));
    }

    @Test
    public void testHashCode() {
        MultiKey<String> anotherMultiKey2 = new MultiKey<>("key1", "key2");
        assertEquals(multiKey2.hashCode(), anotherMultiKey2.hashCode());

        MultiKey<String> differentMultiKey2 = new MultiKey<>("key1", "key3");
        assertNotEquals(multiKey2.hashCode(), differentMultiKey2.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("MultiKey[key1, key2]", multiKey2.toString());
        assertEquals("MultiKey[key1, key2, key3]", multiKey3.toString());
        assertEquals("MultiKey[key1, key2, key3, key4]", multiKey4.toString());
        assertEquals("MultiKey[key1, key2, key3, key4, key5]", multiKey5.toString());
        assertEquals("MultiKey[key1, key2, key3]", multiKeyArray.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullArray() {
        new MultiKey<>(null);
    }

    @Test
    public void testConstructorWithCloneOption() {
        String[] keys = {"key1", "key2"};
        MultiKey<String> multiKeyClone = new MultiKey<>(keys, true);
        assertArrayEquals(keys, multiKeyClone.getKeys());

        keys[0] = "modifiedKey";
        assertNotEquals(keys[0], multiKeyClone.getKey(0));
    }
}