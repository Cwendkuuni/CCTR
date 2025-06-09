package org.joda.time.field;

import static org.junit.Assert.*;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.LenientDateTimeField;
import org.joda.time.field.StrictDateTimeField;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class LenientDateTimeFieldTest {

    private DateTimeField mockField;
    private Chronology mockChronology;
    private LenientDateTimeField lenientField;

    @Before
    public void setUp() {
        mockField = Mockito.mock(DateTimeField.class);
        mockChronology = Mockito.mock(Chronology.class);
        Mockito.when(mockChronology.getZone()).thenReturn(DateTimeZone.UTC);
        lenientField = new LenientDateTimeField(mockField, mockChronology);
    }

    @Test
    public void testGetInstance_NullField() {
        assertNull(LenientDateTimeField.getInstance(null, mockChronology));
    }

    @Test
    public void testGetInstance_AlreadyLenient() {
        Mockito.when(mockField.isLenient()).thenReturn(true);
        assertEquals(mockField, LenientDateTimeField.getInstance(mockField, mockChronology));
    }

    @Test
    public void testGetInstance_StrictField() {
        DateTimeField strictField = new StrictDateTimeField(mockField);
        DateTimeField result = LenientDateTimeField.getInstance(strictField, mockChronology);
        assertTrue(result instanceof LenientDateTimeField);
    }

    @Test
    public void testGetInstance_NonLenientField() {
        Mockito.when(mockField.isLenient()).thenReturn(false);
        DateTimeField result = LenientDateTimeField.getInstance(mockField, mockChronology);
        assertTrue(result instanceof LenientDateTimeField);
    }

    @Test
    public void testIsLenient() {
        assertTrue(lenientField.isLenient());
    }

    @Test
    public void testSet() {
        long instant = 1000L;
        int value = 5;
        int currentValue = 3;
        long expectedLocalInstant = 2000L;
        long expectedUTCInstant = 1500L;

        Mockito.when(mockField.get(instant)).thenReturn(currentValue);
        Mockito.when(mockChronology.getZone().convertUTCToLocal(instant)).thenReturn(instant);
        Mockito.when(mockField.getType().getField(mockChronology.withUTC()).add(instant, value - currentValue))
               .thenReturn(expectedLocalInstant);
        Mockito.when(mockChronology.getZone().convertLocalToUTC(expectedLocalInstant, false))
               .thenReturn(expectedUTCInstant);

        long result = lenientField.set(instant, value);
        assertEquals(expectedUTCInstant, result);
    }
}