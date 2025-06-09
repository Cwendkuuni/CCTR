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
    public void testStartTag() {
        startTag.name("div");
        startTag.selfClosing = true;
        startTag.attributes = new Attributes();
        startTag.attributes.put("id", "1");

        assertEquals("div", startTag.name());
        assertTrue(startTag.isSelfClosing());
        assertEquals(1, startTag.getAttributes().size());
        assertEquals("1", startTag.getAttributes().get("id"));
    }

    @Test
    public void testEndTag() {
        endTag.name("div");
        assertEquals("div", endTag.name());
    }

    @Test
    public void testComment() {
        comment.data.append("comment");
        comment.bogus = true;

        assertEquals("comment", comment.getData());
        assertTrue(comment.bogus);
    }

    @Test
    public void testCharacter() {
        character.data("text");
        assertEquals("text", character.getData());
    }

    @Test
    public void testEOF() {
        assertTrue(eof.isEOF());
    }

    @Test
    public void testIsDoctype() {
        assertTrue(doctype.isDoctype());
        assertFalse(startTag.isDoctype());
    }

    @Test
    public void testAsDoctype() {
        assertEquals(doctype, doctype.asDoctype());
    }

    @Test
    public void testIsStartTag() {
        assertTrue(startTag.isStartTag());
        assertFalse(endTag.isStartTag());
    }

    @Test
    public void testAsStartTag() {
        assertEquals(startTag, startTag.asStartTag());
    }

    @Test
    public void testIsEndTag() {
        assertTrue(endTag.isEndTag());
        assertFalse(startTag.isEndTag());
    }

    @Test
    public void testAsEndTag() {
        assertEquals(endTag, endTag.asEndTag());
    }

    @Test
    public void testIsComment() {
        assertTrue(comment.isComment());
        assertFalse(character.isComment());
    }

    @Test
    public void testAsComment() {
        assertEquals(comment, comment.asComment());
    }

    @Test
    public void testIsCharacter() {
        assertTrue(character.isCharacter());
        assertFalse(comment.isCharacter());
    }

    @Test
    public void testAsCharacter() {
        assertEquals(character, character.asCharacter());
    }

    @Test
    public void testIsEOF() {
        assertTrue(eof.isEOF());
        assertFalse(character.isEOF());
    }
}