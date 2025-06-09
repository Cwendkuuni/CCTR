package ioproject.server.network;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.ref.WeakReference;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ClientGroupTest {

    private ClientGroup parentGroup;
    private ClientGroup clientGroup;
    private Client client;
    private ClientHandler handler;

    @Before
    public void setUp() {
        parentGroup = new ClientGroup(null);
        clientGroup = new ClientGroup(parentGroup);
        client = mock(Client.class);
        handler = mock(ClientHandler.class);
    }

    @Test
    public void testParentGroup() {
        assertEquals(parentGroup, clientGroup.parentGroup());
    }

    @Test
    public void testNotifyMessageSent() {
        clientGroup.add(client);
        clientGroup.addClientHandler(handler);

        assertTrue(clientGroup.notifyMessageSent(client, "Test Message"));
        verify(handler, times(1)).messageSent(client, "Test Message");
    }

    @Test
    public void testNotifyMessageSent_ClientNotInGroup() {
        assertFalse(clientGroup.notifyMessageSent(client, "Test Message"));
    }

    @Test
    public void testNotifyMessageReceived() {
        clientGroup.add(client);
        clientGroup.addClientHandler(handler);

        assertTrue(clientGroup.notifyMessageReceived(client, "Test Message"));
        verify(handler, times(1)).messageReceived(client, "Test Message");
    }

    @Test
    public void testNotifyMessageReceived_ClientNotInGroup() {
        assertFalse(clientGroup.notifyMessageReceived(client, "Test Message"));
    }

    @Test
    public void testNotifyExceptionCaught() {
        clientGroup.add(client);
        clientGroup.addClientHandler(handler);
        Throwable throwable = new Throwable("Test Exception");

        assertTrue(clientGroup.notifyExceptionCaught(client, throwable));
        verify(handler, times(1)).exceptionCaught(client, throwable);
    }

    @Test
    public void testNotifyExceptionCaught_ClientNotInGroup() {
        Throwable throwable = new Throwable("Test Exception");
        assertFalse(clientGroup.notifyExceptionCaught(client, throwable));
    }

    @Test
    public void testIterator() {
        clientGroup.add(client);
        Iterator<Client> iterator = clientGroup.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(client, iterator.next());
    }

    @Test
    public void testContains() {
        clientGroup.add(client);
        assertTrue(clientGroup.contains(client));
    }

    @Test
    public void testSize() {
        assertEquals(0, clientGroup.size());
        clientGroup.add(client);
        assertEquals(1, clientGroup.size());
    }

    @Test
    public void testAdd() {
        assertTrue(clientGroup.add(client));
        assertTrue(clientGroup.contains(client));
    }

    @Test
    public void testAdd_ClientAlreadyInParentGroup() {
        parentGroup.add(client);
        assertTrue(clientGroup.add(client));
    }

    @Test
    public void testRemove() {
        clientGroup.add(client);
        assertTrue(clientGroup.remove(client));
        assertFalse(clientGroup.contains(client));
    }

    @Test
    public void testRemove_ClientNotInGroup() {
        assertFalse(clientGroup.remove(client));
    }

    @Test
    public void testCreateSubGroup() {
        ClientGroup subGroup = clientGroup.createSubGroup();
        assertNotNull(subGroup);
        assertEquals(clientGroup, subGroup.parentGroup());
    }

    @Test
    public void testAddClientHandler() {
        clientGroup.addClientHandler(handler);
        clientGroup.add(client);
        clientGroup.notifyMessageSent(client, "Test Message");
        verify(handler, times(1)).messageSent(client, "Test Message");
    }
}