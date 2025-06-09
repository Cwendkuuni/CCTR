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

        public TestObject(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            TestObject that = (TestObject) obj;
            return name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    @Test
    public void testClone() {
        TestObject original = new TestObject("test");
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
        TestObject obj = new TestObject("test");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SerializationUtils.serialize(obj, baos);
        byte[] serializedData = baos.toByteArray();
        assertNotNull(serializedData);
        assertTrue(serializedData.length > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSerializeToStreamNullStream() {
        TestObject obj = new TestObject("test");
        SerializationUtils.serialize(obj, null);
    }

    @Test
    public void testSerializeToByteArray() {
        TestObject obj = new TestObject("test");
        byte[] serializedData = SerializationUtils.serialize(obj);
        assertNotNull(serializedData);
        assertTrue(serializedData.length > 0);
    }

    @Test
    public void testDeserializeFromStream() throws IOException, ClassNotFoundException {
        TestObject obj = new TestObject("test");
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
        TestObject obj = new TestObject("test");
        byte[] serializedData = SerializationUtils.serialize(obj);
        TestObject deserialized = (TestObject) SerializationUtils.deserialize(serializedData);
        assertEquals(obj, deserialized);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeserializeFromByteArrayNullData() {
        SerializationUtils.deserialize((byte[]) null);
    }
}