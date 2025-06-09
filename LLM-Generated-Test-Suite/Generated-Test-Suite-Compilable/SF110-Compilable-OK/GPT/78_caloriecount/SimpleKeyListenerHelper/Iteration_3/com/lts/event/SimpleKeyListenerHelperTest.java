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

    private SimpleKeyListenerHelper helper;
    private Component mockComponent;
    private SimpleKeyListener mockListener;

    @Before
    public void setUp() {
        mockComponent = mock(Component.class);
        helper = new SimpleKeyListenerHelper(mockComponent);
        mockListener = mock(SimpleKeyListener.class);
    }

    @Test
    public void testConstructorAddsKeyListener() {
        verify(mockComponent).addKeyListener(helper);
    }

    @Test
    public void testDetachRemovesKeyListener() {
        helper.detach();
        verify(mockComponent).removeKeyListener(helper);
    }

    @Test
    public void testDetachSetsComponentToNull() {
        helper.detach();
        assertNull(helper.component);
    }

    @Test
    public void testNotifyListenerEnter() {
        helper.notifyListener(mockListener, 10, null);
        verify(mockListener).enter(mockComponent);
    }

    @Test
    public void testNotifyListenerInsert() {
        helper.notifyListener(mockListener, 155, null);
        verify(mockListener).insert(mockComponent);
    }

    @Test
    public void testNotifyListenerDelete() {
        helper.notifyListener(mockListener, 127, null);
        verify(mockListener).delete(mockComponent);
    }

    @Test
    public void testNotifyListenerTab() {
        helper.notifyListener(mockListener, 9, null);
        verify(mockListener).tab(mockComponent);
    }

    @Test
    public void testKeyPressedEnter() {
        KeyEvent enterEvent = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        helper.keyPressed(enterEvent);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(helper).fire(captor.capture());
        assertEquals(KeyEvent.VK_ENTER, captor.getValue().intValue());
    }

    @Test
    public void testKeyPressedInsert() {
        KeyEvent insertEvent = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 155, KeyEvent.CHAR_UNDEFINED);
        helper.keyPressed(insertEvent);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(helper).fire(captor.capture());
        assertEquals(155, captor.getValue().intValue());
    }

    @Test
    public void testKeyPressedDelete() {
        KeyEvent deleteEvent = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DELETE, KeyEvent.CHAR_UNDEFINED);
        helper.keyPressed(deleteEvent);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(helper).fire(captor.capture());
        assertEquals(KeyEvent.VK_DELETE, captor.getValue().intValue());
    }

    @Test
    public void testKeyPressedTab() {
        KeyEvent tabEvent = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_TAB, KeyEvent.CHAR_UNDEFINED);
        helper.keyPressed(tabEvent);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(helper).fire(captor.capture());
        assertEquals(KeyEvent.VK_TAB, captor.getValue().intValue());
    }

    @Test
    public void testKeyPressedOtherKey() {
        KeyEvent otherKeyEvent = new KeyEvent(mockComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, KeyEvent.CHAR_UNDEFINED);
        helper.keyPressed(otherKeyEvent);

        verify(helper, never()).fire(anyInt());
    }

    @Test
    public void testKeyReleasedDoesNothing() {
        KeyEvent event = new KeyEvent(mockComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_A, KeyEvent.CHAR_UNDEFINED);
        helper.keyReleased(event);
        // No exception means pass
    }

    @Test
    public void testKeyTypedDoesNothing() {
        KeyEvent event = new KeyEvent(mockComponent, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0, KeyEvent.VK_UNDEFINED, 'a');
        helper.keyTyped(event);
        // No exception means pass
    }
}