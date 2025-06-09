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
        // Initialize with a sample byte array and BIG_ENDIAN order
        byte[] data = new byte[]{(byte) 0b10101010, (byte) 0b11110000};
        byteArrayInputStream = new ByteArrayInputStream(data);
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);
    }

    @After
    public void tearDown() throws IOException {
        if (bitInputStream != null) {
            bitInputStream.close();
        }
    }

    @Test
    public void testReadBitsBigEndian() throws IOException {
        long result = bitInputStream.readBits(4);
        assertEquals(0b1010, result);

        result = bitInputStream.readBits(4);
        assertEquals(0b1010, result);

        result = bitInputStream.readBits(4);
        assertEquals(0b1111, result);

        result = bitInputStream.readBits(4);
        assertEquals(0b0000, result);
    }

    @Test
    public void testReadBitsLittleEndian() throws IOException {
        // Reinitialize with LITTLE_ENDIAN order
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0b10101010, (byte) 0b11110000});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.LITTLE_ENDIAN);

        long result = bitInputStream.readBits(4);
        assertEquals(0b1010, result);

        result = bitInputStream.readBits(4);
        assertEquals(0b1010, result);

        result = bitInputStream.readBits(4);
        assertEquals(0b0000, result);

        result = bitInputStream.readBits(4);
        assertEquals(0b1111, result);
    }

    @Test
    public void testReadBitsEndOfStream() throws IOException {
        // Read more bits than available to reach end of stream
        bitInputStream.readBits(16);
        long result = bitInputStream.readBits(8);
        assertEquals(-1, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadBitsInvalidCountNegative() throws IOException {
        bitInputStream.readBits(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadBitsInvalidCountTooLarge() throws IOException {
        bitInputStream.readBits(64);
    }

    @Test
    public void testClearBitCache() throws IOException {
        bitInputStream.readBits(4);
        bitInputStream.clearBitCache();
        long result = bitInputStream.readBits(4);
        assertEquals(0b1010, result);
    }

    @Test
    public void testClose() throws IOException {
        bitInputStream.close();
        assertTrue(byteArrayInputStream.available() == 0);
    }
}