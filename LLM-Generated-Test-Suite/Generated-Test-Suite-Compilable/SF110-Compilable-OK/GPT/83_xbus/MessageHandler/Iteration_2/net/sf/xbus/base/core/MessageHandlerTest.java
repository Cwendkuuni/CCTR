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

    @Before
    public void setUp() {
        // Assuming the basename is "test" and the directories are set up correctly
        messageHandler = MessageHandler.getInstance("test");
    }

    @Test
    public void testGetMessageWithExistingKey() {
        // Assuming "existingKey" is a key in the properties file with value "Hello $1$"
        List<String> params = Arrays.asList("World");
        String result = messageHandler.getMessage("existingKey", params);
        assertEquals("Hello World", result);
    }

    @Test
    public void testGetMessageWithNonExistingKey() {
        List<String> params = Arrays.asList("World");
        String result = messageHandler.getMessage("nonExistingKey", params);
        assertEquals("Key: nonExistingKey not found in message file", result);
    }

    @Test
    public void testGetMessageOptionalWithExistingKey() {
        // Assuming "existingKey" is a key in the properties file with value "Hello $1$"
        List<String> params = Arrays.asList("World");
        String result = messageHandler.getMessageOptional("existingKey", params);
        assertEquals("Hello World", result);
    }

    @Test
    public void testGetMessageOptionalWithNonExistingKey() {
        List<String> params = Arrays.asList("World");
        String result = messageHandler.getMessageOptional("nonExistingKey", params);
        assertNull(result);
    }

    @Test
    public void testGetMessageOptionalWithNullParams() {
        // Assuming "existingKey" is a key in the properties file with value "Hello $1$"
        String result = messageHandler.getMessageOptional("existingKey", null);
        assertEquals("Hello $1$", result);
    }

    @Test
    public void testGetInstance() {
        MessageHandler instance1 = MessageHandler.getInstance("test");
        MessageHandler instance2 = MessageHandler.getInstance("test");
        assertSame(instance1, instance2);
    }

    @Test
    public void testGetInstanceWithDifferentBasename() {
        MessageHandler instance1 = MessageHandler.getInstance("test1");
        MessageHandler instance2 = MessageHandler.getInstance("test2");
        assertNotSame(instance1, instance2);
    }
}