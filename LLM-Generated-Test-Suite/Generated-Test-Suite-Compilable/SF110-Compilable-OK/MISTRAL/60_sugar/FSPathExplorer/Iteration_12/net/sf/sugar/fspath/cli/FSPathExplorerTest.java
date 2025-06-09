package net.sf.sugar.fspath.cli;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import net.sf.sugar.fspath.FSPath;
import net.sf.sugar.fspath.FSPathFactory;
import net.sf.sugar.fspath.FSPathResult;
import net.sf.sugar.fspath.FSPathResultList;

public class FSPathExplorerTest {

    private FSPathExplorer explorer;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @Before
    public void setUp() {
        explorer = new FSPathExplorer();
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
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
        String[] args = {"-path", "/tmp", "-find", "//dir[@name='test']"};
        explorer.configureExplorer(args);
        assertEquals(new File("/tmp"), explorer.rootPath);
        assertEquals("//dir[@name='test']", explorer.fsPathQuery);
    }

    @Test
    public void testCreateFSPathWithRootPath() {
        explorer.rootPath = new File("/tmp");
        explorer.createFSPath();
        assertNotNull(explorer.fsPath);
        assertEquals("/tmp", explorer.fsPath.getRootDirectory().getAbsolutePath());
    }

    @Test
    public void testCreateFSPathWithoutRootPath() {
        explorer.createFSPath();
        assertNotNull(explorer.fsPath);
    }

    @Test
    public void testStartExitCommand() throws Exception {
        String input = "exit\n";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
        explorer.start();
        assertTrue(outContent.toString().contains("exiting....."));
    }

    @Test
    public void testStartPwdCommand() throws Exception {
        String input = "pwd\nexit\n";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
        explorer.start();
        assertTrue(outContent.toString().contains("Current directory"));
    }

    @Test
    public void testStartCdCommand() throws Exception {
        String input = "cd /tmp\nexit\n";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
        explorer.start();
        assertTrue(outContent.toString().contains("changed to : /tmp"));
    }

    @Test
    public void testStartHelpCommand() throws Exception {
        String input = "help\nexit\n";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
        explorer.start();
        assertTrue(outContent.toString().contains("FSPathExplorer help"));
    }

    @Test
    public void testStartFSPathQuery() throws Exception {
        String input = "//dir[@name='test']\nexit\n";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
        explorer.start();
        // Assuming the query returns some results
        assertTrue(outContent.toString().contains("test"));
    }
}