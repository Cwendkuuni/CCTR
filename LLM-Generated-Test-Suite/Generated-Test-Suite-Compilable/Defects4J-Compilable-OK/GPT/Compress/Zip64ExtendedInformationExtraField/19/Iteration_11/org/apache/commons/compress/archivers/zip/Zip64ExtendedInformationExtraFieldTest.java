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
    private Zip64ExtendedInformationExtraField field;

    @Before
    public void setUp() {
        size = new ZipEightByteInteger(123456789L);
        compressedSize = new ZipEightByteInteger(987654321L);
        relativeHeaderOffset = new ZipEightByteInteger(12345L);
        diskStart = new ZipLong(1L);
        field = new Zip64ExtendedInformationExtraField(size, compressedSize, relativeHeaderOffset, diskStart);
    }

    @Test
    public void testConstructorWithSizeAndCompressedSize() {
        Zip64ExtendedInformationExtraField field = new Zip64ExtendedInformationExtraField(size, compressedSize);
        assertEquals(size, field.getSize());
        assertEquals(compressedSize, field.getCompressedSize());
        assertNull(field.getRelativeHeaderOffset());
        assertNull(field.getDiskStartNumber());
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
        assertEquals(new ZipShort(0x0001), field.getHeaderId());
    }

    @Test
    public void testGetLocalFileDataLength() {
        assertEquals(new ZipShort(2 * 8), field.getLocalFileDataLength());
    }

    @Test
    public void testGetCentralDirectoryLength() {
        assertEquals(new ZipShort(3 * 8 + 4), field.getCentralDirectoryLength());
    }

    @Test
    public void testGetLocalFileDataData() {
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

    @Test
    public void testGetCentralDirectoryData() {
        byte[] data = field.getCentralDirectoryData();
        assertNotNull(data);
        assertEquals(28, data.length);
    }

    @Test
    public void testParseFromLocalFileData() throws ZipException {
        byte[] data = field.getLocalFileDataData();
        Zip64ExtendedInformationExtraField newField = new Zip64ExtendedInformationExtraField();
        newField.parseFromLocalFileData(data, 0, data.length);
        assertEquals(size, newField.getSize());
        assertEquals(compressedSize, newField.getCompressedSize());
    }

    @Test(expected = ZipException.class)
    public void testParseFromLocalFileDataWithInvalidLength() throws ZipException {
        byte[] data = new byte[8];
        Zip64ExtendedInformationExtraField newField = new Zip64ExtendedInformationExtraField();
        newField.parseFromLocalFileData(data, 0, data.length);
    }

    @Test
    public void testParseFromCentralDirectoryData() throws ZipException {
        byte[] data = field.getCentralDirectoryData();
        Zip64ExtendedInformationExtraField newField = new Zip64ExtendedInformationExtraField();
        newField.parseFromCentralDirectoryData(data, 0, data.length);
        assertEquals(size, newField.getSize());
        assertEquals(compressedSize, newField.getCompressedSize());
        assertEquals(relativeHeaderOffset, newField.getRelativeHeaderOffset());
        assertEquals(diskStart, newField.getDiskStartNumber());
    }

    @Test
    public void testReparseCentralDirectoryData() throws ZipException {
        byte[] data = field.getCentralDirectoryData();
        Zip64ExtendedInformationExtraField newField = new Zip64ExtendedInformationExtraField();
        newField.parseFromCentralDirectoryData(data, 0, data.length);
        newField.reparseCentralDirectoryData(true, true, true, true);
        assertEquals(size, newField.getSize());
        assertEquals(compressedSize, newField.getCompressedSize());
        assertEquals(relativeHeaderOffset, newField.getRelativeHeaderOffset());
        assertEquals(diskStart, newField.getDiskStartNumber());
    }

    @Test(expected = ZipException.class)
    public void testReparseCentralDirectoryDataWithInvalidLength() throws ZipException {
        byte[] data = field.getCentralDirectoryData();
        Zip64ExtendedInformationExtraField newField = new Zip64ExtendedInformationExtraField();
        newField.parseFromCentralDirectoryData(data, 0, data.length);
        newField.reparseCentralDirectoryData(true, true, false, false);
    }

    @Test
    public void testGetSize() {
        assertEquals(size, field.getSize());
    }

    @Test
    public void testSetSize() {
        ZipEightByteInteger newSize = new ZipEightByteInteger(111111111L);
        field.setSize(newSize);
        assertEquals(newSize, field.getSize());
    }

    @Test
    public void testGetCompressedSize() {
        assertEquals(compressedSize, field.getCompressedSize());
    }

    @Test
    public void testSetCompressedSize() {
        ZipEightByteInteger newCompressedSize = new ZipEightByteInteger(222222222L);
        field.setCompressedSize(newCompressedSize);
        assertEquals(newCompressedSize, field.getCompressedSize());
    }

    @Test
    public void testGetRelativeHeaderOffset() {
        assertEquals(relativeHeaderOffset, field.getRelativeHeaderOffset());
    }

    @Test
    public void testSetRelativeHeaderOffset() {
        ZipEightByteInteger newRho = new ZipEightByteInteger(333333333L);
        field.setRelativeHeaderOffset(newRho);
        assertEquals(newRho, field.getRelativeHeaderOffset());
    }

    @Test
    public void testGetDiskStartNumber() {
        assertEquals(diskStart, field.getDiskStartNumber());
    }

    @Test
    public void testSetDiskStartNumber() {
        ZipLong newDiskStart = new ZipLong(2L);
        field.setDiskStartNumber(newDiskStart);
        assertEquals(newDiskStart, field.getDiskStartNumber());
    }
}