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
        // Initialize with a sample byte array and BIG_ENDIAN order
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0b10101010, (byte) 0b11001100});
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
        // Read 8 bits and expect 0b10101010
        long result = bitInputStream.readBits(8);
        assertEquals(0b10101010, result);

        // Read another 8 bits and expect 0b11001100
        result = bitInputStream.readBits(8);
        assertEquals(0b11001100, result);
    }

    @Test
    public void testReadBitsLittleEndian() throws IOException {
        // Reinitialize with LITTLE_ENDIAN order
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0b10101010, (byte) 0b11001100});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.LITTLE_ENDIAN);

        // Read 8 bits and expect 0b10101010
        long result = bitInputStream.readBits(8);
        assertEquals(0b10101010, result);

        // Read another 8 bits and expect 0b11001100
        result = bitInputStream.readBits(8);
        assertEquals(0b11001100, result);
    }

    @Test
    public void testReadBitsAcrossByteBoundary() throws IOException {
        // Read 12 bits and expect 0b101010101100
        long result = bitInputStream.readBits(12);
        assertEquals(0b101010101100L, result);
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
        // Read some bits
        bitInputStream.readBits(8);
        // Clear cache
        bitInputStream.clearBitCache();
        // Read again and expect the same result as the first read
        byteArrayInputStream.reset();
        long result = bitInputStream.readBits(8);
        assertEquals(0b10101010, result);
    }

    @Test
    public void testClose() throws IOException {
        bitInputStream.close();
        // Ensure that the stream is closed
        assertEquals(-1, byteArrayInputStream.read());
    }
}