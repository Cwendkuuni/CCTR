package org.jdom2.util;

import org.jdom2.Element;
import org.jdom2.Attribute;
import org.jdom2.Namespace;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        element = new Element("root", ns1);
        attribute = new Attribute("attr", "value", ns2);
    }

    @Test
    public void testConstructor() {
        NamespaceStack stack = new NamespaceStack();
        Assert.assertNotNull(stack);
        Assert.assertEquals(0, stack.getScope().length);
    }

    @Test
    public void testConstructorWithSeed() {
        Namespace[] seed = {ns1, ns2};
        NamespaceStack stack = new NamespaceStack(seed);
        Assert.assertNotNull(stack);
        Assert.assertEquals(2, stack.getScope().length);
    }

    @Test
    public void testPushElement() {
        namespaceStack.push(element);
        Namespace[] scope = namespaceStack.getScope();
        Assert.assertEquals(1, scope.length);
        Assert.assertEquals(ns1, scope[0]);
    }

    @Test
    public void testPushAttribute() {
        namespaceStack.push(attribute);
        Namespace[] scope = namespaceStack.getScope();
        Assert.assertEquals(1, scope.length);
        Assert.assertEquals(ns2, scope[0]);
    }

    @Test
    public void testPop() {
        namespaceStack.push(element);
        namespaceStack.pop();
        Namespace[] scope = namespaceStack.getScope();
        Assert.assertEquals(0, scope.length);
    }

    @Test(expected = IllegalStateException.class)
    public void testPopEmptyStack() {
        namespaceStack.pop();
    }

    @Test
    public void testAddedForward() {
        namespaceStack.push(element);
        Iterable<Namespace> added = namespaceStack.addedForward();
        Iterator<Namespace> iterator = added.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(ns1, iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void testAddedReverse() {
        namespaceStack.push(element);
        Iterable<Namespace> added = namespaceStack.addedReverse();
        Iterator<Namespace> iterator = added.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(ns1, iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void testIterator() {
        namespaceStack.push(element);
        Iterator<Namespace> iterator = namespaceStack.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(ns1, iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetScope() {
        namespaceStack.push(element);
        Namespace[] scope = namespaceStack.getScope();
        Assert.assertEquals(1, scope.length);
        Assert.assertEquals(ns1, scope[0]);
    }

    @Test
    public void testIsInScope() {
        namespaceStack.push(element);
        Assert.assertTrue(namespaceStack.isInScope(ns1));
        Assert.assertFalse(namespaceStack.isInScope(ns2));
    }

    @Test
    public void testPushElementWithAttributes() {
        element.setAttribute(attribute);
        namespaceStack.push(element);
        Namespace[] scope = namespaceStack.getScope();
        Assert.assertEquals(2, scope.length);
        Assert.assertEquals(ns1, scope[0]);
        Assert.assertEquals(ns2, scope[1]);
    }

    @Test
    public void testPushElementWithAdditionalNamespaces() {
        element.addNamespaceDeclaration(ns3);
        namespaceStack.push(element);
        Namespace[] scope = namespaceStack.getScope();
        Assert.assertEquals(2, scope.length);
        Assert.assertEquals(ns1, scope[0]);
        Assert.assertEquals(ns3, scope[1]);
    }
}