package org.joda.time;

import org.joda.time.base.AbstractPartial;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;

import static org.junit.Assert.*;

public class PartialTest {

    private Partial partial;
    private Chronology chronology;
    private DateTimeFieldType[] types;
    private int[] values;

    @Before
    public void setUp() {
        chronology = ISOChronology.getInstanceUTC();
        types = new DateTimeFieldType[]{DateTimeFieldType.year(), DateTimeFieldType.monthOfYear(), DateTimeFieldType.dayOfMonth()};
        values = new int[]{2023, 10, 15};
        partial = new Partial(types, values, chronology);
    }

    @Test
    public void testDefaultConstructor() {
        Partial defaultPartial = new Partial();
        assertNotNull(defaultPartial);
        assertEquals(0, defaultPartial.size());
    }

    @Test
    public void testConstructorWithChronology() {
        Partial chronologyPartial = new Partial(chronology);
        assertNotNull(chronologyPartial);
        assertEquals(0, chronologyPartial.size());
        assertEquals(chronology, chronologyPartial.getChronology());
    }

    @Test
    public void testConstructorWithDateTimeFieldTypeAndValue() {
        Partial fieldTypePartial = new Partial(DateTimeFieldType.year(), 2023);
        assertNotNull(fieldTypePartial);
        assertEquals(1, fieldTypePartial.size());
        assertEquals(DateTimeFieldType.year(), fieldTypePartial.getFieldType(0));
        assertEquals(2023, fieldTypePartial.getValue(0));
    }

    @Test
    public void testConstructorWithDateTimeFieldTypeAndValueAndChronology() {
        Partial fieldTypeChronologyPartial = new Partial(DateTimeFieldType.year(), 2023, chronology);
        assertNotNull(fieldTypeChronologyPartial);
        assertEquals(1, fieldTypeChronologyPartial.size());
        assertEquals(DateTimeFieldType.year(), fieldTypeChronologyPartial.getFieldType(0));
        assertEquals(2023, fieldTypeChronologyPartial.getValue(0));
        assertEquals(chronology, fieldTypeChronologyPartial.getChronology());
    }

    @Test
    public void testConstructorWithDateTimeFieldTypesAndValues() {
        Partial fieldTypesValuesPartial = new Partial(types, values);
        assertNotNull(fieldTypesValuesPartial);
        assertEquals(3, fieldTypesValuesPartial.size());
        assertEquals(DateTimeFieldType.year(), fieldTypesValuesPartial.getFieldType(0));
        assertEquals(2023, fieldTypesValuesPartial.getValue(0));
        assertEquals(DateTimeFieldType.monthOfYear(), fieldTypesValuesPartial.getFieldType(1));
        assertEquals(10, fieldTypesValuesPartial.getValue(1));
        assertEquals(DateTimeFieldType.dayOfMonth(), fieldTypesValuesPartial.getFieldType(2));
        assertEquals(15, fieldTypesValuesPartial.getValue(2));
    }

    @Test
    public void testConstructorWithDateTimeFieldTypesAndValuesAndChronology() {
        Partial fieldTypesValuesChronologyPartial = new Partial(types, values, chronology);
        assertNotNull(fieldTypesValuesChronologyPartial);
        assertEquals(3, fieldTypesValuesChronologyPartial.size());
        assertEquals(DateTimeFieldType.year(), fieldTypesValuesChronologyPartial.getFieldType(0));
        assertEquals(2023, fieldTypesValuesChronologyPartial.getValue(0));
        assertEquals(DateTimeFieldType.monthOfYear(), fieldTypesValuesChronologyPartial.getFieldType(1));
        assertEquals(10, fieldTypesValuesChronologyPartial.getValue(1));
        assertEquals(DateTimeFieldType.dayOfMonth(), fieldTypesValuesChronologyPartial.getFieldType(2));
        assertEquals(15, fieldTypesValuesChronologyPartial.getValue(2));
        assertEquals(chronology, fieldTypesValuesChronologyPartial.getChronology());
    }

