package osa.ora.server.client.ui.utils;

import org.junit.*;
import static org.junit.Assert.*;
import org.mockito.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FontChooserDialogTest {

    private FontChooserDialog dialog;
    private JFontChooser mockChooserPane;
    private ActionListener mockOkListener;
    private ActionListener mockCancelListener;
    private Component mockComponent;

    @Before
    public void setUp() {
        mockChooserPane = Mockito.mock(JFontChooser.class);
        mockOkListener = Mockito.mock(ActionListener.class);
        mockCancelListener = Mockito.mock(ActionListener.class);
        mockComponent = Mockito.mock(Component.class);

        dialog = new FontChooserDialog(mockComponent, "Test Dialog", true, mockChooserPane, mockOkListener, mockCancelListener);
    }

    @Test
    public void testDialogVisibility() {
        dialog.setVisible(true);
        assertTrue(dialog.isVisible());

        dialog.setVisible(false);
        assertFalse(dialog.isVisible());
    }

    @Test
    public void testOkButtonAction() {
        JButton okButton = findButtonByText(dialog, UIManager.getString("ColorChooser.okText"));
        assertNotNull(okButton);

        okButton.doClick();
        assertFalse(dialog.isVisible());
        Mockito.verify(mockOkListener, Mockito.times(1)).actionPerformed(Mockito.any(ActionEvent.class));
    }

    @Test
    public void testCancelButtonAction() {
        JButton cancelButton = findButtonByText(dialog, UIManager.getString("ColorChooser.cancelText"));
        assertNotNull(cancelButton);

        cancelButton.doClick();
        assertFalse(dialog.isVisible());
        Mockito.verify(mockCancelListener, Mockito.times(1)).actionPerformed(Mockito.any(ActionEvent.class));
    }

    @Test
    public void testResetButtonAction() {
        Font initialFont = new Font("Arial", Font.PLAIN, 12);
        Mockito.when(mockChooserPane.getFont()).thenReturn(initialFont);

        dialog.setVisible(true);
        dialog.reset();

        Mockito.verify(mockChooserPane, Mockito.times(1)).setFont(initialFont);
    }

    @Test
    public void testResetFunctionality() {
        Font initialFont = new Font("Arial", Font.PLAIN, 12);
        Font newFont = new Font("Times New Roman", Font.BOLD, 14);

        Mockito.when(mockChooserPane.getFont()).thenReturn(initialFont);
        dialog.setVisible(true);

        Mockito.when(mockChooserPane.getFont()).thenReturn(newFont);
        dialog.reset();

        Mockito.verify(mockChooserPane, Mockito.times(1)).setFont(initialFont);
    }

    private JButton findButtonByText(Container container, String text) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(text)) {
                    return button;
                }
            } else if (component instanceof Container) {
                JButton button = findButtonByText((Container) component, text);
                if (button != null) {
                    return button;
                }
            }
        }
        return null;
    }
}