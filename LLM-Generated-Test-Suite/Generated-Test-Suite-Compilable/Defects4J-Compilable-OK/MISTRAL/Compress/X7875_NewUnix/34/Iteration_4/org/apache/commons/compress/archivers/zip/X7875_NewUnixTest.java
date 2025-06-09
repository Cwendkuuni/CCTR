package org.apache.commons.compress.archivers.zip;

import org.apache.commons.compress.archivers.zip.X7875_NewUnix;
import org.apache.commons.compress.archivers.zip.ZipShort;
import org.apache.commons.compress.archivers.zip.ZipUtil;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.zip.ZipException;

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
        x7875NewUnix.setUID(1000L);
        assertEquals(1000L, x7875NewUnix.getUID());
    }

    @Test
    public void testGetGID() {
        x7875NewUnix.setGID(1000L);
        assertEquals(1000L, x7875NewUnix.getGID());
    }

    @Test
    public void testSetUID() {
        x7875NewUnix.setUID(1001L);
        assertEquals(1001L, x7875NewUnix.getUID());
    }

    @Test
    public void testSetGID() {
        x7875NewUnix.setGID(1001L);
        assertEquals(1001L, x7875NewUnix.getGID());
    }

    @Test
    public void testGetLocalFileDataLength() {
        x7875NewUnix.setUID(1000L);
        x7875NewUnix.setGID(1000L);
        assertEquals(new ZipShort(7), x7875NewUnix.getLocalFileDataLength());
    }

    @Test
    public void testGetCentralDirectoryLength() {
        x7875NewUnix.setUID(1000L);
        x7875NewUnix.setGID(1000L);
        assertEquals(new ZipShort(7), x7875NewUnix.getCentralDirectoryLength());
    }

    @Test
    public void testGetLocalFileDataData() {
        x7875NewUnix.setUID(1000L);
        x7875NewUnix.setGID(1000L);
        byte[] expectedData = new byte[]{1, 2, -128, -1, 2, -128, -1};
        assertArrayEquals(expectedData, x7875NewUnix.getLocalFileDataData());
    }

    @Test
    public void testGetCentralDirectoryData() {
        byte[] expectedData = new byte[0];
        assertArrayEquals(expectedData, x7875NewUnix.getCentralDirectoryData());
    }

    @Test
    public void testParseFromLocalFileData() throws ZipException {
        byte[] data = new byte[]{1, 2, -128, -1, 2, -128, -1};
        x7875NewUnix.parseFromLocalFileData(data, 0, data.length);
        assertEquals(1000L, x7875NewUnix.getUID());
        assertEquals(1000L, x7875NewUnix.getGID());
    }

    @Test
    public void testParseFromCentralDirectoryData() throws ZipException {
        byte[] data = new byte[0];
        x7875NewUnix.parseFromCentralDirectoryData(data, 0, data.length);
        // No assertions needed as the method does nothing
    }

    @Test
    public void testToString() {
        x7875NewUnix.setUID(1000L);
        x7875NewUnix.setGID(1000L);
        assertEquals("0x7875 Zip Extra Field: UID=1000 GID=1000", x7875NewUnix.toString());
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

        other.setUID(1001L);
        assertFalse(x7875NewUnix.equals(other));

        other.setUID(1000L);
        other.setGID(1001L);
        assertFalse(x7875NewUnix.equals(other));
    }

    @Test
    public void testHashCode() {
        X7875_NewUnix other = new X7875_NewUnix();
        assertEquals(x7875NewUnix.hashCode(), other.hashCode());

        other.setUID(1001L);
        assertNotEquals(x7875NewUnix.hashCode(), other.hashCode());

        other.setUID(1000L);
        other.setGID(1001L);
        assertNotEquals(x7875NewUnix.hashCode(), other.hashCode());
    }

    @Test
    public void testTrimLeadingZeroesForceMinLength() {
        byte[] array = new byte[]{0, 0, 1, 2, 3};
        byte[] expected = new byte[]{1, 2, 3};
        assertArrayEquals(expected, X7875_NewUnix.trimLeadingZeroesForceMinLength(array));

        array = new byte[]{0, 0, 0, 0};
        expected = new byte[]{0};
        assertArrayEquals(expected, X7875_NewUnix.trimLeadingZeroesForceMinLength(array));
    }
}