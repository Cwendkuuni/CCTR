package org.mockito.internal.creation.instance;

import org.junit.Test;
import org.junit.Before;
import org.mockito.internal.creation.instance.ConstructorInstantiator;
import org.mockito.internal.creation.instance.InstantationException;

import static org.junit.Assert.*;

public class ConstructorInstantiatorTest {

    private ConstructorInstantiator instantiatorWithOuter;
    private ConstructorInstantiator instantiatorWithoutOuter;

    private static class NoArgConstructorClass {
        public NoArgConstructorClass() {}
    }

    private static class OuterClass {}

    private static class InnerClass {
        private final OuterClass outer;

        public InnerClass(OuterClass outer) {
            this.outer = outer;
        }
    }

    private static class NoConstructorClass {}

    @Before
    public void setUp() {
        instantiatorWithOuter = new ConstructorInstantiator(new OuterClass());
        instantiatorWithoutOuter = new ConstructorInstantiator(null);
    }

    @Test
    public void testNewInstanceWithNoArgConstructor() {
        NoArgConstructorClass instance = instantiatorWithoutOuter.newInstance(NoArgConstructorClass.class);
        assertNotNull(instance);
    }

    @Test
    public void testNewInstanceWithOuterClass() {
        InnerClass instance = instantiatorWithOuter.newInstance(InnerClass.class);
        assertNotNull(instance);
        assertNotNull(instance.outer);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithOuterClassThrowsException() {
        instantiatorWithoutOuter.newInstance(InnerClass.class);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceNoArgConstructorThrowsException() {
        instantiatorWithoutOuter.newInstance(NoConstructorClass.class);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithOuterClassWrongOuterType() {
        ConstructorInstantiator wrongOuterInstantiator = new ConstructorInstantiator(new Object());
        wrongOuterInstantiator.newInstance(InnerClass.class);
    }
}