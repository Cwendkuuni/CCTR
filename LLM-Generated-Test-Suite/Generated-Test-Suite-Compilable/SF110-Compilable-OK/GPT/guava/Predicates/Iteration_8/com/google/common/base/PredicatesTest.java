package com.google.common.base;

import com.google.common.base.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
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

        andPredicate = Predicates.and(alwaysFalse, notNull);
        assertFalse(andPredicate.apply(new Object()));
        assertFalse(andPredicate.apply(null));
    }

    @Test
    public void testOr() {
        Predicate<Object> orPredicate = Predicates.or(alwaysTrue, notNull);
        assertTrue(orPredicate.apply(new Object()));
        assertTrue(orPredicate.apply(null));

        orPredicate = Predicates.or(alwaysFalse, notNull);
        assertTrue(orPredicate.apply(new Object()));
        assertFalse(orPredicate.apply(null));
    }

    @Test
    public void testEqualTo() {
        Predicate<Object> equalToPredicate = Predicates.equalTo("test");
        assertTrue(equalToPredicate.apply("test"));
        assertFalse(equalToPredicate.apply("not test"));
        assertFalse(equalToPredicate.apply(null));

        equalToPredicate = Predicates.equalTo(null);
        assertTrue(equalToPredicate.apply(null));
        assertFalse(equalToPredicate.apply("test"));
    }

    @Test
    public void testIn() {
        Collection<String> collection = Arrays.asList("a", "b", "c");
        Predicate<String> inPredicate = Predicates.in(collection);
        assertTrue(inPredicate.apply("a"));
        assertFalse(inPredicate.apply("d"));
        assertFalse(inPredicate.apply(null));
    }

    @Test
    public void testCompose() {
        Predicate<Integer> greaterThanZero = new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input > 0;
            }
        };
        Function<String, Integer> stringLength = new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return input.length();
            }
        };
        Predicate<String> composedPredicate = Predicates.compose(greaterThanZero, stringLength);
        assertTrue(composedPredicate.apply("test"));
        assertFalse(composedPredicate.apply(""));
    }

    @Test
    public void testContainsPattern() {
        Predicate<CharSequence> containsPattern = Predicates.containsPattern("test");
        assertTrue(containsPattern.apply("this is a test"));
        assertFalse(containsPattern.apply("no match here"));
    }

    @Test
    public void testContains() {
        Pattern pattern = Pattern.compile("test");
        Predicate<CharSequence> contains = Predicates.contains(pattern);
        assertTrue(contains.apply("this is a test"));
        assertFalse(contains.apply("no match here"));
    }

    @Test
    public void testInstanceOf() {
        Predicate<Object> instanceOfPredicate = Predicates.instanceOf(String.class);
        assertTrue(instanceOfPredicate.apply("test"));
        assertFalse(instanceOfPredicate.apply(123));
    }

    @Test
    public void testAssignableFrom() {
        Predicate<Class<?>> assignableFromPredicate = Predicates.assignableFrom(Number.class);
        assertTrue(assignableFromPredicate.apply(Integer.class));
        assertFalse(assignableFromPredicate.apply(String.class));
    }
}