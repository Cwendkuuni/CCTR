package weka.classifiers.bayes.net.search.ci;

import org.junit.Before;
import org.junit.Test;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.net.search.ci.ICSSearchAlgorithm;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import static org.junit.Assert.*;

public class ICSSearchAlgorithmTest {

    private ICSSearchAlgorithm algorithm;
    private Instances instances;
    private BayesNet bayesNet;

    @Before
    public void setUp() throws Exception {
        algorithm = new ICSSearchAlgorithm();
        DataSource source = new DataSource("path/to/contact-lenses.arff");
        instances = source.getDataSet();
        instances.setClassIndex(instances.numAttributes() - 1);
        bayesNet = new BayesNet();
    }

    @Test
    public void testDefaultMaxCardinality() {
        assertEquals(2, algorithm.getMaxCardinality());
    }

    @Test
    public void testSetMaxCardinality() {
        algorithm.setMaxCardinality(3);
        assertEquals(3, algorithm.getMaxCardinality());
    }

    @Test
    public void testName() {
        String attributeName = algorithm.name(0);
        assertEquals(instances.attribute(0).name(), attributeName);
    }

    @Test
    public void testMaxn() {
        assertEquals(instances.numAttributes(), algorithm.maxn());
    }

    @Test
    public void testSearch() throws Exception {
        algorithm.search(bayesNet, instances);
        // Add assertions to verify the expected state of bayesNet after search
        assertNotNull(bayesNet);
    }

    @Test
    public void testCalcDependencyGraph() {
        boolean[][] edges = new boolean[algorithm.maxn() + 1][];
        ICSSearchAlgorithm.SeparationSet[][] sepsets = new ICSSearchAlgorithm.SeparationSet[algorithm.maxn() + 1][];
        for (int i = 0; i < algorithm.maxn() + 1; ++i) {
            edges[i] = new boolean[algorithm.maxn()];
            sepsets[i] = new ICSSearchAlgorithm.SeparationSet[algorithm.maxn()];
        }
        algorithm.calcDependencyGraph(edges, sepsets);
        // Add assertions to verify the expected state of edges and sepsets
    }

    @Test
    public void testExistsSepSet() {
        boolean[][] edges = new boolean[algorithm.maxn()][algorithm.maxn()];
        for (int i = 0; i < algorithm.maxn(); ++i) {
            for (int j = 0; j < algorithm.maxn(); ++j) {
                edges[i][j] = true;
            }
        }
        ICSSearchAlgorithm.SeparationSet sepSet = algorithm.existsSepSet(0, 1, 1, edges);
        // Add assertions to verify the expected state of sepSet
        assertNotNull(sepSet);
    }

    @Test
    public void testNext() {
        boolean[][] edges = new boolean[algorithm.maxn()][algorithm.maxn()];
        for (int i = 0; i < algorithm.maxn(); ++i) {
            for (int j = 0; j < algorithm.maxn(); ++j) {
                edges[i][j] = true;
            }
        }
        int next = algorithm.next(-1, 0, 1, edges);
        assertEquals(0, next);
    }

    @Test
    public void testCalcVeeNodes() {
        boolean[][] edges = new boolean[algorithm.maxn()][algorithm.maxn()];
        boolean[][] arrows = new boolean[algorithm.maxn()][algorithm.maxn()];
        ICSSearchAlgorithm.SeparationSet[][] sepsets = new ICSSearchAlgorithm.SeparationSet[algorithm.maxn()][algorithm.maxn()];
        for (int i = 0; i < algorithm.maxn(); ++i) {
            for (int j = 0; j < algorithm.maxn(); ++j) {
                edges[i][j] = true;
                sepsets[i][j] = algorithm.new SeparationSet();
            }
        }
        algorithm.calcVeeNodes(edges, arrows, sepsets);
        // Add assertions to verify the expected state of arrows
    }

    @Test
    public void testCalcArcDirections() {
        boolean[][] edges = new boolean[algorithm.maxn()][algorithm.maxn()];
        boolean[][] arrows = new boolean[algorithm.maxn()][algorithm.maxn()];
        for (int i = 0; i < algorithm.maxn(); ++i) {
            for (int j = 0; j < algorithm.maxn(); ++j) {
                edges[i][j] = true;
            }
        }
        algorithm.calcArcDirections(edges, arrows);
        // Add assertions to verify the expected state of arrows
    }

    @Test
    public void testListOptions() {
        assertNotNull(algorithm.listOptions());
    }

    @Test
    public void testSetOptions() throws Exception {
        String[] options = {"-cardinality", "3"};
        algorithm.setOptions(options);
        assertEquals(3, algorithm.getMaxCardinality());
    }

    @Test
    public void testGetOptions() {
        String[] options = algorithm.getOptions();
        assertNotNull(options);
        assertTrue(options.length > 0);
    }

    @Test
    public void testMaxCardinalityTipText() {
        assertNotNull(algorithm.maxCardinalityTipText());
    }

    @Test
    public void testGlobalInfo() {
        assertNotNull(algorithm.globalInfo());
    }

    @Test
    public void testGetRevision() {
        assertNotNull(algorithm.getRevision());
    }
}