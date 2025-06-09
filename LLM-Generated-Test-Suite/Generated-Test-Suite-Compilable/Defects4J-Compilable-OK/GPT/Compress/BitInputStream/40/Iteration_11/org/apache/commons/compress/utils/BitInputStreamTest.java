package org.apache.commons.compress.utils;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import org.apache.commons.compress.utils.BitInputStream;

public class BitInputStreamTest {

    private BitInputStream bitInputStream;
    private ByteArrayInputStream byteArrayInputStream;

    @Before
    public void setUp() {
        // Setup will be done in individual test methods
    }

    @After
    public void tearDown() throws IOException {
        if (bitInputStream != null) {
            bitInputStream.close();
        }
    }

    @Test
    public void testReadBitsBigEndian() throws IOException {
        byte[] data = {(byte) 0b10101010, (byte) 0b11001100};
        byteArrayInputStream = new ByteArrayInputStream(data);
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);

        assertEquals(0b10101010, bitInputStream.readBits(8));
        assertEquals(0b11001100, bitInputStream.readBits(8));
        assertEquals(-1, bitInputStream.readBits(8)); // End of stream
    }

    @Test
    public void testReadBitsLittleEndian() throws IOException {
        byte[] data = {(byte) 0b10101010, (byte) 0b11001100};
        byteArrayInputStream = new ByteArrayInputStream(data);
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.LITTLE_ENDIAN);

        assertEquals(0b10101010, bitInputStream.readBits(8));
        assertEquals(0b11001100, bitInputStream.readBits(8));
        assertEquals(-1, bitInputStream.readBits(8)); // End of stream
    }

    @Test
    public void testReadBitsAcrossByteBoundaryBigEndian() throws IOException {
        byte[] data = {(byte) 0b10101010, (byte) 0b11001100};
        byteArrayInputStream = new ByteArrayInputStream(data);
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);

        assertEquals(0b1010101011001100L, bitInputStream.readBits(16));
        assertEquals(-1, bitInputStream.readBits(8)); // End of stream
    }

    @Test
    public void testReadBitsAcrossByteBoundaryLittleEndian() throws IOException {
        byte[] data = {(byte) 0b10101010, (byte) 0b11001100};
        byteArrayInputStream = new ByteArrayInputStream(data);
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.LITTLE_ENDIAN);

        assertEquals(0b1100110010101010L, bitInputStream.readBits(16));
        assertEquals(-1, bitInputStream.readBits(8)); // End of stream
    }

    @Test
    public void testClearBitCache() throws IOException {
        byte[] data = {(byte) 0b10101010, (byte) 0b11001100};
        byteArrayInputStream = new ByteArrayInputStream(data);
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);

        assertEquals(0b10101010, bitInputStream.readBits(8));
        bitInputStream.clearBitCache();
        assertEquals(0b11001100, bitInputStream.readBits(8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadBitsWithInvalidCount() throws IOException {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0b10101010});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);

        bitInputStream.readBits(64); // Invalid count, should throw exception
    }

    @Test
    public void testReadBitsWithZeroCount() throws IOException {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0b10101010});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);

        assertEquals(0, bitInputStream.readBits(0)); // Zero count should return 0
    }
}