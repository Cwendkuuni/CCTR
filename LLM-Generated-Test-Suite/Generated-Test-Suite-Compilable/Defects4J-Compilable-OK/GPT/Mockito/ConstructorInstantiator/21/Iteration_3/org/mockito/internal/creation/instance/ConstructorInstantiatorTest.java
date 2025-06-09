package org.mockito.internal.creation.instance;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.creation.instance.ConstructorInstantiator;
import org.mockito.internal.creation.instance.InstantationException;

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

    private static class NoNoArgClass {
        NoNoArgClass(String param) {}
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
        instantiatorWithoutOuter.newInstance(NoNoArgClass.class);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithOuterClassThrowsExceptionForNoNoArgClass() {
        instantiatorWithOuter.newInstance(NoNoArgClass.class);
    }
}