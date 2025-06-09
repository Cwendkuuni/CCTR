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
        WebServiceDescriptor sameDescriptor = new WebServiceDescriptor(serviceClass, targetNamespace, typesNamespace);
        assertEquals(descriptor.hashCode(), sameDescriptor.hashCode());

        WebServiceDescriptor differentDescriptor = new WebServiceDescriptor(Integer.class, targetNamespace, typesNamespace);
        assertNotEquals(descriptor.hashCode(), differentDescriptor.hashCode());
    }

    @Test
    public void testEquals() {
        WebServiceDescriptor sameDescriptor = new WebServiceDescriptor(serviceClass, targetNamespace, typesNamespace);
        assertTrue(descriptor.equals(sameDescriptor));

        WebServiceDescriptor differentDescriptor = new WebServiceDescriptor(Integer.class, targetNamespace, typesNamespace);
        assertFalse(descriptor.equals(differentDescriptor));

        assertFalse(descriptor.equals(null));
        assertFalse(descriptor.equals(new Object()));
    }
}