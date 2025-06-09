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

        when(mockTreeBuilder.parse(eq(html), eq(baseUri), any(ParseErrorList.class), any(ParseSettings.class)))
                .thenReturn(mockDocument);

        Document doc = parser.parseInput(html, baseUri);
        assertNotNull(doc);
        assertEquals(mockDocument, doc);
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
        parser.setTrackErrors(10);
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
        parser.setTrackErrors(10);
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
        Document doc = Parser.parse(html, baseUri);
        assertNotNull(doc);
    }

    @Test
    public void testStaticParseFragment() {
        String fragmentHtml = "<div></div>";
        Element context = new Element("body");
        String baseUri = "http://example.com";
        List<Node> nodes = Parser.parseFragment(fragmentHtml, context, baseUri);
        assertNotNull(nodes);
        assertFalse(nodes.isEmpty());
    }

    @Test
    public void testStaticParseXmlFragment() {
        String fragmentXml = "<note></note>";
        String baseUri = "http://example.com";
        List<Node> nodes = Parser.parseXmlFragment(fragmentXml, baseUri);
        assertNotNull(nodes);
        assertFalse(nodes.isEmpty());
    }

    @Test
    public void testStaticParseBodyFragment() {
        String bodyHtml = "<p>Test</p>";
        String baseUri = "http://example.com";
        Document doc = Parser.parseBodyFragment(bodyHtml, baseUri);
        assertNotNull(doc);
        assertEquals("Test", doc.body().text());
    }

    @Test
    public void testStaticUnescapeEntities() {
        String escaped = "&lt;p&gt;Test&lt;/p&gt;";
        String unescaped = Parser.unescapeEntities(escaped, false);
        assertEquals("<p>Test</p>", unescaped);
    }

    @Test
    public void testStaticParseBodyFragmentRelaxed() {
        String bodyHtml = "<p>Test</p>";
        String baseUri = "http://example.com";
        Document doc = Parser.parseBodyFragmentRelaxed(bodyHtml, baseUri);
        assertNotNull(doc);
        assertEquals("Test", doc.body().text());
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