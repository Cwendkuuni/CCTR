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
        doctype.publicIdentifier.append("publicId");
        doctype.systemIdentifier.append("systemId");
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
        startTag.attributes.put("id", "test");

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
        comment.data.append("This is a comment");
        comment.bogus = true;

        comment.reset();

        assertEquals("", comment.getData());
        assertFalse(comment.bogus);
    }

    @Test
    public void testCharacterReset() {
        character.data("Some data");

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
    public void testIsTypeMethods() {
        assertTrue(doctype.isDoctype());
        assertTrue(startTag.isStartTag());
        assertTrue(endTag.isEndTag());
        assertTrue(comment.isComment());
        assertTrue(character.isCharacter());
        assertTrue(eof.isEOF());
    }

    @Test
    public void testAsTypeMethods() {
        assertSame(doctype, doctype.asDoctype());
        assertSame(startTag, startTag.asStartTag());
        assertSame(endTag, endTag.asEndTag());
        assertSame(comment, comment.asComment());
        assertSame(character, character.asCharacter());
    }

    @Test
    public void testStartTagNameAttr() {
        Attributes attributes = new Attributes();
        attributes.put("class", "testClass");

        startTag.nameAttr("p", attributes);

        assertEquals("p", startTag.name());
        assertEquals("p", startTag.normalName());
        assertEquals(attributes, startTag.getAttributes());
    }

    @Test
    public void testStartTagToString() {
        startTag.name("p");
        assertEquals("<p>", startTag.toString());

        startTag.getAttributes().put("class", "testClass");
        assertEquals("<p class=\"testClass\">", startTag.toString());
    }

    @Test
    public void testEndTagToString() {
        endTag.name("p");
        assertEquals("</p>", endTag.toString());
    }

    @Test
    public void testCommentToString() {
        comment.data.append("This is a comment");
        assertEquals("<!--This is a comment-->", comment.toString());
    }

    @Test
    public void testCharacterToString() {
        character.data("Some data");
        assertEquals("Some data", character.toString());
    }
}