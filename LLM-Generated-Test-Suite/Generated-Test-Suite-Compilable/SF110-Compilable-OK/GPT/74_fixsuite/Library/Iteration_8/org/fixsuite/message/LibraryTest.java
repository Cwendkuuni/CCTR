package org.fixsuite.message;

import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.fixsuite.message.Library;
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

    @Before
    public void setUp() {
        library = new Library();
    }

    @Test
    public void testLoadFromDirectoryWithValidDirectory() throws IOException {
        File validDir = folder.newFolder("validDir");
        File subDir = new File(validDir, "subDir");
        subDir.mkdir();

        createFile(subDir, "Fields.xml");
        createFile(subDir, "Enums.xml");
        createFile(subDir, "Components.xml");
        createFile(subDir, "MsgType.xml");
        createFile(subDir, "MsgContents.xml");

        boolean result = library.loadFromDirectory(validDir.getAbsolutePath());
        assertTrue("Expected loadFromDirectory to return true for valid directory", result);

        List<DictionaryInfo> dictionaries = library.getDictionaries();
        assertEquals("Expected one dictionary to be loaded", 1, dictionaries.size());
    }

    @Test
    public void testLoadFromDirectoryWithEmptyDirectory() throws IOException {
        File emptyDir = folder.newFolder("emptyDir");

        boolean result = library.loadFromDirectory(emptyDir.getAbsolutePath());
        assertFalse("Expected loadFromDirectory to return false for empty directory", result);

        List<DictionaryInfo> dictionaries = library.getDictionaries();
        assertTrue("Expected no dictionaries to be loaded", dictionaries.isEmpty());
    }

    @Test
    public void testLoadFromDirectoryWithNonDirectoryPath() {
        boolean result = library.loadFromDirectory("nonExistentPath");
        assertFalse("Expected loadFromDirectory to return false for non-directory path", result);

        List<DictionaryInfo> dictionaries = library.getDictionaries();
        assertTrue("Expected no dictionaries to be loaded", dictionaries.isEmpty());
    }

    @Test
    public void testGetDictionariesWhenEmpty() {
        List<DictionaryInfo> dictionaries = library.getDictionaries();
        assertNotNull("Expected getDictionaries to return a non-null list", dictionaries);
        assertTrue("Expected getDictionaries to return an empty list", dictionaries.isEmpty());
    }

    @Test
    public void testGetDictionaryWithExistingVersion() throws IOException {
        File validDir = folder.newFolder("validDir");
        File subDir = new File(validDir, "subDir");
        subDir.mkdir();

        createFile(subDir, "Fields.xml");
        createFile(subDir, "Enums.xml");
        createFile(subDir, "Components.xml");
        createFile(subDir, "MsgType.xml");
        createFile(subDir, "MsgContents.xml");

        library.loadFromDirectory(validDir.getAbsolutePath());

        DictionaryInfo dictionary = library.getDictionary("subDir");
        assertNotNull("Expected getDictionary to return a non-null DictionaryInfo for existing version", dictionary);
    }

    @Test
    public void testGetDictionaryWithNonExistingVersion() {
        DictionaryInfo dictionary = library.getDictionary("nonExistentVersion");
        assertNull("Expected getDictionary to return null for non-existing version", dictionary);
    }

    private void createFile(File dir, String fileName) throws IOException {
        File file = new File(dir, fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("<xml></xml>");
        }
    }
}