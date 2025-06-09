package net.sf.sugar.fspath.cli;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import net.sf.sugar.fspath.FSPath;
import net.sf.sugar.fspath.FSPathFactory;
import net.sf.sugar.fspath.FSPathResult;
import net.sf.sugar.fspath.FSPathResultList;
import org.junit.Before;
import org.junit.Test;

public class FSPathExplorerTest {

    private FSPathExplorer explorer;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;
    private PrintStream originalOut;
    private PrintStream originalErr;

    @Before
    public void setUp() {
        explorer = new FSPathExplorer();
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        originalOut = System.out;
        originalErr = System.err;
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void testShowWelcome() {
        FSPathExplorer.showWelcome();
        String output = outContent.toString();
        assertTrue(output.contains("FSPathExplorer"));
        assertTrue(output.contains("Type 'help' for commands"));
    }

    @Test
    public void testShowHelp() {
        FSPathExplorer.showHelp();
        String output = outContent.toString();
        assertTrue(output.contains("FSPathExplorer help"));
        assertTrue(output.contains("Examples :"));
    }

    @Test
    public void testConfigureExplorerWithPathAndQuery() {
        String[] args = {"-path", "/some/path", "-find", "//dir[@name='foo']"};
        explorer.configureExplorer(args);
        assertEquals(new File("/some/path"), explorer.rootPath);
        assertEquals("//dir[@name='foo']", explorer.fsPathQuery);
    }

    @Test
    public void testConfigureExplorerWithShortOptions() {
        String[] args = {"-p", "/another/path", "-f", "//file[@name='bar']"};
        explorer.configureExplorer(args);
        assertEquals(new File("/another/path"), explorer.rootPath);
        assertEquals("//file[@name='bar']", explorer.fsPathQuery);
    }

    @Test
    public void testCreateFSPathWithRootPath() {
        explorer.rootPath = new File("/some/path");
        explorer.createFSPath();
        assertNotNull(explorer.fsPath);
        assertEquals(explorer.rootPath, explorer.fsPath.getRootDirectory());
    }

    @Test
    public void testCreateFSPathWithoutRootPath() {
        explorer.createFSPath();
        assertNotNull(explorer.fsPath);
    }

    @Test
    public void testStartWithExitCommand() throws Exception {
        String input = "exit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        explorer.start();
        String output = outContent.toString();
        assertTrue(output.contains("exiting....."));
    }

    @Test
    public void testStartWithPwdCommand() throws Exception {
        explorer.fsPath = FSPathFactory.newFSPath(new File("/some/path"));
        String input = "pwd\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        explorer.start();
        String output = outContent.toString();
        assertTrue(output.contains("Current directory : /some/path"));
    }

    @Test
    public void testStartWithCdCommand() throws Exception {
        String input = "cd /another/path\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        explorer.start();
        String output = outContent.toString();
        assertTrue(output.contains("changed to : /another/path"));
    }

    @Test
    public void testStartWithHelpCommand() throws Exception {
        String input = "help\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        explorer.start();
        String output = outContent.toString();
        assertTrue(output.contains("FSPathExplorer help"));
    }

    @Test
    public void testStartWithFSPathQuery() throws Exception {
        FSPath mockFSPath = mock(FSPath.class);
        FSPathResultList mockResultList = mock(FSPathResultList.class);
        FSPathResult mockResult = mock(FSPathResult.class);
        when(mockResultList.iterator()).thenReturn(java.util.Collections.singletonList(mockResult).iterator());
        when(mockResult.getFile()).thenReturn(new File("/some/file"));
        when(mockFSPath.query(anyString())).thenReturn(mockResultList);

        explorer.fsPath = mockFSPath;
        String input = "//file[@name='test']\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        explorer.start();
        String output = outContent.toString();
        assertTrue(output.contains("/some/file"));
    }

    @Before
    public void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}