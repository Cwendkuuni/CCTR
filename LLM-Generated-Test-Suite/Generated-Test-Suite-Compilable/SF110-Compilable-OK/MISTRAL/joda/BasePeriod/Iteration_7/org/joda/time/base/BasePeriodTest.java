package org.joda.time.base;

import org.joda.time.*;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BasePeriodTest {

    private BasePeriod basePeriod;
    private PeriodType periodType;
    private Chronology chronology;
    private ReadableInstant startInstant;
    private ReadableInstant endInstant;
    private ReadablePartial startPartial;
    private ReadablePartial endPartial;
    private ReadableDuration duration;

    @Before
    public void setUp() {
        periodType = PeriodType.standard();
        chronology = ISOChronology.getInstanceUTC();
        startInstant = new DateTime(2023, 1, 1, 0, 0, 0, 0, chronology);
        endInstant = new DateTime(2023, 1, 2, 0, 0, 0, 0, chronology);
        startPartial = new LocalDate(2023, 1, 1);
        endPartial = new LocalDate(2023, 1, 2);
        duration = new Duration(1000);
    }

    @Test
    public void testConstructorWithInts() {
        basePeriod = new BasePeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        assertEquals(1, basePeriod.getValue(0));
        assertEquals(2, basePeriod.getValue(1));
        assertEquals(3, basePeriod.getValue(2));
        assertEquals(4, basePeriod.getValue(3));
        assertEquals(5, basePeriod.getValue(4));
        assertEquals(6, basePeriod.getValue(5));
        assertEquals(7, basePeriod.getValue(6));
        assertEquals(8, basePeriod.getValue(7));
    }

    @Test
    public void testConstructorWithLongs() {
        basePeriod = new BasePeriod(1000L, 2000L, periodType, chronology) {
            @Override
            public int size() {
                return 8;
            }
        };
        assertNotNull(basePeriod.getValues());
    }

    @Test
    public void testConstructorWithInstants() {
        basePeriod = new BasePeriod(startInstant, endInstant, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        assertNotNull(basePeriod.getValues());
    }

    @Test
    public void testConstructorWithPartials() {
        basePeriod = new BasePeriod(startPartial, endPartial, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        assertNotNull(basePeriod.getValues());
    }

    @Test
    public void testConstructorWithInstantAndDuration() {
        basePeriod = new BasePeriod(startInstant, duration, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        assertNotNull(basePeriod.getValues());
    }

    @Test
    public void testConstructorWithDurationAndInstant() {
        basePeriod = new BasePeriod(duration, endInstant, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        assertNotNull(basePeriod.getValues());
    }

    @Test
    public void testConstructorWithLong() {
        basePeriod = new BasePeriod(1000L) {
            @Override
            public int size() {
                return 8;
            }
        };
        assertNotNull(basePeriod.getValues());
    }

    @Test
    public void testConstructorWithLongPeriodTypeChronology() {
        basePeriod = new BasePeriod(1000L, periodType, chronology) {
            @Override
            public int size() {
                return 8;
            }
        };
        assertNotNull(basePeriod.getValues());
    }

    @Test
    public void testConstructorWithObjectPeriodTypeChronology() {
        basePeriod = new BasePeriod(new Period(1, 2, 3, 4, 5, 6, 7, 8), periodType, chronology) {
            @Override
            public int size() {
                return 8;
            }
        };
        assertNotNull(basePeriod.getValues());
    }

    @Test
    public void testConstructorWithIntArrayPeriodType() {
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8};
        basePeriod = new BasePeriod(values, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        assertArrayEquals(values, basePeriod.getValues());
    }

    @Test
    public void testCheckPeriodType() {
        assertEquals(periodType, basePeriod.checkPeriodType(periodType));
    }

    @Test
    public void testGetPeriodType() {
        basePeriod = new BasePeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        assertEquals(periodType, basePeriod.getPeriodType());
    }

    @Test
    public void testGetValue() {
        basePeriod = new BasePeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        assertEquals(1, basePeriod.getValue(0));
        assertEquals(2, basePeriod.getValue(1));
        assertEquals(3, basePeriod.getValue(2));
        assertEquals(4, basePeriod.getValue(3));
        assertEquals(5, basePeriod.getValue(4));
        assertEquals(6, basePeriod.getValue(5));
        assertEquals(7, basePeriod.getValue(6));
        assertEquals(8, basePeriod.getValue(7));
    }

    @Test
    public void testToDurationFrom() {
        basePeriod = new BasePeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        Duration duration = basePeriod.toDurationFrom(startInstant);
        assertNotNull(duration);
    }

    @Test
    public void testToDurationTo() {
        basePeriod = new BasePeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        Duration duration = basePeriod.toDurationTo(endInstant);
        assertNotNull(duration);
    }

    @Test
    public void testSetPeriod() {
        basePeriod = new BasePeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        ReadablePeriod period = new Period(1, 1, 1, 1, 1, 1, 1, 1);
        basePeriod.setPeriod(period);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 1, 1}, basePeriod.getValues());
    }

    @Test
    public void testSetPeriodWithInts() {
        basePeriod = new BasePeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        basePeriod.setPeriod(1, 1, 1, 1, 1, 1, 1, 1);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 1, 1}, basePeriod.getValues());
    }

    @Test
    public void testSetField() {
        basePeriod = new BasePeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        basePeriod.setField(DurationFieldType.years(), 10);
        assertEquals(10, basePeriod.getValue(0));
    }

    @Test
    public void testAddField() {
        basePeriod = new BasePeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        basePeriod.addField(DurationFieldType.years(), 10);
        assertEquals(11, basePeriod.getValue(0));
    }

    @Test
    public void testMergePeriod() {
        basePeriod = new BasePeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        ReadablePeriod period = new Period(1, 1, 1, 1, 1, 1, 1, 1);
        basePeriod.mergePeriod(period);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 1, 1}, basePeriod.getValues());
    }

    @Test
    public void testAddPeriod() {
        basePeriod = new BasePeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        ReadablePeriod period = new Period(1, 1, 1, 1, 1, 1, 1, 1);
        basePeriod.addPeriod(period);
        assertArrayEquals(new int[]{2, 3, 4, 5, 6, 7, 8, 9}, basePeriod.getValues());
    }

    @Test
    public void testSetValue() {
        basePeriod = new BasePeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        basePeriod.setValue(0, 10);
        assertEquals(10, basePeriod.getValue(0));
    }

    @Test
    public void testSetValues() {
        basePeriod = new BasePeriod(1, 2, 3, 4, 5, 6, 7, 8, periodType) {
            @Override
            public int size() {
                return 8;
            }
        };
        int[] values = {10, 20, 30, 40, 50, 60, 70, 80};
        basePeriod.setValues(values);
        assertArrayEquals(values, basePeriod.getValues());
    }
}