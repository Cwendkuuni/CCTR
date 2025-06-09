package org.apache.commons.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.Assert.*;

public class PosixParserTest {

    private PosixParser parser;
    private Options options;

    @Before
    public void setUp() {
        parser = new PosixParser();
        options = new Options();
        options.addOption("a", false, "Option a");
        options.addOption("b", true, "Option b");
        options.addOption("c", "cOption", true, "Option c");
    }

    @Test
    public void testFlattenWithStopAtNonOption() {
        String[] args = {"-a", "arg1", "--", "arg2"};
        String[] expected = {"-a", "arg1", "--", "arg2"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithoutStopAtNonOption() {
        String[] args = {"-a", "arg1", "arg2"};
        String[] expected = {"-a", "arg1", "arg2"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithDoubleDash() {
        String[] args = {"--cOption=value"};
        String[] expected = {"--cOption", "value"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithSingleHyphen() {
        String[] args = {"-"};
        String[] expected = {"-"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithBurstToken() {
        String[] args = {"-abc"};
        String[] expected = {"-a", "-b", "c"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithUnknownOptionAndStopAtNonOption() {
        String[] args = {"-x", "arg"};
        String[] expected = {"--", "x", "arg"};
        String[] result = parser.flatten(options, args, true);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testFlattenWithUnknownOptionWithoutStopAtNonOption() {
        String[] args = {"-x", "arg"};
        String[] expected = {"-x", "arg"};
        String[] result = parser.flatten(options, args, false);
        assertArrayEquals(expected, result);
    }

    // Reflection-based tests for private methods
    @Test
    public void testProcessSingleHyphen() throws Exception {
        Method method = PosixParser.class.getDeclaredMethod("processSingleHyphen", String.class);
        method.setAccessible(true);
        method.invoke(parser, "-");
        String[] expected = {"-"};
        assertArrayEquals(expected, parser.flatten(options, new String[]{"-"}, false));
    }

    @Test
    public void testProcessOptionToken() throws Exception {
        Method method = PosixParser.class.getDeclaredMethod("processOptionToken", String.class, boolean.class);
        method.setAccessible(true);
        method.invoke(parser, "-a", false);
        String[] expected = {"-a"};
        assertArrayEquals(expected, parser.flatten(options, new String[]{"-a"}, false));
    }

    @Test
    public void testBurstToken() throws Exception {
        Method method = PosixParser.class.getDeclaredMethod("burstToken", String.class, boolean.class);
        method.setAccessible(true);
        method.invoke(parser, "-abc", false);
        String[] expected = {"-a", "-b", "c"};
        assertArrayEquals(expected, parser.flatten(options, new String[]{"-abc"}, false));
    }
}