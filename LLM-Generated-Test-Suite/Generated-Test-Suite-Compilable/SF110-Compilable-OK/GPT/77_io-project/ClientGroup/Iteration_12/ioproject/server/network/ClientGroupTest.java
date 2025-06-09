package ioproject.server.network;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ClientGroupTest {

    private ClientGroup parentGroup;
    private ClientGroup clientGroup;
    private Client mockClient;
    private ClientHandler mockHandler;

    @Before
    public void setUp() {
        parentGroup = new ClientGroup(null);
        clientGroup = new ClientGroup(parentGroup);
        mockClient = Mockito.mock(Client.class);
        mockHandler = Mockito.mock(ClientHandler.class);
    }

    @Test
    public void testParentGroup() {
        assertEquals(parentGroup, clientGroup.parentGroup());
    }

    @Test
    public void testNotifyMessageSent() {
        clientGroup.add(mockClient);
        clientGroup.addClientHandler(mockHandler);
        assertTrue(clientGroup.notifyMessageSent(mockClient, "Test Message"));
        Mockito.verify(mockHandler).messageSent(mockClient, "Test Message");
    }

    @Test
    public void testNotifyMessageSent_ClientNotInGroup() {
        assertFalse(clientGroup.notifyMessageSent(mockClient, "Test Message"));
    }

    @Test
    public void testNotifyMessageReceived() {
        clientGroup.add(mockClient);
        clientGroup.addClientHandler(mockHandler);
        assertTrue(clientGroup.notifyMessageReceived(mockClient, "Test Message"));
        Mockito.verify(mockHandler).messageReceived(mockClient, "Test Message");
    }

    @Test
    public void testNotifyMessageReceived_ClientNotInGroup() {
        assertFalse(clientGroup.notifyMessageReceived(mockClient, "Test Message"));
    }

    @Test
    public void testNotifyExceptionCaught() {
        clientGroup.add(mockClient);
        clientGroup.addClientHandler(mockHandler);
        Throwable throwable = new Throwable("Test Exception");
        assertTrue(clientGroup.notifyExceptionCaught(mockClient, throwable));
        Mockito.verify(mockHandler).exceptionCaught(mockClient, throwable);
    }

    @Test
    public void testNotifyExceptionCaught_ClientNotInGroup() {
        Throwable throwable = new Throwable("Test Exception");
        assertFalse(clientGroup.notifyExceptionCaught(mockClient, throwable));
    }

    @Test
    public void testIterator() {
        clientGroup.add(mockClient);
        Iterator<Client> iterator = clientGroup.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(mockClient, iterator.next());
    }

    @Test(expected = NoSuchElementException.class)
    public void testIterator_NoSuchElementException() {
        Iterator<Client> iterator = clientGroup.iterator();
        iterator.next(); // Should throw NoSuchElementException
    }

    @Test
    public void testContains() {
        clientGroup.add(mockClient);
        assertTrue(clientGroup.contains(mockClient));
    }

    @Test
    public void testContains_ClientNotInGroup() {
        assertFalse(clientGroup.contains(mockClient));
    }

    @Test
    public void testSize() {
        assertEquals(0, clientGroup.size());
        clientGroup.add(mockClient);
        assertEquals(1, clientGroup.size());
    }

    @Test
    public void testAdd() {
        assertTrue(clientGroup.add(mockClient));
        assertTrue(clientGroup.contains(mockClient));
    }

    @Test
    public void testAdd_ClientAlreadyInParentGroup() {
        parentGroup.add(mockClient);
        assertTrue(clientGroup.add(mockClient));
    }

    @Test
    public void testRemove() {
        clientGroup.add(mockClient);
        assertTrue(clientGroup.remove(mockClient));
        assertFalse(clientGroup.contains(mockClient));
    }

    @Test
    public void testRemove_ClientNotInGroup() {
        assertFalse(clientGroup.remove(mockClient));
    }

    @Test
    public void testCreateSubGroup() {
        ClientGroup subGroup = clientGroup.createSubGroup();
        assertNotNull(subGroup);
        assertEquals(clientGroup, subGroup.parentGroup());
    }

    @Test
    public void testAddClientHandler() {
        clientGroup.addClientHandler(mockHandler);
        clientGroup.add(mockClient);
        Mockito.verify(mockHandler).clientAdded(mockClient);
    }
}