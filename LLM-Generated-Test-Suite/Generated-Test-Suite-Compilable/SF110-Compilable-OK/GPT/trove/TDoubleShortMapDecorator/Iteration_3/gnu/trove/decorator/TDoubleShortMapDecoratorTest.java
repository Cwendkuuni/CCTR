package gnu.trove.decorator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import gnu.trove.decorator.TDoubleShortMapDecorator;
import gnu.trove.map.TDoubleShortMap;
import gnu.trove.iterator.TDoubleShortIterator;
import org.junit.Before;
import org.junit.Test;

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
    public void testConstructor() {
        assertNotNull(new TDoubleShortMapDecorator());
        assertNotNull(new TDoubleShortMapDecorator(mockMap));
    }

    @Test
    public void testPut() {
        when(mockMap.put(1.0, (short) 2)).thenReturn((short) 0);
        assertNull(decorator.put(1.0, (short) 2));
        verify(mockMap).put(1.0, (short) 2);
    }

    @Test
    public void testGet() {
        when(mockMap.get(1.0)).thenReturn((short) 2);
        assertEquals(Short.valueOf((short) 2), decorator.get(1.0));
        verify(mockMap).get(1.0);
    }

    @Test
    public void testRemove() {
        when(mockMap.remove(1.0)).thenReturn((short) 2);
        assertEquals(Short.valueOf((short) 2), decorator.remove(1.0));
        verify(mockMap).remove(1.0);
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
        assertTrue(entrySet.contains(new AbstractMap.SimpleEntry<>(1.0, (short) 2)));
    }

    @Test
    public void testContainsValue() {
        when(mockMap.containsValue((short) 2)).thenReturn(true);
        assertTrue(decorator.containsValue((short) 2));
        verify(mockMap).containsValue((short) 2);
    }

    @Test
    public void testContainsKey() {
        when(mockMap.containsKey(1.0)).thenReturn(true);
        assertTrue(decorator.containsKey(1.0));
        verify(mockMap).containsKey(1.0);
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
        assertTrue(byteArrayOutputStream.size() > 0);
    }
}