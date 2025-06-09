package org.mockito.internal.creation.instance;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.creation.instance.ConstructorInstantiator;
import org.mockito.internal.creation.instance.Instantiator;
import org.mockito.internal.creation.instance.InstantationException;

import static org.junit.Assert.*;

public class ConstructorInstantiatorTest {

    private ConstructorInstantiator constructorInstantiator;
    private ConstructorInstantiator constructorInstantiatorWithOuterClass;

    @Before
    public void setUp() {
        constructorInstantiator = new ConstructorInstantiator(null);
        constructorInstantiatorWithOuterClass = new ConstructorInstantiator(new OuterClass());
    }

    @Test
    public void testNewInstanceWithNoArgConstructor() {
        ClassWithNoArgConstructor instance = constructorInstantiator.newInstance(ClassWithNoArgConstructor.class);
        assertNotNull(instance);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithNoArgConstructorThrowsException() {
        constructorInstantiator.newInstance(ClassWithoutNoArgConstructor.class);
    }

    @Test
    public void testNewInstanceWithOuterClass() {
        ClassWithOuterClass instance = constructorInstantiatorWithOuterClass.newInstance(ClassWithOuterClass.class);
        assertNotNull(instance);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithOuterClassThrowsException() {
        constructorInstantiatorWithOuterClass.newInstance(ClassWithoutOuterClassConstructor.class);
    }

    public static class OuterClass {
        // Empty outer class for testing purposes
    }

    public static class ClassWithNoArgConstructor {
        public ClassWithNoArgConstructor() {
            // No-arg constructor
        }
    }

    public static class ClassWithoutNoArgConstructor {
        public ClassWithoutNoArgConstructor(String arg) {
            // Constructor with argument
        }
    }

    public static class ClassWithOuterClass {
        public ClassWithOuterClass(OuterClass outerClass) {
            // Constructor with outer class
        }
    }

    public static class ClassWithoutOuterClassConstructor {
        public ClassWithoutOuterClassConstructor(String arg) {
            // Constructor with argument but not outer class
        }
    }
}