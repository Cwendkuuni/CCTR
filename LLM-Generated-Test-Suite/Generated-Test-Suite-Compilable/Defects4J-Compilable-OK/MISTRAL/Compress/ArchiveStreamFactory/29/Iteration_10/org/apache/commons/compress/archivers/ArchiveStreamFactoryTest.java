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
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
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
    public void testConstructorWithDefaultEncoding() {
        assertNull(factory.getEntryEncoding());
    }

    @Test
    public void testConstructorWithSpecifiedEncoding() {
        ArchiveStreamFactory factoryWithEncoding = new ArchiveStreamFactory("UTF-8");
        assertEquals("UTF-8", factoryWithEncoding.getEntryEncoding());
    }

    @Test(expected = IllegalStateException.class)
    public void testSetEntryEncodingWithConstructorEncoding() {
        ArchiveStreamFactory factoryWithEncoding = new ArchiveStreamFactory("UTF-8");
        factoryWithEncoding.setEntryEncoding("ISO-8859-1");
    }

    @Test
    public void testSetEntryEncoding() {
        factory.setEntryEncoding("UTF-8");
        assertEquals("UTF-8", factory.getEntryEncoding());
    }

    @Test
    public void testCreateArchiveInputStream() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);

        assertEquals(ArArchiveInputStream.class, factory.createArchiveInputStream(ArchiveStreamFactory.AR, in).getClass());
        assertEquals(ArjArchiveInputStream.class, factory.createArchiveInputStream(ArchiveStreamFactory.ARJ, in).getClass());
        assertEquals(ZipArchiveInputStream.class, factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, in).getClass());
        assertEquals(TarArchiveInputStream.class, factory.createArchiveInputStream(ArchiveStreamFactory.TAR, in).getClass());
        assertEquals(JarArchiveInputStream.class, factory.createArchiveInputStream(ArchiveStreamFactory.JAR, in).getClass());
        assertEquals(CpioArchiveInputStream.class, factory.createArchiveInputStream(ArchiveStreamFactory.CPIO, in).getClass());
        assertEquals(DumpArchiveInputStream.class, factory.createArchiveInputStream(ArchiveStreamFactory.DUMP, in).getClass());

        try {
            factory.createArchiveInputStream(ArchiveStreamFactory.SEVEN_Z, in);
            fail("Expected StreamingNotSupportedException");
        } catch (StreamingNotSupportedException e) {
            // expected
        }

        try {
            factory.createArchiveInputStream("unknown", in);
            fail("Expected ArchiveException");
        } catch (ArchiveException e) {
            // expected
        }
    }

    @Test
    public void testCreateArchiveOutputStream() throws Exception {
        OutputStream out = new ByteArrayOutputStream();

        assertEquals(ArArchiveOutputStream.class, factory.createArchiveOutputStream(ArchiveStreamFactory.AR, out).getClass());
        assertEquals(ZipArchiveOutputStream.class, factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, out).getClass());
        assertEquals(TarArchiveOutputStream.class, factory.createArchiveOutputStream(ArchiveStreamFactory.TAR, out).getClass());
        assertEquals(JarArchiveOutputStream.class, factory.createArchiveOutputStream(ArchiveStreamFactory.JAR, out).getClass());
        assertEquals(CpioArchiveOutputStream.class, factory.createArchiveOutputStream(ArchiveStreamFactory.CPIO, out).getClass());

        try {
            factory.createArchiveOutputStream(ArchiveStreamFactory.SEVEN_Z, out);
            fail("Expected StreamingNotSupportedException");
        } catch (StreamingNotSupportedException e) {
            // expected
        }

        try {
            factory.createArchiveOutputStream("unknown", out);
            fail("Expected ArchiveException");
        } catch (ArchiveException e) {
            // expected
        }
    }

    @Test
    public void testCreateArchiveInputStreamAutoDetect() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);

        try {
            factory.createArchiveInputStream(in);
            fail("Expected ArchiveException");
        } catch (ArchiveException e) {
            // expected
        }
    }

    @Test
    public void testCreateArchiveInputStreamAutoDetectWithMarkSupported() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        in.mark(0);

        try {
            factory.createArchiveInputStream(in);
            fail("Expected ArchiveException");
        } catch (ArchiveException e) {
            // expected
        }
    }
}