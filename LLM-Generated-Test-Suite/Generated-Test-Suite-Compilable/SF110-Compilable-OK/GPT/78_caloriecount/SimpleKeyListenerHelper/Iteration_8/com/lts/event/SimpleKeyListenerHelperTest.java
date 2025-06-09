package com.lts.event;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SimpleKeyListenerHelperTest {

    private Component mockComponent;
    private SimpleKeyListenerHelper keyListenerHelper;
    private SimpleKeyListener mockListener;

    @Before
    public void setUp() {
        mockComponent = mock(Component.class);
        keyListenerHelper = new SimpleKeyListenerHelper(mockComponent);
        mockListener = mock(SimpleKeyListener.class);
    }

    @Test
    public void testConstructorAddsKeyListener() {
        verify(mockComponent).addKeyListener(keyListenerHelper);
    }

    @Test
    public void testDetachRemovesKeyListener() {
        keyListenerHelper.detach();
        verify(mockComponent).removeKeyListener(keyListenerHelper);
    }

    @Test
    public void testDetachSetsComponentToNull() {
        keyListenerHelper.detach();
        assertNull(keyListenerHelper.component);
    }

    @Test
    public void testNotifyListenerEnter() {
        keyListenerHelper.notifyListener(mockListener, 10, null);
        verify(mockListener).enter(mockComponent);
    }

    @Test
    public void testNotifyListenerInsert() {
        keyListenerHelper.notifyListener(mockListener, 155, null);
        verify(mockListener).insert(mockComponent);
    }

    @Test
    public void testNotifyListenerDelete() {
        keyListenerHelper.notifyListener(mockListener, 127, null);
        verify(mockListener).delete(mockComponent);
    }

    @Test
    public void testNotifyListenerTab() {
        keyListenerHelper.notifyListener(mockListener, 9, null);
        verify(mockListener).tab(mockComponent);
    }

    @Test
    public void testKeyPressedEnter() {
        KeyEvent enterEvent = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        keyListenerHelper.keyPressed(enterEvent);
        verify(keyListenerHelper).fire(KeyEvent.VK_ENTER);
    }

    @Test
    public void testKeyPressedInsert() {
        KeyEvent insertEvent = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 155, KeyEvent.CHAR_UNDEFINED);
        keyListenerHelper.keyPressed(insertEvent);
        verify(keyListenerHelper).fire(155);
    }

    @Test
    public void testKeyPressedDelete() {
        KeyEvent deleteEvent = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DELETE, KeyEvent.CHAR_UNDEFINED);
        keyListenerHelper.keyPressed(deleteEvent);
        verify(keyListenerHelper).fire(KeyEvent.VK_DELETE);
    }

    @Test
    public void testKeyPressedTab() {
        KeyEvent tabEvent = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_TAB, KeyEvent.CHAR_UNDEFINED);
        keyListenerHelper.keyPressed(tabEvent);
        verify(keyListenerHelper).fire(KeyEvent.VK_TAB);
    }

    @Test
    public void testKeyPressedOtherKey() {
        KeyEvent otherEvent = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, KeyEvent.CHAR_UNDEFINED);
        keyListenerHelper.keyPressed(otherEvent);
        verify(keyListenerHelper, never()).fire(anyInt());
    }

    @Test
    public void testKeyReleasedDoesNothing() {
        KeyEvent event = new KeyEvent(mockComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_A, KeyEvent.CHAR_UNDEFINED);
        keyListenerHelper.keyReleased(event);
        // No exception means it does nothing
    }

    @Test
    public void testKeyTypedDoesNothing() {
        KeyEvent event = new KeyEvent(mockComponent, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0, KeyEvent.VK_UNDEFINED, 'a');
        keyListenerHelper.keyTyped(event);
        // No exception means it does nothing
    }
}