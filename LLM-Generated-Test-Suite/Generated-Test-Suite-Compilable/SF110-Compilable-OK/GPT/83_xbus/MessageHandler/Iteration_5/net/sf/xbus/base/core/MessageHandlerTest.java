package net.sf.xbus.base.core;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.*;

@RunWith(JUnit4.class)
public class MessageHandlerTest {

    private MessageHandler messageHandler;
    private static final String BASENAME = "testMessages";

    @Before
    public void setUp() {
        // Initialize the MessageHandler instance
        messageHandler = MessageHandler.getInstance(BASENAME);
    }

    @Test
    public void testGetMessageWithExistingKey() {
        // Assuming the message file contains a key "greeting" with value "Hello, $1$!"
        List<String> params = new ArrayList<>();
        params.add("World");
        String result = messageHandler.getMessage("greeting", params);
        assertEquals("Hello, World!", result);
    }

    @Test
    public void testGetMessageWithNonExistingKey() {
        List<String> params = new ArrayList<>();
        String result = messageHandler.getMessage("nonExistingKey", params);
        assertEquals("Key: nonExistingKey not found in message file", result);
    }

    @Test
    public void testGetMessageOptionalWithExistingKey() {
        // Assuming the message file contains a key "farewell" with value "Goodbye, $1$!"
        List<String> params = new ArrayList<>();
        params.add("Friend");
        String result = messageHandler.getMessageOptional("farewell", params);
        assertEquals("Goodbye, Friend!", result);
    }

    @Test
    public void testGetMessageOptionalWithNonExistingKey() {
        List<String> params = new ArrayList<>();
        String result = messageHandler.getMessageOptional("nonExistingKey", params);
        assertNull(result);
    }

    @Test
    public void testGetMessageOptionalWithNullParams() {
        // Assuming the message file contains a key "simple" with value "Simple message"
        String result = messageHandler.getMessageOptional("simple", null);
        assertEquals("Simple message", result);
    }

    @Test
    public void testGetInstanceReturnsSameInstance() {
        MessageHandler instance1 = MessageHandler.getInstance(BASENAME);
        MessageHandler instance2 = MessageHandler.getInstance(BASENAME);
        assertSame(instance1, instance2);
    }

    @Test
    public void testGetInstanceWithDifferentBasename() {
        MessageHandler instance1 = MessageHandler.getInstance(BASENAME);
        MessageHandler instance2 = MessageHandler.getInstance("anotherBasename");
        assertNotSame(instance1, instance2);
    }
}