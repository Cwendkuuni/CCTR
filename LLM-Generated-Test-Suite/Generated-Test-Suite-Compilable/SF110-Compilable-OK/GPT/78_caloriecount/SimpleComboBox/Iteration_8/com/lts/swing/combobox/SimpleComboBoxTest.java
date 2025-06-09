package com.lts.swing.combobox;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import javax.swing.ComboBoxModel;
import com.lts.swing.combobox.SimpleComboBox;
import com.lts.swing.combobox.SimpleComboBoxModel;

public class SimpleComboBoxTest {

    private SimpleComboBoxModel mockModel;
    private SimpleComboBox simpleComboBox;

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
        Object expectedValue = new Object();
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected value should be returned correctly", expectedValue, simpleComboBox.getSelectedValue());
    }

    @Test
    public void testSetSelectedValue() {
        Object value = new Object();
        simpleComboBox.setSelectedValue(value);
        Mockito.verify(mockModel).setSelectedValue(value);
    }

    @Test
    public void testGetSelectedIntWithInteger() {
        Integer expectedValue = 42;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected int should be returned correctly", expectedValue.intValue(), simpleComboBox.getSelectedInt());
    }

    @Test
    public void testGetSelectedIntWithLong() {
        Long expectedValue = 42L;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected int should be returned correctly", expectedValue.intValue(), simpleComboBox.getSelectedInt());
    }

    @Test
    public void testGetSelectedIntWithShort() {
        Short expectedValue = 42;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected int should be returned correctly", expectedValue.intValue(), simpleComboBox.getSelectedInt());
    }

    @Test
    public void testGetSelectedIntWithByte() {
        Byte expectedValue = 42;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected int should be returned correctly", expectedValue.intValue(), simpleComboBox.getSelectedInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSelectedIntWithInvalidType() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn("InvalidType");
        simpleComboBox.getSelectedInt();
    }

    @Test
    public void testGetSelectedLongWithInteger() {
        Integer expectedValue = 42;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected long should be returned correctly", expectedValue.longValue(), simpleComboBox.getSelectedLong());
    }

    @Test
    public void testGetSelectedLongWithLong() {
        Long expectedValue = 42L;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected long should be returned correctly", expectedValue.longValue(), simpleComboBox.getSelectedLong());
    }

    @Test
    public void testGetSelectedLongWithShort() {
        Short expectedValue = 42;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected long should be returned correctly", expectedValue.longValue(), simpleComboBox.getSelectedLong());
    }

    @Test
    public void testGetSelectedLongWithByte() {
        Byte expectedValue = 42;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected long should be returned correctly", expectedValue.longValue(), simpleComboBox.getSelectedLong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSelectedLongWithInvalidType() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn("InvalidType");
        simpleComboBox.getSelectedLong();
    }
}