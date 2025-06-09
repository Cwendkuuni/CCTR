package dsachat.server;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import dsachat.server.Handler;
import dsachat.share.*;
import dsachat.share.hero.*;

import java.io.IOException;
import java.util.Vector;

public class HandlerTest {

    private Handler handler;
    private ConnectionEndpoint mockConnection;
    private InputEvent mockInputEvent;
    private Challenge mockChallenge;
    private ChallReq mockChallReq;
    private Hero mockHero;
    private UserEvent mockUserEvent;

    @Before
    public void setUp() {
        handler = new Handler();
        mockConnection = mock(ConnectionEndpoint.class);
        mockInputEvent = mock(InputEvent.class);
        mockChallenge = mock(Challenge.class);
        mockChallReq = mock(ChallReq.class);
        mockHero = mock(Hero.class);
        mockUserEvent = mock(UserEvent.class);
    }

    @Test
    public void testAddConnection() throws IOException {
        when(mockConnection.isConnected()).thenReturn(true);
        handler.addConnection(mockConnection);

        assertEquals(1, handler.clients.size());
        assertEquals(mockConnection, handler.clients.elementAt(0).ce);
    }

    @Test
    public void testRemoveConnection() throws IOException {
        when(mockConnection.getClientPort()).thenReturn(1234);
        handler.addConnection(mockConnection);
        handler.removeConnection(mockConnection);

        assertEquals(0, handler.clients.size());
    }

    @Test
    public void testHandleInputWithString() throws IOException {
        when(mockInputEvent.getData()).thenReturn("/me action");
        when(mockInputEvent.getSource()).thenReturn(mockConnection);
        when(mockConnection.getClientPort()).thenReturn(1234);

        handler.addConnection(mockConnection);
        handler.handleInput(mockInputEvent);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(mockConnection).send(argument.capture());
        assertTrue(argument.getValue().contains("*: action"));
    }

    @Test
    public void testHandleInputWithChallenge() throws IOException {
        when(mockInputEvent.getData()).thenReturn(mockChallenge);
        when(mockInputEvent.getSource()).thenReturn(mockConnection);
        when(mockChallenge.roll()).thenReturn("rolled");
        when(mockChallenge.getTo()).thenReturn(null);

        handler.addConnection(mockConnection);
        handler.handleInput(mockInputEvent);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(mockConnection).send(argument.capture());
        assertTrue(argument.getValue().contains("rolled"));
    }

    @Test
    public void testHandleInputWithChallReq() throws IOException {
        when(mockInputEvent.getData()).thenReturn(mockChallReq);
        when(mockInputEvent.getSource()).thenReturn(mockConnection);
        when(mockChallReq.getTo()).thenReturn("target");

        handler.addConnection(mockConnection);
        handler.handleInput(mockInputEvent);

        // Verify that the ChallReq is sent to the correct target
        // This requires more setup to add a target connection
    }

    @Test
    public void testHandleInputWithHero() throws IOException {
        when(mockInputEvent.getData()).thenReturn(mockHero);
        when(mockInputEvent.getSource()).thenReturn(mockConnection);
        when(mockHero.getName()).thenReturn("HeroName");

        handler.addConnection(mockConnection);
        handler.handleInput(mockInputEvent);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(mockConnection).send(argument.capture());
        assertTrue(argument.getValue().contains("Welcome HeroName"));
    }

    @Test
    public void testHandleInputWithUserEvent() throws IOException {
        when(mockInputEvent.getData()).thenReturn(mockUserEvent);
        when(mockInputEvent.getSource()).thenReturn(mockConnection);
        when(mockUserEvent.getName()).thenReturn("Gamemaster");

        handler.addConnection(mockConnection);
        handler.handleInput(mockInputEvent);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(mockConnection).send(argument.capture());
        assertTrue(argument.getValue().contains("Welcome Gamemaster"));
    }

    // Additional tests for private methods can be added by testing public methods that use them
}