package org.scribe.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.scribe.model.Verifier;

public class VerifierTest {

    private Verifier verifier;
    private final String testValue = "testVerifier";

    @Before
    public void setUp() {
        verifier = new Verifier(testValue);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullValue() {
        new Verifier(null);
    }

    @Test
    public void testGetValue() {
        assertEquals("The value returned by getValue should match the value passed to the constructor", testValue, verifier.getValue());
    }
}