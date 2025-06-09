package com.google.common.base;

import com.google.common.base.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.util.regex.Pattern;

public class PredicatesTest {

    private Predicate<Object> alwaysTrue;
    private Predicate<Object> alwaysFalse;
    private Predicate<Object> isNull;
    private Predicate<Object> notNull;

    @Before
    public void setUp() {
        alwaysTrue = Predicates.alwaysTrue();
        alwaysFalse = Predicates.alwaysFalse();
        isNull = Predicates.isNull();
        notNull = Predicates.notNull();
    }

    @Test
    public void testAlwaysTrue() {
        assertTrue(alwaysTrue.apply(new Object()));
        assertTrue(alwaysTrue.apply(null));
    }

    @Test
    public void testAlwaysFalse() {
        assertFalse(alwaysFalse.apply(new Object()));
        assertFalse(alwaysFalse.apply(null));
    }

    @Test
    public void testIsNull() {
        assertTrue(isNull.apply(null));
        assertFalse(isNull.apply(new Object()));
    }

    @Test
    public void testNotNull() {
        assertFalse(notNull.apply(null));
        assertTrue(notNull.apply(new Object()));
    }

    @Test
    public void testNot() {
        Predicate<Object> notAlwaysTrue = Predicates.not(alwaysTrue);
        assertFalse(notAlwaysTrue.apply(new Object()));
        assertFalse(notAlwaysTrue.apply(null));

        Predicate<Object> notAlwaysFalse = Predicates.not(alwaysFalse);
        assertTrue(notAlwaysFalse.apply(new Object()));
        assertTrue(notAlwaysFalse.apply(null));
    }

    @Test
    public void testAnd() {
        Predicate<Object> andPredicate = Predicates.and(alwaysTrue, notNull);
        assertTrue(andPredicate.apply(new Object()));
        assertFalse(andPredicate.apply(null));

        List<Predicate<Object>> predicates = Arrays.asList(alwaysTrue, notNull);
        Predicate<Object> andIterablePredicate = Predicates.and(predicates);
        assertTrue(andIterablePredicate.apply(new Object()));
        assertFalse(andIterablePredicate.apply(null));
    }

    @Test
    public void testOr() {
        Predicate<Object> orPredicate = Predicates.or(alwaysFalse, notNull);
        assertTrue(orPredicate.apply(new Object()));
        assertFalse(orPredicate.apply(null));

        List<Predicate<Object>> predicates = Arrays.asList(alwaysFalse, notNull);
        Predicate<Object> orIterablePredicate = Predicates.or(predicates);
        assertTrue(orIterablePredicate.apply(new Object()));
        assertFalse(orIterablePredicate.apply(null));
    }

    @Test
    public void testEqualTo() {
        Object obj = new Object();
        Predicate<Object> equalToPredicate = Predicates.equalTo(obj);
        assertTrue(equalToPredicate.apply(obj));
        assertFalse(equalToPredicate.apply(new Object()));
        assertFalse(equalToPredicate.apply(null));

        Predicate<Object> equalToNullPredicate = Predicates.equalTo(null);
        assertTrue(equalToNullPredicate.apply(null));
        assertFalse(equalToNullPredicate.apply(new Object()));
    }

    @Test
    public void testIn() {
        List<String> list = Arrays.asList("a", "b", "c");
        Predicate<String> inPredicate = Predicates.in(list);
        assertTrue(inPredicate.apply("a"));
        assertFalse(inPredicate.apply("d"));
    }

    @Test
    public void testCompose() {
        Function<String, Integer> lengthFunction = String::length;
        Predicate<Integer> greaterThanTwo = input -> input > 2;
        Predicate<String> composedPredicate = Predicates.compose(greaterThanTwo, lengthFunction);
        assertTrue(composedPredicate.apply("hello"));
        assertFalse(composedPredicate.apply("hi"));
    }

    @Test
    public void testContainsPattern() {
        Predicate<CharSequence> containsPatternPredicate = Predicates.containsPattern("test");
        assertTrue(containsPatternPredicate.apply("this is a test"));
        assertFalse(containsPatternPredicate.apply("no match here"));
    }

    @Test
    public void testContains() {
        Pattern pattern = Pattern.compile("test");
        Predicate<CharSequence> containsPredicate = Predicates.contains(pattern);
        assertTrue(containsPredicate.apply("this is a test"));
        assertFalse(containsPredicate.apply("no match here"));
    }

    @Test
    public void testInstanceOf() {
        Predicate<Object> instanceOfPredicate = Predicates.instanceOf(String.class);
        assertTrue(instanceOfPredicate.apply("string"));
        assertFalse(instanceOfPredicate.apply(123));
    }

    @Test
    public void testAssignableFrom() {
        Predicate<Class<?>> assignableFromPredicate = Predicates.assignableFrom(Number.class);
        assertTrue(assignableFromPredicate.apply(Integer.class));
        assertFalse(assignableFromPredicate.apply(String.class));
    }
}