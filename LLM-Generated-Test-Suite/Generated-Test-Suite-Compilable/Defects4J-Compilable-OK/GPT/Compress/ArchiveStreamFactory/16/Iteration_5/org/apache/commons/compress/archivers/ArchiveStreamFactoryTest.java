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

    @Test
    public void testCreateArchiveInputStreamWithValidNames() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);

        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.AR, in) instanceof ArArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, in) instanceof ZipArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.TAR, in) instanceof TarArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.JAR, in) instanceof JarArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.CPIO, in) instanceof CpioArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.DUMP, in) instanceof DumpArchiveInputStream);
    }

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
    public void testCreateArchiveOutputStreamWithValidNames() throws Exception {
        OutputStream out = new ByteArrayOutputStream();

        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.AR, out) instanceof ArArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, out) instanceof ZipArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.TAR, out) instanceof TarArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.JAR, out) instanceof JarArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.CPIO, out) instanceof CpioArchiveOutputStream);
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
    public void testCreateArchiveInputStreamAutoDetect() throws Exception {
        byte[] zipSignature = new byte[] { 'P', 'K', 3, 4 };
        InputStream in = new ByteArrayInputStream(zipSignature);
        assertTrue(factory.createArchiveInputStream(in) instanceof ZipArchiveInputStream);

        byte[] jarSignature = new byte[] { 'P', 'K', 3, 4 };
        in = new ByteArrayInputStream(jarSignature);
        assertTrue(factory.createArchiveInputStream(in) instanceof JarArchiveInputStream);

        byte[] arSignature = new byte[] { '!', '<', 'a', 'r', 'c', 'h', '>' };
        in = new ByteArrayInputStream(arSignature);
        assertTrue(factory.createArchiveInputStream(in) instanceof ArArchiveInputStream);

        byte[] cpioSignature = new byte[] { '0', '7', '0', '7', '0', '7' };
        in = new ByteArrayInputStream(cpioSignature);
        assertTrue(factory.createArchiveInputStream(in) instanceof CpioArchiveInputStream);

        byte[] dumpSignature = new byte[32]; // Assuming a valid dump signature
        in = new ByteArrayInputStream(dumpSignature);
        assertTrue(factory.createArchiveInputStream(in) instanceof DumpArchiveInputStream);

        byte[] tarSignature = new byte[512]; // Assuming a valid tar signature
        in = new ByteArrayInputStream(tarSignature);
        assertTrue(factory.createArchiveInputStream(in) instanceof TarArchiveInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamAutoDetectWithNullStream() throws Exception {
        factory.createArchiveInputStream(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamAutoDetectWithNonMarkSupportedStream() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]) {
            @Override
            public boolean markSupported() {
                return false;
            }
        };
        factory.createArchiveInputStream(in);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveInputStreamAutoDetectWithUnknownSignature() throws Exception {
        byte[] unknownSignature = new byte[] { 0, 0, 0, 0 };
        InputStream in = new ByteArrayInputStream(unknownSignature);
        factory.createArchiveInputStream(in);
    }
}