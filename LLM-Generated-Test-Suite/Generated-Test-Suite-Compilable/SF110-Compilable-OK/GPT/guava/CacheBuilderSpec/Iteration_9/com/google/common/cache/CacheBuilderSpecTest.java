package com.google.common.cache;

import com.google.common.cache.CacheBuilderSpec;
import com.google.common.cache.LocalCache;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.util.concurrent.TimeUnit;

public class CacheBuilderSpecTest {

    private CacheBuilderSpec spec;

    @Before
    public void setUp() {
        // Setup code if needed
    }

    @After
    public void tearDown() {
        // Teardown code if needed
    }

    @Test
    public void testParse_EmptySpecification() {
        spec = CacheBuilderSpec.parse("");
        assertNotNull(spec);
        assertEquals("", spec.toParsableString());
    }

    @Test
    public void testParse_InitialCapacity() {
        spec = CacheBuilderSpec.parse("initialCapacity=10");
        assertNotNull(spec);
        assertEquals(Integer.valueOf(10), spec.initialCapacity);
    }

    @Test
    public void testParse_MaximumSize() {
        spec = CacheBuilderSpec.parse("maximumSize=100");
        assertNotNull(spec);
        assertEquals(Long.valueOf(100), spec.maximumSize);
    }

    @Test
    public void testParse_MaximumWeight() {
        spec = CacheBuilderSpec.parse("maximumWeight=200");
        assertNotNull(spec);
        assertEquals(Long.valueOf(200), spec.maximumWeight);
    }

    @Test
    public void testParse_ConcurrencyLevel() {
        spec = CacheBuilderSpec.parse("concurrencyLevel=5");
        assertNotNull(spec);
        assertEquals(Integer.valueOf(5), spec.concurrencyLevel);
    }

    @Test
    public void testParse_WeakKeys() {
        spec = CacheBuilderSpec.parse("weakKeys");
        assertNotNull(spec);
        assertEquals(LocalCache.Strength.WEAK, spec.keyStrength);
    }

    @Test
    public void testParse_SoftValues() {
        spec = CacheBuilderSpec.parse("softValues");
        assertNotNull(spec);
        assertEquals(LocalCache.Strength.SOFT, spec.valueStrength);
    }

    @Test
    public void testParse_WeakValues() {
        spec = CacheBuilderSpec.parse("weakValues");
        assertNotNull(spec);
        assertEquals(LocalCache.Strength.WEAK, spec.valueStrength);
    }

    @Test
    public void testParse_RecordStats() {
        spec = CacheBuilderSpec.parse("recordStats");
        assertNotNull(spec);
        assertTrue(spec.recordStats);
    }

    @Test
    public void testParse_ExpireAfterAccess() {
        spec = CacheBuilderSpec.parse("expireAfterAccess=10s");
        assertNotNull(spec);
        assertEquals(10, spec.accessExpirationDuration);
        assertEquals(TimeUnit.SECONDS, spec.accessExpirationTimeUnit);
    }

    @Test
    public void testParse_ExpireAfterWrite() {
        spec = CacheBuilderSpec.parse("expireAfterWrite=5m");
        assertNotNull(spec);
        assertEquals(5, spec.writeExpirationDuration);
        assertEquals(TimeUnit.MINUTES, spec.writeExpirationTimeUnit);
    }

    @Test
    public void testParse_RefreshAfterWrite() {
        spec = CacheBuilderSpec.parse("refreshAfterWrite=1h");
        assertNotNull(spec);
        assertEquals(1, spec.refreshDuration);
        assertEquals(TimeUnit.HOURS, spec.refreshTimeUnit);
    }

    @Test
    public void testDisableCaching() {
        spec = CacheBuilderSpec.disableCaching();
        assertNotNull(spec);
        assertEquals(Long.valueOf(0), spec.maximumSize);
    }

    @Test
    public void testToCacheBuilder() {
        spec = CacheBuilderSpec.parse("initialCapacity=10,maximumSize=100,concurrencyLevel=5,weakKeys,softValues,recordStats,expireAfterAccess=10s,expireAfterWrite=5m,refreshAfterWrite=1h");
        assertNotNull(spec);
        CacheBuilder<Object, Object> builder = spec.toCacheBuilder();
        assertNotNull(builder);
    }

    @Test
    public void testToParsableString() {
        spec = CacheBuilderSpec.parse("initialCapacity=10");
        assertEquals("initialCapacity=10", spec.toParsableString());
    }

    @Test
    public void testToString() {
        spec = CacheBuilderSpec.parse("initialCapacity=10");
        assertTrue(spec.toString().contains("initialCapacity=10"));
    }

    @Test
    public void testHashCode() {
        CacheBuilderSpec spec1 = CacheBuilderSpec.parse("initialCapacity=10");
        CacheBuilderSpec spec2 = CacheBuilderSpec.parse("initialCapacity=10");
        assertEquals(spec1.hashCode(), spec2.hashCode());
    }

    @Test
    public void testEquals_SameObject() {
        spec = CacheBuilderSpec.parse("initialCapacity=10");
        assertTrue(spec.equals(spec));
    }

    @Test
    public void testEquals_NullObject() {
        spec = CacheBuilderSpec.parse("initialCapacity=10");
        assertFalse(spec.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        spec = CacheBuilderSpec.parse("initialCapacity=10");
        assertFalse(spec.equals("Some String"));
    }

    @Test
    public void testEquals_EqualObjects() {
        CacheBuilderSpec spec1 = CacheBuilderSpec.parse("initialCapacity=10");
        CacheBuilderSpec spec2 = CacheBuilderSpec.parse("initialCapacity=10");
        assertTrue(spec1.equals(spec2));
    }

    @Test
    public void testEquals_DifferentObjects() {
        CacheBuilderSpec spec1 = CacheBuilderSpec.parse("initialCapacity=10");
        CacheBuilderSpec spec2 = CacheBuilderSpec.parse("initialCapacity=20");
        assertFalse(spec1.equals(spec2));
    }
}