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
    public void testPushElement() {
        Element element = new Element("element", ns1);
        namespaceStack.push(element);
        assertTrue(namespaceStack.isInScope(ns1));
    }

    @Test
    public void testPushAttribute() {
        Attribute attribute = new Attribute("attr", "value", ns2);
        namespaceStack.push(attribute);
        assertTrue(namespaceStack.isInScope(ns2));
    }

    @Test
    public void testPop() {
        Element element = new Element("element", ns1);
        namespaceStack.push(element);
        namespaceStack.pop();
        assertFalse(namespaceStack.isInScope(ns1));
    }

    @Test(expected = IllegalStateException.class)
    public void testPopEmptyStack() {
        namespaceStack.pop();
    }

    @Test
    public void testAddedForward() {
        Element element = new Element("element", ns1);
        namespaceStack.push(element);
        Iterator<Namespace> iterator = namespaceStack.addedForward().iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns1, iterator.next());
    }

    @Test
    public void testAddedReverse() {
        Element element = new Element("element", ns1);
        namespaceStack.push(element);
        Iterator<Namespace> iterator = namespaceStack.addedReverse().iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns1, iterator.next());
    }

    @Test
    public void testIterator() {
        Element element = new Element("element", ns1);
        namespaceStack.push(element);
        Iterator<Namespace> iterator = namespaceStack.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns1, iterator.next());
    }

    @Test
    public void testGetScope() {
        Element element = new Element("element", ns1);
        namespaceStack.push(element);
        Namespace[] scope = namespaceStack.getScope();
        assertEquals(1, scope.length);
        assertEquals(ns1, scope[0]);
    }

    @Test
    public void testIsInScope() {
        Element element = new Element("element", ns1);
        namespaceStack.push(element);
        assertTrue(namespaceStack.isInScope(ns1));
        assertFalse(namespaceStack.isInScope(ns2));
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorNoSuchElementException() {
        Iterator<Namespace> iterator = namespaceStack.iterator();
        iterator.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemoveUnsupportedOperationException() {
        Iterator<Namespace> iterator = namespaceStack.iterator();
        iterator.remove();
    }
}