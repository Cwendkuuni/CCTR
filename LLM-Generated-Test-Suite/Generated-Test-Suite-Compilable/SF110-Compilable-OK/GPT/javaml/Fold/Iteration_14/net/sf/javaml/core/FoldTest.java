package net.sf.javaml.core;

import net.sf.javaml.core.Fold;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FoldTest {

    private Dataset parentDataset;
    private int[] indices;
    private Fold fold;

    @Before
    public void setUp() {
        parentDataset = mock(Dataset.class);
        indices = new int[]{0, 1, 2};
        fold = new Fold(parentDataset, indices);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddInstance() {
        fold.add(mock(Instance.class));
    }

    @Test
    public void testClasses() {
        SortedSet<Object> expectedClasses = new TreeSet<>();
        when(parentDataset.classes()).thenReturn(expectedClasses);

        SortedSet<Object> actualClasses = fold.classes();
        assertEquals(expectedClasses, actualClasses);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFolds() {
        fold.folds(3, new Random());
    }

    @Test
    public void testInstance() {
        Instance expectedInstance = mock(Instance.class);
        when(parentDataset.instance(0)).thenReturn(expectedInstance);

        Instance actualInstance = fold.instance(0);
        assertEquals(expectedInstance, actualInstance);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testKNearest() {
        fold.kNearest(3, mock(Instance.class), mock(DistanceMeasure.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAtIndex() {
        fold.add(0, mock(Instance.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAllCollection() {
        fold.addAll(new ArrayList<>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAllAtIndex() {
        fold.addAll(0, new ArrayList<>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() {
        fold.clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testContains() {
        fold.contains(new Object());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testContainsAll() {
        fold.containsAll(new ArrayList<>());
    }

    @Test
    public void testGet() {
        Instance expectedInstance = mock(Instance.class);
        when(parentDataset.instance(0)).thenReturn(expectedInstance);

        Instance actualInstance = fold.get(0);
        assertEquals(expectedInstance, actualInstance);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIndexOf() {
        fold.indexOf(new Object());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(fold.isEmpty());
    }

    @Test
    public void testIterator() {
        Iterator<Instance> iterator = fold.iterator();
        assertNotNull(iterator);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testLastIndexOf() {
        fold.lastIndexOf(new Object());
    }

    @Test
    public void testListIterator() {
        ListIterator<Instance> listIterator = fold.listIterator();
        assertNotNull(listIterator);
    }

    @Test
    public void testListIteratorWithIndex() {
        ListIterator<Instance> listIterator = fold.listIterator(0);
        assertNotNull(listIterator);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveObject() {
        fold.remove(new Object());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAtIndex() {
        fold.remove(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() {
        fold.removeAll(new ArrayList<>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() {
        fold.retainAll(new ArrayList<>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSet() {
        fold.set(0, mock(Instance.class));
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
        Object[] array = fold.toArray();
        assertEquals(indices.length, array.length);
    }

    @Test
    public void testToArrayWithParameter() {
        Instance[] array = new Instance[indices.length];
        Instance[] result = fold.toArray(array);
        assertEquals(indices.length, result.length);
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