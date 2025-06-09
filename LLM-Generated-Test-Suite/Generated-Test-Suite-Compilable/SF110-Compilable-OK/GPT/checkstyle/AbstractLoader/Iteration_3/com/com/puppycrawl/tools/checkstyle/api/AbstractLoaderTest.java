package com.com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.puppycrawl.tools.checkstyle.api.AbstractLoader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AbstractLoaderTest {

    private AbstractLoader loader;
    private Map<String, String> publicIdToResourceNameMap;

    @Before
    public void setUp() throws SAXException, ParserConfigurationException {
        publicIdToResourceNameMap = new HashMap<>();
        publicIdToResourceNameMap.put("-//Puppy Crawl//DTD Check Configuration 1.3//EN", "dtds/configuration_1_3.dtd");
        loader = new AbstractLoader(publicIdToResourceNameMap) {};
    }

    @Test
    public void testParseInputSource() throws IOException, SAXException {
        InputSource inputSource = mock(InputSource.class);
        loader.parseInputSource(inputSource);
        // Verify that the parser's parse method was called with the input source
        // Since parser is private, we cannot directly verify it. This test ensures no exceptions are thrown.
    }

    @Test
    public void testResolveEntityWithKnownPublicId() throws IOException, SAXException {
        String publicId = "-//Puppy Crawl//DTD Check Configuration 1.3//EN";
        String systemId = "http://checkstyle.sourceforge.net/dtds/configuration_1_3.dtd";

        InputSource result = loader.resolveEntity(publicId, systemId);

        assertNotNull("InputSource should not be null", result);
        assertNotNull("InputStream should not be null", result.getByteStream());
    }

    @Test
    public void testResolveEntityWithUnknownPublicId() throws IOException, SAXException {
        String publicId = "unknown";
        String systemId = "http://checkstyle.sourceforge.net/dtds/unknown.dtd";

        InputSource result = loader.resolveEntity(publicId, systemId);

        assertNull("InputSource should be null for unknown publicId", result.getByteStream());
    }

    @Test(expected = SAXException.class)
    public void testError() throws SAXException {
        SAXParseException exception = mock(SAXParseException.class);
        loader.error(exception);
    }

    @Test(expected = SAXException.class)
    public void testFatalError() throws SAXException {
        SAXParseException exception = mock(SAXParseException.class);
        loader.fatalError(exception);
    }
}