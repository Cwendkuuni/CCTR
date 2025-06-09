package org.jdom2.util;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.util.NamespaceStack;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class NamespaceStackTest {

    private NamespaceStack namespaceStack;
    private Namespace ns1;
    private Namespace ns2;
    private Namespace ns3;

    @Before
    public void setUp() {
        namespaceStack = new NamespaceStack();
        ns1 = Namespace.getNamespace("prefix1", "uri1");
        ns2 = Namespace.getNamespace("prefix2", "uri2");
        ns3 = Namespace.getNamespace("prefix3", "uri3");
    }

    @Test
    public void testConstructor() {
        Namespace[] scope = namespaceStack.getScope();
        assertEquals(2, scope.length);
        assertEquals(Namespace.NO_NAMESPACE, scope[0]);
        assertEquals(Namespace.XML_NAMESPACE, scope[1]);
    }

    @Test
    public void testPushElement() {
        Element element = new Element("element", ns1);
        namespaceStack.push(element);

        Namespace[] scope = namespaceStack.getScope();
        assertEquals(3, scope.length);
        assertEquals(ns1, scope[0]);
    }

    @Test
    public void testPushAttribute() {
        Attribute attribute = new Attribute("attr", "value", ns2);
        namespaceStack.push(attribute);

        Namespace[] scope = namespaceStack.getScope();
        assertEquals(3, scope.length);
        assertEquals(ns2, scope[0]);
    }

    @Test
    public void testPop() {
        Element element = new Element("element", ns1);
        namespaceStack.push(element);
        namespaceStack.pop();

        Namespace[] scope = namespaceStack.getScope();
        assertEquals(2, scope.length);
        assertEquals(Namespace.NO_NAMESPACE, scope[0]);
        assertEquals(Namespace.XML_NAMESPACE, scope[1]);
    }

    @Test(expected = IllegalStateException.class)
    public void testPopException() {
        namespaceStack.pop();
    }

    @Test
    public void testAddedForward() {
        Element element = new Element("element", ns1);
        namespaceStack.push(element);

        Iterator<Namespace> iterator = namespaceStack.addedForward().iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testAddedReverse() {
        Element element = new Element("element", ns1);
        namespaceStack.push(element);

        Iterator<Namespace> iterator = namespaceStack.addedReverse().iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIterator() {
        Element element = new Element("element", ns1);
        namespaceStack.push(element);

        Iterator<Namespace> iterator = namespaceStack.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Namespace.NO_NAMESPACE, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Namespace.XML_NAMESPACE, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIsInScope() {
        Element element = new Element("element", ns1);
        namespaceStack.push(element);

        assertTrue(namespaceStack.isInScope(ns1));
        assertTrue(namespaceStack.isInScope(Namespace.NO_NAMESPACE));
        assertTrue(namespaceStack.isInScope(Namespace.XML_NAMESPACE));
        assertFalse(namespaceStack.isInScope(ns2));
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorException() {
        Iterator<Namespace> iterator = namespaceStack.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        iterator.next(); // This should throw NoSuchElementException
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemoveException() {
        Iterator<Namespace> iterator = namespaceStack.iterator();
        iterator.remove(); // This should throw UnsupportedOperationException
    }
}