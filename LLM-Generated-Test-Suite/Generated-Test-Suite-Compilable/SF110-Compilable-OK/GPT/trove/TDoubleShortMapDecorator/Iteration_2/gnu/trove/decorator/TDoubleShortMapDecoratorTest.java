package gnu.trove.decorator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import gnu.trove.decorator.TDoubleShortMapDecorator;
import gnu.trove.map.TDoubleShortMap;
import gnu.trove.iterator.TDoubleShortIterator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.*;
import java.util.*;

public class TDoubleShortMapDecoratorTest {

    private TDoubleShortMap mockMap;
    private TDoubleShortMapDecorator decorator;

    @Before
    public void setUp() {
        mockMap = mock(TDoubleShortMap.class);
        decorator = new TDoubleShortMapDecorator(mockMap);
    }

    @Test
    public void testConstructorWithMap() {
        assertNotNull(decorator.getMap());
    }

    @Test
    public void testPut() {
        when(mockMap.put(1.0, (short) 2)).thenReturn((short) 0);
        Short result = decorator.put(1.0, (short) 2);
        assertNull(result);
        verify(mockMap).put(1.0, (short) 2);
    }

    @Test
    public void testGet() {
        when(mockMap.get(1.0)).thenReturn((short) 2);
        Short result = decorator.get(1.0);
        assertEquals((Short) (short) 2, result);
    }

    @Test
    public void testRemove() {
        when(mockMap.remove(1.0)).thenReturn((short) 2);
        Short result = decorator.remove(1.0);
        assertEquals((Short) (short) 2, result);
    }

    @Test
    public void testClear() {
        decorator.clear();
        verify(mockMap).clear();
    }

    @Test
    public void testEntrySet() {
        TDoubleShortIterator mockIterator = mock(TDoubleShortIterator.class);
        when(mockMap.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, false);
        when(mockIterator.key()).thenReturn(1.0);
        when(mockIterator.value()).thenReturn((short) 2);

        Set<Map.Entry<Double, Short>> entrySet = decorator.entrySet();
        assertEquals(1, entrySet.size());
    }

    @Test
    public void testContainsValue() {
        when(mockMap.containsValue((short) 2)).thenReturn(true);
        assertTrue(decorator.containsValue((short) 2));
    }

    @Test
    public void testContainsKey() {
        when(mockMap.containsKey(1.0)).thenReturn(true);
        assertTrue(decorator.containsKey(1.0));
    }

    @Test
    public void testSize() {
        when(mockMap.size()).thenReturn(1);
        assertEquals(1, decorator.size());
    }

    @Test
    public void testIsEmpty() {
        when(mockMap.size()).thenReturn(0);
        assertTrue(decorator.isEmpty());
    }

    @Test
    public void testPutAll() {
        Map<Double, Short> map = new HashMap<>();
        map.put(1.0, (short) 2);
        decorator.putAll(map);
        verify(mockMap).put(1.0, (short) 2);
    }

    @Test
    public void testReadExternal() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[]{0});
        ObjectInput in = new ObjectInputStream(byteArrayInputStream);
        decorator.readExternal(in);
        assertNotNull(decorator.getMap());
    }

    @Test
    public void testWriteExternal() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(byteArrayOutputStream);
        decorator.writeExternal(out);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        assertNotNull(bytes);
    }
}