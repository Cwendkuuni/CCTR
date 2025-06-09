package org.joda.time.field;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.field.LenientDateTimeField;
import org.joda.time.field.StrictDateTimeField;
import org.joda.time.field.FieldUtils;
import org.junit.Before;
import org.junit.Test;

public class LenientDateTimeFieldTest {

    private DateTimeField mockField;
    private Chronology mockChronology;
    private LenientDateTimeField lenientField;

    @Before
    public void setUp() {
        mockField = mock(DateTimeField.class);
        mockChronology = mock(Chronology.class);
        lenientField = new LenientDateTimeField(mockField, mockChronology);
    }

    @Test
    public void testGetInstance_NullField() {
        assertNull(LenientDateTimeField.getInstance(null, mockChronology));
    }

    @Test
    public void testGetInstance_AlreadyLenient() {
        when(mockField.isLenient()).thenReturn(true);
        assertEquals(mockField, LenientDateTimeField.getInstance(mockField, mockChronology));
    }

    @Test
    public void testGetInstance_StrictField() {
        StrictDateTimeField strictField = mock(StrictDateTimeField.class);
        DateTimeField wrappedField = mock(DateTimeField.class);
        when(strictField.getWrappedField()).thenReturn(wrappedField);
        when(wrappedField.isLenient()).thenReturn(false);

        DateTimeField result = LenientDateTimeField.getInstance(strictField, mockChronology);
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
        long localInstant = 2000L;
        long difference = 2L;
        long addedInstant = 3000L;
        long expectedInstant = 4000L;

        DateTimeZone mockZone = mock(DateTimeZone.class);
        when(mockChronology.getZone()).thenReturn(mockZone);
        when(mockZone.convertUTCToLocal(instant)).thenReturn(localInstant);
        when(mockField.get(instant)).thenReturn(currentValue);
        when(mockField.getType()).thenReturn(mockField.getType());
        when(mockField.getType().getField(mockChronology.withUTC())).thenReturn(mockField);
        when(mockField.add(localInstant, difference)).thenReturn(addedInstant);
        when(mockZone.convertLocalToUTC(addedInstant, false)).thenReturn(expectedInstant);

        long result = lenientField.set(instant, value);
        assertEquals(expectedInstant, result);
    }
}