    @Test
    public void testConstructorWithReadablePartial() {
        Partial readablePartial = new Partial(partial);
        assertNotNull(readablePartial);
        assertEquals(3, readablePartial.size());
        assertEquals(DateTimeFieldType.year(), readablePartial.getFieldType(0));
        assertEquals(2023, readablePartial.getValue(0));
        assertEquals(DateTimeFieldType.monthOfYear(), readablePartial.getFieldType(1));
        assertEquals(10, readablePartial.getValue(1));
        assertEquals(DateTimeFieldType.dayOfMonth(), readablePartial.getFieldType(2));
        assertEquals(15, readablePartial.getValue(2));
        assertEquals(chronology, readablePartial.getChronology());
    }

    @Test
    public void testSize() {
        assertEquals(3, partial.size());
    }

    @Test
    public void testGetChronology() {
        assertEquals(chronology, partial.getChronology());
    }

    @Test
    public void testGetFieldType() {
        assertEquals(DateTimeFieldType.year(), partial.getFieldType(0));
        assertEquals(DateTimeFieldType.monthOfYear(), partial.getFieldType(1));
        assertEquals(DateTimeFieldType.dayOfMonth(), partial.getFieldType(2));
    }

    @Test
    public void testGetFieldTypes() {
        assertTrue(Arrays.equals(types, partial.getFieldTypes()));
    }

    @Test
    public void testGetValue() {
        assertEquals(2023, partial.getValue(0));
        assertEquals(10, partial.getValue(1));
        assertEquals(15, partial.getValue(2));
    }

    @Test
    public void testGetValues() {
        assertTrue(Arrays.equals(values, partial.getValues()));
    }

    @Test
    public void testWithChronologyRetainFields() {
        Chronology newChronology = ISOChronology.getInstance();
        Partial newPartial = partial.withChronologyRetainFields(newChronology);
        assertEquals(newChronology, newPartial.getChronology());
        assertEquals(3, newPartial.size());
        assertEquals(DateTimeFieldType.year(), newPartial.getFieldType(0));
        assertEquals(2023, newPartial.getValue(0));
        assertEquals(DateTimeFieldType.monthOfYear(), newPartial.getFieldType(1));
        assertEquals(10, newPartial.getValue(1));
        assertEquals(DateTimeFieldType.dayOfMonth(), newPartial.getFieldType(2));
        assertEquals(15, newPartial.getValue(2));
    }

    @Test
    public void testWith() {
        Partial newPartial = partial.with(DateTimeFieldType.hourOfDay(), 12);
        assertEquals(4, newPartial.size());
        assertEquals(DateTimeFieldType.year(), newPartial.getFieldType(0));
        assertEquals(2023, newPartial.getValue(0));
        assertEquals(DateTimeFieldType.monthOfYear(), newPartial.getFieldType(1));
        assertEquals(10, newPartial.getValue(1));
        assertEquals(DateTimeFieldType.dayOfMonth(), newPartial.getFieldType(2));
        assertEquals(15, newPartial.getValue(2));
        assertEquals(DateTimeFieldType.hourOfDay(), newPartial.getFieldType(3));
        assertEquals(12, newPartial.getValue(3));
    }

    @Test
    public void testWithout() {
        Partial newPartial = partial.without(DateTimeFieldType.monthOfYear());
        assertEquals(2, newPartial.size());
        assertEquals(DateTimeFieldType.year(), newPartial.getFieldType(0));
        assertEquals(2023, newPartial.getValue(0));
        assertEquals(DateTimeFieldType.dayOfMonth(), newPartial.getFieldType(1));
        assertEquals(15, newPartial.getValue(1));
    }

    @Test
    public void testWithField() {
        Partial newPartial = partial.withField(DateTimeFieldType.monthOfYear(), 11);
        assertEquals(3, newPartial.size());
        assertEquals(DateTimeFieldType.year(), newPartial.getFieldType(0));
        assertEquals(2023, newPartial.getValue(0));
        assertEquals(DateTimeFieldType.monthOfYear(), newPartial.getFieldType(1));
        assertEquals(11, newPartial.getValue(1));
        assertEquals(DateTimeFieldType.dayOfMonth(), newPartial.getFieldType(2));
        assertEquals(15, newPartial.getValue(2));
    }

