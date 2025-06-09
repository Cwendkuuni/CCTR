package org.jsoup.parser;

import org.jsoup.nodes.Attributes;
import org.junit.Before;
import org.junit.Test;
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
    public void testDoctypeReset() {
        doctype.name.append("html");
        doctype.publicIdentifier.append("public");
        doctype.systemIdentifier.append("system");
        doctype.forceQuirks = true;
        doctype.reset();
        assertEquals("", doctype.getName());
        assertEquals("", doctype.getPublicIdentifier());
        assertEquals("", doctype.getSystemIdentifier());
        assertFalse(doctype.isForceQuirks());
    }

    @Test
    public void testDoctypeTokenType() {
        assertEquals("Doctype", doctype.tokenType());
    }

    @Test
    public void testStartTagReset() {
        startTag.name("div");
        startTag.selfClosing = true;
        startTag.reset();
        assertNull(startTag.name());
        assertFalse(startTag.isSelfClosing());
    }

    @Test
    public void testStartTagNameAttr() {
        Attributes attributes = new Attributes();
        attributes.put("id", "test");
        startTag.nameAttr("div", attributes);
        assertEquals("div", startTag.name());
        assertEquals(attributes, startTag.getAttributes());
    }

    @Test
    public void testStartTagToString() {
        Attributes attributes = new Attributes();
        attributes.put("id", "test");
        startTag.nameAttr("div", attributes);
        assertEquals("<div id=\"test\">", startTag.toString());
    }

    @Test
    public void testEndTagReset() {
        endTag.name("div");
        endTag.reset();
        assertNull(endTag.name());
    }

    @Test
    public void testEndTagToString() {
        endTag.name("div");
        assertEquals("</div>", endTag.toString());
    }

    @Test
    public void testCommentReset() {
        comment.data.append("comment");
        comment.bogus = true;
        comment.reset();
        assertEquals("", comment.getData());
        assertFalse(comment.bogus);
    }

    @Test
    public void testCommentToString() {
        comment.data.append("comment");
        assertEquals("<!--comment-->", comment.toString());
    }

    @Test
    public void testCharacterReset() {
        character.data("data");
        character.reset();
        assertNull(character.getData());
    }

    @Test
    public void testCharacterToString() {
        character.data("data");
        assertEquals("data", character.toString());
    }

    @Test
    public void testEOFReset() {
        assertSame(eof, eof.reset());
    }

    @Test
    public void testIsDoctype() {
        assertTrue(doctype.isDoctype());
        assertFalse(startTag.isDoctype());
    }

    @Test
    public void testAsDoctype() {
        assertSame(doctype, doctype.asDoctype());
    }

    @Test
    public void testIsStartTag() {
        assertTrue(startTag.isStartTag());
        assertFalse(doctype.isStartTag());
    }

    @Test
    public void testAsStartTag() {
        assertSame(startTag, startTag.asStartTag());
    }

    @Test
    public void testIsEndTag() {
        assertTrue(endTag.isEndTag());
        assertFalse(startTag.isEndTag());
    }

    @Test
    public void testAsEndTag() {
        assertSame(endTag, endTag.asEndTag());
    }

    @Test
    public void testIsComment() {
        assertTrue(comment.isComment());
        assertFalse(character.isComment());
    }

    @Test
    public void testAsComment() {
        assertSame(comment, comment.asComment());
    }

    @Test
    public void testIsCharacter() {
        assertTrue(character.isCharacter());
        assertFalse(comment.isCharacter());
    }

    @Test
    public void testAsCharacter() {
        assertSame(character, character.asCharacter());
    }

    @Test
    public void testIsEOF() {
        assertTrue(eof.isEOF());
        assertFalse(character.isEOF());
    }
}