package org.apache.commons.compress.archivers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.StreamingNotSupportedException;
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
import org.junit.Before;
import org.junit.Test;

public class ArchiveStreamFactoryTest {

    private ArchiveStreamFactory factory;

    @Before
    public void setUp() {
        factory = new ArchiveStreamFactory();
    }

    @Test
    public void testCreateArchiveInputStreamWithValidArchiverName() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);

        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.AR, in) instanceof ArArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.ARJ, in) instanceof ArjArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, in) instanceof ZipArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.TAR, in) instanceof TarArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.JAR, in) instanceof JarArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.CPIO, in) instanceof CpioArchiveInputStream);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.DUMP, in) instanceof DumpArchiveInputStream);
    }

    @Test(expected = StreamingNotSupportedException.class)
    public void testCreateArchiveInputStreamWithSevenZ() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        factory.createArchiveInputStream(ArchiveStreamFactory.SEVEN_Z, in);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveInputStreamWithUnknownArchiverName() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        factory.createArchiveInputStream("unknown", in);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullArchiverName() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        factory.createArchiveInputStream(null, in);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullInputStream() throws Exception {
        factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, null);
    }

    @Test
    public void testCreateArchiveOutputStreamWithValidArchiverName() throws Exception {
        OutputStream out = new ByteArrayOutputStream();

        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.AR, out) instanceof ArArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, out) instanceof ZipArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.TAR, out) instanceof TarArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.JAR, out) instanceof JarArchiveOutputStream);
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.CPIO, out) instanceof CpioArchiveOutputStream);
    }

    @Test(expected = StreamingNotSupportedException.class)
    public void testCreateArchiveOutputStreamWithSevenZ() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        factory.createArchiveOutputStream(ArchiveStreamFactory.SEVEN_Z, out);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveOutputStreamWithUnknownArchiverName() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        factory.createArchiveOutputStream("unknown", out);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveOutputStreamWithNullArchiverName() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        factory.createArchiveOutputStream(null, out);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveOutputStreamWithNullOutputStream() throws Exception {
        factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, null);
    }

    @Test
    public void testGetEntryEncoding() {
        assertNull(factory.getEntryEncoding());

        ArchiveStreamFactory factoryWithEncoding = new ArchiveStreamFactory("UTF-8");
        assertEquals("UTF-8", factoryWithEncoding.getEntryEncoding());
    }

    @Test(expected = IllegalStateException.class)
    public void testSetEntryEncodingThrowsExceptionWhenEncodingIsSetInConstructor() {
        ArchiveStreamFactory factoryWithEncoding = new ArchiveStreamFactory("UTF-8");
        factoryWithEncoding.setEntryEncoding("ISO-8859-1");
    }

    @Test
    public void testSetEntryEncoding() {
        factory.setEntryEncoding("ISO-8859-1");
        assertEquals("ISO-8859-1", factory.getEntryEncoding());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullStream() throws Exception {
        factory.createArchiveInputStream((InputStream) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNonMarkSupportedStream() throws Exception {
        InputStream in = mock(InputStream.class);
        when(in.markSupported()).thenReturn(false);
        factory.createArchiveInputStream(in);
    }

    @Test
    public void testCreateArchiveInputStreamWithAutoDetection() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[512]);
        assertTrue(factory.createArchiveInputStream(in) instanceof TarArchiveInputStream);
    }
}