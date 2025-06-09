package com.densebrain.rif.server.transport;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class WebServiceDescriptorTest {

    private WebServiceDescriptor descriptor;
    private Class<?> serviceClazz;
    private String targetNamespace;
    private String typesNamespace;

    @Before
    public void setUp() {
        serviceClazz = WebServiceDescriptorTest.class;
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
        Class<?> newServiceClazz = String.class;
        descriptor.setServiceClazz(newServiceClazz);
        assertEquals(newServiceClazz, descriptor.getServiceClazz());
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
        int expectedHashCode = 1;
        expectedHashCode = 31 * expectedHashCode + ((serviceClazz == null) ? 0 : serviceClazz.hashCode());
        assertEquals(expectedHashCode, descriptor.hashCode());
    }

    @Test
    public void testEquals() {
        WebServiceDescriptor other = new WebServiceDescriptor(serviceClazz, targetNamespace, typesNamespace);
        assertTrue(descriptor.equals(other));

        other.setServiceClazz(String.class);
        assertFalse(descriptor.equals(other));

        assertFalse(descriptor.equals(null));
        assertFalse(descriptor.equals(new Object()));
    }
}