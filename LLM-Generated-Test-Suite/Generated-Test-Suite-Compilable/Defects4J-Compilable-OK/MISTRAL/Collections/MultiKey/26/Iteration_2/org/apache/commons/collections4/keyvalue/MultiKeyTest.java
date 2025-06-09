package org.apache.commons.collections4.keyvalue;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import java.util.Arrays;

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
    public void testConstructorWithTwoKeys() {
        MultiKey<String> mk = new MultiKey<>("key1", "key2");
        Assert.assertEquals(2, mk.size());
        Assert.assertEquals("key1", mk.getKey(0));
        Assert.assertEquals("key2", mk.getKey(1));
    }

    @Test
    public void testConstructorWithThreeKeys() {
        MultiKey<String> mk = new MultiKey<>("key1", "key2", "key3");
        Assert.assertEquals(3, mk.size());
        Assert.assertEquals("key1", mk.getKey(0));
        Assert.assertEquals("key2", mk.getKey(1));
        Assert.assertEquals("key3", mk.getKey(2));
    }

    @Test
    public void testConstructorWithFourKeys() {
        MultiKey<String> mk = new MultiKey<>("key1", "key2", "key3", "key4");
        Assert.assertEquals(4, mk.size());
        Assert.assertEquals("key1", mk.getKey(0));
        Assert.assertEquals("key2", mk.getKey(1));
        Assert.assertEquals("key3", mk.getKey(2));
        Assert.assertEquals("key4", mk.getKey(3));
    }

    @Test
    public void testConstructorWithFiveKeys() {
        MultiKey<String> mk = new MultiKey<>("key1", "key2", "key3", "key4", "key5");
        Assert.assertEquals(5, mk.size());
        Assert.assertEquals("key1", mk.getKey(0));
        Assert.assertEquals("key2", mk.getKey(1));
        Assert.assertEquals("key3", mk.getKey(2));
        Assert.assertEquals("key4", mk.getKey(3));
        Assert.assertEquals("key5", mk.getKey(4));
    }

    @Test
    public void testConstructorWithArray() {
        MultiKey<String> mk = new MultiKey<>(new String[]{"key1", "key2", "key3"});
        Assert.assertEquals(3, mk.size());
        Assert.assertEquals("key1", mk.getKey(0));
        Assert.assertEquals("key2", mk.getKey(1));
        Assert.assertEquals("key3", mk.getKey(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullArray() {
        new MultiKey<>(null);
    }

    @Test
    public void testGetKeys() {
        String[] keys = multiKeyArray.getKeys();
        Assert.assertArrayEquals(new String[]{"key1", "key2", "key3"}, keys);
    }

    @Test
    public void testGetKey() {
        Assert.assertEquals("key1", multiKeyArray.getKey(0));
        Assert.assertEquals("key2", multiKeyArray.getKey(1));
        Assert.assertEquals("key3", multiKeyArray.getKey(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetKeyInvalidIndex() {
        multiKeyArray.getKey(3);
    }

    @Test
    public void testSize() {
        Assert.assertEquals(2, multiKey2.size());
        Assert.assertEquals(3, multiKey3.size());
        Assert.assertEquals(4, multiKey4.size());
        Assert.assertEquals(5, multiKey5.size());
        Assert.assertEquals(3, multiKeyArray.size());
    }

    @Test
    public void testEquals() {
        MultiKey<String> mk1 = new MultiKey<>("key1", "key2");
        MultiKey<String> mk2 = new MultiKey<>("key1", "key2");
        MultiKey<String> mk3 = new MultiKey<>("key1", "key3");

        Assert.assertTrue(mk1.equals(mk2));
        Assert.assertFalse(mk1.equals(mk3));
        Assert.assertFalse(mk1.equals(null));
        Assert.assertFalse(mk1.equals("not a MultiKey"));
    }

    @Test
    public void testHashCode() {
        MultiKey<String> mk1 = new MultiKey<>("key1", "key2");
        MultiKey<String> mk2 = new MultiKey<>("key1", "key2");
        MultiKey<String> mk3 = new MultiKey<>("key1", "key3");

        Assert.assertEquals(mk1.hashCode(), mk2.hashCode());
        Assert.assertNotEquals(mk1.hashCode(), mk3.hashCode());
    }

    @Test
    public void testToString() {
        MultiKey<String> mk = new MultiKey<>("key1", "key2");
        Assert.assertEquals("MultiKey[key1, key2]", mk.toString());
    }
}