package weka.classifiers.bayes.net.search.ci;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import weka.classifiers.bayes.BayesNet;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import java.io.FileReader;

public class ICSSearchAlgorithmTest {

    private ICSSearchAlgorithm icsSearchAlgorithm;
    private Instances instances;
    private BayesNet bayesNet;

    @Before
    public void setUp() throws Exception {
        icsSearchAlgorithm = new ICSSearchAlgorithm();
        instances = new Instances(new FileReader("C:\\eclipse\\workspace\\weka\\data\\contact-lenses.arff"));
        instances.setClassIndex(instances.numAttributes() - 1);
        bayesNet = new BayesNet();
    }

    @Test
    public void testConstructor() {
        assertEquals(2, icsSearchAlgorithm.getMaxCardinality());
    }

    @Test
    public void testSetMaxCardinality() {
        icsSearchAlgorithm.setMaxCardinality(3);
        assertEquals(3, icsSearchAlgorithm.getMaxCardinality());
    }

    @Test
    public void testGetMaxCardinality() {
        assertEquals(2, icsSearchAlgorithm.getMaxCardinality());
    }

    @Test
    public void testName() {
        String attributeName = icsSearchAlgorithm.name(0);
        assertNotNull(attributeName);
    }

    @Test
    public void testMaxn() {
        assertEquals(instances.numAttributes(), icsSearchAlgorithm.maxn());
    }

    @Test
    public void testSearch() throws Exception {
        icsSearchAlgorithm.search(bayesNet, instances);
        assertNotNull(bayesNet);
    }

    @Test
    public void testCalcDependencyGraph() {
        boolean[][] edges = new boolean[icsSearchAlgorithm.maxn() + 1][icsSearchAlgorithm.maxn()];
        ICSSearchAlgorithm.SeparationSet[][] sepsets = new ICSSearchAlgorithm.SeparationSet[icsSearchAlgorithm.maxn() + 1][icsSearchAlgorithm.maxn()];
        icsSearchAlgorithm.calcDependencyGraph(edges, sepsets);
        assertNotNull(edges);
        assertNotNull(sepsets);
    }

    @Test
    public void testExistsSepSet() {
        boolean[][] edges = new boolean[icsSearchAlgorithm.maxn()][icsSearchAlgorithm.maxn()];
        ICSSearchAlgorithm.SeparationSet sepSet = icsSearchAlgorithm.existsSepSet(0, 1, 1, edges);
        assertNull(sepSet); // Assuming no separation set exists initially
    }

    @Test
    public void testNext() {
        boolean[][] edges = new boolean[icsSearchAlgorithm.maxn()][icsSearchAlgorithm.maxn()];
        int next = icsSearchAlgorithm.next(-1, 0, 1, edges);
        assertTrue(next >= 0);
    }

    @Test
    public void testCalcVeeNodes() {
        boolean[][] edges = new boolean[icsSearchAlgorithm.maxn()][icsSearchAlgorithm.maxn()];
        boolean[][] arrows = new boolean[icsSearchAlgorithm.maxn()][icsSearchAlgorithm.maxn()];
        ICSSearchAlgorithm.SeparationSet[][] sepsets = new ICSSearchAlgorithm.SeparationSet[icsSearchAlgorithm.maxn()][icsSearchAlgorithm.maxn()];
        icsSearchAlgorithm.calcVeeNodes(edges, arrows, sepsets);
        assertNotNull(arrows);
    }

    @Test
    public void testCalcArcDirections() {
        boolean[][] edges = new boolean[icsSearchAlgorithm.maxn()][icsSearchAlgorithm.maxn()];
        boolean[][] arrows = new boolean[icsSearchAlgorithm.maxn()][icsSearchAlgorithm.maxn()];
        icsSearchAlgorithm.calcArcDirections(edges, arrows);
        assertNotNull(arrows);
    }

    @Test
    public void testListOptions() {
        assertNotNull(icsSearchAlgorithm.listOptions());
    }

    @Test
    public void testSetOptions() throws Exception {
        String[] options = {"-cardinality", "3"};
        icsSearchAlgorithm.setOptions(options);
        assertEquals(3, icsSearchAlgorithm.getMaxCardinality());
    }

    @Test
    public void testGetOptions() {
        String[] options = icsSearchAlgorithm.getOptions();
        assertNotNull(options);
        assertTrue(options.length > 0);
    }

    @Test
    public void testMaxCardinalityTipText() {
        assertNotNull(icsSearchAlgorithm.maxCardinalityTipText());
    }

    @Test
    public void testGlobalInfo() {
        assertNotNull(icsSearchAlgorithm.globalInfo());
    }

    @Test
    public void testGetRevision() {
        assertNotNull(icsSearchAlgorithm.getRevision());
    }
}