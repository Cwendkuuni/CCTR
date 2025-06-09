package com.google.common.base;

import com.google.common.base.*;
import org.junit.Test;
import java.util.*;
import java.util.regex.Pattern;
import static org.junit.Assert.*;

public class PredicatesTest {

    @Test
    public void testAlwaysTrue() {
        Predicate<Object> predicate = Predicates.alwaysTrue();
        assertTrue(predicate.apply(null));
        assertTrue(predicate.apply(new Object()));
    }

    @Test
    public void testAlwaysFalse() {
        Predicate<Object> predicate = Predicates.alwaysFalse();
        assertFalse(predicate.apply(null));
        assertFalse(predicate.apply(new Object()));
    }

    @Test
    public void testIsNull() {
        Predicate<Object> predicate = Predicates.isNull();
        assertTrue(predicate.apply(null));
        assertFalse(predicate.apply(new Object()));
    }

    @Test
    public void testNotNull() {
        Predicate<Object> predicate = Predicates.notNull();
        assertFalse(predicate.apply(null));
        assertTrue(predicate.apply(new Object()));
    }

    @Test
    public void testNot() {
        Predicate<Object> truePredicate = Predicates.alwaysTrue();
        Predicate<Object> notPredicate = Predicates.not(truePredicate);
        assertFalse(notPredicate.apply(null));
        assertFalse(notPredicate.apply(new Object()));
    }

    @Test
    public void testAndIterable() {
        List<Predicate<Object>> predicates = Arrays.asList(Predicates.alwaysTrue(), Predicates.notNull());
        Predicate<Object> andPredicate = Predicates.and(predicates);
        assertTrue(andPredicate.apply(new Object()));
        assertFalse(andPredicate.apply(null));
    }

    @Test
    public void testAndVarargs() {
        Predicate<Object> andPredicate = Predicates.and(Predicates.alwaysTrue(), Predicates.notNull());
        assertTrue(andPredicate.apply(new Object()));
        assertFalse(andPredicate.apply(null));
    }

    @Test
    public void testOrIterable() {
        List<Predicate<Object>> predicates = Arrays.asList(Predicates.alwaysFalse(), Predicates.notNull());
        Predicate<Object> orPredicate = Predicates.or(predicates);
        assertTrue(orPredicate.apply(new Object()));
        assertFalse(orPredicate.apply(null));
    }

    @Test
    public void testOrVarargs() {
        Predicate<Object> orPredicate = Predicates.or(Predicates.alwaysFalse(), Predicates.notNull());
        assertTrue(orPredicate.apply(new Object()));
        assertFalse(orPredicate.apply(null));
    }

    @Test
    public void testEqualTo() {
        Object target = new Object();
        Predicate<Object> equalToPredicate = Predicates.equalTo(target);
        assertTrue(equalToPredicate.apply(target));
        assertFalse(equalToPredicate.apply(new Object()));
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
        Predicate<CharSequence> containsPatternPredicate = Predicates.containsPattern("test");
        assertTrue(containsPatternPredicate.apply("this is a test"));
        assertFalse(containsPatternPredicate.apply("no match"));
    }

    @Test
    public void testContains() {
        Pattern pattern = Pattern.compile("test");
        Predicate<CharSequence> containsPredicate = Predicates.contains(pattern);
        assertTrue(containsPredicate.apply("this is a test"));
        assertFalse(containsPredicate.apply("no match"));
    }
}