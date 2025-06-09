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

    private static final String PUBLIC_ID = "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN";
    private static final String DTD_RESOURCE_NAME = "dtds/configuration_1_3.dtd";

    private AbstractLoader loader;
    private Map<String, String> publicIdToResourceNameMap;

    @Before
    public void setUp() throws SAXException, ParserConfigurationException {
        publicIdToResourceNameMap = new HashMap<>();
        publicIdToResourceNameMap.put(PUBLIC_ID, DTD_RESOURCE_NAME);
        loader = new AbstractLoader(publicIdToResourceNameMap) {};
    }

    @Test
    public void testConstructorWithMap() throws SAXException, ParserConfigurationException {
        assertNotNull(loader);
        assertEquals(publicIdToResourceNameMap, loader.publicIdToResourceNameMap);
    }

    @Test
    public void testConstructorWithPublicIdAndResourceName() throws SAXException, ParserConfigurationException {
        AbstractLoader loaderWithParams = new AbstractLoader(PUBLIC_ID, DTD_RESOURCE_NAME) {};
        assertNotNull(loaderWithParams);
        assertEquals(DTD_RESOURCE_NAME, loaderWithParams.publicIdToResourceNameMap.get(PUBLIC_ID));
    }

    @Test
    public void testParseInputSource() throws IOException, SAXException {
        InputSource inputSource = mock(InputSource.class);
        loader.parseInputSource(inputSource);
        // No exception means success
    }

    @Test
    public void testResolveEntityWithKnownPublicId() throws IOException, SAXException {
        InputSource inputSource = loader.resolveEntity(PUBLIC_ID, null);
        assertNotNull(inputSource);
        assertNotNull(inputSource.getByteStream());
    }

    @Test
    public void testResolveEntityWithUnknownPublicId() throws IOException, SAXException {
        InputSource inputSource = loader.resolveEntity("unknown", null);
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