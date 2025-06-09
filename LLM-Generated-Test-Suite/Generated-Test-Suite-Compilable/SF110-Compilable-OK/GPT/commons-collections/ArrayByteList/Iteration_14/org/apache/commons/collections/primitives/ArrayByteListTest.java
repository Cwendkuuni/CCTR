package org.apache.commons.collections.primitives;

import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ByteCollection;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class ArrayByteListTest {

    private ArrayByteList list;

    @Before
    public void setUp() {
        list = new ArrayByteList();
    }

    @Test
    public void testConstructorWithInitialCapacity() {
        ArrayByteList listWithCapacity = new ArrayByteList(10);
        assertEquals(0, listWithCapacity.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeCapacity() {
        new ArrayByteList(-1);
    }

    @Test
    public void testConstructorWithByteCollection() {
        ByteCollection collection = new ByteCollection() {
            public boolean add(byte element) { return false; }
            public boolean addAll(ByteCollection c) { return false; }
            public void clear() {}
            public boolean contains(byte element) { return false; }
            public boolean containsAll(ByteCollection c) { return false; }
            public boolean isEmpty() { return false; }
            public ByteIterator iterator() { return null; }
            public boolean removeAll(ByteCollection c) { return false; }
            public boolean removeElement(byte element) { return false; }
            public boolean retainAll(ByteCollection c) { return false; }
            public int size() { return 5; }
            public byte[] toArray() { return new byte[0]; }
            public byte[] toArray(byte[] a) { return new byte[0]; }
        };
        ArrayByteList listFromCollection = new ArrayByteList(collection);
        assertEquals(5, listFromCollection.size());
    }

    @Test
    public void testGet() {
        list.add(0, (byte) 10);
        assertEquals(10, list.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetWithInvalidIndex() {
        list.get(0);
    }

    @Test
    public void testSize() {
        assertEquals(0, list.size());
        list.add(0, (byte) 10);
        assertEquals(1, list.size());
    }

    @Test
    public void testRemoveElementAt() {
        list.add(0, (byte) 10);
        byte removed = list.removeElementAt(0);
        assertEquals(10, removed);
        assertEquals(0, list.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveElementAtWithInvalidIndex() {
        list.removeElementAt(0);
    }

    @Test
    public void testSet() {
        list.add(0, (byte) 10);
        byte oldVal = list.set(0, (byte) 20);
        assertEquals(10, oldVal);
        assertEquals(20, list.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetWithInvalidIndex() {
        list.set(0, (byte) 10);
    }

    @Test
    public void testAdd() {
        list.add(0, (byte) 10);
        assertEquals(10, list.get(0));
        assertEquals(1, list.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddWithInvalidIndex() {
        list.add(1, (byte) 10);
    }

    @Test
    public void testEnsureCapacity() {
        list.ensureCapacity(20);
        assertTrue(list.size() == 0);
    }

    @Test
    public void testTrimToSize() {
        list.add(0, (byte) 10);
        list.trimToSize();
        assertEquals(1, list.size());
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        list.add(0, (byte) 10);
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(list);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        ArrayByteList deserializedList = (ArrayByteList) in.readObject();

        assertEquals(1, deserializedList.size());
        assertEquals(10, deserializedList.get(0));
    }
}