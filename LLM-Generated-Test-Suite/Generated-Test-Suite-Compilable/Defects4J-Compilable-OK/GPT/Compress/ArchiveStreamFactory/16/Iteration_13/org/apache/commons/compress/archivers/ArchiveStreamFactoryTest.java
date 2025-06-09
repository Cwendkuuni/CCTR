package org.apache.commons.compress.archivers;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
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
import org.junit.Test;

public class ArchiveStreamFactoryTest {

    private final ArchiveStreamFactory factory = new ArchiveStreamFactory();

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullName() throws Exception {
        factory.createArchiveInputStream(null, new ByteArrayInputStream(new byte[0]));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullStream() throws Exception {
        factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, null);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveInputStreamWithUnknownName() throws Exception {
        factory.createArchiveInputStream("unknown", new ByteArrayInputStream(new byte[0]));
    }

    @Test
    public void testCreateArchiveInputStreamWithKnownNames() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);

        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.AR, in) instanceof ArArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, in) instanceof ZipArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.TAR, in) instanceof TarArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.JAR, in) instanceof JarArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.CPIO, in) instanceof CpioArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.DUMP, in) instanceof DumpArchiveInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveOutputStreamWithNullName() throws Exception {
        factory.createArchiveOutputStream(null, new ByteArrayOutputStream());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveOutputStreamWithNullStream() throws Exception {
        factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, null);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveOutputStreamWithUnknownName() throws Exception {
        factory.createArchiveOutputStream("unknown", new ByteArrayOutputStream());
    }

    @Test
    public void testCreateArchiveOutputStreamWithKnownNames() throws Exception {
        OutputStream out = new ByteArrayOutputStream();

        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.AR, out) instanceof ArArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, out) instanceof ZipArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.TAR, out) instanceof TarArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.JAR, out) instanceof JarArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.CPIO, out) instanceof CpioArchiveOutputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullStreamAutodetect() throws Exception {
        factory.createArchiveInputStream((InputStream) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNonMarkSupportedStream() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]) {
            @Override
            public boolean markSupported() {
                return false;
            }
        };
        factory.createArchiveInputStream(in);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveInputStreamWithUnknownSignature() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]) {
            @Override
            public boolean markSupported() {
                return true;
            }
        };
        factory.createArchiveInputStream(in);
    }

    @Test
    public void testCreateArchiveInputStreamWithKnownSignatures() throws Exception {
        // Mock input streams with known signatures
        InputStream zipIn = new ByteArrayInputStream(new byte[]{0x50, 0x4B, 0x03, 0x04}) {
            @Override
            public boolean markSupported() {
                return true;
            }
        };
        assertTrue(factory.createArchiveInputStream(zipIn) instanceof ZipArchiveInputStream);

        InputStream jarIn = new ByteArrayInputStream(new byte[]{0x50, 0x4B, 0x03, 0x04}) {
            @Override
            public boolean markSupported() {
                return true;
            }
        };
        assertTrue(factory.createArchiveInputStream(jarIn) instanceof JarArchiveInputStream);

        InputStream arIn = new ByteArrayInputStream(new byte[]{0x21, 0x3C, 0x61, 0x72, 0x63, 0x68, 0x3E, 0x0A}) {
            @Override
            public boolean markSupported() {
                return true;
            }
        };
        assertTrue(factory.createArchiveInputStream(arIn) instanceof ArArchiveInputStream);

        InputStream cpioIn = new ByteArrayInputStream(new byte[]{0x30, 0x37, 0x30, 0x37, 0x30, 0x37, 0x30, 0x37}) {
            @Override
            public boolean markSupported() {
                return true;
            }
        };
        assertTrue(factory.createArchiveInputStream(cpioIn) instanceof CpioArchiveInputStream);

        InputStream dumpIn = new ByteArrayInputStream(new byte[32]) {
            @Override
            public boolean markSupported() {
                return true;
            }
        };
        assertTrue(factory.createArchiveInputStream(dumpIn) instanceof DumpArchiveInputStream);

        InputStream tarIn = new ByteArrayInputStream(new byte[512]) {
            @Override
            public boolean markSupported() {
                return true;
            }
        };
        assertTrue(factory.createArchiveInputStream(tarIn) instanceof TarArchiveInputStream);
    }
}