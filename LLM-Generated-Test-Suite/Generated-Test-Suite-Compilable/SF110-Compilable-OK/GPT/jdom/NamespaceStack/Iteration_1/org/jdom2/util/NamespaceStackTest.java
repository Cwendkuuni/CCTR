package org.jdom2.util;

import org.jdom2.Namespace;
import org.jdom2.Element;
import org.jdom2.Attribute;
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
        ns1 = Namespace.getNamespace("prefix1", "uri1");
        ns2 = Namespace.getNamespace("prefix2", "uri2");
        ns3 = Namespace.getNamespace("prefix3", "uri3");
        namespaceStack = new NamespaceStack(new Namespace[]{ns1, ns2});
    }

    @Test
    public void testPushElement() {
        Element element = new Element("element", ns3);
        namespaceStack.push(element);

        Namespace[] scope = namespaceStack.getScope();
        assertEquals(3, scope.length);
        assertEquals(ns3, scope[0]);
        assertEquals(ns1, scope[1]);
        assertEquals(ns2, scope[2]);
    }

    @Test
    public void testPushAttribute() {
        Attribute attribute = new Attribute("attr", "value", ns3);
        namespaceStack.push(attribute);

        Namespace[] scope = namespaceStack.getScope();
        assertEquals(3, scope.length);
        assertEquals(ns3, scope[0]);
        assertEquals(ns1, scope[1]);
        assertEquals(ns2, scope[2]);
    }

    @Test
    public void testPop() {
        namespaceStack.pop();
        Namespace[] scope = namespaceStack.getScope();
        assertEquals(2, scope.length);
        assertEquals(ns1, scope[0]);
        assertEquals(ns2, scope[1]);
    }

    @Test(expected = IllegalStateException.class)
    public void testPopBeyondLimit() {
        namespaceStack.pop();
        namespaceStack.pop(); // This should throw an exception
    }

    @Test
    public void testAddedForward() {
        Element element = new Element("element", ns3);
        namespaceStack.push(element);

        Iterator<Namespace> iterator = namespaceStack.addedForward().iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns3, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testAddedReverse() {
        Element element = new Element("element", ns3);
        namespaceStack.push(element);

        Iterator<Namespace> iterator = namespaceStack.addedReverse().iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns3, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIterator() {
        Iterator<Namespace> iterator = namespaceStack.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(ns2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIsInScope() {
        assertTrue(namespaceStack.isInScope(ns1));
        assertTrue(namespaceStack.isInScope(ns2));
        assertFalse(namespaceStack.isInScope(ns3));
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorBeyondLimit() {
        Iterator<Namespace> iterator = namespaceStack.iterator();
        iterator.next();
        iterator.next();
        iterator.next(); // This should throw an exception
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemove() {
        Iterator<Namespace> iterator = namespaceStack.iterator();
        iterator.remove(); // This should throw an exception
    }
}