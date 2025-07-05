package player;

import game.Gesture;

/**
 * Represents a human player who makes choices via keyboard input
 */
public class HumanPlayer extends Player {
    private char rockKey;
    private char paperKey;
    private char scissorsKey;
    
    public HumanPlayer(String name, char rockKey, char paperKey, char scissorsKey) {
        super(name);
        this.rockKey = Character.toLowerCase(rockKey);
        this.paperKey = Character.toLowerCase(paperKey);
        this.scissorsKey = Character.toLowerCase(scissorsKey);
    }
    
    public char getRockKey() {
        return rockKey;
    }
    
    public char getPaperKey() {
        return paperKey;
    }
    
    public char getScissorsKey() {
        return scissorsKey;
    }
    
    /**
     * Process key input and set gesture if valid
     * @param key The key that was pressed
     * @return true if the key was valid and gesture was set, false otherwise
     */
    public boolean processKeyInput(char key) {
        char lowerKey = Character.toLowerCase(key);
        
        if (lowerKey == rockKey) {
            return setGesture(Gesture.ROCK);
        } else if (lowerKey == paperKey) {
            return setGesture(Gesture.PAPER);
        } else if (lowerKey == scissorsKey) {
            return setGesture(Gesture.SCISSORS);
        }
        
        return false; // Invalid key
    }
    
    /**
     * Human players don't make automatic choices
     * This method is mainly for consistency with the abstract class
     * @return null (human players choose via keyboard input)
     */
    @Override
    public Gesture makeChoice() {
        return null; // Human players make choices via keyboard input
    }
    
    /**
     * Get key binding information as a string
     * @return String describing the key bindings
     */
    public String getKeyBindings() {
        return String.format("%s: %c=Rock, %c=Paper, %c=Scissors", 
                           getName(), 
                           Character.toUpperCase(rockKey),
                           Character.toUpperCase(paperKey),
                           Character.toUpperCase(scissorsKey));
    }
}
