package net.sf.xbus.base.core.reflection;

import net.sf.xbus.base.core.reflection.XBUSClassLoader;
import org.junit.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class XBUSClassLoaderTest {

    private static final String XBUS_HOME = "testXBUSHome";

    @BeforeClass
    public static void setUpClass() {
        // Set up the XBUS_HOME environment variable for testing
        System.setProperty("XBUS_HOME", XBUS_HOME);
        new File(XBUS_HOME + "/lib").mkdirs();
        new File(XBUS_HOME + "/lib/runtime").mkdirs();
        new File(XBUS_HOME + "/plugin/lib").mkdirs();
        new File(XBUS_HOME + "/test/lib").mkdirs();
    }

    @AfterClass
    public static void tearDownClass() {
        // Clean up the XBUS_HOME environment variable and directories
        System.clearProperty("XBUS_HOME");
        deleteDirectory(new File(XBUS_HOME));
    }

    @Before
    public void setUp() throws Exception {
        // Reset the singleton instance before each test
        Field field = XBUSClassLoader.class.getDeclaredField("mClassLoader");
        field.setAccessible(true);
        field.set(null, null);
    }

    @Test
    public void testGetInstance() {
        ClassLoader parent = new URLClassLoader(new URL[]{});
        XBUSClassLoader instance = XBUSClassLoader.getInstance(parent);
        assertNotNull(instance);
        assertTrue(instance instanceof XBUSClassLoader);
    }

    @Test
    public void testSingletonBehavior() {
        ClassLoader parent = new URLClassLoader(new URL[]{});
        XBUSClassLoader instance1 = XBUSClassLoader.getInstance(parent);
        XBUSClassLoader instance2 = XBUSClassLoader.getInstance(parent);
        assertSame(instance1, instance2);
    }

    @Test
    public void testCreateClassLoaderWithNullXBUSHome() {
        System.clearProperty("XBUS_HOME");
        try {
            XBUSClassLoader.getInstance(new URLClassLoader(new URL[]{}));
            fail("Expected System.exit to be called due to null XBUS_HOME");
        } catch (SecurityException e) {
            // Expected behavior, as System.exit should be called
        } finally {
            System.setProperty("XBUS_HOME", XBUS_HOME);
        }
    }

    @Test
    public void testAddUrls() throws Exception {
        // Create a temporary jar file in the lib directory
        File jarFile = new File(XBUS_HOME + "/lib/test.jar");
        assertTrue(jarFile.createNewFile());

        ClassLoader parent = new URLClassLoader(new URL[]{});
        XBUSClassLoader instance = XBUSClassLoader.getInstance(parent);

        URL[] urls = instance.getURLs();
        boolean jarFound = false;
        for (URL url : urls) {
            if (url.toString().endsWith("test.jar")) {
                jarFound = true;
                break;
            }
        }
        assertTrue("The test.jar should be included in the class loader URLs", jarFound);

        // Clean up
        assertTrue(jarFile.delete());
    }

    private static void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        directory.delete();
    }
}