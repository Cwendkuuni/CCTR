package org.apache.commons.compress.archivers;

import org.apache.commons.compress.archivers.ar.ArArchiveInputStream;
import org.apache.commons.compress.archivers.ar.ArArchiveOutputStream;
import org.apache.commons.compress.archivers.cpio.CpioArchiveInputStream;
import org.apache.commons.compress.archivers.cpio.CpioArchiveOutputStream;
import org.apache.commons.compress.archivers.dump.DumpArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class ArchiveStreamFactoryTest {

    private ArchiveStreamFactory factory;

    @Before
    public void setUp() {
        factory = new ArchiveStreamFactory();
    }

    @Test
    public void testCreateArchiveInputStream_AR() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        ArchiveInputStream ais = factory.createArchiveInputStream(ArchiveStreamFactory.AR, in);
        assertTrue(ais instanceof ArArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStream_ZIP() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        ArchiveInputStream ais = factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, in);
        assertTrue(ais instanceof ZipArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStream_TAR() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        ArchiveInputStream ais = factory.createArchiveInputStream(ArchiveStreamFactory.TAR, in);
        assertTrue(ais instanceof TarArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStream_JAR() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        ArchiveInputStream ais = factory.createArchiveInputStream(ArchiveStreamFactory.JAR, in);
        assertTrue(ais instanceof JarArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStream_CPIO() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        ArchiveInputStream ais = factory.createArchiveInputStream(ArchiveStreamFactory.CPIO, in);
        assertTrue(ais instanceof CpioArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStream_DUMP() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        ArchiveInputStream ais = factory.createArchiveInputStream(ArchiveStreamFactory.DUMP, in);
        assertTrue(ais instanceof DumpArchiveInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStream_NullArchiverName() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        factory.createArchiveInputStream(null, in);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStream_NullInputStream() throws Exception {
        factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, null);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveInputStream_UnknownArchiver() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        factory.createArchiveInputStream("unknown", in);
    }

    @Test
    public void testCreateArchiveOutputStream_AR() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        ArchiveOutputStream aos = factory.createArchiveOutputStream(ArchiveStreamFactory.AR, out);
        assertTrue(aos instanceof ArArchiveOutputStream);
    }

    @Test
    public void testCreateArchiveOutputStream_ZIP() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        ArchiveOutputStream aos = factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, out);
        assertTrue(aos instanceof ZipArchiveOutputStream);
    }

    @Test
    public void testCreateArchiveOutputStream_TAR() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        ArchiveOutputStream aos = factory.createArchiveOutputStream(ArchiveStreamFactory.TAR, out);
        assertTrue(aos instanceof TarArchiveOutputStream);
    }

    @Test
    public void testCreateArchiveOutputStream_JAR() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        ArchiveOutputStream aos = factory.createArchiveOutputStream(ArchiveStreamFactory.JAR, out);
        assertTrue(aos instanceof JarArchiveOutputStream);
    }

    @Test
    public void testCreateArchiveOutputStream_CPIO() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        ArchiveOutputStream aos = factory.createArchiveOutputStream(ArchiveStreamFactory.CPIO, out);
        assertTrue(aos instanceof CpioArchiveOutputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveOutputStream_NullArchiverName() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        factory.createArchiveOutputStream(null, out);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveOutputStream_NullOutputStream() throws Exception {
        factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, null);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveOutputStream_UnknownArchiver() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        factory.createArchiveOutputStream("unknown", out);
    }

    @Test
    public void testCreateArchiveInputStream_AutoDetect_ZIP() throws Exception {
        byte[] zipSignature = {0x50, 0x4B, 0x03, 0x04}; // ZIP signature
        InputStream in = new ByteArrayInputStream(zipSignature);
        ArchiveInputStream ais = factory.createArchiveInputStream(in);
        assertTrue(ais instanceof ZipArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStream_AutoDetect_JAR() throws Exception {
        byte[] jarSignature = {0x50, 0x4B, 0x03, 0x04}; // JAR signature (same as ZIP)
        InputStream in = new ByteArrayInputStream(jarSignature);
        ArchiveInputStream ais = factory.createArchiveInputStream(in);
        assertTrue(ais instanceof JarArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStream_AutoDetect_AR() throws Exception {
        byte[] arSignature = {0x21, 0x3C, 0x61, 0x72, 0x63, 0x68, 0x3E, 0x0A}; // AR signature
        InputStream in = new ByteArrayInputStream(arSignature);
        ArchiveInputStream ais = factory.createArchiveInputStream(in);
        assertTrue(ais instanceof ArArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStream_AutoDetect_CPIO() throws Exception {
        byte[] cpioSignature = {0x30, 0x37, 0x30, 0x37, 0x30, 0x31}; // CPIO signature
        InputStream in = new ByteArrayInputStream(cpioSignature);
        ArchiveInputStream ais = factory.createArchiveInputStream(in);
        assertTrue(ais instanceof CpioArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStream_AutoDetect_DUMP() throws Exception {
        byte[] dumpSignature = {0x30, 0x37, 0x30, 0x37, 0x30, 0x31}; // DUMP signature
        InputStream in = new ByteArrayInputStream(dumpSignature);
        ArchiveInputStream ais = factory.createArchiveInputStream(in);
        assertTrue(ais instanceof DumpArchiveInputStream);
    }

    @Test
    public void testCreateArchiveInputStream_AutoDetect_TAR() throws Exception {
        byte[] tarSignature = new byte[512]; // TAR signature (empty block)
        InputStream in = new ByteArrayInputStream(tarSignature);
        ArchiveInputStream ais = factory.createArchiveInputStream(in);
        assertTrue(ais instanceof TarArchiveInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStream_AutoDetect_NullInputStream() throws Exception {
        factory.createArchiveInputStream(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStream_AutoDetect_NoMarkSupport() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]) {
            @Override
            public boolean markSupported() {
                return false;
            }
        };
        factory.createArchiveInputStream(in);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveInputStream_AutoDetect_UnknownSignature() throws Exception {
        byte[] unknownSignature = {0x00, 0x00, 0x00, 0x00}; // Unknown signature
        InputStream in = new ByteArrayInputStream(unknownSignature);
        factory.createArchiveInputStream(in);
    }
}