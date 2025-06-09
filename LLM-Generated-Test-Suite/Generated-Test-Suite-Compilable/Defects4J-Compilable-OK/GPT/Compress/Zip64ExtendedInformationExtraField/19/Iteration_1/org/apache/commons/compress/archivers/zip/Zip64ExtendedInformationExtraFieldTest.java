package org.apache.commons.compress.archivers.zip;

import org.apache.commons.compress.archivers.zip.ZipEightByteInteger;
import org.apache.commons.compress.archivers.zip.ZipLong;
import org.apache.commons.compress.archivers.zip.ZipShort;
import org.apache.commons.compress.archivers.zip.Zip64ExtendedInformationExtraField;
import org.junit.Before;
import org.junit.Test;

import java.util.zip.ZipException;

import static org.junit.Assert.*;

public class Zip64ExtendedInformationExtraFieldTest {

    private ZipEightByteInteger size;
    private ZipEightByteInteger compressedSize;
    private ZipEightByteInteger relativeHeaderOffset;
    private ZipLong diskStart;

    @Before
    public void setUp() {
        size = new ZipEightByteInteger(123456789L);
        compressedSize = new ZipEightByteInteger(987654321L);
        relativeHeaderOffset = new ZipEightByteInteger(12345L);
        diskStart = new ZipLong(1L);
    }

    @Test
    public void testConstructorWithSizeAndCompressedSize() {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField(size, compressedSize);
        assertEquals(size, field.getSize());
        assertEquals(compressedSize, field.getCompressedSize());
        assertNull(field.getRelativeHeaderOffset());
        assertNull(field.getDiskStartNumber());
    }

    @Test
    public void testConstructorWithAllFields() {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField(size, compressedSize, relativeHeaderOffset, diskStart);
        assertEquals(size, field.getSize());
        assertEquals(compressedSize, field.getCompressedSize());
        assertEquals(relativeHeaderOffset, field.getRelativeHeaderOffset());
        assertEquals(diskStart, field.getDiskStartNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullSize() {
        new Zip64ExtendedInformationExtraField(null, compressedSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullCompressedSize() {
        new Zip64ExtendedInformationExtraField(size, null);
    }

    @Test
    public void testGetHeaderId() {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField();
        assertEquals(new ZipShort(0x0001), field.getHeaderId());
    }

    @Test
    public void testGetLocalFileDataLength() {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField(size, compressedSize);
        assertEquals(new ZipShort(2 * 8), field.getLocalFileDataLength());
    }

    @Test
    public void testGetCentralDirectoryLength() {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField(size, compressedSize, relativeHeaderOffset, diskStart);
        assertEquals(new ZipShort(3 * 8 + 4), field.getCentralDirectoryLength());
    }

    @Test
    public void testGetLocalFileDataData() {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField(size, compressedSize);
        byte[] data = field.getLocalFileDataData();
        assertNotNull(data);
        assertEquals(16, data.length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetLocalFileDataDataWithMissingSize() {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField();
        field.setCompressedSize(compressedSize);
        field.getLocalFileDataData();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetLocalFileDataDataWithMissingCompressedSize() {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField();
        field.setSize(size);
        field.getLocalFileDataData();
    }

    @Test
    public void testGetCentralDirectoryData() {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField(size, compressedSize, relativeHeaderOffset, diskStart);
        byte[] data = field.getCentralDirectoryData();
        assertNotNull(data);
        assertEquals(28, data.length);
    }

    @Test
    public void testParseFromLocalFileData() throws ZipException {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField();
        byte[] data = new byte[16];
        System.arraycopy(size.getBytes(), 0, data, 0, 8);
        System.arraycopy(compressedSize.getBytes(), 0, data, 8, 8);
        field.parseFromLocalFileData(data, 0, data.length);
        assertEquals(size, field.getSize());
        assertEquals(compressedSize, field.getCompressedSize());
    }

    @Test(expected = ZipException.class)
    public void testParseFromLocalFileDataWithInsufficientLength() throws ZipException {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField();
        byte[] data = new byte[8];
        field.parseFromLocalFileData(data, 0, data.length);
    }

    @Test
    public void testParseFromCentralDirectoryData() throws ZipException {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField();
        byte[] data = new byte[28];
        System.arraycopy(size.getBytes(), 0, data, 0, 8);
        System.arraycopy(compressedSize.getBytes(), 0, data, 8, 8);
        System.arraycopy(relativeHeaderOffset.getBytes(), 0, data, 16, 8);
        System.arraycopy(diskStart.getBytes(), 0, data, 24, 4);
        field.parseFromCentralDirectoryData(data, 0, data.length);
        assertEquals(size, field.getSize());
        assertEquals(compressedSize, field.getCompressedSize());
        assertEquals(relativeHeaderOffset, field.getRelativeHeaderOffset());
        assertEquals(diskStart, field.getDiskStartNumber());
    }

    @Test
    public void testReparseCentralDirectoryData() throws ZipException {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField();
        byte[] data = new byte[28];
        System.arraycopy(size.getBytes(), 0, data, 0, 8);
        System.arraycopy(compressedSize.getBytes(), 0, data, 8, 8);
        System.arraycopy(relativeHeaderOffset.getBytes(), 0, data, 16, 8);
        System.arraycopy(diskStart.getBytes(), 0, data, 24, 4);
        field.parseFromCentralDirectoryData(data, 0, data.length);
        field.reparseCentralDirectoryData(true, true, true, true);
        assertEquals(size, field.getSize());
        assertEquals(compressedSize, field.getCompressedSize());
        assertEquals(relativeHeaderOffset, field.getRelativeHeaderOffset());
        assertEquals(diskStart, field.getDiskStartNumber());
    }

    @Test(expected = ZipException.class)
    public void testReparseCentralDirectoryDataWithMismatchedLength() throws ZipException {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField();
        byte[] data = new byte[28];
        System.arraycopy(size.getBytes(), 0, data, 0, 8);
        System.arraycopy(compressedSize.getBytes(), 0, data, 8, 8);
        System.arraycopy(relativeHeaderOffset.getBytes(), 0, data, 16, 8);
        System.arraycopy(diskStart.getBytes(), 0, data, 24, 4);
        field.parseFromCentralDirectoryData(data, 0, data.length);
        field.reparseCentralDirectoryData(true, true, false, true);
    }

    @Test
    public void testSettersAndGetters() {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField();
        field.setSize(size);
        field.setCompressedSize(compressedSize);
        field.setRelativeHeaderOffset(relativeHeaderOffset);
        field.setDiskStartNumber(diskStart);

        assertEquals(size, field.getSize());
        assertEquals(compressedSize, field.getCompressedSize());
        assertEquals(relativeHeaderOffset, field.getRelativeHeaderOffset());
        assertEquals(diskStart, field.getDiskStartNumber());
    }
}