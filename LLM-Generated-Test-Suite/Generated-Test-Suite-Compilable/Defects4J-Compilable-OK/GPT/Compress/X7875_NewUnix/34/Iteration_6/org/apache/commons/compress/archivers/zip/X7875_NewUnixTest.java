package org.apache.commons.compress.archivers.zip;

import org.apache.commons.compress.archivers.zip.ZipShort;
import org.junit.Before;
import org.junit.Test;

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
    public void testGetHeaderId() {
        assertEquals(new ZipShort(0x7875), x7875NewUnix.getHeaderId());
    }

    @Test
    public void testGetUID() {
        assertEquals(1000L, x7875NewUnix.getUID());
    }

    @Test
    public void testGetGID() {
        assertEquals(1000L, x7875NewUnix.getGID());
    }

    @Test
    public void testSetUID() {
        x7875NewUnix.setUID(12345L);
        assertEquals(12345L, x7875NewUnix.getUID());
    }

    @Test
    public void testSetGID() {
        x7875NewUnix.setGID(54321L);
        assertEquals(54321L, x7875NewUnix.getGID());
    }

    @Test
    public void testGetLocalFileDataLength() {
        assertEquals(new ZipShort(5), x7875NewUnix.getLocalFileDataLength());
    }

    @Test
    public void testGetCentralDirectoryLength() {
        assertEquals(new ZipShort(5), x7875NewUnix.getCentralDirectoryLength());
    }

    @Test
    public void testGetLocalFileDataData() {
        byte[] expectedData = new byte[]{1, 1, (byte) 232, 1, (byte) 232};
        assertArrayEquals(expectedData, x7875NewUnix.getLocalFileDataData());
    }

    @Test
    public void testGetCentralDirectoryData() {
        assertArrayEquals(new byte[0], x7875NewUnix.getCentralDirectoryData());
    }

    @Test
    public void testParseFromLocalFileData() throws ZipException {
        byte[] data = new byte[]{1, 1, (byte) 144, 1, (byte) 144};
        x7875NewUnix.parseFromLocalFileData(data, 0, data.length);
        assertEquals(144L, x7875NewUnix.getUID());
        assertEquals(144L, x7875NewUnix.getGID());
    }

    @Test
    public void testParseFromCentralDirectoryData() throws ZipException {
        // This method does nothing, but we can ensure it doesn't throw an exception
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
    public void testEqualsAndHashCode() {
        X7875_NewUnix another = new X7875_NewUnix();
        assertEquals(x7875NewUnix, another);
        assertEquals(x7875NewUnix.hashCode(), another.hashCode());

        another.setUID(12345L);
        assertNotEquals(x7875NewUnix, another);
    }

    @Test
    public void testTrimLeadingZeroesForceMinLength() {
        byte[] input = new byte[]{0, 0, 1, 2, 3};
        byte[] expected = new byte[]{1, 2, 3};
        assertArrayEquals(expected, X7875_NewUnix.trimLeadingZeroesForceMinLength(input));

        byte[] singleZero = new byte[]{0};
        assertArrayEquals(singleZero, X7875_NewUnix.trimLeadingZeroesForceMinLength(singleZero));
    }
}