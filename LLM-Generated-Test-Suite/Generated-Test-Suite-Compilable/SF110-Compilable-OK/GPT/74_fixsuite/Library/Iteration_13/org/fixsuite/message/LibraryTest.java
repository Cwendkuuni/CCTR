package org.fixsuite.message;

import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.fixsuite.message.info.DictionaryInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class LibraryTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private Library library;
    private static Logger logger;

    @BeforeClass
    public static void setUpClass() {
        logger = LoggerFactory.getLogger(LibraryTest.class);
    }

    @Before
    public void setUp() {
        library = new Library();
    }

    @Test
    public void testLoadFromDirectoryWithValidDirectory() throws IOException {
        File validDir = folder.newFolder("validDir");
        createDummyFiles(validDir);

        boolean result = library.loadFromDirectory(validDir.getAbsolutePath());

        assertTrue("Expected loadFromDirectory to return true for a valid directory", result);
        assertFalse("Expected dictionaries to be loaded", library.getDictionaries().isEmpty());
    }

    @Test
    public void testLoadFromDirectoryWithEmptyDirectory() throws IOException {
        File emptyDir = folder.newFolder("emptyDir");

        boolean result = library.loadFromDirectory(emptyDir.getAbsolutePath());

        assertFalse("Expected loadFromDirectory to return false for an empty directory", result);
        assertTrue("Expected no dictionaries to be loaded", library.getDictionaries().isEmpty());
    }

    @Test
    public void testLoadFromDirectoryWithInvalidPath() {
        boolean result = library.loadFromDirectory("invalidPath");

        assertFalse("Expected loadFromDirectory to return false for an invalid path", result);
        assertTrue("Expected no dictionaries to be loaded", library.getDictionaries().isEmpty());
    }

    @Test
    public void testGetDictionaries() throws IOException {
        File validDir = folder.newFolder("validDir");
        createDummyFiles(validDir);

        library.loadFromDirectory(validDir.getAbsolutePath());
        List<DictionaryInfo> dictionaries = library.getDictionaries();

        assertNotNull("Expected non-null list of dictionaries", dictionaries);
        assertFalse("Expected non-empty list of dictionaries", dictionaries.isEmpty());
    }

    @Test
    public void testGetDictionary() throws IOException {
        File validDir = folder.newFolder("validDir");
        createDummyFiles(validDir);

        library.loadFromDirectory(validDir.getAbsolutePath());
        DictionaryInfo dictionary = library.getDictionary("validDir");

        assertNotNull("Expected non-null dictionary for valid version", dictionary);
    }

    @Test
    public void testGetDictionaryWithInvalidVersion() {
        DictionaryInfo dictionary = library.getDictionary("invalidVersion");

        assertNull("Expected null dictionary for invalid version", dictionary);
    }

    private void createDummyFiles(File dir) throws IOException {
        createFile(dir, "Fields.xml");
        createFile(dir, "Enums.xml");
        createFile(dir, "Components.xml");
        createFile(dir, "MsgType.xml");
        createFile(dir, "MsgContents.xml");
    }

    private void createFile(File dir, String fileName) throws IOException {
        File file = new File(dir, fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("<xml></xml>");
        }
    }
}