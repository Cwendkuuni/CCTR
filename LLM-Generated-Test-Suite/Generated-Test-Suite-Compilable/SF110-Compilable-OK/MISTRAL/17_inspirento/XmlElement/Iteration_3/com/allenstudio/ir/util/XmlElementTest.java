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
    public void testConstructor() {
        XmlElement element = new XmlElement();
        assertNotNull(element.getAttributes());
        assertNotNull(element.getElements());

        element = new XmlElement("name");
        assertEquals("name", element.getName());
        assertEquals("", element.getData());
        assertNotNull(element.getAttributes());
        assertNotNull(element.getElements());

        Hashtable<String, String> attributes = new Hashtable<>();
        attributes.put("key", "value");
        element = new XmlElement("name", attributes);
        assertEquals("name", element.getName());
        assertEquals("value", element.getAttribute("key"));
        assertNotNull(element.getElements());

        element = new XmlElement("name", "data");
        assertEquals("name", element.getName());
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
        assertNull(xmlElement.getAttribute("nonexistent"));
    }

    @Test
    public void testGetAttributeWithDefault() {
        assertNull(xmlElement.getAttribute("nonexistent"));
        assertEquals("default", xmlElement.getAttribute("nonexistent", "default"));
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
        assertEquals(2, ((Vector<String>) xmlElement.getAttributeNames()).size());
    }

    @Test
    public void testAddElement() {
        XmlElement child = new XmlElement("child");
        assertTrue(xmlElement.addElement(child));
        assertEquals(child, xmlElement.getElement(0));
    }

    @Test
    public void testRemoveElement() {
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
        XmlElement parent = new XmlElement("parent");
        parent.addElement(xmlElement);
        xmlElement.removeFromParent();
        assertNull(xmlElement.getParent());
        assertEquals(0, parent.count());
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
        assertNull(xmlElement.getElement("nonexistent"));
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
    public void testAddSubElement() {
        XmlElement child = new XmlElement("child");
        xmlElement.addSubElement(child);
        assertEquals(child, xmlElement.getElement(0));
    }

    @Test
    public void testAddSubElementWithData() {
        XmlElement child = xmlElement.addSubElement("child", "data");
        assertEquals("child", child.getName());
        assertEquals("data", child.getData());
    }

    @Test
    public void testSetParent() {
        XmlElement parent = new XmlElement("parent");
        xmlElement.setParent(parent);
        assertEquals(parent, xmlElement.getParent());
    }

    @Test
    public void testGetParent() {
        XmlElement parent = new XmlElement("parent");
        xmlElement.setParent(parent);
        assertEquals(parent, xmlElement.getParent());
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
        xmlElement.addAttribute("key", "value");
        XmlElement clone = (XmlElement) xmlElement.clone();
        assertEquals(xmlElement.getName(), clone.getName());
        assertEquals(xmlElement.getData(), clone.getData());
        assertEquals(xmlElement.getAttribute("key"), clone.getAttribute("key"));
    }

    @Test
    public void testEquals() {
        XmlElement element1 = new XmlElement("name", "data");
        XmlElement element2 = new XmlElement("name", "data");
        assertTrue(element1.equals(element2));

        element2.setData("differentData");
        assertFalse(element1.equals(element2));
    }

    @Test
    public void testHashCode() {
        XmlElement element1 = new XmlElement("name", "data");
        XmlElement element2 = new XmlElement("name", "data");
        assertEquals(element1.hashCode(), element2.hashCode());

        element2.setData("differentData");
        assertNotEquals(element1.hashCode(), element2.hashCode());
    }
}