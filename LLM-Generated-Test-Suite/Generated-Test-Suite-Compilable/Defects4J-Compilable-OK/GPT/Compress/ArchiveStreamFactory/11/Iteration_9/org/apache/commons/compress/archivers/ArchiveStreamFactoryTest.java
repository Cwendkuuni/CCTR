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

    @Test
    public void testCreateArchiveInputStreamWithValidArchiverNames() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        InputStream dummyInputStream = new ByteArrayInputStream(new byte[0]);

        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.AR, dummyInputStream) instanceof ArArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, dummyInputStream) instanceof ZipArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.TAR, dummyInputStream) instanceof TarArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.JAR, dummyInputStream) instanceof JarArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.CPIO, dummyInputStream) instanceof CpioArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.DUMP, dummyInputStream) instanceof DumpArchiveInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullArchiverName() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        InputStream dummyInputStream = new ByteArrayInputStream(new byte[0]);
        factory.createArchiveInputStream(null, dummyInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullInputStream() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, null);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveInputStreamWithUnknownArchiverName() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        InputStream dummyInputStream = new ByteArrayInputStream(new byte[0]);
        factory.createArchiveInputStream("unknown", dummyInputStream);
    }

    @Test
    public void testCreateArchiveOutputStreamWithValidArchiverNames() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        OutputStream dummyOutputStream = new ByteArrayOutputStream();

        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.AR, dummyOutputStream) instanceof ArArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, dummyOutputStream) instanceof ZipArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.TAR, dummyOutputStream) instanceof TarArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.JAR, dummyOutputStream) instanceof JarArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.CPIO, dummyOutputStream) instanceof CpioArchiveOutputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveOutputStreamWithNullArchiverName() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        OutputStream dummyOutputStream = new ByteArrayOutputStream();
        factory.createArchiveOutputStream(null, dummyOutputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveOutputStreamWithNullOutputStream() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, null);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveOutputStreamWithUnknownArchiverName() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        OutputStream dummyOutputStream = new ByteArrayOutputStream();
        factory.createArchiveOutputStream("unknown", dummyOutputStream);
    }

    @Test
    public void testCreateArchiveInputStreamWithAutoDetection() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        byte[] zipSignature = new byte[] { 'P', 'K', 3, 4 }; // ZIP file signature
        InputStream zipInputStream = new ByteArrayInputStream(zipSignature);
        assertTrue(factory.createArchiveInputStream(zipInputStream) instanceof ZipArchiveInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullStreamForAutoDetection() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        factory.createArchiveInputStream((InputStream) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNonMarkSupportedStreamForAutoDetection() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        InputStream nonMarkSupportedStream = new InputStream() {
            @Override
            public int read() {
                return -1;
            }
        };
        factory.createArchiveInputStream(nonMarkSupportedStream);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveInputStreamWithUnknownSignatureForAutoDetection() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        byte[] unknownSignature = new byte[] { 0, 1, 2, 3 };
        InputStream unknownInputStream = new ByteArrayInputStream(unknownSignature);
        factory.createArchiveInputStream(unknownInputStream);
    }
}