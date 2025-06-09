package com.allenstudio.ir.util;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class XmlElementTest {

    private XmlElement xmlElement;

    @Before
    public void setUp() {
        xmlElement = new XmlElement("root");
    }

    @Test
    public void testDefaultConstructor() {
        XmlElement element = new XmlElement();
        assertNotNull(element.getAttributes());
        assertNotNull(element.getElements());
    }

    @Test
    public void testConstructorWithName() {
        XmlElement element = new XmlElement("test");
        assertEquals("test", element.getName());
        assertNotNull(element.getAttributes());
        assertNotNull(element.getElements());
        assertEquals("", element.getData());
    }

    @Test
    public void testConstructorWithNameAndAttributes() {
        Hashtable<String, String> attributes = new Hashtable<>();
        attributes.put("key", "value");
        XmlElement element = new XmlElement("test", attributes);
        assertEquals("test", element.getName());
        assertEquals("value", element.getAttribute("key"));
        assertNotNull(element.getElements());
    }

    @Test
    public void testConstructorWithNameAndData() {
        XmlElement element = new XmlElement("test", "data");
        assertEquals("test", element.getName());
        assertEquals("data", element.getData());
        assertNotNull(element.getAttributes());
        assertNotNull(element.getElements());
    }

    @Test
    public void testAddAttribute() {
        xmlElement.addAttribute("key", "value");
        assertEquals("value", xmlElement.getAttribute("key"));
    }

    @Test
    public void testGetAttribute() {
        xmlElement.addAttribute("key", "value");
        assertEquals("value", xmlElement.getAttribute("key"));
    }

    @Test
    public void testGetAttributeWithDefault() {
        assertEquals("default", xmlElement.getAttribute("key", "default"));
        assertEquals("default", xmlElement.getAttribute("key"));
    }

    @Test
    public void testGetAttributes() {
        Hashtable<String, String> attributes = new Hashtable<>();
        attributes.put("key", "value");
        xmlElement.setAttributes(attributes);
        assertEquals(attributes, xmlElement.getAttributes());
    }

    @Test
    public void testSetAttributes() {
        Hashtable<String, String> attributes = new Hashtable<>();
        attributes.put("key", "value");
        xmlElement.setAttributes(attributes);
        assertEquals("value", xmlElement.getAttribute("key"));
    }

    @Test
    public void testGetAttributeNames() {
        xmlElement.addAttribute("key1", "value1");
        xmlElement.addAttribute("key2", "value2");
        assertTrue(xmlElement.getAttributeNames().hasMoreElements());
    }

    @Test
    public void testAddElement() {
        XmlElement child = new XmlElement("child");
        assertTrue(xmlElement.addElement(child));
        assertEquals(child, xmlElement.getElement(0));
    }

    @Test
    public void testRemoveElementByObject() {
        XmlElement child = new XmlElement("child");
        xmlElement.addElement(child);
        assertEquals(child, xmlElement.removeElement(child));
        assertEquals(0, xmlElement.count());
    }

    @Test
    public void testRemoveElementByIndex() {
        XmlElement child = new XmlElement("child");
        xmlElement.addElement(child);
        assertEquals(child, xmlElement.removeElement(0));
        assertEquals(0, xmlElement.count());
    }

    @Test
    public void testRemoveAllElements() {
        XmlElement child1 = new XmlElement("child1");
        XmlElement child2 = new XmlElement("child2");
        xmlElement.addElement(child1);
        xmlElement.addElement(child2);
        xmlElement.removeAllElements();
        assertEquals(0, xmlElement.count());
    }

    @Test
    public void testRemoveFromParent() {
        XmlElement child = new XmlElement("child");
        xmlElement.addElement(child);
        child.removeFromParent();
        assertEquals(0, xmlElement.count());
        assertNull(child.getParent());
    }

    @Test
    public void testAppend() {
        XmlElement child = new XmlElement("child");
        xmlElement.append(child);
        assertEquals(child, xmlElement.getElement(0));
    }

    @Test
    public void testInsertElement() {
        XmlElement child1 = new XmlElement("child1");
        XmlElement child2 = new XmlElement("child2");
        xmlElement.addElement(child1);
        xmlElement.insertElement(child2, 0);
        assertEquals(child2, xmlElement.getElement(0));
        assertEquals(child1, xmlElement.getElement(1));
    }

    @Test
    public void testGetElements() {
        XmlElement child = new XmlElement("child");
        xmlElement.addElement(child);
        List<XmlElement> elements = xmlElement.getElements();
        assertEquals(1, elements.size());
        assertEquals(child, elements.get(0));
    }

    @Test
    public void testCount() {
        XmlElement child1 = new XmlElement("child1");
        XmlElement child2 = new XmlElement("child2");
        xmlElement.addElement(child1);
        xmlElement.addElement(child2);
        assertEquals(2, xmlElement.count());
    }

    @Test
    public void testGetElementByPath() {
        XmlElement child = new XmlElement("child");
        xmlElement.addElement(child);
        assertEquals(child, xmlElement.getElement("child"));
    }

    @Test
    public void testGetElementByIndex() {
        XmlElement child = new XmlElement("child");
        xmlElement.addElement(child);
        assertEquals(child, xmlElement.getElement(0));
    }

    @Test
    public void testAddSubElementByPath() {
        XmlElement child = xmlElement.addSubElement("child.subchild");
        assertEquals("subchild", child.getName());
        assertEquals("child", child.getParent().getName());
    }

    @Test
    public void testAddSubElementByElement() {
        XmlElement child = new XmlElement("child");
        xmlElement.addSubElement(child);
        assertEquals(child, xmlElement.getElement(0));
    }

    @Test
    public void testAddSubElementByNameAndData() {
        XmlElement child = xmlElement.addSubElement("child", "data");
        assertEquals("child", child.getName());
        assertEquals("data", child.getData());
    }

    @Test
    public void testSetParent() {
        XmlElement child = new XmlElement("child");
        child.setParent(xmlElement);
        assertEquals(xmlElement, child.getParent());
    }

    @Test
    public void testGetParent() {
        XmlElement child = new XmlElement("child");
        child.setParent(xmlElement);
        assertEquals(xmlElement, child.getParent());
    }

    @Test
    public void testSetData() {
        xmlElement.setData("data");
        assertEquals("data", xmlElement.getData());
    }

    @Test
    public void testGetData() {
        xmlElement.setData("data");
        assertEquals("data", xmlElement.getData());
    }

    @Test
    public void testGetName() {
        assertEquals("root", xmlElement.getName());
    }

    @Test
    public void testSetName() {
        xmlElement.setName("newName");
        assertEquals("newName", xmlElement.getName());
    }

    @Test
    public void testClone() {
        XmlElement clone = (XmlElement) xmlElement.clone();
        assertNotSame(xmlElement, clone);
        assertEquals(xmlElement.getName(), clone.getName());
        assertEquals(xmlElement.getData(), clone.getData());
        assertEquals(xmlElement.getAttributes(), clone.getAttributes());
    }

    @Test
    public void testEquals() {
        XmlElement other = new XmlElement("root");
        assertTrue(xmlElement.equals(other));
    }

    @Test
    public void testHashCode() {
        XmlElement other = new XmlElement("root");
        assertEquals(xmlElement.hashCode(), other.hashCode());
    }
}