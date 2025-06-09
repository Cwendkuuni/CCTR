package net.sf.javaml.tools.data;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.io.*;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.ARFFHandler;

public class ARFFHandlerTest {

    private File validARFFFile;
    private File validARFFFileWithClassIndex;
    private File emptyFile;
    private File fileWithComments;
    private File fileWithInvalidData;

    @Before
    public void setUp() throws IOException {
        // Create a valid ARFF file without class index
        validARFFFile = File.createTempFile("validARFF", ".arff");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(validARFFFile))) {
            writer.write("@relation test\n");
            writer.write("@attribute attr1 numeric\n");
            writer.write("@attribute attr2 numeric\n");
            writer.write("@data\n");
            writer.write("1.0,2.0\n");
            writer.write("3.0,4.0\n");
        }

        // Create a valid ARFF file with class index
        validARFFFileWithClassIndex = File.createTempFile("validARFFWithClass", ".arff");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(validARFFFileWithClassIndex))) {
            writer.write("@relation test\n");
            writer.write("@attribute attr1 numeric\n");
            writer.write("@attribute attr2 numeric\n");
            writer.write("@attribute class {A, B}\n");
            writer.write("@data\n");
            writer.write("1.0,2.0,A\n");
            writer.write("3.0,4.0,B\n");
        }

        // Create an empty file
        emptyFile = File.createTempFile("empty", ".arff");

        // Create a file with comments
        fileWithComments = File.createTempFile("comments", ".arff");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileWithComments))) {
            writer.write("% This is a comment\n");
            writer.write("@relation test\n");
            writer.write("@attribute attr1 numeric\n");
            writer.write("@attribute attr2 numeric\n");
            writer.write("@data\n");
            writer.write("1.0,2.0\n");
        }

        // Create a file with invalid data
        fileWithInvalidData = File.createTempFile("invalidData", ".arff");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileWithInvalidData))) {
            writer.write("@relation test\n");
            writer.write("@attribute attr1 numeric\n");
            writer.write("@attribute attr2 numeric\n");
            writer.write("@data\n");
            writer.write("1.0,abc\n");
        }
    }

    @Test
    public void testLoadARFFWithoutClassIndex() throws FileNotFoundException {
        Dataset dataset = ARFFHandler.loadARFF(validARFFFile);
        assertEquals(2, dataset.size());
        Instance instance1 = dataset.instance(0);
        assertEquals(1.0, instance1.value(0), 0.001);
        assertEquals(2.0, instance1.value(1), 0.001);
        assertNull(instance1.classValue());
    }

    @Test
    public void testLoadARFFWithClassIndex() throws FileNotFoundException {
        Dataset dataset = ARFFHandler.loadARFF(validARFFFileWithClassIndex, 2);
        assertEquals(2, dataset.size());
        Instance instance1 = dataset.instance(0);
        assertEquals(1.0, instance1.value(0), 0.001);
        assertEquals(2.0, instance1.value(1), 0.001);
        assertEquals("A", instance1.classValue());
    }

    @Test
    public void testLoadEmptyARFF() throws FileNotFoundException {
        Dataset dataset = ARFFHandler.loadARFF(emptyFile);
        assertEquals(0, dataset.size());
    }

    @Test
    public void testLoadARFFWithComments() throws FileNotFoundException {
        Dataset dataset = ARFFHandler.loadARFF(fileWithComments);
        assertEquals(1, dataset.size());
        Instance instance1 = dataset.instance(0);
        assertEquals(1.0, instance1.value(0), 0.001);
        assertEquals(2.0, instance1.value(1), 0.001);
    }

    @Test
    public void testLoadARFFWithInvalidData() throws FileNotFoundException {
        Dataset dataset = ARFFHandler.loadARFF(fileWithInvalidData);
        assertEquals(1, dataset.size());
        Instance instance1 = dataset.instance(0);
        assertEquals(1.0, instance1.value(0), 0.001);
        assertTrue(Double.isNaN(instance1.value(1)));
    }
}