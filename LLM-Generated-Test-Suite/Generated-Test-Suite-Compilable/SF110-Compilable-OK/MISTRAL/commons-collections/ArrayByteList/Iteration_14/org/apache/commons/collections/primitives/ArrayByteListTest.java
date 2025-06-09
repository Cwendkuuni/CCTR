package org.apache.commons.collections.primitives;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.util.Arrays;

public class ArrayByteListTest {

    private ArrayByteList arrayByteList;

    @Before
    public void setUp() {
        arrayByteList = new ArrayByteList();
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(0, arrayByteList.size());
    }

    @Test
    public void testConstructorWithInitialCapacity() {
        arrayByteList = new ArrayByteList(10);
        assertEquals(0, arrayByteList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeInitialCapacity() {
        new ArrayByteList(-1);
    }

    @Test
    public void testConstructorWithByteCollection() {
        ByteCollection byteCollection = new ArrayByteList();
        byteCollection.add((byte) 1);
        byteCollection.add((byte) 2);
        arrayByteList = new ArrayByteList(byteCollection);
        assertEquals(2, arrayByteList.size());
        assertEquals((byte) 1, arrayByteList.get(0));
        assertEquals((byte) 2, arrayByteList.get(1));
    }

    @Test
    public void testGet() {
        arrayByteList.add((byte) 1);
        assertEquals((byte) 1, arrayByteList.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOutOfBounds() {
        arrayByteList.get(0);
    }

    @Test
    public void testSize() {
        assertEquals(0, arrayByteList.size());
        arrayByteList.add((byte) 1);
        assertEquals(1, arrayByteList.size());
    }

    @Test
    public void testRemoveElementAt() {
        arrayByteList.add((byte) 1);
        arrayByteList.add((byte) 2);
        assertEquals((byte) 1, arrayByteList.removeElementAt(0));
        assertEquals(1, arrayByteList.size());
        assertEquals((byte) 2, arrayByteList.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveElementAtOutOfBounds() {
        arrayByteList.removeElementAt(0);
    }

    @Test
    public void testSet() {
        arrayByteList.add((byte) 1);
        assertEquals((byte) 1, arrayByteList.set(0, (byte) 2));
        assertEquals((byte) 2, arrayByteList.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetOutOfBounds() {
        arrayByteList.set(0, (byte) 1);
    }

    @Test
    public void testAdd() {
        arrayByteList.add(0, (byte) 1);
        assertEquals((byte) 1, arrayByteList.get(0));
        arrayByteList.add(1, (byte) 2);
        assertEquals((byte) 2, arrayByteList.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddOutOfBounds() {
        arrayByteList.add(1, (byte) 1);
    }

    @Test
    public void testEnsureCapacity() {
        arrayByteList.ensureCapacity(10);
        assertTrue(arrayByteList.size() <= 10);
    }

    @Test
    public void testTrimToSize() {
        arrayByteList.add((byte) 1);
        arrayByteList.trimToSize();
        assertEquals(1, arrayByteList.size());
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        arrayByteList.add((byte) 1);
        arrayByteList.add((byte) 2);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(arrayByteList);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        ArrayByteList deserializedList = (ArrayByteList) ois.readObject();
        ois.close();

        assertEquals(arrayByteList.size(), deserializedList.size());
        assertEquals(arrayByteList.get(0), deserializedList.get(0));
        assertEquals(arrayByteList.get(1), deserializedList.get(1));
    }
}