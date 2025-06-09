package net.sf.javaml.tools.data;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

public class ARFFHandlerTest {

    private File testFile;
    private File testFileWithClassIndex;

    @Before
    public void setUp() throws IOException {
        // Create a temporary ARFF file for testing
        testFile = File.createTempFile("testARFF", ".arff");
        String arffContent = "@relation test\n" +
                             "@attribute attr1 numeric\n" +
                             "@attribute attr2 numeric\n" +
                             "@data\n" +
                             "1.0,2.0\n" +
                             "3.0,4.0\n";
        Files.write(Paths.get(testFile.getPath()), arffContent.getBytes());

        // Create another temporary ARFF file with class index for testing
        testFileWithClassIndex = File.createTempFile("testARFFWithClassIndex", ".arff");
        String arffContentWithClassIndex = "@relation test\n" +
                                           "@attribute attr1 numeric\n" +
                                           "@attribute attr2 numeric\n" +
                                           "@attribute class {A,B}\n" +
                                           "@data\n" +
                                           "1.0,2.0,A\n" +
                                           "3.0,4.0,B\n";
        Files.write(Paths.get(testFileWithClassIndex.getPath()), arffContentWithClassIndex.getBytes());
    }

    @Test
    public void testLoadARFF() throws FileNotFoundException {
        Dataset dataset = ARFFHandler.loadARFF(testFile);
        assertNotNull(dataset);
        assertEquals(2, dataset.size());

        Instance instance1 = dataset.get(0);
        assertEquals(1.0, instance1.value(0), 0.001);
        assertEquals(2.0, instance1.value(1), 0.001);

        Instance instance2 = dataset.get(1);
        assertEquals(3.0, instance2.value(0), 0.001);
        assertEquals(4.0, instance2.value(1), 0.001);
    }

    @Test
    public void testLoadARFFWithClassIndex() throws FileNotFoundException {
        Dataset dataset = ARFFHandler.loadARFF(testFileWithClassIndex, 2);
        assertNotNull(dataset);
        assertEquals(2, dataset.size());

        Instance instance1 = dataset.get(0);
        assertEquals(1.0, instance1.value(0), 0.001);
        assertEquals(2.0, instance1.value(1), 0.001);
        assertEquals("A", instance1.classValue());

        Instance instance2 = dataset.get(1);
        assertEquals(3.0, instance2.value(0), 0.001);
        assertEquals(4.0, instance2.value(1), 0.001);
        assertEquals("B", instance2.classValue());
    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadARFFWithNonExistentFile() throws FileNotFoundException {
        File nonExistentFile = new File("nonExistentFile.arff");
        ARFFHandler.loadARFF(nonExistentFile);
    }

    @Test
    public void testLoadARFFWithEmptyFile() throws FileNotFoundException, IOException {
        File emptyFile = File.createTempFile("emptyARFF", ".arff");
        Dataset dataset = ARFFHandler.loadARFF(emptyFile);
        assertNotNull(dataset);
        assertEquals(0, dataset.size());
    }

    @Test
    public void testLoadARFFWithInvalidData() throws FileNotFoundException, IOException {
        File invalidDataFile = File.createTempFile("invalidDataARFF", ".arff");
        String invalidContent = "@relation test\n" +
                                "@attribute attr1 numeric\n" +
                                "@attribute attr2 numeric\n" +
                                "@data\n" +
                                "invalid,data\n";
        Files.write(Paths.get(invalidDataFile.getPath()), invalidContent.getBytes());

        Dataset dataset = ARFFHandler.loadARFF(invalidDataFile);
        assertNotNull(dataset);
        assertEquals(1, dataset.size());

        Instance instance = dataset.get(0);
        assertTrue(Double.isNaN(instance.value(0)));
        assertTrue(Double.isNaN(instance.value(1)));
    }
}