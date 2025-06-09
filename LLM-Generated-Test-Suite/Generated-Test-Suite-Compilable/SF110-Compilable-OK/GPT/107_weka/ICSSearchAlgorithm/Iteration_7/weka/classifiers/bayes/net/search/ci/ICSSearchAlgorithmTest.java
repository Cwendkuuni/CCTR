package weka.classifiers.bayes.net.search.ci;

import org.junit.Before;
import org.junit.Test;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.net.search.ci.ICSSearchAlgorithm;
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
        assertNotNull(bayesNet);
        assertEquals(instances.numAttributes(), bayesNet.getNrOfNodes());
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
        String tipText = icsSearchAlgorithm.maxCardinalityTipText();
        assertNotNull(tipText);
        assertFalse(tipText.isEmpty());
    }

    @Test
    public void testGlobalInfo() {
        String globalInfo = icsSearchAlgorithm.globalInfo();
        assertNotNull(globalInfo);
        assertFalse(globalInfo.isEmpty());
    }

    @Test
    public void testGetRevision() {
        String revision = icsSearchAlgorithm.getRevision();
        assertNotNull(revision);
        assertFalse(revision.isEmpty());
    }
}