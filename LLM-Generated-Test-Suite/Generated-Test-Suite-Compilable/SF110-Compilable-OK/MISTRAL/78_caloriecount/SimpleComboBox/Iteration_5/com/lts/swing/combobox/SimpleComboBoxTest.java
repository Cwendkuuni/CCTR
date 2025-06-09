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
        model.setSelectedValue(123);
        assertEquals(123, simpleComboBox.getSelectedValue());

        model.setSelectedValue(null);
        assertNull(simpleComboBox.getSelectedValue());
    }

    @Test
    public void testSetSelectedValue() {
        simpleComboBox.setSelectedValue(456);
        assertEquals(456, model.getSelectedValue());
    }

    @Test
    public void testGetSelectedInt() {
        model.setSelectedValue(123);
        assertEquals(123, simpleComboBox.getSelectedInt());

        model.setSelectedValue(123L);
        assertEquals(123, simpleComboBox.getSelectedInt());

        model.setSelectedValue((short) 123);
        assertEquals(123, simpleComboBox.getSelectedInt());

        model.setSelectedValue((byte) 123);
        assertEquals(123, simpleComboBox.getSelectedInt());

        model.setSelectedValue(null);
        assertEquals(-1, simpleComboBox.getSelectedInt());

        try {
            model.setSelectedValue("invalid");
            simpleComboBox.getSelectedInt();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testGetSelectedLong() {
        model.setSelectedValue(123);
        assertEquals(123L, simpleComboBox.getSelectedLong());

        model.setSelectedValue(123L);
        assertEquals(123L, simpleComboBox.getSelectedLong());

        model.setSelectedValue((short) 123);
        assertEquals(123L, simpleComboBox.getSelectedLong());

        model.setSelectedValue((byte) 123);
        assertEquals(123L, simpleComboBox.getSelectedLong());

        model.setSelectedValue(null);
        assertEquals(-1L, simpleComboBox.getSelectedLong());

        try {
            model.setSelectedValue("invalid");
            simpleComboBox.getSelectedLong();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}