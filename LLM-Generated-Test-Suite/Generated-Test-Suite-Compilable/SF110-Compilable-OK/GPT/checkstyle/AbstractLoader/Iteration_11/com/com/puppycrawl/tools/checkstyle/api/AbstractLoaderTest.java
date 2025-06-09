package com.com.puppycrawl.tools.checkstyle.api;

import com.puppycrawl.tools.checkstyle.api.AbstractLoader;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AbstractLoaderTest {

    private static class TestLoader extends AbstractLoader {
        protected TestLoader(String publicId, String dtdResourceName) throws SAXException, ParserConfigurationException {
            super(publicId, dtdResourceName);
        }

        protected TestLoader(Map<String, String> publicIdToResourceNameMap) throws SAXException, ParserConfigurationException {
            super(publicIdToResourceNameMap);
        }
    }

    private TestLoader loader;
    private Map<String, String> publicIdToResourceNameMap;

    @Before
    public void setUp() throws Exception {
        publicIdToResourceNameMap = new HashMap<>();
        publicIdToResourceNameMap.put("publicId", "dtdResourceName");
        loader = new TestLoader(publicIdToResourceNameMap);
    }

    @Test
    public void testConstructorWithMap() throws Exception {
        assertNotNull(loader);
    }

    @Test
    public void testConstructorWithPublicIdAndResourceName() throws Exception {
        TestLoader loaderWithPublicId = new TestLoader("publicId", "dtdResourceName");
        assertNotNull(loaderWithPublicId);
    }

    @Test
    public void testParseInputSource() throws Exception {
        InputSource inputSource = mock(InputSource.class);
        try {
            loader.parseInputSource(inputSource);
        } catch (SAXException | IOException e) {
            fail("Parsing should not throw an exception: " + e.getMessage());
        }
    }

    @Test
    public void testResolveEntity() throws Exception {
        InputSource result = loader.resolveEntity("publicId", "systemId");
        assertNotNull(result);
        assertTrue(result.getByteStream() instanceof InputStream);
    }

    @Test
    public void testResolveEntityWithUnknownPublicId() throws Exception {
        InputSource result = loader.resolveEntity("unknownPublicId", "systemId");
        assertNull(result.getByteStream());
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