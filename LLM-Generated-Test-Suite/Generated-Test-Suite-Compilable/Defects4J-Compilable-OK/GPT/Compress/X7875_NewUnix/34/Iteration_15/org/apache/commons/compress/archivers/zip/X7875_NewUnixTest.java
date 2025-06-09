package org.apache.commons.compress.archivers.zip;

import org.junit.Before;
import org.junit.Test;
import org.apache.commons.compress.archivers.zip.ZipShort;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.zip.ZipException;

import static org.junit.Assert.*;

public class X7875_NewUnixTest {

    private X7875_NewUnix x7875NewUnix;

    @Before
    public void setUp() {
        x7875NewUnix = new X7875_NewUnix();
    }

    @Test
    public void testConstructor() {
        assertEquals(1000, x7875NewUnix.getUID());
        assertEquals(1000, x7875NewUnix.getGID());
    }

    @Test
    public void testGetHeaderId() {
        assertEquals(new ZipShort(0x7875), x7875NewUnix.getHeaderId());
    }

    @Test
    public void testGetUID() {
        x7875NewUnix.setUID(12345);
        assertEquals(12345, x7875NewUnix.getUID());
    }

    @Test
    public void testGetGID() {
        x7875NewUnix.setGID(54321);
        assertEquals(54321, x7875NewUnix.getGID());
    }

    @Test
    public void testSetUID() {
        x7875NewUnix.setUID(12345);
        assertEquals(12345, x7875NewUnix.getUID());
    }

    @Test
    public void testSetGID() {
        x7875NewUnix.setGID(54321);
        assertEquals(54321, x7875NewUnix.getGID());
    }

    @Test
    public void testGetLocalFileDataLength() {
        x7875NewUnix.setUID(12345);
        x7875NewUnix.setGID(54321);
        assertEquals(new ZipShort(9), x7875NewUnix.getLocalFileDataLength());
    }

    @Test
    public void testGetCentralDirectoryLength() {
        x7875NewUnix.setUID(12345);
        x7875NewUnix.setGID(54321);
        assertEquals(new ZipShort(9), x7875NewUnix.getCentralDirectoryLength());
    }

    @Test
    public void testGetLocalFileDataData() {
        x7875NewUnix.setUID(12345);
        x7875NewUnix.setGID(54321);
        byte[] expectedData = new byte[]{1, 2, 57, 48, 2, -47, 106};
        assertArrayEquals(expectedData, x7875NewUnix.getLocalFileDataData());
    }

    @Test
    public void testGetCentralDirectoryData() {
        assertArrayEquals(new byte[0], x7875NewUnix.getCentralDirectoryData());
    }

    @Test
    public void testParseFromLocalFileData() throws ZipException {
        byte[] data = new byte[]{1, 2, 57, 48, 2, -47, 106};
        x7875NewUnix.parseFromLocalFileData(data, 0, data.length);
        assertEquals(12345, x7875NewUnix.getUID());
        assertEquals(54321, x7875NewUnix.getGID());
    }

    @Test
    public void testParseFromCentralDirectoryData() throws ZipException {
        // This method does nothing, but we can still call it to ensure no exceptions are thrown.
        x7875NewUnix.parseFromCentralDirectoryData(new byte[0], 0, 0);
    }

    @Test
    public void testToString() {
        String expected = "0x7875 Zip Extra Field: UID=1000 GID=1000";
        assertEquals(expected, x7875NewUnix.toString());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        X7875_NewUnix clone = (X7875_NewUnix) x7875NewUnix.clone();
        assertEquals(x7875NewUnix, clone);
        assertNotSame(x7875NewUnix, clone);
    }

    @Test
    public void testEquals() {
        X7875_NewUnix another = new X7875_NewUnix();
        assertTrue(x7875NewUnix.equals(another));
        another.setUID(12345);
        assertFalse(x7875NewUnix.equals(another));
    }

    @Test
    public void testHashCode() {
        X7875_NewUnix another = new X7875_NewUnix();
        assertEquals(x7875NewUnix.hashCode(), another.hashCode());
        another.setUID(12345);
        assertNotEquals(x7875NewUnix.hashCode(), another.hashCode());
    }

    @Test
    public void testTrimLeadingZeroesForceMinLength() {
        byte[] input = new byte[]{0, 0, 1, 2, 3};
        byte[] expected = new byte[]{1, 2, 3};
        assertArrayEquals(expected, X7875_NewUnix.trimLeadingZeroesForceMinLength(input));

        byte[] inputSingleZero = new byte[]{0};
        byte[] expectedSingleZero = new byte[]{0};
        assertArrayEquals(expectedSingleZero, X7875_NewUnix.trimLeadingZeroesForceMinLength(inputSingleZero));
    }
}