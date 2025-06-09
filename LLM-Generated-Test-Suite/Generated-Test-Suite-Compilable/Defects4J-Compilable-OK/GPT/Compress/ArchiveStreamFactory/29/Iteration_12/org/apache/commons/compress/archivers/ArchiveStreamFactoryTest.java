package org.apache.commons.compress.archivers;

import static org.junit.Assert.*;
import org.apache.commons.compress.archivers.*;
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
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ArchiveStreamFactoryTest {

    @Test
    public void testCreateArchiveInputStreamWithValidArchiverNames() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();

        InputStream arInputStream = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.AR, arInputStream) instanceof ArArchiveInputStream);

        InputStream arjInputStream = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.ARJ, arjInputStream) instanceof ArjArchiveInputStream);

        InputStream zipInputStream = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, zipInputStream) instanceof ZipArchiveInputStream);

        InputStream tarInputStream = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.TAR, tarInputStream) instanceof TarArchiveInputStream);

        InputStream jarInputStream = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.JAR, jarInputStream) instanceof JarArchiveInputStream);

        InputStream cpioInputStream = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.CPIO, cpioInputStream) instanceof CpioArchiveInputStream);

        InputStream dumpInputStream = new ByteArrayInputStream(new byte[0]);
        assertTrue(factory.createArchiveInputStream(ArchiveStreamFactory.DUMP, dumpInputStream) instanceof DumpArchiveInputStream);
    }

    @Test(expected = StreamingNotSupportedException.class)
    public void testCreateArchiveInputStreamWithSevenZ() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        InputStream sevenZInputStream = new ByteArrayInputStream(new byte[0]);
        factory.createArchiveInputStream(ArchiveStreamFactory.SEVEN_Z, sevenZInputStream);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveInputStreamWithUnknownArchiverName() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        InputStream unknownInputStream = new ByteArrayInputStream(new byte[0]);
        factory.createArchiveInputStream("unknown", unknownInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullArchiverName() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        InputStream nullInputStream = new ByteArrayInputStream(new byte[0]);
        factory.createArchiveInputStream(null, nullInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamWithNullInputStream() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        factory.createArchiveInputStream(ArchiveStreamFactory.ZIP, null);
    }

    @Test
    public void testCreateArchiveOutputStreamWithValidArchiverNames() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();

        OutputStream arOutputStream = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.AR, arOutputStream) instanceof ArArchiveOutputStream);

        OutputStream zipOutputStream = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, zipOutputStream) instanceof ZipArchiveOutputStream);

        OutputStream tarOutputStream = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.TAR, tarOutputStream) instanceof TarArchiveOutputStream);

        OutputStream jarOutputStream = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.JAR, jarOutputStream) instanceof JarArchiveOutputStream);

        OutputStream cpioOutputStream = new ByteArrayOutputStream();
        assertTrue(factory.createArchiveOutputStream(ArchiveStreamFactory.CPIO, cpioOutputStream) instanceof CpioArchiveOutputStream);
    }

    @Test(expected = StreamingNotSupportedException.class)
    public void testCreateArchiveOutputStreamWithSevenZ() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        OutputStream sevenZOutputStream = new ByteArrayOutputStream();
        factory.createArchiveOutputStream(ArchiveStreamFactory.SEVEN_Z, sevenZOutputStream);
    }

    @Test(expected = ArchiveException.class)
    public void testCreateArchiveOutputStreamWithUnknownArchiverName() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        OutputStream unknownOutputStream = new ByteArrayOutputStream();
        factory.createArchiveOutputStream("unknown", unknownOutputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveOutputStreamWithNullArchiverName() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        OutputStream nullOutputStream = new ByteArrayOutputStream();
        factory.createArchiveOutputStream(null, nullOutputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveOutputStreamWithNullOutputStream() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        factory.createArchiveOutputStream(ArchiveStreamFactory.ZIP, null);
    }

    @Test
    public void testGetEntryEncoding() {
        ArchiveStreamFactory factory = new ArchiveStreamFactory("UTF-8");
        assertEquals("UTF-8", factory.getEntryEncoding());
    }

    @Test(expected = IllegalStateException.class)
    public void testSetEntryEncodingThrowsException() {
        ArchiveStreamFactory factory = new ArchiveStreamFactory("UTF-8");
        factory.setEntryEncoding("ISO-8859-1");
    }

    @Test
    public void testSetEntryEncoding() {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        factory.setEntryEncoding("ISO-8859-1");
        assertEquals("ISO-8859-1", factory.getEntryEncoding());
    }

    @Test
    public void testCreateArchiveInputStreamAutodetect() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        byte[] zipSignature = new byte[]{0x50, 0x4B, 0x03, 0x04}; // PKZIP signature
        InputStream zipInputStream = new ByteArrayInputStream(zipSignature);
        assertTrue(factory.createArchiveInputStream(zipInputStream) instanceof ZipArchiveInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamAutodetectWithNullStream() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        factory.createArchiveInputStream(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateArchiveInputStreamAutodetectWithNonMarkSupportedStream() throws Exception {
        ArchiveStreamFactory factory = new ArchiveStreamFactory();
        InputStream nonMarkSupportedStream = new ByteArrayInputStream(new byte[0]) {
            @Override
            public boolean markSupported() {
                return false;
            }
        };
        factory.createArchiveInputStream(nonMarkSupportedStream);
    }
}