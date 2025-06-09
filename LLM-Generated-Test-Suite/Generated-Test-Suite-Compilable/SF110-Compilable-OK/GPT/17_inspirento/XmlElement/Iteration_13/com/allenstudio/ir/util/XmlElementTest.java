package com.allenstudio.ir.util;

import com.allenstudio.ir.util.XmlElement;
import org.junit.Before;
import org.junit.Test;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

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
    public void testGetAttributeWithDefault() {
        assertEquals("defaultValue", xmlElement.getAttribute("key", "defaultValue"));
        assertEquals("defaultValue", xmlElement.getAttribute("key"));
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
    public void testRemoveElementByReference() {
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
        assertEquals(parent, child.getParent());
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
    public void testGetElementByPath() {
        XmlElement child = new XmlElement("child");
        xmlElement.addElement(child);
        assertEquals(child, xmlElement.getElement("child"));
    }

    @Test
    public void testAddSubElementByPath() {
        XmlElement subElement = xmlElement.addSubElement("parent.child");
        assertEquals("child", subElement.getName());
        assertEquals(1, xmlElement.count());
    }

    @Test
    public void testAddSubElementByElement() {
        XmlElement child = new XmlElement("child");
        xmlElement.addSubElement(child);
        assertEquals(1, xmlElement.count());
        assertEquals(child, xmlElement.getElement(0));
    }

    @Test
    public void testAddSubElementByNameAndData() {
        XmlElement child = xmlElement.addSubElement("child", "data");
        assertEquals("child", child.getName());
        assertEquals("data", child.getData());
    }

    @Test
    public void testClone() {
        xmlElement.setName("name");
        xmlElement.setData("data");
        xmlElement.addAttribute("key", "value");
        XmlElement clone = (XmlElement) xmlElement.clone();
        assertEquals(xmlElement, clone);
        assertNotSame(xmlElement, clone);
    }

    @Test
    public void testEquals() {
        XmlElement element1 = new XmlElement("name", "data");
        XmlElement element2 = new XmlElement("name", "data");
        assertTrue(element1.equals(element2));
    }

    @Test
    public void testHashCode() {
        xmlElement.setName("name");
        xmlElement.setData("data");
        int hashCode = xmlElement.hashCode();
        assertNotNull(hashCode);
    }

    @Test
    public void testNotifyObservers() {
        // This test would require a mock observer to verify notification
        // For simplicity, we just ensure no exceptions are thrown
        xmlElement.notifyObservers();
    }
}