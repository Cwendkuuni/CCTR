package org.scribe.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.scribe.model.Verifier;

public class VerifierTest {

    private Verifier verifier;
    private final String testValue = "testValue";

    @Before
    public void setUp() {
        verifier = new Verifier(testValue);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullValue() {
        new Verifier(null);
    }

    @Test
    public void testConstructorWithValidValue() {
        Verifier verifier = new Verifier(testValue);
        assertNotNull(verifier);
    }

    @Test
    public void testGetValue() {
        assertEquals(testValue, verifier.getValue());
    }
}