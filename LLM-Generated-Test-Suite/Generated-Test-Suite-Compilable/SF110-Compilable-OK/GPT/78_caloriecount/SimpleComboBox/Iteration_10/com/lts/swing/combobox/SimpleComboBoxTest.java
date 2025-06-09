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
    public void testSetModelWithSimpleComboBoxModel() {
        simpleComboBox.setModel(mockModel);
        assertEquals(mockModel, simpleComboBox.getModel());
    }

    @Test
    public void testSetModelWithNonSimpleComboBoxModel() {
        ComboBoxModel nonSimpleModel = Mockito.mock(ComboBoxModel.class);
        simpleComboBox.setModel(nonSimpleModel);
        assertEquals(nonSimpleModel, simpleComboBox.getModel());
    }

    @Test
    public void testGetSelectedValueWhenModelIsNull() {
        SimpleComboBox comboBox = new SimpleComboBox(null);
        assertNull(comboBox.getSelectedValue());
    }

    @Test
    public void testGetSelectedValue() {
        Object expectedValue = "Test Value";
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals(expectedValue, simpleComboBox.getSelectedValue());
    }

    @Test
    public void testSetSelectedValue() {
        Object value = "New Value";
        simpleComboBox.setSelectedValue(value);
        Mockito.verify(mockModel).setSelectedValue(value);
    }

    @Test
    public void testGetSelectedIntWithNullValue() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn(null);
        assertEquals(-1, simpleComboBox.getSelectedInt());
    }

    @Test
    public void testGetSelectedIntWithIntegerValue() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn(42);
        assertEquals(42, simpleComboBox.getSelectedInt());
    }

    @Test
    public void testGetSelectedIntWithLongValue() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn(42L);
        assertEquals(42, simpleComboBox.getSelectedInt());
    }

    @Test
    public void testGetSelectedIntWithShortValue() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn((short) 42);
        assertEquals(42, simpleComboBox.getSelectedInt());
    }

    @Test
    public void testGetSelectedIntWithByteValue() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn((byte) 42);
        assertEquals(42, simpleComboBox.getSelectedInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSelectedIntWithInvalidValue() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn("Invalid");
        simpleComboBox.getSelectedInt();
    }

    @Test
    public void testGetSelectedLongWithNullValue() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn(null);
        assertEquals(-1L, simpleComboBox.getSelectedLong());
    }

    @Test
    public void testGetSelectedLongWithIntegerValue() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn(42);
        assertEquals(42L, simpleComboBox.getSelectedLong());
    }

    @Test
    public void testGetSelectedLongWithLongValue() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn(42L);
        assertEquals(42L, simpleComboBox.getSelectedLong());
    }

    @Test
    public void testGetSelectedLongWithShortValue() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn((short) 42);
        assertEquals(42L, simpleComboBox.getSelectedLong());
    }

    @Test
    public void testGetSelectedLongWithByteValue() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn((byte) 42);
        assertEquals(42L, simpleComboBox.getSelectedLong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSelectedLongWithInvalidValue() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn("Invalid");
        simpleComboBox.getSelectedLong();
    }
}