package util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;
import java.util.HashSet;

/**
 * Handles keyboard input with anti-cheat mechanisms
 */
public class InputHandler implements KeyListener {
    private InputHandlerListener listener;
    private Set<Integer> pressedKeys;
    private long lastKeyPressTime;
    private int keyPressCount;
    private static final int MAX_KEYS_PER_SECOND = 10; // Anti-cheat threshold
    private static final long KEY_PRESS_WINDOW = 1000; // 1 second window
    
    public InputHandler(InputHandlerListener listener) {
        this.listener = listener;
        this.pressedKeys = new HashSet<>();
        this.lastKeyPressTime = 0;
        this.keyPressCount = 0;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        char keyChar = e.getKeyChar();
        
        // Prevent key repeat
        if (pressedKeys.contains(keyCode)) {
            return;
        }
        
        pressedKeys.add(keyCode);
        
        // Anti-cheat: Check for excessive key presses
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastKeyPressTime > KEY_PRESS_WINDOW) {
            // Reset counter after time window
            keyPressCount = 0;
            lastKeyPressTime = currentTime;
        }
        
        keyPressCount++;
        
        if (keyPressCount > MAX_KEYS_PER_SECOND) {
            // Potential cheating detected
            listener.onCheatDetected("Excessive key presses detected");
            return;
        }
        
        // Process the key
        listener.onKeyPressed(keyChar);
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used, but required by interface
    }
    
    /**
     * Reset the input handler state
     */
    public void reset() {
        pressedKeys.clear();
        keyPressCount = 0;
        lastKeyPressTime = 0;
    }
    
    /**
     * Interface for input handler events
     */
    public interface InputHandlerListener {
        void onKeyPressed(char key);
        void onCheatDetected(String reason);
    }
}
