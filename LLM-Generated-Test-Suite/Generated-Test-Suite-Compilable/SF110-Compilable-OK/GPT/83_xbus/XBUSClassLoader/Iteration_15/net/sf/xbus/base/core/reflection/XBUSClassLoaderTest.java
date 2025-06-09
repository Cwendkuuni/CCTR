package net.sf.xbus.base.core.reflection;

import net.sf.xbus.base.core.reflection.XBUSClassLoader;
import org.junit.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

public class XBUSClassLoaderTest {

    private static final String XBUS_HOME = "path/to/xbus/home"; // Set this to a valid path for testing

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Set the XBUS_HOME environment variable for testing
        System.setProperty("XBUS_HOME", XBUS_HOME);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // Clean up the XBUS_HOME environment variable
        System.clearProperty("XBUS_HOME");
    }

    @Before
    public void setUp() throws Exception {
        // Reset the singleton instance before each test
        resetSingleton();
    }

    @Test
    public void testGetInstance() {
        ClassLoader parentClassLoader = getClass().getClassLoader();
        XBUSClassLoader instance = XBUSClassLoader.getInstance(parentClassLoader);
        assertNotNull("Instance should not be null", instance);
        assertTrue("Instance should be of type XBUSClassLoader", instance instanceof XBUSClassLoader);
    }

    @Test
    public void testSingletonBehavior() {
        ClassLoader parentClassLoader = getClass().getClassLoader();
        XBUSClassLoader instance1 = XBUSClassLoader.getInstance(parentClassLoader);
        XBUSClassLoader instance2 = XBUSClassLoader.getInstance(parentClassLoader);
        assertSame("Instances should be the same", instance1, instance2);
    }

    @Test
    public void testUrlsLoaded() {
        ClassLoader parentClassLoader = getClass().getClassLoader();
        XBUSClassLoader instance = XBUSClassLoader.getInstance(parentClassLoader);
        URL[] urls = instance.getURLs();
        assertNotNull("URLs should not be null", urls);
        assertTrue("URLs should contain entries", urls.length > 0);
    }

    private void resetSingleton() throws Exception {
        Field instanceField = XBUSClassLoader.class.getDeclaredField("mClassLoader");
        instanceField.setAccessible(true);

        // Remove final modifier from the field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(instanceField, instanceField.getModifiers() & ~Modifier.FINAL);

        instanceField.set(null, null);
    }
}