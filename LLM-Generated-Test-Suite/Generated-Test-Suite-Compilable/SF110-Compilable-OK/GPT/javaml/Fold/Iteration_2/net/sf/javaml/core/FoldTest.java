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
    private Instance mockInstance1;
    private Instance mockInstance2;
    private int[] indices;
    private Fold fold;

    @Before
    public void setUp() {
        parentDataset = mock(Dataset.class);
        mockInstance1 = mock(Instance.class);
        mockInstance2 = mock(Instance.class);
        indices = new int[]{0, 1};
        fold = new Fold(parentDataset, indices);

        when(parentDataset.instance(0)).thenReturn(mockInstance1);
        when(parentDataset.instance(1)).thenReturn(mockInstance2);
        when(parentDataset.classes()).thenReturn(new TreeSet<>(Arrays.asList("class1", "class2")));
        when(parentDataset.noAttributes()).thenReturn(5);
        when(parentDataset.classIndex("class1")).thenReturn(0);
        when(parentDataset.classValue(0)).thenReturn("class1");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddInstance() {
        fold.add(mockInstance1);
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
        fold.folds(2, new Random());
    }

    @Test
    public void testInstance() {
        assertEquals(mockInstance1, fold.instance(0));
        assertEquals(mockInstance2, fold.instance(1));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testKNearest() {
        fold.kNearest(1, mockInstance1, mock(DistanceMeasure.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAtIndex() {
        fold.add(0, mockInstance1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAllCollection() {
        fold.addAll(Arrays.asList(mockInstance1, mockInstance2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAllAtIndex() {
        fold.addAll(0, Arrays.asList(mockInstance1, mockInstance2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() {
        fold.clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testContains() {
        fold.contains(mockInstance1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testContainsAll() {
        fold.containsAll(Arrays.asList(mockInstance1, mockInstance2));
    }

    @Test
    public void testGet() {
        assertEquals(mockInstance1, fold.get(0));
        assertEquals(mockInstance2, fold.get(1));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIndexOf() {
        fold.indexOf(mockInstance1);
    }

    @Test
    public void testIsEmpty() {
        assertFalse(fold.isEmpty());
    }

    @Test
    public void testIterator() {
        Iterator<Instance> iterator = fold.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(mockInstance1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(mockInstance2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testLastIndexOf() {
        fold.lastIndexOf(mockInstance1);
    }

    @Test
    public void testListIterator() {
        ListIterator<Instance> listIterator = fold.listIterator();
        assertTrue(listIterator.hasNext());
        assertEquals(mockInstance1, listIterator.next());
        assertTrue(listIterator.hasNext());
        assertEquals(mockInstance2, listIterator.next());
        assertFalse(listIterator.hasNext());
    }

    @Test
    public void testListIteratorWithIndex() {
        ListIterator<Instance> listIterator = fold.listIterator(1);
        assertTrue(listIterator.hasNext());
        assertEquals(mockInstance2, listIterator.next());
        assertFalse(listIterator.hasNext());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveObject() {
        fold.remove(mockInstance1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAtIndex() {
        fold.remove(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() {
        fold.removeAll(Arrays.asList(mockInstance1, mockInstance2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() {
        fold.retainAll(Arrays.asList(mockInstance1, mockInstance2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSet() {
        fold.set(0, mockInstance1);
    }

    @Test
    public void testSize() {
        assertEquals(2, fold.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSubList() {
        fold.subList(0, 1);
    }

    @Test
    public void testToArray() {
        Object[] array = fold.toArray();
        assertEquals(2, array.length);
        assertEquals(mockInstance1, array[0]);
        assertEquals(mockInstance2, array[1]);
    }

    @Test
    public void testToArrayWithType() {
        Instance[] array = fold.toArray(new Instance[0]);
        assertEquals(2, array.length);
        assertEquals(mockInstance1, array[0]);
        assertEquals(mockInstance2, array[1]);
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