package net.sf.sugar.fspath.cli;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import net.sf.sugar.fspath.FSPath;
import net.sf.sugar.fspath.FSPathFactory;
import net.sf.sugar.fspath.FSPathResult;
import net.sf.sugar.fspath.FSPathResultList;
import net.sf.sugar.fspath.cli.FSPathExplorer;
import net.sf.sugar.fspath.cli.Prompt;

public class FSPathExplorerTest {

    private FSPathExplorer explorer;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @Before
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        explorer = new FSPathExplorer();
        explorer.out = new PrintStream(outContent);
        explorer.err = new PrintStream(errContent);
    }

    @Test
    public void testConstructor() {
        assertNotNull(explorer.out);
        assertNotNull(explorer.err);
        assertNotNull(explorer.prompt);
        assertEquals("fspath>", explorer.prompt.getPromptText());
    }

    @Test
    public void testShowWelcome() {
        FSPathExplorer.showWelcome();
        assertTrue(outContent.toString().contains("FSPathExplorer"));
    }

    @Test
    public void testShowHelp() {
        FSPathExplorer.showHelp();
        assertTrue(outContent.toString().contains("FSPathExplorer help"));
    }

    @Test
    public void testConfigureExplorer() {
        String[] args = {"-path", "/test/path", "-find", "//dir[@name='test']"};
        explorer.configureExplorer(args);
        assertEquals(new File("/test/path"), explorer.rootPath);
        assertEquals("//dir[@name='test']", explorer.fsPathQuery);
    }

    @Test
    public void testCreateFSPathWithRootPath() {
        explorer.rootPath = new File("/test/path");
        explorer.createFSPath();
        assertNotNull(explorer.fsPath);
        assertEquals("/test/path", explorer.fsPath.getRootDirectory().getAbsolutePath());
    }

    @Test
    public void testCreateFSPathWithoutRootPath() {
        explorer.createFSPath();
        assertNotNull(explorer.fsPath);
    }

    @Test
    public void testStart() throws IOException {
        // Mocking Prompt to avoid actual user input
        explorer.prompt = new Prompt(System.in, explorer.out, explorer.err) {
            @Override
            public String readLine() throws IOException {
                return "exit";
            }
        };

        explorer.start();
        assertTrue(outContent.toString().contains("exiting....."));
    }

    @Test
    public void testStartWithFSPathQuery() throws IOException {
        // Mocking Prompt to avoid actual user input
        explorer.prompt = new Prompt(System.in, explorer.out, explorer.err) {
            @Override
            public String readLine() throws IOException {
                return "//dir[@name='test']";
            }
        };

        explorer.fsPath = FSPathFactory.newFSPath(new File("/test/path"));
        explorer.start();
        assertTrue(outContent.toString().contains("//dir[@name='test']"));
    }

    @Test
    public void testStartWithPWDCommand() throws IOException {
        // Mocking Prompt to avoid actual user input
        explorer.prompt = new Prompt(System.in, explorer.out, explorer.err) {
            @Override
            public String readLine() throws IOException {
                return "pwd";
            }
        };

        explorer.fsPath = FSPathFactory.newFSPath(new File("/test/path"));
        explorer.start();
        assertTrue(outContent.toString().contains("Current directory : /test/path"));
    }

    @Test
    public void testStartWithCDCommand() throws IOException {
        // Mocking Prompt to avoid actual user input
        explorer.prompt = new Prompt(System.in, explorer.out, explorer.err) {
            @Override
            public String readLine() throws IOException {
                return "cd /new/path";
            }
        };

        explorer.fsPath = FSPathFactory.newFSPath(new File("/test/path"));
        explorer.start();
        assertTrue(outContent.toString().contains("changed to : /new/path"));
    }

    @Test
    public void testStartWithHELPCommand() throws IOException {
        // Mocking Prompt to avoid actual user input
        explorer.prompt = new Prompt(System.in, explorer.out, explorer.err) {
            @Override
            public String readLine() throws IOException {
                return "help";
            }
        };

        explorer.start();
        assertTrue(outContent.toString().contains("FSPathExplorer help"));
    }
}