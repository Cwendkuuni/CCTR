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
    public void testCreateArchiveInputStreamWithKnownFormats() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();

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
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        factory.createArchiveInputStream(null, new ByteArrayInputStream(new byte[0]));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullInputStream() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, null);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveInputStreamWithUnknownFormat() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        factory.createArchiveInputStream("unknown", new ByteArrayInputStream(new byte[0]));
    }

    @Test
    public void testCreateArchiveOutputStreamWithKnownFormats() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();

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
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        factory.createArchiveOutputStream(null, new ByteArrayOutputStream());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveOutputStreamWithNullOutputStream() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, null);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveOutputStreamWithUnknownFormat() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        factory.createArchiveOutputStream("unknown", new ByteArrayOutputStream());
    }

    @Test
    public void testCreateArchiveInputStreamWithAutoDetection() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();

        // Test with a ZIP signature
        byte[] zipSignature = new byte[] { 'P', 'K', 3, 4 };
        InputStream zipInput = new ByteArrayInputStream(zipSignature);
        assertTrue(factory.createArchiveInputStream(zipInput) instanceof ZipArchiveInputStream);

        // Test with a JAR signature (same as ZIP)
        InputStream jarInput = new ByteArrayInputStream(zipSignature);
        assertTrue(factory.createArchiveInputStream(jarInput) instanceof JarArchiveInputStream);

        // Test with an AR signature
        byte[] arSignature = new byte[] { '!', '<', 'a', 'r', 'c', 'h', '>' };
        InputStream arInput = new ByteArrayInputStream(arSignature);
        assertTrue(factory.createArchiveInputStream(arInput) instanceof ArArchiveInputStream);

        // Test with a CPIO signature
        byte[] cpioSignature = new byte[] { '0', '7', '0', '7', '0', '7' };
        InputStream cpioInput = new ByteArrayInputStream(cpioSignature);
        assertTrue(factory.createArchiveInputStream(cpioInput) instanceof CpioArchiveInputStream);

        // Test with a DUMP signature
        byte[] dumpSignature = new byte[32]; // Assuming a valid dump signature
        InputStream dumpInput = new ByteArrayInputStream(dumpSignature);
        assertTrue(factory.createArchiveInputStream(dumpInput) instanceof DumpArchiveInputStream);

        // Test with a TAR signature
        byte[] tarSignature = new byte[512]; // Assuming a valid tar signature
        InputStream tarInput = new ByteArrayInputStream(tarSignature);
        assertTrue(factory.createArchiveInputStream(tarInput) instanceof TarArchiveInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullStreamForAutoDetection() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        factory.createArchiveInputStream((InputStream) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNonMarkSupportedStream() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        InputStream nonMarkSupportedStream = new ByteArrayInputStream(new byte[0]) {
            @Override
            public boolean markSupported() {
                return false;
            }
        };
        factory.createArchiveInputStream(nonMarkSupportedStream);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveInputStreamWithUnknownSignature() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        byte[] unknownSignature = new byte[] { 0x00, 0x01, 0x02, 0x03 };
        InputStream unknownInput = new ByteArrayInputStream(unknownSignature);
        factory.createArchiveInputStream(unknownInput);
    }
}