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
    public void testCreateArchiveInputStreamWithKnownFormats() throws Exception {
        InputStream arInput = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.AR, arInput) instanceof ArArchiveInputStream);

        InputStream zipInput = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, zipInput) instanceof ZipArchiveInputStream);

        InputStream tarInput = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.TAR, tarInput) instanceof TarArchiveInputStream);

        InputStream jarInput = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.JAR, jarInput) instanceof JarArchiveInputStream);

        InputStream cpioInput = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.CPIO, cpioInput) instanceof CpioArchiveInputStream);

        InputStream dumpInput = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.DUMP, dumpInput) instanceof DumpArchiveInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullArchiverName() throws Exception {
        factory.createArchiveInputStream(null, new ByteArrayInputStream(new byte[0]));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullInputStream() throws Exception {
        factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, null);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveInputStreamWithUnknownFormat() throws Exception {
        factory.createArchiveInputStream("unknown", new ByteArrayInputStream(new byte[0]));
    }

    @Test
    public void testCreateArchiveOutputStreamWithKnownFormats() throws Exception {
        OutputStream arOutput = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.AR, arOutput) instanceof ArArchiveOutputStream);

        OutputStream zipOutput = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, zipOutput) instanceof ZipArchiveOutputStream);

        OutputStream tarOutput = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.TAR, tarOutput) instanceof TarArchiveOutputStream);

        OutputStream jarOutput = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.JAR, jarOutput) instanceof JarArchiveOutputStream);

        OutputStream cpioOutput = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.CPIO, cpioOutput) instanceof CpioArchiveOutputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveOutputStreamWithNullArchiverName() throws Exception {
        factory.createArchiveOutputStream(null, new ByteArrayOutputStream());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveOutputStreamWithNullOutputStream() throws Exception {
        factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, null);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveOutputStreamWithUnknownFormat() throws Exception {
        factory.createArchiveOutputStream("unknown", new ByteArrayOutputStream());
    }

    @Test
    public void testCreateArchiveInputStreamWithAutoDetection() throws Exception {
        InputStream zipInput = new ByteArrayInputStream(new byte[0]) {
            @Override
            public boolean markSupported() {
                return true;
            }
        };
        assertTrue(factory.createArchiveInputStream(zipInput) instanceof ZipArchiveInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullStreamForAutoDetection() throws Exception {
        factory.createArchiveInputStream((InputStream) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNonMarkSupportedStreamForAutoDetection() throws Exception {
        InputStream nonMarkSupportedInput = new ByteArrayInputStream(new byte[0]) {
            @Override
            public boolean markSupported() {
                return false;
            }
        };
        factory.createArchiveInputStream(nonMarkSupportedInput);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveInputStreamWithUnknownSignature() throws Exception {
        InputStream unknownInput = new ByteArrayInputStream(new byte[0]) {
            @Override
            public boolean markSupported() {
                return true;
            }
        };
        factory.createArchiveInputStream(unknownInput);
    }
}