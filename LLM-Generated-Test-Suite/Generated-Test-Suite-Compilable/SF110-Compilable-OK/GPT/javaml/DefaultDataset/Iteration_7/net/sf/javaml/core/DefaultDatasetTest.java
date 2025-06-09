package net.sf.javaml.core;

import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DefaultDatasetTest {

    private DefaultDataset dataset;
    private Instance mockInstance1;
    private Instance mockInstance2;
    private DistanceMeasure mockDistanceMeasure;

    @Before
    public void setUp() {
        dataset = new DefaultDataset();
        mockInstance1 = mock(Instance.class);
        mockInstance2 = mock(Instance.class);
        mockDistanceMeasure = mock(DistanceMeasure.class);

        when(mockInstance1.classValue()).thenReturn("Class1");
        when(mockInstance2.classValue()).thenReturn("Class2");
        when(mockInstance1.noAttributes()).thenReturn(5);
        when(mockInstance2.noAttributes()).thenReturn(3);
    }

    @Test
    public void testAddInstance() {
        dataset.add(mockInstance1);
        assertEquals(1, dataset.size());
        assertTrue(dataset.contains(mockInstance1));
    }

    @Test
    public void testAddAllInstances() {
        Collection<Instance> instances = Arrays.asList(mockInstance1, mockInstance2);
        dataset.addAll(instances);
        assertEquals(2, dataset.size());
        assertTrue(dataset.containsAll(instances));
    }

    @Test
    public void testClear() {
        dataset.add(mockInstance1);
        dataset.clear();
        assertEquals(0, dataset.size());
        assertTrue(dataset.classes().isEmpty());
    }

    @Test
    public void testClasses() {
        dataset.add(mockInstance1);
        dataset.add(mockInstance2);
        SortedSet<Object> classes = dataset.classes();
        assertEquals(2, classes.size());
        assertTrue(classes.contains("Class1"));
        assertTrue(classes.contains("Class2"));
    }

    @Test
    public void testKNearest() {
        dataset.add(mockInstance1);
        dataset.add(mockInstance2);
        when(mockDistanceMeasure.measure(any(Instance.class), any(Instance.class))).thenReturn(1.0);
        Set<Instance> nearest = dataset.kNearest(1, mockInstance1, mockDistanceMeasure);
        assertEquals(1, nearest.size());
    }

    @Test
    public void testFolds() {
        dataset.add(mockInstance1);
        dataset.add(mockInstance2);
        Dataset[] folds = dataset.folds(2, new Random());
        assertEquals(2, folds.length);
    }

    @Test
    public void testNoAttributes() {
        dataset.add(mockInstance1);
        assertEquals(5, dataset.noAttributes());
    }

    @Test
    public void testClassIndex() {
        dataset.add(mockInstance1);
        dataset.add(mockInstance2);
        assertEquals(0, dataset.classIndex("Class1"));
        assertEquals(1, dataset.classIndex("Class2"));
    }

    @Test
    public void testClassValue() {
        dataset.add(mockInstance1);
        dataset.add(mockInstance2);
        assertEquals("Class1", dataset.classValue(0));
        assertEquals("Class2", dataset.classValue(1));
    }

    @Test
    public void testCopy() {
        dataset.add(mockInstance1);
        DefaultDataset copy = (DefaultDataset) dataset.copy();
        assertEquals(dataset.size(), copy.size());
        assertTrue(copy.contains(mockInstance1));
    }

    @Test
    public void testAddAtIndex() {
        dataset.add(0, mockInstance1);
        assertEquals(mockInstance1, dataset.instance(0));
    }

    @Test
    public void testAddElement() {
        dataset.addElement(mockInstance1);
        assertEquals(mockInstance1, dataset.instance(0));
    }

    @Test
    public void testInsertElementAt() {
        dataset.insertElementAt(mockInstance1, 0);
        assertEquals(mockInstance1, dataset.instance(0));
    }

    @Test
    public void testSetElementAt() {
        dataset.add(mockInstance1);
        dataset.setElementAt(mockInstance2, 0);
        assertEquals(mockInstance2, dataset.instance(0));
    }

    @Test
    public void testInstance() {
        dataset.add(mockInstance1);
        assertEquals(mockInstance1, dataset.instance(0));
    }
}