package game;

/**
 * Enumeration representing the three possible gestures in Rock-Paper-Scissors
 */
public enum Gesture {
    ROCK("Rock", "R"),
    PAPER("Paper", "P"),
    SCISSORS("Scissors", "S");
    
    private final String displayName;
    private final String shortCode;
    
    Gesture(String displayName, String shortCode) {
        this.displayName = displayName;
        this.shortCode = shortCode;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getShortCode() {
        return shortCode;
    }
    
    /**
     * Determines if this gesture beats the opponent gesture
     * @param opponent The opponent's gesture
     * @return true if this gesture wins, false otherwise
     */
    public boolean beats(Gesture opponent) {
        return (this == ROCK && opponent == SCISSORS) ||
               (this == PAPER && opponent == ROCK) ||
               (this == SCISSORS && opponent == PAPER);
    }
    
    /**
     * Get gesture from short code
     * @param shortCode The short code (R, P, S)
     * @return The corresponding gesture, or null if not found
     */
    public static Gesture fromShortCode(String shortCode) {
        for (Gesture gesture : values()) {
            if (gesture.shortCode.equalsIgnoreCase(shortCode)) {
                return gesture;
            }
        }
        return null;
    }
    
    /**
     * Get a random gesture (for computer players)
     * @return A randomly selected gesture
     */
    public static Gesture random() {
        Gesture[] gestures = values();
        return gestures[(int) (Math.random() * gestures.length)];
    }
}
