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
    private XmlElement childElement;

    @Before
    public void setUp() {
        xmlElement = new XmlElement("root");
        childElement = new XmlElement("child");
    }

    @Test
    public void testAddAttribute() {
        assertNull(xmlElement.addAttribute("key", "value"));
        assertEquals("value", xmlElement.getAttribute("key"));
    }

    @Test
    public void testGetAttributeWithDefault() {
        assertEquals("default", xmlElement.getAttribute("nonexistent", "default"));
        assertEquals("default", xmlElement.getAttribute("nonexistent"));
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
        assertTrue(xmlElement.addElement(childElement));
        assertEquals(childElement, xmlElement.getElement(0));
    }

    @Test
    public void testRemoveElementByReference() {
        xmlElement.addElement(childElement);
        assertEquals(childElement, xmlElement.removeElement(childElement));
        assertNull(xmlElement.getElement(0));
    }

    @Test
    public void testRemoveElementByIndex() {
        xmlElement.addElement(childElement);
        assertEquals(childElement, xmlElement.removeElement(0));
        assertEquals(0, xmlElement.count());
    }

    @Test
    public void testRemoveAllElements() {
        xmlElement.addElement(childElement);
        xmlElement.removeAllElements();
        assertEquals(0, xmlElement.count());
    }

    @Test
    public void testRemoveFromParent() {
        xmlElement.addElement(childElement);
        childElement.removeFromParent();
        assertNull(childElement.getParent());
        assertEquals(0, xmlElement.count());
    }

    @Test
    public void testAppend() {
        XmlElement newParent = new XmlElement("newParent");
        newParent.append(childElement);
        assertEquals(newParent, childElement.getParent());
        assertEquals(childElement, newParent.getElement(0));
    }

    @Test
    public void testInsertElement() {
        XmlElement anotherChild = new XmlElement("anotherChild");
        xmlElement.addElement(childElement);
        xmlElement.insertElement(anotherChild, 0);
        assertEquals(anotherChild, xmlElement.getElement(0));
    }

    @Test
    public void testGetElements() {
        xmlElement.addElement(childElement);
        List<XmlElement> elements = xmlElement.getElements();
        assertEquals(1, elements.size());
        assertEquals(childElement, elements.get(0));
    }

    @Test
    public void testCount() {
        xmlElement.addElement(childElement);
        assertEquals(1, xmlElement.count());
    }

    @Test
    public void testGetElementByPath() {
        xmlElement.addElement(childElement);
        assertEquals(childElement, xmlElement.getElement("child"));
    }

    @Test
    public void testAddSubElementByPath() {
        XmlElement subElement = xmlElement.addSubElement("child.subChild");
        assertEquals("subChild", subElement.getName());
        assertEquals(subElement, xmlElement.getElement("child").getElement("subChild"));
    }

    @Test
    public void testAddSubElementByElement() {
        XmlElement subElement = xmlElement.addSubElement(childElement);
        assertEquals(childElement, subElement);
        assertEquals(childElement, xmlElement.getElement(0));
    }

    @Test
    public void testAddSubElementByNameAndData() {
        XmlElement subElement = xmlElement.addSubElement("child", "data");
        assertEquals("child", subElement.getName());
        assertEquals("data", subElement.getData());
    }

    @Test
    public void testSetParent() {
        childElement.setParent(xmlElement);
        assertEquals(xmlElement, childElement.getParent());
    }

    @Test
    public void testSetData() {
        xmlElement.setData("data");
        assertEquals("data", xmlElement.getData());
    }

    @Test
    public void testSetName() {
        xmlElement.setName("newName");
        assertEquals("newName", xmlElement.getName());
    }

    @Test
    public void testClone() {
        xmlElement.addElement(childElement);
        XmlElement clonedElement = (XmlElement) xmlElement.clone();
        assertEquals(xmlElement, clonedElement);
        assertNotSame(xmlElement, clonedElement);
    }

    @Test
    public void testEquals() {
        XmlElement anotherElement = new XmlElement("root");
        assertTrue(xmlElement.equals(anotherElement));
        anotherElement.addElement(new XmlElement("child"));
        assertFalse(xmlElement.equals(anotherElement));
    }

    @Test
    public void testHashCode() {
        XmlElement anotherElement = new XmlElement("root");
        assertEquals(xmlElement.hashCode(), anotherElement.hashCode());
    }
}