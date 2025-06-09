package org.scribe.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.scribe.model.Verifier;
import org.scribe.utils.Preconditions;

public class VerifierTest {

    private Verifier verifier;
    private final String validValue = "testValue";

    @Before
    public void setUp() {
        verifier = new Verifier(validValue);
    }

    @Test
    public void testConstructorWithValidValue() {
        assertNotNull("Verifier object should be created", verifier);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullValue() {
        new Verifier(null);
    }

    @Test
    public void testGetValue() {
        assertEquals("getValue should return the correct value", validValue, verifier.getValue());
    }
}