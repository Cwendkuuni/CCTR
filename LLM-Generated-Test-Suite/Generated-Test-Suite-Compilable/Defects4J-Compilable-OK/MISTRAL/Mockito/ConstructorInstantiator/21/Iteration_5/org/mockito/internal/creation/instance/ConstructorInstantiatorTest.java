package org.mockito.internal.creation.instance;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.creation.instance.ConstructorInstantiator;
import org.mockito.internal.creation.instance.Instantiator;
import org.mockito.internal.creation.instance.InstantationException;

import static org.junit.Assert.*;

public class ConstructorInstantiatorTest {

    private ConstructorInstantiator constructorInstantiator;
    private Object outerClassInstance;

    @Before
    public void setUp() {
        outerClassInstance = new Object();
        constructorInstantiator = new ConstructorInstantiator(outerClassInstance);
    }

    @Test
    public void testNewInstanceWithNoOuterClass() {
        Class<NoArgConstructorClass> cls = NoArgConstructorClass.class;
        NoArgConstructorClass instance = constructorInstantiator.newInstance(cls);
        assertNotNull(instance);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithNoOuterClassThrowsException() {
        Class<NoNoArgConstructorClass> cls = NoNoArgConstructorClass.class;
        constructorInstantiator.newInstance(cls);
    }

    @Test
    public void testNewInstanceWithOuterClass() {
        Class<OuterClassConstructorClass> cls = OuterClassConstructorClass.class;
        OuterClassConstructorClass instance = constructorInstantiator.newInstance(cls);
        assertNotNull(instance);
        assertEquals(outerClassInstance, instance.getOuterClassInstance());
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithOuterClassThrowsException() {
        Class<NoOuterClassConstructorClass> cls = NoOuterClassConstructorClass.class;
        constructorInstantiator.newInstance(cls);
    }

    // Helper classes for testing
    public static class NoArgConstructorClass {
        public NoArgConstructorClass() {}
    }

    public static class NoNoArgConstructorClass {
        public NoNoArgConstructorClass(int i) {}
    }

    public static class OuterClassConstructorClass {
        private final Object outerClassInstance;

        public OuterClassConstructorClass(Object outerClassInstance) {
            this.outerClassInstance = outerClassInstance;
        }

        public Object getOuterClassInstance() {
            return outerClassInstance;
        }
    }

    public static class NoOuterClassConstructorClass {
        public NoOuterClassConstructorClass(String s) {}
    }
}