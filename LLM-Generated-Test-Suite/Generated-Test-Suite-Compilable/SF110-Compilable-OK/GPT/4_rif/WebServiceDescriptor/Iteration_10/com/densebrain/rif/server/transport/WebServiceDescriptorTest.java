package com.densebrain.rif.server.transport;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WebServiceDescriptorTest {

    private WebServiceDescriptor descriptor;
    private Class<?> serviceClass;
    private String targetNamespace;
    private String typesNamespace;

    @Before
    public void setUp() {
        serviceClass = String.class;
        targetNamespace = "http://example.com/target";
        typesNamespace = "http://example.com/types";
        descriptor = new WebServiceDescriptor(serviceClass, targetNamespace, typesNamespace);
    }

    @Test
    public void testConstructor() {
        assertEquals(serviceClass, descriptor.getServiceClazz());
        assertEquals(targetNamespace, descriptor.getTargetNamespace());
        assertEquals(typesNamespace, descriptor.getTypesNamespace());
    }

    @Test
    public void testGetServiceClazz() {
        assertEquals(serviceClass, descriptor.getServiceClazz());
    }

    @Test
    public void testSetServiceClazz() {
        Class<?> newServiceClass = Integer.class;
        descriptor.setServiceClazz(newServiceClass);
        assertEquals(newServiceClass, descriptor.getServiceClazz());
    }

    @Test
    public void testGetTargetNamespace() {
        assertEquals(targetNamespace, descriptor.getTargetNamespace());
    }

    @Test
    public void testSetTargetNamespace() {
        String newTargetNamespace = "http://example.com/newTarget";
        descriptor.setTargetNamespace(newTargetNamespace);
        assertEquals(newTargetNamespace, descriptor.getTargetNamespace());
    }

    @Test
    public void testGetTypesNamespace() {
        assertEquals(typesNamespace, descriptor.getTypesNamespace());
    }

    @Test
    public void testSetTypesNamespace() {
        String newTypesNamespace = "http://example.com/newTypes";
        descriptor.setTypesNamespace(newTypesNamespace);
        assertEquals(newTypesNamespace, descriptor.getTypesNamespace());
    }

    @Test
    public void testHashCode() {
        int expectedHashCode = 31 + (serviceClass == null ? 0 : serviceClass.hashCode());
        assertEquals(expectedHashCode, descriptor.hashCode());
    }

    @Test
    public void testEqualsSameObject() {
        assertTrue(descriptor.equals(descriptor));
    }

    @Test
    public void testEqualsNull() {
        assertFalse(descriptor.equals(null));
    }

    @Test
    public void testEqualsDifferentClass() {
        assertFalse(descriptor.equals("A String"));
    }

    @Test
    public void testEqualsDifferentServiceClazz() {
        WebServiceDescriptor other = new WebServiceDescriptor(Integer.class, targetNamespace, typesNamespace);
        assertFalse(descriptor.equals(other));
    }

    @Test
    public void testEqualsSameServiceClazz() {
        WebServiceDescriptor other = new WebServiceDescriptor(serviceClass, targetNamespace, typesNamespace);
        assertTrue(descriptor.equals(other));
    }

    @Test
    public void testEqualsBothServiceClazzNull() {
        WebServiceDescriptor descriptor1 = new WebServiceDescriptor(null, targetNamespace, typesNamespace);
        WebServiceDescriptor descriptor2 = new WebServiceDescriptor(null, targetNamespace, typesNamespace);
        assertTrue(descriptor1.equals(descriptor2));
    }

    @Test
    public void testEqualsOneServiceClazzNull() {
        WebServiceDescriptor descriptor1 = new WebServiceDescriptor(null, targetNamespace, typesNamespace);
        WebServiceDescriptor descriptor2 = new WebServiceDescriptor(serviceClass, targetNamespace, typesNamespace);
        assertFalse(descriptor1.equals(descriptor2));
    }
}