package com.lts.swing.combobox;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleComboBoxTest {

    private SimpleComboBox simpleComboBox;
    private SimpleComboBoxModel model;

    @Before
    public void setUp() {
        model = new SimpleComboBoxModel();
        simpleComboBox = new SimpleComboBox(model);
    }

    @Test
    public void testConstructor() {
        assertNotNull(simpleComboBox);
        assertEquals(model, simpleComboBox.getModel());
    }

    @Test
    public void testSetModel() {
        SimpleComboBoxModel newModel = new SimpleComboBoxModel();
        simpleComboBox.setModel(newModel);
        assertEquals(newModel, simpleComboBox.getModel());
    }

    @Test
    public void testGetSelectedValue() {
        assertNull(simpleComboBox.getSelectedValue());

        model.setSelectedValue("Test Value");
        assertEquals("Test Value", simpleComboBox.getSelectedValue());
    }

    @Test
    public void testSetSelectedValue() {
        simpleComboBox.setSelectedValue("New Value");
        assertEquals("New Value", model.getSelectedValue());
    }

    @Test
    public void testGetSelectedInt() {
        assertEquals(-1, simpleComboBox.getSelectedInt());

        model.setSelectedValue(123);
        assertEquals(123, simpleComboBox.getSelectedInt());

        model.setSelectedValue(123L);
        assertEquals(123, simpleComboBox.getSelectedInt());

        model.setSelectedValue((short) 123);
        assertEquals(123, simpleComboBox.getSelectedInt());

        model.setSelectedValue((byte) 123);
        assertEquals(123, simpleComboBox.getSelectedInt());

        try {
            model.setSelectedValue("Invalid");
            simpleComboBox.getSelectedInt();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    @Test
    public void testGetSelectedLong() {
        assertEquals(-1L, simpleComboBox.getSelectedLong());

        model.setSelectedValue(123);
        assertEquals(123L, simpleComboBox.getSelectedLong());

        model.setSelectedValue(123L);
        assertEquals(123L, simpleComboBox.getSelectedLong());

        model.setSelectedValue((short) 123);
        assertEquals(123L, simpleComboBox.getSelectedLong());

        model.setSelectedValue((byte) 123);
        assertEquals(123L, simpleComboBox.getSelectedLong());

        try {
            model.setSelectedValue("Invalid");
            simpleComboBox.getSelectedLong();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}