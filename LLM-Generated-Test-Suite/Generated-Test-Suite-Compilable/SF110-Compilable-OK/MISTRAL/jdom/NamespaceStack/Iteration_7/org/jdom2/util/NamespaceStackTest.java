package org.jdom2.util;

import org.jdom2.Namespace;
import org.jdom2.Element;
import org.jdom2.Attribute;
import org.jdom2.util.NamespaceStack;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;

public class NamespaceStackTest {

    private NamespaceStack namespaceStack;
    private Namespace ns1;
    private Namespace ns2;
    private Namespace ns3;
    private Element element;
    private Attribute attribute;

    @Before
    public void setUp() {
        namespaceStack = new NamespaceStack();
        ns1 = Namespace.getNamespace("prefix1", "uri1");
        ns2 = Namespace.getNamespace("prefix2", "uri2");
        ns3 = Namespace.getNamespace("prefix3", "uri3");
        element = new Element("element", ns1);
        attribute = new Attribute("attribute", "value", ns2);
    }

    @Test
    public void testConstructor() {
        assertNotNull(namespaceStack);
        assertEquals(0, namespaceStack.getScope().length);
    }

    @Test
    public void testPushElement() {
        namespaceStack.push(element);
        assertTrue(namespaceStack.isInScope(ns1));
        assertEquals(1, namespaceStack.getScope().length);
    }

    @Test
    public void testPushAttribute() {
        namespaceStack.push(attribute);
        assertTrue(namespaceStack.isInScope(ns2));
        assertEquals(1, namespaceStack.getScope().length);
    }

    @Test
    public void testPushPop() {
        namespaceStack.push(element);
        namespaceStack.push(attribute);
        assertTrue(namespaceStack.isInScope(ns1));
        assertTrue(namespaceStack.isInScope(ns2));
        namespaceStack.pop();
        assertFalse(namespaceStack.isInScope(ns2));
        assertTrue(namespaceStack.isInScope(ns1));
        namespaceStack.pop();
        assertFalse(namespaceStack.isInScope(ns1));
    }

    @Test(expected = IllegalStateException.class)
    public void testPopEmptyStack() {
        namespaceStack.pop();
    }

    @Test
    public void testAddedForward() {
        namespaceStack.push(element);
        Iterable<Namespace> addedForward = namespaceStack.addedForward();
        Iterator<Namespace> iterator = addedForward.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testAddedReverse() {
        namespaceStack.push(element);
        Iterable<Namespace> addedReverse = namespaceStack.addedReverse();
        Iterator<Namespace> iterator = addedReverse.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIterator() {
        namespaceStack.push(element);
        Iterator<Namespace> iterator = namespaceStack.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ns1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetScope() {
        namespaceStack.push(element);
        Namespace[] scope = namespaceStack.getScope();
        assertEquals(1, scope.length);
        assertEquals(ns1, scope[0]);
    }

    @Test
    public void testIsInScope() {
        namespaceStack.push(element);
        assertTrue(namespaceStack.isInScope(ns1));
        assertFalse(namespaceStack.isInScope(ns2));
    }

    @Test
    public void testEmptyIterable() {
        Iterable<Namespace> emptyIterable = namespaceStack.addedForward();
        Iterator<Namespace> iterator = emptyIterable.iterator();
        assertFalse(iterator.hasNext());
    }
}