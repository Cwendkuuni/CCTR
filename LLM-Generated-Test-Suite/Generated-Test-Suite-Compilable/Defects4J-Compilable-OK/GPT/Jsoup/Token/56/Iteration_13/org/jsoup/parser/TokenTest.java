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
    public void testStartTagReset() {
        startTag.name("div");
        startTag.selfClosing = true;
        startTag.getAttributes().put("id", "test");

        startTag.reset();

        assertNull(startTag.name());
        assertNull(startTag.getAttributes());
        assertFalse(startTag.isSelfClosing());
    }

    @Test
    public void testEndTagReset() {
        endTag.name("div");

        endTag.reset();

        assertNull(endTag.name());
    }

    @Test
    public void testCommentReset() {
        comment.data.append("test comment");
        comment.bogus = true;

        comment.reset();

        assertEquals("", comment.getData());
        assertFalse(comment.bogus);
    }

    @Test
    public void testCharacterReset() {
        character.data("test");

        character.reset();

        assertNull(character.getData());
    }

    @Test
    public void testEOFReset() {
        assertSame(eof, eof.reset());
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
        assertFalse(endTag.isStartTag());
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

    @Test
    public void testStartTagNameAttr() {
        Attributes attributes = new Attributes();
        attributes.put("class", "test");
        startTag.nameAttr("div", attributes);

        assertEquals("div", startTag.name());
        assertEquals("test", startTag.getAttributes().get("class"));
    }

    @Test
    public void testStartTagToString() {
        startTag.name("div");
        assertEquals("<div>", startTag.toString());

        startTag.getAttributes().put("id", "test");
        assertEquals("<div id=\"test\">", startTag.toString());
    }

    @Test
    public void testEndTagToString() {
        endTag.name("div");
        assertEquals("</div>", endTag.toString());
    }

    @Test
    public void testCommentToString() {
        comment.data.append("test comment");
        assertEquals("<!--test comment-->", comment.toString());
    }

    @Test
    public void testCharacterToString() {
        character.data("test");
        assertEquals("test", character.toString());
    }
}