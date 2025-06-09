package org.apache.commons.collections4.sequence;

import org.apache.commons.collections4.sequence.SequencesComparator;
import org.apache.commons.collections4.sequence.EditScript;
import org.apache.commons.collections4.sequence.KeepCommand;
import org.apache.commons.collections4.sequence.InsertCommand;
import org.apache.commons.collections4.sequence.DeleteCommand;
import org.apache.commons.collections4.functors.DefaultEquator;
import org.apache.commons.collections4.Equator;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SequencesComparatorTest {

    private List<String> sequence1;
    private List<String> sequence2;
    private Equator<String> equator;

    @Before
    public void setUp() {
        sequence1 = Arrays.asList("a", "b", "c", "d");
        sequence2 = Arrays.asList("a", "x", "c", "d");
        equator = DefaultEquator.defaultEquator();
    }

    @Test
    public void testConstructorWithEquator() {
        SequencesComparator<String> comparator = new SequencesComparator<>(sequence1, sequence2, equator);
        assertNotNull(comparator);
    }

    @Test
    public void testConstructorWithoutEquator() {
        SequencesComparator<String> comparator = new SequencesComparator<>(sequence1, sequence2);
        assertNotNull(comparator);
    }

    @Test
    public void testGetScript() {
        SequencesComparator<String> comparator = new SequencesComparator<>(sequence1, sequence2, equator);
        EditScript<String> script = comparator.getScript();

        assertNotNull(script);
        assertEquals(4, script.getModifications());

        assertTrue(script.getLCSLength() == 3);

        assertTrue(script.getModifications() == 2);

        assertTrue(script.getModifications() == 2);
    }

    @Test
    public void testGetScriptWithIdenticalSequences() {
        SequencesComparator<String> comparator = new SequencesComparator<>(sequence1, sequence1, equator);
        EditScript<String> script = comparator.getScript();

        assertNotNull(script);
        assertEquals(0, script.getModifications());
        assertEquals(sequence1.size(), script.getLCSLength());
    }

    @Test
    public void testGetScriptWithEmptySequences() {
        SequencesComparator<String> comparator = new SequencesComparator<>(Arrays.asList(), Arrays.asList(), equator);
        EditScript<String> script = comparator.getScript();

        assertNotNull(script);
        assertEquals(0, script.getModifications());
        assertEquals(0, script.getLCSLength());
    }

    @Test
    public void testGetScriptWithCompletelyDifferentSequences() {
        List<String> seq1 = Arrays.asList("a", "b", "c");
        List<String> seq2 = Arrays.asList("x", "y", "z");
        SequencesComparator<String> comparator = new SequencesComparator<>(seq1, seq2, equator);
        EditScript<String> script = comparator.getScript();

        assertNotNull(script);
        assertEquals(seq1.size() + seq2.size(), script.getModifications());
        assertEquals(0, script.getLCSLength());
    }
}