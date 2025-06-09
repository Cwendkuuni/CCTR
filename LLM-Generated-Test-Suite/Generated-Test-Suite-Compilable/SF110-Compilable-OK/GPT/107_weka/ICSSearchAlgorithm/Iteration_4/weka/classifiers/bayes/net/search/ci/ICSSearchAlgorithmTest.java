package weka.classifiers.bayes.net.search.ci;

import org.junit.Before;
import org.junit.Test;
import weka.classifiers.bayes.BayesNet;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import static org.junit.Assert.*;

public class ICSSearchAlgorithmTest {

    private ICSSearchAlgorithm icsSearchAlgorithm;
    private Instances instances;
    private BayesNet bayesNet;

    @Before
    public void setUp() throws Exception {
        icsSearchAlgorithm = new ICSSearchAlgorithm();
        DataSource source = new DataSource("path/to/contact-lenses.arff");
        instances = source.getDataSet();
        instances.setClassIndex(instances.numAttributes() - 1);
        bayesNet = new BayesNet();
    }

    @Test
    public void testDefaultMaxCardinality() {
        assertEquals(2, icsSearchAlgorithm.getMaxCardinality());
    }

    @Test
    public void testSetMaxCardinality() {
        icsSearchAlgorithm.setMaxCardinality(3);
        assertEquals(3, icsSearchAlgorithm.getMaxCardinality());
    }

    @Test
    public void testName() {
        String attributeName = icsSearchAlgorithm.name(0);
        assertEquals(instances.attribute(0).name(), attributeName);
    }

    @Test
    public void testMaxn() {
        assertEquals(instances.numAttributes(), icsSearchAlgorithm.maxn());
    }

    @Test
    public void testSearch() throws Exception {
        icsSearchAlgorithm.search(bayesNet, instances);
        // Add assertions to verify the expected structure of the BayesNet
        // This might include checking the number of nodes, edges, etc.
    }

    @Test
    public void testListOptions() {
        assertNotNull(icsSearchAlgorithm.listOptions());
    }

    @Test
    public void testSetOptions() throws Exception {
        String[] options = {"-cardinality", "4"};
        icsSearchAlgorithm.setOptions(options);
        assertEquals(4, icsSearchAlgorithm.getMaxCardinality());
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

    // Additional tests for protected methods can be added here if necessary
    // These might require using reflection or making the methods package-private for testing purposes
}