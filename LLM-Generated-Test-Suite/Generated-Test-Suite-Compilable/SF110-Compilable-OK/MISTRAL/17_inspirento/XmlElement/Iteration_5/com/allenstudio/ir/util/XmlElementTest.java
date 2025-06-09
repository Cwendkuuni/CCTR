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
        assertEquals("root", xmlElement.getName());
        assertNotNull(xmlElement.getAttributes());
        assertNotNull(xmlElement.getElements());
        assertEquals("", xmlElement.getData());
    }

    @Test
    public void testConstructorWithNameAndAttributes() {
        Hashtable<String, String> attributes = new Hashtable<>();
        attributes.put("attr1", "value1");
        XmlElement element = new XmlElement("root", attributes);
        assertEquals("root", element.getName());
        assertEquals("value1", element.getAttribute("attr1"));
        assertNotNull(element.getElements());
    }

    @Test
    public void testConstructorWithNameAndData() {
        XmlElement element = new XmlElement("root", "data");
        assertEquals("root", element.getName());
        assertEquals("data", element.getData());
        assertNotNull(element.getAttributes());
        assertNotNull(element.getElements());
    }

    @Test
    public void testAddAttribute() {
        xmlElement.addAttribute("attr1", "value1");
        assertEquals("value1", xmlElement.getAttribute("attr1"));
    }

    @Test
    public void testGetAttribute() {
        xmlElement.addAttribute("attr1", "value1");
        assertEquals("value1", xmlElement.getAttribute("attr1"));
    }

    @Test
    public void testGetAttributeWithDefaultValue() {
        assertEquals("defaultValue", xmlElement.getAttribute("attr1", "defaultValue"));
        assertEquals("defaultValue", xmlElement.getAttribute("attr1"));
    }

    @Test
    public void testGetAttributes() {
        Hashtable<String, String> attributes = new Hashtable<>();
        attributes.put("attr1", "value1");
        xmlElement.setAttributes(attributes);
        assertEquals(attributes, xmlElement.getAttributes());
    }

    @Test
    public void testSetAttributes() {
        Hashtable<String, String> attributes = new Hashtable<>();
        attributes.put("attr1", "value1");
        xmlElement.setAttributes(attributes);
        assertEquals("value1", xmlElement.getAttribute("attr1"));
    }

    @Test
    public void testGetAttributeNames() {
        xmlElement.addAttribute("attr1", "value1");
        xmlElement.addAttribute("attr2", "value2");
        assertEquals(2, ((Vector<String>) xmlElement.getAttributeNames()).size());
    }

    @Test
    public void testAddElement() {
        XmlElement child = new XmlElement("child");
        assertTrue(xmlElement.addElement(child));
        assertEquals(1, xmlElement.count());
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
        assertEquals(1, xmlElement.count());
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
    public void testAddSubElement() {
        XmlElement child = new XmlElement("child");
        assertEquals(child, xmlElement.addSubElement(child));
        assertEquals(1, xmlElement.count());
    }

    @Test
    public void testAddSubElementWithNameAndData() {
        XmlElement child = xmlElement.addSubElement("child", "data");
        assertEquals("child", child.getName());
        assertEquals("data", child.getData());
        assertEquals(1, xmlElement.count());
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
        xmlElement.addAttribute("attr1", "value1");
        XmlElement child = new XmlElement("child");
        xmlElement.addElement(child);
        XmlElement clone = (XmlElement) xmlElement.clone();
        assertEquals(xmlElement.getName(), clone.getName());
        assertEquals(xmlElement.getAttribute("attr1"), clone.getAttribute("attr1"));
        assertEquals(xmlElement.count(), clone.count());
        assertEquals(xmlElement.getElement(0).getName(), clone.getElement(0).getName());
    }

    @Test
    public void testEquals() {
        XmlElement element1 = new XmlElement("root");
        XmlElement element2 = new XmlElement("root");
        assertTrue(element1.equals(element2));
    }

    @Test
    public void testHashCode() {
        XmlElement element1 = new XmlElement("root");
        XmlElement element2 = new XmlElement("root");
        assertEquals(element1.hashCode(), element2.hashCode());
    }

    @Test
    public void testNotifyObservers() {
        xmlElement.addObserver((o, arg) -> {
            // Observer logic
        });
        xmlElement.notifyObservers();
        // Verify observer notification
    }
}