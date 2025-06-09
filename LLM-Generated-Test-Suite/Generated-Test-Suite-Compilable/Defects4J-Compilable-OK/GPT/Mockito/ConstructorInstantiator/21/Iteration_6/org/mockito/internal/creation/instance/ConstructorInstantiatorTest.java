package org.mockito.internal.creation.instance;

import org.junit.Test;
import org.junit.Before;
import org.mockito.internal.creation.instance.ConstructorInstantiator;
import org.mockito.internal.creation.instance.InstantationException;

import static org.junit.Assert.*;

public class ConstructorInstantiatorTest {

    private ConstructorInstantiator instantiatorWithOuter;
    private ConstructorInstantiator instantiatorWithoutOuter;

    @Before
    public void setUp() {
        instantiatorWithOuter = new ConstructorInstantiator(new OuterClass());
        instantiatorWithoutOuter = new ConstructorInstantiator(null);
    }

    @Test
    public void testNewInstanceWithOuterClass() {
        InnerClass instance = instantiatorWithOuter.newInstance(InnerClass.class);
        assertNotNull(instance);
        assertEquals("OuterClass", instance.getOuterClassName());
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithOuterClassThrowsException() {
        instantiatorWithOuter.newInstance(NoValidConstructorClass.class);
    }

    @Test
    public void testNewInstanceWithoutOuterClass() {
        NoArgConstructorClass instance = instantiatorWithoutOuter.newInstance(NoArgConstructorClass.class);
        assertNotNull(instance);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithoutOuterClassThrowsException() {
        instantiatorWithoutOuter.newInstance(NoValidConstructorClass.class);
    }

    // Helper classes for testing
    public static class OuterClass {
    }

    public static class InnerClass {
        private final OuterClass outer;

        public InnerClass(OuterClass outer) {
            this.outer = outer;
        }

        public String getOuterClassName() {
            return outer.getClass().getSimpleName();
        }
    }

    public static class NoArgConstructorClass {
        public NoArgConstructorClass() {
        }
    }

    public static class NoValidConstructorClass {
        private NoValidConstructorClass(String param) {
        }
    }
}