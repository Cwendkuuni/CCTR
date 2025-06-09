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
    private JDOMFactory factory;

    @Before
    public void setUp() {
        jdomResult = new JDOMResult();
        factory = new DefaultJDOMFactory();
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
        Document document = new Document(new Element("root"));
        jdomResult.setDocument(document);

        Document resultDoc = jdomResult.getDocument();
        assertNotNull(resultDoc);
        assertEquals("root", resultDoc.getRootElement().getName());
    }

    @Test
    public void testSetAndGetFactory() {
        jdomResult.setFactory(factory);
        JDOMFactory resultFactory = jdomResult.getFactory();

        assertNotNull(resultFactory);
        assertEquals(factory, resultFactory);
    }

    @Test
    public void testGetResultWhenDocumentIsSet() {
        Document document = new Document(new Element("root"));
        jdomResult.setDocument(document);

        List<Content> result = jdomResult.getResult();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("root", ((Element) result.get(0)).getName());
    }

    @Test
    public void testGetDocumentWhenResultIsSet() {
        List<Content> contentList = new ArrayList<>();
        contentList.add(new Element("testElement"));

        jdomResult.setResult(contentList);
        Document resultDoc = jdomResult.getDocument();

        assertNotNull(resultDoc);
        assertEquals(1, resultDoc.getContentSize());
        assertEquals("testElement", ((Element) resultDoc.getContent(0)).getName());
    }

    @Test
    public void testGetDocumentWithNullFactory() {
        List<Content> contentList = new ArrayList<>();
        contentList.add(new Element("testElement"));

        jdomResult.setResult(contentList);
        jdomResult.setFactory(null); // Ensure factory is null

        Document resultDoc = jdomResult.getDocument();
        assertNotNull(resultDoc);
        assertEquals(1, resultDoc.getContentSize());
        assertEquals("testElement", ((Element) resultDoc.getContent(0)).getName());
    }

    @Test
    public void testRetrieveResultWithNoInitialData() {
        List<Content> result = jdomResult.getResult();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}