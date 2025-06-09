package com.allenstudio.ir.util;

import com.allenstudio.ir.util.XmlElement;
import org.junit.Before;
import org.junit.Test;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

public class XmlElementTest {

    private XmlElement xmlElement;

    @Before
    public void setUp() {
        xmlElement = new XmlElement();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(xmlElement.getAttributes());
        assertNotNull(xmlElement.getElements());
        assertNull(xmlElement.getName());
        assertNull(xmlElement.getData());
    }

    @Test
    public void testConstructorWithName() {
        XmlElement element = new XmlElement("testName");
        assertEquals("testName", element.getName());
        assertEquals("", element.getData());
    }

    @Test
    public void testConstructorWithNameAndAttributes() {
        Hashtable<String, String> attributes = new Hashtable<>();
        attributes.put("key", "value");
        XmlElement element = new XmlElement("testName", attributes);
        assertEquals("testName", element.getName());
        assertEquals(attributes, element.getAttributes());
    }

    @Test
    public void testConstructorWithNameAndData() {
        XmlElement element = new XmlElement("testName", "testData");
        assertEquals("testName", element.getName());
        assertEquals("testData", element.getData());
    }

    @Test
    public void testAddAttribute() {
        xmlElement.addAttribute("key", "value");
        assertEquals("value", xmlElement.getAttribute("key"));
    }

    @Test
    public void testGetAttributeWithDefaultValue() {
        assertEquals("default", xmlElement.getAttribute("key", "default"));
        assertEquals("default", xmlElement.getAttribute("key"));
    }

    @Test
    public void testSetAttributes() {
        Hashtable<String, String> attributes = new Hashtable<>();
        attributes.put("key", "value");
        xmlElement.setAttributes(attributes);
        assertEquals(attributes, xmlElement.getAttributes());
    }

    @Test
    public void testGetAttributeNames() {
        xmlElement.addAttribute("key1", "value1");
        xmlElement.addAttribute("key2", "value2");
        Enumeration<String> attributeNames = xmlElement.getAttributeNames();
        assertTrue(attributeNames.hasMoreElements());
    }

    @Test
    public void testAddElement() {
        XmlElement child = new XmlElement("child");
        xmlElement.addElement(child);
        assertEquals(1, xmlElement.count());
        assertEquals(child, xmlElement.getElement(0));
    }

    @Test
    public void testRemoveElement() {
        XmlElement child = new XmlElement("child");
        xmlElement.addElement(child);
        xmlElement.removeElement(child);
        assertEquals(0, xmlElement.count());
    }

    @Test
    public void testRemoveElementByIndex() {
        XmlElement child = new XmlElement("child");
        xmlElement.addElement(child);
        xmlElement.removeElement(0);
        assertEquals(0, xmlElement.count());
    }

    @Test
    public void testRemoveAllElements() {
        xmlElement.addElement(new XmlElement("child1"));
        xmlElement.addElement(new XmlElement("child2"));
        xmlElement.removeAllElements();
        assertEquals(0, xmlElement.count());
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
        xmlElement.addElement(new XmlElement("child1"));
        xmlElement.addElement(new XmlElement("child2"));
        assertEquals(2, xmlElement.count());
    }

    @Test
    public void testGetElementByPath() {
        XmlElement child = new XmlElement("child");
        xmlElement.addElement(child);
        assertEquals(child, xmlElement.getElement("child"));
    }

    @Test
    public void testAddSubElementByPath() {
        XmlElement subElement = xmlElement.addSubElement("parent.child");
        assertNotNull(subElement);
        assertEquals("child", subElement.getName());
    }

    @Test
    public void testAddSubElement() {
        XmlElement child = new XmlElement("child");
        xmlElement.addSubElement(child);
        assertEquals(1, xmlElement.count());
        assertEquals(child, xmlElement.getElement(0));
    }

    @Test
    public void testAddSubElementWithNameAndData() {
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
    public void testSetData() {
        xmlElement.setData("data");
        assertEquals("data", xmlElement.getData());
    }

    @Test
    public void testSetName() {
        xmlElement.setName("name");
        assertEquals("name", xmlElement.getName());
    }

    @Test
    public void testClone() {
        xmlElement.setName("name");
        xmlElement.setData("data");
        XmlElement clone = (XmlElement) xmlElement.clone();
        assertEquals(xmlElement, clone);
    }

    @Test
    public void testEquals() {
        XmlElement element1 = new XmlElement("name", "data");
        XmlElement element2 = new XmlElement("name", "data");
        assertTrue(element1.equals(element2));
    }

    @Test
    public void testHashCode() {
        XmlElement element1 = new XmlElement("name", "data");
        XmlElement element2 = new XmlElement("name", "data");
        assertEquals(element1.hashCode(), element2.hashCode());
    }
}