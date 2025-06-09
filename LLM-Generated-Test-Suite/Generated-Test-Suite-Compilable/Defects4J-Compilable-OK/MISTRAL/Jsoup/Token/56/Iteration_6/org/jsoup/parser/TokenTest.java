package org.jsoup.parser;

import org.junit.Before;
import org.junit.Test;
import org.jsoup.nodes.Attributes;

import static org.junit.Assert.*;

public class TokenTest {

    private Token.Doctype doctype;
    private Token.StartTag startTag;
    private Token.EndTag endTag;
    private Token.Comment comment;
    private Token.Character character;
    private Token.EOF eof;

    @Before
    public void setUp() {
        doctype = new Token.Doctype();
        startTag = new Token.StartTag();
        endTag = new Token.EndTag();
        comment = new Token.Comment();
        character = new Token.Character();
        eof = new Token.EOF();
    }

    @Test
    public void testTokenType() {
        assertEquals("Doctype", doctype.tokenType());
        assertEquals("StartTag", startTag.tokenType());
        assertEquals("EndTag", endTag.tokenType());
        assertEquals("Comment", comment.tokenType());
        assertEquals("Character", character.tokenType());
        assertEquals("EOF", eof.tokenType());
    }

    @Test
    public void testReset() {
        doctype.name.append("html");
        doctype.reset();
        assertEquals("", doctype.getName());

        startTag.name("div");
        startTag.reset();
        assertNull(startTag.tagName);

        endTag.name("div");
        endTag.reset();
        assertNull(endTag.tagName);

        comment.data.append("comment");
        comment.reset();
        assertEquals("", comment.getData());

        character.data("text");
        character.reset();
        assertNull(character.getData());

        eof.reset();
        assertTrue(eof.isEOF());
    }

    @Test
    public void testDoctype() {
        doctype.name.append("html");
        doctype.publicIdentifier.append("public");
        doctype.systemIdentifier.append("system");
        doctype.forceQuirks = true;

        assertEquals("html", doctype.getName());
        assertEquals("public", doctype.getPublicIdentifier());
        assertEquals("system", doctype.getSystemIdentifier());
        assertTrue(doctype.isForceQuirks());
    }

    @Test
    public void testTag() {
        startTag.name("div");
        startTag.appendTagName("span");
        startTag.appendAttributeName("id");
        startTag.appendAttributeValue("main");
        startTag.setEmptyAttributeValue();
        startTag.newAttribute();
        startTag.finaliseTag();

        assertEquals("divspan", startTag.name());
        assertEquals("divspan", startTag.normalName());
        assertEquals(1, startTag.getAttributes().size());
        assertEquals("main", startTag.getAttributes().get("id"));

        endTag.name("div");
        assertEquals("div", endTag.name());
        assertEquals("div", endTag.normalName());
    }

    @Test
    public void testStartTag() {
        Attributes attributes = new Attributes();
        attributes.put("id", "main");
        startTag.nameAttr("div", attributes);

        assertEquals("div", startTag.name());
        assertEquals(1, startTag.getAttributes().size());
        assertEquals("main", startTag.getAttributes().get("id"));
        assertEquals("<div id=\"main\">", startTag.toString());
    }

    @Test
    public void testEndTag() {
        endTag.name("div");
        assertEquals("</div>", endTag.toString());
    }

    @Test
    public void testComment() {
        comment.data.append("comment");
        assertEquals("comment", comment.getData());
        assertEquals("<!--comment-->", comment.toString());
    }

    @Test
    public void testCharacter() {
        character.data("text");
        assertEquals("text", character.getData());
        assertEquals("text", character.toString());
    }

    @Test
    public void testEOF() {
        assertTrue(eof.isEOF());
    }

    @Test
    public void testTokenTypeChecks() {
        assertTrue(doctype.isDoctype());
        assertFalse(doctype.isStartTag());

        assertTrue(startTag.isStartTag());
        assertFalse(startTag.isEndTag());

        assertTrue(endTag.isEndTag());
        assertFalse(endTag.isComment());

        assertTrue(comment.isComment());
        assertFalse(comment.isCharacter());

        assertTrue(character.isCharacter());
        assertFalse(character.isEOF());

        assertTrue(eof.isEOF());
        assertFalse(eof.isDoctype());
    }
}