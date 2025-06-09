package org.scribe.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.scribe.model.Verifier;
import org.scribe.utils.Preconditions;

public class VerifierTest {

    private Verifier verifier;
    private final String validValue = "validVerifier";

    @Before
    public void setUp() {
        verifier = new Verifier(validValue);
    }

    @Test
    public void testConstructorWithValidValue() {
        Verifier verifier = new Verifier(validValue);
        assertNotNull("Verifier object should not be null", verifier);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullValue() {
        new Verifier(null);
    }

    @Test
    public void testGetValue() {
        assertEquals("The value returned by getValue should match the input value", validValue, verifier.getValue());
    }
}