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
        // This setup will be overridden in each test case as needed
    }

    @After
    public void tearDown() throws IOException {
        if (bitInputStream != null) {
            bitInputStream.close();
        }
    }

    @Test
    public void testConstructor() {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{0b00000000});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);
        assertNotNull(bitInputStream);
    }

    @Test
    public void testReadBitsBigEndian() throws IOException {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0b10101010, (byte) 0b11001100});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);
        assertEquals(0b10101010, bitInputStream.readBits(8));
        assertEquals(0b11001100, bitInputStream.readBits(8));
    }

    @Test
    public void testReadBitsLittleEndian() throws IOException {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0b10101010, (byte) 0b11001100});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.LITTLE_ENDIAN);
        assertEquals(0b01010101, bitInputStream.readBits(8));
        assertEquals(0b00110011, bitInputStream.readBits(8));
    }

    @Test
    public void testReadBitsAcrossByteBoundary() throws IOException {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0b10101010, (byte) 0b11001100});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);
        assertEquals(0b1010101011001100L, bitInputStream.readBits(16));
    }

    @Test
    public void testReadBitsEndOfStream() throws IOException {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0b10101010});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);
        assertEquals(0b10101010, bitInputStream.readBits(8));
        assertEquals(-1, bitInputStream.readBits(8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadBitsInvalidCountNegative() throws IOException {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0b10101010});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);
        bitInputStream.readBits(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadBitsInvalidCountTooLarge() throws IOException {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0b10101010});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);
        bitInputStream.readBits(64);
    }

    @Test
    public void testClearBitCache() throws IOException {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0b10101010, (byte) 0b11001100});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);
        bitInputStream.readBits(4);
        bitInputStream.clearBitCache();
        assertEquals(0b11001100, bitInputStream.readBits(8));
    }

    @Test
    public void testClose() throws IOException {
        byteArrayInputStream = new ByteArrayInputStream(new byte[]{(byte) 0b10101010});
        bitInputStream = new BitInputStream(byteArrayInputStream, ByteOrder.BIG_ENDIAN);
        bitInputStream.close();
        assertEquals(-1, bitInputStream.readBits(8)); // Stream should be closed
    }
}