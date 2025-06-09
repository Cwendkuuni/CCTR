package org.apache.commons.collections4.sequence;

import org.apache.commons.collections4.sequence.SequencesComparator;
import org.apache.commons.collections4.sequence.EditScript;
import org.apache.commons.collections4.sequence.KeepCommand;
import org.apache.commons.collections4.sequence.InsertCommand;
import org.apache.commons.collections4.sequence.DeleteCommand;
import org.apache.commons.collections4.Equator;
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
        sequence2 = Arrays.asList("a", "x", "c", "d");
        comparator = new SequencesComparator<>(sequence1, sequence2);
    }

    @Test
    public void testGetScriptWithDefaultEquator() {
        EditScript<String> script = comparator.getScript();
        assertEquals(4, script.getModifications());
        assertTrue(script.getLCSLength() == 3);
        assertTrue(script.getModifications() == 1);
    }

    @Test
    public void testGetScriptWithCustomEquator() {
        Equator<String> customEquator = new Equator<String>() {
            @Override
            public boolean equate(String o1, String o2) {
                return o1.equalsIgnoreCase(o2);
            }

            @Override
            public int hash(String o) {
                return o.toLowerCase().hashCode();
            }
        };

        SequencesComparator<String> customComparator = new SequencesComparator<>(sequence1, sequence2, customEquator);
        EditScript<String> script = customComparator.getScript();
        assertEquals(4, script.getModifications());
        assertTrue(script.getLCSLength() == 3);
        assertTrue(script.getModifications() == 1);
    }

    @Test
    public void testGetScriptWithIdenticalSequences() {
        SequencesComparator<String> identicalComparator = new SequencesComparator<>(sequence1, sequence1);
        EditScript<String> script = identicalComparator.getScript();
        assertEquals(0, script.getModifications());
        assertTrue(script.getLCSLength() == sequence1.size());
    }

    @Test
    public void testGetScriptWithEmptySequences() {
        SequencesComparator<String> emptyComparator = new SequencesComparator<>(Arrays.asList(), Arrays.asList());
        EditScript<String> script = emptyComparator.getScript();
        assertEquals(0, script.getModifications());
        assertTrue(script.getLCSLength() == 0);
    }

    @Test
    public void testGetScriptWithOneEmptySequence() {
        SequencesComparator<String> oneEmptyComparator = new SequencesComparator<>(sequence1, Arrays.asList());
        EditScript<String> script = oneEmptyComparator.getScript();
        assertEquals(sequence1.size(), script.getModifications());
        assertTrue(script.getLCSLength() == 0);
    }

    @Test
    public void testGetScriptWithDifferentSequences() {
        List<String> seq1 = Arrays.asList("a", "b", "c");
        List<String> seq2 = Arrays.asList("x", "y", "z");
        SequencesComparator<String> differentComparator = new SequencesComparator<>(seq1, seq2);
        EditScript<String> script = differentComparator.getScript();
        assertEquals(seq1.size() + seq2.size(), script.getModifications());
        assertTrue(script.getLCSLength() == 0);
    }
}