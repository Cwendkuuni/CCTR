package org.mockito.internal.creation.instance;

import org.junit.Test;
import org.junit.Before;
import org.mockito.internal.creation.instance.ConstructorInstantiator;

import static org.junit.Assert.*;

public class ConstructorInstantiatorTest {

    private ConstructorInstantiator instantiatorWithOuter;
    private ConstructorInstantiator instantiatorWithoutOuter;

    private static class OuterClass {
        class InnerClass {
            InnerClass() {}
            InnerClass(OuterClass outer) {}
        }
    }

    private static class NoArgClass {
        NoArgClass() {}
    }

    private static class NoNoArgConstructorClass {
        NoNoArgConstructorClass(String param) {}
    }

    @Before
    public void setUp() {
        instantiatorWithOuter = new ConstructorInstantiator(new OuterClass());
        instantiatorWithoutOuter = new ConstructorInstantiator(null);
    }

    @Test
    public void testNewInstanceWithOuterClass() {
        OuterClass.InnerClass instance = instantiatorWithOuter.newInstance(OuterClass.InnerClass.class);
        assertNotNull(instance);
    }

    @Test
    public void testNewInstanceWithoutOuterClass() {
        NoArgClass instance = instantiatorWithoutOuter.newInstance(NoArgClass.class);
        assertNotNull(instance);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithOuterClassThrowsException() {
        instantiatorWithOuter.newInstance(NoArgClass.class);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithoutOuterClassThrowsException() {
        instantiatorWithoutOuter.newInstance(NoNoArgConstructorClass.class);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithOuterClassWrongTypeThrowsException() {
        instantiatorWithOuter.newInstance(NoNoArgConstructorClass.class);
    }
}