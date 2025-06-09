package org.jsoup.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.jsoup.parser.TreeBuilder;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.XmlTreeBuilder;
import org.jsoup.parser.ParseErrorList;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.ParseError;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ParserTest {

    private Parser parser;
    private TreeBuilder mockTreeBuilder;

    @Before
    public void setUp() {
        mockTreeBuilder = mock(TreeBuilder.class);
        parser = new Parser(mockTreeBuilder);
    }

    @Test
    public void testParseInput() {
        String html = "<html></html>";
        String baseUri = "http://example.com";
        Document mockDocument = mock(Document.class);
        when(mockTreeBuilder.parse(html, baseUri, ParseErrorList.noTracking(), mockTreeBuilder.defaultSettings()))
                .thenReturn(mockDocument);

        Document document = parser.parseInput(html, baseUri);
        assertEquals(mockDocument, document);
    }

    @Test
    public void testGetTreeBuilder() {
        assertEquals(mockTreeBuilder, parser.getTreeBuilder());
    }

    @Test
    public void testSetTreeBuilder() {
        TreeBuilder newTreeBuilder = new HtmlTreeBuilder();
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
        parser.parseInput("<html>", "http://example.com");
        List<ParseError> errors = parser.getErrors();
        assertNotNull(errors);
    }

    @Test
    public void testSettings() {
        ParseSettings newSettings = new ParseSettings(true, true);
        parser.settings(newSettings);
        assertEquals(newSettings, parser.settings());
    }

    @Test
    public void testStaticParse() {
        String html = "<html></html>";
        String baseUri = "http://example.com";
        Document document = Parser.parse(html, baseUri);
        assertNotNull(document);
    }

    @Test
    public void testParseFragment() {
        String fragmentHtml = "<div></div>";
        Element context = new Element("div");
        String baseUri = "http://example.com";
        List<Node> nodes = Parser.parseFragment(fragmentHtml, context, baseUri);
        assertNotNull(nodes);
    }

    @Test
    public void testParseXmlFragment() {
        String fragmentXml = "<note></note>";
        String baseUri = "http://example.com";
        List<Node> nodes = Parser.parseXmlFragment(fragmentXml, baseUri);
        assertNotNull(nodes);
    }

    @Test
    public void testParseBodyFragment() {
        String bodyHtml = "<p>Test</p>";
        String baseUri = "http://example.com";
        Document document = Parser.parseBodyFragment(bodyHtml, baseUri);
        assertNotNull(document);
        assertEquals("<p>Test</p>", document.body().html());
    }

    @Test
    public void testUnescapeEntities() {
        String escaped = "&lt;p&gt;Test&lt;/p&gt;";
        String unescaped = Parser.unescapeEntities(escaped, false);
        assertEquals("<p>Test</p>", unescaped);
    }

    @Test
    public void testParseBodyFragmentRelaxed() {
        String bodyHtml = "<p>Test</p>";
        String baseUri = "http://example.com";
        Document document = Parser.parseBodyFragmentRelaxed(bodyHtml, baseUri);
        assertNotNull(document);
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