package net.sf.xbus.base.core.reflection;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.BeforeClass;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;

public class XBUSClassLoaderTest {

    private static final String XBUS_HOME = "testXBUSHome";
    private static final String LIB_DIR = XBUS_HOME + "/lib";
    private static final String RUNTIME_DIR = XBUS_HOME + "/lib/runtime";
    private static final String PLUGIN_DIR = XBUS_HOME + "/plugin/lib";
    private static final String TEST_DIR = XBUS_HOME + "/test/lib";

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Set up the directory structure and files for testing
        new File(LIB_DIR).mkdirs();
        new File(RUNTIME_DIR).mkdirs();
        new File(PLUGIN_DIR).mkdirs();
        new File(TEST_DIR).mkdirs();

        // Create dummy jar files
        new File(LIB_DIR + "/test1.jar").createNewFile();
        new File(RUNTIME_DIR + "/test2.jar").createNewFile();
        new File(PLUGIN_DIR + "/test3.jar").createNewFile();
        new File(TEST_DIR + "/test4.jar").createNewFile();

        // Set the XBUS_HOME environment variable
        System.setProperty("XBUS_HOME", XBUS_HOME);
    }

    @After
    public void tearDown() throws Exception {
        // Reset the singleton instance for each test
        Field field = XBUSClassLoader.class.getDeclaredField("mClassLoader");
        field.setAccessible(true);
        field.set(null, null);
    }

    @Test
    public void testGetInstance() throws Exception {
        ClassLoader parent = getClass().getClassLoader();
        XBUSClassLoader classLoader = XBUSClassLoader.getInstance(parent);
        assertNotNull("ClassLoader instance should not be null", classLoader);
        assertEquals("ClassLoader should be a singleton", classLoader, XBUSClassLoader.getInstance(parent));
    }

    @Test
    public void testCreateClassLoader() throws Exception {
        ClassLoader parent = getClass().getClassLoader();
        Method method = XBUSClassLoader.class.getDeclaredMethod("createClassLoader", ClassLoader.class);
        method.setAccessible(true);
        XBUSClassLoader classLoader = (XBUSClassLoader) method.invoke(null, parent);
        assertNotNull("ClassLoader instance should not be null", classLoader);
    }

    @Test
    public void testAddUrls() throws Exception {
        Vector<URL> urls = new Vector<>();
        Method method = XBUSClassLoader.class.getDeclaredMethod("addUrls", Vector.class, String.class);
        method.setAccessible(true);
        method.invoke(null, urls, LIB_DIR);
        method.invoke(null, urls, RUNTIME_DIR);
        method.invoke(null, urls, PLUGIN_DIR);
        method.invoke(null, urls, TEST_DIR);

        assertEquals("URLs vector should contain 4 URLs", 4, urls.size());
    }

    @Test(expected = RuntimeException.class)
    public void testAddUrlsWithInvalidDirectory() throws Exception {
        Vector<URL> urls = new Vector<>();
        Method method = XBUSClassLoader.class.getDeclaredMethod("addUrls", Vector.class, String.class);
        method.setAccessible(true);
        method.invoke(null, urls, "invalidDir");
    }

    @Test(expected = RuntimeException.class)
    public void testCreateClassLoaderWithoutXBUSHome() throws Exception {
        System.clearProperty("XBUS_HOME");
        ClassLoader parent = getClass().getClassLoader();
        XBUSClassLoader.getInstance(parent);
    }
}