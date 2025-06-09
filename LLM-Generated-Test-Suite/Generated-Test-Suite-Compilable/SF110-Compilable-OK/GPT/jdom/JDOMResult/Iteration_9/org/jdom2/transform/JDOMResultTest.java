package org.jdom2.transform;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMFactory;
import org.jdom2.DefaultJDOMFactory;
import org.jdom2.transform.JDOMResult;

import java.util.ArrayList;
import java.util.List;

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
        document.addContent(new Element("testElement"));
        jdomResult.setDocument(document);

        Document resultDoc = jdomResult.getDocument();
        assertNotNull(resultDoc);
        assertEquals(1, resultDoc.getContentSize());
        assertEquals("testElement", ((Element) resultDoc.getContent(0)).getName());
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
    public void testGetResultWhenNoResultSet() {
        List<Content> result = jdomResult.getResult();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetDocumentWhenNoDocumentSet() {
        Document resultDoc = jdomResult.getDocument();
        assertNull(resultDoc);
    }

    @Test
    public void testGetResultAfterSettingDocument() {
        Document document = new Document();
        document.addContent(new Element("testElement"));
        jdomResult.setDocument(document);

        List<Content> result = jdomResult.getResult();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testElement", ((Element) result.get(0)).getName());
    }

    @Test
    public void testGetDocumentAfterSettingResult() {
        List<Content> contentList = new ArrayList<>();
        contentList.add(new Element("testElement"));
        jdomResult.setResult(contentList);

        Document resultDoc = jdomResult.getDocument();
        assertNotNull(resultDoc);
        assertEquals(1, resultDoc.getContentSize());
        assertEquals("testElement", ((Element) resultDoc.getContent(0)).getName());
    }
}