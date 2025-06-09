package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ParserTest {

    @Mock
    private TreeBuilder mockTreeBuilder;

    @Mock
    private ParseSettings mockSettings;

    private Parser parser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        parser = new Parser(mockTreeBuilder);
    }

    @Test
    public void testParseInput() {
        String html = "<html><body>Test</body></html>";
        String baseUri = "http://example.com";
        Document mockDocument = mock(Document.class);

        when(mockTreeBuilder.parse(eq(html), eq(baseUri), any(ParseErrorList.class), eq(mockSettings))).thenReturn(mockDocument);
        when(mockTreeBuilder.defaultSettings()).thenReturn(mockSettings);

        Document result = parser.parseInput(html, baseUri);

        assertNotNull(result);
        assertEquals(mockDocument, result);
    }

    @Test
    public void testGetTreeBuilder() {
        assertEquals(mockTreeBuilder, parser.getTreeBuilder());
    }

    @Test
    public void testSetTreeBuilder() {
        TreeBuilder newTreeBuilder = mock(TreeBuilder.class);
        parser.setTreeBuilder(newTreeBuilder);
        assertEquals(newTreeBuilder, parser.getTreeBuilder());
    }

    @Test
    public void testIsTrackErrors() {
        assertFalse(parser.isTrackErrors());
        parser.setTrackErrors(5);
        assertTrue(parser.isTrackErrors());
    }

    @Test
    public void testSetTrackErrors() {
        parser.setTrackErrors(5);
        assertTrue(parser.isTrackErrors());
        parser.setTrackErrors(0);
        assertFalse(parser.isTrackErrors());
    }

    @Test
    public void testGetErrors() {
        parser.setTrackErrors(5);
        parser.parseInput("<html><body>Test</body></html>", "http://example.com");
        assertNotNull(parser.getErrors());
    }

    @Test
    public void testSettings() {
        ParseSettings newSettings = mock(ParseSettings.class);
        parser.settings(newSettings);
        assertEquals(newSettings, parser.settings());
    }

    @Test
    public void testParse() {
        String html = "<html><body>Test</body></html>";
        String baseUri = "http://example.com";
        Document result = Parser.parse(html, baseUri);
        assertNotNull(result);
    }

    @Test
    public void testParseFragment() {
        String fragmentHtml = "<div>Test</div>";
        Element context = new Element("div");
        String baseUri = "http://example.com";
        List<Node> result = Parser.parseFragment(fragmentHtml, context, baseUri);
        assertNotNull(result);
    }

    @Test
    public void testParseXmlFragment() {
        String fragmentXml = "<div>Test</div>";
        String baseUri = "http://example.com";
        List<Node> result = Parser.parseXmlFragment(fragmentXml, baseUri);
        assertNotNull(result);
    }

    @Test
    public void testParseBodyFragment() {
        String bodyHtml = "<div>Test</div>";
        String baseUri = "http://example.com";
        Document result = Parser.parseBodyFragment(bodyHtml, baseUri);
        assertNotNull(result);
    }

    @Test
    public void testUnescapeEntities() {
        String escapedString = "&lt;div&gt;Test&lt;/div&gt;";
        String result = Parser.unescapeEntities(escapedString, false);
        assertEquals("<div>Test</div>", result);
    }

    @Test
    public void testParseBodyFragmentRelaxed() {
        String bodyHtml = "<div>Test</div>";
        String baseUri = "http://example.com";
        Document result = Parser.parseBodyFragmentRelaxed(bodyHtml, baseUri);
        assertNotNull(result);
    }

    @Test
    public void testHtmlParser() {
        Parser htmlParser = Parser.htmlParser();
        assertNotNull(htmlParser);
        assertTrue(htmlParser.getTreeBuilder() instanceof HtmlTreeBuilder);
    }

    @Test
    public void testXmlParser() {
        Parser xmlParser = Parser.xmlParser();
        assertNotNull(xmlParser);
        assertTrue(xmlParser.getTreeBuilder() instanceof XmlTreeBuilder);
    }
}