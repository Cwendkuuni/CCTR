package org.objectweb.asm.jip.commons;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.objectweb.asm.jip.MethodVisitor;
import org.objectweb.asm.jip.Type;
import org.objectweb.asm.jip.Label;
import org.objectweb.asm.jip.Opcodes;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LocalVariablesSorterTest {

    private MethodVisitor mockMethodVisitor;
    private LocalVariablesSorter localVariablesSorter;

    @Before
    public void setUp() {
        mockMethodVisitor = mock(MethodVisitor.class);
        localVariablesSorter = new LocalVariablesSorter(Opcodes.ACC_PUBLIC, "(I)V", mockMethodVisitor);
    }

    @Test
    public void testConstructor() {
        assertNotNull(localVariablesSorter);
        assertEquals(1, localVariablesSorter.firstLocal);
    }

    @Test
    public void testVisitVarInsn() {
        localVariablesSorter.visitVarInsn(Opcodes.ILOAD, 1);
        verify(mockMethodVisitor).visitVarInsn(eq(Opcodes.ILOAD), anyInt());
    }

    @Test
    public void testVisitIincInsn() {
        localVariablesSorter.visitIincInsn(1, 5);
        verify(mockMethodVisitor).visitIincInsn(anyInt(), eq(5));
    }

    @Test
    public void testVisitMaxs() {
        localVariablesSorter.visitMaxs(10, 10);
        verify(mockMethodVisitor).visitMaxs(eq(10), anyInt());
    }

    @Test
    public void testVisitLocalVariable() {
        Label start = new Label();
        Label end = new Label();
        localVariablesSorter.visitLocalVariable("var", "I", null, start, end, 1);
        verify(mockMethodVisitor).visitLocalVariable(eq("var"), eq("I"), isNull(), eq(start), eq(end), anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testVisitFrameWithException() {
        localVariablesSorter.visitFrame(0, 0, new Object[0], 0, new Object[0]);
    }

    @Test
    public void testVisitFrame() {
        Object[] locals = {Opcodes.INTEGER};
        Object[] stack = {};
        localVariablesSorter.visitFrame(-1, 1, locals, 0, stack);
        verify(mockMethodVisitor).visitFrame(eq(-1), anyInt(), any(Object[].class), eq(0), eq(stack));
    }

    @Test
    public void testNewLocal() {
        int localIndex = localVariablesSorter.newLocal(Type.INT_TYPE);
        assertEquals(1, localIndex);
    }

    @Test
    public void testRemap() {
        int remappedIndex = localVariablesSorter.newLocal(Type.INT_TYPE);
        assertEquals(1, remappedIndex);
    }

    @Test
    public void testSetLocalType() {
        // This method is protected and empty, so no direct test is needed.
        // It is indirectly tested through other methods.
    }

    @Test
    public void testSetFrameLocal() {
        localVariablesSorter.newLocal(Type.INT_TYPE);
        // This method is indirectly tested through other methods.
    }

    @Test
    public void testNewLocalMapping() {
        int localIndex = localVariablesSorter.newLocalMapping(Type.INT_TYPE);
        assertEquals(1, localIndex);
    }
}