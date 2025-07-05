package player;

import game.Gesture;

/**
 * Abstract base class for all player types
 */
public abstract class Player {
    protected String name;
    protected Gesture currentGesture;
    protected boolean hasSubmittedGesture;
    
    public Player(String name) {
        this.name = name;
        this.currentGesture = null;
        this.hasSubmittedGesture = false;
    }
    
    public String getName() {
        return name;
    }
    
    public Gesture getCurrentGesture() {
        return currentGesture;
    }
    
    public boolean hasSubmittedGesture() {
        return hasSubmittedGesture;
    }
    
    /**
     * Reset the player's gesture for a new round
     */
    public void resetForNewRound() {
        this.currentGesture = null;
        this.hasSubmittedGesture = false;
    }
    
    /**
     * Abstract method for making a gesture choice
     * This will be implemented differently for human and computer players
     * @return The chosen gesture
     */
    public abstract Gesture makeChoice();
    
    /**
     * Set the player's gesture
     * @param gesture The gesture to set
     * @return true if gesture was set successfully, false if already submitted
     */
    public boolean setGesture(Gesture gesture) {
        if (hasSubmittedGesture) {
            return false; // Anti-cheat: prevent multiple submissions
        }
        this.currentGesture = gesture;
        this.hasSubmittedGesture = true;
        return true;
    }
}
