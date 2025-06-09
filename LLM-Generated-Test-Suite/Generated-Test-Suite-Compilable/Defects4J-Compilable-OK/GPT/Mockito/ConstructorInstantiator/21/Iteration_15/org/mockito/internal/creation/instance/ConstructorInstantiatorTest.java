package org.mockito.internal.creation.instance;

import org.junit.Test;
import org.mockito.internal.creation.instance.ConstructorInstantiator;
import org.mockito.internal.creation.instance.InstantationException;

import static org.junit.Assert.*;

public class ConstructorInstantiatorTest {

    public static class NoArgConstructorClass {
        public NoArgConstructorClass() {
        }
    }

    public static class OuterClass {
        public class InnerClass {
            public InnerClass(OuterClass outer) {
            }
        }
    }

    public static class NoSuitableConstructorClass {
        private NoSuitableConstructorClass() {
        }
    }

    @Test
    public void testNewInstanceWithNoArgConstructor() {
        ConstructorInstantiator instantiator = new ConstructorInstantiator(null);
        NoArgConstructorClass instance = instantiator.newInstance(NoArgConstructorClass.class);
        assertNotNull(instance);
    }

    @Test
    public void testNewInstanceWithOuterClass() {
        OuterClass outer = new OuterClass();
        ConstructorInstantiator instantiator = new ConstructorInstantiator(outer);
        OuterClass.InnerClass instance = instantiator.newInstance(OuterClass.InnerClass.class);
        assertNotNull(instance);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithNoSuitableConstructor() {
        ConstructorInstantiator instantiator = new ConstructorInstantiator(null);
        instantiator.newInstance(NoSuitableConstructorClass.class);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithIncorrectOuterClass() {
        ConstructorInstantiator instantiator = new ConstructorInstantiator(new Object());
        instantiator.newInstance(OuterClass.InnerClass.class);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithNullClass() {
        ConstructorInstantiator instantiator = new ConstructorInstantiator(null);
        instantiator.newInstance(null);
    }
}