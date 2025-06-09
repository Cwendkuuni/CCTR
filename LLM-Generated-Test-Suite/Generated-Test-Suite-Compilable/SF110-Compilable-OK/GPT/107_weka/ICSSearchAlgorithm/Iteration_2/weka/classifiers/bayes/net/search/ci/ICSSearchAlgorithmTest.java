package weka.classifiers.bayes.net.search.ci;

import org.junit.Before;
import org.junit.Test;
import weka.classifiers.bayes.BayesNet;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import static org.junit.Assert.*;

public class ICSSearchAlgorithmTest {

    private ICSSearchAlgorithm algorithm;
    private Instances instances;

    @Before
    public void setUp() throws Exception {
        algorithm = new ICSSearchAlgorithm();
        DataSource source = new DataSource("path/to/contact-lenses.arff");
        instances = source.getDataSet();
        if (instances.classIndex() == -1) {
            instances.setClassIndex(instances.numAttributes() - 1);
        }
    }

    @Test
    public void testConstructor() {
        assertEquals(2, algorithm.getMaxCardinality());
    }

    @Test
    public void testSetMaxCardinality() {
        algorithm.setMaxCardinality(3);
        assertEquals(3, algorithm.getMaxCardinality());
    }

    @Test
    public void testGetMaxCardinality() {
        assertEquals(2, algorithm.getMaxCardinality());
    }

    @Test
    public void testName() {
        String attributeName = algorithm.name(0);
        assertNotNull(attributeName);
        assertEquals(instances.attribute(0).name(), attributeName);
    }

    @Test
    public void testMaxn() {
        assertEquals(instances.numAttributes(), algorithm.maxn());
    }

    @Test
    public void testSearch() throws Exception {
        BayesNet bayesNet = new BayesNet();
        algorithm.search(bayesNet, instances);
        // Add assertions to verify the expected state of bayesNet after search
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