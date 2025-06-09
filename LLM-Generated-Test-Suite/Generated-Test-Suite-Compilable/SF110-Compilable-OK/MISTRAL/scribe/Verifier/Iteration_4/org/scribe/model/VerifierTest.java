package org.scribe.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class VerifierTest {

    @Test
    public void testConstructorWithValidString() {
        String validString = "validVerifier";
        Verifier verifier = new Verifier(validString);
        assertNotNull(verifier);
        assertEquals(validString, verifier.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullString() {
        new Verifier(null);
    }

    @Test
    public void testGetValue() {
        String validString = "validVerifier";
        Verifier verifier = new Verifier(validString);
        assertEquals(validString, verifier.getValue());
    }
}