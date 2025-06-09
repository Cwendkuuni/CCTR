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
    private Namespace ns1;
    private Namespace ns2;
    private Namespace ns3;
    private Element element;
    private Attribute attribute;

    @Before
    public void setUp() {
        ns1 = Namespace.getNamespace("prefix1", "uri1");
        ns2 = Namespace.getNamespace("prefix2", "uri2");
        ns3 = Namespace.getNamespace("prefix3", "uri3");

        namespaceStack = new NamespaceStack(new Namespace[]{ns1, ns2});

        element = new Element("element", ns1);
        element.addNamespaceDeclaration(ns2);

        attribute = new Attribute("attribute", "value", ns3);
    }

    @Test
    public void testPushElement() {
        namespaceStack.push(element);
        Namespace[] scope = namespaceStack.getScope();
        assertEquals(2, scope.length);
        assertEquals(ns1, scope[0]);
        assertEquals(ns2, scope[1]);
    }

    @Test
    public void testPushAttribute() {
        namespaceStack.push(attribute);
        Namespace[] scope = namespaceStack.getScope();
        assertEquals(3, scope.length);
        assertEquals(ns1, scope[0]);
        assertEquals(ns2, scope[1]);
        assertEquals(ns3, scope[2]);
    }

    @Test
    public void testPop() {
        namespaceStack.push(element);
        namespaceStack.pop();
        Namespace[] scope = namespaceStack.getScope();
        assertEquals(2, scope.length);
        assertEquals(ns1, scope[0]);
        assertEquals(ns2, scope[1]);
    }

    @Test(expected = IllegalStateException.class)
    public void testPopBeyondInitial() {
        namespaceStack.pop();
    }

    @Test
    public void testAddedForward() {
        namespaceStack.push(element);
        Iterator<Namespace> iterator = namespaceStack.addedForward().iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testAddedReverse() {
        namespaceStack.push(element);
        Iterator<Namespace> iterator = namespaceStack.addedReverse().iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIterator() {
        namespaceStack.push(element);
        Iterator<Namespace> iterator = namespaceStack.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(ns2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIsInScope() {
        namespaceStack.push(element);
        assertTrue(namespaceStack.isInScope(ns1));
        assertTrue(namespaceStack.isInScope(ns2));
        assertFalse(namespaceStack.isInScope(ns3));
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorBeyondEnd() {
        namespaceStack.push(element);
        Iterator<Namespace> iterator = namespaceStack.iterator();
        iterator.next();
        iterator.next();
        iterator.next(); // This should throw NoSuchElementException
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemove() {
        namespaceStack.push(element);
        Iterator<Namespace> iterator = namespaceStack.iterator();
        iterator.remove(); // This should throw UnsupportedOperationException
    }
}