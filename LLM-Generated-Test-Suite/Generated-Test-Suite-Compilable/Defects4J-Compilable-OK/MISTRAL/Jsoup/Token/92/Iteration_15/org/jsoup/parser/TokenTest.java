package org.jsoup.parser;

import org.junit.Before;
import org.junit.Test;
import org.jsoup.nodes.Attributes;
import org.jsoup.parser.Token;

import static org.junit.Assert.*;

public class TokenTest {

    private Token.Doctype doctype;
    private Token.StartTag startTag;
    private Token.EndTag endTag;
    private Token.Comment comment;
    private Token.Character character;
    private Token.CData cdata;
    private Token.EOF eof;

    @Before
    public void setUp() {
        doctype = new Token.Doctype();
        startTag = new Token.StartTag();
        endTag = new Token.EndTag();
        comment = new Token.Comment();
        character = new Token.Character();
        cdata = new Token.CData("cdata content");
        eof = new Token.EOF();
    }

    @Test
    public void testTokenType() {
        assertEquals("Doctype", doctype.tokenType());
        assertEquals("StartTag", startTag.tokenType());
        assertEquals("EndTag", endTag.tokenType());
        assertEquals("Comment", comment.tokenType());
        assertEquals("Character", character.tokenType());
        assertEquals("CData", cdata.tokenType());
        assertEquals("EOF", eof.tokenType());
    }

    @Test
    public void testDoctypeReset() {
        doctype.name.append("html");
        doctype.pubSysKey = "public";
        doctype.publicIdentifier.append("publicIdentifier");
        doctype.systemIdentifier.append("systemIdentifier");
        doctype.forceQuirks = true;

        doctype.reset();

        assertEquals("", doctype.getName());
        assertNull(doctype.getPubSysKey());
        assertEquals("", doctype.getPublicIdentifier());
        assertEquals("", doctype.getSystemIdentifier());
        assertFalse(doctype.isForceQuirks());
    }

    @Test
    public void testStartTagReset() {
        startTag.name("div").appendTagName("span");
        startTag.newAttribute();
        startTag.appendAttributeName("id");
        startTag.appendAttributeValue("main");
        startTag.finaliseTag();

        startTag.reset();

        assertNull(startTag.name());
        assertNull(startTag.normalName());
        assertNull(startTag.getAttributes());
        assertFalse(startTag.isSelfClosing());
    }

    @Test
    public void testEndTagReset() {
        endTag.name("div");
        endTag.finaliseTag();

        endTag.reset();

        assertNull(endTag.name());
        assertNull(endTag.normalName());
        assertNull(endTag.getAttributes());
        assertFalse(endTag.isSelfClosing());
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
        character.data("character data");

        character.reset();

        assertNull(character.getData());
    }

    @Test
    public void testCDataReset() {
        cdata.data("cdata content");

        cdata.reset();

        assertNull(cdata.getData());
    }

    @Test
    public void testEOFReset() {
        eof.reset();

        assertTrue(eof.isEOF());
    }

    @Test
    public void testTokenTypeChecks() {
        assertTrue(doctype.isDoctype());
        assertTrue(startTag.isStartTag());
        assertTrue(endTag.isEndTag());
        assertTrue(comment.isComment());
        assertTrue(character.isCharacter());
        assertTrue(cdata.isCData());
        assertTrue(eof.isEOF());
    }

    @Test
    public void testTokenCasting() {
        assertEquals(doctype, doctype.asDoctype());
        assertEquals(startTag, startTag.asStartTag());
        assertEquals(endTag, endTag.asEndTag());
        assertEquals(comment, comment.asComment());
        assertEquals(character, character.asCharacter());
    }

    @Test
    public void testStartTagToString() {
        startTag.name("div").appendTagName("span");
        startTag.newAttribute();
        startTag.appendAttributeName("id");
        startTag.appendAttributeValue("main");
        startTag.finaliseTag();

        assertEquals("<divspan id=\"main\">", startTag.toString());
    }

    @Test
    public void testEndTagToString() {
        endTag.name("div");
        endTag.finaliseTag();

        assertEquals("</div>", endTag.toString());
    }

    @Test
    public void testCommentToString() {
        comment.data.append("This is a comment");

        assertEquals("<!--This is a comment-->", comment.toString());
    }

    @Test
    public void testCharacterToString() {
        character.data("character data");

        assertEquals("character data", character.toString());
    }

    @Test
    public void testCDataToString() {
        cdata.data("cdata content");

        assertEquals("<![CDATA[cdata content]]>", cdata.toString());
    }
}