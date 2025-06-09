package net.sf.javaml.tools.data;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.ARFFHandler;

public class ARFFHandlerTest {

    private File validArffFile;
    private File invalidArffFile;
    private File emptyArffFile;
    private File arffWithClassIndex;

    @Before
    public void setUp() throws IOException {
        // Create a valid ARFF file
        validArffFile = File.createTempFile("valid", ".arff");
        try (PrintWriter writer = new PrintWriter(new FileWriter(validArffFile))) {
            writer.println("@relation test");
            writer.println("@attribute attr1 numeric");
            writer.println("@attribute attr2 numeric");
            writer.println("@data");
            writer.println("1.0,2.0");
            writer.println("3.0,4.0");
        }

        // Create an invalid ARFF file
        invalidArffFile = File.createTempFile("invalid", ".arff");
        try (PrintWriter writer = new PrintWriter(new FileWriter(invalidArffFile))) {
            writer.println("@relation test");
            writer.println("@attribute attr1 numeric");
            writer.println("@attribute attr2 numeric");
            writer.println("@data");
            writer.println("1.0,abc");
        }

        // Create an empty ARFF file
        emptyArffFile = File.createTempFile("empty", ".arff");

        // Create an ARFF file with a class index
        arffWithClassIndex = File.createTempFile("classIndex", ".arff");
        try (PrintWriter writer = new PrintWriter(new FileWriter(arffWithClassIndex))) {
            writer.println("@relation test");
            writer.println("@attribute attr1 numeric");
            writer.println("@attribute class {A,B}");
            writer.println("@data");
            writer.println("1.0,A");
            writer.println("2.0,B");
        }
    }

    @Test
    public void testLoadARFFValidFile() throws Exception {
        Dataset dataset = ARFFHandler.loadARFF(validArffFile);
        assertNotNull(dataset);
        assertEquals(2, dataset.size());
        for (Instance instance : dataset) {
            assertEquals(2, instance.noAttributes());
        }
    }

    @Test
    public void testLoadARFFInvalidFile() {
        try {
            ARFFHandler.loadARFF(invalidArffFile);
            fail("Expected NumberFormatException");
        } catch (NumberFormatException e) {
            // Expected exception
        } catch (Exception e) {
            fail("Expected NumberFormatException, but got " + e.getClass().getSimpleName());
        }
    }

    @Test
    public void testLoadARFFEmptyFile() throws Exception {
        Dataset dataset = ARFFHandler.loadARFF(emptyArffFile);
        assertNotNull(dataset);
        assertEquals(0, dataset.size());
    }

    @Test
    public void testLoadARFFWithClassIndex() throws Exception {
        Dataset dataset = ARFFHandler.loadARFF(arffWithClassIndex, 1);
        assertNotNull(dataset);
        assertEquals(2, dataset.size());
        for (Instance instance : dataset) {
            assertEquals(1, instance.noAttributes());
            assertNotNull(instance.classValue());
        }
    }

    @Test
    public void testLoadARFFWithInvalidClassIndex() {
        try {
            ARFFHandler.loadARFF(validArffFile, 5);
            fail("Expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            fail("Expected ArrayIndexOutOfBoundsException, but got " + e.getClass().getSimpleName());
        }
    }
}