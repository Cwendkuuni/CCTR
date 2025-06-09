package org.jdom2.util;

import org.jdom2.Namespace;
import org.jdom2.Element;
import org.jdom2.Attribute;
import org.jdom2.util.NamespaceStack;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class NamespaceStackTest {

    private NamespaceStack namespaceStack;

    @Before
    public void setUp() {
        namespaceStack = new NamespaceStack();
    }

    @Test
    public void testConstructorWithDefaultSeed() {
        Namespace[] scope = namespaceStack.getScope();
        assertEquals(2, scope.length);
        assertEquals(Namespace.NO_NAMESPACE, scope[0]);
        assertEquals(Namespace.XML_NAMESPACE, scope[1]);
    }

    @Test
    public void testPushElement() {
        Element element = new Element("test", Namespace.getNamespace("prefix", "uri"));
        namespaceStack.push(element);

        Namespace[] scope = namespaceStack.getScope();
        assertEquals(3, scope.length);
        assertEquals(Namespace.getNamespace("prefix", "uri"), scope[0]);
    }

    @Test
    public void testPushAttribute() {
        Attribute attribute = new Attribute("attr", "value", Namespace.getNamespace("prefix", "uri"));
        namespaceStack.push(attribute);

        Namespace[] scope = namespaceStack.getScope();
        assertEquals(3, scope.length);
        assertEquals(Namespace.getNamespace("prefix", "uri"), scope[0]);
    }

    @Test
    public void testPop() {
        Element element = new Element("test", Namespace.getNamespace("prefix", "uri"));
        namespaceStack.push(element);
        namespaceStack.pop();

        Namespace[] scope = namespaceStack.getScope();
        assertEquals(2, scope.length);
        assertEquals(Namespace.NO_NAMESPACE, scope[0]);
        assertEquals(Namespace.XML_NAMESPACE, scope[1]);
    }

    @Test(expected = IllegalStateException.class)
    public void testPopBeyondInitialDepth() {
        namespaceStack.pop();
    }

    @Test
    public void testAddedForward() {
        Element element = new Element("test", Namespace.getNamespace("prefix", "uri"));
        namespaceStack.push(element);

        Iterable<Namespace> added = namespaceStack.addedForward();
        Iterator<Namespace> iterator = added.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(Namespace.getNamespace("prefix", "uri"), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testAddedReverse() {
        Element element = new Element("test", Namespace.getNamespace("prefix", "uri"));
        namespaceStack.push(element);

        Iterable<Namespace> added = namespaceStack.addedReverse();
        Iterator<Namespace> iterator = added.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(Namespace.getNamespace("prefix", "uri"), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIterator() {
        Element element = new Element("test", Namespace.getNamespace("prefix", "uri"));
        namespaceStack.push(element);

        Iterator<Namespace> iterator = namespaceStack.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(Namespace.getNamespace("prefix", "uri"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Namespace.NO_NAMESPACE, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Namespace.XML_NAMESPACE, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIsInScope() {
        Element element = new Element("test", Namespace.getNamespace("prefix", "uri"));
        namespaceStack.push(element);

        assertTrue(namespaceStack.isInScope(Namespace.getNamespace("prefix", "uri")));
        assertTrue(namespaceStack.isInScope(Namespace.NO_NAMESPACE));
        assertTrue(namespaceStack.isInScope(Namespace.XML_NAMESPACE));
        assertFalse(namespaceStack.isInScope(Namespace.getNamespace("other", "otheruri")));
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorBeyondEnd() {
        Iterator<Namespace> iterator = namespaceStack.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        iterator.next(); // This should throw NoSuchElementException
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemove() {
        Iterator<Namespace> iterator = namespaceStack.iterator();
        iterator.remove(); // This should throw UnsupportedOperationException
    }
}