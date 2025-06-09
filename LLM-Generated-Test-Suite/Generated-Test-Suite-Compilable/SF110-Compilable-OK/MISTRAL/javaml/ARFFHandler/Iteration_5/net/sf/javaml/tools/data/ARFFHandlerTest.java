package net.sf.javaml.tools.data;

import static org.junit.Assert.*;
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

    @Test
    public void testLoadARFFWithoutClassIndex() throws FileNotFoundException, IOException {
        // Create a temporary ARFF file
        File tempFile = File.createTempFile("testARFF", ".arff");
        String arffContent = "@relation test\n" +
                             "@attribute attr1 numeric\n" +
                             "@attribute attr2 numeric\n" +
                             "@data\n" +
                             "1.0,2.0\n" +
                             "3.0,4.0\n";
        Files.write(Paths.get(tempFile.getPath()), arffContent.getBytes());

        // Load the ARFF file
        Dataset dataset = ARFFHandler.loadARFF(tempFile);

        // Verify the dataset
        assertEquals(2, dataset.size());
        Instance instance1 = dataset.get(0);
        assertEquals(1.0, instance1.value(0), 0.001);
        assertEquals(2.0, instance1.value(1), 0.001);
        Instance instance2 = dataset.get(1);
        assertEquals(3.0, instance2.value(0), 0.001);
        assertEquals(4.0, instance2.value(1), 0.001);

        // Clean up
        tempFile.delete();
    }

    @Test
    public void testLoadARFFWithClassIndex() throws FileNotFoundException, IOException {
        // Create a temporary ARFF file
        File tempFile = File.createTempFile("testARFF", ".arff");
        String arffContent = "@relation test\n" +
                             "@attribute attr1 numeric\n" +
                             "@attribute attr2 numeric\n" +
                             "@attribute class {A,B}\n" +
                             "@data\n" +
                             "1.0,2.0,A\n" +
                             "3.0,4.0,B\n";
        Files.write(Paths.get(tempFile.getPath()), arffContent.getBytes());

        // Load the ARFF file with class index
        Dataset dataset = ARFFHandler.loadARFF(tempFile, 2);

        // Verify the dataset
        assertEquals(2, dataset.size());
        Instance instance1 = dataset.get(0);
        assertEquals(1.0, instance1.value(0), 0.001);
        assertEquals(2.0, instance1.value(1), 0.001);
        assertEquals("A", instance1.classValue());
        Instance instance2 = dataset.get(1);
        assertEquals(3.0, instance2.value(0), 0.001);
        assertEquals(4.0, instance2.value(1), 0.001);
        assertEquals("B", instance2.classValue());

        // Clean up
        tempFile.delete();
    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadARFFWithNonExistentFile() throws FileNotFoundException {
        // Try to load a non-existent ARFF file
        ARFFHandler.loadARFF(new File("non_existent_file.arff"));
    }

    @Test
    public void testLoadARFFWithInvalidData() throws FileNotFoundException, IOException {
        // Create a temporary ARFF file with invalid data
        File tempFile = File.createTempFile("testARFF", ".arff");
        String arffContent = "@relation test\n" +
                             "@attribute attr1 numeric\n" +
                             "@attribute attr2 numeric\n" +
                             "@data\n" +
                             "1.0,invalid\n" +
                             "3.0,4.0\n";
        Files.write(Paths.get(tempFile.getPath()), arffContent.getBytes());

        // Load the ARFF file
        Dataset dataset = ARFFHandler.loadARFF(tempFile);

        // Verify the dataset
        assertEquals(2, dataset.size());
        Instance instance1 = dataset.get(0);
        assertEquals(1.0, instance1.value(0), 0.001);
        assertTrue(Double.isNaN(instance1.value(1)));
        Instance instance2 = dataset.get(1);
        assertEquals(3.0, instance2.value(0), 0.001);
        assertEquals(4.0, instance2.value(1), 0.001);

        // Clean up
        tempFile.delete();
    }
}