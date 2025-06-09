package lotus.core.phases;

import lotus.core.*;
import lotus.core.effect.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PhaseTest {

    private Phase untapPhase;
    private Phase upkeepPhase;
    private Phase drawPhase;
    private Phase main1Phase;
    private Phase combatBeginningPhase;
    private Phase declareAttackersPhase;
    private Phase declareBlockersPhase;
    private Phase combatDamagePhase;
    private Phase combatEndPhase;
    private Phase main2Phase;
    private Phase endOfTurnPhase;
    private Phase cleanupPhase;
    private Phase playerChangePhase;

    @Before
    public void setUp() {
        untapPhase = new UntapPhase();
        upkeepPhase = new UpkeepPhase();
        drawPhase = new DrawPhase();
        main1Phase = new Main1Phase();
        combatBeginningPhase = new CombatBeginningPhase();
        declareAttackersPhase = new DeclareAttackersPhase();
        declareBlockersPhase = new DeclareBlockersPhase();
        combatDamagePhase = new CombatDamagePhase();
        combatEndPhase = new CombatEndPhase();
        main2Phase = new Main2Phase();
        endOfTurnPhase = new EndOfTurnPhase();
        cleanupPhase = new CleanupPhase();
        playerChangePhase = new PlayerChangePhase();
    }

    @Test
    public void testNextPhaseFromUntap() {
        Game.currentPhase = untapPhase;
        Phase.nextPhase();
        assertEquals(upkeepPhase.getClass(), Game.currentPhase.getClass());
    }

    @Test
    public void testNextPhaseFromUpkeep() {
        Game.currentPhase = upkeepPhase;
        Phase.nextPhase();
        assertEquals(drawPhase.getClass(), Game.currentPhase.getClass());
    }

    @Test
    public void testNextPhaseFromDraw() {
        Game.currentPhase = drawPhase;
        Phase.nextPhase();
        assertEquals(main1Phase.getClass(), Game.currentPhase.getClass());
    }

    @Test
    public void testNextPhaseFromMain1() {
        Game.currentPhase = main1Phase;
        Phase.nextPhase();
        assertEquals(combatBeginningPhase.getClass(), Game.currentPhase.getClass());
    }

    @Test
    public void testNextPhaseFromCombatBeginning() {
        Game.currentPhase = combatBeginningPhase;
        Phase.nextPhase();
        assertEquals(declareAttackersPhase.getClass(), Game.currentPhase.getClass());
    }

    @Test
    public void testNextPhaseFromDeclareAttackers() {
        Game.currentPhase = declareAttackersPhase;
        Phase.nextPhase();
        assertEquals(declareBlockersPhase.getClass(), Game.currentPhase.getClass());
    }

    @Test
    public void testNextPhaseFromDeclareBlockers() {
        Game.currentPhase = declareBlockersPhase;
        Phase.nextPhase();
        assertEquals(combatDamagePhase.getClass(), Game.currentPhase.getClass());
    }

    @Test
    public void testNextPhaseFromCombatDamage() {
        Game.currentPhase = combatDamagePhase;
        Phase.nextPhase();
        assertEquals(combatEndPhase.getClass(), Game.currentPhase.getClass());
    }

    @Test
    public void testNextPhaseFromCombatEnd() {
        Game.currentPhase = combatEndPhase;
        Phase.nextPhase();
        assertEquals(main2Phase.getClass(), Game.currentPhase.getClass());
    }

    @Test
    public void testNextPhaseFromMain2() {
        Game.currentPhase = main2Phase;
        Phase.nextPhase();
        assertEquals(endOfTurnPhase.getClass(), Game.currentPhase.getClass());
    }

    @Test
    public void testNextPhaseFromEndOfTurn() {
        Game.currentPhase = endOfTurnPhase;
        Phase.nextPhase();
        assertEquals(cleanupPhase.getClass(), Game.currentPhase.getClass());
    }

    @Test
    public void testNextPhaseFromCleanup() {
        Game.currentPhase = cleanupPhase;
        Phase.nextPhase();
        assertEquals(playerChangePhase.getClass(), Game.currentPhase.getClass());
    }

    @Test
    public void testNextPhaseFromPlayerChange() {
        Game.currentPhase = playerChangePhase;
        Game.playingPlayer = 1;
        Phase.nextPhase();
        assertEquals(2, Game.playingPlayer);
        assertEquals(untapPhase.getClass(), Game.currentPhase.getClass());
    }

    @Test
    public void testNextPhaseFromPlayerChangeWithPlayer2() {
        Game.currentPhase = playerChangePhase;
        Game.playingPlayer = 2;
        Phase.nextPhase();
        assertEquals(1, Game.playingPlayer);
        assertEquals(untapPhase.getClass(), Game.currentPhase.getClass());
    }
}