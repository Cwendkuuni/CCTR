package com.google.common.cache;

import com.google.common.cache.CacheBuilderSpec;
import com.google.common.cache.LocalCache;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;

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
    public void testParseEmptySpecification() {
        spec = CacheBuilderSpec.parse("");
        Assert.assertNotNull(spec);
    }

    @Test
    public void testParseInitialCapacity() {
        spec = CacheBuilderSpec.parse("initialCapacity=10");
        Assert.assertEquals(Integer.valueOf(10), spec.initialCapacity);
    }

    @Test
    public void testParseMaximumSize() {
        spec = CacheBuilderSpec.parse("maximumSize=100");
        Assert.assertEquals(Long.valueOf(100), spec.maximumSize);
    }

    @Test
    public void testParseMaximumWeight() {
        spec = CacheBuilderSpec.parse("maximumWeight=200");
        Assert.assertEquals(Long.valueOf(200), spec.maximumWeight);
    }

    @Test
    public void testParseConcurrencyLevel() {
        spec = CacheBuilderSpec.parse("concurrencyLevel=5");
        Assert.assertEquals(Integer.valueOf(5), spec.concurrencyLevel);
    }

    @Test
    public void testParseWeakKeys() {
        spec = CacheBuilderSpec.parse("weakKeys");
        Assert.assertEquals(LocalCache.Strength.WEAK, spec.keyStrength);
    }

    @Test
    public void testParseSoftValues() {
        spec = CacheBuilderSpec.parse("softValues");
        Assert.assertEquals(LocalCache.Strength.SOFT, spec.valueStrength);
    }

    @Test
    public void testParseWeakValues() {
        spec = CacheBuilderSpec.parse("weakValues");
        Assert.assertEquals(LocalCache.Strength.WEAK, spec.valueStrength);
    }

    @Test
    public void testParseRecordStats() {
        spec = CacheBuilderSpec.parse("recordStats");
        Assert.assertTrue(spec.recordStats);
    }

    @Test
    public void testParseExpireAfterAccess() {
        spec = CacheBuilderSpec.parse("expireAfterAccess=10s");
        Assert.assertEquals(10, spec.accessExpirationDuration);
        Assert.assertEquals(TimeUnit.SECONDS, spec.accessExpirationTimeUnit);
    }

    @Test
    public void testParseExpireAfterWrite() {
        spec = CacheBuilderSpec.parse("expireAfterWrite=5m");
        Assert.assertEquals(5, spec.writeExpirationDuration);
        Assert.assertEquals(TimeUnit.MINUTES, spec.writeExpirationTimeUnit);
    }

    @Test
    public void testParseRefreshAfterWrite() {
        spec = CacheBuilderSpec.parse("refreshAfterWrite=1h");
        Assert.assertEquals(1, spec.refreshDuration);
        Assert.assertEquals(TimeUnit.HOURS, spec.refreshTimeUnit);
    }

    @Test
    public void testDisableCaching() {
        spec = CacheBuilderSpec.disableCaching();
        Assert.assertEquals(Long.valueOf(0), spec.maximumSize);
    }

    @Test
    public void testToCacheBuilder() {
        spec = CacheBuilderSpec.parse("initialCapacity=10,maximumSize=100,concurrencyLevel=5");
        CacheBuilder<Object, Object> builder = spec.toCacheBuilder();
        Assert.assertNotNull(builder);
    }

    @Test
    public void testToParsableString() {
        spec = CacheBuilderSpec.parse("initialCapacity=10");
        Assert.assertEquals("initialCapacity=10", spec.toParsableString());
    }

    @Test
    public void testToString() {
        spec = CacheBuilderSpec.parse("initialCapacity=10");
        Assert.assertTrue(spec.toString().contains("initialCapacity=10"));
    }

    @Test
    public void testHashCode() {
        spec = CacheBuilderSpec.parse("initialCapacity=10");
        int hashCode = spec.hashCode();
        Assert.assertNotEquals(0, hashCode);
    }

    @Test
    public void testEquals() {
        CacheBuilderSpec spec1 = CacheBuilderSpec.parse("initialCapacity=10");
        CacheBuilderSpec spec2 = CacheBuilderSpec.parse("initialCapacity=10");
        Assert.assertTrue(spec1.equals(spec2));
    }

    @Test
    public void testNotEquals() {
        CacheBuilderSpec spec1 = CacheBuilderSpec.parse("initialCapacity=10");
        CacheBuilderSpec spec2 = CacheBuilderSpec.parse("initialCapacity=20");
        Assert.assertFalse(spec1.equals(spec2));
    }
}