package net.sf.javaml.core;

import net.sf.javaml.core.Fold;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FoldTest {

    private Fold fold;
    private Dataset parentDataset;
    private int[] indices;
    private Instance mockInstance;

    @Before
    public void setUp() {
        parentDataset = mock(Dataset.class);
        indices = new int[]{0, 1, 2};
        fold = new Fold(parentDataset, indices);
        mockInstance = mock(Instance.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddInstance() {
        fold.add(mockInstance);
    }

    @Test
    public void testClasses() {
        SortedSet<Object> mockClasses = new TreeSet<>();
        when(parentDataset.classes()).thenReturn(mockClasses);
        assertEquals(mockClasses, fold.classes());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFolds() {
        fold.folds(3, new Random());
    }

    @Test
    public void testInstance() {
        when(parentDataset.instance(0)).thenReturn(mockInstance);
        assertEquals(mockInstance, fold.instance(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testKNearest() {
        fold.kNearest(3, mockInstance, mock(DistanceMeasure.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAtIndex() {
        fold.add(0, mockInstance);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAllCollection() {
        fold.addAll(Collections.singletonList(mockInstance));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAllAtIndex() {
        fold.addAll(0, Collections.singletonList(mockInstance));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() {
        fold.clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testContains() {
        fold.contains(mockInstance);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testContainsAll() {
        fold.containsAll(Collections.singletonList(mockInstance));
    }

    @Test
    public void testGet() {
        when(parentDataset.instance(0)).thenReturn(mockInstance);
        assertEquals(mockInstance, fold.get(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIndexOf() {
        fold.indexOf(mockInstance);
    }

    @Test
    public void testIsEmpty() {
        assertFalse(fold.isEmpty());
    }

    @Test
    public void testIterator() {
        Iterator<Instance> iterator = fold.iterator();
        assertTrue(iterator.hasNext());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testLastIndexOf() {
        fold.lastIndexOf(mockInstance);
    }

    @Test
    public void testListIterator() {
        ListIterator<Instance> listIterator = fold.listIterator();
        assertTrue(listIterator.hasNext());
    }

    @Test
    public void testListIteratorWithIndex() {
        ListIterator<Instance> listIterator = fold.listIterator(1);
        assertTrue(listIterator.hasNext());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveObject() {
        fold.remove(mockInstance);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAtIndex() {
        fold.remove(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() {
        fold.removeAll(Collections.singletonList(mockInstance));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() {
        fold.retainAll(Collections.singletonList(mockInstance));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSet() {
        fold.set(0, mockInstance);
    }

    @Test
    public void testSize() {
        assertEquals(indices.length, fold.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSubList() {
        fold.subList(0, 1);
    }

    @Test
    public void testToArray() {
        when(parentDataset.instance(0)).thenReturn(mockInstance);
        Object[] array = fold.toArray();
        assertEquals(indices.length, array.length);
        assertEquals(mockInstance, array[0]);
    }

    @Test
    public void testToArrayWithParameter() {
        when(parentDataset.instance(0)).thenReturn(mockInstance);
        Instance[] array = fold.toArray(new Instance[0]);
        assertEquals(indices.length, array.length);
        assertEquals(mockInstance, array[0]);
    }

    @Test
    public void testNoAttributes() {
        when(parentDataset.noAttributes()).thenReturn(5);
        assertEquals(5, fold.noAttributes());
    }

    @Test
    public void testClassIndex() {
        when(parentDataset.classIndex("class")).thenReturn(1);
        assertEquals(1, fold.classIndex("class"));
    }

    @Test
    public void testClassValue() {
        when(parentDataset.classValue(0)).thenReturn("classValue");
        assertEquals("classValue", fold.classValue(0));
    }

    @Test
    public void testCopy() {
        Dataset copy = fold.copy();
        assertNotNull(copy);
        assertEquals(fold.size(), copy.size());
    }
}