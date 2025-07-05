package game;

import player.Player;
import player.HumanPlayer;
import player.ComputerPlayer;
import java.util.ArrayList;
import java.util.List;

/**
 * Main game engine that manages game flow and logic
 */
public class GameEngine {
    private GameState gameState;
    private Player player1;
    private Player player2;
    private List<GameEngineListener> listeners;
    
    public GameEngine() {
        this.listeners = new ArrayList<>();
    }
    
    /**
     * Initialize a new game
     * @param mode The game mode (PvP or PvC)
     * @param maxRounds Maximum number of rounds
     * @param player1Name Name of player 1
     * @param player2Name Name of player 2 (or computer)
     * @param computerDifficulty Difficulty level for computer player (0-2)
     */
    public void initializeGame(GameMode mode, int maxRounds, String player1Name, 
                             String player2Name, int computerDifficulty) {
        gameState = new GameState(mode, maxRounds);
        
        if (mode == GameMode.PLAYER_VS_COMPUTER) {
            // PvC mode: Player 1 uses R,P,S keys
            player1 = new HumanPlayer(player1Name, 'r', 'p', 's');
            player2 = new ComputerPlayer(player2Name, computerDifficulty);
        } else {
            // PvP mode: Player 1 uses A,S,D and Player 2 uses J,K,L
            player1 = new HumanPlayer(player1Name, 'a', 's', 'd');
            player2 = new HumanPlayer(player2Name, 'j', 'k', 'l');
        }
        
        notifyGameInitialized();
    }
    
    /**
     * Start a new round
     */
    public void startRound() {
        if (gameState.isGameFinished()) {
            return; // Game already finished
        }
        
        // Reset players for new round
        player1.resetForNewRound();
        player2.resetForNewRound();
        
        gameState.nextRound();
        gameState.startCountdown();
        gameState.setGameInProgress(true);
        
        notifyRoundStarted();
        
        // If computer player, make its choice immediately but don't reveal it
        if (player2 instanceof ComputerPlayer) {
            Gesture computerChoice = player2.makeChoice();
            player2.setGesture(computerChoice);
        }
    }
    
    /**
     * Process a key press during the game
     * @param key The key that was pressed
     * @return true if the key was processed successfully
     */
    public boolean processKeyPress(char key) {
        if (!gameState.isRoundInProgress() || gameState.isCountdownExpired()) {
            return false;
        }
        
        boolean processed = false;
        
        // Try player 1 first
        if (player1 instanceof HumanPlayer && !player1.hasSubmittedGesture()) {
            if (((HumanPlayer) player1).processKeyInput(key)) {
                processed = true;
                notifyPlayerGestureSubmitted(1, player1.getCurrentGesture());
            }
        }
        
        // Try player 2 if PvP mode
        if (gameState.getMode() == GameMode.PLAYER_VS_PLAYER && 
            player2 instanceof HumanPlayer && !player2.hasSubmittedGesture()) {
            if (((HumanPlayer) player2).processKeyInput(key)) {
                processed = true;
                notifyPlayerGestureSubmitted(2, player2.getCurrentGesture());
            }
        }
        
        // Check if round should end
        checkRoundCompletion();
        
        return processed;
    }
    
    /**
     * Check if the current round is complete and process results
     */
    private void checkRoundCompletion() {
        boolean bothPlayersReady = player1.hasSubmittedGesture() && player2.hasSubmittedGesture();
        boolean countdownExpired = gameState.isCountdownExpired();
        
        if (bothPlayersReady || countdownExpired) {
            endRound();
        }
    }
    
