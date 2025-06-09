package org.scribe.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.scribe.model.Verifier;

public class VerifierTest {

    private static final String VALID_STRING = "validVerifier";

    private Verifier verifier;

    @Before
    public void setUp() {
        verifier = new Verifier(VALID_STRING);
    }

    @Test
    public void testConstructorWithValidString() {
        assertNotNull("Verifier object should be created", verifier);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullString() {
        new Verifier(null);
    }

    @Test
    public void testGetValue() {
        assertEquals("getValue should return the correct string", VALID_STRING, verifier.getValue());
    }
}