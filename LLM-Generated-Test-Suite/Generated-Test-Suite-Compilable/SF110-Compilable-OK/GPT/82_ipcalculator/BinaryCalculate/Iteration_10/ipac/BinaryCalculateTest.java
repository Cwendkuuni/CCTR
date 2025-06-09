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
        assertEquals("0", binaryCalculate.addBinary("0", "0"));
        assertEquals("1", binaryCalculate.addBinary("0", "1"));
        assertEquals("10000", binaryCalculate.addBinary("1111", "1"));
    }

    @Test
    public void testSubBinary() {
        assertEquals("100", binaryCalculate.subBinary("101", "1"));
        assertEquals("0", binaryCalculate.subBinary("1", "1"));
        assertEquals("1110", binaryCalculate.subBinary("1111", "1"));
        assertEquals("0", binaryCalculate.subBinary("0", "0"));
    }

    @Test
    public void testIPCalculate() {
        // Basic test case for IPCalculate
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
        // Basic test case for prefixInPrefixCalculate
        try {
            binaryCalculate.prefixInPrefixCalculate("11000000101010000000000100000001", "11111111111111111111111100000000", 1, "/24");
            // If no exception is thrown, the test passes
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }
}