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
    public void testNotPredicate() {
        Predicate<Object> notTrue = Predicates.not(alwaysTrue);
        assertFalse(notTrue.apply(new Object()));
        assertFalse(notTrue.apply(null));

        Predicate<Object> notFalse = Predicates.not(alwaysFalse);
        assertTrue(notFalse.apply(new Object()));
        assertTrue(notFalse.apply(null));
    }

    @Test
    public void testAndPredicate() {
        Predicate<Object> andTrueTrue = Predicates.and(alwaysTrue, alwaysTrue);
        assertTrue(andTrueTrue.apply(new Object()));

        Predicate<Object> andTrueFalse = Predicates.and(alwaysTrue, alwaysFalse);
        assertFalse(andTrueFalse.apply(new Object()));

        Predicate<Object> andFalseFalse = Predicates.and(alwaysFalse, alwaysFalse);
        assertFalse(andFalseFalse.apply(new Object()));
    }

    @Test
    public void testOrPredicate() {
        Predicate<Object> orTrueTrue = Predicates.or(alwaysTrue, alwaysTrue);
        assertTrue(orTrueTrue.apply(new Object()));

        Predicate<Object> orTrueFalse = Predicates.or(alwaysTrue, alwaysFalse);
        assertTrue(orTrueFalse.apply(new Object()));

        Predicate<Object> orFalseFalse = Predicates.or(alwaysFalse, alwaysFalse);
        assertFalse(orFalseFalse.apply(new Object()));
    }

    @Test
    public void testEqualToPredicate() {
        Predicate<String> equalToHello = Predicates.equalTo("Hello");
        assertTrue(equalToHello.apply("Hello"));
        assertFalse(equalToHello.apply("World"));
        assertFalse(equalToHello.apply(null));

        Predicate<Object> equalToNull = Predicates.equalTo(null);
        assertTrue(equalToNull.apply(null));
        assertFalse(equalToNull.apply(new Object()));
    }

    @Test
    public void testInPredicate() {
        List<String> list = Arrays.asList("a", "b", "c");
        Predicate<String> inPredicate = Predicates.in(list);
        assertTrue(inPredicate.apply("a"));
        assertFalse(inPredicate.apply("d"));
    }

    @Test
    public void testComposePredicate() {
        Predicate<Integer> greaterThanFive = new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input > 5;
            }
        };
        Function<String, Integer> stringLength = new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return input.length();
            }
        };
        Predicate<String> composed = Predicates.compose(greaterThanFive, stringLength);
        assertTrue(composed.apply("HelloWorld"));
        assertFalse(composed.apply("Hi"));
    }

    @Test
    public void testContainsPatternPredicate() {
        Predicate<CharSequence> containsPattern = Predicates.containsPattern("foo");
        assertTrue(containsPattern.apply("foobar"));
        assertFalse(containsPattern.apply("barbaz"));
    }

    @Test
    public void testContainsPredicate() {
        Pattern pattern = Pattern.compile("foo");
        Predicate<CharSequence> contains = Predicates.contains(pattern);
        assertTrue(contains.apply("foobar"));
        assertFalse(contains.apply("barbaz"));
    }

    @Test
    public void testInstanceOfPredicate() {
        Predicate<Object> instanceOfString = Predicates.instanceOf(String.class);
        assertTrue(instanceOfString.apply("Hello"));
        assertFalse(instanceOfString.apply(123));
    }

    @Test
    public void testAssignableFromPredicate() {
        Predicate<Class<?>> assignableFromNumber = Predicates.assignableFrom(Number.class);
        assertTrue(assignableFromNumber.apply(Integer.class));
        assertFalse(assignableFromNumber.apply(String.class));
    }
}