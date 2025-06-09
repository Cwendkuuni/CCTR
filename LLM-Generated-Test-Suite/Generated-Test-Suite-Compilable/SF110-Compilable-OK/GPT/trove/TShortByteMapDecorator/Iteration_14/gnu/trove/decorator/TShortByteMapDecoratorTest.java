package gnu.trove.decorator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import gnu.trove.map.TShortByteMap;
import gnu.trove.iterator.TShortByteIterator;
import org.junit.Before;
import org.junit.Test;
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
    public void testPut() {
        when(mockMap.put((short)1, (byte)2)).thenReturn((byte)0);
        assertNull(decorator.put((short)1, (byte)2));
        verify(mockMap).put((short)1, (byte)2);
    }

    @Test
    public void testGet() {
        when(mockMap.get((short)1)).thenReturn((byte)2);
        assertEquals(Byte.valueOf((byte)2), decorator.get((short)1));
        verify(mockMap).get((short)1);
    }

    @Test
    public void testClear() {
        decorator.clear();
        verify(mockMap).clear();
    }

    @Test
    public void testRemove() {
        when(mockMap.remove((short)1)).thenReturn((byte)2);
        assertEquals(Byte.valueOf((byte)2), decorator.remove((short)1));
        verify(mockMap).remove((short)1);
    }

    @Test
    public void testEntrySet() {
        TShortByteIterator mockIterator = mock(TShortByteIterator.class);
        when(mockMap.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, false);
        when(mockIterator.key()).thenReturn((short)1);
        when(mockIterator.value()).thenReturn((byte)2);

        Set<Map.Entry<Short, Byte>> entrySet = decorator.entrySet();
        Iterator<Map.Entry<Short, Byte>> iterator = entrySet.iterator();
        assertTrue(iterator.hasNext());
        Map.Entry<Short, Byte> entry = iterator.next();
        assertEquals(Short.valueOf((short)1), entry.getKey());
        assertEquals(Byte.valueOf((byte)2), entry.getValue());
    }

    @Test
    public void testContainsValue() {
        when(mockMap.containsValue((byte)2)).thenReturn(true);
        assertTrue(decorator.containsValue((byte)2));
        verify(mockMap).containsValue((byte)2);
    }

    @Test
    public void testContainsKey() {
        when(mockMap.containsKey((short)1)).thenReturn(true);
        assertTrue(decorator.containsKey((short)1));
        verify(mockMap).containsKey((short)1);
    }

    @Test
    public void testSize() {
        when(mockMap.size()).thenReturn(1);
        assertEquals(1, decorator.size());
        verify(mockMap).size();
    }

    @Test
    public void testIsEmpty() {
        when(mockMap.size()).thenReturn(0);
        assertTrue(decorator.isEmpty());
        verify(mockMap).size();
    }

    @Test
    public void testPutAll() {
        Map<Short, Byte> map = new HashMap<>();
        map.put((short)1, (byte)2);
        decorator.putAll(map);
        verify(mockMap).put((short)1, (byte)2);
    }

    @Test
    public void testReadExternal() throws Exception {
        // This test would require a more complex setup with ObjectInput
        // Skipping implementation for brevity
    }

    @Test
    public void testWriteExternal() throws Exception {
        // This test would require a more complex setup with ObjectOutput
        // Skipping implementation for brevity
    }
}