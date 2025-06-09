package lotus.core;

import lotus.core.stack.*;
import lotus.core.interfaces.*;
import lotus.core.phases.*;
import lotus.core.card.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameTest {

    private static UserInterface mockIP1;
    private static UserInterface mockIP2;
    private static CardCollection mockDeck1;
    private static CardCollection mockDeck2;
    private static Player mockPlayer1;
    private static Player mockPlayer2;

    @Before
    public void setUp() {
        mockIP1 = mock(UserInterface.class);
        mockIP2 = mock(UserInterface.class);
        mockDeck1 = mock(CardCollection.class);
        mockDeck2 = mock(CardCollection.class);
        mockPlayer1 = mock(Player.class);
        mockPlayer2 = mock(Player.class);

        Game.player1 = mockPlayer1;
        Game.player2 = mockPlayer2;
        Game.playingPlayer = 1;
        Game.currentPhase = mock(Phase.class);
        Game.stack = mock(Stack.class);
    }

    @Test
    public void testGetPlayingPlayer() {
        when(Game.playingPlayer).thenReturn(1);
        assertEquals(mockPlayer1, Game.getPlayingPlayer());

        when(Game.playingPlayer).thenReturn(2);
        assertEquals(mockPlayer2, Game.getPlayingPlayer());
    }

    @Test
    public void testGetNonPlayingPlayer() {
        when(Game.playingPlayer).thenReturn(1);
        assertEquals(mockPlayer2, Game.getNonPlayingPlayer());

        when(Game.playingPlayer).thenReturn(2);
        assertEquals(mockPlayer1, Game.getNonPlayingPlayer());
    }

    @Test
    public void testGetOtherPlayer() {
        assertEquals(mockPlayer2, Game.getOtherPlayer(mockPlayer1));
        assertEquals(mockPlayer1, Game.getOtherPlayer(mockPlayer2));
    }

    @Test
    public void testInit() {
        Game.init(mockIP1, mockIP2, "Player1", "Player2", mockDeck1, mockDeck2);

        verify(mockIP1).init(any(Player.class));
        verify(mockIP2).init(any(Player.class));

        assertEquals(1, Game.playingPlayer);
        assertNotNull(Game.currentPhase);
    }

    @Test
    public void testGivePriorityToCurrentPlayer() {
        when(mockPlayer1.letPlayerSpeak()).thenReturn(false);
        when(mockPlayer2.letPlayerSpeak()).thenReturn(false);
        when(Game.stack.empty()).thenReturn(true);

        Game.givePriorityToCurrentPlayer();

        verify(mockPlayer1, times(1)).letPlayerSpeak();
        verify(mockPlayer2, times(1)).letPlayerSpeak();
        verify(Game.stack, never()).resolveLast();
    }

    @Test
    public void testGivePriorityToCurrentPlayerWithStack() {
        when(mockPlayer1.letPlayerSpeak()).thenReturn(false);
        when(mockPlayer2.letPlayerSpeak()).thenReturn(false);
        when(Game.stack.empty()).thenReturn(false);

        Game.givePriorityToCurrentPlayer();

        verify(mockPlayer1, times(1)).letPlayerSpeak();
        verify(mockPlayer2, times(1)).letPlayerSpeak();
        verify(Game.stack, times(1)).resolveLast();
    }
}