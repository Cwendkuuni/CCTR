package com.allenstudio.ir.util;

import com.allenstudio.ir.util.XmlElement;
import org.junit.Before;
import org.junit.Test;

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
    public void testDefaultConstructor() {
        XmlElement element = new XmlElement();
        assertNotNull(element.getAttributes());
        assertNotNull(element.getElements());
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
        assertEquals("defaultValue", xmlElement.getAttribute("nonExistent", "defaultValue"));
        assertEquals("defaultValue", xmlElement.getAttribute("nonExistent"));
    }

    @Test
    public void testSetAttributes() {
        Hashtable<String, String> attributes = new Hashtable<>();
        attributes.put("key", "value");
        xmlElement.setAttributes(attributes);
        assertEquals(attributes, xmlElement.getAttributes());
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
        assertEquals(0, xmlElement.count());
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
        assertEquals(childElement, xmlElement.getElement(1));
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
        assertEquals(0, xmlElement.count());
        xmlElement.addElement(childElement);
        assertEquals(1, xmlElement.count());
    }

    @Test
    public void testGetElementByPath() {
        XmlElement subChild = new XmlElement("subChild");
        childElement.addElement(subChild);
        xmlElement.addElement(childElement);
        assertEquals(subChild, xmlElement.getElement("child.subChild"));
    }

    @Test
    public void testAddSubElementByPath() {
        XmlElement subElement = xmlElement.addSubElement("child.subChild");
        assertEquals(subElement, xmlElement.getElement("child.subChild"));
    }

    @Test
    public void testAddSubElementByElement() {
        xmlElement.addSubElement(childElement);
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
        xmlElement.setData("newData");
        assertEquals("newData", xmlElement.getData());
    }

    @Test
    public void testSetName() {
        xmlElement.setName("newName");
        assertEquals("newName", xmlElement.getName());
    }

    @Test
    public void testClone() {
        xmlElement.addElement(childElement);
        XmlElement clone = (XmlElement) xmlElement.clone();
        assertEquals(xmlElement, clone);
        assertNotSame(xmlElement, clone);
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

    @Test
    public void testNotifyObservers() {
        // This test would require a mock observer to verify notification
        // Since Observable is deprecated, this is left as a placeholder
    }
}