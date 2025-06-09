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

    private static class NoNoArgClass {
        NoNoArgClass(String param) {}
    }

    @Before
    public void setUp() {
        instantiatorWithOuter = new ConstructorInstantiator(new OuterClass());
        instantiatorWithoutOuter = new ConstructorInstantiator(null);
    }

    @Test
    public void testNewInstanceWithNoArgConstructor() {
        NoArgClass instance = instantiatorWithoutOuter.newInstance(NoArgClass.class);
        assertNotNull(instance);
    }

    @Test
    public void testNewInstanceWithOuterClassConstructor() {
        OuterClass.InnerClass instance = instantiatorWithOuter.newInstance(OuterClass.InnerClass.class);
        assertNotNull(instance);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithNoNoArgConstructor() {
        instantiatorWithoutOuter.newInstance(NoNoArgClass.class);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithIncorrectOuterClass() {
        instantiatorWithOuter.newInstance(NoArgClass.class);
    }

    @Test(expected = InstantationException.class)
    public void testNewInstanceWithNullOuterClass() {
        instantiatorWithoutOuter.newInstance(OuterClass.InnerClass.class);
    }
}