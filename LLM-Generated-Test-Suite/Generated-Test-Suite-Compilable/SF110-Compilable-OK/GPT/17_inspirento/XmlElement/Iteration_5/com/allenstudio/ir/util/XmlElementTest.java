package com.allenstudio.ir.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

public class XmlElementTest {

    private XmlElement element;

    @Before
    public void setUp() {
        element = new XmlElement();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(element.getAttributes());
        assertNotNull(element.getElements());
        assertNull(element.getName());
        assertNull(element.getData());
    }

    @Test
    public void testConstructorWithName() {
        XmlElement elementWithName = new XmlElement("testName");
        assertEquals("testName", elementWithName.getName());
        assertEquals("", elementWithName.getData());
    }

    @Test
    public void testConstructorWithNameAndAttributes() {
        Hashtable<String, String> attributes = new Hashtable<>();
        attributes.put("key", "value");
        XmlElement elementWithAttributes = new XmlElement("testName", attributes);
        assertEquals("testName", elementWithAttributes.getName());
        assertEquals(attributes, elementWithAttributes.getAttributes());
    }

    @Test
    public void testConstructorWithNameAndData() {
        XmlElement elementWithData = new XmlElement("testName", "testData");
        assertEquals("testName", elementWithData.getName());
        assertEquals("testData", elementWithData.getData());
    }

    @Test
    public void testAddAttribute() {
        element.addAttribute("key", "value");
        assertEquals("value", element.getAttribute("key"));
    }

    @Test
    public void testGetAttributeWithDefault() {
        assertEquals("defaultValue", element.getAttribute("nonExistentKey", "defaultValue"));
        assertEquals("defaultValue", element.getAttribute("nonExistentKey"));
    }

    @Test
    public void testSetAttributes() {
        Hashtable<String, String> attributes = new Hashtable<>();
        attributes.put("key", "value");
        element.setAttributes(attributes);
        assertEquals(attributes, element.getAttributes());
    }

    @Test
    public void testGetAttributeNames() {
        element.addAttribute("key1", "value1");
        element.addAttribute("key2", "value2");
        Enumeration<String> attributeNames = element.getAttributeNames();
        assertTrue(attributeNames.hasMoreElements());
    }

    @Test
    public void testAddElement() {
        XmlElement child = new XmlElement("child");
        element.addElement(child);
        assertEquals(1, element.count());
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
        XmlElement child1 = new XmlElement("child1");
        XmlElement child2 = new XmlElement("child2");
        element.addElement(child1);
        element.addElement(child2);
        element.removeAllElements();
        assertEquals(0, element.count());
    }

    @Test
    public void testRemoveFromParent() {
        XmlElement parent = new XmlElement("parent");
        XmlElement child = new XmlElement("child");
        parent.addElement(child);
        child.removeFromParent();
        assertNull(child.getParent());
        assertEquals(0, parent.count());
    }

    @Test
    public void testAppend() {
        XmlElement parent = new XmlElement("parent");
        XmlElement child = new XmlElement("child");
        parent.append(child);
        assertEquals(1, parent.count());
        assertEquals(child, parent.getElement(0));
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
    public void testGetElements() {
        XmlElement child = new XmlElement("child");
        element.addElement(child);
        List<XmlElement> elements = element.getElements();
        assertEquals(1, elements.size());
        assertEquals(child, elements.get(0));
    }

    @Test
    public void testCount() {
        XmlElement child = new XmlElement("child");
        element.addElement(child);
        assertEquals(1, element.count());
    }

    @Test
    public void testGetElementByPath() {
        XmlElement child = new XmlElement("child");
        element.addElement(child);
        assertEquals(child, element.getElement("child"));
    }

    @Test
    public void testAddSubElementByPath() {
        XmlElement subElement = element.addSubElement("parent.child");
        assertNotNull(subElement);
        assertEquals("child", subElement.getName());
    }

    @Test
    public void testAddSubElementByElement() {
        XmlElement child = new XmlElement("child");
        element.addSubElement(child);
        assertEquals(1, element.count());
        assertEquals(child, element.getElement(0));
    }

    @Test
    public void testAddSubElementByNameAndData() {
        XmlElement subElement = element.addSubElement("child", "data");
        assertEquals("child", subElement.getName());
        assertEquals("data", subElement.getData());
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
        element.setName("name");
        assertEquals("name", element.getName());
    }

    @Test
    public void testClone() {
        XmlElement clone = (XmlElement) element.clone();
        assertNotSame(element, clone);
        assertEquals(element, clone);
    }

    @Test
    public void testEquals() {
        XmlElement anotherElement = new XmlElement();
        assertTrue(element.equals(anotherElement));
        anotherElement.setName("name");
        assertFalse(element.equals(anotherElement));
    }

    @Test
    public void testHashCode() {
        XmlElement anotherElement = new XmlElement();
        assertEquals(element.hashCode(), anotherElement.hashCode());
    }
}