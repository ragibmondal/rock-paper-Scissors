package player;

import game.Gesture;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents a computer player with intelligent decision making
 */
public class ComputerPlayer extends Player {
    private List<Gesture> opponentHistory;
    private int difficulty; // 0 = random, 1 = basic pattern, 2 = advanced pattern
    
    public ComputerPlayer(String name, int difficulty) {
        super(name);
        this.opponentHistory = new ArrayList<>();
        this.difficulty = Math.max(0, Math.min(2, difficulty)); // Clamp between 0-2
    }
    
    /**
     * Add opponent's gesture to history for pattern analysis
     * @param gesture The opponent's previous gesture
     */
    public void addOpponentGestureToHistory(Gesture gesture) {
        if (gesture != null) {
            opponentHistory.add(gesture);
        }
    }
    
    /**
     * Make an intelligent choice based on difficulty level and opponent history
     * @return The chosen gesture
     */
    @Override
    public Gesture makeChoice() {
        switch (difficulty) {
            case 0:
                return makeRandomChoice();
            case 1:
                return makeBasicPatternChoice();
            case 2:
                return makeAdvancedPatternChoice();
            default:
                return makeRandomChoice();
        }
    }
    
    /**
     * Make a completely random choice
     * @return Random gesture
     */
    private Gesture makeRandomChoice() {
        return Gesture.random();
    }
    
    /**
     * Make choice based on basic pattern recognition
     * @return Gesture that counters the most frequent opponent gesture
     */
    private Gesture makeBasicPatternChoice() {
        if (opponentHistory.isEmpty()) {
            return makeRandomChoice();
        }
        
        // Count frequency of each gesture
        Map<Gesture, Integer> frequencies = new HashMap<>();
        for (Gesture gesture : opponentHistory) {
            frequencies.put(gesture, frequencies.getOrDefault(gesture, 0) + 1);
        }
        
        // Find most frequent gesture
        Gesture mostFrequent = Gesture.ROCK;
        int maxCount = 0;
        for (Map.Entry<Gesture, Integer> entry : frequencies.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }
        
        // Return gesture that beats the most frequent
        return getCounterGesture(mostFrequent);
    }
    
    /**
     * Make choice based on advanced pattern recognition
     * Looks for sequences and recent trends
     * @return Strategically chosen gesture
     */
    private Gesture makeAdvancedPatternChoice() {
        if (opponentHistory.size() < 3) {
            return makeBasicPatternChoice();
        }
        
        // Weight recent gestures more heavily
        Map<Gesture, Double> weights = new HashMap<>();
        double totalWeight = 0;
        
        for (int i = 0; i < opponentHistory.size(); i++) {
            Gesture gesture = opponentHistory.get(i);
            // Recent gestures get more weight
            double weight = Math.pow(1.2, i);
            weights.put(gesture, weights.getOrDefault(gesture, 0.0) + weight);
            totalWeight += weight;
        }
        
        // Look for the last few gestures to predict next one
        if (opponentHistory.size() >= 2) {
            Gesture lastGesture = opponentHistory.get(opponentHistory.size() - 1);
            Gesture secondLastGesture = opponentHistory.get(opponentHistory.size() - 2);
            
            // If opponent is alternating, predict the next in sequence
            if (lastGesture != secondLastGesture) {
                // Add extra weight to the gesture that would continue the pattern
                Gesture predictedNext = predictNextInSequence(secondLastGesture, lastGesture);
                if (predictedNext != null) {
                    weights.put(predictedNext, weights.getOrDefault(predictedNext, 0.0) + totalWeight * 0.3);
                }
            }
        }
        
        // Find the gesture with highest weight
        Gesture mostLikely = Gesture.ROCK;
        double maxWeight = 0;
        for (Map.Entry<Gesture, Double> entry : weights.entrySet()) {
            if (entry.getValue() > maxWeight) {
                maxWeight = entry.getValue();
                mostLikely = entry.getKey();
            }
        }
        
        // Add some randomness to avoid being too predictable
        if (Math.random() < 0.2) { // 20% chance of random choice
            return makeRandomChoice();
        }
        
        return getCounterGesture(mostLikely);
    }
    
    /**
     * Predict the next gesture in a sequence
     * @param first First gesture in sequence
     * @param second Second gesture in sequence
     * @return Predicted third gesture, or null if no pattern detected
     */
    private Gesture predictNextInSequence(Gesture first, Gesture second) {
        // Simple cycling pattern: Rock -> Paper -> Scissors -> Rock
        if (first == Gesture.ROCK && second == Gesture.PAPER) {
            return Gesture.SCISSORS;
        } else if (first == Gesture.PAPER && second == Gesture.SCISSORS) {
            return Gesture.ROCK;
        } else if (first == Gesture.SCISSORS && second == Gesture.ROCK) {
            return Gesture.PAPER;
        }
        
        // Reverse cycling pattern
        if (first == Gesture.ROCK && second == Gesture.SCISSORS) {
            return Gesture.PAPER;
        } else if (first == Gesture.SCISSORS && second == Gesture.PAPER) {
            return Gesture.ROCK;
        } else if (first == Gesture.PAPER && second == Gesture.ROCK) {
            return Gesture.SCISSORS;
        }
        
        return null; // No clear pattern
    }
    
    /**
     * Get the gesture that beats the given gesture
     * @param gesture The gesture to counter
     * @return The counter gesture
     */
    private Gesture getCounterGesture(Gesture gesture) {
        switch (gesture) {
            case ROCK:
                return Gesture.PAPER;
            case PAPER:
                return Gesture.SCISSORS;
            case SCISSORS:
                return Gesture.ROCK;
            default:
                return Gesture.random();
        }
    }
    
    /**
     * Reset the computer player for a new game
     */
    @Override
    public void resetForNewRound() {
        super.resetForNewRound();
        // Don't clear history between rounds, only between games
    }
    
    /**
     * Reset for a completely new game
     */
    public void resetForNewGame() {
        resetForNewRound();
        opponentHistory.clear();
    }
    
    /**
     * Get difficulty level
     * @return The difficulty level (0-2)
     */
    public int getDifficulty() {
        return difficulty;
    }
    
    /**
     * Get difficulty description
     * @return String describing the difficulty level
     */
    public String getDifficultyDescription() {
        switch (difficulty) {
            case 0:
                return "Easy (Random)";
            case 1:
                return "Medium (Basic Pattern)";
            case 2:
                return "Hard (Advanced Pattern)";
            default:
                return "Unknown";
        }
    }
}
