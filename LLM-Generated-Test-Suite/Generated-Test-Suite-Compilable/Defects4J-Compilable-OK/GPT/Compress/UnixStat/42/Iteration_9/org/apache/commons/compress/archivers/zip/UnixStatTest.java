package org.apache.commons.compress.archivers.zip;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.apache.commons.compress.archivers.zip.UnixStat;

public class UnixStatTest {

    @Test
    public void testPermMask() {
        assertEquals("PERM_MASK should be 07777", 07777, UnixStat.PERM_MASK);
    }

    @Test
    public void testLinkFlag() {
        assertEquals("LINK_FLAG should be 0120000", 0120000, UnixStat.LINK_FLAG);
    }

    @Test
    public void testFileFlag() {
        assertEquals("FILE_FLAG should be 0100000", 0100000, UnixStat.FILE_FLAG);
    }

    @Test
    public void testDirFlag() {
        assertEquals("DIR_FLAG should be 040000", 040000, UnixStat.DIR_FLAG);
    }

    @Test
    public void testDefaultLinkPerm() {
        assertEquals("DEFAULT_LINK_PERM should be 0777", 0777, UnixStat.DEFAULT_LINK_PERM);
    }

    @Test
    public void testDefaultDirPerm() {
        assertEquals("DEFAULT_DIR_PERM should be 0755", 0755, UnixStat.DEFAULT_DIR_PERM);
    }

    @Test
    public void testDefaultFilePerm() {
        assertEquals("DEFAULT_FILE_PERM should be 0644", 0644, UnixStat.DEFAULT_FILE_PERM);
    }
}