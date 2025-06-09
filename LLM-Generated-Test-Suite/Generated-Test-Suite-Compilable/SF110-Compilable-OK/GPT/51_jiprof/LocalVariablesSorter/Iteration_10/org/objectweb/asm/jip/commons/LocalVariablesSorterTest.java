package org.objectweb.asm.jip.commons;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.objectweb.asm.jip.MethodVisitor;
import org.objectweb.asm.jip.Type;
import org.objectweb.asm.jip.Label;
import org.objectweb.asm.jip.Opcodes;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LocalVariablesSorterTest {

    private LocalVariablesSorter localVariablesSorter;
    private MethodVisitor mockMethodVisitor;

    @Before
    public void setUp() {
        mockMethodVisitor = mock(MethodVisitor.class);
        localVariablesSorter = new LocalVariablesSorter(Opcodes.ACC_PUBLIC, "(I)V", mockMethodVisitor);
    }

    @Test
    public void testConstructorInitializesFieldsCorrectly() {
        assertEquals(1, localVariablesSorter.firstLocal);
        assertEquals(1, localVariablesSorter.nextLocal);
    }

    @Test
    public void testVisitVarInsnRemapsCorrectly() {
        localVariablesSorter.visitVarInsn(Opcodes.ILOAD, 1);
        verify(mockMethodVisitor).visitVarInsn(eq(Opcodes.ILOAD), anyInt());
    }

    @Test
    public void testVisitIincInsnRemapsCorrectly() {
        localVariablesSorter.visitIincInsn(1, 5);
        verify(mockMethodVisitor).visitIincInsn(anyInt(), eq(5));
    }

    @Test
    public void testVisitMaxsUpdatesMaxLocals() {
        localVariablesSorter.visitMaxs(10, 5);
        verify(mockMethodVisitor).visitMaxs(10, localVariablesSorter.nextLocal);
    }

    @Test
    public void testVisitLocalVariableRemapsCorrectly() {
        Label start = new Label();
        Label end = new Label();
        localVariablesSorter.visitLocalVariable("var", "I", null, start, end, 1);
        verify(mockMethodVisitor).visitLocalVariable(eq("var"), eq("I"), isNull(), eq(start), eq(end), anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testVisitFrameThrowsExceptionForInvalidType() {
        localVariablesSorter.visitFrame(0, 0, new Object[0], 0, new Object[0]);
    }

    @Test
    public void testVisitFrameHandlesExpandedFrames() {
        Object[] locals = new Object[]{Opcodes.INTEGER};
        Object[] stack = new Object[]{};
        localVariablesSorter.visitFrame(-1, 1, locals, 0, stack);
        verify(mockMethodVisitor).visitFrame(eq(-1), anyInt(), any(Object[].class), eq(0), eq(stack));
    }

    @Test
    public void testNewLocalIncrementsNextLocal() {
        int initialNextLocal = localVariablesSorter.nextLocal;
        Type intType = Type.INT_TYPE;
        int newLocal = localVariablesSorter.newLocal(intType);
        assertEquals(initialNextLocal, newLocal);
        assertEquals(initialNextLocal + intType.getSize(), localVariablesSorter.nextLocal);
    }
}