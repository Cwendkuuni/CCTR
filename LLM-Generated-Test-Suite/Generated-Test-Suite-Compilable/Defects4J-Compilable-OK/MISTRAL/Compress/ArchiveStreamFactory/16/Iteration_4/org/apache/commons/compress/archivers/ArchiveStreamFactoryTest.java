package org.apache.commons.compress.archivers;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    public void testCreateArchiveInputStream() throws ArchiveException {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();

        InputStream in = new ByteArrayInputStream(new byte[0]);

        assertNotNull(factory.createArchiveInputStream(ArchiveStreamFactory.AR, in));
        assertNotNull(factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, in));
        assertNotNull(factory.createArchiveInputStream(ArchiveStreamFactory.TAR, in));
        assertNotNull(factory.createArchiveInputStream(ArchiveStreamFactory.JAR, in));
        assertNotNull(factory.createArchiveInputStream(ArchiveStreamFactory.CPIO, in));
        assertNotNull(factory.createArchiveInputStream(ArchiveStreamFactory.DUMP, in));

        try {
            factory.createArchiveInputStream(null, in);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Archivername must not be null.", e.getMessage());
        }

        try {
            factory.createArchiveInputStream("unknown", in);
            fail("Expected ArchiveException");
        } catch (ArchiveException e) {
            assertEquals("Archiver: unknown not found.", e.getMessage());
        }

        try {
            factory.createArchiveInputStream(ArchiveStreamFactory.AR, null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("InputStream must not be null.", e.getMessage());
        }
    }

    @Test
    public void testCreateArchiveOutputStream() throws ArchiveException {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();

        OutputStream out = new ByteArrayOutputStream();

        assertNotNull(factory.createArchiveOutputStream(ArchiveStreamFactory.AR, out));
        assertNotNull(factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, out));
        assertNotNull(factory.createArchiveOutputStream(ArchiveStreamFactory.TAR, out));
        assertNotNull(factory.createArchiveOutputStream(ArchiveStreamFactory.JAR, out));
        assertNotNull(factory.createArchiveOutputStream(ArchiveStreamFactory.CPIO, out));

        try {
            factory.createArchiveOutputStream(null, out);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Archivername must not be null.", e.getMessage());
        }

        try {
            factory.createArchiveOutputStream("unknown", out);
            fail("Expected ArchiveException");
        } catch (ArchiveException e) {
            assertEquals("Archiver: unknown not found.", e.getMessage());
        }

        try {
            factory.createArchiveOutputStream(ArchiveStreamFactory.AR, null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("OutputStream must not be null.", e.getMessage());
        }
    }

    @Test
    public void testCreateArchiveInputStreamAutoDetect() throws ArchiveException, IOException {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();

        // Test with ZIP
        ByteArrayOutputStream zipOut = new ByteArrayOutputStream();
        ZipArchiveOutputStream zipArchiveOut = new ZipArchiveOutputStream(zipOut);
        zipArchiveOut.close();
        InputStream zipIn = new ByteArrayInputStream(zipOut.toByteArray());
        assertTrue(factory.createArchiveInputStream(zipIn) instanceof ZipArchiveInputStream);

        // Test with JAR
        ByteArrayOutputStream jarOut = new ByteArrayOutputStream();
        JarArchiveOutputStream jarArchiveOut = new JarArchiveOutputStream(jarOut);
        jarArchiveOut.close();
        InputStream jarIn = new ByteArrayInputStream(jarOut.toByteArray());
        assertTrue(factory.createArchiveInputStream(jarIn) instanceof JarArchiveInputStream);

        // Test with AR
        ByteArrayOutputStream arOut = new ByteArrayOutputStream();
        ArArchiveOutputStream arArchiveOut = new ArArchiveOutputStream(arOut);
        arArchiveOut.close();
        InputStream arIn = new ByteArrayInputStream(arOut.toByteArray());
        assertTrue(factory.createArchiveInputStream(arIn) instanceof ArArchiveInputStream);

        // Test with CPIO
        ByteArrayOutputStream cpioOut = new ByteArrayOutputStream();
        CpioArchiveOutputStream cpioArchiveOut = new CpioArchiveOutputStream(cpioOut);
        cpioArchiveOut.close();
        InputStream cpioIn = new ByteArrayInputStream(cpioOut.toByteArray());
        assertTrue(factory.createArchiveInputStream(cpioIn) instanceof CpioArchiveInputStream);

        // Test with DUMP
        ByteArrayOutputStream dumpOut = new ByteArrayOutputStream();
        // DumpArchiveOutputStream does not exist, so we simulate the signature
        byte[] dumpSignature = new byte[32];
        dumpOut.write(dumpSignature);
        InputStream dumpIn = new ByteArrayInputStream(dumpOut.toByteArray());
        assertTrue(factory.createArchiveInputStream(dumpIn) instanceof DumpArchiveInputStream);

        // Test with TAR
        ByteArrayOutputStream tarOut = new ByteArrayOutputStream();
        TarArchiveOutputStream tarArchiveOut = new TarArchiveOutputStream(tarOut);
        tarArchiveOut.close();
        InputStream tarIn = new ByteArrayInputStream(tarOut.toByteArray());
        assertTrue(factory.createArchiveInputStream(tarIn) instanceof TarArchiveInputStream);

        try {
            factory.createArchiveInputStream(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Stream must not be null.", e.getMessage());
        }

        try {
            InputStream noMarkIn = new ByteArrayInputStream(new byte[0]);
            factory.createArchiveInputStream(noMarkIn);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Mark is not supported.", e.getMessage());
        }
    }
}