package macaw.presentationLayer;

import macaw.businessLayer.*;
import macaw.persistenceLayer.demo.*;
import macaw.persistenceLayer.production.*;
import macaw.system.*;
import macaw.io.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MacawWorkBenchTest {

    @Mock
    private SessionProperties sessionProperties;

    @Mock
    private UserInterfaceFactory userInterfaceFactory;

    @Mock
    private JDialog dialog;

    @Mock
    private JButton editConstants;

    @Mock
    private JButton editVariables;

    @Mock
    private JButton exportVariableData;

    @Mock
    private JButton exit;

    private MacawWorkBench macawWorkBench;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(sessionProperties.getUserInterfaceFactory()).thenReturn(userInterfaceFactory);
        when(userInterfaceFactory.createDialog()).thenReturn(dialog);
        when(userInterfaceFactory.createButton(anyString())).thenReturn(editConstants, editVariables, exportVariableData, exit);

        macawWorkBench = new MacawWorkBench(sessionProperties);
    }

    @Test
    public void testShow() {
        macawWorkBench.show();
        verify(dialog).setVisible(true);
    }

    @Test
    public void testEditConstants() {
        macawWorkBench.actionPerformed(new ActionEvent(editConstants, ActionEvent.ACTION_PERFORMED, null));
        verify(editConstants).addActionListener(macawWorkBench);
        verify(editConstants).setToolTipText(anyString());
    }

    @Test
    public void testEditVariables() {
        macawWorkBench.actionPerformed(new ActionEvent(editVariables, ActionEvent.ACTION_PERFORMED, null));
        verify(editVariables).addActionListener(macawWorkBench);
        verify(editVariables).setToolTipText(anyString());
    }

    @Test
    public void testExportVariableData() {
        macawWorkBench.actionPerformed(new ActionEvent(exportVariableData, ActionEvent.ACTION_PERFORMED, null));
        verify(exportVariableData).addActionListener(macawWorkBench);
        verify(exportVariableData).setToolTipText(anyString());
    }

    @Test
    public void testExit() {
        macawWorkBench.actionPerformed(new ActionEvent(exit, ActionEvent.ACTION_PERFORMED, null));
        verify(exit).addActionListener(macawWorkBench);
    }

    @Test
    public void testActionPerformedEditConstants() {
        macawWorkBench.actionPerformed(new ActionEvent(editConstants, ActionEvent.ACTION_PERFORMED, null));
        verify(editConstants).addActionListener(macawWorkBench);
        verify(editConstants).setToolTipText(anyString());
    }

    @Test
    public void testActionPerformedEditVariables() {
        macawWorkBench.actionPerformed(new ActionEvent(editVariables, ActionEvent.ACTION_PERFORMED, null));
        verify(editVariables).addActionListener(macawWorkBench);
        verify(editVariables).setToolTipText(anyString());
    }

    @Test
    public void testActionPerformedExportVariableData() {
        macawWorkBench.actionPerformed(new ActionEvent(exportVariableData, ActionEvent.ACTION_PERFORMED, null));
        verify(exportVariableData).addActionListener(macawWorkBench);
        verify(exportVariableData).setToolTipText(anyString());
    }

    @Test
    public void testActionPerformedExit() {
        macawWorkBench.actionPerformed(new ActionEvent(exit, ActionEvent.ACTION_PERFORMED, null));
        verify(exit).addActionListener(macawWorkBench);
    }
}