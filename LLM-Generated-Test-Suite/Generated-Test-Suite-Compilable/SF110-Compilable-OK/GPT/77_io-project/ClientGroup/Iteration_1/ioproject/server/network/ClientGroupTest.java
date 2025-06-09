package ioproject.server.network;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.lang.ref.WeakReference;
import java.util.Iterator;

public class ClientGroupTest {

    private ClientGroup parentGroup;
    private ClientGroup clientGroup;
    private Client mockClient;
    private ClientHandler mockHandler;

    @Before
    public void setUp() {
        parentGroup = new ClientGroup(null);
        clientGroup = new ClientGroup(parentGroup);
        mockClient = mock(Client.class);
        mockHandler = mock(ClientHandler.class);
    }

    @Test
    public void testParentGroup() {
        assertEquals(parentGroup, clientGroup.parentGroup());
    }

    @Test
    public void testAddClient() {
        assertTrue(clientGroup.add(mockClient));
        assertTrue(clientGroup.contains(mockClient));
    }

    @Test
    public void testAddClientWithHandler() {
        clientGroup.addClientHandler(mockHandler);
        clientGroup.add(mockClient);

        verify(mockHandler).clientAdded(mockClient);
    }

    @Test
    public void testRemoveClient() {
        clientGroup.add(mockClient);
        assertTrue(clientGroup.remove(mockClient));
        assertFalse(clientGroup.contains(mockClient));
    }

    @Test
    public void testRemoveClientWithHandler() {
        clientGroup.addClientHandler(mockHandler);
        clientGroup.add(mockClient);
        clientGroup.remove(mockClient);

        verify(mockHandler).clientRemoved(mockClient);
    }

    @Test
    public void testNotifyMessageSent() {
        clientGroup.add(mockClient);
        clientGroup.addClientHandler(mockHandler);

        assertTrue(clientGroup.notifyMessageSent(mockClient, "Test Message"));

        verify(mockHandler).messageSent(mockClient, "Test Message");
    }

    @Test
    public void testNotifyMessageReceived() {
        clientGroup.add(mockClient);
        clientGroup.addClientHandler(mockHandler);

        assertTrue(clientGroup.notifyMessageReceived(mockClient, "Test Message"));

        verify(mockHandler).messageReceived(mockClient, "Test Message");
    }

    @Test
    public void testNotifyExceptionCaught() {
        clientGroup.add(mockClient);
        clientGroup.addClientHandler(mockHandler);
        Throwable mockThrowable = new Throwable("Test Exception");

        assertTrue(clientGroup.notifyExceptionCaught(mockClient, mockThrowable));

        verify(mockHandler).exceptionCaught(mockClient, mockThrowable);
    }

    @Test
    public void testIterator() {
        clientGroup.add(mockClient);
        Iterator<Client> iterator = clientGroup.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(mockClient, iterator.next());
    }

    @Test
    public void testSize() {
        assertEquals(0, clientGroup.size());
        clientGroup.add(mockClient);
        assertEquals(1, clientGroup.size());
    }

    @Test
    public void testCreateSubGroup() {
        ClientGroup subGroup = clientGroup.createSubGroup();
        assertNotNull(subGroup);
        assertEquals(clientGroup, subGroup.parentGroup());
    }

    @Test
    public void testSubGroupNotification() {
        ClientGroup subGroup = clientGroup.createSubGroup();
        subGroup.add(mockClient);
        subGroup.addClientHandler(mockHandler);

        subGroup.notifyMessageSent(mockClient, "SubGroup Message");

        verify(mockHandler).messageSent(mockClient, "SubGroup Message");
    }
}