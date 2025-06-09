package org.apache.commons.compress.archivers;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.ar.ArArchiveInputStream;
import org.apache.commons.compress.archivers.ar.ArArchiveOutputStream;
import org.apache.commons.compress.archivers.arj.ArjArchiveInputStream;
import org.apache.commons.compress.archivers.cpio.CpioArchiveInputStream;
import org.apache.commons.compress.archivers.cpio.CpioArchiveOutputStream;
import org.apache.commons.compress.archivers.dump.DumpArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class ArchiveStreamFactoryTest {

    private ArchiveStreamFactory factory;

    @Before
    public void setUp() {
        factory = new ArchiveStreamFactory();
    }

    @Test
    public void testGetEntryEncoding() {
        assertNull(factory.getEntryEncoding());
    }

    @Test
    public void testSetEntryEncoding() {
        factory.setEntryEncoding("UTF-8");
        assertEquals("UTF-8", factory.getEntryEncoding());
    }

    @Test(expected = IllegalStateException.class)
    public void testSetEntryEncodingWithConstructorEncoding() {
        ArchiveStreamFactory factoryWithEncoding = new ArchiveStreamFactory("UTF-8");
        factoryWithEncoding.setEntryEncoding("ISO-8859-1");
    }

    @Test
    public void testCreateArchiveInputStreamAR() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.AR, in) instanceof ArArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStreamARJ() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.ARJ, in) instanceof ArjArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStreamZIP() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, in) instanceof ZipArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStreamTAR() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.TAR, in) instanceof TarArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStreamJAR() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.JAR, in) instanceof JarArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStreamCPIO() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.CPIO, in) instanceof CpioArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStreamDUMP() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.DUMP, in) instanceof DumpArchiveInputStream);
    }

    @Test(expected = StreamingNotSupportedException.class)
    public void testCreateArchiveInputStreamSEVEN_Z() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        factory.createArchiveInputStream(ArchiveStreamFactory.SEVEN_Z, in);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveInputStreamUnknown() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        factory.createArchiveInputStream("unknown", in);
    }

    @Test
    public void testCreateArchiveOutputStreamAR() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.AR, out) instanceof ArArchiveOutputStream);
    }

    @Test
    public void testCreateArchiveOutputStreamZIP() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, out) instanceof ZipArchiveOutputStream);
    }

    @Test
    public void testCreateArchiveOutputStreamTAR() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.TAR, out) instanceof TarArchiveOutputStream);
    }

    @Test
    public void testCreateArchiveOutputStreamJAR() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.JAR, out) instanceof JarArchiveOutputStream);
    }

    @Test
    public void testCreateArchiveOutputStreamCPIO() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.CPIO, out) instanceof CpioArchiveOutputStream);
    }

    @Test(expected = StreamingNotSupportedException.class)
    public void testCreateArchiveOutputStreamSEVEN_Z() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        factory.createArchiveOutputStream(ArchiveStreamFactory.SEVEN_Z, out);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveOutputStreamUnknown() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        factory.createArchiveOutputStream("unknown", out);
    }

    @Test
    public void testCreateArchiveInputStreamAutoDetect() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        try {
            factory.createArchiveInputStream(in);
        } catch (ArchiveException e) {
            assertEquals("No Archiver found for the stream signature", e.getMessage());
        }
    }
}