package net.sf.javaml.core;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import net.sf.javaml.core.Fold;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class FoldTest {

    private Dataset parentDataset;
    private Instance mockInstance;
    private Fold fold;
    private int[] indices;

    @Before
    public void setUp() {
        parentDataset = mock(Dataset.class);
        mockInstance = mock(Instance.class);
        indices = new int[]{0, 1, 2};
        fold = new Fold(parentDataset, indices);

        when(parentDataset.instance(0)).thenReturn(mockInstance);
        when(parentDataset.instance(1)).thenReturn(mockInstance);
        when(parentDataset.instance(2)).thenReturn(mockInstance);
        when(parentDataset.classes()).thenReturn(new TreeSet<>(Arrays.asList("class1", "class2")));
        when(parentDataset.noAttributes()).thenReturn(5);
        when(parentDataset.classIndex("class1")).thenReturn(0);
        when(parentDataset.classValue(0)).thenReturn("class1");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddInstance() {
        fold.add(mockInstance);
    }

    @Test
    public void testClasses() {
        SortedSet<Object> classes = fold.classes();
        assertNotNull(classes);
        assertEquals(2, classes.size());
        assertTrue(classes.contains("class1"));
        assertTrue(classes.contains("class2"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFolds() {
        fold.folds(3, new Random());
    }

    @Test
    public void testInstance() {
        Instance instance = fold.instance(0);
        assertNotNull(instance);
        assertEquals(mockInstance, instance);
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
        Instance instance = fold.get(0);
        assertNotNull(instance);
        assertEquals(mockInstance, instance);
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
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
        assertEquals(mockInstance, iterator.next());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testLastIndexOf() {
        fold.lastIndexOf(mockInstance);
    }

    @Test
    public void testListIterator() {
        ListIterator<Instance> listIterator = fold.listIterator();
        assertNotNull(listIterator);
        assertTrue(listIterator.hasNext());
        assertEquals(mockInstance, listIterator.next());
    }

    @Test
    public void testListIteratorWithIndex() {
        ListIterator<Instance> listIterator = fold.listIterator(1);
        assertNotNull(listIterator);
        assertTrue(listIterator.hasNext());
        assertEquals(mockInstance, listIterator.next());
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
        assertEquals(3, fold.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSubList() {
        fold.subList(0, 1);
    }

    @Test
    public void testToArray() {
        Object[] array = fold.toArray();
        assertNotNull(array);
        assertEquals(3, array.length);
        assertEquals(mockInstance, array[0]);
    }

    @Test
    public void testToArrayWithParameter() {
        Instance[] array = fold.toArray(new Instance[0]);
        assertNotNull(array);
        assertEquals(3, array.length);
        assertEquals(mockInstance, array[0]);
    }

    @Test
    public void testNoAttributes() {
        assertEquals(5, fold.noAttributes());
    }

    @Test
    public void testClassIndex() {
        assertEquals(0, fold.classIndex("class1"));
    }

    @Test
    public void testClassValue() {
        assertEquals("class1", fold.classValue(0));
    }

    @Test
    public void testCopy() {
        Dataset copy = fold.copy();
        assertNotNull(copy);
        assertEquals(fold.size(), copy.size());
    }
}