package lotus.core.phases;

import lotus.core.*;
import lotus.core.effect.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PhaseTest {

    private Phase phase;
    private Game game;

    @Before
    public void setUp() {
        game = mock(Game.class);
        Game.currentPhase = mock(UntapPhase.class);
        Game.playingPlayer = 1;
    }

    @Test
    public void testNextPhaseFromUntap() {
        Phase.nextPhase();
        verify(game).currentPhase = any(UpkeepPhase.class);
    }

    @Test
    public void testNextPhaseFromUpkeep() {
        Game.currentPhase = mock(UpkeepPhase.class);
        Phase.nextPhase();
        verify(game).currentPhase = any(DrawPhase.class);
    }

    @Test
    public void testNextPhaseFromDraw() {
        Game.currentPhase = mock(DrawPhase.class);
        Phase.nextPhase();
        verify(game).currentPhase = any(Main1Phase.class);
    }

    @Test
    public void testNextPhaseFromMain1() {
        Game.currentPhase = mock(Main1Phase.class);
        Phase.nextPhase();
        verify(game).currentPhase = any(CombatBeginningPhase.class);
    }

    @Test
    public void testNextPhaseFromCombatBeginning() {
        Game.currentPhase = mock(CombatBeginningPhase.class);
        Phase.nextPhase();
        verify(game).currentPhase = any(DeclareAttackersPhase.class);
    }

    @Test
    public void testNextPhaseFromDeclareAttackers() {
        Game.currentPhase = mock(DeclareAttackersPhase.class);
        Phase.nextPhase();
        verify(game).currentPhase = any(DeclareBlockersPhase.class);
    }

    @Test
    public void testNextPhaseFromDeclareBlockers() {
        Game.currentPhase = mock(DeclareBlockersPhase.class);
        Phase.nextPhase();
        verify(game).currentPhase = any(CombatDamagePhase.class);
    }

    @Test
    public void testNextPhaseFromCombatDamage() {
        Game.currentPhase = mock(CombatDamagePhase.class);
        Phase.nextPhase();
        verify(game).currentPhase = any(CombatEndPhase.class);
    }

    @Test
    public void testNextPhaseFromCombatEnd() {
        Game.currentPhase = mock(CombatEndPhase.class);
        Phase.nextPhase();
        verify(game).currentPhase = any(Main2Phase.class);
    }

    @Test
    public void testNextPhaseFromMain2() {
        Game.currentPhase = mock(Main2Phase.class);
        Phase.nextPhase();
        verify(game).currentPhase = any(EndOfTurnPhase.class);
    }

    @Test
    public void testNextPhaseFromEndOfTurn() {
        Game.currentPhase = mock(EndOfTurnPhase.class);
        Phase.nextPhase();
        verify(game).currentPhase = any(CleanupPhase.class);
    }

    @Test
    public void testNextPhaseFromCleanup() {
        Game.currentPhase = mock(CleanupPhase.class);
        Phase.nextPhase();
        verify(game).currentPhase = any(PlayerChangePhase.class);
    }

    @Test
    public void testNextPhaseFromPlayerChange() {
        Game.currentPhase = mock(PlayerChangePhase.class);
        Phase.nextPhase();
        assertEquals(2, Game.playingPlayer);
        verify(game).currentPhase = any(UntapPhase.class);
    }

    @Test
    public void testNextPhaseFromPlayerChangeBackToPlayer1() {
        Game.currentPhase = mock(PlayerChangePhase.class);
        Game.playingPlayer = 2;
        Phase.nextPhase();
        assertEquals(1, Game.playingPlayer);
        verify(game).currentPhase = any(UntapPhase.class);
    }
}