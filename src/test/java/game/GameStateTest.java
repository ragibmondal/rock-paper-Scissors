package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GameState class
 */
public class GameStateTest {
    
    private GameState gameState;
    
    @BeforeEach
    public void setUp() {
        gameState = new GameState(GameMode.PLAYER_VS_COMPUTER, 5);
    }
    
    @Test
    public void testInitialState() {
        assertEquals(GameMode.PLAYER_VS_COMPUTER, gameState.getMode());
        assertEquals(5, gameState.getMaxRounds());
        assertEquals(0, gameState.getCurrentRound());
        assertEquals(0, gameState.getPlayer1Wins());
        assertEquals(0, gameState.getPlayer2Wins());
        assertEquals(0, gameState.getDraws());
        assertFalse(gameState.isGameInProgress());
        assertFalse(gameState.isRoundInProgress());
        assertFalse(gameState.isGameFinished());
    }
    
    @Test
    public void testRoundProgression() {
        gameState.nextRound();
        assertEquals(1, gameState.getCurrentRound());
        
        gameState.nextRound();
        assertEquals(2, gameState.getCurrentRound());
    }
    
    @Test
    public void testScoreTracking() {
        gameState.incrementPlayer1Wins();
        assertEquals(1, gameState.getPlayer1Wins());
        
        gameState.incrementPlayer2Wins();
        assertEquals(1, gameState.getPlayer2Wins());
        
        gameState.incrementDraws();
        assertEquals(1, gameState.getDraws());
    }
    
    @Test
    public void testGameFinished() {
        // Game should finish when one player gets majority of rounds
        // For 5 rounds, need 3 wins
        assertFalse(gameState.isGameFinished());
        
        gameState.incrementPlayer1Wins(); // 1 win
        assertFalse(gameState.isGameFinished());
        
        gameState.incrementPlayer1Wins(); // 2 wins
        assertFalse(gameState.isGameFinished());
        
        gameState.incrementPlayer1Wins(); // 3 wins (majority)
        assertTrue(gameState.isGameFinished());
    }
    
    @Test
    public void testGameWinner() {
        // Initially no winner
        assertEquals(0, gameState.getGameWinner());
        
        // Player 1 wins
        gameState.incrementPlayer1Wins();
        gameState.incrementPlayer1Wins();
        gameState.incrementPlayer1Wins();
        assertEquals(1, gameState.getGameWinner());
        
        // Reset and test Player 2 wins
        gameState.reset();
        gameState.incrementPlayer2Wins();
        gameState.incrementPlayer2Wins();
        gameState.incrementPlayer2Wins();
        assertEquals(2, gameState.getGameWinner());
        
        // Test draw scenario
        gameState.reset();
        gameState.setMaxRounds(4); // Even number for easier draw
        gameState.incrementPlayer1Wins();
        gameState.incrementPlayer1Wins();
        gameState.incrementPlayer2Wins();
        gameState.incrementPlayer2Wins();
        gameState.nextRound();
        gameState.nextRound();
        gameState.nextRound();
        gameState.nextRound();
        assertEquals(0, gameState.getGameWinner()); // Draw
    }
    
    @Test
    public void testCountdownTimer() {
        gameState.startCountdown();
        assertTrue(gameState.isRoundInProgress());
        
        // Initially should have time remaining
        assertTrue(gameState.getCountdownTimeRemaining() > 0);
        
        // Wait a bit and check time decreased
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertTrue(gameState.getCountdownTimeRemaining() < 3000);
    }
    
    @Test
    public void testReset() {
        // Set some state
        gameState.setGameInProgress(true);
        gameState.setRoundInProgress(true);
        gameState.nextRound();
        gameState.incrementPlayer1Wins();
        gameState.incrementPlayer2Wins();
        gameState.incrementDraws();
        
        // Reset and verify
        gameState.reset();
        
        assertEquals(0, gameState.getCurrentRound());
        assertEquals(0, gameState.getPlayer1Wins());
        assertEquals(0, gameState.getPlayer2Wins());
        assertEquals(0, gameState.getDraws());
        assertFalse(gameState.isGameInProgress());
        assertFalse(gameState.isRoundInProgress());
    }
}
