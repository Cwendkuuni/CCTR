package org.apache.commons.lang3;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.SerializationException;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class SerializationUtilsTest {

    static class TestObject implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private int value;

        public TestObject(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            TestObject that = (TestObject) obj;
            return value == that.value && name.equals(that.name);
        }
    }

    @Test
    public void testClone() {
        TestObject original = new TestObject("Test", 123);
        TestObject cloned = SerializationUtils.clone(original);
        assertNotSame(original, cloned);
        assertEquals(original, cloned);
    }

    @Test
    public void testCloneNull() {
        assertNull(SerializationUtils.clone(null));
    }

    @Test(expected = SerializationException.class)
    public void testCloneNonSerializable() {
        Object nonSerializable = new Object();
        SerializationUtils.clone((Serializable) nonSerializable);
    }

    @Test
    public void testSerializeToStream() throws IOException {
        TestObject obj = new TestObject("Test", 123);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SerializationUtils.serialize(obj, baos);
        byte[] data = baos.toByteArray();
        assertNotNull(data);
        assertTrue(data.length > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSerializeToStreamNullStream() {
        TestObject obj = new TestObject("Test", 123);
        SerializationUtils.serialize(obj, null);
    }

    @Test
    public void testSerializeToByteArray() {
        TestObject obj = new TestObject("Test", 123);
        byte[] data = SerializationUtils.serialize(obj);
        assertNotNull(data);
        assertTrue(data.length > 0);
    }

    @Test
    public void testDeserializeFromStream() throws IOException {
        TestObject obj = new TestObject("Test", 123);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SerializationUtils.serialize(obj, baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        TestObject deserialized = (TestObject) SerializationUtils.deserialize(bais);
        assertEquals(obj, deserialized);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeserializeFromStreamNullStream() {
        SerializationUtils.deserialize((ByteArrayInputStream) null);
    }

    @Test
    public void testDeserializeFromByteArray() {
        TestObject obj = new TestObject("Test", 123);
        byte[] data = SerializationUtils.serialize(obj);
        TestObject deserialized = (TestObject) SerializationUtils.deserialize(data);
        assertEquals(obj, deserialized);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeserializeFromByteArrayNullData() {
        SerializationUtils.deserialize((byte[]) null);
    }
}