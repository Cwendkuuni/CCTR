package com.google.common.base;

import com.google.common.base.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;
import java.util.regex.Pattern;
import static org.junit.Assert.*;

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
        assertTrue(alwaysTrue.apply(null));
        assertTrue(alwaysTrue.apply(new Object()));
    }

    @Test
    public void testAlwaysFalse() {
        assertFalse(alwaysFalse.apply(null));
        assertFalse(alwaysFalse.apply(new Object()));
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
        Predicate<Object> notTrue = Predicates.not(alwaysTrue);
        assertFalse(notTrue.apply(null));
        assertFalse(notTrue.apply(new Object()));

        Predicate<Object> notFalse = Predicates.not(alwaysFalse);
        assertTrue(notFalse.apply(null));
        assertTrue(notFalse.apply(new Object()));
    }

    @Test
    public void testAnd() {
        Predicate<Object> andPredicate = Predicates.and(alwaysTrue, notNull);
        assertTrue(andPredicate.apply(new Object()));
        assertFalse(andPredicate.apply(null));

        List<Predicate<Object>> predicates = Arrays.asList(alwaysTrue, notNull);
        andPredicate = Predicates.and(predicates);
        assertTrue(andPredicate.apply(new Object()));
        assertFalse(andPredicate.apply(null));
    }

    @Test
    public void testOr() {
        Predicate<Object> orPredicate = Predicates.or(alwaysFalse, notNull);
        assertTrue(orPredicate.apply(new Object()));
        assertFalse(orPredicate.apply(null));

        List<Predicate<Object>> predicates = Arrays.asList(alwaysFalse, notNull);
        orPredicate = Predicates.or(predicates);
        assertTrue(orPredicate.apply(new Object()));
        assertFalse(orPredicate.apply(null));
    }

    @Test
    public void testEqualTo() {
        Object obj = new Object();
        Predicate<Object> equalToPredicate = Predicates.equalTo(obj);
        assertTrue(equalToPredicate.apply(obj));
        assertFalse(equalToPredicate.apply(new Object()));
        assertFalse(equalToPredicate.apply(null));

        equalToPredicate = Predicates.equalTo(null);
        assertTrue(equalToPredicate.apply(null));
        assertFalse(equalToPredicate.apply(new Object()));
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
        Predicate<Integer> greaterThanZero = new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input > 0;
            }
        };

        Function<String, Integer> stringToInteger = new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return Integer.parseInt(input);
            }
        };

        Predicate<String> composedPredicate = Predicates.compose(greaterThanZero, stringToInteger);
        assertTrue(composedPredicate.apply("1"));
        assertFalse(composedPredicate.apply("-1"));
    }

    @Test
    public void testContainsPattern() {
        Predicate<CharSequence> containsPattern = Predicates.containsPattern("foo");
        assertTrue(containsPattern.apply("foobar"));
        assertFalse(containsPattern.apply("barbaz"));
    }

    @Test
    public void testContains() {
        Pattern pattern = Pattern.compile("foo");
        Predicate<CharSequence> contains = Predicates.contains(pattern);
        assertTrue(contains.apply("foobar"));
        assertFalse(contains.apply("barbaz"));
    }

    // GWT Incompatible tests
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