    @Test
    public void testWithFieldAdded() {
        Partial newPartial = partial.withFieldAdded(DateTimeFieldType.monthOfYear().getDurationType(), 1);
        assertEquals(3, newPartial.size());
        assertEquals(DateTimeFieldType.year(), newPartial.getFieldType(0));
        assertEquals(2023, newPartial.getValue(0));
        assertEquals(DateTimeFieldType.monthOfYear(), newPartial.getFieldType(1));
        assertEquals(11, newPartial.getValue(1));
        assertEquals(DateTimeFieldType.dayOfMonth(), newPartial.getFieldType(2));
        assertEquals(15, newPartial.getValue(2));
    }

    @Test
    public void testWithFieldAddWrapped() {
        Partial newPartial = partial.withFieldAddWrapped(DateTimeFieldType.monthOfYear().getDurationType(), 1);
        assertEquals(3, newPartial.size());
        assertEquals(DateTimeFieldType.year(), newPartial.getFieldType(0));
        assertEquals(2023, newPartial.getValue(0));
        assertEquals(DateTimeFieldType.monthOfYear(), newPartial.getFieldType(1));
        assertEquals(11, newPartial.getValue(1));
        assertEquals(DateTimeFieldType.dayOfMonth(), newPartial.getFieldType(2));
        assertEquals(15, newPartial.getValue(2));
    }

    @Test
    public void testWithPeriodAdded() {
        Partial newPartial = partial.withPeriodAdded(new Period(0, 1, 0, 0, 0, 0, 0, 0), 1);
        assertEquals(3, newPartial.size());
        assertEquals(DateTimeFieldType.year(), newPartial.getFieldType(0));
        assertEquals(2023, newPartial.getValue(0));
        assertEquals(DateTimeFieldType.monthOfYear(), newPartial.getFieldType(1));
        assertEquals(11, newPartial.getValue(1));
        assertEquals(DateTimeFieldType.dayOfMonth(), newPartial.getFieldType(2));
        assertEquals(15, newPartial.getValue(2));
    }

    @Test
    public void testPlus() {
        Partial newPartial = partial.plus(new Period(0, 1, 0, 0, 0, 0, 0, 0));
        assertEquals(3, newPartial.size());
        assertEquals(DateTimeFieldType.year(), newPartial.getFieldType(0));
        assertEquals(2023, newPartial.getValue(0));
        assertEquals(DateTimeFieldType.monthOfYear(), newPartial.getFieldType(1));
        assertEquals(11, newPartial.getValue(1));
        assertEquals(DateTimeFieldType.dayOfMonth(), newPartial.getFieldType(2));
        assertEquals(15, newPartial.getValue(2));
    }

    @Test
    public void testMinus() {
        Partial newPartial = partial.minus(new Period(0, 1, 0, 0, 0, 0, 0, 0));
        assertEquals(3, newPartial.size());
        assertEquals(DateTimeFieldType.year(), newPartial.getFieldType(0));
        assertEquals(2023, newPartial.getValue(0));
        assertEquals(DateTimeFieldType.monthOfYear(), newPartial.getFieldType(1));
        assertEquals(9, newPartial.getValue(1));
        assertEquals(DateTimeFieldType.dayOfMonth(), newPartial.getFieldType(2));
        assertEquals(15, newPartial.getValue(2));
    }

    @Test
    public void testProperty() {
        Partial.Property property = partial.property(DateTimeFieldType.monthOfYear());
        assertEquals(10, property.get());
    }

    @Test
    public void testIsMatchReadableInstant() {
        DateTime dateTime = new DateTime(2023, 10, 15, 0, 0, chronology);
        assertTrue(partial.isMatch(dateTime));
    }

    @Test
    public void testIsMatchReadablePartial() {
        Partial otherPartial = new Partial(types, values, chronology);
        assertTrue(partial.isMatch(otherPartial));
    }

    @Test
    public void testGetFormatter() {
        DateTimeFormatter formatter = partial.getFormatter();
        assertNotNull(formatter);
    }

    @Test
    public void testToString() {
        assertEquals("[year=2023, monthOfYear=10, dayOfMonth=15]", partial.toString());
    }

    @Test
    public void testToStringList() {
        assertEquals("[year=2023, monthOfYear=10, dayOfMonth=15]", partial.toStringList());
    }

    @Test
    public void testToStringWithPattern() {
        assertEquals("2023-10-15", partial.toString("yyyy-MM-dd"));
    }

    @Test
    public void testToStringWithPatternAndLocale() {
        assertEquals("2023-10-15", partial.toString("yyyy-MM-dd", Locale.ENGLISH));
    }
}