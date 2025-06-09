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
        assertEquals(0L, bitInputStream.readBits(1));
        assertEquals(1L, bitInputStream.readBits(2));
        assertEquals(0L, bitInputStream.readBits(3));
        assertEquals(1L, bitInputStream.readBits(4));
        assertEquals(0L, bitInputStream.readBits(5));
        assertEquals(1L, bitInputStream.readBits(6));
        assertEquals(0L, bitInputStream.readBits(7));
        assertEquals(2L, bitInputStream.readBits(8));
        assertEquals(3L, bitInputStream.readBits(8));
        assertEquals(4L, bitInputStream.readBits(8));
    }

    @Test
    public void testReadBitsLittleEndian() throws IOException {
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.LITTLE_ENDIAN);
        assertEquals(1L, bitInputStream.readBits(1));
        assertEquals(0L, bitInputStream.readBits(1));
        assertEquals(0L, bitInputStream.readBits(2));
        assertEquals(0L, bitInputStream.readBits(3));
        assertEquals(0L, bitInputStream.readBits(4));
        assertEquals(0L, bitInputStream.readBits(5));
        assertEquals(0L, bitInputStream.readBits(6));
        assertEquals(0L, bitInputStream.readBits(7));
        assertEquals(2L, bitInputStream.readBits(8));
        assertEquals(3L, bitInputStream.readBits(8));
        assertEquals(4L, bitInputStream.readBits(8));
    }

    @Test
    public void testReadBitsEndOfStream() throws IOException {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{0x01});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);
        assertEquals(1L, bitInputStream.readBits(1));
        assertEquals(0L, bitInputStream.readBits(1));
        assertEquals(0L, bitInputStream.readBits(1));
        assertEquals(0L, bitInputStream.readBits(1));
        assertEquals(0L, bitInputStream.readBits(1));
        assertEquals(0L, bitInputStream.readBits(1));
        assertEquals(0L, bitInputStream.readBits(1));
        assertEquals(0L, bitInputStream.readBits(1));
        assertEquals(-1L, bitInputStream.readBits(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadBitsInvalidCount() throws IOException {
        bitInputStream.readBits(64);
    }

    @Test
    public void testClearBitCache() throws IOException {
        bitInputStream.readBits(1);
        bitInputStream.clearBitCache();
        assertEquals(1L, bitInputStream.readBits(1));
    }

    @Test
    public void testClose() throws IOException {
        bitInputStream.close();
        assertTrue(byteArrayInputStream.available() == 0);
    }
}