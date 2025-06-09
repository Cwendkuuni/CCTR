package net.sourceforge.beanbin.reflect;

import net.sourceforge.beanbin.search.*;
import net.sourceforge.beanbin.*;
import net.sourceforge.beanbin.query.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ReflectionSearchTest {

    private ReflectionSearch reflectionSearch;

    @Before
    public void setUp() {
        reflectionSearch = new ReflectionSearch(TestClass.class);
    }

    @Test
    public void testHasAnnotation() {
        assertTrue(reflectionSearch.hasAnnotation("@TestAnnotation"));
        assertFalse(reflectionSearch.hasAnnotation("@NonExistentAnnotation"));
    }

    @Test
    public void testHasWildcard() throws Exception {
        Method hasWildcardMethod = ReflectionSearch.class.getDeclaredMethod("hasWildcard", String.class);
        hasWildcardMethod.setAccessible(true);

        assertTrue((Boolean) hasWildcardMethod.invoke(reflectionSearch, "*Test*"));
        assertFalse((Boolean) hasWildcardMethod.invoke(reflectionSearch, "Test"));
    }

    @Test
    public void testMethodsThatHave() throws BeanBinException {
        reflectionSearch.methodsThatHave("testMethod");
        List<Method> methods = reflectionSearch.getMethods();
        assertNotNull(methods);
        assertTrue(methods.stream().anyMatch(m -> m.getName().equals("testMethod")));
    }

    @Test
    public void testMethodsThatDontHave() throws BeanBinException {
        reflectionSearch.methodsThatDontHave("testMethod");
        List<Method> methods = reflectionSearch.getMethods();
        assertNotNull(methods);
        assertFalse(methods.stream().anyMatch(m -> m.getName().equals("testMethod")));
    }

    @Test
    public void testAnd() throws BeanBinException {
        reflectionSearch.methodsThatHave("testMethod").and().methodsThatHave("anotherMethod");
        List<Method> methods = reflectionSearch.getMethods();
        assertNotNull(methods);
        assertTrue(methods.stream().anyMatch(m -> m.getName().equals("testMethod")));
        assertTrue(methods.stream().anyMatch(m -> m.getName().equals("anotherMethod")));
    }

    @Test
    public void testOr() throws BeanBinException {
        reflectionSearch.methodsThatHave("testMethod").or().methodsThatHave("anotherMethod");
        List<Method> methods = reflectionSearch.getMethods();
        assertNotNull(methods);
        assertTrue(methods.stream().anyMatch(m -> m.getName().equals("testMethod") || m.getName().equals("anotherMethod")));
    }

    @Test
    public void testGetMethods() throws BeanBinException {
        List<Method> methods = reflectionSearch.getMethods();
        assertNotNull(methods);
        assertTrue(methods.size() > 0);
    }

    @Test
    public void testIntersect() throws Exception {
        Method intersectMethod = ReflectionSearch.class.getDeclaredMethod("intersect", List.class, List.class);
        intersectMethod.setAccessible(true);

        List<Method> master = new ArrayList<>(Arrays.asList(TestClass.class.getMethods()));
        List<Method> toAdd = new ArrayList<>(Arrays.asList(TestClass.class.getMethods()));

        intersectMethod.invoke(reflectionSearch, master, toAdd);
        assertEquals(master.size(), toAdd.size());
    }

    @Test
    public void testUnion() throws Exception {
        Method unionMethod = ReflectionSearch.class.getDeclaredMethod("union", List.class, List.class);
        unionMethod.setAccessible(true);

        List<Method> master = new ArrayList<>(Arrays.asList(TestClass.class.getMethods()));
        List<Method> toAdd = new ArrayList<>(Arrays.asList(TestClass.class.getMethods()));

        unionMethod.invoke(reflectionSearch, master, toAdd);
        assertEquals(master.size(), toAdd.size());
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface TestAnnotation {}

    @TestAnnotation
    public static class TestClass {
        public void testMethod() {}
        public void anotherMethod() {}
    }
}