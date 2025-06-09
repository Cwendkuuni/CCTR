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
    public void testConstructorWithMap() throws SAXException, ParserConfigurationException {
        assertNotNull(loader);
    }

    @Test
    public void testConstructorWithPublicIdAndResourceName() throws SAXException, ParserConfigurationException {
        AbstractLoader loaderWithPublicId = new AbstractLoader("-//Puppy Crawl//DTD Check Configuration 1.3//EN", "dtds/configuration_1_3.dtd") {};
        assertNotNull(loaderWithPublicId);
    }

    @Test
    public void testParseInputSource() throws IOException, SAXException {
        InputSource inputSource = mock(InputSource.class);
        loader.parseInputSource(inputSource);
        // No exception means success
    }

    @Test
    public void testResolveEntityWithKnownPublicId() throws IOException, SAXException {
        String publicId = "-//Puppy Crawl//DTD Check Configuration 1.3//EN";
        String systemId = "someSystemId";
        InputSource inputSource = loader.resolveEntity(publicId, systemId);
        assertNotNull(inputSource);
    }

    @Test
    public void testResolveEntityWithUnknownPublicId() throws IOException, SAXException {
        String publicId = "unknownPublicId";
        String systemId = "someSystemId";
        InputSource inputSource = loader.resolveEntity(publicId, systemId);
        assertNull(inputSource.getByteStream());
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