package game;

/**
 * Represents the current state of the game
 */
public class GameState {
    private GameMode mode;
    private int currentRound;
    private int maxRounds;
    private int player1Wins;
    private int player2Wins;
    private int draws;
    private boolean gameInProgress;
    private boolean roundInProgress;
    private long countdownStartTime;
    private static final int COUNTDOWN_DURATION = 3000; // 3 seconds in milliseconds
    
    public GameState(GameMode mode, int maxRounds) {
        this.mode = mode;
        this.maxRounds = maxRounds;
        this.currentRound = 0;
        this.player1Wins = 0;
        this.player2Wins = 0;
        this.draws = 0;
        this.gameInProgress = false;
        this.roundInProgress = false;
        this.countdownStartTime = 0;
    }
    
    // Getters and setters
    public GameMode getMode() {
        return mode;
    }
    
    public void setMode(GameMode mode) {
        this.mode = mode;
    }
    
    public int getCurrentRound() {
        return currentRound;
    }
    
    public void nextRound() {
        this.currentRound++;
    }
    
    public int getMaxRounds() {
        return maxRounds;
    }
    
    public void setMaxRounds(int maxRounds) {
        this.maxRounds = maxRounds;
    }
    
    public int getPlayer1Wins() {
        return player1Wins;
    }
    
    public void incrementPlayer1Wins() {
        this.player1Wins++;
    }
    
    public int getPlayer2Wins() {
        return player2Wins;
    }
    
    public void incrementPlayer2Wins() {
        this.player2Wins++;
    }
    
    public int getDraws() {
        return draws;
    }
    
    public void incrementDraws() {
        this.draws++;
    }
    
    public boolean isGameInProgress() {
        return gameInProgress;
    }
    
    public void setGameInProgress(boolean gameInProgress) {
        this.gameInProgress = gameInProgress;
    }
    
    public boolean isRoundInProgress() {
        return roundInProgress;
    }
    
    public void setRoundInProgress(boolean roundInProgress) {
        this.roundInProgress = roundInProgress;
    }
    
    public void startCountdown() {
        this.countdownStartTime = System.currentTimeMillis();
        this.roundInProgress = true;
    }
    
    public long getCountdownTimeRemaining() {
        if (countdownStartTime == 0) {
            return COUNTDOWN_DURATION;
        }
        long elapsed = System.currentTimeMillis() - countdownStartTime;
        return Math.max(0, COUNTDOWN_DURATION - elapsed);
    }
    
    public boolean isCountdownExpired() {
        return getCountdownTimeRemaining() == 0;
    }
    
    /**
     * Check if the game is finished (one player has won the majority of rounds)
     * @return true if the game is finished, false otherwise
     */
    public boolean isGameFinished() {
        int roundsToWin = (maxRounds / 2) + 1;
        return player1Wins >= roundsToWin || player2Wins >= roundsToWin || currentRound >= maxRounds;
    }
    
    /**
     * Get the winner of the game
     * @return 1 if player 1 wins, 2 if player 2 wins, 0 if draw
     */
    public int getGameWinner() {
        if (player1Wins > player2Wins) {
            return 1;
        } else if (player2Wins > player1Wins) {
            return 2;
        } else {
            return 0; // Draw
        }
    }
    
    /**
     * Reset the game state for a new game
     */
    public void reset() {
        this.currentRound = 0;
        this.player1Wins = 0;
        this.player2Wins = 0;
        this.draws = 0;
        this.gameInProgress = false;
        this.roundInProgress = false;
        this.countdownStartTime = 0;
    }
}
