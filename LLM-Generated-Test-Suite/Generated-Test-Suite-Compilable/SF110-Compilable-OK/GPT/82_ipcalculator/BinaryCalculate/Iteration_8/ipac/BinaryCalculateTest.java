package ipac;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import ipac.BinaryCalculate;

public class BinaryCalculateTest {

    private BinaryCalculate binaryCalculate;

    @Before
    public void setUp() {
        binaryCalculate = new BinaryCalculate();
    }

    @Test
    public void testAddBinary() {
        assertEquals("110", binaryCalculate.addBinary("101", "1"));
        assertEquals("1000", binaryCalculate.addBinary("111", "1"));
        assertEquals("10101", binaryCalculate.addBinary("1010", "1011"));
    }

    @Test
    public void testSubBinary() {
        assertEquals("100", binaryCalculate.subBinary("110", "10"));
        assertEquals("0", binaryCalculate.subBinary("101", "101"));
        assertEquals("1", binaryCalculate.subBinary("101", "100"));
    }

    @Test
    public void testIPCalculate() {
        String result = binaryCalculate.IPCalculate("11000000101010000000000100000001", "11111111111111111111111100000000");
        assertTrue(result.contains("Total Range:"));
        assertTrue(result.contains("Usable Range:"));
        assertTrue(result.contains("Total usable IP Addresses :"));
        assertTrue(result.contains("Subnet:"));
        assertTrue(result.contains("Binary Subnet:"));
        assertTrue(result.contains("Broadcast Address:"));
        assertTrue(result.contains("Prefix:"));
        assertTrue(result.contains("Netmask:"));
        assertTrue(result.contains("Binary Netmask:"));
    }

    @Test
    public void testPrefixInPrefixCalculate() {
        // This method does not return anything, so we need to ensure it runs without exceptions
        binaryCalculate.prefixInPrefixCalculate("11000000101010000000000100000001", "11111111111111111111111100000000", 5, "/24");
    }

    // Additional tests for edge cases and specific scenarios
    @Test
    public void testAddBinaryWithEmptyStrings() {
        assertEquals("0", binaryCalculate.addBinary("", ""));
    }

    @Test
    public void testSubBinaryWithEmptyStrings() {
        assertEquals("0", binaryCalculate.subBinary("", ""));
    }

    @Test
    public void testIPCalculateWithIPv6() {
        String result = binaryCalculate.IPCalculate("20010DB8000000000000000000000001", "FFFF0000000000000000000000000000");
        assertTrue(result.contains("Total Range:"));
        assertTrue(result.contains("Usable Range:"));
        assertTrue(result.contains("Total usable IP Addresses :"));
        assertTrue(result.contains("Subnet:"));
        assertTrue(result.contains("Binary Subnet:"));
        assertTrue(result.contains("Broadcast Address:"));
        assertTrue(result.contains("Prefix:"));
        assertTrue(result.contains("Netmask:"));
        assertTrue(result.contains("Binary Netmask:"));
    }
}