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
        // Setup will be done in individual tests to accommodate different scenarios
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
    public void testReadBitsAcrossByteBoundary() throws IOException {
        byte[] data = {(byte) 0b10101010, (byte) 0b11001100};
        byteArrayInputStream = new ByteArrayInputStream(data);
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);

        assertEquals(0b1010101011001100L, bitInputStream.readBits(16));
        assertEquals(-1, bitInputStream.readBits(8)); // End of stream
    }

    @Test
    public void testClearBitCache() throws IOException {
        byte[] data = {(byte) 0b10101010, (byte) 0b11001100};
        byteArrayInputStream = new ByteArrayInputStream(data);
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);

        assertEquals(0b101010, bitInputStream.readBits(6));
        bitInputStream.clearBitCache();
        assertEquals(0b11001100, bitInputStream.readBits(8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadBitsWithInvalidCount() throws IOException {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);

        bitInputStream.readBits(64); // Should throw IllegalArgumentException
    }

    @Test
    public void testReadBitsEndOfStream() throws IOException {
        byte[] data = {(byte) 0b10101010};
        byteArrayInputStream = new ByteArrayInputStream(data);
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);

        assertEquals(0b10101010, bitInputStream.readBits(8));
        assertEquals(-1, bitInputStream.readBits(8)); // End of stream
    }
}