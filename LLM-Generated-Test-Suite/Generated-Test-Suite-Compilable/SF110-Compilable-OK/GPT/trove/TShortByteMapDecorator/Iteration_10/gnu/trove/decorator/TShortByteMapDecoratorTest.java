package gnu.trove.decorator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import gnu.trove.map.TShortByteMap;
import gnu.trove.iterator.TShortByteIterator;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.util.*;

public class TShortByteMapDecoratorTest {

    private TShortByteMap mockMap;
    private TShortByteMapDecorator decorator;

    @Before
    public void setUp() {
        mockMap = mock(TShortByteMap.class);
        decorator = new TShortByteMapDecorator(mockMap);
    }

    @Test
    public void testConstructorWithMap() {
        assertNotNull(decorator.getMap());
    }

    @Test
    public void testPut() {
        when(mockMap.put((short) 1, (byte) 2)).thenReturn((byte) 0);
        assertNull(decorator.put((short) 1, (byte) 2));

        when(mockMap.put((short) 1, (byte) 2)).thenReturn((byte) 3);
        assertEquals(Byte.valueOf((byte) 3), decorator.put((short) 1, (byte) 2));
    }

    @Test
    public void testGet() {
        when(mockMap.get((short) 1)).thenReturn((byte) 2);
        assertEquals(Byte.valueOf((byte) 2), decorator.get((short) 1));

        when(mockMap.get((short) 1)).thenReturn(mockMap.getNoEntryValue());
        assertNull(decorator.get((short) 1));
    }

    @Test
    public void testRemove() {
        when(mockMap.remove((short) 1)).thenReturn((byte) 2);
        assertEquals(Byte.valueOf((byte) 2), decorator.remove((short) 1));

        when(mockMap.remove((short) 1)).thenReturn(mockMap.getNoEntryValue());
        assertNull(decorator.remove((short) 1));
    }

    @Test
    public void testClear() {
        decorator.clear();
        verify(mockMap).clear();
    }

    @Test
    public void testEntrySet() {
        TShortByteIterator mockIterator = mock(TShortByteIterator.class);
        when(mockMap.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, false);
        when(mockIterator.key()).thenReturn((short) 1);
        when(mockIterator.value()).thenReturn((byte) 2);

        Set<Map.Entry<Short, Byte>> entrySet = decorator.entrySet();
        assertEquals(1, entrySet.size());

        Iterator<Map.Entry<Short, Byte>> iterator = entrySet.iterator();
        assertTrue(iterator.hasNext());
        Map.Entry<Short, Byte> entry = iterator.next();
        assertEquals(Short.valueOf((short) 1), entry.getKey());
        assertEquals(Byte.valueOf((byte) 2), entry.getValue());
    }

    @Test
    public void testContainsKey() {
        when(mockMap.containsKey((short) 1)).thenReturn(true);
        assertTrue(decorator.containsKey((short) 1));

        when(mockMap.containsKey((short) 1)).thenReturn(false);
        assertFalse(decorator.containsKey((short) 1));
    }

    @Test
    public void testContainsValue() {
        when(mockMap.containsValue((byte) 2)).thenReturn(true);
        assertTrue(decorator.containsValue((byte) 2));

        when(mockMap.containsValue((byte) 2)).thenReturn(false);
        assertFalse(decorator.containsValue((byte) 2));
    }

    @Test
    public void testSize() {
        when(mockMap.size()).thenReturn(5);
        assertEquals(5, decorator.size());
    }

    @Test
    public void testIsEmpty() {
        when(mockMap.size()).thenReturn(0);
        assertTrue(decorator.isEmpty());

        when(mockMap.size()).thenReturn(1);
        assertFalse(decorator.isEmpty());
    }

    @Test
    public void testPutAll() {
        Map<Short, Byte> map = new HashMap<>();
        map.put((short) 1, (byte) 2);
        map.put((short) 3, (byte) 4);

        decorator.putAll(map);
        verify(mockMap).put((short) 1, (byte) 2);
        verify(mockMap).put((short) 3, (byte) 4);
    }

    @Test
    public void testReadExternal() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[]{0});
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        decorator.readExternal(objectInputStream);
        assertNotNull(decorator.getMap());
    }

    @Test
    public void testWriteExternal() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        decorator.writeExternal(objectOutputStream);
        assertTrue(byteArrayOutputStream.size() > 0);
    }
}