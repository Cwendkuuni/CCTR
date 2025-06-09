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
        assertEquals("10000", binaryCalculate.addBinary("1111", "1"));
        assertEquals("0", binaryCalculate.addBinary("0", "0"));
        assertEquals("1", binaryCalculate.addBinary("0", "1"));
        assertEquals("10", binaryCalculate.addBinary("1", "1"));
    }

    @Test
    public void testSubBinary() {
        assertEquals("100", binaryCalculate.subBinary("101", "1"));
        assertEquals("1110", binaryCalculate.subBinary("1111", "1"));
        assertEquals("0", binaryCalculate.subBinary("0", "0"));
        assertEquals("1", binaryCalculate.subBinary("10", "1"));
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

    // Since private methods cannot be tested directly, they are tested indirectly through public methods.
    // For example, getBinaryIP, getNetmask, calculate, checkIPType, convert, convertToBinary, and getStartIP
    // are all used within the public methods and are thus tested indirectly.

    // Additional tests can be added to cover edge cases and more complex scenarios.
}