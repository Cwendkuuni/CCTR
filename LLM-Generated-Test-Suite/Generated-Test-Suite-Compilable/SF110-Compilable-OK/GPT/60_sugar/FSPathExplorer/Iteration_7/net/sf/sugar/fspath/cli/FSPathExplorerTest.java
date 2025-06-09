package net.sf.sugar.fspath.cli;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import net.sf.sugar.fspath.FSPath;
import net.sf.sugar.fspath.FSPathFactory;
import net.sf.sugar.fspath.FSPathResult;
import net.sf.sugar.fspath.FSPathResultList;

import org.junit.Before;
import org.junit.Test;

public class FSPathExplorerTest {

    private FSPathExplorer explorer;
    private PrintStream mockOut;
    private PrintStream mockErr;
    private Prompt mockPrompt;
    private FSPath mockFSPath;

    @Before
    public void setUp() {
        mockOut = mock(PrintStream.class);
        mockErr = mock(PrintStream.class);
        mockPrompt = mock(Prompt.class);
        mockFSPath = mock(FSPath.class);

        explorer = new FSPathExplorer();
        explorer.out = mockOut;
        explorer.err = mockErr;
        explorer.prompt = mockPrompt;
        explorer.fsPath = mockFSPath;
    }

    @Test
    public void testConfigureExplorerWithPathAndQuery() {
        String[] args = {"-path", "/some/path", "-find", "//dir[@name='foo']"};
        explorer.configureExplorer(args);

        assertEquals(new File("/some/path"), explorer.rootPath);
        assertEquals("//dir[@name='foo']", explorer.fsPathQuery);
    }

    @Test
    public void testConfigureExplorerWithOnlyQuery() {
        String[] args = {"-find", "//dir[@name='foo']"};
        explorer.configureExplorer(args);

        assertNull(explorer.rootPath);
        assertEquals("//dir[@name='foo']", explorer.fsPathQuery);
    }

    @Test
    public void testCreateFSPathWithRootPath() {
        explorer.rootPath = new File("/some/path");
        explorer.createFSPath();

        assertNotNull(explorer.fsPath);
        verify(mockFSPath, never()).getRootDirectory();
    }

    @Test
    public void testCreateFSPathWithoutRootPath() {
        explorer.rootPath = null;
        explorer.createFSPath();

        assertNotNull(explorer.fsPath);
        verify(mockFSPath, never()).getRootDirectory();
    }

    @Test
    public void testStartWithExitCommand() throws IOException {
        when(mockPrompt.readLine()).thenReturn("exit");

        explorer.start();

        verify(mockOut).println("exiting.....");
        verify(mockPrompt).close();
    }

    @Test
    public void testStartWithPwdCommand() throws IOException {
        when(mockPrompt.readLine()).thenReturn("pwd");
        when(mockFSPath.getRootDirectory()).thenReturn(new File("/current/directory"));

        explorer.start();

        verify(mockOut).println("Current directory : /current/directory");
    }

    @Test
    public void testStartWithCdCommand() throws IOException {
        when(mockPrompt.readLine()).thenReturn("cd /new/directory");
        File newDir = mock(File.class);
        when(newDir.exists()).thenReturn(true);
        when(newDir.isDirectory()).thenReturn(true);
        when(newDir.canRead()).thenReturn(true);
        when(newDir.getAbsolutePath()).thenReturn("/new/directory");

        explorer.start();

        verify(mockOut).println("changed to : /new/directory");
    }

    @Test
    public void testStartWithHelpCommand() throws IOException {
        when(mockPrompt.readLine()).thenReturn("help");

        explorer.start();

        verify(mockOut, atLeastOnce()).println(anyString());
    }

    @Test
    public void testStartWithFSPathQuery() throws IOException {
        when(mockPrompt.readLine()).thenReturn("//dir[@name='foo']");
        FSPathResultList mockResults = mock(FSPathResultList.class);
        FSPathResult mockResult = mock(FSPathResult.class);
        when(mockResult.getFile()).thenReturn(new File("/some/path/foo"));
        when(mockResults.iterator()).thenReturn(java.util.Collections.singletonList(mockResult).iterator());
        when(mockFSPath.query("//dir[@name='foo']")).thenReturn(mockResults);

        explorer.start();

        verify(mockOut).println("/some/path/foo");
    }
}