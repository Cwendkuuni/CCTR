package org.apache.commons.compress.utils;

import org.apache.commons.compress.utils.BitInputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteOrder;

import static org.junit.Assert.*;

public class BitInputStreamTest {

    private BitInputStream bitInputStream;
    private ByteArrayInputStream byteArrayInputStream;

    @Before
    public void setUp() {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{0x01, 0x02, 0x03, 0x04});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);
    }

    @After
    public void tearDown() throws IOException {
        bitInputStream.close();
    }

    @Test
    public void testReadBits() throws IOException {
        assertEquals(1L, bitInputStream.readBits(1));
        assertEquals(2L, bitInputStream.readBits(2));
        assertEquals(3L, bitInputStream.readBits(3));
        assertEquals(4L, bitInputStream.readBits(4));
        assertEquals(5L, bitInputStream.readBits(5));
        assertEquals(6L, bitInputStream.readBits(6));
        assertEquals(7L, bitInputStream.readBits(7));
        assertEquals(8L, bitInputStream.readBits(8));
        assertEquals(9L, bitInputStream.readBits(9));
        assertEquals(10L, bitInputStream.readBits(10));
        assertEquals(11L, bitInputStream.readBits(11));
        assertEquals(12L, bitInputStream.readBits(12));
        assertEquals(13L, bitInputStream.readBits(13));
        assertEquals(14L, bitInputStream.readBits(14));
        assertEquals(15L, bitInputStream.readBits(15));
        assertEquals(16L, bitInputStream.readBits(16));
        assertEquals(17L, bitInputStream.readBits(17));
        assertEquals(18L, bitInputStream.readBits(18));
        assertEquals(19L, bitInputStream.readBits(19));
        assertEquals(20L, bitInputStream.readBits(20));
        assertEquals(21L, bitInputStream.readBits(21));
        assertEquals(22L, bitInputStream.readBits(22));
        assertEquals(23L, bitInputStream.readBits(23));
        assertEquals(24L, bitInputStream.readBits(24));
        assertEquals(25L, bitInputStream.readBits(25));
        assertEquals(26L, bitInputStream.readBits(26));
        assertEquals(27L, bitInputStream.readBits(27));
        assertEquals(28L, bitInputStream.readBits(28));
        assertEquals(29L, bitInputStream.readBits(29));
        assertEquals(30L, bitInputStream.readBits(30));
        assertEquals(31L, bitInputStream.readBits(31));
        assertEquals(32L, bitInputStream.readBits(32));
        assertEquals(33L, bitInputStream.readBits(33));
        assertEquals(34L, bitInputStream.readBits(34));
        assertEquals(35L, bitInputStream.readBits(35));
        assertEquals(36L, bitInputStream.readBits(36));
        assertEquals(37L, bitInputStream.readBits(37));
        assertEquals(38L, bitInputStream.readBits(38));
        assertEquals(39L, bitInputStream.readBits(39));
        assertEquals(40L, bitInputStream.readBits(40));
        assertEquals(41L, bitInputStream.readBits(41));
        assertEquals(42L, bitInputStream.readBits(42));
        assertEquals(43L, bitInputStream.readBits(43));
        assertEquals(44L, bitInputStream.readBits(44));
        assertEquals(45L, bitInputStream.readBits(45));
        assertEquals(46L, bitInputStream.readBits(46));
        assertEquals(47L, bitInputStream.readBits(47));
        assertEquals(48L, bitInputStream.readBits(48));
        assertEquals(49L, bitInputStream.readBits(49));
        assertEquals(50L, bitInputStream.readBits(50));
        assertEquals(51L, bitInputStream.readBits(51));
        assertEquals(52L, bitInputStream.readBits(52));
        assertEquals(53L, bitInputStream.readBits(53));
        assertEquals(54L, bitInputStream.readBits(54));
        assertEquals(55L, bitInputStream.readBits(55));
        assertEquals(56L, bitInputStream.readBits(56));
        assertEquals(57L, bitInputStream.readBits(57));
        assertEquals(58L, bitInputStream.readBits(58));
        assertEquals(59L, bitInputStream.readBits(59));
        assertEquals(60L, bitInputStream.readBits(60));
        assertEquals(61L, bitInputStream.readBits(61));
        assertEquals(62L, bitInputStream.readBits(62));
        assertEquals(63L, bitInputStream.readBits(63));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadBitsInvalidCount() throws IOException {
        bitInputStream.readBits(64);
    }

    @Test
    public void testClearBitCache() throws IOException {
        bitInputStream.readBits(8);
        bitInputStream.clearBitCache();
        assertEquals(0L, bitInputStream.readBits(8));
    }

    @Test
    public void testClose() throws IOException {
        bitInputStream.close();
        assertThrows(IOException.class, () -> bitInputStream.readBits(1));
    }
}