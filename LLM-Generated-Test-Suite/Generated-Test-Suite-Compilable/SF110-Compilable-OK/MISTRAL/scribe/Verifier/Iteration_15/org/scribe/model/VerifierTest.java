package org.scribe.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

public class VerifierTest {

    private Verifier verifier;
    private static final String VALID_VALUE = "validVerifier";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        verifier = new Verifier(VALID_VALUE);
    }

    @Test
    public void testConstructorWithValidValue() {
        assertEquals(VALID_VALUE, verifier.getValue());
    }

    @Test
    public void testConstructorWithNullValue() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Must provide a valid string as verifier");
        new Verifier(null);
    }

    @Test
    public void testConstructorWithEmptyValue() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Must provide a valid string as verifier");
        new Verifier("");
    }

    @Test
    public void testGetValue() {
        assertEquals(VALID_VALUE, verifier.getValue());
    }
}