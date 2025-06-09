package com.jstevh.viewer;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.swing.*;
import java.lang.reflect.*;
import java.util.*;
import com.jstevh.tools.*;

public class ClassInfoTest {

    private ClassInfo classInfo;
    private DirManager mockDirManager;

    @Before
    public void setUp() throws Exception {
        ClassInfo.debug = false;
        mockDirManager = new DirManager(); // Assuming DirManager has a default constructor
        classInfo = new ClassInfo("java.lang.String", mockDirManager);
    }

    @Test
    public void testIsAbstract() {
        assertFalse(classInfo.isAbstract());
    }

    @Test
    public void testIsInterface() {
        assertFalse(classInfo.isInterface());
    }

    @Test
    public void testGetClassName() {
        assertEquals("java.lang.String", classInfo.getClassName());
    }

    @Test
    public void testGetClassPackage() {
        assertEquals("java.lang", classInfo.getClassPackage());
    }

    @Test
    public void testGetSuperClassName() {
        assertEquals("java.lang.Object", classInfo.getSuperClassName());
    }

    @Test
    public void testPrintFields() {
        String[] fields = classInfo.printFields();
        assertNotNull(fields);
        assertTrue(fields.length > 0);
    }

    @Test
    public void testPrintMethods() {
        String[] methods = classInfo.printMethods();
        assertNotNull(methods);
        assertTrue(methods.length > 0);
    }

    @Test
    public void testPrintMethodsWithParam() {
        String[] methods = classInfo.printMethods(ClassInfo.NO_OBJECT_METHODS);
        assertNotNull(methods);
        assertTrue(methods.length > 0);
    }

    @Test
    public void testPrintConstructors() {
        String[] constructors = classInfo.printConstructors();
        assertNotNull(constructors);
        assertTrue(constructors.length > 0);
    }

    @Test
    public void testPrintInterfaces() {
        String[] interfaces = classInfo.printInterfaces();
        assertNotNull(interfaces);
        assertTrue(interfaces.length > 0);
    }

    @Test
    public void testSrchMethods() {
        String[] methods = classInfo.srchMethods("toString");
        assertNotNull(methods);
        assertTrue(methods.length > 0);
    }

    @Test
    public void testSrchMethodsWithIndex() {
        String[] methods = classInfo.srchMethods("toString", new String[]{"toString"});
        assertNotNull(methods);
        assertTrue(methods.length > 0);
    }

    @Test
    public void testGetFoundMethod() {
        classInfo.srchMethods("toString");
        MethodData methodData = classInfo.getFoundMethod(0);
        assertNotNull(methodData);
        assertEquals("toString", methodData.getMethName());
    }

    @Test(expected = ClassNotFoundException.class)
    public void testClassInfoWithInvalidClassName() throws ClassNotFoundException {
        new ClassInfo("InvalidClassName");
    }

    @Test
    public void testMainMethod() throws Exception {
        String[] args = {"java.lang.String"};
        ClassInfo.main(args);
        // Add assertions based on expected output if necessary
    }
}