    /**
     * End the current round and determine winner
     */
    private void endRound() {
        gameState.setRoundInProgress(false);
        
        Gesture gesture1 = player1.getCurrentGesture();
        Gesture gesture2 = player2.getCurrentGesture();
        
        // Handle case where players didn't submit in time
        if (gesture1 == null && gesture2 == null) {
            // Both players forfeit - count as draw
            gameState.incrementDraws();
            notifyRoundResult(gesture1, gesture2, 0, "Both players forfeited!");
        } else if (gesture1 == null) {
            // Player 1 forfeited - Player 2 wins
            gameState.incrementPlayer2Wins();
            notifyRoundResult(gesture1, gesture2, 2, player1.getName() + " forfeited!");
        } else if (gesture2 == null) {
            // Player 2 forfeited - Player 1 wins
            gameState.incrementPlayer1Wins();
            notifyRoundResult(gesture1, gesture2, 1, player2.getName() + " forfeited!");
        } else {
            // Both players submitted - normal game logic
            int winner = determineRoundWinner(gesture1, gesture2);
            
            if (winner == 1) {
                gameState.incrementPlayer1Wins();
                notifyRoundResult(gesture1, gesture2, winner, 
                    gesture1.getDisplayName() + " beats " + gesture2.getDisplayName());
            } else if (winner == 2) {
                gameState.incrementPlayer2Wins();
                notifyRoundResult(gesture1, gesture2, winner, 
                    gesture2.getDisplayName() + " beats " + gesture1.getDisplayName());
            } else {
                gameState.incrementDraws();
                notifyRoundResult(gesture1, gesture2, winner, "It's a tie!");
            }
            
            // Update computer AI with opponent's gesture
            if (player1 instanceof ComputerPlayer) {
                ((ComputerPlayer) player1).addOpponentGestureToHistory(gesture2);
            }
            if (player2 instanceof ComputerPlayer) {
                ((ComputerPlayer) player2).addOpponentGestureToHistory(gesture1);
            }
        }
        
        // Check if game is finished
        if (gameState.isGameFinished()) {
            endGame();
        }
    }
    
    /**
     * Determine the winner of a round
     * @param gesture1 Player 1's gesture
     * @param gesture2 Player 2's gesture
     * @return 1 if player 1 wins, 2 if player 2 wins, 0 if draw
     */
    private int determineRoundWinner(Gesture gesture1, Gesture gesture2) {
        if (gesture1 == gesture2) {
            return 0; // Draw
        } else if (gesture1.beats(gesture2)) {
            return 1; // Player 1 wins
        } else {
            return 2; // Player 2 wins
        }
    }
    
    /**
     * End the game and declare overall winner
     */
    private void endGame() {
        gameState.setGameInProgress(false);
        int gameWinner = gameState.getGameWinner();
        notifyGameEnded(gameWinner);
    }
    
    /**
     * Force end the current round (for timeout handling)
     */
    public void forceEndRound() {
        if (gameState.isRoundInProgress()) {
            endRound();
        }
    }
    
    // Getters
    public GameState getGameState() {
        return gameState;
    }
    
    public Player getPlayer1() {
        return player1;
    }
    
    public Player getPlayer2() {
        return player2;
    }
    
    // Listener management
    public void addListener(GameEngineListener listener) {
        listeners.add(listener);
    }
    
    public void removeListener(GameEngineListener listener) {
        listeners.remove(listener);
    }
    
    // Notification methods
    private void notifyGameInitialized() {
        for (GameEngineListener listener : listeners) {
            listener.onGameInitialized(gameState);
        }
    }
    
    private void notifyRoundStarted() {
        for (GameEngineListener listener : listeners) {
            listener.onRoundStarted(gameState.getCurrentRound());
        }
    }
    
    private void notifyPlayerGestureSubmitted(int playerNumber, Gesture gesture) {
        for (GameEngineListener listener : listeners) {
            listener.onPlayerGestureSubmitted(playerNumber, gesture);
        }
    }
    
    private void notifyRoundResult(Gesture gesture1, Gesture gesture2, int winner, String message) {
        for (GameEngineListener listener : listeners) {
            listener.onRoundResult(gesture1, gesture2, winner, message);
        }
    }
    
    private void notifyGameEnded(int winner) {
        for (GameEngineListener listener : listeners) {
            listener.onGameEnded(winner, gameState);
        }
    }
    
    /**
     * Interface for game engine event listeners
     */
    public interface GameEngineListener {
        void onGameInitialized(GameState gameState);
        void onRoundStarted(int roundNumber);
        void onPlayerGestureSubmitted(int playerNumber, Gesture gesture);
        void onRoundResult(Gesture gesture1, Gesture gesture2, int winner, String message);
        void onGameEnded(int winner, GameState gameState);
    }
}
