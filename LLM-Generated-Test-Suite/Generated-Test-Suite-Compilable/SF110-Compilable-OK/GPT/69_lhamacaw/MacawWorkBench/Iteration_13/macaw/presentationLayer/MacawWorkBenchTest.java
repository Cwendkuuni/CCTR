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

    private SessionProperties mockSessionProperties;
    private UserInterfaceFactory mockUserInterfaceFactory;
    private JDialog mockDialog;
    private JButton mockEditConstantsButton;
    private JButton mockEditVariablesButton;
    private JButton mockExportVariableDataButton;
    private JButton mockExitButton;
    private MacawWorkBench macawWorkBench;

    @Before
    public void setUp() {
        mockSessionProperties = mock(SessionProperties.class);
        mockUserInterfaceFactory = mock(UserInterfaceFactory.class);
        mockDialog = mock(JDialog.class);
        mockEditConstantsButton = mock(JButton.class);
        mockEditVariablesButton = mock(JButton.class);
        mockExportVariableDataButton = mock(JButton.class);
        mockExitButton = mock(JButton.class);

        when(mockSessionProperties.getUserInterfaceFactory()).thenReturn(mockUserInterfaceFactory);
        when(mockUserInterfaceFactory.createDialog()).thenReturn(mockDialog);
        when(mockUserInterfaceFactory.createButton(anyString())).thenReturn(mockEditConstantsButton, mockEditVariablesButton, mockExportVariableDataButton, mockExitButton);

        macawWorkBench = new MacawWorkBench(mockSessionProperties);
    }

    @Test
    public void testConstructorInitializesDialog() {
        verify(mockDialog).setTitle(anyString());
        verify(mockDialog).setModal(true);
        verify(mockDialog).setSize(300, 300);
    }

    @Test
    public void testShowMethod() {
        macawWorkBench.show();
        verify(mockDialog).setVisible(true);
    }

    @Test
    public void testActionPerformedEditConstants() {
        ActionEvent mockEvent = mock(ActionEvent.class);
        when(mockEvent.getSource()).thenReturn(mockEditConstantsButton);

        macawWorkBench.actionPerformed(mockEvent);

        // Verify that the editConstants method is called
        // This requires spying on the macawWorkBench instance
        MacawWorkBench spyMacawWorkBench = spy(macawWorkBench);
        doNothing().when(spyMacawWorkBench).editConstants();
        spyMacawWorkBench.actionPerformed(mockEvent);
        verify(spyMacawWorkBench).editConstants();
    }

    @Test
    public void testActionPerformedEditVariables() {
        ActionEvent mockEvent = mock(ActionEvent.class);
        when(mockEvent.getSource()).thenReturn(mockEditVariablesButton);

        macawWorkBench.actionPerformed(mockEvent);

        // Verify that the editVariables method is called
        MacawWorkBench spyMacawWorkBench = spy(macawWorkBench);
        doNothing().when(spyMacawWorkBench).editVariables();
        spyMacawWorkBench.actionPerformed(mockEvent);
        verify(spyMacawWorkBench).editVariables();
    }

    @Test
    public void testActionPerformedExportVariableData() {
        ActionEvent mockEvent = mock(ActionEvent.class);
        when(mockEvent.getSource()).thenReturn(mockExportVariableDataButton);

        macawWorkBench.actionPerformed(mockEvent);

        // Verify that the exportVariableData method is called
        MacawWorkBench spyMacawWorkBench = spy(macawWorkBench);
        doNothing().when(spyMacawWorkBench).exportVariableData();
        spyMacawWorkBench.actionPerformed(mockEvent);
        verify(spyMacawWorkBench).exportVariableData();
    }

    @Test
    public void testActionPerformedExit() {
        ActionEvent mockEvent = mock(ActionEvent.class);
        when(mockEvent.getSource()).thenReturn(mockExitButton);

        macawWorkBench.actionPerformed(mockEvent);

        // Verify that the exit method is called
        MacawWorkBench spyMacawWorkBench = spy(macawWorkBench);
        doNothing().when(spyMacawWorkBench).exit();
        spyMacawWorkBench.actionPerformed(mockEvent);
        verify(spyMacawWorkBench).exit();
    }
}