package com.google.common.base;

import com.google.common.base.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;
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
        Predicate<Object> notTrue = Predicates.not(alwaysTrue);
        assertFalse(notTrue.apply(new Object()));
        assertFalse(notTrue.apply(null));

        Predicate<Object> notFalse = Predicates.not(alwaysFalse);
        assertTrue(notFalse.apply(new Object()));
        assertTrue(notFalse.apply(null));
    }

    @Test
    public void testAnd() {
        Predicate<Object> andTrueTrue = Predicates.and(alwaysTrue, alwaysTrue);
        assertTrue(andTrueTrue.apply(new Object()));

        Predicate<Object> andTrueFalse = Predicates.and(alwaysTrue, alwaysFalse);
        assertFalse(andTrueFalse.apply(new Object()));

        Predicate<Object> andFalseFalse = Predicates.and(alwaysFalse, alwaysFalse);
        assertFalse(andFalseFalse.apply(new Object()));
    }

    @Test
    public void testOr() {
        Predicate<Object> orTrueTrue = Predicates.or(alwaysTrue, alwaysTrue);
        assertTrue(orTrueTrue.apply(new Object()));

        Predicate<Object> orTrueFalse = Predicates.or(alwaysTrue, alwaysFalse);
        assertTrue(orTrueFalse.apply(new Object()));

        Predicate<Object> orFalseFalse = Predicates.or(alwaysFalse, alwaysFalse);
        assertFalse(orFalseFalse.apply(new Object()));
    }

    @Test
    public void testEqualTo() {
        Object obj = new Object();
        Predicate<Object> equalToObj = Predicates.equalTo(obj);
        assertTrue(equalToObj.apply(obj));
        assertFalse(equalToObj.apply(new Object()));
        assertFalse(equalToObj.apply(null));

        Predicate<Object> equalToNull = Predicates.equalTo(null);
        assertTrue(equalToNull.apply(null));
        assertFalse(equalToNull.apply(new Object()));
    }

    @Test
    public void testIn() {
        List<String> list = Arrays.asList("a", "b", "c");
        Predicate<String> inList = Predicates.in(list);
        assertTrue(inList.apply("a"));
        assertFalse(inList.apply("d"));
    }

    @Test
    public void testCompose() {
        Predicate<Integer> isEven = new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        };
        Function<String, Integer> parseInt = new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return Integer.parseInt(input);
            }
        };
        Predicate<String> isEvenString = Predicates.compose(isEven, parseInt);
        assertTrue(isEvenString.apply("2"));
        assertFalse(isEvenString.apply("3"));
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

    @Test
    public void testInstanceOf() {
        Predicate<Object> instanceOfString = Predicates.instanceOf(String.class);
        assertTrue(instanceOfString.apply("string"));
        assertFalse(instanceOfString.apply(123));
    }

    @Test
    public void testAssignableFrom() {
        Predicate<Class<?>> assignableFromNumber = Predicates.assignableFrom(Number.class);
        assertTrue(assignableFromNumber.apply(Integer.class));
        assertFalse(assignableFromNumber.apply(String.class));
    }
}