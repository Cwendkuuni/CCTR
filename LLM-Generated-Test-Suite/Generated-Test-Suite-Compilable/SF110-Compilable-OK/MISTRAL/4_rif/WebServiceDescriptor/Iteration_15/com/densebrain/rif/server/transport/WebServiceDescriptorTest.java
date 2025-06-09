package com.densebrain.rif.server.transport;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class WebServiceDescriptorTest {

    private WebServiceDescriptor descriptor;
    private Class serviceClazz;
    private String targetNamespace;
    private String typesNamespace;

    @Before
    public void setUp() {
        serviceClazz = String.class;
        targetNamespace = "http://example.com/target";
        typesNamespace = "http://example.com/types";
        descriptor = new WebServiceDescriptor(serviceClazz, targetNamespace, typesNamespace);
    }

    @Test
    public void testConstructor() {
        assertEquals(serviceClazz, descriptor.getServiceClazz());
        assertEquals(targetNamespace, descriptor.getTargetNamespace());
        assertEquals(typesNamespace, descriptor.getTypesNamespace());
    }

    @Test
    public void testGetServiceClazz() {
        assertEquals(serviceClazz, descriptor.getServiceClazz());
    }

    @Test
    public void testSetServiceClazz() {
        Class newServiceClazz = Integer.class;
        descriptor.setServiceClazz(newServiceClazz);
        assertEquals(newServiceClazz, descriptor.getServiceClazz());
    }

    @Test
    public void testGetTargetNamespace() {
        assertEquals(targetNamespace, descriptor.getTargetNamespace());
    }

    @Test
    public void testSetTargetNamespace() {
        String newTargetNamespace = "http://example.com/newtarget";
        descriptor.setTargetNamespace(newTargetNamespace);
        assertEquals(newTargetNamespace, descriptor.getTargetNamespace());
    }

    @Test
    public void testGetTypesNamespace() {
        assertEquals(typesNamespace, descriptor.getTypesNamespace());
    }

    @Test
    public void testSetTypesNamespace() {
        String newTypesNamespace = "http://example.com/newtypes";
        descriptor.setTypesNamespace(newTypesNamespace);
        assertEquals(newTypesNamespace, descriptor.getTypesNamespace());
    }

    @Test
    public void testHashCode() {
        WebServiceDescriptor descriptor1 = new WebServiceDescriptor(serviceClazz, targetNamespace, typesNamespace);
        WebServiceDescriptor descriptor2 = new WebServiceDescriptor(serviceClazz, targetNamespace, typesNamespace);
        assertEquals(descriptor1.hashCode(), descriptor2.hashCode());
    }

    @Test
    public void testEquals() {
        WebServiceDescriptor descriptor1 = new WebServiceDescriptor(serviceClazz, targetNamespace, typesNamespace);
        WebServiceDescriptor descriptor2 = new WebServiceDescriptor(serviceClazz, targetNamespace, typesNamespace);
        assertTrue(descriptor1.equals(descriptor2));
    }

    @Test
    public void testEqualsWithDifferentServiceClazz() {
        WebServiceDescriptor descriptor1 = new WebServiceDescriptor(serviceClazz, targetNamespace, typesNamespace);
        WebServiceDescriptor descriptor2 = new WebServiceDescriptor(Integer.class, targetNamespace, typesNamespace);
        assertFalse(descriptor1.equals(descriptor2));
    }

    @Test
    public void testEqualsWithNull() {
        assertFalse(descriptor.equals(null));
    }

    @Test
    public void testEqualsWithDifferentClass() {
        assertFalse(descriptor.equals(new Object()));
    }
}