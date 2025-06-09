package org.apache.commons.collections4.sequence;

import org.apache.commons.collections4.sequence.EditScript;
import org.apache.commons.collections4.sequence.SequencesComparator;
import org.apache.commons.collections4.functors.DefaultEquator;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SequencesComparatorTest {

    private List<String> sequence1;
    private List<String> sequence2;
    private SequencesComparator<String> comparator;

    @Before
    public void setUp() {
        sequence1 = Arrays.asList("a", "b", "c", "d");
        sequence2 = Arrays.asList("a", "c", "e", "d");
        comparator = new SequencesComparator<>(sequence1, sequence2);
    }

    @Test
    public void testConstructorWithDefaultEquator() {
        SequencesComparator<String> defaultComparator = new SequencesComparator<>(sequence1, sequence2);
        assertNotNull(defaultComparator);
    }

    @Test
    public void testConstructorWithCustomEquator() {
        SequencesComparator<String> customComparator = new SequencesComparator<>(sequence1, sequence2, DefaultEquator.defaultEquator());
        assertNotNull(customComparator);
    }

    @Test
    public void testGetScript() {
        EditScript<String> script = comparator.getScript();
        assertNotNull(script);
        assertEquals(5, script.getModifications()); // 2 Keeps, 1 Delete, 1 Insert
    }

    @Test
    public void testGetScriptWithIdenticalSequences() {
        SequencesComparator<String> identicalComparator = new SequencesComparator<>(sequence1, sequence1);
        EditScript<String> script = identicalComparator.getScript();
        assertNotNull(script);
        assertEquals(0, script.getModifications()); // All Keeps
    }

    @Test
    public void testGetScriptWithEmptySequences() {
        SequencesComparator<String> emptyComparator = new SequencesComparator<>(Arrays.asList(), Arrays.asList());
        EditScript<String> script = emptyComparator.getScript();
        assertNotNull(script);
        assertEquals(0, script.getModifications()); // No operations
    }

    @Test
    public void testGetScriptWithOneEmptySequence() {
        SequencesComparator<String> oneEmptyComparator = new SequencesComparator<>(sequence1, Arrays.asList());
        EditScript<String> script = oneEmptyComparator.getScript();
        assertNotNull(script);
        assertEquals(sequence1.size(), script.getModifications()); // All Deletes
    }

    @Test
    public void testGetScriptWithReversedSequences() {
        SequencesComparator<String> reversedComparator = new SequencesComparator<>(sequence1, Arrays.asList("d", "c", "b", "a"));
        EditScript<String> script = reversedComparator.getScript();
        assertNotNull(script);
        assertEquals(6, script.getModifications()); // 4 Deletes, 2 Inserts
    }
}