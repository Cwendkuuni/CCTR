package org.apache.commons.compress.utils;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteOrder;

public class BitInputStreamTest {

    private BitInputStream bitInputStream;
    private ByteArrayInputStream byteArrayInputStream;

    @Before
    public void setUp() {
        // Setup with a simple byte array for testing
        byte[] data = new byte[]{(byte) 0b10101010, (byte) 0b11001100, (byte) 0b11110000};
        byteArrayInputStream = new ByteArrayInputStream(data);
    }

    @After
    public void tearDown() throws IOException {
        if (bitInputStream != null) {
            bitInputStream.close();
        }
    }

    @Test
    public void testReadBitsBigEndian() throws IOException {
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);

        assertEquals(0b10101010, bitInputStream.readBits(8));
        assertEquals(0b11001100, bitInputStream.readBits(8));
        assertEquals(0b11110000, bitInputStream.readBits(8));
        assertEquals(-1, bitInputStream.readBits(8)); // End of stream
    }

    @Test
    public void testReadBitsLittleEndian() throws IOException {
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.LITTLE_ENDIAN);

        assertEquals(0b01010101, bitInputStream.readBits(8));
        assertEquals(0b00110011, bitInputStream.readBits(8));
        assertEquals(0b00001111, bitInputStream.readBits(8));
        assertEquals(-1, bitInputStream.readBits(8)); // End of stream
    }

    @Test
    public void testReadBitsAcrossByteBoundary() throws IOException {
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);

        assertEquals(0b1010101011001100L, bitInputStream.readBits(16));
        assertEquals(0b11110000, bitInputStream.readBits(8));
        assertEquals(-1, bitInputStream.readBits(8)); // End of stream
    }

    @Test
    public void testClearBitCache() throws IOException {
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);

        assertEquals(0b101010, bitInputStream.readBits(6));
        bitInputStream.clearBitCache();
        assertEquals(0b11001100, bitInputStream.readBits(8)); // Should start fresh from the next byte
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadBitsWithInvalidCountNegative() throws IOException {
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);
        bitInputStream.readBits(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadBitsWithInvalidCountTooLarge() throws IOException {
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);
        bitInputStream.readBits(64);
    }

    @Test
    public void testReadBitsEndOfStream() throws IOException {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);

        assertEquals(-1, bitInputStream.readBits(8)); // End of stream
    }
}