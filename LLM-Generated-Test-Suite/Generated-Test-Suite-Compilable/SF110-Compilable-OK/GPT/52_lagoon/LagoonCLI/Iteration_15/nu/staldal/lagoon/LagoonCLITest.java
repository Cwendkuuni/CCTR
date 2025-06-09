package nu.staldal.lagoon;

import nu.staldal.lagoon.LagoonCLI;
import nu.staldal.lagoon.core.LagoonException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Properties;

import static org.junit.Assert.*;

public class LagoonCLITest {

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @Before
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    @Test
    public void testMainNoArgs() {
        LagoonCLI.main(new String[]{});
        assertTrue(outContent.toString().contains("Syntax:"));
    }

    @Test
    public void testMainInvalidInterval() {
        LagoonCLI.main(new String[]{"test.properties", "invalid"});
        assertTrue(outContent.toString().contains("Syntax:"));
    }

    @Test
    public void testMainNegativeInterval() {
        LagoonCLI.main(new String[]{"test.properties", "-1"});
        assertTrue(outContent.toString().contains("Syntax:"));
    }

    @Test
    public void testMainFileNotFound() {
        LagoonCLI.main(new String[]{"nonexistent.properties"});
        assertTrue(errContent.toString().contains("File not found"));
    }

    @Test
    public void testGetProperty() throws Exception {
        Properties props = new Properties();
        props.setProperty("testKey", "testValue");
        LagoonCLI.class.getDeclaredField("properties").set(null, props);

        Method getPropertyMethod = LagoonCLI.class.getDeclaredMethod("getProperty", String.class);
        getPropertyMethod.setAccessible(true);

        String value = (String) getPropertyMethod.invoke(null, "testKey");
        assertEquals("testValue", value);
    }

    @Test(expected = LagoonException.class)
    public void testGetPropertyException() throws Exception {
        Properties props = new Properties();
        LagoonCLI.class.getDeclaredField("properties").set(null, props);

        Method getPropertyMethod = LagoonCLI.class.getDeclaredMethod("getProperty", String.class);
        getPropertyMethod.setAccessible(true);

        getPropertyMethod.invoke(null, "missingKey");
    }

    @Test
    public void testShowTimeMilliseconds() throws Exception {
        Method showTimeMethod = LagoonCLI.class.getDeclaredMethod("showTime", long.class);
        showTimeMethod.setAccessible(true);

        showTimeMethod.invoke(null, 5000L);
        assertTrue(outContent.toString().contains("in 5000 ms"));
    }

    @Test
    public void testShowTimeSeconds() throws Exception {
        Method showTimeMethod = LagoonCLI.class.getDeclaredMethod("showTime", long.class);
        showTimeMethod.setAccessible(true);

        showTimeMethod.invoke(null, 15000L);
        assertTrue(outContent.toString().contains("in 15 s"));
    }
}