package com.lts.swing.combobox;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import javax.swing.ComboBoxModel;
import com.lts.swing.combobox.SimpleComboBox;
import com.lts.swing.combobox.SimpleComboBoxModel;

public class SimpleComboBoxTest {

    private SimpleComboBox simpleComboBox;
    private SimpleComboBoxModel mockModel;

    @Before
    public void setUp() {
        mockModel = Mockito.mock(SimpleComboBoxModel.class);
        simpleComboBox = new SimpleComboBox(mockModel);
    }

    @Test
    public void testConstructor() {
        assertNotNull("SimpleComboBox should be instantiated", simpleComboBox);
    }

    @Test
    public void testSetModelWithSimpleComboBoxModel() {
        simpleComboBox.setModel(mockModel);
        assertEquals("Model should be set correctly", mockModel, simpleComboBox.getModel());
    }

    @Test
    public void testSetModelWithNonSimpleComboBoxModel() {
        ComboBoxModel nonSimpleModel = Mockito.mock(ComboBoxModel.class);
        simpleComboBox.setModel(nonSimpleModel);
        assertEquals("Model should be set correctly", nonSimpleModel, simpleComboBox.getModel());
    }

    @Test
    public void testGetSelectedValue() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn("TestValue");
        assertEquals("Should return the selected value", "TestValue", simpleComboBox.getSelectedValue());
    }

    @Test
    public void testSetSelectedValue() {
        simpleComboBox.setSelectedValue("NewValue");
        Mockito.verify(mockModel).setSelectedValue("NewValue");
    }

    @Test
    public void testGetSelectedIntWithInteger() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn(5);
        assertEquals("Should return the selected integer value", 5, simpleComboBox.getSelectedInt());
    }

    @Test
    public void testGetSelectedIntWithLong() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn(5L);
        assertEquals("Should return the selected long value as int", 5, simpleComboBox.getSelectedInt());
    }

    @Test
    public void testGetSelectedIntWithShort() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn((short) 5);
        assertEquals("Should return the selected short value as int", 5, simpleComboBox.getSelectedInt());
    }

    @Test
    public void testGetSelectedIntWithByte() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn((byte) 5);
        assertEquals("Should return the selected byte value as int", 5, simpleComboBox.getSelectedInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSelectedIntWithInvalidType() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn("InvalidType");
        simpleComboBox.getSelectedInt();
    }

    @Test
    public void testGetSelectedLongWithInteger() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn(5);
        assertEquals("Should return the selected integer value as long", 5L, simpleComboBox.getSelectedLong());
    }

    @Test
    public void testGetSelectedLongWithLong() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn(5L);
        assertEquals("Should return the selected long value", 5L, simpleComboBox.getSelectedLong());
    }

    @Test
    public void testGetSelectedLongWithShort() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn((short) 5);
        assertEquals("Should return the selected short value as long", 5L, simpleComboBox.getSelectedLong());
    }

    @Test
    public void testGetSelectedLongWithByte() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn((byte) 5);
        assertEquals("Should return the selected byte value as long", 5L, simpleComboBox.getSelectedLong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSelectedLongWithInvalidType() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn("InvalidType");
        simpleComboBox.getSelectedLong();
    }
}