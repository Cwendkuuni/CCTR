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
    private SimpleComboBox comboBox;

    @Before
    public void setUp() {
        mockModel = Mockito.mock(SimpleComboBoxModel.class);
        comboBox = new SimpleComboBox(mockModel);
    }

    @Test
    public void testConstructor() {
        assertNotNull("ComboBox should be initialized", comboBox);
    }

    @Test
    public void testSetModelWithSimpleComboBoxModel() {
        SimpleComboBoxModel newModel = Mockito.mock(SimpleComboBoxModel.class);
        comboBox.setModel(newModel);
        assertEquals("Model should be set to new SimpleComboBoxModel", newModel, comboBox.getModel());
    }

    @Test
    public void testSetModelWithNonSimpleComboBoxModel() {
        ComboBoxModel nonSimpleModel = Mockito.mock(ComboBoxModel.class);
        comboBox.setModel(nonSimpleModel);
        assertEquals("Model should be set to non-SimpleComboBoxModel", nonSimpleModel, comboBox.getModel());
    }

    @Test
    public void testGetSelectedValue() {
        Object expectedValue = "TestValue";
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected value should match", expectedValue, comboBox.getSelectedValue());
    }

    @Test
    public void testSetSelectedValue() {
        Object value = "NewValue";
        comboBox.setSelectedValue(value);
        Mockito.verify(mockModel).setSelectedValue(value);
    }

    @Test
    public void testGetSelectedIntWithInteger() {
        Integer expectedValue = 42;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected int should match", expectedValue.intValue(), comboBox.getSelectedInt());
    }

    @Test
    public void testGetSelectedIntWithLong() {
        Long expectedValue = 42L;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected int should match", expectedValue.intValue(), comboBox.getSelectedInt());
    }

    @Test
    public void testGetSelectedIntWithShort() {
        Short expectedValue = 42;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected int should match", expectedValue.intValue(), comboBox.getSelectedInt());
    }

    @Test
    public void testGetSelectedIntWithByte() {
        Byte expectedValue = 42;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected int should match", expectedValue.intValue(), comboBox.getSelectedInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSelectedIntWithInvalidType() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn("InvalidType");
        comboBox.getSelectedInt();
    }

    @Test
    public void testGetSelectedLongWithInteger() {
        Integer expectedValue = 42;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected long should match", expectedValue.longValue(), comboBox.getSelectedLong());
    }

    @Test
    public void testGetSelectedLongWithLong() {
        Long expectedValue = 42L;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected long should match", expectedValue.longValue(), comboBox.getSelectedLong());
    }

    @Test
    public void testGetSelectedLongWithShort() {
        Short expectedValue = 42;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected long should match", expectedValue.longValue(), comboBox.getSelectedLong());
    }

    @Test
    public void testGetSelectedLongWithByte() {
        Byte expectedValue = 42;
        Mockito.when(mockModel.getSelectedValue()).thenReturn(expectedValue);
        assertEquals("Selected long should match", expectedValue.longValue(), comboBox.getSelectedLong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSelectedLongWithInvalidType() {
        Mockito.when(mockModel.getSelectedValue()).thenReturn("InvalidType");
        comboBox.getSelectedLong();
    }
}