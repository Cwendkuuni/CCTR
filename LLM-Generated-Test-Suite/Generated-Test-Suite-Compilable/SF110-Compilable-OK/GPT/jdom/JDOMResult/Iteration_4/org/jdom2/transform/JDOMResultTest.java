package org.jdom2.transform;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMFactory;
import org.jdom2.DefaultJDOMFactory;
import org.jdom2.transform.JDOMResult;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JDOMResultTest {

    private JDOMResult jdomResult;

    @Before
    public void setUp() {
        jdomResult = new JDOMResult();
    }

    @Test
    public void testSetAndGetResult() {
        List<Content> contentList = new ArrayList<>();
        contentList.add(new Element("testElement"));

        jdomResult.setResult(contentList);
        List<Content> result = jdomResult.getResult();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testElement", ((Element) result.get(0)).getName());
    }

    @Test
    public void testSetAndGetDocument() {
        Document document = new Document();
        Element rootElement = new Element("root");
        document.setRootElement(rootElement);

        jdomResult.setDocument(document);
        Document resultDoc = jdomResult.getDocument();

        assertNotNull(resultDoc);
        assertEquals("root", resultDoc.getRootElement().getName());
    }

    @Test
    public void testSetAndGetFactory() {
        JDOMFactory factory = new DefaultJDOMFactory();
        jdomResult.setFactory(factory);

        JDOMFactory resultFactory = jdomResult.getFactory();
        assertNotNull(resultFactory);
        assertEquals(factory, resultFactory);
    }

    @Test
    public void testGetResultWithNoSet() {
        List<Content> result = jdomResult.getResult();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetDocumentWithNoSet() {
        Document resultDoc = jdomResult.getDocument();
        assertNull(resultDoc);
    }

    @Test
    public void testSetHandlerDoesNotThrow() {
        try {
            jdomResult.setHandler(null);
        } catch (Exception e) {
            fail("setHandler should not throw an exception");
        }
    }

    @Test
    public void testSetLexicalHandlerDoesNotThrow() {
        try {
            jdomResult.setLexicalHandler(null);
        } catch (Exception e) {
            fail("setLexicalHandler should not throw an exception");
        }
    }
}