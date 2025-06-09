package org.apache.commons.compress.archivers.zip;

import org.apache.commons.compress.archivers.zip.X7875_NewUnix;
import org.apache.commons.compress.archivers.zip.ZipShort;
import org.apache.commons.compress.archivers.zip.ZipUtil;
import org.junit.Before;
import org.junit.Test;
import java.math.BigInteger;
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
        assertEquals(1000, x7875NewUnix.getUID());
    }

    @Test
    public void testGetGID() {
        assertEquals(1000, x7875NewUnix.getGID());
    }

    @Test
    public void testSetUID() {
        long uid = 12345L;
        x7875NewUnix.setUID(uid);
        assertEquals(uid, x7875NewUnix.getUID());
    }

    @Test
    public void testSetGID() {
        long gid = 54321L;
        x7875NewUnix.setGID(gid);
        assertEquals(gid, x7875NewUnix.getGID());
    }

    @Test
    public void testGetLocalFileDataLength() {
        x7875NewUnix.setUID(12345L);
        x7875NewUnix.setGID(54321L);
        assertEquals(new ZipShort(10), x7875NewUnix.getLocalFileDataLength());
    }

    @Test
    public void testGetCentralDirectoryLength() {
        x7875NewUnix.setUID(12345L);
        x7875NewUnix.setGID(54321L);
        assertEquals(new ZipShort(10), x7875NewUnix.getCentralDirectoryLength());
    }

    @Test
    public void testGetLocalFileDataData() {
        x7875NewUnix.setUID(12345L);
        x7875NewUnix.setGID(54321L);
        byte[] data = x7875NewUnix.getLocalFileDataData();
        assertEquals(10, data.length);
        assertEquals(1, data[0]);
        assertEquals(2, data[1]);
        assertEquals(2, data[2]);
    }

    @Test
    public void testGetCentralDirectoryData() {
        byte[] data = x7875NewUnix.getCentralDirectoryData();
        assertEquals(0, data.length);
    }

    @Test
    public void testParseFromLocalFileData() throws ZipException {
        byte[] data = {1, 2, 51, 0, 1, 0, 2, 0, 51, 0};
        x7875NewUnix.parseFromLocalFileData(data, 0, data.length);
        assertEquals(12345L, x7875NewUnix.getUID());
        assertEquals(54321L, x7875NewUnix.getGID());
    }

    @Test
    public void testParseFromCentralDirectoryData() throws ZipException {
        byte[] data = {};
        x7875NewUnix.parseFromCentralDirectoryData(data, 0, data.length);
        // No exception should be thrown
    }

    @Test
    public void testToString() {
        String expected = "0x7875 Zip Extra Field: UID=1000 GID=1000";
        assertEquals(expected, x7875NewUnix.toString());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        X7875_NewUnix clone = (X7875_NewUnix) x7875NewUnix.clone();
        assertNotSame(x7875NewUnix, clone);
        assertEquals(x7875NewUnix, clone);
    }

    @Test
    public void testEquals() {
        X7875_NewUnix other = new X7875_NewUnix();
        assertTrue(x7875NewUnix.equals(other));
        other.setUID(12345L);
        assertFalse(x7875NewUnix.equals(other));
    }

    @Test
    public void testHashCode() {
        X7875_NewUnix other = new X7875_NewUnix();
        assertEquals(x7875NewUnix.hashCode(), other.hashCode());
        other.setUID(12345L);
        assertNotEquals(x7875NewUnix.hashCode(), other.hashCode());
    }

    @Test
    public void testTrimLeadingZeroesForceMinLength() {
        byte[] array = {0, 0, 1, 2, 3};
        byte[] trimmedArray = X7875_NewUnix.trimLeadingZeroesForceMinLength(array);
        assertEquals(3, trimmedArray.length);
        assertArrayEquals(new byte[]{1, 2, 3}, trimmedArray);
    }
}