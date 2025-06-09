package org.scribe.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.scribe.utils.Preconditions;

public class VerifierTest {

    @Test
    public void testConstructorWithValidValue() {
        String validValue = "validVerifier";
        Verifier verifier = new Verifier(validValue);
        assertNotNull(verifier);
        assertEquals(validValue, verifier.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullValue() {
        new Verifier(null);
    }

    @Test
    public void testGetValue() {
        String expectedValue = "testValue";
        Verifier verifier = new Verifier(expectedValue);
        assertEquals(expectedValue, verifier.getValue());
    }
}