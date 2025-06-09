package com.allenstudio.ir.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

public class XmlElementTest {

    private XmlElement element;

    @Before
    public void setUp() {
        element = new XmlElement("root");
    }

    @Test
    public void testAddAttribute() {
        element.addAttribute("key", "value");
        assertEquals("value", element.getAttribute("key"));
    }

    @Test
    public void testGetAttributeWithDefault() {
        assertEquals("default", element.getAttribute("nonexistent", "default"));
        element.addAttribute("key", "value");
        assertEquals("value", element.getAttribute("key", "default"));
    }

    @Test
    public void testSetAttributes() {
        Hashtable<String, String> attrs = new Hashtable<>();
        attrs.put("key1", "value1");
        attrs.put("key2", "value2");
        element.setAttributes(attrs);
        assertEquals(attrs, element.getAttributes());
    }

    @Test
    public void testAddElement() {
        XmlElement child = new XmlElement("child");
        element.addElement(child);
        assertEquals(child, element.getElement(0));
    }

    @Test
    public void testRemoveElementByReference() {
        XmlElement child = new XmlElement("child");
        element.addElement(child);
        element.removeElement(child);
        assertEquals(0, element.count());
    }

    @Test
    public void testRemoveElementByIndex() {
        XmlElement child = new XmlElement("child");
        element.addElement(child);
        element.removeElement(0);
        assertEquals(0, element.count());
    }

    @Test
    public void testRemoveAllElements() {
        element.addElement(new XmlElement("child1"));
        element.addElement(new XmlElement("child2"));
        element.removeAllElements();
        assertEquals(0, element.count());
    }

    @Test
    public void testAppend() {
        XmlElement child = new XmlElement("child");
        element.append(child);
        assertEquals(child, element.getElement(0));
    }

    @Test
    public void testInsertElement() {
        XmlElement child1 = new XmlElement("child1");
        XmlElement child2 = new XmlElement("child2");
        element.addElement(child1);
        element.insertElement(child2, 0);
        assertEquals(child2, element.getElement(0));
    }

    @Test
    public void testGetElementByPath() {
        XmlElement child = new XmlElement("child");
        element.addElement(child);
        assertEquals(child, element.getElement("child"));
    }

    @Test
    public void testAddSubElementByPath() {
        XmlElement subElement = element.addSubElement("child.subchild");
        assertNotNull(subElement);
        assertEquals("subchild", subElement.getName());
    }

    @Test
    public void testAddSubElementByElement() {
        XmlElement child = new XmlElement("child");
        element.addSubElement(child);
        assertEquals(child, element.getElement(0));
    }

    @Test
    public void testAddSubElementByNameAndData() {
        XmlElement child = element.addSubElement("child", "data");
        assertEquals("child", child.getName());
        assertEquals("data", child.getData());
    }

    @Test
    public void testSetParent() {
        XmlElement parent = new XmlElement("parent");
        element.setParent(parent);
        assertEquals(parent, element.getParent());
    }

    @Test
    public void testSetData() {
        element.setData("data");
        assertEquals("data", element.getData());
    }

    @Test
    public void testSetName() {
        element.setName("newName");
        assertEquals("newName", element.getName());
    }

    @Test
    public void testClone() {
        XmlElement clone = (XmlElement) element.clone();
        assertEquals(element, clone);
        assertNotSame(element, clone);
    }

    @Test
    public void testEquals() {
        XmlElement anotherElement = new XmlElement("root");
        assertTrue(element.equals(anotherElement));
        anotherElement.setName("different");
        assertFalse(element.equals(anotherElement));
    }

    @Test
    public void testHashCode() {
        XmlElement anotherElement = new XmlElement("root");
        assertEquals(element.hashCode(), anotherElement.hashCode());
        anotherElement.setName("different");
        assertNotEquals(element.hashCode(), anotherElement.hashCode());
    }
}