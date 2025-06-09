package macaw.presentationLayer;

import static org.mockito.Mockito.*;

import macaw.presentationLayer.MacawWorkBench;
import macaw.system.SessionProperties;
import macaw.system.UserInterfaceFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MacawWorkBenchTest {

    private SessionProperties sessionProperties;
    private UserInterfaceFactory userInterfaceFactory;
    private JDialog dialog;
    private JButton editConstantsButton;
    private JButton editVariablesButton;
    private JButton exportVariableDataButton;
    private JButton exitButton;
    private MacawWorkBench macawWorkBench;

    @Before
    public void setUp() {
        sessionProperties = mock(SessionProperties.class);
        userInterfaceFactory = mock(UserInterfaceFactory.class);
        dialog = mock(JDialog.class);
        editConstantsButton = mock(JButton.class);
        editVariablesButton = mock(JButton.class);
        exportVariableDataButton = mock(JButton.class);
        exitButton = mock(JButton.class);

        when(sessionProperties.getUserInterfaceFactory()).thenReturn(userInterfaceFactory);
        when(userInterfaceFactory.createDialog()).thenReturn(dialog);
        when(userInterfaceFactory.createButton(anyString())).thenReturn(editConstantsButton, editVariablesButton, exportVariableDataButton, exitButton);

        macawWorkBench = new MacawWorkBench(sessionProperties);
    }

    @Test
    public void testConstructorInitializesDialogAndButtons() {
        verify(dialog).setTitle(anyString());
        verify(dialog).setModal(true);
        verify(dialog).setSize(300, 300);
        verify(editConstantsButton).addActionListener(macawWorkBench);
        verify(editVariablesButton).addActionListener(macawWorkBench);
        verify(exportVariableDataButton).addActionListener(macawWorkBench);
        verify(exitButton).addActionListener(macawWorkBench);
    }

    @Test
    public void testShowMethodMakesDialogVisible() {
        macawWorkBench.show();
        verify(dialog).setVisible(true);
    }

    @Test
    public void testActionPerformedEditConstants() {
        ActionEvent event = new ActionEvent(editConstantsButton, ActionEvent.ACTION_PERFORMED, null);
        macawWorkBench.actionPerformed(event);
        // Verify that the editConstants method is called
        // Since editConstants is private, we cannot directly verify its call
        // Instead, we can verify the interaction with the mocked VariableConstantsEditor
        // This requires refactoring the MacawWorkBench class to allow injection of dependencies
    }

    @Test
    public void testActionPerformedEditVariables() {
        ActionEvent event = new ActionEvent(editVariablesButton, ActionEvent.ACTION_PERFORMED, null);
        macawWorkBench.actionPerformed(event);
        // Verify that the editVariables method is called
        // Similar to editConstants, verify interaction with MacawVariableBrowser
    }

    @Test
    public void testActionPerformedExportVariableData() {
        ActionEvent event = new ActionEvent(exportVariableDataButton, ActionEvent.ACTION_PERFORMED, null);
        macawWorkBench.actionPerformed(event);
        // Verify that the exportVariableData method is called
        // Similar to above, verify interaction with ExportVariablesToMacawXML
    }

    @Test
    public void testActionPerformedExit() {
        ActionEvent event = new ActionEvent(exitButton, ActionEvent.ACTION_PERFORMED, null);
        macawWorkBench.actionPerformed(event);
        // Verify that System.exit(0) is called
        // This requires using a SecurityManager to catch the exit call
    }
}