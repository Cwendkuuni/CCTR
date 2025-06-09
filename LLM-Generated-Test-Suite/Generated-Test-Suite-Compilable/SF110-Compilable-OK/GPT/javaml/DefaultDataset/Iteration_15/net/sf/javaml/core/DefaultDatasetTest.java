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
    }

    @Test
    public void testConstructorWithCollection() {
        Collection<Instance> instances = Arrays.asList(mockInstance1, mockInstance2);
        DefaultDataset datasetWithCollection = new DefaultDataset(instances);
        assertEquals(2, datasetWithCollection.size());
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(0, dataset.size());
    }

    @Test
    public void testAddInstance() {
        when(mockInstance1.classValue()).thenReturn("class1");
        when(mockInstance1.noAttributes()).thenReturn(5);

        dataset.add(mockInstance1);
        assertEquals(1, dataset.size());
        assertTrue(dataset.classes().contains("class1"));
    }

    @Test
    public void testAddAllInstances() {
        Collection<Instance> instances = Arrays.asList(mockInstance1, mockInstance2);
        when(mockInstance1.classValue()).thenReturn("class1");
        when(mockInstance2.classValue()).thenReturn("class2");
        when(mockInstance1.noAttributes()).thenReturn(5);
        when(mockInstance2.noAttributes()).thenReturn(3);

        dataset.addAll(instances);
        assertEquals(2, dataset.size());
        assertTrue(dataset.classes().contains("class1"));
        assertTrue(dataset.classes().contains("class2"));
    }

    @Test
    public void testClear() {
        dataset.add(mockInstance1);
        dataset.clear();
        assertEquals(0, dataset.size());
        assertTrue(dataset.classes().isEmpty());
    }

    @Test
    public void testInstance() {
        dataset.add(mockInstance1);
        assertEquals(mockInstance1, dataset.instance(0));
    }

    @Test
    public void testClasses() {
        when(mockInstance1.classValue()).thenReturn("class1");
        dataset.add(mockInstance1);
        SortedSet<Object> classes = dataset.classes();
        assertEquals(1, classes.size());
        assertTrue(classes.contains("class1"));
    }

    @Test
    public void testKNearest() {
        dataset.add(mockInstance1);
        dataset.add(mockInstance2);

        when(mockDistanceMeasure.measure(any(Instance.class), any(Instance.class))).thenReturn(1.0);
        when(mockDistanceMeasure.compare(anyDouble(), anyDouble())).thenReturn(true);

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
        when(mockInstance1.noAttributes()).thenReturn(5);
        dataset.add(mockInstance1);
        assertEquals(5, dataset.noAttributes());
    }

    @Test
    public void testClassIndex() {
        when(mockInstance1.classValue()).thenReturn("class1");
        dataset.add(mockInstance1);
        assertEquals(0, dataset.classIndex("class1"));
    }

    @Test
    public void testClassValue() {
        when(mockInstance1.classValue()).thenReturn("class1");
        dataset.add(mockInstance1);
        assertEquals("class1", dataset.classValue(0));
    }

    @Test
    public void testCopy() {
        when(mockInstance1.copy()).thenReturn(mockInstance1);
        dataset.add(mockInstance1);
        Dataset copy = dataset.copy();
        assertEquals(1, copy.size());
        assertEquals(mockInstance1, copy.instance(0));
    }
}