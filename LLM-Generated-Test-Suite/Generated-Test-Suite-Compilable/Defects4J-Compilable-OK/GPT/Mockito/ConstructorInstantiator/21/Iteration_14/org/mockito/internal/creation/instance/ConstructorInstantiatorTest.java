package org.mockito.internal.creation.instance;

import org.junit.Test;
import org.mockito.internal.creation.instance.ConstructorInstantiator;

import static org.junit.Assert.*;

public class ConstructorInstantiatorTest {

    public static class NoArgConstructorClass {
        public NoArgConstructorClass() {
        }
    }

    public static class OuterClass {
        public class InnerClass {
            public InnerClass() {
            }
        }

        public class InnerClassWithOuter {
            public InnerClassWithOuter(OuterClass outer) {
            }
        }
    }

    public static class NoConstructorClass {
        private NoConstructorClass() {
        }
    }

    public static class InvalidOuterClass {
        public InvalidOuterClass(String str) {
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
        OuterClass.InnerClassWithOuter instance = instantiator.newInstance(OuterClass.InnerClassWithOuter.class);
        assertNotNull(instance);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithInvalidOuterClass() {
        ConstructorInstantiator instantiator = new ConstructorInstantiator(new Object());
        instantiator.newInstance(OuterClass.InnerClassWithOuter.class);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithNoConstructorClass() {
        ConstructorInstantiator instantiator = new ConstructorInstantiator(null);
        instantiator.newInstance(NoConstructorClass.class);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithInvalidOuterClassType() {
        ConstructorInstantiator instantiator = new ConstructorInstantiator(new OuterClass());
        instantiator.newInstance(InvalidOuterClass.class);
    }
}